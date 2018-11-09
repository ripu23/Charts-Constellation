package chartconstellation.app.util;

import chartconstellation.app.entities.Chart;
import chartconstellation.app.entities.UserCharts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class UsersUtil {

    @Autowired
    DbUtil dbUtil;

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
}
