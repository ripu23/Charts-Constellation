package chartconstellation.app.entities;

import com.mongodb.DBCollection;

public class MongoCollections {

    private DBCollection attributeCollection;
    private DBCollection descriptionCollection;

    public DBCollection getAttributeCollection() {
        return attributeCollection;
    }

    public void setAttributeCollection(DBCollection attributeCollection) {
        this.attributeCollection = attributeCollection;
    }

    public DBCollection getDescriptionCollection() {
        return descriptionCollection;
    }

    public void setDescriptionCollection(DBCollection descriptionCollection) {
        this.descriptionCollection = descriptionCollection;
    }
}
