package chartconstellation.app.entities.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class OutputResponse {

    private Collection<List<IdCoordinates>> coordinatesList;

    private List<Cluster> clusters;

    private HashMap<String, Integer> attributesMap;

    public Collection<List<IdCoordinates>> getCoordinatesList() {
        return coordinatesList;
    }

    public void setCoordinatesList(Collection<List<IdCoordinates>> coordinatesList) {
        this.coordinatesList = coordinatesList;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    public HashMap<String, Integer> getAttributesMap() {
        return attributesMap;
    }

    public void setAttributesMap(HashMap<String, Integer> attributesMap) {
        this.attributesMap = attributesMap;
    }

    @Override
    public String toString() {
        return "OutputResponse{" +
                "coordinatesList=" + coordinatesList +
                ", clusters=" + clusters +
                ", attributesMap=" + attributesMap +
                '}';
    }
}
