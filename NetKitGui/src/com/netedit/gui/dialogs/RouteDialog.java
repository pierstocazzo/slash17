package com.netedit.gui.dialogs;

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

import com.netedit.common.IpAddress;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.components.AbstractRoute;
import com.netedit.gui.GuiManager;


public class RouteDialog extends JDialog {
	private static final long serialVersionUID = -4280244546799009674L;
	
	AbstractRoute route;
	AbstractHost host;
	
	public RouteDialog( final AbstractRoute route ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Adding Route" );
		this.route = route;
		host = route.getHost();
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		showDialog(null);
	}
	
	public RouteDialog( final AbstractRoute route, String message ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Adding Route" );
		this.route = route;
		host = route.getHost();
		this.setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		showDialog(message);
	}	

	private void showDialog( String message ) {
		setLayout(new BorderLayout());
		
		String net = route.getNet();
		String gw = route.getGw();
		
		if( net == null || gw == null ) {
			net = "";
			gw = "";
		}
		
		final JLabel label;
		if( message == null ) 
			label = new JLabel("Add a routing table row to " + host.getName() );
		else 
			label = new JLabel(message + "\nAdd a routing table row to " + host.getName() );
		
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

		JLabel icon = new JLabel(new ImageIcon("data/images/big/route.png"));
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 0;
		labelConstraint.gridheight = 3;
		panel.add( icon, labelConstraint );
		labelConstraint.gridheight = 1;
		
		labelConstraint.gridy = 0;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Net:"), labelConstraint );
		
		final JTextField netField = new JTextField(net, 15);
		textFieldConstraint.gridy = 0;
		textFieldConstraint.gridx = 2; //fino a 5
		panel.add( netField, textFieldConstraint );
		
		labelConstraint.gridy = 1;
		labelConstraint.gridx = 1;
		panel.add( new JLabel("Gateway:"), labelConstraint );
		
		final JTextField gwField = new JTextField(gw, 15);
		textFieldConstraint.gridy = 1;
		textFieldConstraint.gridx = 2;
		panel.add( gwField, textFieldConstraint );
		
		Dimension size = new Dimension(60,30);
		
		labelConstraint.gridy = 3;
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
				String net = netField.getText();
				String gw = gwField.getText();
				
				if( net.matches(IpAddress.netRx) && gw.matches(IpAddress.ipRx) ) {
					route.setNet(net);
					route.setGw(gw);
					dispose();
				} else {
					label.setText("Format incorrect. Correct example: 10.0.0.0/8");
					repaint();
				}
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
