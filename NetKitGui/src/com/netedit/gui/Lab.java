package com.netedit.gui;

import java.io.Serializable;
import java.util.ArrayList;

import com.netedit.core.project.AbstractProject;
import com.netedit.gui.nodes.LabNode;

public class Lab implements Serializable {
	private static final long serialVersionUID = -3935966292120774295L;

	private AbstractProject project;
	
	private ArrayList<LabNode> nodes;
	
	private static Lab lab;
	
	private Lab() {
		nodes = new ArrayList<LabNode>();
	}
	
	public static Lab getInstance() {
		if( lab == null ) {
			lab = new Lab();
		}
		return lab;
	}
	
	public static void setInstance( Lab newLab ) {
		lab = newLab;
	}
	
	public void clear() {
		nodes.clear();
	}
	
	public void setProject( AbstractProject project ) {
		this.project = project;
	}
	
	public AbstractProject getProject() {
		return project;
	}
	
	public ArrayList<LabNode> getNodes() {
		return nodes;
	}
	
	public void addNode( LabNode node ) {
		int size = nodes.size();
		int i;
		for( i = 0; i < size; i++ ) {
			if( nodes.get(i).equals(node) ) {
				nodes.remove(i);
				break;
			}
		}
		nodes.add(i, node);
	}
	
	public void removeNode( LabNode node ) {
		nodes.remove(node);
	}
	
	public void removeNode( Object absNode ) {
		for( LabNode node : nodes ) {
			if( node.getAbsNode() == absNode ) {
				nodes.remove(node);
				return;
			}
		}
	}
}
