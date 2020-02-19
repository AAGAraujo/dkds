package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.IndexController;
import model.BestPractice;
import model.Challenge;
import model.Member;
import model.Project;
import model.Resource;
import model.Team;
import tableModel.MemberTableModel;
import tableModel.PersonalCharacteristicsTableModel;

import javax.swing.JList;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;

public class Index {

	
	private JFrame frame;
	private JTable tableMember, tLanguage, tSkills, tProjectPhase, tCulturalCharacteristics, tPersonalCharacteristics;
	private static IndexController idxcontroller;
	private JLabel lbName, lbEmail, lbLocalization;
	private JEditorPane edtDescription;
	private JTable table_1;
	private JTable table_3;
	private JTable table_2;
	private JLabel lMethodology;
	private int count;
	private JComboBox cbTeams;
	private ProjectPhaseView pp;
	private ProcessPhaseView pcp;
	JTextArea taChallenge, taBestPratice;
	private JTable table_4;
	JList listResources, listAvoid, listSolve;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					idxcontroller = new IndexController();
					Index window = new Index();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Index() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(0, 0, 1280, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("DKDOnto Manager");
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmLoadOntology = new JMenuItem("Load Ontology");
		
		mnFile.add(mntmLoadOntology);
		
		JMenu mnExtra = new JMenu("Extra");
		menuBar.add(mnExtra);
		
		JMenuItem mntmRbc = new JMenuItem("RBC");
		mntmRbc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MensagemView msg = new MensagemView("Loading cases into memory");
				msg.setVisible(true);
				Rbc frame = new Rbc();
				frame.setVisible(true);
				msg.setVisible(false);
				msg = null;
			}
		});
		
		mnExtra.add(mntmRbc);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		JMenuItem mntmGuide = new JMenuItem("Guide");
		mnHelp.add(mntmGuide);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblProject = new JLabel("Project:");
		panel.add(lblProject);
		
		@SuppressWarnings("unchecked")
		JComboBox cbProjects = new JComboBox(idxcontroller.getProjectsDescriptions());
		cbProjects.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Project p = idxcontroller.getProjects().get(cbProjects.getSelectedIndex());
				lMethodology.setText(p.getMethodology());
				
				tableMember.clearSelection();
				cbTeams.removeAllItems();
				for(int i = 0; i< idxcontroller.getTeamDescription(p.getDescription()).length;i++){
					cbTeams.addItem(idxcontroller.getTeamDescription(p.getDescription())[i]);
				}
				cbTeams.setSelectedIndex(0);
				
				tProjectPhase.clearSelection();
				idxcontroller.fillProjectPhaseTable(tProjectPhase, p.getDescription().replaceAll(" ", "_"));
				
				listResources.clearSelection();
				idxcontroller.fillResources(listResources, p.getDescription().replaceAll(" ", "_"));
				listAvoid.setModel(new DefaultListModel<>());
				listSolve.setModel(new DefaultListModel<>());
				edtDescription.setText("");
			}
		});
		panel.add(cbProjects);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Overview", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Methodology Using:");
		label.setBounds(10, 11, 95, 14);
		panel_1.add(label);
		
		lMethodology = new JLabel("");
		lMethodology.setBounds(115, 11, 321, 14);
		panel_1.add(lMethodology);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Project Phase", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(10, 36, 441, 555);
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_5 = new JScrollPane();
		panel_4.add(scrollPane_5, BorderLayout.CENTER);
		
		tProjectPhase = idxcontroller.createProjectPhaseTable();
		tProjectPhase.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(tProjectPhase.getSelectedRow()>=0){
					if(count == 0){
						pp = new ProjectPhaseView(idxcontroller.getProjectPhaseTableModel().getProjectPhase(tProjectPhase.getSelectedRow()).getDescription());
						pp.setVisible(true);
						count = 1;
					}else{
						count = 0;
					}
				}
			}
		});
		scrollPane_5.setViewportView(tProjectPhase);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Resources", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(461, 36, 788, 555);
		panel_1.add(panel_5);
		panel_5.setLayout(null);
		
		JScrollPane scrollPane_6 = new JScrollPane();
		scrollPane_6.setBounds(10, 16, 299, 528);
		panel_5.add(scrollPane_6);
		
		listResources = new JList();
		scrollPane_6.setViewportView(listResources);
		listResources.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(listResources.getSelectedIndex() >= 0 ){

					Resource r = idxcontroller.getResources().get(listResources.getSelectedIndex());
									
					edtDescription.setText(String.valueOf(r.getDescription()));
					
					listAvoid.clearSelection();
					idxcontroller.fillAvoid(listAvoid, listResources.getSelectedIndex());
					listSolve.clearSelection();
					idxcontroller.fillSolve(listSolve, listResources.getSelectedIndex());
				}
			}
		});
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Detail", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_6.setBounds(319, 16, 459, 528);
		panel_5.add(panel_6);
		panel_6.setLayout(null);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(10, 22, 57, 14);
		panel_6.add(lblDescription);
		
		edtDescription = new JEditorPane();
		edtDescription.setBounds(10, 40, 439, 209);
		panel_6.add(edtDescription);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "Suport to Avoid Challenge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_7.setBounds(10, 260, 210, 257);
		panel_6.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_7 = new JScrollPane();
		panel_7.add(scrollPane_7, BorderLayout.CENTER);
		
		listAvoid = new JList();
		scrollPane_7.setViewportView(listAvoid);
		listAvoid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				idxcontroller.openChallengeAvoid(listAvoid.getSelectedIndex(), listResources.getSelectedIndex());
				
			}
		});
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "Suporte to Solve Challenge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_8.setBounds(239, 260, 210, 257);
		panel_6.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_8 = new JScrollPane();
		panel_8.add(scrollPane_8, BorderLayout.CENTER);
		
		listSolve = new JList();
		scrollPane_8.setViewportView(listSolve);
		listSolve.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				idxcontroller.openChallengeSolve(listSolve.getSelectedIndex(), listResources.getSelectedIndex());
				
			}
		});
		
		JPanel pnTeams = new JPanel();
		tabbedPane.addTab("Teams", null, pnTeams, null);
		pnTeams.setLayout(null);
		
		JLabel lblSelectTeam = new JLabel("Select Team:");
		lblSelectTeam.setBounds(10, 25, 67, 14);
		pnTeams.add(lblSelectTeam);
		
		cbTeams = new JComboBox();
		cbTeams.setBounds(87, 22, 391, 20);
		cbTeams.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(cbTeams.getItemCount() > 0){
					Team t = idxcontroller.getTeams().get(cbTeams.getSelectedIndex());
					tableMember.setModel(new MemberTableModel(t.getMembers()));
				}
				
				
			}
		});
		pnTeams.add(cbTeams);
		
		JPanel pnDetails = new JPanel();
		pnDetails.setBorder(new TitledBorder(null, "Details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnDetails.setBounds(643, 80, 606, 511);
		pnTeams.add(pnDetails);
		pnDetails.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 99, 289, 200);
		pnDetails.add(scrollPane_1);
		
		tLanguage = idxcontroller.createLanguageJtable();
		scrollPane_1.setViewportView(tLanguage);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(309, 99, 287, 200);
		pnDetails.add(scrollPane_2);
		
		tSkills = idxcontroller.createSkillJtable();
		scrollPane_2.setViewportView(tSkills);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(10, 24, 59, 14);
		pnDetails.add(lblName);
		
		lbName = new JLabel("New label");
		lbName.setBounds(79, 24, 517, 14);
		pnDetails.add(lbName);
		
		JLabel lblLocalization = new JLabel("Localization:");
		lblLocalization.setBounds(10, 49, 59, 14);
		pnDetails.add(lblLocalization);
		
		lbLocalization = new JLabel("New label");
		lbLocalization.setBounds(79, 49, 517, 14);
		pnDetails.add(lbLocalization);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(10, 74, 59, 14);
		pnDetails.add(lblEmail);
		
		lbEmail = new JLabel("New label");
		lbEmail.setBounds(79, 74, 517, 14);
		pnDetails.add(lbEmail);
		
		JScrollPane scrollPane_9 = new JScrollPane();
		scrollPane_9.setBounds(10, 310, 289, 190);
		pnDetails.add(scrollPane_9);
		
		tCulturalCharacteristics = idxcontroller.createCulturalCharacteristicsJtable();
		scrollPane_9.setViewportView(tCulturalCharacteristics);
		
		tCulturalCharacteristics.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				JOptionPane.showMessageDialog(null, idxcontroller.getCc().get(tCulturalCharacteristics.getSelectedRow()).getDescription());
			}
		});
		
		JScrollPane scrollPane_10 = new JScrollPane();
		scrollPane_10.setBounds(309, 310, 289, 190);
		pnDetails.add(scrollPane_10);
		
		tPersonalCharacteristics = idxcontroller.createPersonalCharacteristicsJtable();
		scrollPane_10.setViewportView(tPersonalCharacteristics);
		tPersonalCharacteristics.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				JOptionPane.showMessageDialog(null, idxcontroller.getPc().get(tPersonalCharacteristics.getSelectedRow()).getDescription());
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 80, 623, 511);
		pnTeams.add(scrollPane);
		
		tableMember = idxcontroller.createMemberJtable();
		tableMember.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(tableMember.getSelectedRow()>=0)
				loadMemberSelected(tableMember.getSelectedRow());
			}
		});
		scrollPane.setViewportView(tableMember);
		
		
		
		JPanel pnChallengesBestP = new JPanel();
		tabbedPane.addTab("Challenges/Best Pratices", null, pnChallengesBestP, null);
		pnChallengesBestP.setLayout(null);
		
		JPanel pnChallenges = new JPanel();
		pnChallenges.setBorder(new TitledBorder(null, "Challenges", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnChallenges.setBounds(10, 202, 1239, 389);
		pnChallengesBestP.add(pnChallenges);
		pnChallenges.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Technical", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 22, 608, 356);
		pnChallenges.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		panel_2.add(scrollPane_3, BorderLayout.CENTER);
		
		table_3 = idxcontroller.createTecnicalChallengeJtable();
		table_3.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				loadChallengeSelected(table_3.getSelectedRow(), 0);
			}
		});
		scrollPane_3.setViewportView(table_3);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Non-Technical", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(622, 22, 607, 356);
		pnChallenges.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_4 = new JScrollPane();
		panel_3.add(scrollPane_4, BorderLayout.CENTER);
		
		table_2 = idxcontroller.createNonTecnicalChallengeJtable();
		table_2.getSelectionModel().addListSelectionListener( new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				loadChallengeSelected(table_2.getSelectedRow(), 1);
			}
		});
		scrollPane_4.setViewportView(table_2);
		
		JPanel pnSelectedChallenge = new JPanel();
		pnSelectedChallenge.setBorder(new TitledBorder(null, "Selected Challenge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnSelectedChallenge.setBounds(10, 11, 617, 180);
		pnChallengesBestP.add(pnSelectedChallenge);
		pnSelectedChallenge.setLayout(null);
		
		taChallenge = new JTextArea();
		taChallenge.setLineWrap(true);
		taChallenge.setBackground(SystemColor.control);
		taChallenge.setEditable(false);
		taChallenge.setEnabled(false);
		taChallenge.setBounds(10, 21, 597, 148);
		pnSelectedChallenge.add(taChallenge);
		
		JPanel pnSelectedBestPratice = new JPanel();
		pnSelectedBestPratice.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selected Best Pratice", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnSelectedBestPratice.setBounds(632, 11, 617, 180);
		pnChallengesBestP.add(pnSelectedBestPratice);
		pnSelectedBestPratice.setLayout(null);
		
		taBestPratice = new JTextArea();
		taBestPratice.setLineWrap(true);
		taBestPratice.setEnabled(false);
		taBestPratice.setEditable(false);
		taBestPratice.setBackground(SystemColor.menu);
		taBestPratice.setBounds(10, 21, 597, 148);
		pnSelectedBestPratice.add(taBestPratice);
	}

	protected void loadMemberSelected(int selectedRow) {
		MemberTableModel mtm = (MemberTableModel) tableMember.getModel();
		Member m = mtm.getMember(tableMember.getSelectedRow());
		lbName.setText(m.getName());
		lbEmail.setText(m.getEmail());
		lbLocalization.setText(m.getLocalization());
		idxcontroller.fillLanguageTable(tLanguage, m.getLanguages());
		idxcontroller.fillSkillTable(tSkills, m.getSkills());
		idxcontroller.fillCulturalPersonalCharacteristicsJtable(tCulturalCharacteristics, "CulturalCharacteristics", m.getName().replaceAll(" ", "_"));
		idxcontroller.fillCulturalPersonalCharacteristicsJtable(tPersonalCharacteristics, "PersonalCharacteristics", m.getName().replaceAll(" ", "_"));
		
	}
	
	protected void loadChallengeSelected(int selectedRow, int type){
		Challenge c;
		if(type == 0){//Technical
			c = idxcontroller.getTechnicalChallenge().get(selectedRow);
		}else{//Non Technical
			c = idxcontroller.getNonTechnicalChallenge().get(selectedRow);
		}
		for (int i = 0; i < idxcontroller.getBestPractices().size(); i++) {
			if(c.getBestPractice().equals(idxcontroller.getBestPractices().get(i).getCod())){
				taChallenge.setText(c.getDescription());
				taBestPratice.setText(idxcontroller.getBestPractices().get(i).getDescription());
			}
		}
		
	}
}
