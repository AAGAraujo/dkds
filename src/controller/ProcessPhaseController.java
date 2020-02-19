package controller;

import java.util.List;

import javax.swing.JTable;

import model.ProcessActivity;
import tableModel.ProcessActivityTableModel;
import tableModel.ProcessPhaseTableModel;
import util.OntologyQuery;

public class ProcessPhaseController {
	
	private ProcessPhaseTableModel processPhaseTableModel;
	private ProcessActivityTableModel processActivityTableModel;
	private OntologyQuery ontologyQuery = new OntologyQuery();
	private List<ProcessActivity> listProcessActivity;
    
    public JTable createProcessActivityTable(){
    	JTable processActivityTable = new JTable(processActivityTableModel);
    	return processActivityTable;
    }
    
    public JTable fillProcessActivityTable(JTable table, String processPhase){
    	listProcessActivity = ontologyQuery.getProcessActivity(processPhase.replaceAll(" ", "_"));
    	processActivityTableModel = new ProcessActivityTableModel(listProcessActivity);
    	table.setModel(processActivityTableModel);
    	return table;
    }

	public ProcessPhaseTableModel getProcessPhaseTableModel() {
		return processPhaseTableModel;
	}

	public void setProcessPhaseTableModel(ProcessPhaseTableModel processPhaseTableModel) {
		this.processPhaseTableModel = processPhaseTableModel;
	}

	public List<ProcessActivity> getListProcessActivity() {
		return listProcessActivity;
	}

	public void setListProcessActivity(List<ProcessActivity> listProcessActivity) {
		this.listProcessActivity = listProcessActivity;
	}
	
	

}
