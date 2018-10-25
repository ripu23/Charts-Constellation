package chartconstellation.app.AppConfiguration;

public class WeightConfig {

    private String chartEncoding;
    private String attribute;
    private String similarity;

    public String getChartEncoding() {
        return chartEncoding;
    }

    public void setChartEncoding(String chartEncoding) {
        this.chartEncoding = chartEncoding;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    @Override
    public String toString() {
        return "WeightConfig{" +
                "chartEncoding='" + chartEncoding + '\'' +
                ", attribute='" + attribute + '\'' +
                ", similarity='" + similarity + '\'' +
                '}';
    }
}
