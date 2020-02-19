package model;

public class CulturalPersonalCharacteristic {

	private String cod;
	private String description;
	
	
	public CulturalPersonalCharacteristic(String cod, String description) {
		super();
		this.cod = cod;
		this.description = description;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
