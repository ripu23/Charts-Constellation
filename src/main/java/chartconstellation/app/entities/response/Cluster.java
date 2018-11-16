package chartconstellation.app.entities.response;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Cluster {

    private int clusterId;

    private List<String> users;

    private List<String> attributes;

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "clusterId=" + clusterId +
                ", users=" + users +
                ", attributes=" + attributes +
                '}';
    }
}
