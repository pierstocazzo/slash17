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
		
		setBorder( new MatteBorder(0, 1, 0, 0, Color.lightGray));
		
		tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		this.project = project;
		
		interfacesTab = new GTreePanel("Hosts interfaces");
		
		routingTab = new GTreePanel("Hosts routing tables");
		
		firewallingTab = new GTreePanel("Firewalls");
	
		tab.addTab("Interfaces", interfacesTab);
		tab.addTab("Routing", routingTab);
		tab.addTab("Firewalling", firewallingTab);
		
		add(tab, -1);
		
		netkitTree = new GTreePanel("Filesystem view");
		
		add(netkitTree, -1);
	}
	
	public void update() {
		interfacesTab.update();
		routingTab.update();
		firewallingTab.update();
		netkitTree.update();
	}
}
