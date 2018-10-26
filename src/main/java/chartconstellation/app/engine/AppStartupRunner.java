package chartconstellation.app.engine;

import chartconstellation.app.appconfiguration.Configuration;
import chartconstellation.app.entities.FeatureDistance;
import chartconstellation.app.entities.FeatureVector;
import chartconstellation.app.entities.MongoCollections;
import chartconstellation.app.util.AttributeUtil;
import chartconstellation.app.util.DbUtil;
import chartconstellation.app.util.DocumentUtil;
import chartconstellation.app.util.FeatureMergeUtil;
import com.mongodb.*;
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
    DbUtil dbUtil;

    @Autowired
    FeatureMergeUtil featureMergeUtil;

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

            dbUtil.updateAttributeCollection(configuration.getMongoDatabase()
                    , configuration.getDescriptionCollection()
                    , descriptionDistances );

            MongoCollections mongoCollections = dbUtil.getCollections(configuration.getMongoDatabase());

            List<FeatureVector> featureVectors = featureMergeUtil.mergeAllFeatures(mongoCollections);

            System.out.println(featureVectors.size());

            dbUtil.updateFeaturesCollection(configuration.getMongoDatabase()
                            , configuration.getTotalFeatureCollection()
            , featureVectors);
        }
    }
}

