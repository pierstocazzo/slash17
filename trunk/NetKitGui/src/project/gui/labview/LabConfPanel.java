package project.gui.labview;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import project.core.AbstractProject;

public class LabConfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GTreePanel interfacesTab;
	GTreePanel routingTab;
	GTreePanel firewallingTab;
	
	AbstractProject project;
	
	JTabbedPane tab;
	
	GTreePanel netkitTree;
	
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
		
		netkitTree = new GTreePanel("Filesystem view", projName, GTreePanel.FILESYSTEM);
		
		add(netkitTree, -1);
	}
	
	public void update() {
		interfacesTab.update();
		routingTab.update();
		firewallingTab.update();
		netkitTree.update();
	}

	public void setProject( AbstractProject project ) {
		this.project = project;
	}
}

