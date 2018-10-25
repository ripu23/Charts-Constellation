package chartconstellation.app.entities;

import java.util.List;

public class AttributeDistance {

    private String id;
    private List<IdValue> idValues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<IdValue> getIdValues() {
        return idValues;
    }

    public void setIdValues(List<IdValue> idValues) {
        this.idValues = idValues;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", idValues=" + idValues +
                '}';
    }
}
