package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.border.MatteBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import core.Project;

public class ConfPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JTabbedPane tabbedPane;
	JPanel interfacesTab;
	JPanel routingTab;
	JPanel firewallingTab;
	
	Project project;
	
	public ConfPanel( Project project ) {
		this.project = project;
		
		tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		  
		interfacesTab = new JPanel();
		routingTab = new JPanel();
		firewallingTab = new JPanel();
	
		interfacesTab.add(new JLabel("Interfaces"));
		interfacesTab.setPreferredSize(new Dimension(150, 230));
		routingTab.add(new JLabel("Routing"));
		firewallingTab.add(new JLabel("Firewalling"));
		
		tabbedPane.addTab("Interfaces", interfacesTab);
		tabbedPane.addTab("Routing", routingTab);
		tabbedPane.addTab("Firewalling", firewallingTab);
		
		createJTree( interfacesTab );
		
		add(tabbedPane);
		
		setBorder(new MatteBorder(0, 1, 0, 0, Color.lightGray));
	}

	private void createJTree(JPanel interfacesTab) {
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Interfaces");
		JTree tree = new JTree(topNode);
		tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);

		//Listen for when the selection changes.
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				System.out.println("changed");
			}
		});

		DefaultMutableTreeNode iface = new DefaultMutableTreeNode("eth0");
		topNode.add(iface);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(140, 200));
		interfacesTab.add(scrollPane);
	}
}
