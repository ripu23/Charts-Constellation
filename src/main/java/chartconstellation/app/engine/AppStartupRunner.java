package chartconstellation.app.engine;

import chartconstellation.app.AppConfiguration.Configuration;
import chartconstellation.app.entities.Chart;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    @Autowired
    DocumentUtil docutil;

    @Autowired
    MongoClient mongoClient;

    @Autowired
    Configuration configuration;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getOptionNames());

        List<DBObject> dobobjects = docutil.convertToDBObjectList(configuration.getInputPath());
        System.out.println(configuration.toString());

        mongoClient.getDatabase(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection()).drop();

        mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection())
                .insert(dobobjects);

        DBCollection collection = mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection());

        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            System.out.println(obj.toString());
        }

        //System.out.println(docs.size());

    }
}

