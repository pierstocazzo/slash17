package project.gui.netconf;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import project.core.AbstractProject;

public class LabConfPanel extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	GTree interfacesTab;
	GTree routingTab;
	GTree firewallingTab;
	AbstractProject project;
	
	public LabConfPanel( AbstractProject project ) {
		super(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		this.project = project;
		
		JPanel interfacePanel = new JPanel(new BorderLayout());
		JLabel interfaceLabel = new JLabel("Interfaces list");
		interfaceLabel.setBorder( new EmptyBorder(5, 5, 5, 5) );
		interfacePanel.add( interfaceLabel, BorderLayout.NORTH );
		interfacesTab = new GTree("Interfaces");
		interfacesTab.setLeafIcon("data/images/icon/nic_icon.png");
		interfacePanel.add(interfacesTab);
		
		JPanel routingPanel = new JPanel(new BorderLayout());
		JLabel routingLabel = new JLabel("Routers list");
		routingLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		routingPanel.add( routingLabel, BorderLayout.NORTH );
		routingTab = new GTree("Routers");
		routingTab.setLeafIcon("data/images/icon/router_icon.png");
		routingPanel.add(routingTab);
		
		JPanel firewallPanel = new JPanel(new BorderLayout());
		JLabel firewallLabel = new JLabel("Firewalls list");
		firewallLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
		firewallPanel.add( firewallLabel, BorderLayout.NORTH );
		firewallingTab = new GTree("Firewalls");
		firewallingTab.setLeafIcon("data/images/icon/firewall_icon.png");
		firewallPanel.add(firewallingTab);
	
		addTab("Interfaces", interfacePanel);
		addTab("Routing", routingPanel);
		addTab("Firewalling", firewallPanel);
	}
	
	public void update() {
		interfacesTab.update();
		routingTab.update();
		firewallingTab.update();
	}
}
