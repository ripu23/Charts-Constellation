package chartconstellation.app.entities.response;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DataCoverageResponse {

    private HashMap<String, Integer> attributesMap;

    private Set<String> attributeKeys;

    private HashMap<String, List<String>> attributeOccurenceMap;

    public Set<String> getAttributeKeys() {
        return attributeKeys;
    }

    public void setAttributeKeys(Set<String> attributeKeys) {
        this.attributeKeys = attributeKeys;
    }

    public HashMap<String, List<String>> getAttributeOccurenceMap() {
        return attributeOccurenceMap;
    }

    public void setAttributeOccurenceMap(HashMap<String, List<String>> attributeOccurenceMap) {
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
                '}';
    }
}
