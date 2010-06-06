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

package com.jnetedit.gui.dialogs;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jnetedit.core.nodes.AbstractHost;
import com.jnetedit.gui.GuiManager;


public class FileDialog extends JDialog {
	private static final long serialVersionUID = 2855449568360456412L;

	AbstractHost host;
	String text;
	
	public FileDialog( AbstractHost host ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Bash script" );
		
		this.host = host;
		
		if( host == null ) {
			text = GuiManager.getInstance().getProject().getLabConfFile();
		} else {
			text = host.getStartupFile();
		}
		
		JTextArea pane = new JTextArea(text);
		pane.setEditable(false);
		pane.setBackground(Color.white);
//		pane.setHighlighter(new DefaultHighlighter());
//		new ShHighlighter(pane).highlight();

		JScrollPane scrollPane = new JScrollPane(pane);
		add(scrollPane);
		
		setSize(550, 700);
		
		setLocationRelativeTo(GuiManager.getInstance().getFrame());
		setVisible(true);
	}
	
//	public class ShHighlighter {
//		
//		JTextComponent comp;
//		
//		String[] commands = {"IFCONFIG", "ROUTE", "IPTABLES"};
//		String[] keyword = {"ETH[0-3]", "^NETMASK$", "^BROADCAST$", "NET$", "^DEFAULT$", "^GW$"};
//		String ip = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}$";
//		String comment = "^#.\n";
//		
//		public ShHighlighter( JTextComponent comp ) {
//			this.comp = comp;
//		}
//		
//		public void highlight() {
//			// highlight all characters that appear in charsToHighlight
//			Highlighter h = comp.getHighlighter();
//			h.removeAllHighlights();
//			
//			String text = comp.getText().toUpperCase();
//			for ( int i = 0; i < text.length(); ) {
//				String s = "";
//				int start = i;
//				char ch = text.charAt(i);
//				while( ch != ' ' && ch != '\n' && i < text.length() - 1 ) {
//					s += ch;
//					ch = text.charAt(++i);
//				}
//				System.out.println(s);
//				int end = i++;
//				
//				for( String rx : commands ) {
//					if( s.matches(rx) ) {
//						try {
//							h.addHighlight(start, end, DefaultHighlighter.DefaultPainter);
//						} catch (BadLocationException ble) { }
//						break;
//					}
//				}
//				for( String rx : keyword ) {
//					if( s.matches(rx) ) {
//						try {
//							h.addHighlight(start, end, DefaultHighlighter.DefaultPainter);
//						} catch (BadLocationException ble) { }
//						break;
//					}
//				}
//				if( s.matches(ip) ) 
//					try {
//						h.addHighlight(start, end, DefaultHighlighter.DefaultPainter);
//					} catch (BadLocationException ble) { }
//				
//			}
//		}
//	}
}
