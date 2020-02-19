package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import util.OntologyQuery;
import view.RBCShowChallengeView;

import java.io.Serializable;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import model.*;
import rbc.basic.CasoProblemaDDS;
import rbc.business.SimilaridadeCasos;
import tableModel.ChallengeTableModel;
import tableModel.CulturalCharacteristicsTableModel;
import tableModel.LanguageTableModel;
import tableModel.MemberTableModel;
import tableModel.PersonalCharacteristicsTableModel;
import tableModel.ProjectPhaseTableModel;
import tableModel.SkillTableModel;

/**
 * esta classe é o controlador da tela index.xhtml
 * provê a comunicação com ela e com a ontologia.
 * @author Renan
 */

public class IndexController implements Serializable{
    
    
    private Project selectedProject;
    private List<Project> projects;
    private List<BestPractice> bestPractices;
    private List<Challenge> technicalChallenge;
    private List<Challenge> nonTechnicalChallenge;
    private List<Resource> resources;
    
    private MemberTableModel memberModel;
    private LanguageTableModel languageModel;
    private SkillTableModel skillModel;
    private ChallengeTableModel challengeTableModel;
    private ProjectPhaseTableModel projectPhaseTableModel;
    private CulturalCharacteristicsTableModel ccModel;
    private PersonalCharacteristicsTableModel pcModel;
    private DefaultListModel listResourcesModel;
    private DefaultListModel listSolveModel;
    private DefaultListModel listAvoidModel;
    
    private List<Member> members;
    private List<Team> teams;
    private Member selectedMember;
    private boolean count;
    List<CulturalPersonalCharacteristic> cc;
    List<CulturalPersonalCharacteristic> pc;
    
    
    private String search = "";
    private OntologyQuery ontologyQuery;
    
    public IndexController() {
         ontologyQuery = new OntologyQuery();
         
         technicalChallenge = ontologyQuery.getTechnicalChallenges(OntologyQuery.createTechnicalChallengeQuery(), "technical");
         
         nonTechnicalChallenge = ontologyQuery.getNonTechnicalChallenges(OntologyQuery.createNonTechnicalChallengeQuery(), "nontechnical");
         
         bestPractices = ontologyQuery.getNonTechnicalBestPractices(OntologyQuery.createNonTechnicalBestPracticeQuery(), "practice");
         
         projects = ontologyQuery.getProjects(OntologyQuery.createProjectQuery());
         
         count = true;
	}
    
    public List<BestPractice> getBestPractices() {
		return bestPractices;
	}

	public void setBestPractices(List<BestPractice> bestPractices) {
		this.bestPractices = bestPractices;
	}

	public List<Challenge> getTechnicalChallenge() {
		return technicalChallenge;
	}

	public void setTechnicalChallenge(List<Challenge> technicalChallenge) {
		this.technicalChallenge = technicalChallenge;
	}

	public List<Challenge> getNonTechnicalChallenge() {
		return nonTechnicalChallenge;
	}

	public void setNonTechnicalChallenge(List<Challenge> nonTechnicalChallenge) {
		this.nonTechnicalChallenge = nonTechnicalChallenge;
	}

	public OntologyQuery getOntologyQuery() {
		return ontologyQuery;
	}

	public void setOntologyQuery(OntologyQuery ontologyQuery) {
		this.ontologyQuery = ontologyQuery;
	}

	/**
     * este método é iniciado quando aperta o botão search no index.xhtml
     */
    public void actionSearch(){        
        
        String[] keyWords = search.split(" ");
        for(String keyWord : keyWords){
            keyWord = keyWord.replace(" ","");
        }
        
        setMembers(ontologyQuery.getMembers(OntologyQuery.createMemberQuery(keyWords)));
    }
    
    public void actionInsertProject(){
    
    }
    
    public void listProjects(){
    }
    
    public void setSelected(Member m){
        selectedMember = m;
        
        System.out.println(m.getName());
    }
    
    /**
     * @return the members
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }

    /**
     * @return the selectedMember
     */
    public Member getSelectedMember() {
        return selectedMember;
    }

    /**
     * @param selectedMember the selectedMember to set
     */
    public void setSelectedMember(Member selectedMember) {
        
        this.selectedMember = selectedMember;
    }

    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
    public JTable createTecnicalChallengeJtable(){
    	JTable challengeTable = new JTable(challengeTableModel);
    	fillChallenge(1,challengeTable);
    	return challengeTable;
    }
    
    public JTable createNonTecnicalChallengeJtable(){
    	JTable challengeTable = new JTable(challengeTableModel);
    	fillChallenge(0,challengeTable);
    	return challengeTable;
    }
    
    public JTable fillChallenge(int type, JTable challengeTable){
    	if (type == 0) {
    		challengeTableModel = new ChallengeTableModel(ontologyQuery.getNonTechnicalChallenges(OntologyQuery.createNonTechnicalChallengeQuery(), "nontechnical"));
		}else{
			challengeTableModel = new ChallengeTableModel(ontologyQuery.getTechnicalChallenges(OntologyQuery.createTechnicalChallengeQuery(), "technical"));	
		}
    	challengeTable.setModel(challengeTableModel);
    	return challengeTable;
    }
    
    
    public JTable createMemberJtable(){
    	JTable membersTable = new JTable(memberModel);
    	fillMember(membersTable);
    	return membersTable;
    }
    
