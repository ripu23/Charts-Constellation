package chartconstellation.app.AppConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@JsonIgnoreProperties
public class Configuration {

    private String inputPath;
    private String mongoDatabase;
    private String olympicchartcollection;

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }

    public void setMongoDatabase(String mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public String getOlympicchartcollection() {
        return olympicchartcollection;
    }

    public void setOlympicchartcollection(String olympicchartcollection) {
        this.olympicchartcollection = olympicchartcollection;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "inputPath='" + inputPath + '\'' +
                ", mongoDatabase='" + mongoDatabase + '\'' +
                ", olympicchartcollection='" + olympicchartcollection + '\'' +
                '}';
    }
}
