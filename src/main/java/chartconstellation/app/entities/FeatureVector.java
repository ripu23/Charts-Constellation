package chartconstellation.app.entities;

import java.util.List;

public class FeatureVector {

    private String id;
    private List<IdFeatures> features;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IdFeatures> getFeatures() {
        return features;
    }

    public void setFeatures(List<IdFeatures> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "FeatureVector{" +
                "id='" + id + '\'' +
                ", features=" + features +
                '}';
    }
}
