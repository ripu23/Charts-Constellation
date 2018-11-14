package chartconstellation.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.UserCharts;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.appconfiguration.ScalingConfig;
import chartconstellation.app.entities.FeatureVector;
import chartconstellation.app.entities.IdFeatures;
import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.entities.response.Point;
import chartconstellation.app.multidimensionalscaling.MDSUtil;

@Component
public class CoordinatesUtil {

    @Autowired
    Configuration configuration;

    @Autowired
    DbUtil dbUtil;

    @Autowired
    MDSUtil mdsUtil;

    @Autowired
    ChartsUtil chartsUtil;

    @Autowired
    CoordinatesScalingUtil coordinatesScalingUtil;

    @Autowired
    Clustering clustering;

    private Double weightedScore(IdFeatures idFeatures, Double descWeight, Double attrWeight, Double chartEncodingWeight) {

        Double value = 0.0;
        value += idFeatures.getAttributeDistance() * attrWeight;
        value += idFeatures.getChartEncodingDistance() * chartEncodingWeight;
        value += idFeatures.getDescriptionDistance() * descWeight;

        return value;
    }

    public List<IdCoordinates>  calculateCoordinates(List<FeatureVector> featurevectors, Double descWeight, Double attrWeight, Double chartEncodingWeight) {

        int size = featurevectors.size();
        double[][] input = new double[size][size];

        HashMap<String, Integer> idKeyMap = new HashMap<>();
        HashMap<Integer, String> keyIdMap = new HashMap<>();
        int key = 0;

        for(FeatureVector featureVector : featurevectors) {
            idKeyMap.put(featureVector.getId(), key);
            keyIdMap.put(key, featureVector.getId());
            key++;
        }


        for(FeatureVector featureVector : featurevectors) {
            int index_i = idKeyMap.get(featureVector.getId());

            for(IdFeatures idFeature : featureVector.getFeatures()) {
                int index_j = idKeyMap.get(idFeature.getId());
                double score = weightedScore(idFeature, descWeight, attrWeight, chartEncodingWeight);

                input[index_i][index_j] = score;
            }

        }
//
//        for(int i=0; i< input.length; i++) {
//            for(int j=0; j < input[0].length; j++) {
//                System.out.print(input[i][j]+" ");
//            }
//            System.out.println();
//        }

        double output[][] = mdsUtil.classicalScaling(input);
        List<IdCoordinates> coordinates = new ArrayList<>();


        for(int i=0 ;i< output[0].length; i++) {
            IdCoordinates idCoordinate = new IdCoordinates();
            idCoordinate.setId(keyIdMap.get(i));
            idCoordinate.setPoint(new Point(output[0][i], output[1][i]));
            coordinates.add(idCoordinate);
        }

        return coordinates;
    }

    public HashMap<Integer, ScalingConfig> distributeSVG(HashMap<Integer, List<IdCoordinates>> coordinatesHashMap) {

        ScalingConfig scalingConfig = configuration.getMdsScalingConfig();
        HashMap<Integer, Double> clusterIDSizeMap = new HashMap<>();
        Double total_value = 0.0;
        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesHashMap.entrySet()) {
            double val = (double)entry.getValue().size();
            clusterIDSizeMap.put(entry.getKey(), val);
            total_value += val;
        }

        Double xDiff = scalingConfig.getXmax() - scalingConfig.getXmin();
        Double yDiff = scalingConfig.getYmax() - scalingConfig.getYmin();

        HashMap<Integer, ScalingConfig> clusterScalingConfig = new HashMap<>();

        Double startX = scalingConfig.getXmin() + 20.0;
        Double startY = scalingConfig.getYmin() + 20.0;

