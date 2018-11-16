package chartconstellation.app.entities.response;

import java.util.Collection;
import java.util.List;

public class OutputResponse {

    private Collection<List<IdCoordinates>> coordinatesList;

    private List<Cluster> clusters;

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

    @Override
    public String toString() {
        return "OutputResponse{" +
                "clusters=" + clusters +
                '}';
    }
}
