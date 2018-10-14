package chartconstellation.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class Chart {

	private String description;
	@JsonProperty("user")
	private String userName;
	private DateTime dateTime;
	private String chartType;
	private String title;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
		return "Chart [description=" + description + ", userName=" + userName + ", dateTime=" + dateTime + ", chartType="
				+ chartType + ", title=" + title + "]";
	}
	
	
	
}
