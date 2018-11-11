package chartconstellation.app.clustering;

import org.springframework.stereotype.Component;

@Component
public class ClusterParams {

    // Default clusters 3
    private int clusterSize = 3;
    private int iterations = 10;


    public int getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
