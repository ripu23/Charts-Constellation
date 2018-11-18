package chartconstellation.app.util;

import java.util.*;

import chartconstellation.app.entities.Chart;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import chartconstellation.app.entities.FeatureDistance;
import chartconstellation.app.entities.IdValue;

@Component
public class AttributeUtil {

    @Autowired
    MongoClient mongoClient;

    public double jaccardSimilarity(Set<String> s1, Set<String> s2) {

        final int sa = s1.size();
        final int sb = s2.size();
        s1.retainAll(s2);
        final int intersection = s1.size();
        return 1d / (sa + sb - intersection) * intersection;
    }

    public List<String> getAttributesOfaObject(JSONObject jsonObj) {

        List<String> attrs = new ArrayList<>();

        try {
            String xattr = jsonObj.getJSONObject("encoding").getJSONObject("x").get("field").toString();
            attrs.add(xattr);
        } catch(Exception e) {

        }

        try {
            JSONObject obj = jsonObj.getJSONObject("encoding").getJSONObject("y");
            if(obj.has("field")) {
                String yattr = obj.get("field").toString();
                attrs.add(yattr);
            }
        } catch(Exception e) {

        }

        try {

            JSONArray arr = jsonObj.getJSONArray("layer");
            for(int i=0 ;i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String xattr = obj.getJSONObject("encoding").getJSONObject("x").get("field").toString();
                String yattr = obj.getJSONObject("encoding").getJSONObject("y").get("field").toString();
                attrs.add(xattr);
                attrs.add(yattr);
            }

        } catch(Exception e) {

        }
        return attrs;
    }


    public List<DBObject> getDBObjects(DBCollection collection) {
        DBCursor cursor = collection.find();
        List<DBObject> docs = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            docs.add(obj);
        }
        return docs;
    }

    public Set<String> getAllAttributes(String database, String dbCollection) {
        DBCollection collection = mongoClient.getDB(database)
                .getCollection(dbCollection);
        List<DBObject> docs = getDBObjects(collection);
        Set<String> allAttributes = new HashSet<>();
        for(DBObject obj : docs) {
            JSONObject jsonObj = new JSONObject(obj.toString());
            Set<String> set = new HashSet<String>(getAttributesOfaObject(jsonObj));
            allAttributes.addAll(set);
        }
        return allAttributes;
    }




    public List<FeatureDistance> computerAttributeDistance(DBCollection collection) {

        List<DBObject> docs = getDBObjects(collection);
        List<FeatureDistance> distances = new ArrayList<>();

        for (DBObject obj1 : docs) {
            FeatureDistance indDistance = new FeatureDistance();
            int id1 = (int) obj1.get("id");
            indDistance.setId(String.valueOf(id1));
            List<IdValue> values = new ArrayList<>();
            for (DBObject obj2 : docs) {

                int id2 = (int) obj2.get("id");
                if (id1 != id2) {
                    try {
                        JSONObject jsonObj1 = new JSONObject(obj1.toString());
                        JSONObject jsonObj2 = new JSONObject(obj2.toString());

                        Set<String> set1 = new HashSet<String>(getAttributesOfaObject(jsonObj1));
                        Set<String> set2 = new HashSet<String>(getAttributesOfaObject(jsonObj2));


                        Double val = jaccardSimilarity(
                                set1,
                                set2
                        );

                        values.add(new IdValue(String.valueOf(id2), val));

                    } catch (Exception e) {

                    }
                }
            }
            indDistance.setIdValues(values);
            distances.add(indDistance);
        }

        return distances;
    }

    public HashMap<String, Integer> getAttributesList(List<Chart> charts) {

        HashMap<String, Integer> attributesMap = new HashMap<>();

        for(Chart chart : charts) {
            List<String> attributes = chart.getAttributes();
            for(String str : attributes) {
                if(attributesMap.containsKey(str)) {
                    attributesMap.put(str, attributesMap.get(str) + 1);
                } else {
                    attributesMap.put(str, 1);
                }

            }
        }
        return sortMap(attributesMap);

    }

    public HashMap<String, Integer>  sortMap(HashMap<String, Integer> attributesMap ) {

        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(attributesMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return -1 * (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


}
