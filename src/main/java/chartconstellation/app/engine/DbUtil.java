package chartconstellation.app.engine;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.FeatureDistance;
import chartconstellation.app.entities.FeatureVector;
import chartconstellation.app.entities.MongoCollections;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.util.JSON;

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
            System.out.println(feature.toString());
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
}
