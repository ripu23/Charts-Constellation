package chartconstellation.app.engine;

import chartconstellation.app.entities.AttributeDistance;
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

        for (DBObject obj1 : docs) {
            for (DBObject obj2 : docs) {
                int id1 = (int) obj1.get("id");
                int id2 = (int) obj2.get("id");
                if (id1 != id2) {
                    try {
                        JSONObject jsonObj1 = new JSONObject(obj1.toString());
                        System.out.print(jsonObj1.getJSONObject("encoding").getJSONObject("x").get("field") + " ");
                        JSONObject jsonObj2 = new JSONObject(obj2.toString());
                        System.out.println(jsonObj2.getJSONObject("encoding").getJSONObject("x").get("field"));
                    } catch (Exception e) {

                    }
                }
            }
        }

        return new ArrayList<>();
    }

}
