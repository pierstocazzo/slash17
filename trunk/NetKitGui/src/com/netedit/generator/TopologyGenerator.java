package com.netedit.generator;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.netedit.core.Factory;
import com.netedit.gui.GuiManager;

public class TopologyGenerator {

	String number = "";
	String areasString = "";
	
	public static void start() {
		new TopologyGenerator();
	}
	
	public TopologyGenerator() {
		createDialog();
		
		LinkedList<String> areas = new LinkedList<String>();
		String[] areasStrings = areasString.split(" ");
		for( String area : areasStrings ) {
			areas.add(area);
		}
		
		if( number.equals("") || areasStrings.equals("") ) {
			return;
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the projects directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showDialog( GuiManager.getInstance().getFrame(), "Select" );
		
		if( choose == JFileChooser.APPROVE_OPTION ) {
			String directory = fc.getSelectedFile().getAbsolutePath();

			int n = Integer.parseInt(number);
			for( int i = 0; i < n; i++ ) {
				new Topology("proj" + i, directory, areas, new Factory() );
			}
			
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), number + " topologies generated in " + directory);
		} 
	}

	private void createDialog() {
		final JDialog dialog = new JDialog(GuiManager.getInstance().getFrame());
		dialog.setTitle("Topologies generation");
		dialog.setLayout(new BorderLayout());

		final JLabel label;
		label = new JLabel("Insert data: number of topologies and areas (at least 3)");
		
		label.setFont( new Font("Serif", Font.BOLD, 14) );
		label.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		dialog.add( label, BorderLayout.NORTH );
		
		JPanel panel = new JPanel( new GridBagLayout() );
		panel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
		
		GridBagConstraints labelConstraint = new GridBagConstraints();
		labelConstraint.anchor = GridBagConstraints.EAST;
		labelConstraint.ipadx = 10;
		labelConstraint.ipady = 5;
		
		GridBagConstraints textFieldConstraint = new GridBagConstraints();
		textFieldConstraint.gridwidth = 3;
		textFieldConstraint.anchor = GridBagConstraints.EAST;

		JLabel icon = new JLabel(new ImageIcon("data/images/big/route.png"));
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 0;
		labelConstraint.gridheight = 3;
		panel.add( icon, labelConstraint );
		labelConstraint.gridheight = 1;
		
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Number:"), labelConstraint );
		
		final JTextField numberField = new JTextField(number, 15);
		textFieldConstraint.gridy = 0;
		textFieldConstraint.gridx = 2; //fino a 5
		panel.add( numberField, textFieldConstraint );
		
		labelConstraint.gridy = 1;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Areas:"), labelConstraint );
		
		final JTextField areasField = new JTextField(areasString, 15);
		textFieldConstraint.gridy = 1;
		textFieldConstraint.gridx = 2;
		panel.add( areasField, textFieldConstraint );
		
		Dimension size = new Dimension(60,30);
		
		labelConstraint.gridy = 3;
		labelConstraint.gridx = 4;
		
		JPanel buttonPanel = new JPanel();
		
		JButton go = new JButton("Go");
		go.setPreferredSize(size);
		buttonPanel.add( go );
		
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(size);
		buttonPanel.add( cancel );
		
		panel.add( buttonPanel, labelConstraint );
		
		dialog.add(panel, BorderLayout.CENTER);
		
		
		go.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String n = numberField.getText();
				String areas = areasField.getText();
				
				if( !areas.matches("^((\\w|\\d)+ ){2}((\\w|\\d)+ )*(\\w|\\d)+") ||
					!n.matches("\\d+") ) {
					label.setText("Error: e.g. Number: 3 Areas: DMZ Green Red");
					dialog.repaint();
				} else {
					System.out.println("Generate " + n + " topologies. Areas: " + areas);
					number = n;
					areasString = areas;
					dialog.dispose();
				}
			}
		});
		
		cancel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		dialog.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		dialog.pack();
		dialog.setLocationRelativeTo(GuiManager.getInstance().getFrame());
		dialog.setVisible(true);
	}
}