    public JTable createLanguageJtable(){
    	JTable languageTable = new JTable(languageModel);
    	return languageTable;
    }
    
    public JTable fillLanguageTable(JTable languageTable, List<Language> languages){
    	languageModel = new LanguageTableModel(languages);
    	languageTable.setModel(languageModel);
    	return languageTable;
    }
    
    public JTable createSkillJtable(){
    	JTable skillTable = new JTable(skillModel);
    	return skillTable;
    }
    
    public JTable createProjectPhaseTable(){
    	JTable projectPhaseTable = new JTable(projectPhaseTableModel);
    	return projectPhaseTable;
    }
    
    public JTable fillProjectPhaseTable(JTable table, String project){
    	projectPhaseTableModel = new ProjectPhaseTableModel(ontologyQuery.getProjectPhase(project));
    	table.setModel(projectPhaseTableModel);
    	return table;
    }
    
    public JTable fillSkillTable(JTable skillTable, List<Skills> skills){
    	skillModel = new SkillTableModel(skills);
    	skillTable.setModel(skillModel);
    	return skillTable;
    }
    
	public ProjectPhaseTableModel getProjectPhaseTableModel() {
		return projectPhaseTableModel;
	}

	public void setProjectPhaseTableModel(ProjectPhaseTableModel projectPhaseTableModel) {
		this.projectPhaseTableModel = projectPhaseTableModel;
	}

	private JTable fillMember(JTable membersTable) {
		members = ontologyQuery.getMembers(OntologyQuery.createMemberQuery(new String[]{"a","e","i","o","u"}));
		memberModel = new MemberTableModel(members);
		membersTable.setModel(memberModel);
		return membersTable;
		
	}

	public String[] getProjectsDescriptions() {
		String[] descriptions = new String[getProjects().size()];
		int i=0;
		for(Project p : getProjects()){
			descriptions[i] = p.getDescription();
			i++;
		}
		return descriptions;
	}

	public String[] getTeamDescription(String description) {
		String project = description.replaceAll(" ", "_");
		teams = ontologyQuery.getTeams(OntologyQuery.createTeamQuery(project));
		String[] descriptions = new String[teams.size()];
		int i=0;
		for(Team t : getTeams()){
			descriptions[i] = t.getDescription();
			i++;
		}
		return descriptions;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public JList fillResources(JList listResources, String description) {
		resources = ontologyQuery.getResources(description);
		listResourcesModel = new DefaultListModel<>();
		for(Resource r : resources){
			listResourcesModel.addElement(r.getResource());
		}
		listResources.setModel(listResourcesModel);
		return listResources;
		
	}

	public JList fillAvoid(JList listAvoid, int i) {
		listAvoidModel = new DefaultListModel<>();
		for(SimilaridadeCasos c : resources.get(i).getSuportToAvoid()){
			listAvoidModel.addElement(c.getCaso1().getId());
		}
		listAvoid.setModel(listAvoidModel);
		return listAvoid;
		
	}
	public JList fillSolve(JList listSolve, int i) {
		listSolveModel = new DefaultListModel<>();
		for(SimilaridadeCasos c : resources.get(i).getSuportToSolve()){
			listSolveModel.addElement(c.getCaso1().getId());
		}
		listSolve.setModel(listSolveModel);
		return listSolve;
		
	}

	public void openChallengeAvoid(int challenge, int resource) {
		if(challenge >= 0)
			if(count){
				new RBCShowChallengeView(resources.get(resource).getSuportToAvoid().get(challenge)).setVisible(true);
				count = false;
			}else
				count = true;
				
		
	}
	public void openChallengeSolve(int challenge, int resource) {
		if(challenge >= 0)
			if(count){
				new RBCShowChallengeView(resources.get(resource).getSuportToSolve().get(challenge)).setVisible(true);
				count = false;
			}else
				count = true;
		
	}

	public JTable createCulturalCharacteristicsJtable() {
		JTable skillTable = new JTable(ccModel);
    	return skillTable;
	}
	public JTable createPersonalCharacteristicsJtable() {
		JTable skillTable = new JTable(pcModel);
    	return skillTable;
	}
	public JTable fillCulturalPersonalCharacteristicsJtable(JTable cpcTable, String type, String member) {
		if(type.equals("CulturalCharacteristics")){
			cc = ontologyQuery.getCPC(type, member);
			ccModel = new CulturalCharacteristicsTableModel(cc);
			cpcTable.setModel(ccModel);
		}else{
			pc = ontologyQuery.getCPC(type, member);
			pcModel = new PersonalCharacteristicsTableModel(pc);
			cpcTable.setModel(pcModel);
		}
		
		return cpcTable;
	}

	public List<CulturalPersonalCharacteristic> getCc() {
		return cc;
	}

	public void setCc(List<CulturalPersonalCharacteristic> cc) {
		this.cc = cc;
	}

	public List<CulturalPersonalCharacteristic> getPc() {
		return pc;
	}

	public void setPc(List<CulturalPersonalCharacteristic> pc) {
		this.pc = pc;
	}
	
}

