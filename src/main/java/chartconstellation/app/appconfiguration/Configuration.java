package chartconstellation.app.appconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private String totalFeatureCollection;
    private String descriptionDistancePath;
    private String dataset2inputPath;
    private String dataset2mongoDatabase;
    private String crimechartcollection;
    private String dataset2descriptionCollection;
    private String dataset2attributeDistanceCollection;
    private String dataset2totalFeatureCollection;
    private String dataset2descriptionDistancePath;
    private WeightConfig featureWeights;
    private ClusterParams clusterParams;
    private ScalingConfig mdsScalingConfig;

    public String getDataset2mongoDatabase() {
        return dataset2mongoDatabase;
    }

    public void setDataset2mongoDatabase(String dataset2mongoDatabase) {
        this.dataset2mongoDatabase = dataset2mongoDatabase;
    }

    public ClusterParams getClusterParams() {
        return clusterParams;
    }

    public void setClusterParams(ClusterParams clusterParams) {
        this.clusterParams = clusterParams;
    }

    public ScalingConfig getMdsScalingConfig() {
        return mdsScalingConfig;
    }

    public void setMdsScalingConfig(ScalingConfig mdsScalingConfig) {
        this.mdsScalingConfig = mdsScalingConfig;
    }

    public String getTotalFeatureCollection() {
        return totalFeatureCollection;
    }

    public void setTotalFeatureCollection(String totalFeatureCollection) {
        this.totalFeatureCollection = totalFeatureCollection;
    }

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

    public String getCrimechartcollection() {
        return crimechartcollection;
    }

    public void setCrimechartcollection(String crimechartcollection) {
        this.crimechartcollection = crimechartcollection;
    }

    public String getDataset2descriptionCollection() {
        return dataset2descriptionCollection;
    }

    public void setDataset2descriptionCollection(String dataset2descriptionCollection) {
        this.dataset2descriptionCollection = dataset2descriptionCollection;
    }

    public String getDataset2attributeDistanceCollection() {
        return dataset2attributeDistanceCollection;
    }

    public void setDataset2attributeDistanceCollection(String dataset2attributeDistanceCollection) {
        this.dataset2attributeDistanceCollection = dataset2attributeDistanceCollection;
    }

    public String getDataset2totalFeatureCollection() {
        return dataset2totalFeatureCollection;
    }

    public void setDataset2totalFeatureCollection(String dataset2totalFeatureCollection) {
        this.dataset2totalFeatureCollection = dataset2totalFeatureCollection;
    }

    public String getDataset2descriptionDistancePath() {
        return dataset2descriptionDistancePath;
    }

    public void setDataset2descriptionDistancePath(String dataset2descriptionDistancePath) {
        this.dataset2descriptionDistancePath = dataset2descriptionDistancePath;
    }

    public String getDataset2inputPath() {
        return dataset2inputPath;
    }

    public void setDataset2inputPath(String dataset2inputPath) {
        this.dataset2inputPath = dataset2inputPath;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "updateData=" + updateData +
                ", inputPath='" + inputPath + '\'' +
                ", mongoDatabase='" + mongoDatabase + '\'' +
                ", olympicchartcollection='" + olympicchartcollection + '\'' +
                ", descriptionCollection='" + descriptionCollection + '\'' +
                ", attributeDistanceCollection='" + attributeDistanceCollection + '\'' +
                ", totalFeatureCollection='" + totalFeatureCollection + '\'' +
                ", descriptionDistancePath='" + descriptionDistancePath + '\'' +
                ", dataset2inputPath='" + dataset2inputPath + '\'' +
                ", dataset2mongoDatabase='" + dataset2mongoDatabase + '\'' +
                ", crimechartcollection='" + crimechartcollection + '\'' +
                ", dataset2descriptionCollection='" + dataset2descriptionCollection + '\'' +
                ", dataset2attributeDistanceCollection='" + dataset2attributeDistanceCollection + '\'' +
                ", dataset2totalFeatureCollection='" + dataset2totalFeatureCollection + '\'' +
                ", dataset2descriptionDistancePath='" + dataset2descriptionDistancePath + '\'' +
                ", featureWeights=" + featureWeights +
                ", clusterParams=" + clusterParams +
                ", mdsScalingConfig=" + mdsScalingConfig +
                '}';
    }
}
