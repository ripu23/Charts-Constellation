package chartconstellation.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void distributeSVG(HashMap<Integer, List<IdCoordinates>> coordinatesHashMap) {

        ScalingConfig scalingConfig = configuration.getMdsScalingConfig();
        for(Map.Entry<Integer, List<IdCoordinates>> entry : coordinatesHashMap.entrySet()) {

        }

    }

}
