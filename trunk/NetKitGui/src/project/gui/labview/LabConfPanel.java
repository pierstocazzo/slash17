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
		
		interfacesTab = new GTreePanel("Interfaces");
		interfacesTab.setIcons("data/images/24x24/nic_icon.png", "data/images/24x24/folder_icon.png");
		
		routingTab = new GTreePanel("Routers");
		routingTab.setIcons("data/images/24x24/router_icon.png", "data/images/24x24/folder_icon.png");
		
		firewallingTab = new GTreePanel("Firewalls");
		firewallingTab.setIcons("data/images/24x24/firewall_icon.png", "data/images/24x24/folder_icon.png");
	
		tab.addTab("Interfaces", interfacesTab);
		tab.addTab("Routing", routingTab);
		tab.addTab("Firewalling", firewallingTab);
		
		add(tab, -1);
		
		netkitTree = new GTreePanel("Filesystem view");
		netkitTree.setIcons("data/images/24x24/file_icon.png", "data/images/24x24/folder_icon.png");
		
		add(netkitTree, -1);
	}
	
	public void update() {
		interfacesTab.update();
		routingTab.update();
		firewallingTab.update();
		netkitTree.update();
	}
}

