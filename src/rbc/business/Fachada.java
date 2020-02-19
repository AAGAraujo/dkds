
package rbc.business;

import java.util.ArrayList;
import java.util.List;

import rbc.basic.CasoProblemaDDS;
import rbc.controller.CasoProblemaDDSJpaController;
import rbc.util.StanLexParser;

/**
 *
 * @author YGOR
 */
public class Fachada {
    private RBC rbc;
    private StanLexParser parser;
    private List<CasoProblemaDDS> todosCasos;
    
    public Fachada(String modelLexParser, int pesoQuantidadePalavrasIguais, int pesoQuantidadePalavras, int pesoQuantidadeIguaisVerbo, int pesoQuantidadePalavraDependencia){
        initComponents(modelLexParser, pesoQuantidadePalavrasIguais, pesoQuantidadePalavras, pesoQuantidadeIguaisVerbo, pesoQuantidadePalavraDependencia);
    }
    
    public Fachada(String modelLexParser){
        initComponents(modelLexParser, 8, 3, 3, 4);
        todosCasos = new CasoProblemaDDSJpaController().findCasoProblemaDDSEntities();
    }
    
    private void initComponents(String modelLexParser, int pesoQuantidadePalavrasIguais, int pesoQuantidadePalavras, int pesoQuantidadeIguaisVerbo, int pesoQuantidadePalavraDependencia){
        rbc = new RBC();
        rbc.pesosSimilaridade(pesoQuantidadePalavrasIguais, pesoQuantidadePalavras, pesoQuantidadeIguaisVerbo, pesoQuantidadePalavraDependencia);
        parser = new StanLexParser(modelLexParser);
    }
    
    public ArrayList<SimilaridadeCasos> listarCasosSimilares(CasoProblemaDDS novoCaso, double minimo){
        novoCaso = parser.extrairTokensParaCaso(novoCaso);
        novoCaso = parser.extrairDependenciasParaCaso(novoCaso);
        return rbc.listarCasosSimilares(novoCaso, minimo, todosCasos);
    }
    
    public SimilaridadeCasos buscarCasoMaisSimilar(CasoProblemaDDS novoCaso){
    	novoCaso = parser.extrairTokensParaCaso(novoCaso);
    	novoCaso = parser.extrairDependenciasParaCaso(novoCaso);
        return rbc.buscarCasoMaisSimilar(novoCaso, todosCasos);
    }

	public RBC getRbc() {
		return rbc;
	}

	public void setRbc(RBC rbc) {
		this.rbc = rbc;
	}
    
    
}
