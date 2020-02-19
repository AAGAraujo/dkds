/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbc.basic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author YGOR
 */

public class CasoProblemaDDS implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String descricao;
    private String solucao;
    private String efetividade;
    private Date dataCaso;
    private String skill;
    private List<PalavraProblemaDDS> palavraProblemaDDSList;
    private List<DependenciaProblemaDDS> dependenciaProblemaDDSList;

    public CasoProblemaDDS() {
    }

    
    public CasoProblemaDDS(String id,String descricao, String solucao, String efetividade, String Skill) {
        this.id = id;
        this.descricao = descricao;
        this.solucao = solucao;
        this.efetividade = efetividade;
        this.skill = skill;
    }
    
    public CasoProblemaDDS(String descricao){
    	this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public String getEfetividade() {
        return efetividade;
    }

    public void setEfetividade(String efetividade) {
        this.efetividade = efetividade;
    }

    public Date getDataCaso() {
        return dataCaso;
    }

    public void setDataCaso(Date dataCaso) {
        this.dataCaso = dataCaso;
    }

    @XmlTransient
    public List<PalavraProblemaDDS> getPalavraProblemaDDSList() {
        return palavraProblemaDDSList;
    }

    public void setPalavraProblemaDDSList(List<PalavraProblemaDDS> palavraProblemaDDSList) {
        this.palavraProblemaDDSList = palavraProblemaDDSList;
    }

    @XmlTransient
    public List<DependenciaProblemaDDS> getDependenciaProblemaDDSList() {
        return dependenciaProblemaDDSList;
    }

    public void setDependenciaProblemaDDSList(List<DependenciaProblemaDDS> dependenciaProblemaDDSList) {
        this.dependenciaProblemaDDSList = dependenciaProblemaDDSList;
    }
    
    

    public String getSkill() {
		return skill;
	}


	public void setSkill(String skill) {
		this.skill = skill;
	}


	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CasoProblemaDDS)) {
            return false;
        }
        CasoProblemaDDS other = (CasoProblemaDDS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rbc.pln.tcc.basic.CasoProblemaDDS[ id=" + id + " ]";
    }
    
}
