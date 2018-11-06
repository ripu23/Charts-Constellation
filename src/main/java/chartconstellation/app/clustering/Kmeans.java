package chartconstellation.app.clustering;

import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.entities.response.Point;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Kmeans {

    private ClusterParams params;

    public List<Point> intitializeCentroids(int size) {

        List<Point> points = new ArrayList<>();

        for(int i=0; i<size; i++) {
            double random_X = Math.random() * 0.5 + 1;
            double random_Y = Math.random() * 0.5 + 1;
            points.add(new Point(random_X, random_Y));
        }

        return points;
    }



    public void expectation(List<IdCoordinates> idCoordinates, List<Point> centroids) {

        for(IdCoordinates idCoordinate : idCoordinates) {

        }

    }

    public void maximisation() {

    }

    public void runKmeansCLustering() {

    }
}
