package project.gui.labview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import project.core.AbstractHost;

public class GTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -2967012607835738947L;

	public static final int IFACE = 0;
	public static final int RULE = 1;
	public static final int ROUTE = 2;
	public static final int FOLDER = 3;
	public static final int FILE = 4;
	public static final int MAINFOLDER = 5;
	
	private static final Icon ifaceIcon = new ImageIcon("data/images/16x16/nic_icon.png");
	private static final Icon folderIcon = new ImageIcon("data/images/16x16/folder_icon.png");
	private static final Icon mainfolderIcon = new ImageIcon("data/images/16x16/mainfolder_icon.png");
	private static final Icon routerIcon = new ImageIcon("data/images/16x16/router_icon.png");
	private static final Icon firewallIcon = new ImageIcon("data/images/16x16/firewall_icon.png");
	private static final Icon fileIcon = new ImageIcon("data/images/16x16/conffile_icon.png");

	int type;
	
	AbstractHost host;
	
	JPopupMenu menu;
	
	JTree tree;
	
	public GTreeNode( int type, JTree tree ) {
		this(null, type, tree);
	}
	
	public GTreeNode( Object obj, int type, JTree tree ) {
		this(obj, true, type, tree);
	}
	
	public GTreeNode( Object obj, boolean allowsChildren, int type, JTree tree ) {
		super(obj, allowsChildren);
		this.type = type;
		this.tree = tree;
		
		createPopupMenu();
	}
	
	private void createPopupMenu() {
		menu = new JPopupMenu();
		
		switch (type) {
		case IFACE:
			JMenuItem set = new JMenuItem("Set interface", new ImageIcon("data/images/16x16/configure_icon.png"));
		    set.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InterfaceDialog( host.getInterface( (String) getUserObject() ) );
				}
			});
		    menu.add(set);
		    break;
		    
		case FILE:
			JMenuItem view = new JMenuItem("View file", new ImageIcon("data/images/16x16/viewfile_icon.png"));
		    view.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new FileDialog(host);
				}
			});
		    menu.add(view);
			break;
			
		case RULE:
			//TODO popup menu rule
			break;
			
		case ROUTE:
			//TODO popup menu route
			break;
		}
	}

	public void setHost( AbstractHost host ) {
		this.host = host;
	}
	
	public void showMenu(MouseEvent e) {
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
	
	public Icon getIcon() {
		Icon icon = null;
		
		switch (type) {
		case IFACE:
			icon = ifaceIcon;
			break;
		case RULE:
			icon = firewallIcon;
			break;
		case ROUTE:
			icon = routerIcon;
			break;
		case FOLDER: 
			icon = folderIcon;
			break;
		case FILE: 
			icon = fileIcon;
			break;
		case MAINFOLDER:
			icon = mainfolderIcon;
			break;
		}
		
		return icon;
	}
	
	public int getType() {
		return type;
	}

	public void showConfDialog() {
		switch (type) {
		case IFACE:
			new InterfaceDialog( host.getInterface( (String) getUserObject() ) );
		    break;
		    
		case FILE:
			new FileDialog(host);
			break;
			
		case RULE:
			System.out.println("doppio click su una regola di firewalling");
			break;
			
		case ROUTE:
			System.out.println("doppio click su una route");
			break;
		}
	}
}
