package project.gui.labview;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import project.core.AbstractHost;
import project.core.AbstractInterface;
import project.core.AbstractProject;

public class LabConfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GTreePanel interfacesTab;
	GTreePanel routingTab;
	GTreePanel firewallingTab;
	
	AbstractProject project;
	
	JTabbedPane tab;
	
	GTreePanel labStructure;
	
	public LabConfPanel( AbstractProject project ) {
		super(new GridLayout(2,1));
		this.project = project;
		
		setBorder( new MatteBorder(0, 1, 0, 0, Color.lightGray));
		
		tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		this.project = project;
		
		String projName = "";
		if( project == null ) 
			projName = "noProject";
		else
			projName = project.getName();
		
		interfacesTab = new GTreePanel("Hosts interfaces", projName, GTreePanel.INTERFACES);
		
		routingTab = new GTreePanel("Hosts routing tables", projName, GTreePanel.ROUTING);
		
		firewallingTab = new GTreePanel("Firewalls", projName, GTreePanel.FIREWALLING);
	
		tab.addTab("Interfaces", interfacesTab);
		tab.addTab("Routing", routingTab);
		tab.addTab("Firewalling", firewallingTab);
		
		add(tab, -1);
		
		labStructure = new GTreePanel("Lab structure", projName, GTreePanel.LABSTRUCTURE);
		
		add(labStructure, -1);
	}
	
	public void update() {
		if( project == null ) 
			return;
		
		interfacesTab.clear();
		routingTab.clear();
		firewallingTab.clear();
		labStructure.clear();
		
		Collection<AbstractHost> hosts = project.getHosts();
		
		labStructure.addObject( "lab.conf", GTreeNode.FILE );
		
		for( AbstractHost host : hosts ) {
			// add a folder for each host
			GTreeNode node = interfacesTab.addObject( host.getName(), GTreeNode.FOLDER, host );
			// add his interfaces to each host
			for( AbstractInterface iface : host.getInterfaces() ) {
				interfacesTab.addObject( node, iface.getName() + " : " + iface.getCollisionDomain().getName(), GTreeNode.IFACE, host );
			}
			
			labStructure.addObject( host.getName(), GTreeNode.FOLDER );
			labStructure.addObject( host.getName() + ".startup", GTreeNode.FILE );
		}
		
		this.repaint();
	}

	public void setProject( AbstractProject project ) {
		this.project = project;
		interfacesTab.setName(project.getName());
		routingTab.setName(project.getName());
		firewallingTab.setName(project.getName());
		labStructure.setName(project.getName());
	}
	
	public void selectHost( String hostName ) {
		interfacesTab.selectHost(hostName);
	}
}

