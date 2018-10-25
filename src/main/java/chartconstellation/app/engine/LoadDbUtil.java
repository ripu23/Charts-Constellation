package chartconstellation.app.engine;

import chartconstellation.app.AppConfiguration.Configuration;
import chartconstellation.app.entities.FeatureDistance;
import com.google.gson.Gson;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.util.JSON;

@Component
public class LoadDbUtil {

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
}
