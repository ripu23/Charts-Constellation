package chartconstellation.app.entities.response;

import java.util.HashMap;

public class Cluster {

    private String clusteId;

    private HashMap<String, Integer> attributeMap;

    public String getClusteId() {
        return clusteId;
    }

    public void setClusteId(String clusteId) {
        this.clusteId = clusteId;
    }

    public HashMap<String, Integer> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(HashMap<String, Integer> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
