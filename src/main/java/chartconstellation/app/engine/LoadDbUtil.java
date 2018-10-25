package chartconstellation.app.engine;

import chartconstellation.app.AppConfiguration.Configuration;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadDbUtil {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    Configuration configuration;

    public void updateDB(List<DBObject> dobobjects) {

        mongoClient.getDatabase(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection()).drop();

        mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection())
                .insert(dobobjects);
    }
}
