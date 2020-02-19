package model;

public class Schedule {
	
	private String dateTime;
	private String description;
	
	public Schedule(String dateTime, String description) {
		super();
		this.dateTime = dateTime;
		this.description = description;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
