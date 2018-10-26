package chartconstellation.app.util;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.FeatureVector;
import chartconstellation.app.entities.IdFeatures;
import chartconstellation.app.entities.response.IdCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CoordinatesUtil {

    @Autowired
    Configuration configuration;

    @Autowired
    DbUtil dbUtil;

    private Double weightedScore(IdFeatures idFeatures, Double descWeight, Double attrWeight, Double chartEncodingWeight) {

        Double value = 0.0;
        value += idFeatures.getAttributeDistance() * attrWeight;
        value += idFeatures.getChartEncodingDistance() * chartEncodingWeight;
        value += idFeatures.getDescriptionDistance() * descWeight;
        return value;
    }

    public void calculateCoordinates(Double descWeight, Double attrWeight, Double chartEncodingWeight) {

        List<FeatureVector> featurevectors = dbUtil.getDocsFromCollection(configuration.getMongoDatabase(),
                configuration.getTotalFeatureCollection());

        List<IdCoordinates> coordinates = new ArrayList<>();
        for(FeatureVector featureVector : featurevectors) {
            for(IdFeatures idFeatures : featureVector.getFeatures()) {
                weightedScore(idFeatures, descWeight, attrWeight, chartEncodingWeight);
            }

        }

    }

}
