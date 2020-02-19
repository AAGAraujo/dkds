/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbc.controller;

import java.io.Serializable;
import java.util.List;
import rbc.basic.CasoProblemaDDS;
import util.OntologyQuery;

/**
 *
 * @author YGOR
 */
public class CasoProblemaDDSJpaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<CasoProblemaDDS> findCasoProblemaDDSEntities() {
		OntologyQuery oq = new OntologyQuery();
        return oq.getCasoProblemaDDS();
    }

    public CasoProblemaDDS findCasoProblemaDDS(String id) {
        List<CasoProblemaDDS> list = findCasoProblemaDDSEntities();
        for(CasoProblemaDDS cp : list){
        	if(cp.getId().equals(id))
        		return cp;
        }
        return null;
    }

//    public int getCasoProblemaDDSCount() {
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            Root<CasoProblemaDDS> rt = cq.from(CasoProblemaDDS.class);
//            cq.select(em.getCriteriaBuilder().count(rt));
//            Query q = em.createQuery(cq);
//            return ((Long) q.getSingleResult()).intValue();
//        } finally {
//            em.close();
//        }
//    }
    
}
