package project.gui.labview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import project.core.AbstractHost;
import project.core.AbstractInterface;
import project.core.AbstractProject;
import project.gui.GuiManager;

public class LabConfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GTreePanel interfacesTab;
	GTreePanel routingTab;
	GTreePanel firewallingTab;
	
	AbstractProject project;
	
	JTabbedPane tab;
	
	GTreePanel labStructure;
	
	public LabConfPanel() {
		super(new GridLayout(1,0));
		
		setBorder( new MatteBorder(0, 1, 0, 0, Color.lightGray) );
		
		tab = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
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
		tab.setMinimumSize( new Dimension( 100, (int) (GuiManager.getInstance().getFrame().getSize().getHeight() / 2.3f) ) );
		
		labStructure = new GTreePanel("Lab structure", projName, GTreePanel.LABSTRUCTURE);
		labStructure.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, tab, labStructure);
		add(splitpane);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(220, 400);
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

