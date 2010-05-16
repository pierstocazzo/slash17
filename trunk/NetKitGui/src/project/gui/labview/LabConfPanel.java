package project.gui.labview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import project.common.ItemType;
import project.core.AbstractHost;
import project.core.AbstractInterface;
import project.core.AbstractProject;

public class LabConfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GTree interfacesTree;
	GTree routingTree;
	GTree firewallingTree;
	
	AbstractProject project;
	
	JTabbedPane tab;
	
	GTree labStructure;
	
	public LabConfPanel() {
		super(new GridLayout(1,0));
		
		setBorder( new MatteBorder(0, 1, 0, 0, Color.lightGray) );
		
		tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		String projName = "";
		if( project == null ) 
			projName = "noProject";
		else
			projName = project.getName();
		
		interfacesTree = new GTree("Hosts interfaces", projName);
		routingTree = new GTree("Hosts routing tables", projName);
		firewallingTree = new GTree("Firewalls", projName);
	
		tab.addTab("Interfaces", interfacesTree);
		tab.addTab("Routing", routingTree);
		tab.addTab("Firewalling", firewallingTree);
		
		labStructure = new GTree("Lab structure", projName);
		labStructure.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, tab, labStructure);
		splitpane.setResizeWeight(0.5D);
		add(splitpane);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(220, 400);
	}
	
	public void update() {
		if( project == null ) 
			return;
		
		interfacesTree.clear();
		routingTree.clear();
		firewallingTree.clear();
		labStructure.clear();
		
		labStructure.addObject( "lab.conf", GTreeNode.FILE, null );
		
		for( AbstractHost host : project.getHosts() ) {
			// add a folder for each host in the interfaces tree
			GTreeNode node = interfacesTree.addObject( host.getName(), GTreeNode.FOLDER, host );
			// add his interfaces to each host
			for( AbstractInterface iface : host.getInterfaces() ) {
				interfacesTree.addObject( node, iface.getName() + " : " + iface.getCollisionDomain().getName(), GTreeNode.IFACE, host );
			}
			
			// add a folder for each host in the routing tree
			node = routingTree.addObject( host.getName(), GTreeNode.ROUTER, host );
			// TODO add his routes to each host
//			for( AbstractRoute route : host.getRoutes() ) {
//				routingTree.addObject( node, route.getName(), GTreeNode.ROUTE, host );
//			}
			
			// add a folder for each host in the firewalling tree
			if( host.getType() == ItemType.FIREWALL ) {
				node = firewallingTree.addObject( host.getName(), GTreeNode.FIREWALL, host );
				firewallingTree.addObject( node, "Input", GTreeNode.CHAIN, host );
				firewallingTree.addObject( node, "Output", GTreeNode.CHAIN, host );
				firewallingTree.addObject( node, "Forward", GTreeNode.CHAIN, host );
				// TODO add his fw rules to each host
//				for( AbstractChain chain : host.getChains() ) {
//					node = firewallingTree.addObject( node, chain.getName(), GTreeNode.CHAIN, host );
//					for( AbstractRule rule : chain.getRules() ) {
//						firewallingTree.addObject( node, rule.getName(), GTreeNode.RULE, host );
//					}
//				}
			}
			
			labStructure.addObject( host.getName(), GTreeNode.FOLDER, host );
			labStructure.addObject( host.getName() + ".startup", GTreeNode.FILE, host );
		}
		
		this.repaint();
	}

	public void setProject( AbstractProject project ) {
		this.project = project;
		interfacesTree.setName(project.getName());
		routingTree.setName(project.getName());
		firewallingTree.setName(project.getName());
		labStructure.setName(project.getName());
	}
	
	public void selectHost( String hostName ) {
		interfacesTree.selectHost(hostName);
	}
}

