package chartconstellation.app.util;

import chartconstellation.app.appconfiguration.ScalingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ScalingUtil {


    private Double xmin;
    private Double xmax;
    private Double ymin;
    private Double ymax;
    private List<Double> xValues;
    private List<Double> yValues;

    public ScalingUtil initialize(List<Double> xvalueList, List<Double> yvalueList) {
        this.xValues = xvalueList;
        xmax = Collections.max(xvalueList);
        xmin = Collections.min(xvalueList);
        ymax = Collections.max(yvalueList);
        ymin = Collections.min(yvalueList);
        return this;
    }

    public Double getScaledvalueX(Double val, Double a, Double b) {

        return (b - a) * ((val  - xmin) / (xmax - xmin)) + a;
        //return 2 * val;
    }

    public Double getScaledvalueY(Double val, Double a, Double b) {

        return (b - a) * ((val  - ymin) / (ymax - ymin)) + a;
        //return 2 * val;
    }

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

    public List<Double> getxValues() {
        return xValues;
    }

    public void setxValues(List<Double> xValues) {
        this.xValues = xValues;
    }

    public List<Double> getyValues() {
        return yValues;
    }

    public void setyValues(List<Double> yValues) {
        this.yValues = yValues;
    }
}
