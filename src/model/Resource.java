package model;

import java.util.List;

import rbc.business.SimilaridadeCasos;

public class Resource {
	
	private String resource;
	private String description;
	private List<SimilaridadeCasos> suportToSolve;
	private List<SimilaridadeCasos> suportToAvoid;
	
	
	public Resource(String resource, String description, List<SimilaridadeCasos> suportToSolve, List<SimilaridadeCasos> suportToAvoid) {
		super();
		this.resource = resource;
		this.description = description;
		this.suportToSolve = suportToSolve;
		this.suportToAvoid = suportToAvoid;
	}
	
	
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<SimilaridadeCasos> getSuportToSolve() {
		return suportToSolve;
	}
	public void setSuportToSolve(List<SimilaridadeCasos> suportToSolve) {
		this.suportToSolve = suportToSolve;
	}
	public List<SimilaridadeCasos> getSuportToAvoid() {
		return suportToAvoid;
	}
	public void setSuportToAvoid(List<SimilaridadeCasos> suportToAvoid) {
		this.suportToAvoid = suportToAvoid;
	}
	
	

}
