package chartconstellation.app.entities.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class OutputResponse {

    private String filterMap;

    private Collection<List<IdCoordinates>> coordinatesList;

    private List<Cluster> clusters;

    private DataCoverageResponse dataCoverage;

    public Collection<List<IdCoordinates>> getCoordinatesList()
    {
        return coordinatesList;
    }

    public String getFilterMap() {
        return filterMap;
    }

    public void setFilterMap(String filterMap) {
        this.filterMap = filterMap;
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

    public DataCoverageResponse getDataCoverage() {
        return dataCoverage;
    }

    public void setDataCoverage(DataCoverageResponse dataCoverage) {
        this.dataCoverage = dataCoverage;
    }

    @Override
    public String toString() {
        return "OutputResponse{" +
                "filterMap='" + filterMap + '\'' +
                ", coordinatesList=" + coordinatesList +
                ", clusters=" + clusters +
                ", dataCoverage=" + dataCoverage +
                '}';
    }
}
