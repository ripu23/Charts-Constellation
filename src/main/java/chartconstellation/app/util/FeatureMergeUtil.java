package chartconstellation.app.util;

import chartconstellation.app.entities.*;
import com.google.gson.Gson;
import com.mongodb.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FeatureMergeUtil {

    @Autowired
    MongoClient mongoClient;


    public DBObject findDocumentById(String id, DBCollection collection) {

        BasicDBObject query = new BasicDBObject();
        query.put("id", id);

        DBObject dbObj = collection.findOne(query);
        return dbObj;
    }


    public List<DBObject> getDbDocs(DBCollection collection) {

        DBCursor cursor = collection.find();
        List<DBObject> docs = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            docs.add(obj);
        }

        return docs;
    }

    public IdValue convertToIDvalue(BasicDBObject dbObject) {
        IdValue idValue = new IdValue();
        idValue.setId((String) dbObject.get("id"));
        idValue.setValue((Double) dbObject.get("value"));
        return idValue;
    }


    public List<FeatureVector> mergeAllFeatures(MongoCollections mongoCollections) {

        DBCollection descriptionCollection = mongoCollections.getDescriptionCollection();
        DBCollection attributeCollection = mongoCollections.getAttributeCollection();

        List<DBObject> attributeDocs = getDbDocs(attributeCollection);
        List<FeatureVector> featurevectors = new ArrayList<>();
        Gson gson = new Gson();

        for(DBObject object : attributeDocs) {

            DBObject descOBject = findDocumentById((String) object.get("id"), descriptionCollection);
            List<Object> attrValues = ( List<Object> )object.get("idValues");
            List<Object> descValues = (List<Object>) descOBject.get("idValues");

            FeatureVector featureVector = new FeatureVector();
            featureVector.setId((String) object.get("id"));
            List<IdFeatures> list = new ArrayList<>();

            for(int i=0 ;i< attrValues.size(); i++) {
                IdValue value1 = gson.fromJson(attrValues.get(i).toString(), IdValue.class);
                for(int j=0; j < descValues.size();  j++) {
                    IdValue value2 = gson.fromJson(descValues.get(j).toString(), IdValue.class);
                    //System.out.println(value1.getId()+" "+value2.getId());
                    if(value1.getId().equals(value2.getId())) {
                        list.add(new IdFeatures(value1.getId(),
                                1- value2.getValue(),
                                1- value1.getValue(),
                                0.0));
                    }
                }
            }
            featureVector.setFeatures(list);
            featurevectors.add(featureVector);

        }

        return featurevectors;
    }
}
