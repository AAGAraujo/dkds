/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbc.basic;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author YGOR
 */
public class PalavraProblemaDDS implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String palavra;
    private String tipo;
    private Integer posicao;
    private CasoProblemaDDS idCaso;
    private List<DependenciaProblemaDDS> dependenciaProblemaDDSList;
    private List<DependenciaProblemaDDS> dependenciaProblemaDDSList1;

    public PalavraProblemaDDS() {
    }

    public PalavraProblemaDDS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public CasoProblemaDDS getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(CasoProblemaDDS idCaso) {
        this.idCaso = idCaso;
    }

    @XmlTransient
    public List<DependenciaProblemaDDS> getDependenciaProblemaDDSList() {
        return dependenciaProblemaDDSList;
    }

    public void setDependenciaProblemaDDSList(List<DependenciaProblemaDDS> dependenciaProblemaDDSList) {
        this.dependenciaProblemaDDSList = dependenciaProblemaDDSList;
    }

    @XmlTransient
    public List<DependenciaProblemaDDS> getDependenciaProblemaDDSList1() {
        return dependenciaProblemaDDSList1;
    }

    public void setDependenciaProblemaDDSList1(List<DependenciaProblemaDDS> dependenciaProblemaDDSList1) {
        this.dependenciaProblemaDDSList1 = dependenciaProblemaDDSList1;
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
        if (!(object instanceof PalavraProblemaDDS)) {
            return false;
        }
        PalavraProblemaDDS other = (PalavraProblemaDDS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rbc.pln.tcc.basic.PalavraProblemaDDS[ id=" + id + " ]";
    }
    
}
