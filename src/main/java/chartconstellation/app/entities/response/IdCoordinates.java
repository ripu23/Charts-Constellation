package chartconstellation.app.entities.response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IdCoordinates {

    private String id;
    private String userName;
    private int clusterId = 0;
    private String chartType;
    private String chartName;
    private String color;
    private String title;
    private String description;
    private Point point;
    private Set<String> attributes;
    private String Date;

    public Set<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = new HashSet<>(attributes);
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public void setChartName(String chartName)
    {
        this.chartName = chartName;
    }

    @Override
    public String toString() {
        return "IdCoordinates{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", clusterId=" + clusterId +
                ", chartType='" + chartType + '\'' +
                ", chartName='" + chartName + '\'' +
                ", color='" + color + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", point=" + point +
                ", attributes=" + attributes +
                ", Date='" + Date + '\'' +
                '}';
    }
}
