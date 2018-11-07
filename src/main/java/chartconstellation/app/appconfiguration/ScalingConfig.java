package chartconstellation.app.appconfiguration;

import org.springframework.stereotype.Component;

@Component
public class ScalingConfig {

    private Double min;
    private Double max;

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
}
