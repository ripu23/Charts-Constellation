package chartconstellation.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Chart {

	private String description;
	@JsonProperty("user")
	private String user;
	@JsonProperty("id")
	private String id;
	private DateTime dateTime;
	private String chartType;
	private String title;
	private List<String> attributes;

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	public String getChartType() {
		return chartType;
	}
	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	@Override
	public String toString() {
		return "Chart [description=" + description + ", user=" + user + ", dateTime=" + dateTime + ", chartType="
				+ chartType + ", title=" + title + "]";
	}
}
