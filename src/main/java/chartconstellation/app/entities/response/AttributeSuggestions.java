package chartconstellation.app.entities.response;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class AttributeSuggestions {

    private List<String> suggestions;

    private HashMap<String, Set<String>> userExploringAttributes;

    public HashMap<String, Set<String>> getUserExploringAttributes() {
        return userExploringAttributes;
    }

    public void setUserExploringAttributes(HashMap<String, Set<String>> userExploringAttributes) {
        this.userExploringAttributes = userExploringAttributes;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public String toString() {
        return "AttributeSuggestions{" +
                "suggestions=" + suggestions +
                ", userExploringAttributes=" + userExploringAttributes +
                '}';
    }
}
