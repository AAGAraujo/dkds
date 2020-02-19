package model;

import java.util.List;

/**
 *
 * @author Arthur
 */
public class Project {
    
    private String description;
    private String methodology;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMethodology() {
		return methodology;
	}
	public void setMethodology(String methodology) {
		this.methodology = methodology;
	}
	public Project(String description, String methodology) {
		super();
		this.description = description;
		this.methodology = methodology;
	}

   
    
    
}
