package project.gui.labview;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.core.nodes.AbstractHost;
import project.core.nodes.components.AbstractChain;
import project.core.nodes.components.AbstractRule;
import project.gui.GuiManager;

public class IptablesDialog extends JDialog {
	private static final long serialVersionUID = -4933993393385661003L;
	
	AbstractRule rule;
	AbstractHost host;
	AbstractChain chain;
	
	public IptablesDialog( final AbstractRule rule ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Adding Rule" );
		this.rule = rule;
		this.chain = rule.getChain();
		host = chain.getHost();
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		showDialog(null);
	}
	
	public IptablesDialog( final AbstractRule rule, String message ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Adding Rule" );
		this.rule = rule;
		this.chain = rule.getChain();
		host = chain.getHost();
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		showDialog(message);
	}	

	private void showDialog( String message ) {
		setLayout(new BorderLayout());
		
		String src = rule.getSource();
		String dst = rule.getDestination();
		String sport = rule.getSourcePort() + "";
		String dport = rule.getDestPort() + "";
		String in = rule.getInputIface();
		String out = rule.getOutputIface();
		String prot = rule.getProtocol();
		String target = rule.getTarget();
		String chname = chain.getName();
		
		final JLabel label;
		if( message == null ) 
			label = new JLabel("Add a rule to the " + chname + " chain of " + host.getName() );
		else 
			label = new JLabel(message + "\nAdd a rule to the " + chname + " chain of " + host.getName() );
		
		label.setFont( new Font("Serif", Font.BOLD, 14) );
		label.setBorder( BorderFactory.createEmptyBorder(5, 5, 5, 5) );
		add( label, BorderLayout.NORTH );
		
		JPanel panel = new JPanel( new GridBagLayout() );
		panel.setBorder( BorderFactory.createEmptyBorder(10, 10, 10, 10) );
		
		GridBagConstraints labelConstraint = new GridBagConstraints();
		labelConstraint.anchor = GridBagConstraints.EAST;
		labelConstraint.ipadx = 10;
		labelConstraint.ipady = 5;
		
		GridBagConstraints textFieldConstraint = new GridBagConstraints();
		textFieldConstraint.gridwidth = 3;
		textFieldConstraint.anchor = GridBagConstraints.EAST;

		JLabel icon = new JLabel(new ImageIcon("data/images/big/fw.png"));
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 0;
		labelConstraint.gridheight = 3;
		panel.add( icon, labelConstraint );
		labelConstraint.gridheight = 1;
		
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Protocol:"), labelConstraint );
		
		final JTextField protField = new JTextField(prot, 15);
		textFieldConstraint.gridy = 0;
		textFieldConstraint.gridx = 2; //fino a 5
		panel.add( protField, textFieldConstraint );
		
		labelConstraint.gridy = 1;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Source:"), labelConstraint );
		
		final JTextField srcField = new JTextField(src, 15);
		textFieldConstraint.gridy = 1;
		textFieldConstraint.gridx = 2;
		panel.add( srcField, textFieldConstraint );
		
		labelConstraint.gridy = 2;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Destination:"), labelConstraint );
		
		final JTextField dstField = new JTextField(dst, 15);
		textFieldConstraint.gridy = 2;
		textFieldConstraint.gridx = 2;
		panel.add( dstField, textFieldConstraint );
		
		labelConstraint.gridy = 3;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Source port:"), labelConstraint );
		
		final JTextField sportField = new JTextField(sport, 15);
		textFieldConstraint.gridy = 3;
		textFieldConstraint.gridx = 2;
		panel.add( sportField, textFieldConstraint );
		
		labelConstraint.gridy = 4;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Destionation port:"), labelConstraint );
		
		final JTextField dportField = new JTextField(dport, 15);
		textFieldConstraint.gridy = 4;
		textFieldConstraint.gridx = 2;
		panel.add( dportField, textFieldConstraint );
		
		labelConstraint.gridy = 5;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Input Iface::"), labelConstraint );
		
		final JTextField inField = new JTextField(in, 15);
		textFieldConstraint.gridy = 5;
		textFieldConstraint.gridx = 2;
		panel.add( inField, textFieldConstraint );
		
		labelConstraint.gridy = 6;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Output Iface:"), labelConstraint );
		
		final JTextField outField = new JTextField(out, 15);
		textFieldConstraint.gridy = 6;
		textFieldConstraint.gridx = 2;
		panel.add( outField, textFieldConstraint );
		
		labelConstraint.gridy = 7;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Target:"), labelConstraint );
		
		final JTextField targetField = new JTextField(target, 15);
		textFieldConstraint.gridy = 7;
		textFieldConstraint.gridx = 2;
		panel.add( targetField, textFieldConstraint );
		
		Dimension size = new Dimension(60,30);
		
		labelConstraint.gridy = 8;
		labelConstraint.gridx = 4;
		
		JPanel buttonPanel = new JPanel();
		
		JButton set = new JButton("Set");
		set.setPreferredSize(size);
		buttonPanel.add( set );
		
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(size);
		buttonPanel.add( cancel );
		
		panel.add( buttonPanel, labelConstraint );
		
		add(panel, BorderLayout.CENTER);
		
		
		set.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String prot = protField.getText();
				String src = srcField.getText();
				String dst = dstField.getText();
				String sport = sportField.getText();
				String dport = dportField.getText();
				String in = inField.getText();
				String out = outField.getText();
				String target = targetField.getText();
				
				rule.setSource(src);
				rule.setDestination(dst);
				rule.setSourcePort(Integer.parseInt(sport));
				rule.setDestPort(Integer.parseInt(dport));
				rule.setInputIface(in);
				rule.setOutputIface(out);
				rule.setProtocol(prot);
				rule.setTarget(target);
				
				dispose();
			}
		});
		
		cancel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		pack();
		// center the dialog
		setLocationRelativeTo(GuiManager.getInstance().getFrame());
		setVisible(true);
	}
}

