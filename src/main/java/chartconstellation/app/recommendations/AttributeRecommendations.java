package chartconstellation.app.recommendations;

import chartconstellation.app.entities.response.IdCoordinates;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AttributeRecommendations {

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

    public HashMap<String, Set<String>> getAttributeRecommendationsForAllUsers(Collection<List<IdCoordinates>> coordinatesCollection, List<String> fullAttributesList) {

        Iterator<List<IdCoordinates>> itr = coordinatesCollection.iterator();

        List<IdCoordinates> idCoordinatesList = new ArrayList<>();
        while(itr.hasNext()) {
            List<IdCoordinates> list = itr.next();
            idCoordinatesList.addAll(list);
        }

        HashMap<String, Set<String>> userExploringAttributes = new HashMap<>();
        HashMap<String, Set<String>> userSimilarAttributes = new HashMap<>();

        HashMap<String, HashMap<String, Integer>> map = getUsersAttributesMap(idCoordinatesList);
        for(Map.Entry<String, HashMap<String, Integer>> entry1 : map.entrySet()) {
            //System.out.print(entry1.getKey()+" ");
            TreeMap<Double, String> treeMap = new TreeMap<>();
            for(Map.Entry<String, HashMap<String, Integer>> entry2 : map.entrySet()) {
                if(!entry1.getKey().equals(entry2.getKey())) {
                    treeMap.put(cosineSimilarity(entry1.getValue(), entry2.getValue()), entry2.getKey());
                    //System.out.println(cosineSimilarity(entry1.getValue(), entry2.getValue()));
                }
            }
            int size = treeMap.size();
            int count = 0;
            for(Map.Entry<Double, String> entry : treeMap.entrySet()) {
                Set<String> diffSet = getDissimilarAttributes(entry1.getValue(), map.get(entry.getValue()));
                if(count < size/2) {

                    if(userExploringAttributes.containsKey(entry1.getKey())) {
                        Set<String> outputSet = userExploringAttributes.get(entry1.getKey());
                        outputSet.addAll(diffSet);
                        userExploringAttributes.put(entry1.getKey(), outputSet);
                    } else {
                        userExploringAttributes.put(entry1.getKey(), diffSet);
                    }
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
