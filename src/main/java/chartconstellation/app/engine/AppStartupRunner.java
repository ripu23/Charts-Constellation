package chartconstellation.app.engine;

import chartconstellation.app.AppConfiguration.Configuration;
import chartconstellation.app.entities.FeatureDistance;
import com.mongodb.*;
import org.json.JSONArray;
import org.json.JSONObject;
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

    @Autowired
    AttributeUtil attributeUtil;

    @Autowired
    LoadDbUtil dbUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Your application started with option names : {}", args.getOptionNames());

        System.out.println(configuration.toString());

        if(configuration.isUpdateData()) {

            List<DBObject> dobobjects = docutil.convertToDBObjectList(configuration.getInputPath());

            dbUtil.updateDBDocs(dobobjects);

            DBCollection collection = mongoClient.getDB(configuration.getMongoDatabase())
                    .getCollection(configuration.getOlympicchartcollection());

            List<FeatureDistance> attrDistances = attributeUtil.computerAttributeDistance(collection);

            dbUtil.updateAttributeCollection(configuration.getMongoDatabase(),
                    configuration.getAttributeDistanceCollection(),
                    attrDistances);

            List<FeatureDistance> descriptionDistances =
                    docutil.convertJsonToFeatureList(configuration.getDescriptionDistancePath());

            System.out.println(descriptionDistances.size());

            dbUtil.updateAttributeCollection(configuration.getMongoDatabase()
                    , configuration.getDescriptionCollection()
                    , descriptionDistances );
        }
    }
}

