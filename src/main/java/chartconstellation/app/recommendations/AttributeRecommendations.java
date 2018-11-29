package chartconstellation.app.recommendations;

import chartconstellation.app.entities.response.IdCoordinates;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AttributeRecommendations {

    public <K,V extends Comparable<? super V>>
    SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e1.getValue().compareTo(e2.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    public Double cosineSimilarity(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
        Double denom = 0.0;
        Double num = 0.0;

        for(Map.Entry<String, Integer> entry1 : map1.entrySet()) {
            denom += entry1.getValue() * entry1.getValue();
        }

        for(Map.Entry<String, Integer> entry2 : map2.entrySet()) {
            denom += entry2.getValue() * entry2.getValue();
        }

        for(Map.Entry<String, Integer> entry1 : map1.entrySet()) {
            for(Map.Entry<String, Integer> entry2 : map2.entrySet()) {
                if(entry1.getKey().equals(entry2.getKey())) {
                    num += entry1.getValue() * entry2.getValue();
                }
            }
        }

        return num/ denom;
    }

    public Set<String> getDissimilarAttributes(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {

        Set<String> set1 = map1.keySet();
        Set<String> set2 = map2.keySet();

        Set<String> output = new HashSet<>();

        for(String str2 : set2) {
           if(!set1.contains(str2)) {
               output.add(str2);
           }
        }
        return output;
    }

    public HashMap<String, HashMap<String, Integer>> getUsersAttributesMap(List<IdCoordinates> idCoordinatesList) {

        HashMap<String, HashMap<String, Integer>> usersMap = new HashMap<>();

        Set<String> usedAttributes = new HashSet<>();
        Set<String> unusedAttributes = new HashSet<>();

        for(IdCoordinates idCoordinates : idCoordinatesList) {

            String userName = idCoordinates.getUserName();
            Set<String> attrs = idCoordinates.getAttributes();

            if(usersMap.containsKey(userName)) {

                HashMap<String, Integer> attrsMap = usersMap.get(userName);

                for(String str : attrs) {
                    if(attrsMap.containsKey(str)) {
                        attrsMap.put(str, attrsMap.get(str)+1);
                    } else {
                        attrsMap.put(str, 1);
                    }
                }

                usersMap.put(userName, attrsMap);

            } else {
                HashMap<String, Integer> attrsMap = new HashMap<>();
                for(String str : attrs) {
                    if(attrsMap.containsKey(str)) {
                        attrsMap.put(str, attrsMap.get(str)+1);
                    } else {
                        attrsMap.put(str, 1);
                    }
                }

                usersMap.put(userName, attrsMap);
            }
        }
        return usersMap;
    }

    public Set<String> unusedAttributes(Set<String> usedAttributes, List<String> allAttributes) {

        Set<String> output = new HashSet<>();

        for(String str : allAttributes) {
            if(!usedAttributes.contains(str)) {
                output.add(str);
            }
        }
        return output;

    }

    public Set<String> getAttributes(Set<String> attributes,int k) {
        List<String> list = new ArrayList<String>(attributes);
        Collections.shuffle(list);
        Set<String> output = new HashSet<>();
        for(int i=0; i<k; i++) {
            output.add(list.get(i));
        }
        return output;
    }

    public HashMap<String, Set<String>> getAttributeRecommendationsForAllUsers(Collection<List<IdCoordinates>> coordinatesCollection, List<String> fullAttributesList) {

        Iterator<List<IdCoordinates>> itr = coordinatesCollection.iterator();
        Set<String> usedAttributes = new HashSet<>();

        List<IdCoordinates> idCoordinatesList = new ArrayList<>();
        while(itr.hasNext()) {
            List<IdCoordinates> list = itr.next();
            idCoordinatesList.addAll(list);
        }

        HashMap<String, Set<String>> userExploringAttributes = new HashMap<>();
        HashMap<String, Set<String>> userSimilarAttributes = new HashMap<>();

        HashMap<String, HashMap<String, Integer>> map = getUsersAttributesMap(idCoordinatesList);
        for(Map.Entry<String, HashMap<String, Integer>> entry1 : map.entrySet()) {
            usedAttributes.addAll(entry1.getValue().keySet());
        }

        Set<String> unusedAttributes = unusedAttributes(usedAttributes, fullAttributesList);

        for(Map.Entry<String, HashMap<String, Integer>> entry1 : map.entrySet()) {
            Map<String, Double> treeMap = new HashMap<>();

            for(Map.Entry<String, HashMap<String, Integer>> entry2 : map.entrySet()) {
                if(!entry1.getKey().equals(entry2.getKey())) {
                    treeMap.put(entry2.getKey(), cosineSimilarity(entry1.getValue(), entry2.getValue())) ;

                }
            }
            SortedSet<Map.Entry<String, Double>> sortedTreeMap = entriesSortedByValues(treeMap);
            int size = treeMap.size();

            int count = 0;
            Iterator<Map.Entry<String, Double>> itr1 = sortedTreeMap.iterator();
            while(itr1.hasNext()){
                Map.Entry<String, Double> entry = itr1.next();
                Set<String> diffSet = getDissimilarAttributes(entry1.getValue(), map.get(entry.getKey()));

                if(count < size/2) {


                    if(userExploringAttributes.containsKey(entry1.getKey())) {
                        Set<String> outputSet = userExploringAttributes.get(entry1.getKey());
                        outputSet.addAll(diffSet);
                        userExploringAttributes.put(entry1.getKey(), outputSet);
                    } else {
                        userExploringAttributes.put(entry1.getKey(), diffSet);
                    }

                    Set<String> addAttributes = getAttributes(unusedAttributes, 3);


                    Set<String> a = userExploringAttributes.get(entry1.getKey());
                    a.addAll(addAttributes);

                    userExploringAttributes.put(entry1.getKey(), a);
                } else {

                    if(userSimilarAttributes.containsKey(entry1.getKey())) {
                        Set<String> outputSet = userSimilarAttributes.get(entry1.getKey());
                        outputSet.addAll(diffSet);
                        userSimilarAttributes.put(entry1.getKey(), outputSet);
                    } else {
                        userSimilarAttributes.put(entry1.getKey(), diffSet);
                    }
                }
                count++;
            }
        }

        return userExploringAttributes;
    }
}
