package chartconstellation.app.engine;

import chartconstellation.app.entities.AttributeDistance;
import chartconstellation.app.entities.IdValue;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttributeUtil {

    public List<AttributeDistance> computerAttributeDistance(DBCollection collection) {

        DBCursor cursor = collection.find();
        List<DBObject> docs = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            docs.add(obj);
        }

        List<AttributeDistance> distances = new ArrayList<>();

        for (DBObject obj1 : docs) {
            AttributeDistance indDistance = new AttributeDistance();
            int id1 = (int) obj1.get("id");
            indDistance.setId(String.valueOf(id1));
            List<IdValue> values = new ArrayList<>();
            for (DBObject obj2 : docs) {

                int id2 = (int) obj2.get("id");
                if (id1 != id2) {
                    try {
                        JSONObject jsonObj1 = new JSONObject(obj1.toString());
                        String val1 = jsonObj1.getJSONObject("encoding").getJSONObject("x").get("field").toString();
                        JSONObject jsonObj2 = new JSONObject(obj2.toString());
                        String val2 = jsonObj2.getJSONObject("encoding").getJSONObject("x").get("field").toString();

                        if(val1.equals(val2)) {
                            values.add(new IdValue(String.valueOf(id2), 1.0));
                        } else {
                            values.add(new IdValue(String.valueOf(id2), 0.0));
                        }

                    } catch (Exception e) {

                    }
                }
            }
            indDistance.setIdValues(values);
            distances.add(indDistance);
        }

        return distances;
    }

}
