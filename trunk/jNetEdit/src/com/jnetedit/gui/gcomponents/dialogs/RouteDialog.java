/**
 * jNetEdit - Copyright (c) 2010 Salvatore Loria
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.jnetedit.gui.gcomponents.dialogs;

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

import com.jnetedit.common.IpAddress;
import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.core.nodes.components.AbstractRoute;
import com.jnetedit.gui.GuiManager;


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
				
				if( !net.matches(IpAddress.netRx) ) {
					label.setText("Not valid net address. E.g. 192.168.0.0/24");
					repaint();
				} else if( !gw.matches(IpAddress.ipRx) ) {
					label.setText("Not valid gateway ip address.");
					repaint();
				} else {
					route.setNet(net);
					route.setGw(gw);
					dispose();
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
