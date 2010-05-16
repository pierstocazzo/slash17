package project.gui.labview;

import java.awt.Color;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import project.core.AbstractHost;
import project.core.AbstractInterface;
import project.core.AbstractRoute;
import project.gui.GuiManager;

public class FileDialog extends JDialog {
	private static final long serialVersionUID = 2855449568360456412L;

	AbstractHost host;
	String text;
	
	public FileDialog( AbstractHost host ) {
		super( (Frame) GuiManager.getInstance().getFrame(), "Bash script" );
		
		this.host = host;
		
		if( host == null ) {
			createLabConf();
		} else {
			createStartupFile();
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

	private void createStartupFile() {
		String hostName = host.getName();
		text = "# '" + hostName + ".startup' created by NetKit GUI\n\n";
		
		for( AbstractInterface iface : host.getInterfaces() ) {
			String ifaceName =  iface.getName();
			String ip = iface.getIp();
			String mask = iface.getMask();
			String bcast = iface.getBCast();
			
			if( ip != null && mask != null && bcast != null ) {
				text += "ifconfig " + ifaceName + " " + ip + " netmask " + 
								mask + " broadcast " + bcast + " up\n";
			} else {
				text += "ifconfig " + ifaceName + " up # not configured \n";
			}
		}
		text += "\n";
		
		for( AbstractRoute route : host.getRoutes() ) {
			String net = route.getNet();
			String gw = route.getGw();
			if( net != null && gw != null ) {
				text += "route add -net " + net + " gw " + gw + "\n";
			}
		}
	}

	private void createLabConf() {
		text = "# 'lab.conf' created by NetKit GUI\n\n";
		
		for( AbstractHost host : GuiManager.getInstance().getProject().getHosts() ) {
			String hostName = host.getName();
			
			for( AbstractInterface iface : host.getInterfaces() ) {
				String ifaceName =  iface.getName();
				String cdName = iface.getCollisionDomain().getName();
				
				text += hostName + "[" + ifaceName + "]=\"" + cdName + "\"\n";
			}
			text += "\n";
		}
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
