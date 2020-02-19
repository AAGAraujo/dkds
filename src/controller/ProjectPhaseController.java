package controller;

import java.util.List;

import javax.swing.JTable;

import model.ProjectActivity;
import tableModel.ProcessPhaseTableModel;
import tableModel.ProjectActivityTableModel;
import util.OntologyQuery;

public class ProjectPhaseController {
	
	private ProjectActivityTableModel projectActivityTableModel;
	private ProcessPhaseTableModel processPhaseTableModel;
	private OntologyQuery ontologyQuery = new OntologyQuery();
	private List<ProjectActivity> listProjectActivity;
	
	
	public JTable createProjectActivityTable(){
    	JTable projectPhaseTable = new JTable(projectActivityTableModel);
    	return projectPhaseTable;
    }
    
    public JTable fillProjectActivityTable(JTable table, String projectPhase){
    	listProjectActivity = ontologyQuery.getProjectActivity(projectPhase.replaceAll(" ", "_"));
    	projectActivityTableModel = new ProjectActivityTableModel(listProjectActivity);
    	table.setModel(projectActivityTableModel);
    	return table;
    }
    
    public JTable createProcessPhaseTable(){
    	JTable processPhaseTable = new JTable(processPhaseTableModel);
    	return processPhaseTable;
    }
    
    public JTable fillProcessPhaseTable(JTable table, String processPhase){
    	processPhaseTableModel = new ProcessPhaseTableModel(ontologyQuery.getProcessPhase(processPhase.replaceAll(" ", "_")));
    	table.setModel(processPhaseTableModel);
    	return table;
    }

	public ProjectActivityTableModel getProjectActivityTableModel() {
		return projectActivityTableModel;
	}

	public void setProjectActivityTableModel(ProjectActivityTableModel projectActivityTableModel) {
		this.projectActivityTableModel = projectActivityTableModel;
	}

	public ProcessPhaseTableModel getProcessPhaseTableModel() {
		return processPhaseTableModel;
	}

	public void setProcessPhaseTableModel(ProcessPhaseTableModel processPhaseTableModel) {
		this.processPhaseTableModel = processPhaseTableModel;
	}

	public List<ProjectActivity> getListProjectActivity() {
		return listProjectActivity;
	}

	public void setListProjectActivity(List<ProjectActivity> listProjectActivity) {
		this.listProjectActivity = listProjectActivity;
	}
	
	

}
