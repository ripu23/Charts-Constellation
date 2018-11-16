package chartconstellation.app.entities.response;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Cluster {

    private int clusterId;

    private HashMap<String, List<String>> users;

    private HashMap<String, List<String>> attributes;

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public HashMap<String, List<String>> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, List<String>> users) {
        this.users = users;
    }

    public HashMap<String, List<String>> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, List<String>> attributes) {
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
