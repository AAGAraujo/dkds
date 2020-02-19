/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Renan
 */
public class Challenge {
    
    private String description;
    private String bestPractice;
    
    public Challenge(String description, String bestPractice){
        this.description = description;
        this.bestPractice = bestPractice;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    

	public String getBestPractice() {
		return bestPractice;
	}

	public void setBestPractice(String bestPractice) {
		this.bestPractice = bestPractice;
	}

	@Override
    public String toString(){
        return description;
    }
    
}
