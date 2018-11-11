package chartconstellation.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.Chart;
import chartconstellation.app.entities.FeatureDistance;
import chartconstellation.app.entities.FeatureVector;
import chartconstellation.app.entities.MongoCollections;

@Component
public class DbUtil {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    Configuration configuration;

    public void updateDBDocs(List<DBObject> dobobjects) {

        mongoClient.getDatabase(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection()).drop();

        mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection())
                .insert(dobobjects);
    }

    public void updateAttributeCollection(String database, String collection, List<FeatureDistance> attrDistances) {

        mongoClient.getDatabase(database)
                .getCollection(collection)
                .drop();

        List<DBObject> dbObjects = new ArrayList<>();

        for(FeatureDistance attributeDistance : attrDistances) {
            Gson gson=new Gson();
            dbObjects.add((DBObject) JSON.parse(gson.toJson(attributeDistance)));
        }

        mongoClient.getDB(database)
                .getCollection(collection)
                .insert(dbObjects);
    }

    public void updateFeaturesCollection(String database, String collection, List<FeatureVector> features) {

        mongoClient.getDatabase(database)
                .getCollection(collection)
                .drop();

        List<DBObject> dbObjects = new ArrayList<>();

        for(FeatureVector feature : features) {
            Gson gson=new Gson();
            dbObjects.add((DBObject) JSON.parse(gson.toJson(feature)));
        }

        mongoClient.getDB(database)
                .getCollection(collection)
                .insert(dbObjects);
    }

    public MongoCollections getCollections(String database) {

        MongoCollections mongoCollections = new MongoCollections();

        mongoCollections.setDescriptionCollection(mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getDescriptionCollection()));

        mongoCollections.setAttributeCollection(mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getAttributeDistanceCollection()));

        return mongoCollections;
    }

    public List<FeatureVector>  getFeaturesFromCollection(String database, String collection) {

        DBCursor cursor = mongoClient.getDB(database).getCollection(collection).find();
        List<FeatureVector> featureVectors = new ArrayList<>();
        Gson gson = new Gson();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            FeatureVector featureVector = gson.fromJson(obj.toString(), FeatureVector.class);
            featureVectors.add(featureVector);
        }
        return featureVectors;
    }

    public List<Chart> getAttributesFromCollection(String database, String collection) {

        DBCursor cursor = mongoClient.getDB(database).getCollection(collection).find();
        Gson gson = new Gson();
        List<Chart> charts = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            Chart chart = gson.fromJson(obj.toString(), Chart.class);
            charts.add(chart);
        }
        return charts;
    }
}
