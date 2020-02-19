/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbc.business;

import rbc.basic.CasoProblemaDDS;

/**
 *
 * @author YGOR
 */
public class SimilaridadeCasos {
    private CasoProblemaDDS caso1;
    private CasoProblemaDDS caso2;
    private double similaridade;
    private double effectiveness;
    private String skill;

    public SimilaridadeCasos(CasoProblemaDDS caso1, CasoProblemaDDS caso2, double similaridade, double effectiveness, String skill){
        this.caso1 = caso1;
        this.caso2 = caso2;
        this.similaridade = similaridade;
        this.effectiveness = effectiveness;
        this.skill = skill;
    }
    
    public SimilaridadeCasos(){}    
    public CasoProblemaDDS getCaso1() {
        return caso1;
    }
    
    public void setCaso1(CasoProblemaDDS caso1) {
        this.caso1 = caso1;
    }
    
    public CasoProblemaDDS getCaso2() {
        return caso2;
    }
    
    public void setCaso2(CasoProblemaDDS caso2) {
        this.caso2 = caso2;
    }

    public double getSimilaridade() {
        return similaridade;
    }

    public void setSimilaridade(double similaridade) {
        this.similaridade = similaridade;
    }

	public double getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(double effectiveness) {
		this.effectiveness = effectiveness;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
    
    
    
}
