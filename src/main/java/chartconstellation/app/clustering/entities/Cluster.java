package chartconstellation.app.clustering.entities;

import chartconstellation.app.entities.response.Point;

import java.util.ArrayList;
import java.util.List;

public class Cluster {

    private int id = 0;
    private List<Point> points = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public Point getMeanPoint() {
        int size = points.size();
        Double x = 0.0, y = 0.0;
        for(Point point : points) {
            x += point.getX();
            y += point.getY();
        }

        x = x/size;
        y = y/size;

        return new Point(x, y);
    }
}
