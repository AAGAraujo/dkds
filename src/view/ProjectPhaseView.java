package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.ProjectPhaseController;
import model.ProjectActivity;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class ProjectPhaseView extends JFrame {

	private JPanel contentPane;
	private JTable tProcessPhase, tProjectActivity;
	private String projectPhase;
	private ProcessPhaseView processPhaseView;
	private int count =0;
	private boolean pass = true;
	private ProjectPhaseController projectPhaseController = new ProjectPhaseController();


	public ProjectPhaseView(String projectPhase) {
		setTitle("Project Phase");
		this.projectPhase = projectPhase;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 961, 559);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProjectPhase = new JLabel("Project Phase:");
		lblProjectPhase.setBounds(10, 11, 70, 14);
		contentPane.add(lblProjectPhase);
		
		JLabel label = new JLabel(projectPhase);
		label.setBounds(94, 11, 459, 14);
		contentPane.add(label);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Process Phase", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 36, 464, 476);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		tProcessPhase = projectPhaseController.createProcessPhaseTable();
		scrollPane.setViewportView(tProcessPhase);
		tProcessPhase.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(tProcessPhase.getSelectedRow()>=0){
					if(count == 0){
						processPhaseView = new ProcessPhaseView(projectPhaseController.getProcessPhaseTableModel().getProcessPhase(tProcessPhase.getSelectedRow()).getDescription());
						processPhaseView.setVisible(true);
						count = 1;
					}else{
						count = 0;
					}
				}
				
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Project Activity", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(484, 36, 452, 476);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		tProjectActivity = projectPhaseController.createProjectActivityTable();
		scrollPane_1.setViewportView(tProjectActivity);
		
		tProjectActivity.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(pass){
					ProjectActivity pa = projectPhaseController.getListProjectActivity().get(tProjectActivity.getSelectedRow());
					new DetailView(pa, "ProjectActivity");
					pass = false;
				}else{
					pass = true;
				}
			}
		});
				
		projectPhaseController.fillProcessPhaseTable(tProcessPhase, projectPhase);
		projectPhaseController.fillProjectActivityTable(tProjectActivity, projectPhase);
		
		
	}

	public String getProjectPhase() {
		return projectPhase;
	}

	public void setProjectPhase(String projectPhase) {
		this.projectPhase = projectPhase;
	}
	
	
}
