package chartconstellation.app.engine;

import chartconstellation.app.AppConfiguration.Configuration;
import chartconstellation.app.entities.Chart;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    @Autowired
    AttributeUtil attributeUtil;

    @Autowired
    LoadDbUtil dbUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getOptionNames());

        System.out.println(configuration.toString());

        List<DBObject> dobobjects = docutil.convertToDBObjectList(configuration.getInputPath());

        dbUtil.updateDB(dobobjects);

        DBCollection collection = mongoClient.getDB(configuration.getMongoDatabase())
                .getCollection(configuration.getOlympicchartcollection());

        attributeUtil.computerAttributeDistance(collection);

    }
}

