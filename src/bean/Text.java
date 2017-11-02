package bean;

public class Text {
	
	private String content;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minutes;
	
	
	@Override
	public String toString() {
		return "Text.entity   content=" + content +"; year=" + year 
				+ "; month=" + month+ "; day=" + day + "; hour=" + hour
				+ "; minutes=" + minutes
				;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
}
