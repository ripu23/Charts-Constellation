package chartconstellation.app.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import org.json.JSONObject;
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

    @Autowired
    AttributeUtil attributeUtil;

//    public void updateDBDocs(List<DBObject> dobobjects) {
//
//        mongoClient.getDatabase(configuration.getMongoDatabase())
//                .getCollection(configuration.getOlympicchartcollection()).drop();
//
//        mongoClient.getDB(configuration.getMongoDatabase())
//                .getCollection(configuration.getOlympicchartcollection())
//                .insert(dobobjects);
//    }

    public void updateDBDocs(List<DBObject> dobobjects, String database, String collection) {

        mongoClient.getDatabase(database)
                .getCollection(collection).drop();

        mongoClient.getDB(database)
                .getCollection(collection)
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

    public MongoCollections getCollections(String database, String desccollection, String attrcollection) {

        MongoCollections mongoCollections = new MongoCollections();

        mongoCollections.setDescriptionCollection(mongoClient.getDB(database)
                .getCollection(desccollection));

        mongoCollections.setAttributeCollection(mongoClient.getDB(database)
                .getCollection(attrcollection));

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
            JSONObject jsonObj = new JSONObject(obj.toString());
            List<String> attrSet = attributeUtil.getAttributesOfaObject(jsonObj);
            Chart chart = gson.fromJson(obj.toString(), Chart.class);
            chart.setAttributes(attrSet);
            charts.add(chart);
        }
        return charts;
    }

    public List<Chart> searchDocsInaCollection(String database, String collection, List<String> users, List<String> charts) {
        BasicDBObject inQuery = new BasicDBObject();
        DBCursor dbCursor = null;
        if(charts != null) {
            inQuery.put("chartType", new BasicDBObject("$in", charts));
        }
        if(users != null) {
            inQuery.put("user", new BasicDBObject("$in", users));
        }

        if(inQuery.isEmpty()) {
            dbCursor = mongoClient.getDB(database).getCollection(collection).find();
        } else {
            dbCursor = mongoClient.getDB(database).getCollection(collection).find(inQuery);
        }

        Gson gson = new Gson();
        List<Chart> outputCharts = new ArrayList<>();
        while (dbCursor.hasNext()) {
            DBObject obj = dbCursor.next();
            JSONObject jsonObj = new JSONObject(obj.toString());
            List<String> attrSet = attributeUtil.getAttributesOfaObject(jsonObj);
            Chart chart = gson.fromJson(obj.toString(), Chart.class);
            chart.setAttributes(attrSet);
            outputCharts.add(chart);
        }
        return outputCharts;
    }
}
