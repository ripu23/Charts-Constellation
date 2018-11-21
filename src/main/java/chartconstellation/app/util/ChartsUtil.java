package chartconstellation.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chartconstellation.app.entities.Chart;
import chartconstellation.app.entities.ChartType;
import chartconstellation.app.entities.UserCharts;

@Component
public class ChartsUtil {

    @Autowired
    DbUtil dbUtil;

    @Autowired
    ClusterUtil clusterUtil;

    public HashMap<String, UserCharts> getAllUserCharts(String database, String collection) {

        List<Chart> chartsList  = dbUtil.getAttributesFromCollection(database, collection);
        HashMap<String, UserCharts>  userChartsMap = new HashMap<>();

        for(Chart chart : chartsList) {

            String userName = chart.getUser();
            if(userName != null) {
                if (userChartsMap.containsKey(userName)) {
                    UserCharts userCharts = userChartsMap.get(userName);
                    List<String> chartIds = userCharts.getIdList();
                    chartIds.add(chart.getId());
                    userCharts.setUserName(userName);
                    userCharts.setIdList(chartIds);
                    userChartsMap.put(userName, userCharts);
                } else {
                    List<String> chartIds = new ArrayList<>();
                    chartIds.add(chart.getId());
                    UserCharts userCharts = new UserCharts();
                    userCharts.setUserName(userName);
                    userCharts.setIdList(chartIds);
                    userChartsMap.put(userName, userCharts);
                }
            }

        }

        return userChartsMap;

    }

    public ChartType getAllChartTypes(String database, String collection) {

        List<Chart> chartsList  = dbUtil.getAttributesFromCollection(database, collection);
        Set<String> chartTypeSet = new HashSet<>();
        ChartType chartTypeObj = new ChartType();

        for(Chart chart : chartsList) {
            String chartType = chart.getChartType();
            if(chartType != null) {
                chartTypeSet.add(chartType);
            }
        }

        chartTypeObj.setChartTypes(chartTypeSet);

        return chartTypeObj;

    }

    public List<String> getKeyWords(Chart chart) {

        try {
            List<String> attributes = chart.getAttributes();
            List<String> keywords = new ArrayList<>(clusterUtil.getTokenizedMap(attributes).keySet());
            return keywords;
        } catch(Exception e) {

        }
        return new ArrayList<>();
    }
}
