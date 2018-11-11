package chartconstellation.app.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.appconfiguration.ScalingConfig;
import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.UserCharts;
import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.util.ChartsUtil;
import chartconstellation.app.util.CoordinatesScalingUtil;
import chartconstellation.app.util.CoordinatesUtil;


@RestController
@RequestMapping("/coordinates")
public class CoordinatesController {

    @Autowired
    Configuration configuration;

    @Autowired
    CoordinatesUtil coordinatesUtil;

    @Autowired
    Clustering clustering;

    @Autowired
    CoordinatesScalingUtil coordinatesScalingUtil;

    @Autowired
    ChartsUtil chartsUtil;

    @RequestMapping(value="/getCoordinates", method= RequestMethod.GET)
    @ResponseBody
    public Collection<List<IdCoordinates>> coordinates(@RequestParam("descWeight") Double descWeight,
                                                       @RequestParam("attrWeight") Double attrWeight,
                                                       @RequestParam("chartEncodingWeight") Double chartEncodingWeight,
                                                       @RequestParam("colorMap") Object colorMap) {

        HashMap<String, String> userColorMap = new HashMap<>();

        String colorData = colorMap.toString();
        System.out.println(colorData);
        JSONArray jsonArr = new JSONArray(colorData);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject object = jsonArr.getJSONObject(i);
            String userName = object.get("userName").toString();
            String color = object.get("color").toString();
            userColorMap.put(userName, color);
        }

        HashMap<String, UserCharts> map = chartsUtil
                .getAllUserCharts(configuration.getMongoDatabase()
                        , configuration.getOlympicchartcollection());

        HashMap<String, String> idUserMap = new HashMap<>();

        for(Map.Entry<String, UserCharts>  entry : map.entrySet()) {
            String userName = entry.getKey();
            List<String> ids = entry.getValue().getIdList();
            for(String id : ids) {
                idUserMap.put(id, userName);
            }
        }


        List<IdCoordinates> coordinatesList = coordinatesUtil.calculateCoordinates(descWeight, attrWeight, chartEncodingWeight);

        List<IdCoordinates> clusteredCoordinates = clustering.getClusteredCoordinates(4, 20, coordinatesList);

//        coordinatesScalingUtil.setCoordinatesList(clusteredCoordinates);
//
//        clusteredCoordinates = coordinatesScalingUtil.getScaledCoordinates(configuration.getMdsScalingConfig());


        HashMap<Integer, List<IdCoordinates>> coordinatesHashMap = new HashMap<>();

        for(IdCoordinates idCoordinate : clusteredCoordinates) {

            idCoordinate.setColor(userColorMap.get(idUserMap.get(idCoordinate.getId())));
            idCoordinate.setUserName(idUserMap.get(idCoordinate.getId()));

            if(coordinatesHashMap.containsKey(idCoordinate.getClusterId())) {

                List<IdCoordinates> coordinates = coordinatesHashMap.get(idCoordinate.getClusterId());
                coordinates.add(idCoordinate);
                coordinatesHashMap.put(idCoordinate.getClusterId(), coordinates);

            } else {

                List<IdCoordinates> coordinates = new ArrayList<>();
                coordinates.add(idCoordinate);
                coordinatesHashMap.put(idCoordinate.getClusterId(), coordinates);

            }
        }

        int clusterSize = coordinatesHashMap.keySet().size();
        ScalingConfig scalingConfig = configuration.getMdsScalingConfig();
        ScalingConfig newScalingConfig = configuration.getMdsScalingConfig();
        Double startX = scalingConfig.getXmin();
        Double stepX = (scalingConfig.getXmax() - scalingConfig.getXmin()) / clusterSize;
        Double startY = scalingConfig.getYmin();
        Double stepY = (scalingConfig.getYmax() - scalingConfig.getYmin()) / clusterSize;
        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesHashMap.entrySet()) {

            coordinatesScalingUtil.setCoordinatesList(entry.getValue());
            Double xMin = startX + 5;
            Double xMax = startX + stepX - 5;
            Double yMin = startY + 5;
            Double yMax = startY + stepY - 5;

            startX += stepX;
            startY += stepY;

            List<IdCoordinates> newIdCoordinatesList = coordinatesScalingUtil.getScaledCoordinates(xMin, xMax, yMin, yMax);

            coordinatesHashMap.put(entry.getKey(), newIdCoordinatesList);
        }
        return coordinatesHashMap.values();
    }

    @RequestMapping(value="/getFilterCoordinates", method= RequestMethod.GET)
    @ResponseBody
    public void filterCoordinates(@RequestParam("colorMap") Object colorMap) {


    }
}
