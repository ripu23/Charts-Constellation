package chartconstellation.app.clustering;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chartconstellation.app.entities.response.IdCoordinates;

@Component
public class Clustering {

    @Autowired
    ClusterParams clusterParams;

    @Autowired
    Kmeans kmeans;

    public List<IdCoordinates>  getClusteredCoordinates(int k, int itr, List<IdCoordinates> idCoordinates) {

        clusterParams.setClusterSize(k);
        clusterParams.setIterations(itr);

        List<IdCoordinates> newIdcoordinates = kmeans.runKmeansCLustering(idCoordinates);

        return newIdcoordinates;
    }
}
