package chartconstellation.app.util;

import chartconstellation.app.entities.response.Cluster;
import chartconstellation.app.entities.Chart;
import chartconstellation.app.entities.response.IdCoordinates;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ClusterUtil {

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

        System.out.println(idChartsMap);
        System.out.println(idChartsMap.size());
        return aggregateClusterInfo(idChartsMap);

    }

    public List<Cluster> aggregateClusterInfo(HashMap<Integer, List<Chart>> idChartsMap ) {

        List<Cluster> clusterList = new ArrayList<>();

        for(Map.Entry<Integer, List<Chart>> entry : idChartsMap.entrySet()) {
            Cluster cluster = getInfo(entry.getValue());
            cluster.setClusterId(entry.getKey());
            clusterList.add(cluster);
        }

        return clusterList;
    }

    public Cluster getInfo(List<Chart> charts) {

        Cluster cluster = new Cluster();

        List<String> attributesList = new ArrayList<>();
        List<String> usersList = new ArrayList<>();

        for(Chart chart : charts) {
            Set<String> attributes = chart.getAttributes();
            String user = chart.getUser();
            attributesList.addAll(attributes);
            usersList.add(user);
        }

        cluster.setUsers(getInfoList(usersList));
        cluster.setAttributes(getInfoList(attributesList));

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

        List<String> list = new ArrayList<>();

        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            int val = entry.getValue();
            list.add(key+"("+val+")");
        }

        return list;
    }
}
