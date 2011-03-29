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

import java.awt.Color;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.gui.GuiManager;


public class FileDialog extends JDialog {
	private static final long serialVersionUID = 2855449568360456412L;

	Object obj;
	String text;
	
	public FileDialog( Object obj ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Bash script" );
		
		this.obj = obj;
		this.text = "";
		
		if( obj instanceof AbstractHost ) {
			text = ((AbstractHost) obj).getStartupFile();
		} else {
			String s = (String) obj;
			if (s.matches(".*lab.conf.*"))
				text = GuiManager.getInstance().getProject().getLabConfFile();
			else if (s.matches(".*\\.(txt|sh|conf)") || !s.matches(".*\\w\\.\\w.*")) {
				try {
					BufferedReader b = new BufferedReader(new FileReader(s));
					String line;
					while ((line = b.readLine()) != null) {
						text += line + "\n";
					}
				} catch (Exception e) {}
			} else 
				text = "Cannot read file";
		}
		
		JTextArea pane = new JTextArea(text);
		pane.setEditable(false);
		pane.setBackground(Color.white);

		JScrollPane scrollPane = new JScrollPane(pane);
		add(scrollPane);
		
		setSize(550, 700);
		
		setLocationRelativeTo(GuiManager.getInstance().getFrame());
		setVisible(true);
	}
}
