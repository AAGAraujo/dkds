package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import rbc.basic.CasoProblemaDDS;
import rbc.business.Fachada;
import rbc.business.RBC;
import rbc.business.SimilaridadeCasos;
import tableModel.SimilarityCasesTableModel;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Rbc extends JFrame {

	private JPanel contentPane;
	private JTable tableCases;
	private Fachada fachada;
	private List<SimilaridadeCasos> lista;
	private SimilaridadeCasos maisSimilar;
	private SimilarityCasesTableModel similarityCasesModel;
	private int count =0;

	public Rbc() {
		fachada = new Fachada("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
		setTitle("Search for Similar Cases");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnConfig = new JMenu("Config");
		menuBar.add(mnConfig);
		
		JMenuItem mntmAjustWeight = new JMenuItem("Weights Settings");
		mntmAjustWeight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WeightsSettingsView wsv = new WeightsSettingsView(fachada);
				wsv.setVisible(true);
			}
		});
		mnConfig.add(mntmAjustWeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(35, 24, 616, 602);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JCheckBox ckbMostSimilar = new JCheckBox("Search Only The Most Similar");
		ckbMostSimilar.setBounds(30, 30, 163, 23);
		panel_1.add(ckbMostSimilar);
		
		JLabel lblSimilarityMinimum = new JLabel("Similarity Minimum:");
		lblSimilarityMinimum.setBounds(392, 33, 103, 14);
		panel_1.add(lblSimilarityMinimum);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setMaximumRowCount(10);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"}));
		comboBox.setBounds(505, 30, 81, 20);
		panel_1.add(comboBox);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(30, 63, 81, 14);
		panel_1.add(lblDescription);
		
		JEditorPane edtDescription = new JEditorPane();
		edtDescription.setBounds(30, 88, 556, 469);
		panel_1.add(edtDescription);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(ckbMostSimilar.isSelected()){
					maisSimilar = fachada.buscarCasoMaisSimilar(new CasoProblemaDDS(edtDescription.getText()));
					new RBCShowChallengeView(maisSimilar).setVisible(true);
				}else{
					Double d = (1.0 + comboBox.getSelectedIndex()) / 10;
					lista = fachada.listarCasosSimilares(new CasoProblemaDDS(edtDescription.getText()), d);
					similarityCasesModel = new SimilarityCasesTableModel(lista);
					tableCases.setModel(similarityCasesModel);
					tableCases.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
						
						@Override
						public void valueChanged(ListSelectionEvent e) {
							if(tableCases.getSelectedRow()>=0){
								if(count == 0){
									new RBCShowChallengeView(lista.get(tableCases.getSelectedRow())).setVisible(true);
									count = 1;
								}else{
									count = 0;
								}
							}
						}
					});
				}
			}
		});
		btnSearch.setBounds(497, 568, 89, 23);
		panel_1.add(btnSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(661, 24, 583, 602);
		panel.add(scrollPane);
		
		tableCases = new JTable(similarityCasesModel);
		scrollPane.setViewportView(tableCases);
	}
}
