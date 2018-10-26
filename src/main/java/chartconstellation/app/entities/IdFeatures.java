package chartconstellation.app.entities;

public class IdFeatures {

    private String Id;
    private Double descriptionDistance;
    private Double attributeDistance;
    private Double chartEncodingDistance;

    public IdFeatures(String id, Double descriptionDistance, Double attributeDistance, Double chartEncodingDistance) {
        Id = id;
        this.descriptionDistance = descriptionDistance;
        this.attributeDistance = attributeDistance;
        this.chartEncodingDistance = chartEncodingDistance;
    }

    public IdFeatures(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Double getDescriptionDistance() {
        return descriptionDistance;
    }

    public void setDescriptionDistance(Double descriptionDistance) {
        this.descriptionDistance = descriptionDistance;
    }

    public Double getAttributeDistance() {
        return attributeDistance;
    }

    public void setAttributeDistance(Double attributeDistance) {
        this.attributeDistance = attributeDistance;
    }

    public Double getChartEncodingDistance() {
        return chartEncodingDistance;
    }

    public void setChartEncodingDistance(Double chartEncodingDistance) {
        this.chartEncodingDistance = chartEncodingDistance;
    }

    @Override
    public String toString() {
        return "IdFeatures{" +
                "Id='" + Id + '\'' +
                ", descriptionDistance=" + descriptionDistance +
                ", attributeDistance=" + attributeDistance +
                ", chartEncodingDistance=" + chartEncodingDistance +
                '}';
    }
}
