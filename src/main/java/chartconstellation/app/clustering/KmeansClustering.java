package chartconstellation.app.clustering;

import chartconstellation.app.entities.response.Point;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KmeansClustering {

    // Default clusters 3
    private int clusterSize = 3;
    private int iterations = 10;
    private List<Point> points;

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

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
