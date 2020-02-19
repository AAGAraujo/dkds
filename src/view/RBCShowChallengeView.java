package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import rbc.business.SimilaridadeCasos;
import tableModel.IndicateMemberTableModel;
import util.OntologyQuery;

import javax.swing.JLabel;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class RBCShowChallengeView extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tbMembroIndicado;

	public RBCShowChallengeView(SimilaridadeCasos sc) {
		setBounds(100, 100, 772, 412);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblChallenge = new JLabel("Challenge:");
			lblChallenge.setBounds(10, 30, 51, 14);
			contentPanel.add(lblChallenge);
		}
		
		JEditorPane epChallenge = new JEditorPane();
		epChallenge.setText(sc.getCaso1().getDescricao());
		epChallenge.setEditable(false);
		epChallenge.setBounds(10, 55, 476, 120);
		contentPanel.add(epChallenge);
		
		JLabel lblBestPractice = new JLabel("Best Practice:");
		lblBestPractice.setBounds(10, 186, 66, 14);
		contentPanel.add(lblBestPractice);
		
		JEditorPane epBestPractices = new JEditorPane();
		epBestPractices.setText(sc.getCaso1().getSolucao());
		epBestPractices.setEditable(false);
		epBestPractices.setBounds(10, 209, 476, 120);
		contentPanel.add(epBestPractices);
		
		JLabel label = new JLabel("Similarity:");
		label.setBounds(10, 5, 46, 14);
		contentPanel.add(label);
		
		JLabel lbSimilarity = new JLabel("");
		lbSimilarity.setText(String.valueOf(sc.getSimilaridade()));
		lbSimilarity.setBounds(66, 5, 174, 14);
		contentPanel.add(lbSimilarity);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Indicate Member", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(496, 55, 250, 274);
		contentPanel.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		OntologyQuery ontologyQuery = new OntologyQuery();
		IndicateMemberTableModel imt = new IndicateMemberTableModel(ontologyQuery.getMembersPerSkill("Excelent Oratory"));
		
		tbMembroIndicado = new JTable();
		scrollPane.setViewportView(tbMembroIndicado);
		tbMembroIndicado.setModel(imt);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
