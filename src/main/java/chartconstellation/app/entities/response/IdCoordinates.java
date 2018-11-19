package chartconstellation.app.entities.response;

public class IdCoordinates {

    private String id;
    private String userName;
    private int clusterId = 0;
    private String chartType;
    private String chartName;
    private String color;
    private Point point;

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    @Override
    public String toString() {
        return "IdCoordinates{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", clusterId=" + clusterId +
                ", chartType='" + chartType + '\'' +
                ", color='" + color + '\'' +
                ", point=" + point +
                '}';
    }
}
