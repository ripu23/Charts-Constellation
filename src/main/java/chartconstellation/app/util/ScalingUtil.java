package chartconstellation.app.util;

import chartconstellation.app.appconfiguration.ScalingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ScalingUtil {


    private Double min;

    private Double max;

    private Double a;

    private Double b;

    private List<Double> values;

    // xnormalized=(b−a)x−min(x)max(x)−min(x)+a
    public ScalingUtil initialize(List<Double> valueList, Double a, Double b) {
        this.values = valueList;
        max = Collections.max(values);
        min = Collections.min(values);
        this.a = a;
        this.b = b;
        return this;
    }

    public Double getScaledvalue(Double val) {

        return (b - a) * ((val  -min) / (max - min)) + a;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }
}
