package model;

import java.util.List;

public class Team {
	
	private String description;
	private List<Member> members;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	public Team(String description, List<Member> members) {
		super();
		this.description = description;
		this.members = members;
	}

	
	
}
