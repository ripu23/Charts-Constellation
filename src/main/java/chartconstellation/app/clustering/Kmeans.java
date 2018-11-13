package chartconstellation.app.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import chartconstellation.app.clustering.entities.Cluster;
import chartconstellation.app.entities.response.IdCoordinates;
import chartconstellation.app.entities.response.Point;

@Component
public class Kmeans {

    @Autowired
    KmeansUtil kmeansUtil;

    @Autowired
    ClusterParams clusterParams;

    private ClusterParams params;

    public List<Point> intitializeCentroids(List<IdCoordinates> idCoordinates) {

        int size = idCoordinates.size();

        List<Point> points = new ArrayList<>();

        for(int i=0; i<clusterParams.getClusterSize(); i++) {
            Random r = new Random();
            int index = r.nextInt((size - 1) + 1);
            points.add(new Point(idCoordinates.get(index).getPoint().getX(), idCoordinates.get(index).getPoint().getY()));
        }

        return points;
    }



    public List<IdCoordinates> expectation(List<IdCoordinates> idCoordinates, List<Point> centroids) {

        List<IdCoordinates> newCoordinates = new ArrayList<>();

        for(IdCoordinates idCoordinate : idCoordinates) {
            Double min = Double.MAX_VALUE;
            int id = 0;
            for(int i=1; i<=centroids.size(); i++) {
                double dist = kmeansUtil.getEuclideanDistance(centroids.get(i-1), idCoordinate.getPoint());
                if(dist < min) {
                    min = dist;
                    id = i;
                }
            }
            idCoordinate.setClusterId(id);
            newCoordinates.add(idCoordinate);
        }
        return newCoordinates;
    }

    public List<Point> maximisation(List<IdCoordinates> idCoordinates) {

        HashMap<Integer, Cluster> clusterMap = new HashMap<>();

        for(IdCoordinates idCoordinate : idCoordinates) {

            if(clusterMap.containsKey(idCoordinate.getClusterId())) {

                Cluster existingCluster = clusterMap.get(idCoordinate.getClusterId());
                List<Point> existingPoints = existingCluster.getPoints();
                existingPoints.add(idCoordinate.getPoint());
                existingCluster.setPoints(existingPoints);
                clusterMap.put(idCoordinate.getClusterId(), existingCluster);

            } else {
                Cluster cluster = new Cluster();
                cluster.setId(idCoordinate.getClusterId());
                List<Point> points = new ArrayList<>();
                points.add(idCoordinate.getPoint());
                cluster.setPoints(points);
                clusterMap.put(idCoordinate.getClusterId(), cluster);
            }
        }

        List<Point> newCentroids = new ArrayList<>();

        for(Map.Entry<Integer, Cluster> cluster : clusterMap.entrySet()) {
            Point point = cluster.getValue().getMeanPoint();
            newCentroids.add(point);
        }

        return newCentroids;
    }

    public List<IdCoordinates> runKmeansCLustering(List<IdCoordinates> idCoordinates) {

        List<Point> centroids = intitializeCentroids(idCoordinates);
        List<IdCoordinates> modifiedCoordinates = new ArrayList<>();
        for(int i=0 ; i<clusterParams.getIterations(); i++) {
            modifiedCoordinates = expectation(idCoordinates, centroids);
            centroids = maximisation(modifiedCoordinates);
        }

        return modifiedCoordinates;
    }
}
