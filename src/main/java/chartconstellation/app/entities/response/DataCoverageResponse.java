package chartconstellation.app.entities.response;

import java.util.HashMap;
import java.util.Set;

public class DataCoverageResponse {

    private HashMap<String, Integer> attributesMap;

    private HashMap<String, Set<String>> attributeOccurenceMap;

    public HashMap<String, Set<String>> getAttributeOccurenceMap() {
        return attributeOccurenceMap;
    }

    public void setAttributeOccurenceMap(HashMap<String, Set<String>> attributeOccurenceMap) {
        this.attributeOccurenceMap = attributeOccurenceMap;
    }

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
                ", attributeOccurenceMap=" + attributeOccurenceMap +
                '}';
    }
}
