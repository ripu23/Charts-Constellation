package chartconstellation.app.util;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

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
        this.yValues = yvalueList;
        this.xmax = Collections.max(xvalueList);
        this.xmin = Collections.min(xvalueList);
        this.ymax = Collections.max(this.yValues);
        this.ymin = Collections.min(this.yValues);

        //System.out.println("ymin "+this.ymin+" ymax "+ this.ymax);
//        this.ymin = this.ymin + 1;
//        this.ymax = this.ymax + 1;

        return this;
    }

    public Double getScaledvalueX(Double val, Double a, Double b) {

        double valX =  (b - a) * ((val  - xmin) / (xmax - xmin)) + a;

        //System.out.println("x = "+valX);

        return valX;
        //return 2 * val;
    }

    public Double getScaledvalueY(Double val, Double a, Double b) {

        double valY = (b - a) * ((val  - ymin) / (ymax - ymin)) + a;

        //System.out.println("y = "+valY);

        return valY;
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
