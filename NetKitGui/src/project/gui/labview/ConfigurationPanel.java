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
import project.core.nodes.AbstractHost;
import project.core.nodes.components.AbstractChain;
import project.core.nodes.components.AbstractInterface;
import project.core.nodes.components.AbstractRoute;
import project.core.nodes.components.AbstractRule;
import project.core.project.AbstractProject;

public class ConfigurationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GTree interfacesTree;
	GTree routingTree;
	GTree firewallingTree;
	
	AbstractProject project;
	
	JTabbedPane tab;
	
	GTree labStructure;
	
	public ConfigurationPanel() {
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
		return new Dimension(250, 400);
	}
	
	public void update() {
		if( project == null ) 
			return;
		
		interfacesTree.clear();
		routingTree.clear();
		firewallingTree.clear();
		labStructure.clear();
		
		labStructure.addNode( "lab.conf", GTreeNode.FILE );
		
		for( AbstractHost host : project.getHosts() ) {
			// add a folder for each host in the interfaces tree
			GTreeNode node = interfacesTree.addNode( host, GTreeNode.FOLDER );
			// add his interfaces to each host
			for( AbstractInterface iface : host.getInterfaces() ) {
				interfacesTree.addNode( node, iface, GTreeNode.IFACE );
			}
			
			// add a folder for each host in the routing tree
			node = routingTree.addNode( host, GTreeNode.ROUTER );
			// add the routes
			for( AbstractRoute route : host.getRoutes() ) {
				routingTree.addNode( node, route, GTreeNode.ROUTE );
			}
			
			// add a folder for each firewall in the firewalling tree
			if( host.getType() == ItemType.FIREWALL ) {
				node = firewallingTree.addNode( host, GTreeNode.FIREWALL );
				// add his fw rules to each firewall
				for( AbstractChain chain : host.getChains() ) {
					GTreeNode child = firewallingTree.addNode( node, chain, GTreeNode.CHAIN );
					for( AbstractRule rule : chain.getRules() ) {
						firewallingTree.addNode( child, rule, GTreeNode.RULE );
					}
				}
			}
			
			labStructure.addNode( host, GTreeNode.FOLDER );
			labStructure.addNode( host, GTreeNode.FILE );
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
		interfacesTree.expandNode(hostName);
		routingTree.expandNode(hostName);
		firewallingTree.expandNode(hostName);
	}
}

