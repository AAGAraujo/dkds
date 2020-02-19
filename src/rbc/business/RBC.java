/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbc.business;

import java.util.ArrayList;
import java.util.List;
import rbc.basic.CasoProblemaDDS;
import rbc.basic.DependenciaProblemaDDS;
import rbc.basic.PalavraProblemaDDS;
import rbc.controller.CasoProblemaDDSJpaController;

/**
 *
 * @author YGOR
 */
public class RBC {
    
    private CasoProblemaDDSJpaController daoCaso;
    // 0 QuantidadePalavrasIguais - 1 QuantidadePalavras - 2 QuantidadeIguaisVerbo - 3 QuantidadePalavraDependenciaIguais
    private int[] pesos = {8,3,3,4};
    
    public RBC() {
		daoCaso = new CasoProblemaDDSJpaController();
	}


	public int[] getPesos() {
		return pesos;
	}


	public void setPesos(int[] pesos) {
		this.pesos = pesos;
	}


	public boolean casoIgual(CasoProblemaDDS novoCaso, List<CasoProblemaDDS> todosCasos){
        SimilaridadeCasos casoMaisSemelhante = buscarCasoMaisSimilar(novoCaso, todosCasos);
        if(casoMaisSemelhante.getSimilaridade() == 1){
            return false;
        }
        return true;
    }
    
    
    public SimilaridadeCasos buscarCasoMaisSimilar(CasoProblemaDDS novoCaso, List<CasoProblemaDDS> todosCasos){
        double melhorSimilaridade = 0;
        CasoProblemaDDS casoMaisSimilar = null;
        for(CasoProblemaDDS casoBase: todosCasos){
            double ultimaSimilaridade = verificarSimilaridadeGlobal(novoCaso, casoBase);
            if(ultimaSimilaridade>melhorSimilaridade){
                melhorSimilaridade = ultimaSimilaridade;
                casoMaisSimilar = casoBase;
            }
        }
        return new SimilaridadeCasos(casoMaisSimilar,novoCaso,melhorSimilaridade,Double.parseDouble(casoMaisSimilar.getEfetividade()), casoMaisSimilar.getSkill());
    }
    
    public ArrayList<SimilaridadeCasos> listarCasosSimilares(CasoProblemaDDS novoCaso,double limiar, List<CasoProblemaDDS> todosCasos){
        ArrayList<SimilaridadeCasos> casosSimilares = new ArrayList<SimilaridadeCasos>();
        
        for(CasoProblemaDDS casoBase: todosCasos){
            double similaridade = verificarSimilaridadeGlobal(novoCaso, casoBase);
            if(similaridade >= limiar){
                boolean maior = false;
                int i = 0;
                int qtdCasos = casosSimilares.size();
                while(i<qtdCasos && !maior){
                    if(similaridade>casosSimilares.get(i).getSimilaridade()){
                        maior = true;
                        casosSimilares.add(i,new SimilaridadeCasos(casoBase,novoCaso,similaridade,Double.parseDouble(casoBase.getEfetividade()), casoBase.getSkill()));
                    }
                    i++;
                }
                if(!maior){
                    casosSimilares.add(new SimilaridadeCasos(casoBase,novoCaso,similaridade,Double.parseDouble(casoBase.getEfetividade()), casoBase.getSkill()));
                }
                
            }
        }
        return casosSimilares;
    }
    
    public double verificarSimilaridadeGlobal(CasoProblemaDDS novoCaso, CasoProblemaDDS casoBase){
        double sim = 0;
        int somaPesos = 0;
        for(int i=0; i<pesos.length; i++){
            sim = sim + pesos[i]*verificarSimilaridadeLocal(novoCaso, casoBase, i);
            somaPesos = somaPesos + pesos[i];
        }
        System.out.println("Similaridade: "+sim/somaPesos);
        return sim/somaPesos;
    }
    
