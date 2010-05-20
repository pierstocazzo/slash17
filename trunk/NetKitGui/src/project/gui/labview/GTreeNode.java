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

import project.core.AbstractChain;
import project.core.AbstractHost;
import project.core.AbstractInterface;
import project.core.AbstractRoute;
import project.core.AbstractRule;
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

	private int type;
	
	private AbstractHost host;
	private AbstractInterface iface;
	private AbstractChain chain;
	private AbstractRule rule;
	private AbstractRoute route;
	
	private JPopupMenu menu;
	
	private GTree tree;
	
	/**
	 * Create a tree node of this type
	 * 
	 * @param obj (Object) the object that is represented by this tree node
	 * @param type (int) the type of tree node (e.g. GTreeNode.FOLDER)
	 * @param tree (GTree) the tree that contains this node
	 */
	public GTreeNode( Object obj, int type, GTree tree ) {
		super();
		
		this.type = type;
		this.tree = tree;
		
		switch( type ) {
		case PROJECTFOLDER:
			setUserObject(obj);
			break;
			
		case FOLDER:
			host = (AbstractHost) obj;
			setUserObject(host.getName());
			break;
			
		case FILE:
			if( obj instanceof AbstractHost ) {
				host = (AbstractHost) obj;
				setUserObject(host.getName() + ".startup");
			} else {
				setUserObject(obj);
			}
			break;
			
		case IFACE:
			iface = (AbstractInterface) obj;
			// someting like "eth0 : cd1"
			setUserObject(iface.getName() + " : " + iface.getCollisionDomain().getName());
			break;
			
		case ROUTER:
			host = (AbstractHost) obj;
			setUserObject(host.getName());
			break;
			
		case ROUTE:
			route = (AbstractRoute) obj;
			setUserObject(route.getNet());
			break;
			
		case FIREWALL:
			host = (AbstractHost) obj;
			setUserObject(host.getName());
			break;
			
		case CHAIN:
			chain = (AbstractChain) obj;
			setUserObject(chain.getName());
			break;	
			
		case RULE:
			rule = (AbstractRule) obj;
			setUserObject(rule.getName());
			break;
		}
		
		createPopupMenu();
	}

	private void createPopupMenu() {
		menu = new JPopupMenu();
		
		switch (type) {
		case IFACE:
			JMenuItem set = new JMenuItem("Set interface", new ImageIcon("data/images/16x16/configure_icon.png"));
		    set.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InterfaceDialog(iface);
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
					new IptablesDialog(rule);
				}
			});
			menu.add(editRule);
			JMenuItem manualeditRule = new JMenuItem("Manual set", new ImageIcon("data/images/16x16/configure_icon.png"));
			manualeditRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String r = (String) JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), 
							"Write the iptables rule to add", "Iptables Rule", JOptionPane.PLAIN_MESSAGE, 
							new ImageIcon("data/images/big/fw.png"), null, rule.getRule() );
					if( r != null && !r.equals("") )
						rule.setRule(r);
				}
			});
			menu.add(manualeditRule);
			JMenuItem removeRule = new JMenuItem("Remove rule", new ImageIcon("data/images/16x16/remove_icon.png"));
			removeRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rule.delete();
					tree.removeCurrentNode();
				}
			});
			menu.add(removeRule);
			break;
			
		case ROUTE:
			JMenuItem editRoute = new JMenuItem("Set route", new ImageIcon("data/images/16x16/configure_icon.png"));
			editRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new RouteDialog( route );
				}
			});
			menu.add(editRoute);
			JMenuItem removeRoute = new JMenuItem("Remove route", new ImageIcon("data/images/16x16/remove_icon.png"));
			removeRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					route.delete();
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
			JMenuItem setPolicy = new JMenuItem("Default policy", new ImageIcon("data/images/16x16/configure_icon.png"));
			setPolicy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setPolicy();
				}
			});
			menu.add(setPolicy);
			JMenuItem deleteChain = new JMenuItem("Remove", new ImageIcon("data/images/16x16/delete_icon.png"));
			deleteChain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					chain.delete();
					tree.removeCurrentNode();
				}
			});
			menu.add(deleteChain);
			break;
		}
	}
	
	private void addRoute() {
		AbstractRoute route = host.addRoute();
		new RouteDialog(route);
		if( route.getNet() != null && route.getGw() != null )
			tree.addNode(this, route, ROUTE);
		tree.repaint();
	}
	
	private void addChain() {
		String name = JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), "Chain name");
		AbstractChain chain = host.addChain();
		chain.setName(name);
		tree.addNode(this, chain, CHAIN);
		tree.repaint();
	}
	
	private void addRule() {
		AbstractRule rule = chain.addRule();
		tree.addNode(this, rule, RULE);
		tree.repaint();
	}
	
	private void setPolicy() {
		String[] selection =  {"ACCEPT", "REJECT", "DROP"};
		String policy = (String) JOptionPane.showInputDialog( GuiManager.getInstance().getFrame(),
				"View/change the default policy of the " + chain.getName() + " chain", "Set default policy", 
				JOptionPane.QUESTION_MESSAGE, new ImageIcon("data/images/big/fw.png"), selection, chain.getPolicy() );
		if( policy != null )
			chain.setPolicy(policy);
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
			new InterfaceDialog( iface );
		    break;
		    
		case FILE:
			new FileDialog(host);
			break;
			
		case RULE:
			new IptablesDialog(rule);
			break;
			
		case ROUTE:
			new RouteDialog( route );
			break;
		}
	}
}
