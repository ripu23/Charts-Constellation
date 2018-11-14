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

    public List<IdCoordinates>  calculateCoordinates(Double descWeight, Double attrWeight, Double chartEncodingWeight) {

        List<FeatureVector> featurevectors = dbUtil.getFeaturesFromCollection(configuration.getMongoDatabase(),
                configuration.getTotalFeatureCollection());



        int size = featurevectors.size();
        double[][] input = new double[size][size];


        for(FeatureVector featureVector : featurevectors) {
            int index_i = Integer.parseInt(featureVector.getId());

            for(IdFeatures idFeature : featureVector.getFeatures()) {
                int index_j = Integer.parseInt(idFeature.getId());
                double score = weightedScore(idFeature, descWeight, attrWeight, chartEncodingWeight);

                input[index_i-1][index_j-1] = score;
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
            idCoordinate.setId(String.valueOf(i+1));
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
