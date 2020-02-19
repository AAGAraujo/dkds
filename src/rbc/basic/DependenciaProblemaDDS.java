/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbc.basic;

import java.io.Serializable;

/**
 *
 * @author YGOR
 */
public class DependenciaProblemaDDS implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String tipo;
    private PalavraProblemaDDS idPalavra2;
    private PalavraProblemaDDS idPalavra1;
    private CasoProblemaDDS idCaso;

    public DependenciaProblemaDDS() {
    }

    public DependenciaProblemaDDS(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public PalavraProblemaDDS getIdPalavra2() {
        return idPalavra2;
    }

    public void setIdPalavra2(PalavraProblemaDDS idPalavra2) {
        this.idPalavra2 = idPalavra2;
    }

    public PalavraProblemaDDS getIdPalavra1() {
        return idPalavra1;
    }

    public void setIdPalavra1(PalavraProblemaDDS idPalavra1) {
        this.idPalavra1 = idPalavra1;
    }

    public CasoProblemaDDS getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(CasoProblemaDDS idCaso) {
        this.idCaso = idCaso;
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
        if (!(object instanceof DependenciaProblemaDDS)) {
            return false;
        }
        DependenciaProblemaDDS other = (DependenciaProblemaDDS) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rbc.pln.tcc.basic.DependenciaProblemaDDS[ id=" + id + " ]";
    }
    
}
