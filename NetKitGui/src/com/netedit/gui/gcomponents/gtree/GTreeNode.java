package com.netedit.gui.gcomponents.gtree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.components.AbstractChain;
import com.netedit.core.nodes.components.AbstractInterface;
import com.netedit.core.nodes.components.AbstractRoute;
import com.netedit.core.nodes.components.AbstractRule;
import com.netedit.gui.GuiManager;
import com.netedit.gui.dialogs.FileDialog;
import com.netedit.gui.dialogs.InterfaceDialog;
import com.netedit.gui.dialogs.IptablesDialog;
import com.netedit.gui.dialogs.RouteDialog;


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
		
		createNode(obj);
	}

	private void createNode( Object obj ) {
		menu = new JPopupMenu();
		
		JMenuItem view = new JMenuItem("View", new ImageIcon("data/images/16x16/viewfile_icon.png"));
	    view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), getInfo(), 
						"View..", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		switch (type) {
		case PROJECTFOLDER:
			setUserObject(obj);
			break;
			
		case IFACE:
			/* set the node */
			iface = (AbstractInterface) obj;
			// someting like "eth0 : cd1"
			setUserObject(iface.getName() + " : " + iface.getCollisionDomain().getName());
			
			/* create his popup menu */
			menu.add(view);
			JMenuItem configureIface = new JMenuItem("Configure", new ImageIcon("data/images/16x16/configure_icon.png"));
		    configureIface.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InterfaceDialog(iface);
				}
			});
		    if( iface.isConnectedToTap() ) {
		    	configureIface.setEnabled(false);
		    }
		    menu.add(configureIface);
		    break;
		    
		case FILE:
			/* set the node */
			if( obj instanceof AbstractHost ) {
				host = (AbstractHost) obj;
				setUserObject(host.getName() + ".startup");
			} else {
				setUserObject(obj);
			}
			/* create his popup menu */
			JMenuItem viewFile = new JMenuItem("View file", new ImageIcon("data/images/16x16/viewfile_icon.png"));
			viewFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new FileDialog(host);
				}
			});
		    menu.add(viewFile);
			break;
			
		case RULE:
			/* set the node */
			rule = (AbstractRule) obj;
			setUserObject(rule.getName());
			
			/* create his popup menu */
			menu.add(view);
			JMenuItem renameRule = new JMenuItem("Rename", new ImageIcon("data/images/16x16/text_icon.png"));
			renameRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String r = (String) JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), 
							"New name of this rule:", "Rename Rule", JOptionPane.PLAIN_MESSAGE, 
							new ImageIcon("data/images/big/fw.png"), null, rule.getName() );
					if( r != null && !r.equals("") ) {
						rule.setName(r);
						setUserObject(r);
					}
				}
			});
			menu.add(renameRule);
			JMenuItem editRule = new JMenuItem("Edit", new ImageIcon("data/images/16x16/configure_icon.png"));
			editRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new IptablesDialog(rule, false);
				}
			});
			menu.add(editRule);
			JMenuItem manualeditRule = new JMenuItem("Manual edit", new ImageIcon("data/images/16x16/configure_icon.png"));
			manualeditRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new IptablesDialog(rule, true);
				}
			});
			menu.add(manualeditRule);
			JMenuItem removeRule = new JMenuItem("Remove", new ImageIcon("data/images/16x16/remove_icon.png"));
			removeRule.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rule.delete();
					tree.removeCurrentNode();
				}
			});
			menu.add(removeRule);
			break;
			
		case ROUTE:
			/* set the node */
			route = (AbstractRoute) obj;
			setUserObject(route.getNet() + " gw " + route.getGw());
			
			/* create his popup menu */
			menu.add(view);
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
			/* set the node */
			host = (AbstractHost) obj;
			setUserObject(host.getName());
			/* create his popup menu */
			JMenuItem addRoute = new JMenuItem("Add route", new ImageIcon("data/images/16x16/add_icon.png"));
			addRoute.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addRoute();
				}
			});
		    menu.add(addRoute);
			break;
			
		case FIREWALL:
			/* set the node */
			host = (AbstractHost) obj;
			setUserObject(host.getName());
			
			/* create his popup menu */
			JMenuItem addChain = new JMenuItem("Add Chain", new ImageIcon("data/images/16x16/add_icon.png"));
			addChain.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addChain();
				}
			});
			menu.add(addChain);
			
			break;
			
		case CHAIN:
			/* set the node */
			chain = (AbstractChain) obj;
			setUserObject(chain.getName());
			
			/* create his popup menu */
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
			
		case FOLDER:
			/* set the node */
			host = (AbstractHost) obj;
			setUserObject(host.getName());
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

	public void viewNode() {
		switch (type) {
		case IFACE:
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), getInfo(), 
					"View..", JOptionPane.INFORMATION_MESSAGE);
		    break;
		    
		case FILE:
			new FileDialog(host);
			break;
			
		case RULE:
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), getInfo(), 
					"View..", JOptionPane.INFORMATION_MESSAGE);
			break;
			
		case ROUTE:
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), getInfo(), 
					"View..", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}

	public String getInfo() {
		String s = "";
		switch (type) {
		case IFACE:
			s = iface.getConfCommand();
		    break;
		case RULE:
			s = rule.getRule();
			break;
		case ROUTE:
			s = route.getConfCommand();
			break;
		default:
			s = (String) getUserObject();
			break;
		}
		return s;
	}
	
	public void goDown() {
		if( type == RULE ) {
			GTreeNode parent = (GTreeNode) this.getParent();
			GTreeNode next = (GTreeNode) parent.getChildAfter(this);
			if( next != null ) {
				rule.getChain().ruleDown(rule);
				int index = parent.getIndex(this);
				parent.remove(index);
				parent.remove(index);
				tree.getTreeModel().insertNodeInto(this, parent, index);
				tree.getTreeModel().insertNodeInto(next, parent, index);
				tree.getTreeModel().nodeStructureChanged(parent);
				tree.selectNode(this);
			}
		}
	}

	public void goUp() {
		if( type == RULE ) {
			GTreeNode parent = (GTreeNode) this.getParent();
			GTreeNode previous = (GTreeNode) parent.getChildBefore(this);
			if( previous != null ) {
				rule.getChain().ruleUp(rule);
				int previousIndex = parent.getIndex(previous);
				parent.remove(previousIndex);
				parent.remove(previousIndex);
				tree.getTreeModel().insertNodeInto(previous, parent, previousIndex);
				tree.getTreeModel().insertNodeInto(this, parent, previousIndex);
				tree.getTreeModel().nodeStructureChanged(parent);
				tree.selectNode(this);
			}
		}
	}
}
