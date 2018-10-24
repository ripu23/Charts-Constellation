package chartconstellation.app.AppConfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class Configuration {

    private String mongodb_Host;
    private String mongodb_Port;

    public String getMongodb_Host() {
        return mongodb_Host;
    }

    public void setMongodb_Host(String mongodb_Host) {
        this.mongodb_Host = mongodb_Host;
    }

    public void setMongodb_Port(String mongodb_Port) {
        this.mongodb_Port = mongodb_Port;
    }

    public String getMongodb_Port() {
        return mongodb_Port;
    }
}
