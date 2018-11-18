package chartconstellation.app.util;

import chartconstellation.app.entities.response.Cluster;
import chartconstellation.app.entities.Chart;
import chartconstellation.app.entities.response.IdCoordinates;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ClusterUtil {

    //private Set<String> stopwords = ['manoj'];

    public List<Cluster> generateClusterInfo(HashMap<Integer, List<IdCoordinates>> coordinatesMap, List<Chart> charts) {

        HashMap<Integer, List<Chart>> idChartsMap = new HashMap<>();

        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesMap.entrySet()) {

            List<IdCoordinates> idCoordinatesList = entry.getValue();

            List<Chart> clusterCharts = new ArrayList<>();

            for(IdCoordinates idCoordinate : idCoordinatesList) {

                String id = idCoordinate.getId();

                for(Chart chart : charts) {

                    if(chart.getId().equals(id)) {
                        clusterCharts.add(chart);
                    }
                }

            }

            idChartsMap.put(entry.getKey(), clusterCharts);
        }

        //System.out.println(idChartsMap);
        //System.out.println(idChartsMap.size());
        return aggregateClusterInfo(idChartsMap);

    }

    public List<Cluster> aggregateClusterInfo(HashMap<Integer, List<Chart>> idChartsMap ) {

        List<Cluster> clusterList = new ArrayList<>();

        for(Map.Entry<Integer, List<Chart>> entry : idChartsMap.entrySet()) {
            Cluster cluster = getInfo(entry.getValue());
            cluster.setClusterId(entry.getKey() + 1);
            cluster.setSize(entry.getValue().size());
            clusterList.add(cluster);
        }

        return clusterList;
    }

    public Cluster getInfo(List<Chart> charts) {

        Cluster cluster = new Cluster();

        List<String> attributesList = new ArrayList<>();
        List<String> usersList = new ArrayList<>();
        List<String> keywordList = new ArrayList<>();

        for(Chart chart : charts) {
            List<String> attributes = new ArrayList<>();
            try {
                attributes = chart.getAttributes();
            } catch(Exception e) {

            }

            String user = chart.getUser();
            if (attributes == null) {
                attributesList.addAll(new HashSet<String>());
            } else {
                attributesList.addAll(attributes);
            }

            usersList.add(user);
            String desc = chart.getDescription();
            String title = chart.getTitle();
            keywordList.add(desc);
            keywordList.add(title);
        }

        HashMap<String, List<String>> usersMap = new HashMap<>();
        usersMap.put("Users", getInfoList(usersList));


        HashMap<String, List<String>> attributesMap = new HashMap<>();
        usersMap.put("Attributes", getInfoList(attributesList));

//        HashMap<String, List<String>> keywordsMap = new HashMap<>();
//        keywordsMap.put("keywords", getTokenizedList(keywordList));

        cluster.setUsers(usersMap);
        cluster.setAttributes(attributesMap);
        cluster.setKeywords(getTokenizedList(keywordList));

        return cluster;
    }

    public List<String> getInfoList(List<String> set) {

        HashMap<String, Integer> map = new HashMap<>();

        for(String s : set) {
            if(map.containsKey(s)) {
                int val = map.get(s);
                map.put(s, val+1);
            } else {
                map.put(s, 1);
            }
        }
        return generateListFromaMap(map);
    }

    public List<String> generateListFromaMap( HashMap<String, Integer> map ) {
        List<String> list = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            int val = entry.getValue();
            list.add(key+"("+val+")");
        }

        return list;
    }

    public List<String> getTokenizedList(List<String> list) {

        HashMap<String, Integer> map = new HashMap<>();

        try {

            for (String str : list) {
                //String input = "Input text, with words, punctuation, etc. Well, it's rather short.";
                Pattern p = Pattern.compile("[\\w']+");
                Matcher m = p.matcher(str);

                while (m.find()) {
                    String s = str.substring(m.start(), m.end()).toLowerCase();
                    if (map.containsKey(s)) {
                        int val = map.get(s);
                        map.put(s, val + 1);
                    } else {
                        map.put(s, 1);
                    }
                }
            }

            return generateListFromaMap(map);

        } catch(Exception e) {

        }

        return new ArrayList<>();
    }
}
