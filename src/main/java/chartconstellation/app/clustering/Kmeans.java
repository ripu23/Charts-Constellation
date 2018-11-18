package chartconstellation.app.clustering;

import java.util.*;

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

    public List<Point> intitializeCentroids(int k, List<IdCoordinates> idCoordinates) {

        int size = idCoordinates.size();

        List<Point> points = new ArrayList<>();

        Collections.shuffle(idCoordinates);

        for(int i=0; i<k; i++) {
            Random r = new Random();
            //int index = r.nextInt((size - 1) + 1);
            int index = i;
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
                if(dist <= min) {
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
//
//        TreeMap<Integer, Cluster> sorted = new TreeMap<>();
//        sorted.putAll(clusterMap);

        for(Map.Entry<Integer, Cluster> cluster : clusterMap.entrySet()) {
            //System.out.print(cluster.getKey()+" ");
            Point point = cluster.getValue().getMeanPoint();
            newCentroids.add(point);
        }
        //System.out.println();

        return newCentroids;
    }

    public List<IdCoordinates>  getClusteredPoints(int k, List<IdCoordinates> idCoordinates) {

        List<IdCoordinates> modifiedCoordinates = new ArrayList<>();
        List<Point> centroids = intitializeCentroids(k , idCoordinates);
        System.out.println(centroids);

        for(int i=0 ; i<clusterParams.getIterations(); i++) {
            modifiedCoordinates = expectation(idCoordinates, centroids);
            centroids = maximisation(modifiedCoordinates);
        }
        System.out.println("Cluster size = "+ k + " MSE = "+ MSE(centroids, modifiedCoordinates));
        return modifiedCoordinates;
    }

    public List<IdCoordinates> runKmeansCLustering(List<IdCoordinates> idCoordinates) {

        int pointsSize = idCoordinates.size();

        if(pointsSize <= 3) {
            int k = 1;
            return getClusteredPoints(k, idCoordinates);
        } else {

            int k = (int) Math.sqrt((double)pointsSize/2) + 1;
            return getClusteredPoints(k, idCoordinates);
        }
    }

    private Double MSE(List<Point> centroids, List<IdCoordinates> idCoordinates) {

        Double mse = 0.0;
        int size = idCoordinates.size();

        for(IdCoordinates idCoordinate : idCoordinates) {
            int id = idCoordinate.getClusterId();
            Point centroid = centroids.get(id-1);
            mse += kmeansUtil.getEuclideanDistance(centroid, idCoordinate.getPoint());
        }
        return mse/size;
    }
}
