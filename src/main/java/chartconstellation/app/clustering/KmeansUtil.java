package chartconstellation.app.clustering;

import chartconstellation.app.entities.response.Point;
import org.springframework.stereotype.Component;

@Component
public class KmeansUtil {

    public Double getEuclideanDistance(Point a, Point b) {

        return Math.pow(a.getX() - b.getX(), 2) +
                Math.pow(a.getY() - b.getY(), 2);
    }

}