    // 1 é muito similar e 0 é não similar
    public double verificarSimilaridadeLocal(CasoProblemaDDS novoCaso, CasoProblemaDDS casoBase, int atributo) {
        double valorSim = 1;
        List<PalavraProblemaDDS> novoPalavrasProblema, basePalavrasProblema;
        List<DependenciaProblemaDDS> novoDependenciasProblema, baseDependenciasProblema;
        double quantidadeNovo, quantidadeBase, quantidadeIgual;
        int i;
        switch (atributo) {
            case 0:
                novoPalavrasProblema = new ArrayList<PalavraProblemaDDS>();
                novoPalavrasProblema.addAll(novoCaso.getPalavraProblemaDDSList());
                basePalavrasProblema = new ArrayList<PalavraProblemaDDS>();
                basePalavrasProblema.addAll(casoBase.getPalavraProblemaDDSList());
                quantidadeNovo = novoPalavrasProblema.size();
                quantidadeBase = basePalavrasProblema.size();
                double auxQuantidadeBase = quantidadeBase;
                quantidadeIgual = 0;
                i = 0;
                if (quantidadeNovo > 0 && quantidadeBase > 0) {
                    while (i < quantidadeNovo) {
                        int j = 0;
                        boolean encontrou = false;
                        while (j < auxQuantidadeBase && !encontrou) {
                            if (novoPalavrasProblema.get(i).getPalavra().equals(basePalavrasProblema.get(j).getPalavra()) 
                                    && novoPalavrasProblema.get(i).getTipo().equals(basePalavrasProblema.get(j).getTipo())) {
                                quantidadeIgual++;
                                basePalavrasProblema.remove(j);
                                auxQuantidadeBase--;
                                encontrou = true;
                            }
                            j++;
                        }
                        i++;
                    }
                    if (quantidadeNovo >= quantidadeBase) {
                        valorSim = quantidadeIgual / quantidadeNovo;
                    } else if (quantidadeBase > quantidadeNovo) {
                        valorSim = quantidadeIgual / quantidadeBase;
                    }
                } else {
                    valorSim = 0;
                }
                break;
            case 1:
                novoPalavrasProblema = new ArrayList<PalavraProblemaDDS>();
                novoPalavrasProblema.addAll(novoCaso.getPalavraProblemaDDSList());
                basePalavrasProblema = new ArrayList<PalavraProblemaDDS>();
                basePalavrasProblema.addAll(casoBase.getPalavraProblemaDDSList());
                quantidadeNovo = novoPalavrasProblema.size();
                quantidadeBase = basePalavrasProblema.size();
                if (quantidadeNovo > 0 && quantidadeBase > 0) {
                    if (quantidadeNovo >= quantidadeBase) {
                        valorSim = quantidadeBase / quantidadeNovo;
                    } else if (quantidadeBase > quantidadeNovo) {
                        valorSim = quantidadeNovo / quantidadeBase;
                    }
                } else {
                    valorSim = 0;
                }
                break;
            case 2:
                novoPalavrasProblema = new ArrayList<PalavraProblemaDDS>();
                novoPalavrasProblema.addAll(novoCaso.getPalavraProblemaDDSList());
                basePalavrasProblema = new ArrayList<PalavraProblemaDDS>();
                basePalavrasProblema.addAll(casoBase.getPalavraProblemaDDSList());
                quantidadeNovo = novoPalavrasProblema.size();
                quantidadeBase = basePalavrasProblema.size();
                auxQuantidadeBase = quantidadeBase;
                quantidadeIgual = 0;
                double quantidadeVerbosNovo = 0;
                double quantidadeVerbosBase = 0;
                i = 0;

                if (quantidadeNovo > 0 && quantidadeBase > 0) {
                    for (int a = 0; a < quantidadeNovo; a++) {
                        if (novoPalavrasProblema.get(a).getTipo().contains("VB")) {
                            quantidadeVerbosNovo++;
                        }
                    }
                    for (int a = 0; a < quantidadeBase; a++) {
                        if (basePalavrasProblema.get(a).getTipo().contains("VB")) {
                            quantidadeVerbosBase++;
                        }
                    }

                    while (i < quantidadeNovo) {
                        int j = 0;
                        boolean encontrou = false;
                        while (j < auxQuantidadeBase && !encontrou) {
                            if (novoPalavrasProblema.get(i).getPalavra().equals(basePalavrasProblema.get(j).getPalavra()) 
                                    && novoPalavrasProblema.get(i).getTipo().equals(basePalavrasProblema.get(j).getTipo())
                                    && novoPalavrasProblema.get(i).getTipo().contains("VB")
                                    && basePalavrasProblema.get(j).getTipo().contains("VB")) {
                                quantidadeIgual++;
                                basePalavrasProblema.remove(j);
                                auxQuantidadeBase--;
                                encontrou = true;
                            }
                            j++;
                        }
                        i++;
                    }
                    if(quantidadeVerbosNovo>0 || quantidadeVerbosBase>0){
                    if (quantidadeVerbosNovo >= quantidadeVerbosBase) {
                        valorSim = quantidadeIgual / quantidadeVerbosNovo;
                    } else if (quantidadeVerbosBase > quantidadeVerbosNovo) {
                        valorSim = quantidadeIgual / quantidadeVerbosBase;
                    }
                    }else{
                        valorSim = 1;
                    }
                } else {
                    valorSim = 0;
                }
                break;
            case 3:
                novoDependenciasProblema = new ArrayList<DependenciaProblemaDDS>();
                novoDependenciasProblema.addAll(novoCaso.getDependenciaProblemaDDSList());
                baseDependenciasProblema = new ArrayList<DependenciaProblemaDDS>();
                baseDependenciasProblema.addAll(casoBase.getDependenciaProblemaDDSList());
                quantidadeNovo = novoDependenciasProblema.size();
                quantidadeBase = baseDependenciasProblema.size();
                auxQuantidadeBase = quantidadeBase;
                quantidadeIgual = 0;
                i = 0;
                if (quantidadeNovo > 0 && quantidadeBase > 0) {
                   while (i < quantidadeNovo) {
                        int j = 0;
                        boolean encontrou = false;
                        while (j < auxQuantidadeBase && !encontrou) {
                            if (novoDependenciasProblema.get(i).getIdPalavra1().getPalavra().
                                    equals(baseDependenciasProblema.get(j).getIdPalavra1().getPalavra())) {
                                quantidadeIgual++;
                                baseDependenciasProblema.remove(j);
                                auxQuantidadeBase--;
                                encontrou = true;
                            }
                            j++;
                        }
                        i++;
                    }
                    if (quantidadeNovo >= quantidadeBase) {
                        valorSim = quantidadeIgual / quantidadeNovo;
                    } else if (quantidadeBase > quantidadeNovo) {
                        valorSim = quantidadeIgual / quantidadeBase;
                    }
                } else {
                    valorSim = 0;
                }
                break;
        }
        System.out.println("Similaridade Atributo " + atributo + ": " + valorSim);

        return valorSim;
    }
    
    /* 0 QuantidadePalavrasIguais - 1 QuantidadePalavras 
       2 QuantidadeIguaisVerbo - 3 QuantidadePalavraDependenciaIguais */
    public void pesosSimilaridade(int quantidadePalavrasIguais, int quantidadePalavras, 
            int quantidadeIguaisVerbo, int quantidadePalavraDependencia){
        pesos[0] = quantidadePalavrasIguais;
        pesos[1] = quantidadePalavras;
        pesos[2] = quantidadeIguaisVerbo;
        pesos[3] = quantidadePalavraDependencia;
    }
    /* Verifica quais casos tem similaridade de 100% e exclui um deles
    */
    public void filtrarCasosSimilares(){
        
    }
}
