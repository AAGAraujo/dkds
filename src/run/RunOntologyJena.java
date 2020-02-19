/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package run;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import util.OntologyQuery;

/**
 *
 * @author Renan
 */
public class RunOntologyJena {
    
    private static String queryBestPracticesExample = "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?member ?city ?country \n" +
                "WHERE { ?member a o:Member \n" +
                ". optional{\n" +
                "	?member o:IsInAPlace ?city \n" +
                "	. ?city o:HasEstate ?state \n" +
                "	. ?state  o:IsInARegion ?region \n" +
                "	. ?country o:hasRegion ?region \n" +
                "	}\n" +
                "}\n" +
                "order by ?member";
    
    public static void main(String[] args){
        OntologyQuery ontologyQuery = new OntologyQuery();
        
        ResultSet rs = ontologyQuery.executeQuery(queryBestPracticesExample);
        
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            System.out.println(qs.getResource("member").getLocalName());
        }
    }
}
