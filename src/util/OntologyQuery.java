/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import model.BestPractice;
import model.Challenge;
import model.CulturalPersonalCharacteristic;
import model.Language;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import model.Member;
import model.NonTechnicalBestPractice;
import model.NonTechnicalChallenge;
import model.ProcessActivity;
import model.ProcessPhase;
import model.Project;
import model.ProjectActivity;
import model.ProjectPhase;
import model.Resource;
import model.Schedule;
import model.Skills;
import model.Team;
import model.TechnicalBestPractice;
import model.TechnicalChallenge;
import rbc.basic.CasoProblemaDDS;
import rbc.business.SimilaridadeCasos;
import rbc.util.StanLexParser;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import rbc.pln.tcc.basic.CasoProblemaDDS;
//import rbc.pln.tcc.business.Fachada;


/**
 * esta classe e responsavel pela conversão da ontologia para o projeto
 * @author Renan... UPDATE Arthur
 */
public class OntologyQuery {
    
    private String pathOntology;
    
    private static String prefix = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                                 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
    
    public OntologyQuery(){
        this.pathOntology = "ontology/dkdonto.owl";
    }
    /**
     * este método realixa a conexão da aplicação com o arquivo owl
     * @return 
     */
    private OntModel connectOWL(){
    OntModel mode;
    mode = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_RULE_INF );
    InputStream in = FileManager.get().open( pathOntology ); 

