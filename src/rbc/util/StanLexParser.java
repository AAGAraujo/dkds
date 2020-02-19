package rbc.util;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import rbc.basic.CasoProblemaDDS;
import rbc.basic.DependenciaProblemaDDS;
import rbc.basic.PalavraProblemaDDS;

/**
 *
 * @author YGOR
 */
public class StanLexParser {
    private LexicalizedParser lp;
    
    public StanLexParser(String modelo){
        this.lp = LexicalizedParser.loadModel(modelo);
    }
    
    public CasoProblemaDDS extrairTokensParaCaso(CasoProblemaDDS caso){
        //Para não ter problemas com maiúsculas e minúsculas, colocamos tudo em lowerCase
        String texto = caso.getDescricao().toLowerCase();
        TokenizerFactory<CoreLabel> tokenizerFactory =
                PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        //Separamos os tokens do texto utilizando a classe TokenizerFactory
        List<CoreLabel> words =
                tokenizerFactory.getTokenizer(new StringReader(texto)).tokenize();
        Tree parse = lp.apply(words);
        List<PalavraProblemaDDS> palavrasPro = new ArrayList<PalavraProblemaDDS>();
        int posicao = 1;
        System.out.println("\n\n\nClasses gramaticais das palavras:");
        for(TaggedWord word: parse.taggedYield()){
            System.out.println(word);
            //Pegamos palavra por palavra, seu tipo e posição
            PalavraProblemaDDS palavraProblema = new PalavraProblemaDDS();
            palavraProblema.setIdCaso(caso);
            palavraProblema.setPalavra(word.word());
            palavraProblema.setTipo(word.tag());
            palavraProblema.setPosicao(posicao);
            palavrasPro.add(palavraProblema);
            posicao++;
        }
        //System.out.println(parse.taggedYield());
        System.out.println("\n\n\n�?rvore Gramatical do Texto:");
        parse.pennPrint();
        //Coloca em caso
        caso.setPalavraProblemaDDSList(palavrasPro);
        return caso;
    }
    
