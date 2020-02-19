package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import model.ProcessActivity;
import model.ProjectActivity;
import model.Schedule;
import tableModel.ArtifactsTableModel;
import tableModel.SchedulesTableModel;
import tableModel.TaskTableModel;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class DetailView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tbTasks;
	private JTable tbSchedules;
	private JTable tbArtifacts;

	/**
	 * Create the dialog.
	 */
	public DetailView(Object o, String type) {
		
		String description;
		String role;
		List<String> tasks;
		List<String> artifacts;
		List<Schedule> schedules;
		
		if(type.equals("ProcessActivity")){
			ProcessActivity pa = (ProcessActivity) o;
			description = pa.getDescription();
			role = pa.getRole();
			tasks = pa.getTask();
			artifacts = pa.getArtifacts();
			schedules = pa.getSchedule();
		}else{
			ProjectActivity pa = (ProjectActivity) o;
			description = pa.getDescription();
			role = pa.getRole();
			tasks = pa.getTask();
			artifacts = pa.getArtifacts();
			schedules = pa.getSchedule();
		}
		
		setBounds(100, 100, 450, 381);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		contentPanel.setLayout(null);
		setTitle("Detail");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Tasks", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(222, 173, 202, 125);
		contentPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		tbTasks = new JTable();
		scrollPane.setViewportView(tbTasks);
		TaskTableModel ttm = new TaskTableModel(tasks);
		tbTasks.setModel(ttm);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Schedules", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 61, 202, 237);
		contentPanel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1, BorderLayout.CENTER);
		
		tbSchedules = new JTable();
		scrollPane_1.setViewportView(tbSchedules);
		SchedulesTableModel stm = new SchedulesTableModel(schedules);
		tbSchedules.setModel(stm);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(10, 11, 57, 14);
		contentPanel.add(lblDescription);
		
		JLabel lbDescricao = new JLabel(description);
		lbDescricao.setBounds(77, 11, 347, 14);
		contentPanel.add(lbDescricao);
		
		JLabel lblRole = new JLabel("Role:");
		lblRole.setBounds(10, 36, 57, 14);
		contentPanel.add(lblRole);
		
		JLabel lbRole = new JLabel(role);
		lbRole.setBounds(77, 36, 347, 14);
		contentPanel.add(lbRole);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Artifacts", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(222, 61, 202, 101);
		contentPanel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_2.add(scrollPane_2, BorderLayout.CENTER);
		
		tbArtifacts = new JTable();
		scrollPane_2.setViewportView(tbArtifacts);
		ArtifactsTableModel atm = new ArtifactsTableModel(artifacts);
		tbArtifacts.setModel(atm);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		setVisible(true);
	}
}
