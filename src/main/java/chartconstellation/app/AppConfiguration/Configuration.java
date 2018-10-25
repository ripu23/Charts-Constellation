package chartconstellation.app.AppConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@JsonIgnoreProperties
public class Configuration {

    private boolean updateData;
    private String inputPath;
    private String mongoDatabase;
    private String olympicchartcollection;
    private String descriptionCollection;
    private String attributeDistanceCollection;
    private String descriptionDistancePath;
    private WeightConfig featureWeights;

    public boolean isUpdateData() {
        return updateData;
    }

    public void setUpdateData(boolean updateData) {
        this.updateData = updateData;
    }

    public WeightConfig getFeatureWeights() {
        return featureWeights;
    }

    public void setFeatureWeights(WeightConfig featureWeights) {
        this.featureWeights = featureWeights;
    }

    public String getDescriptionCollection() {
        return descriptionCollection;
    }

    public void setDescriptionCollection(String descriptionCollection) {
        this.descriptionCollection = descriptionCollection;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(String mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public String getOlympicchartcollection() {
        return olympicchartcollection;
    }

    public void setOlympicchartcollection(String olympicchartcollection) {
        this.olympicchartcollection = olympicchartcollection;
    }

    public String getAttributeDistanceCollection() {
        return attributeDistanceCollection;
    }

    public void setAttributeDistanceCollection(String attributeDistanceCollection) {
        this.attributeDistanceCollection = attributeDistanceCollection;
    }

    public String getDescriptionDistancePath() {
        return descriptionDistancePath;
    }

    public void setDescriptionDistancePath(String descriptionDistancePath) {
        this.descriptionDistancePath = descriptionDistancePath;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "inputPath='" + inputPath + '\'' +
                ", mongoDatabase='" + mongoDatabase + '\'' +
                ", olympicchartcollection='" + olympicchartcollection + '\'' +
                ", descriptionCollection='" + descriptionCollection + '\'' +
                ", attributeDistanceCollection='" + attributeDistanceCollection + '\'' +
                ", descriptionDistancePath='" + descriptionDistancePath + '\'' +
                ", featureWeights=" + featureWeights +
                '}';
    }
}
