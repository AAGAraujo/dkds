package model;

import java.util.List;

public class ProjectActivity {
	
	private String description;
	private String role;
	private List<String> task;
	private List<Schedule> schedule;
	private List<String> artifacts;

	public ProjectActivity(String description) {
		super();
		this.description = description;
	}
	
	public ProjectActivity(String description, String role, List<String> task, List<Schedule> schedule, List<String> artifacts) {
		super();
		this.description = description;
		this.role = role;
		this.task = task;
		this.schedule = schedule;
		this.artifacts = artifacts;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getTask() {
		return task;
	}

	public void setTask(List<String> task) {
		this.task = task;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	public List<String> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<String> artifacts) {
		this.artifacts = artifacts;
	}
	
	

}
