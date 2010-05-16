package project.gui.labview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

import project.core.AbstractHost;
import project.gui.GuiManager;

public class GTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -2967012607835738947L;

	public static final int IFACE = 0;
	public static final int RULE = 1;
	public static final int ROUTE = 2;
	public static final int FOLDER = 3;
	public static final int FILE = 4;
	public static final int PROJECTFOLDER = 5;
	public static final int ROUTER = 6;
	public static final int FIREWALL = 7;
	public static final int CHAIN = 8;
	
	private static final Icon ifaceIcon = new ImageIcon("data/images/16x16/nic_icon.png");
	private static final Icon folderIcon = new ImageIcon("data/images/16x16/folder_icon.png");
	private static final Icon mainfolderIcon = new ImageIcon("data/images/16x16/mainfolder_icon.png");
	private static final Icon routeIcon = new ImageIcon("data/images/16x16/route_icon.png");
	private static final Icon ruleIcon = new ImageIcon("data/images/16x16/rule_icon.png");
	private static final Icon fileIcon = new ImageIcon("data/images/16x16/conffile_icon.png");

	int type;
	
	AbstractHost host;
	
	JPopupMenu menu;
	
	GTree tree;
	
	public GTreeNode( int type, GTree tree ) {
		this(null, type, tree);
	}
	
	public GTreeNode( Object obj, int type, GTree tree ) {
		this(obj, true, type, tree);
	}
	
	public GTreeNode( Object obj, boolean allowsChildren, int type, GTree tree ) {
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
			JMenuItem editRule = new JMenuItem("Set rule", new ImageIcon("data/images/16x16/configure_icon.png"));
			editRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			menu.add(editRule);
			JMenuItem removeRule = new JMenuItem("Remove rule", new ImageIcon("data/images/16x16/remove_icon.png"));
			removeRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tree.removeCurrentNode();
				}
			});
			menu.add(removeRule);
			break;
			
		case ROUTE:
			JMenuItem editRoute = new JMenuItem("Set route", new ImageIcon("data/images/16x16/configure_icon.png"));
			editRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}
			});
			menu.add(editRoute);
			JMenuItem removeRoute = new JMenuItem("Remove route", new ImageIcon("data/images/16x16/remove_icon.png"));
			removeRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tree.removeCurrentNode();
				}
			});
			menu.add(removeRoute);
			break;
			
		case ROUTER:
			JMenuItem addRoute = new JMenuItem("Add route", new ImageIcon("data/images/16x16/add_icon.png"));
			addRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addRoute();
				}
			});
		    menu.add(addRoute);
			break;
			
		case FIREWALL:
			JMenuItem addChain = new JMenuItem("Add Chain", new ImageIcon("data/images/16x16/add_icon.png"));
			addChain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addChain();
				}
			});
			menu.add(addChain);
			
			break;
			
		case CHAIN:
			JMenuItem addRule = new JMenuItem("Add rule", new ImageIcon("data/images/16x16/add_icon.png"));
			addRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addRule();
				}
			});
			menu.add(addRule);
			JMenuItem setPolicy = new JMenuItem("Set default policy", new ImageIcon("data/images/16x16/configure_icon.png"));
			setPolicy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
				}
			});
			menu.add(setPolicy);
			break;
		}
	}
	
	private void addRoute() {
		tree.addObject(this, "route", ROUTE, host);
		tree.repaint();
	}
	
	private void addChain() {
		String name = JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), "Chain name");
		tree.addObject(this, name, CHAIN, host);
		tree.repaint();
	}
	
	private void addRule() {
		tree.addObject(this, "rule", RULE, host);
		tree.repaint();
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
			icon = ruleIcon;
			break;
		case ROUTE:
			icon = routeIcon;
			break;
		case FOLDER: 
			icon = folderIcon;
			break;
		case FILE: 
			icon = fileIcon;
			break;
		case PROJECTFOLDER:
			icon = mainfolderIcon;
			break;
		case ROUTER: 
			icon = folderIcon;
			break;
		case FIREWALL:
			icon = folderIcon;
			break;
		case CHAIN:
			icon = folderIcon;
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
