package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import rbc.business.Fachada;
import rbc.business.RBC;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WeightsSettingsView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public WeightsSettingsView(Fachada fachada) {
		setTitle("Weights Settings");
		setBounds(100, 100, 450, 214);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Weight to the number of equal words:");
		lblNewLabel.setBounds(10, 14, 183, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Weight to the number of words:");
		lblNewLabel_1.setBounds(10, 42, 183, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblWeightToThe = new JLabel("Weight to the number of equal verbs:");
		lblWeightToThe.setBounds(10, 70, 183, 14);
		contentPanel.add(lblWeightToThe);
		
		JLabel lblWeightToThe_1 = new JLabel("Weight to the number of similar dependecies:");
		lblWeightToThe_1.setBounds(10, 98, 217, 14);
		contentPanel.add(lblWeightToThe_1);
		
		JComboBox cbPalavrasIguais = new JComboBox();
		cbPalavrasIguais.setMaximumRowCount(9);
		cbPalavrasIguais.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbPalavrasIguais.setSelectedIndex(fachada.getRbc().getPesos()[0]-1);
		cbPalavrasIguais.setBounds(376, 11, 48, 20);
		contentPanel.add(cbPalavrasIguais);
		
		JComboBox cbQuantPalavras = new JComboBox();
		cbQuantPalavras.setMaximumRowCount(9);
		cbQuantPalavras.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbQuantPalavras.setSelectedIndex(fachada.getRbc().getPesos()[1]-1);
		cbQuantPalavras.setBounds(376, 39, 48, 20);
		contentPanel.add(cbQuantPalavras);
		
		JComboBox cbVerbosIguais = new JComboBox();
		cbVerbosIguais.setMaximumRowCount(9);
		cbVerbosIguais.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbVerbosIguais.setSelectedIndex(fachada.getRbc().getPesos()[2]-1);
		cbVerbosIguais.setBounds(376, 67, 48, 20);
		contentPanel.add(cbVerbosIguais);
		
		JComboBox cbDependenciasIguais = new JComboBox();
		cbDependenciasIguais.setMaximumRowCount(9);
		cbDependenciasIguais.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		cbDependenciasIguais.setSelectedIndex(fachada.getRbc().getPesos()[3]-1);
		cbDependenciasIguais.setBounds(376, 95, 48, 20);
		contentPanel.add(cbDependenciasIguais);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						fachada.getRbc().pesosSimilaridade(cbPalavrasIguais.getSelectedIndex()+1, cbQuantPalavras.getSelectedIndex()+1, cbVerbosIguais.getSelectedIndex()+1, cbDependenciasIguais.getSelectedIndex()+1);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