    public CasoProblemaDDS extrairDependenciasParaCaso(CasoProblemaDDS caso){
        //Para não ter problemas com maiúsculas e minúsculas, colocamos tudo em lowerCase
        String texto = caso.getDescricao().toLowerCase();
        TokenizerFactory<CoreLabel> tokenizerFactory =
                PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
        //Separamos os tokens do texto utilizando a classe TokenizerFactory
        List<CoreLabel> words =
                tokenizerFactory.getTokenizer(new StringReader(texto)).tokenize();
        Tree parse = lp.apply(words);
        TreebankLanguagePack tlp = new PennTreebankLanguagePack();
        //Construimos a estrutura gramatical e capturamos as 
        //dependências utilizando a classe GrammaticalStructure
        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
        GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
        List<DependenciaProblemaDDS> dependenciasPro = new ArrayList<DependenciaProblemaDDS>();
        List<PalavraProblemaDDS> palaPro = caso.getPalavraProblemaDDSList();
        // Varremos todas as dependências
        for(int a = 0; a <tdl.size(); a++){
            //Capturamos a dependencia através do objeto da classe TypedDependency
            TypedDependency t = tdl.get(a);
            DependenciaProblemaDDS dependenciaProblema = new DependenciaProblemaDDS();
            dependenciaProblema.setIdCaso(caso);
            // Pegamos o tipo utilizando o método reln(). 
            //Ex: det(factory-14, an-11), onde o tipo é det.
            dependenciaProblema.setTipo(t.reln().toString()); 
            //Separamos a palavra da sua posição que vem juntas. Ex: factory-14, para factory e 14.
            String[] aux = t.gov().toString().split("-");
            String[] aux2 = t.dep().toString().split("-");
            //Para o caso da existência de alguma palavra que utilize "-" em sua formação
            if(aux.length>2){
                String palavra = aux[0];
                for(int i=1; i<aux.length-1 ; i++){
                    palavra = palavra + "-" + aux[i];
                }
                aux[0] = palavra;
                aux[1] = aux[aux.length-1];
            }
            if(aux2.length>2){
                String palavra = aux2[0];
                for(int i=1; i<aux2.length-1 ; i++){
                    palavra = palavra + "-" + aux2[i];
                }
                aux2[0] = palavra;
                aux2[1] = aux2[aux2.length-1];
            }
            int i =0;
            boolean encontrou1 = false;
            boolean encontrou2 = false;
            //Procuramos as palavras que formam as dependências na lista de palavras do CasoProblemaDDS recebido
            if(!aux[1].equals("0") && !aux2[1].equals("0")){
             while(i<palaPro.size() || (!encontrou1 && !encontrou2)){
                PalavraProblemaDDS palavra = palaPro.get(i);
                /**
                 * Modificado em: 09/02/2015
                 * Este código abaixo foi modificado e comentado pois ao tentar criar no banco as palavras e as dependencias a partir de casos
                 * já existentes no banco que foram importados pelo csv.
                 * Quando executado foi lançada a exceção NumberFormatException no trecho Integer.parseInt(...)
                 * Ao invés de converter uma String para um inteiro, fiz o inverso, converti o inteiro palavra.getPosicao() para String e mandei verificar
                 * na String aux[1] ou aux2[1] se a String palavra.getPosicao() estava contida.
                 * Com isso, foram criadas palavras e dependencias para todos os casos.
                 * Em duvidas, excluir todos os dados das 3 tabelas do banco, descomentar o código abaixo e executar utilizando o csv Casos Extraídos3 - Teste
                 */
//                if(palavra.getPalavra().equals(aux[0]) && palavra.getPosicao()==Integer.parseInt(aux[1])){
//                    dependenciaProblema.setIdPalavra1(palavra);
//                    encontrou1 = true;
//                } else if(palavra.getPalavra().equals(aux2[0]) && palavra.getPosicao()==Integer.parseInt(aux2[1])){
//                    dependenciaProblema.setIdPalavra2(palavra);
//                    encontrou2 = true;
//                }
                /**
                 * Modificado em: 10/02/2015
                 * Melhoria do código abaixo pois agora ele retira os caracteres que não forem número.
                 * Como no erro era com a String 14', ele retira o ' e substitui por espaço vazio.
                 * OBS.: Tanto este quanto o código abaixo estão apresentando o mesmo resultado
                 * Porém este aparenta estar mais correto.
                 */
                int auxiliar1 = Integer.parseInt(aux[1].replaceAll("\\D", ""));
                int auxiliar2 = Integer.parseInt(aux2[1].replaceAll("\\D", ""));
                if(palavra.getPalavra().equals(aux[0]) && palavra.getPosicao()==auxiliar1){
                    dependenciaProblema.setIdPalavra1(palavra);
                    encontrou1 = true;
                } else if(palavra.getPalavra().equals(aux2[0]) && palavra.getPosicao()==auxiliar2){
                    dependenciaProblema.setIdPalavra2(palavra);
                    encontrou2 = true;
                }
                /**
                 * Este código abaixo foi o primeiro criado por mim e funcionou corretamente, porém contia um problema
                 * Se a palavra.getPosicao fosse 1 e o aux1 contivesse a String 10 ele iria considerar verdadeiro pois a string 1
                 * estaria contida na 10
                 */
//                String aux1 = aux[1];
//                String auxiliar2 = aux2[1];
//                if(palavra.getPalavra().equals(aux[0]) && aux1.contains(String.valueOf(palavra.getPosicao()))){
//                    dependenciaProblema.setIdPalavra1(palavra);
//                    encontrou1 = true;
//                } else if(palavra.getPalavra().equals(aux2[0]) && auxiliar2.contains(String.valueOf(palavra.getPosicao()))){
//                    dependenciaProblema.setIdPalavra2(palavra);
//                    encontrou2 = true;
//                }
                i++;
             }
            }
            //Formamos a dependência e adicionamos a lista de dependências a ser adicionada ao CasoProblemaDDS
            if(encontrou1 && encontrou2){
            dependenciasPro.add(dependenciaProblema);
            }
        }
        System.out.println("\n\n\nDependências entre palavras e seus tipos:");
        TreePrint tp = new TreePrint("typedDependenciesCollapsed");
        tp.printTree(parse);
        System.out.println("\n\n\n");
        //Adicionamos a lista de dependências ao objeto do CasoProblemaDDS
        caso.setDependenciaProblemaDDSList(dependenciasPro);
        return caso;
    }
}
