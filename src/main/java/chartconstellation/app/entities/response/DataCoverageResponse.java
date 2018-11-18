package chartconstellation.app.entities.response;

import java.util.HashMap;

public class DataCoverageResponse {

    private HashMap<String, Integer> attributesMap;

    public HashMap<String, Integer> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(HashMap<String, Integer> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public String toString() {
        return "DataCoverageResponse{" +
                "attributesMap=" + attributesMap +
                '}';
    }
}
