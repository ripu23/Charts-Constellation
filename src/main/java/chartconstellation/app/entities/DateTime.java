package chartconstellation.app.entities;

public class DateTime {

	private String fullTime;
	private String year;
	private String month;
	private String date;
	public String getFullTime() {
		return fullTime;
	}
	public void setFullTime(String fullTime) {
		this.fullTime = fullTime;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "DateTime [fullTime=" + fullTime + ", year=" + year + ", month=" + month + ", date=" + date + "]";
	}
	
}
