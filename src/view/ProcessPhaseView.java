package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.ProcessPhaseController;
import model.ProcessActivity;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class ProcessPhaseView extends JFrame {

	private JPanel contentPane;
	private JTable tProcessActivity;
	JLabel lprocess;
	private String processPhase;
	private ProcessPhaseView pcp;
	private int count = 0;
	private boolean pass = true;
	private ProcessPhaseController processPhaseController = new ProcessPhaseController();


	public ProcessPhaseView(String process) {
		this.processPhase = process;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 377, 486);
		setLocationRelativeTo(null);
		setTitle("Process Phase");
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblProcessPhase = new JLabel("Process Phase:");
		lblProcessPhase.setBounds(12, 11, 74, 14);
		contentPane.add(lblProcessPhase);
		
		lprocess = new JLabel(processPhase);
		lprocess.setBounds(90, 11, 604, 14);
		contentPane.add(lprocess);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Process Activity", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 36, 346, 400);
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		tProcessActivity = processPhaseController.createProcessActivityTable();
		scrollPane_1.setViewportView(tProcessActivity);
		tProcessActivity.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(pass){
					ProcessActivity pa = processPhaseController.getListProcessActivity().get(tProcessActivity.getSelectedRow());
					new DetailView(pa,"ProcessActivity");
					pass = false;
				}else{
					pass = true;
				}
			}
		});
		
		processPhaseController.fillProcessActivityTable(tProcessActivity, processPhase);
		
		
	}

	public String getProcessPhase() {
		return processPhase;
	}

	public void setProcessPhase(String processPhase) {
		this.processPhase = processPhase;
	}
	
	
}
