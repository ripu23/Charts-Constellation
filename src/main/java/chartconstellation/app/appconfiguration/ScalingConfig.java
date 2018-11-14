package chartconstellation.app.appconfiguration;

import org.springframework.stereotype.Component;

@Component
public class ScalingConfig {

    private Double xmin;
    private Double xmax;
    private Double ymin;
    private Double ymax;

    public Double getXmin() {
        return xmin;
    }

    public void setXmin(Double xmin) {
        this.xmin = xmin;
    }

    public Double getXmax() {
        return xmax;
    }

    public void setXmax(Double xmax) {
        this.xmax = xmax;
    }

    public Double getYmin() {
        return ymin;
    }

    public void setYmin(Double ymin) {
        this.ymin = ymin;
    }

    public Double getYmax() {
        return ymax;
    }

    public void setYmax(Double ymax) {
        this.ymax = ymax;
    }

    @Override
    public String toString() {
        return "ScalingConfig{" +
                "xmin=" + xmin +
                ", xmax=" + xmax +
                ", ymin=" + ymin +
                ", ymax=" + ymax +
                '}';
    }
}