        for(Map.Entry<Integer, Double> entry : clusterIDSizeMap.entrySet()) {
            Double xMin = startX + 70.0;
            Double stepX = (entry.getValue() / total_value)* xDiff - 50;
            Double xMax = startX + stepX;
            Double yMin = startY + 40.0;
            Double stepY = (entry.getValue() / total_value)* yDiff;
            Double yMax = startY + stepY;

            startX += stepX;
            startY += stepY;

            ScalingConfig scalConfig = new ScalingConfig();
            scalConfig.setXmin(xMin);
            scalConfig.setXmax(xMax);


            if(entry.getValue() / total_value < 0.2) {

                scalConfig.setYmin(scalingConfig.getYmin() + 170);
                scalConfig.setYmax(scalingConfig.getYmax() - 170);

            } else if(entry.getValue() / total_value >= 0.2 && entry.getValue() / total_value <0.5 ){

                scalConfig.setYmin(scalingConfig.getYmin() + 100);
                scalConfig.setYmax(scalingConfig.getYmax() - 100);

            } else {

                scalConfig.setYmin(scalingConfig.getYmin() + 20);
                scalConfig.setYmax(scalingConfig.getYmax() - 20);
            }
            clusterScalingConfig.put(entry.getKey(), scalConfig);
        }
        return clusterScalingConfig;
    }

    public HashMap<String, String> getIdUserMap() {

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

        return idUserMap;

    }

    public HashMap<String, String> getColorMap(Object colorMap) {

        HashMap<String, String> userColorMap = new HashMap<>();

        String colorData = colorMap.toString();
        JSONArray jsonArr = new JSONArray(colorData);


        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject object = jsonArr.getJSONObject(i);
            String userName = object.get("userName").toString();
            String color = object.get("color").toString();
            userColorMap.put(userName, color);
        }

        return userColorMap;
    }

    public HashMap<Integer, List<IdCoordinates>> getCoordinates(int cluster_size, List<FeatureVector> featurevectors, Double descWeight, Double attrWeight, Double chartEncodingWeight, Object colorMap) {

        HashMap<String, String> userColorMap = getColorMap(colorMap);
        HashMap<String, String> idUserMap = getIdUserMap();

        List<IdCoordinates> coordinatesList = calculateCoordinates(featurevectors, descWeight, attrWeight, chartEncodingWeight);

        List<IdCoordinates> clusteredCoordinates = clustering.getClusteredCoordinates(cluster_size, 20, coordinatesList);

//        coordinatesScalingUtil.setCoordinatesList(clusteredCoordinates);
//
//        clusteredCoordinates = coordinatesScalingUtil.getScaledCoordinates(configuration.getMdsScalingConfig().getXmin(),
//                configuration.getMdsScalingConfig().getXmax(), configuration.getMdsScalingConfig().getYmin(), configuration.getMdsScalingConfig().getYmax());


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

        HashMap<Integer, List<IdCoordinates>> newCoordinatesHashMap = new HashMap<>();
        HashMap<Integer, ScalingConfig> clusterScalingInfo = distributeSVG(coordinatesHashMap);
        System.out.println("cluster scaling info "+clusterScalingInfo);

        int clusterSize = coordinatesHashMap.keySet().size();
//        ScalingConfig scalingConfig = configuration.getMdsScalingConfig();
//        Double startX = scalingConfig.getXmin();
//        Double stepX = (scalingConfig.getXmax() - scalingConfig.getXmin()) / clusterSize;
//        Double startY = scalingConfig.getYmin();
//        Double stepY = (scalingConfig.getYmax() - scalingConfig.getYmin()) / clusterSize;
        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesHashMap.entrySet()) {

            coordinatesScalingUtil.setCoordinatesList(entry.getValue());
//            Double xMin = startX + 5;
//            Double xMax = startX + stepX - 5;
//            Double yMin = startY + 5;
//            Double yMax = startY + stepY - 5;
//
//            startX += stepX;
//            startY += stepY;

            List<IdCoordinates> newIdCoordinatesList = coordinatesScalingUtil
                    .getScaledCoordinates(
                            clusterScalingInfo.get(entry.getKey()).getXmin(),
                            clusterScalingInfo.get(entry.getKey()).getXmax(),
                            clusterScalingInfo.get(entry.getKey()).getYmin(),
                            clusterScalingInfo.get(entry.getKey()).getYmax()
                    );

            newCoordinatesHashMap.put(entry.getKey(), newIdCoordinatesList);
        }

        return newCoordinatesHashMap;

    }

}
