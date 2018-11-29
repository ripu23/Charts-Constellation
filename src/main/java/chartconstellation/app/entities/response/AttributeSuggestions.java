package chartconstellation.app.entities.response;

import java.util.HashMap;
import java.util.Set;

public class AttributeSuggestions {

    private HashMap<String, Set<String>> userExploringAttributes;

    public HashMap<String, Set<String>> getUserExploringAttributes() {
        return userExploringAttributes;
    }

    public void setUserExploringAttributes(HashMap<String, Set<String>> userExploringAttributes) {
        this.userExploringAttributes = userExploringAttributes;
    }

    @Override
    public String toString() {
        return "AttributeSuggestions{" +
                "userExploringAttributes=" + userExploringAttributes +
                '}';
    }
}
