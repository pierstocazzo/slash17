package project.gui.netconf;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

import project.core.Project;

public class ConfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JTabbedPane tabbedPane;
	JPanel interfacesTab;
	JPanel routingTab;
	JPanel firewallingTab;
	
	public ConfPanel( Project project ) {
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		  
		interfacesTab = new DynamicTree();
		routingTab = new JPanel();
		firewallingTab = new JPanel();
	
		interfacesTab.setPreferredSize(new Dimension(150, 230));
		routingTab.add(new JLabel("Routing"));
		firewallingTab.add(new JLabel("Firewalling"));
		
		tabbedPane.addTab("Interfaces", interfacesTab);
		tabbedPane.addTab("Routing", routingTab);
		tabbedPane.addTab("Firewalling", firewallingTab);
		
		add(tabbedPane);
		
		setBorder(new MatteBorder(0, 1, 0, 0, Color.lightGray));
	}
	
	public DynamicTree getInterfaceTree() {
		return (DynamicTree) interfacesTab;
	}
}
