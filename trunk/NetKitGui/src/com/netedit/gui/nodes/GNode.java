package com.netedit.gui.nodes;

import javax.swing.JPopupMenu;

import com.netedit.gui.Lab;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PText;

public class GNode extends PNode {
	private static final long serialVersionUID = 3165028185079487978L;

	public static final int host = 0;
	public static final int domain = 1;
	public static final int link = 2;
	public static final int area = 3;
	
	int type;
	
	PLayer layer;
	
	PText text;
	
	JPopupMenu menu;
	
	boolean selected;
	
	LabNode labNode;
	
	public GNode( int type, PLayer layer ) {
		this.type = type;
		this.layer = layer;
		
		selected = false;
		
		layer.addChild(this);
	}
	
	public void setText( String aText ) {
		text = new PText( aText );
		text.setPickable(false);
		layer.addChild(text);
	}
	
	public void update() {
		double x = getGlobalTranslation().getX();
		double y = getGlobalTranslation().getY();
		text.centerFullBoundsOnPoint(x + getWidth()/2, y + getHeight() + 10);
	}
	
	public void delete() {
		removeFromParent();
		if( text != null )
			text.removeFromParent();
		if( labNode != null )
			Lab.getInstance().removeNode(labNode);
	}
	
	public LabNode getLabNode() {
		return labNode;
	}
	
	public int getType() {
		return type;
	}

	public PLayer getLayer() {
		return layer;
	}

	public PText getText() {
		return text;
	}

	public JPopupMenu getMenu() {
		return menu;
	}
	
	public void setSelected( boolean selected ) {
		this.selected = selected;
	}
	
	public void showMenu( PInputEvent e ) {
		// to override
	}
	
	public void setMouseOver( boolean mouseOver ) {
		// to override
	}
	
	public void setConnecting( boolean connecting ) {
		// to override
	}
}
