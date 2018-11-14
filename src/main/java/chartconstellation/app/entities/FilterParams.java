package chartconstellation.app.entities;

import chartconstellation.app.appconfiguration.WeightConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class FilterParams {

    private Object charts;

    private Object users;

    private WeightConfig weights;

    public Object getCharts() {
        return charts;
    }

    public void setCharts(Object charts) {
        this.charts = charts;
    }

    public Object getUsers() {
        return users;
    }

    public void setUsers(Object users) {
        this.users = users;
    }

    public WeightConfig getWeights() {
        return weights;
    }

    public void setWeights(WeightConfig weights) {
        this.weights = weights;
    }
}