    //test
    if (in == null) {
        throw new IllegalArgumentException("N�o foi poss�vel encontrar o arquivo");
    }
        return  (OntModel) mode.read(in, "RDF/XML");
    }
    
    /**
     * este método executa a query 
     * @param query
     * @return 
     */
    public ResultSet executeQuery(String query){
       
        //lê a query
        Query q = QueryFactory.create(query);

        //cria uma execuçao da query
        QueryExecution qe = QueryExecutionFactory.create(q, connectOWL());

        // Retorna o Resultset
        return qe.execSelect();

    }
    
    public List<Member> getMembersPerSkill(String skill){
    	System.out.println(skill);
    	List<Member> m = getMembers(OntologyQuery.createMemberQuery(new String[]{"a","e","i","o","u"}));
    	List<Member> result = new ArrayList<>();
    	for(Member line : m){
    		for(Skills s : line.getSkills()){
    			if(s.getDescription().equals(skill)){
    				result.add(line);
    			}
    		}
    	}
    	System.out.println(result.size());
    	return result;
    }
    
    public List<Member> getMembers(String query){
        ResultSet rs = executeQuery(query);
        //inicializa a lista result
        List<Member> result = (List<Member>) new ArrayList();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String name = qs.getResource("member").getLocalName();
            
            List<Skills> skills = getSkills(createSkillsQuery(name));
            
            List<Language> languages = getLanguages(createLanguagesQuery(name));
            
            String localization = "";
            String email = "";
            
            if(qs.getResource("city") != null){
                localization = qs.getResource("city").getLocalName();
            }
            
            if(qs.getResource("state") !=null){
                localization = localization.equals("") ? qs.getResource("state").getLocalName() : localization + " - "+qs.getResource("state").getLocalName();
            }
            
            if(qs.getResource("region") !=null){
                localization = localization.equals("") ? qs.getResource("region").getLocalName() : localization + " - "+qs.getResource("region").getLocalName();
            }
            
            if(qs.getResource("country") !=null){
                localization = localization.equals("") ? qs.getResource("country").getLocalName() : localization + " - "+qs.getResource("country").getLocalName();
            }
            
            if(qs.getResource("email") != null){
                email = qs.getResource("email").getLocalName();
            }
            
            //cria o novo objeto com os atributos acima indicados
            result.add(new Member(name.replace("_", " "), email, localization.replace("_", " "), skills, languages));
        }
        return result;
    }
    
    public List<BestPractice> getNonTechnicalBestPractices(String query, String columnName){
        ResultSet rs = executeQuery(query);
        List<BestPractice> result = new ArrayList();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String bestPractice = qs.getResource("practiceid").getLocalName();
            String description = qs.getLiteral(columnName).getString().replace("_"," ");
            
            result.add(new NonTechnicalBestPractice(bestPractice,description));
        }
        return result;
    }
    
    public List<TechnicalBestPractice> getTechnicalBestPractices(String query, String columnName){
        ResultSet rs = executeQuery(query);
        List<TechnicalBestPractice> result = (List<TechnicalBestPractice>) new ArrayList();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String bestPractice = qs.getResource("practiceid").getLocalName();
            String description = qs.getResource(columnName).getLocalName().replace("_"," ");
            
            result.add(new TechnicalBestPractice(bestPractice,description));
        }
        return result;
    }
    
    public List<Challenge> getNonTechnicalChallenges(String query, String columnName){
        ResultSet rs = executeQuery(query);
        List<Challenge> result = new ArrayList<>();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String description = qs.getLiteral(columnName).getString().replace("_"," ");
            String bestPratice = qs.getResource("bestpractice").getLocalName().replace("_", " ");            
            
            result.add(new Challenge(description,bestPratice));
        }
        return result;
    }
    
    public List<Challenge> getTechnicalChallenges(String query, String columnName){
        ResultSet rs = executeQuery(query);
        List<Challenge> result = new ArrayList<>();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String description = qs.getLiteral(columnName).toString().replace("_"," ");
            String bestPratice = qs.getResource("bestpractice").getLocalName().replace("_", " ");    
            
            result.add(new Challenge(description,bestPratice));
        }
        return result;
    }
    
    public List<Project> getProjects(String query){
    	ResultSet rs = executeQuery(query);
    	List<Project> result = new ArrayList<>();
    	while (rs.hasNext()) {
			QuerySolution qs = rs.next();
			
			String project = qs.getResource("project").getLocalName().replace("_", " ");
			String methodology = qs.getResource("methodology").getLocalName().replace("_", " ");
			result.add(new Project(project, methodology));
		}
    	
    	return result;
    }
    
    public List<Team> getTeams(String query){
    	ResultSet rs = executeQuery(query);
    	List<Team> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		
    		String description = qs.getResource("team").getLocalName().replace("_", " ");
    		List<Member> members = getMembers(createMemberQueryForProject(qs.getResource("team").getLocalName()));
    		
    		result.add(new Team(description, members));
    	}
    	return result;
    }
    
    public List<Skills> getSkills(String query){
        ResultSet rs = executeQuery(query);
        List<Skills> result = (List<Skills>) new ArrayList();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String description = qs.getResource("skills").getLocalName().replace("_"," ");
            
            result.add(new Skills(description));
        }
        return result;
    }
    
    public List<Language> getLanguages(String query){
        ResultSet rs = executeQuery(query);
        List<Language> result = (List<Language>) new ArrayList();
        while(rs.hasNext()){
            QuerySolution qs = rs.next();
            
            String description = qs.getResource("language").getLocalName().replace("_"," ");
            
            result.add(new Language(description));
        }
        return result;
    }
    
    public List<ProjectPhase> getProjectPhase(String filter){
    	ResultSet rs = executeQuery(createProjectPhaseQuery(filter));
    	List<ProjectPhase> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getResource("projectphase").getLocalName().replaceAll("_", " ");
    		result.add(new ProjectPhase(description));
    	}
    	return result;
    }
    
    public List<ProcessPhase> getProcessPhase(String filter){
    	ResultSet rs = executeQuery(createProcessPhaseQuery(filter));
    	List<ProcessPhase> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getResource("processphase").getLocalName().replaceAll("_", " ");
    		result.add(new ProcessPhase(description));
    	}
    	return result;
    }
    
    public List<ProcessActivity> getProcessActivity(String filter){
    	ResultSet rs = executeQuery(createProcessActivityQuery(filter));
    	List<ProcessActivity> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getResource("processactivity").getLocalName();
    		List<Schedule> schedules = getSchedule(description, "Process");
    		List<String> tasks = getTask(description, "Process");
    		List<String> artifacts = getArtifact(description, "Process");
    		String role = "";
    		try {
        		role = qs.getResource("role").getLocalName().replaceAll("_", " ");
			} catch (Exception e) {}
    		description = description.replaceAll("_", " ");
    		result.add(new ProcessActivity(description,role,tasks,schedules,artifacts));
    	}
    	return result;
    }
    
    public List<ProjectActivity> getProjectActivity(String filter){
    	ResultSet rs = executeQuery(createProjectActivityQuery(filter));
    	List<ProjectActivity> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getResource("projectactivity").getLocalName();
    		List<Schedule> schedules = getSchedule(description, "Project");
    		List<String> tasks = getTask(description, "Project");
    		List<String> artifacts = getArtifact(description, "Project");
    		String role = "";
    		try {
        		role = qs.getResource("role").getLocalName().replaceAll("_", " ");
			} catch (Exception e) {}
    		description = description.replaceAll("_", " ");
    		result.add(new ProjectActivity(description,role,tasks,schedules,artifacts));
    	}
    	return result;
    }
    
    public List<String> getArtifact(String filter, String type){
    	ResultSet rs = executeQuery(createArtifactQuery(filter, type));
    	List<String> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getResource("artifact").getLocalName().replaceAll("_", " ");
    		result.add(description);
    	}
    	return result;
    }
    
    public List<String> getTask(String filter, String type){
    	ResultSet rs = executeQuery(createTaskQuery(filter, type));
    	List<String> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getResource("task").getLocalName().replaceAll("_", " ");
    		result.add(description);
    	}
    	return result;
    }
    
    public List<Schedule> getSchedule(String filter, String type){
    	ResultSet rs = executeQuery(createScheduleQuery(filter, type));
    	List<Schedule> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String description = qs.getLiteral("description").getString().replaceAll("_", " ");
    		String dateTime = qs.getLiteral("datetime").getString().replaceAll("_", " ");
    		result.add(new Schedule(dateTime, description));
    	}
    	return result;
    }
    
    public List<CasoProblemaDDS> getCasoProblemaDDS(){
    	StanLexParser parser = new StanLexParser("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
    	ResultSet rs = executeQuery(createCaseQuery());
    	List<CasoProblemaDDS> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String id = qs.getResource("challengeid").getLocalName();
    		String description = qs.getLiteral("challenge").getString().replaceAll("_", " ");
    		String bestPractice = qs.getLiteral("bestpractice").getString().replaceAll("_", " ");
    		String skill = qs.getResource("skill").getLocalName().toString().replaceAll("_", " ");
    		String effectivess = "";
    		try {
    			effectivess = qs.getLiteral("effectiveness").getString();
			} catch (Exception e) {
			}
    		CasoProblemaDDS cp = new CasoProblemaDDS(id, description, bestPractice, effectivess,skill);
    		cp = parser.extrairTokensParaCaso(cp);
            cp = parser.extrairDependenciasParaCaso(cp);
    		result.add(cp);
    	}
    	
    	return result;
    }
    
    public List<Resource> getResources(String project) {
    	ResultSet rs = executeQuery(createResourcesQuery(project));
    	List<Resource> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String resource = qs.getResource("resource").getLocalName().replaceAll("_", " ");
    		String description = qs.getLiteral("description").getString().replaceAll("_", " ");
    		
    		List<SimilaridadeCasos> solve = getChallenge("SupportToSolveChallenge",resource);
    		List<SimilaridadeCasos> avoid = getChallenge("SupportToAvoidChallenge",resource);
    		
    		result.add(new Resource(resource,description,solve,avoid));
    	}
    	return result;
	}
    
    public List<SimilaridadeCasos> getChallenge(String support, String resource){
    	ResultSet rs = executeQuery(createCaseResourceQuery(support, resource));
    	List<SimilaridadeCasos> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String id = qs.getResource("challengeid").getLocalName();
    		String description = qs.getLiteral("challenge").getString().replaceAll("_", " ");
    		String bestPractice = qs.getLiteral("bestpractice").getString().replaceAll("_", " ");
    		String skill = qs.getResource("skill").getLocalName().toString().replaceAll("_", " ");
    		String effectivess = "";
    		try {
    			effectivess = qs.getLiteral("effectiveness").getString();
			} catch (Exception e) {
			}
    		
    		result.add(new SimilaridadeCasos(new CasoProblemaDDS(id, description, bestPractice, effectivess, skill), null, 0, Double.parseDouble(effectivess), skill));
    	}
    	return result;
    }
    
    public List<CulturalPersonalCharacteristic> getCPC(String type, String member) {
    	ResultSet rs = executeQuery(createCPCQuery(type, member));
    	List<CulturalPersonalCharacteristic> result = new ArrayList<>();
    	while(rs.hasNext()){
    		QuerySolution qs = rs.next();
    		String id = qs.getResource("cpcid").getLocalName();
    		String description = qs.getLiteral("description").getString().replaceAll("_", " ");
    		
    		result.add(new CulturalPersonalCharacteristic(id, description));
    	}
    	return result;
	}
    
    private String createCPCQuery(String type, String member) {
    	return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "\n" +
                "SELECT ?cpcid ?description\n" +
                "WHERE {\n" +
                "	?cpcid a o:"+type+"\n" +
                "	. ?cpcid o:CharacteristicsDescription ?description\n" +
                "   . o:"+member+" o:HasCharacteristics ?cpcid\n" +
                "}";
	}
	private String createCaseResourceQuery(String support, String resource) {
    	return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#> \n" +
                "       SELECT ?challengeid ?challenge ?bestpractice ?effectiveness ?skill \n" +
                "       WHERE{\n" +
                "	{\n" +
                "	?challengeid a o:TechnicalChallenge\n" +
                "	. ?challengeid o:HasBestPractice ?bestpracticeid\n" +
                "	. ?challengeid o:ChallengeDescription ?challenge\n" +
                "	. ?bestpracticeid o:BestPracticeDescription ?bestpractice\n" +
                "	. o:"+resource+" o:"+support+" ?challengeid\n" +
                "	. ?bestpracticeid o:UseSkill ?skill\n" +
                "	OPTIONAL{?bestpracticeid o:HasEffectiveness ?effectivenessid . ?effectivenessid o:Value ?effectiveness}\n" +
                "	}\n" +
                "	UNION\n" +
                "	{\n" +
                "	?challengeid o:HasBestPractice ?bestpracticeid\n" +
                "	. ?challengeid a o:NonTechnicalChallenge\n" +
                "	. ?challengeid o:ChallengeDescription ?challenge\n" +
                "	. ?bestpracticeid o:BestPracticeDescription ?bestpractice\n" +
                "	. o:"+resource+" o:"+support+" ?challengeid\n" +
                "	. ?bestpracticeid o:UseSkill ?skill\n" +
                "	OPTIONAL{?bestpracticeid o:HasEffectiveness ?effectivenessid . ?effectivenessid o:Value ?effectiveness}\n" +
                "	}\n" +
                "}";
	}
	public static String createTechnicalChallengeQuery(){
        return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "\n" +
                "SELECT ?technicalid ?technical ?bestpractice\n" +
                "WHERE {\n" +
                "	?technicalid a o:TechnicalChallenge\n" +
                "	. ?technicalid o:ChallengeDescription ?technical\n" +
                "   . ?technicalid o:HasBestPractice ?bestpractice\n" +
                "}";
    }
    
    public static String createNonTechnicalChallengeQuery(){
        return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "\n" +
                "SELECT ?nontechnicalid ?nontechnical ?bestpractice\n" +
                "WHERE {\n" +
                "	?nontechnicalid a o:NonTechnicalChallenge\n" +
                "	. ?nontechnicalid o:ChallengeDescription ?nontechnical\n" +
                "   . ?nontechnicalid o:HasBestPractice ?bestpractice\n" +
                "}";
    }
    
    public static String createNonTechnicalBestPracticeQuery(){
        return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "\n" +
                "SELECT ?practiceid ?practice\n" +
                "WHERE {\n" +
                "	?practiceid a o:BestPractice\n" +
                "	. ?practiceid o:BestPracticeDescription ?practice\n" +
                "}";
    }
    
    public static String createMemberQuery(String[] keyWords){
        String filters = "";
        for(int i = 0; i < keyWords.length; i++){
            String keyWord = keyWords[i];
            if(!keyWord.equals("")){
                if(i == keyWords.length - 1){
                    filters = filters + "  regex(str(?skills), \""+keyWord+"\", \"i\" )\n";
                } else {
                    filters = filters + "  regex(str(?skills), \""+keyWord+"\", \"i\" ) || \n";
                }
            }
        }
        
        
        
        return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT distinct ?member ?city ?state ?region ?country ?email\n" +
                "                WHERE {?member o:HasSkills ?skills\n" +
                ". OPTIONAL{?member o:IsInAPlace ?city\n" +
                "             . ?city o:HasEstate ?state\n" +
                "             . ?state  o:IsInARegion ?region\n" +
                "             . OPTIONAL { ?country o:HasRegion ?region}\n" +
                "             . OPTIONAL { ?country o:HasEstate ?state}\n" +
                "}\n" +
                ". OPTIONAL {\n" +
                "             ?member o:HasEmail ?email\n" +
                "}\n FILTER(" +
                filters +
                ")}\n" +
                "ORDER BY ?member";
    }
    
    public static String createMemberQueryForProject(String team){
        
        return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT distinct ?member ?city ?state ?region ?country ?email\n" +
                "                WHERE {o:"+team+" o:HasMember ?member"
                + "					.?member o:HasSkills ?skills\n" +
                ". OPTIONAL{?member o:IsInAPlace ?city\n" +
                "             . ?city o:HasEstate ?state\n" +
                "             . ?state  o:IsInARegion ?region\n" +
                "             . OPTIONAL { ?country o:HasRegion ?region}\n" +
                "             . OPTIONAL { ?country o:HasEstate ?state}\n" +
                "}\n" +
                ". OPTIONAL {\n" +
                "             ?member o:HasEmail ?email\n" +
                "}}";
                
    }
    
    public static String createSkillsQuery(String member){
        return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?skills\n" +
                "WHERE { \n" +
                "o:"+member+" o:HasSkills ?skills\n" +
                "}";
    }
    
    public static String createCaseQuery(){
        return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#> \n" +
                "       SELECT ?challengeid ?challenge ?bestpractice ?effectiveness ?skill \n" +
                "       WHERE{\n" +
                "	{\n" +
                "	?challengeid o:HasBestPractice ?bestpracticeid\n" +
                "	. ?challengeid a o:TechnicalChallenge\n" +
                "	. ?challengeid o:ChallengeDescription ?challenge\n" +
                "	. ?bestpracticeid o:BestPracticeDescription ?bestpractice\n" +
                "	. ?bestpracticeid o:UseSkill ?skill\n" +
                "	OPTIONAL{?bestpracticeid o:HasEffectiveness ?effectivenessid . ?effectivenessid o:Value ?effectiveness}\n" +
                "	}\n" +
                "	UNION\n" +
                "	{\n" +
                "	?challengeid o:HasBestPractice ?bestpracticeid\n" +
                "	. ?challengeid a o:NonTechnicalChallenge\n" +
                "	. ?challengeid o:ChallengeDescription ?challenge\n" +
                "	. ?bestpracticeid o:BestPracticeDescription ?bestpractice\n" +
                "	. ?bestpracticeid o:UseSkill ?skill\n" +
                "	OPTIONAL{?bestpracticeid o:HasEffectiveness ?effectivenessid . ?effectivenessid o:Value ?effectiveness}\n" +
                "	}\n" +
                "}";
    }
    
    public static String createLanguagesQuery(String member){
        return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?language\n" +
                "WHERE { \n" +
                "o:"+member+" o:SpeakLanguage ?language\n" +
                "}";
    }
    
    public static String createProjectPhaseQuery(String project){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?projectphase\n" +
                "WHERE { \n" +
                "o:"+project+" o:HasProjectPhase ?projectphase\n" +
                "}";
    }
    public static String createProcessPhaseQuery(String project){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?processphase\n" +
                "WHERE { \n" +
                "o:"+project+" o:HasProcessPhase ?processphase\n" +
                "}";
    }
    
    public static String createProjectActivityQuery(String filter){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?projectactivity ?role\n" +
                "WHERE { \n" +
                "o:"+filter+" o:HasProjectActivity ?projectactivity\n" +
                ". OPTIONAL{?projectactivity o:ProjectHasRole ?role\n" +
                "}\n" +
                "}";
    }
    
    public static String createProcessActivityQuery(String filter){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?processactivity ?role\n" +
                "WHERE { \n" +
                "o:"+filter+" o:HasProcessActivity ?processactivity\n" +
                ". OPTIONAL{?processactivity o:ProcessHasRole ?role\n" +
                "}\n" +
                "}";
    }
    
    private static String createTaskQuery(String filter, String type){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?task\n" +
                "WHERE { \n" +
                "o:"+filter+" o:"+type+"HasTask ?task\n" +
                "}";
    }
    
    private static String createScheduleQuery(String filter, String type){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?description ?datetime\n" +
                "WHERE { \n" +
                "o:"+filter+" o:"+type+"HasSchedule ?id\n" +
                ". ?id o:ScheduleDescription ?description\n" +
                ". ?id o:DateTime ?datetime\n" +
                "}";
    }
    
    private static String createArtifactQuery(String filter, String type){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?artifact\n" +
                "WHERE { \n" +
                "o:"+filter+" o:"+type+"UseArtifact ?artifact\n" +
                "}";
    }
    
    public static String createTeamQuery(String project){
    	return "PREFIX o: <http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "SELECT ?team\n" +
                "WHERE { \n" +
                "o:"+project+" o:HasTeam ?team\n" +
                "}";
    }
    
    public static String createProjectQuery(){
    	return "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
                "\n" +
                "SELECT ?project ?methodology\n" +
                "WHERE {\n" +
                "	?project a o:Project\n" +
                "	. ?project o:UseMethodology ?methodology\n" +
                "}";
    }

	public static String createResourcesQuery(String description) {
		
		String[] array = {"AnalysisTool","CodingTool","CommunicationTool",
				"ArchitetureTool","PrototypingTool",
				"DocumentationTool","ManagementTool","StorageTool","TestTool"};
		
		String query = "PREFIX o:<http://www.semanticweb.org/rgcr/ontologies/2015/0/untitled-ontology-3#>\n" +
		                "\n" +
		                "SELECT ?resource ?description\n" +
		                "WHERE {\n";
		for(int i=0;i< array.length;i++){
			query += "{\n"+
					"?resource a o:"+array[i]+"\n" +
					". ?resource o:ResourcesDescription ?description\n" +
					". o:"+description+" o:ProjectUseResource ?resource\n" +
					"}\n";
			if(i != array.length-1){
				query += "UNION\n";
			}	
		}
		query += "}";
		return query;
	}

    /**
     * @return the pathOntology
     */
    public String getPathOntology() {
        return pathOntology;
    }

    /**
     * @param pathOntology the pathOntology to set
     */
    public void setPathOntology(String pathOntology) {
        this.pathOntology = pathOntology;
    }
	
}
