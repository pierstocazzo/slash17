package com.netedit.gui.nodes;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import edu.umd.cs.piccolo.util.PBounds;

/** Utility class to store node's informations for saving
 */
public class LabNode implements Serializable {
	private static final long serialVersionUID = 2877077749467465200L;

	Object absNode;
	
	int type;
	
	Rectangle2D bounds;
	
	Color color;
	
	String text;
	
	public LabNode( Rectangle2D bounds, int type, Object absNode ) {
		this.absNode = absNode;
		this.type = type;
		this.bounds = bounds;
	}
	
	public double getX() {
		return bounds.getCenterX();
	}

	public double getY() {
		return bounds.getCenterY();
	}
	
	public int getType() {
		return type;
	}

	public Object getAbsNode() {
		return absNode;
	}
	
	public boolean equals( LabNode node ) {
		return ( node.absNode == this.absNode );
	}

	public Rectangle2D getBounds() {
		return bounds;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void setBounds(PBounds bounds) {
		this.bounds = bounds;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}