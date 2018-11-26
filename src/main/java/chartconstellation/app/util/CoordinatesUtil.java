package chartconstellation.app.util;

import java.util.*;

import chartconstellation.app.clustering.Clustering;
import chartconstellation.app.entities.Chart;
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

    public List<IdCoordinates> calculateCoordinates(List<FeatureVector> featurevectors, Double descWeight, Double attrWeight, Double chartEncodingWeight) {

        int size = featurevectors.size();
        System.out.println("size "+size);
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

//        System.out.println("input = ");
//
//        for(int i=0; i< input.length; i++) {
//            for(int j=0; j < input[0].length; j++) {
//                System.out.print(input[i][j]+" ");
//            }
//            System.out.println();
//        }
//
        double output[][] = mdsUtil.classicalScaling(input);
        List<IdCoordinates> coordinates = new ArrayList<>();
//
//        System.out.println("output = ");

//        for(int i=0; i< output[0].length; i++) {
//            for(int j=0; j < output.length; j++) {
//                System.out.print(output[j][i]+" ");
//            }
//            System.out.println();
//        }



        for(int i=0 ;i< output[0].length; i++) {
            IdCoordinates idCoordinate = new IdCoordinates();
            idCoordinate.setId(keyIdMap.get(i));
            if(Double.isNaN(output[1][i])) {
                idCoordinate.setPoint(new Point(output[0][i], output[0][i]));
            } else {
                idCoordinate.setPoint(new Point(output[0][i], output[1][i]));
            }
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

    public HashMap<String, String> getIdUserMap(String datasetId) {

        HashMap<String, UserCharts> map = new HashMap<>();
        if(datasetId.equals("olympics")) {
            map = chartsUtil
                    .getAllUserCharts(configuration.getMongoDatabase()
                            , configuration.getOlympicchartcollection());
        } else {
            map = chartsUtil
                    .getAllUserCharts(configuration.getDataset2mongoDatabase()
                            , configuration.getCrimechartcollection());
        }

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

    public HashMap<String,String> getChartTypeMap(List<Chart> charts) {

        HashMap<String, String> chartTyoeMap = new HashMap<>();

        for(Chart chart : charts) {
            chartTyoeMap.put(chart.getId(), chart.getChartType());
        }

        return chartTyoeMap;
    }

    public HashMap<String, Chart> getChartsMap(List<Chart> charts) {
        HashMap<String, Chart> chartsMap = new HashMap<>();

        for(Chart chart : charts) {
            chartsMap.put(chart.getId(), chart);
        }
        return chartsMap;
    }

    public HashMap<Integer, List<IdCoordinates>> getCoordinates(String datasetId, List<Chart> charts, int cluster_size, List<FeatureVector> featurevectors, Double descWeight, Double attrWeight, Double chartEncodingWeight, Object colorMap) {

        HashMap<String, String> userColorMap = getColorMap(colorMap);
        System.out.println(userColorMap);
        HashMap<String, String> idUserMap = getIdUserMap(datasetId);
        System.out.println(idUserMap);
        HashMap<String, String> idChartTypeMap = getChartTypeMap(charts);
        HashMap<String, Chart> chartsMap = getChartsMap(charts);


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
            idCoordinate.setChartType(idChartTypeMap.get(idCoordinate.getId()));
            idCoordinate.setChartName(chartsMap.get(idCoordinate.getId()).getChartName());
            idCoordinate.setTitle(chartsMap.get(idCoordinate.getId()).getTitle());
            idCoordinate.setDescription(chartsMap.get(idCoordinate.getId()).getDescription());
//            idCoordinate.setDate(chartsMap.get(idCoordinate.getId()).getDateTime().toString());
            idCoordinate.setAttributes(chartsMap.get(idCoordinate.getId()).getAttributes());

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

        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesHashMap.entrySet()) {

            coordinatesScalingUtil.setCoordinatesList(entry.getValue());

            List<IdCoordinates> newIdCoordinatesList = coordinatesScalingUtil
                    .getScaledCoordinates(
                            clusterScalingInfo.get(entry.getKey()).getXmin(),
                            clusterScalingInfo.get(entry.getKey()).getXmax(),
                            clusterScalingInfo.get(entry.getKey()).getYmin(),
                            clusterScalingInfo.get(entry.getKey()).getYmax()
                    );

            for(IdCoordinates idCoordinate : newIdCoordinatesList) {
                Point point = idCoordinate.getPoint();
                ScalingConfig scalingConfig = clusterScalingInfo.get(entry.getKey());
                if(Double.isNaN(point.getX()) && Double.isNaN(point.getY())) {

                    idCoordinate.setPoint(getCoordinates(scalingConfig.getXmin(), scalingConfig.getXmax(),
                            scalingConfig.getYmin(), scalingConfig.getYmax()));
                } else if(Double.isNaN(point.getX())) {
                    Double val = getCoordinates(scalingConfig.getXmin(), scalingConfig.getXmax());
                    idCoordinate.setPoint(new Point(val, idCoordinate.getPoint().getY()));
                } else {
                    Double val = getCoordinates(scalingConfig.getYmin(), scalingConfig.getYmax());
                    idCoordinate.setPoint(new Point(idCoordinate.getPoint().getX(), val));
                }
            }
            newCoordinatesHashMap.put(entry.getKey(), newIdCoordinatesList);
        }

        return newCoordinatesHashMap;

    }

    public Point getCoordinates(Double xmin, Double xmx, Double ymin, Double ymax) {
        Random r = new Random();
        double xValue = xmin + (xmx - xmin) * r.nextDouble();
        double yValue = ymin + (ymax - ymin) * r.nextDouble();
        return new Point(xValue, yValue);
    }

    public Double getCoordinates(Double min, Double max) {
        Random r = new Random();
        double value = min + (max - min) * r.nextDouble();
        return value;
    }

}
