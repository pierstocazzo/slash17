package com.netedit.gui.nodes;

import java.io.Serializable;

/** Utility class to store node's informations for saving
 */
public class LabNode implements Serializable {
	private static final long serialVersionUID = 2877077749467465200L;

	double x, y;
	
	Object absNode;
	
	int type;
	
	public LabNode( double x, double y, int type, Object absNode ) {
		this.x = x;
		this.y = y;
		this.absNode = absNode;
		this.type = type;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public int getType() {
		return type;
	}

	public Object getAbsNode() {
		return absNode;
	}
	
	public boolean equals( LabNode node ) {
		return ( node.x == this.x && node.y == this.y && node.absNode == this.absNode && node.type == this.type );
	}
}