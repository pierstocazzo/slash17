package project.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

import project.core.AbstractLink;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

public class GLink extends GNode {
	private static final long serialVersionUID = 1L;
	
	GHost host;
	GCollisionDomain collisionDomain;
	
	AbstractLink absLink;
	
	PPath link;
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param host
	 * @param abstractLink 
	 * @param collisionDomain2
	 */
	public GLink( GHost host, GCollisionDomain collisionDomain, AbstractLink abstractLink, PLayer layer ) {
		super( GNode.link, layer );
		
		this.host = host;
		this.collisionDomain = collisionDomain;
		this.absLink = abstractLink;
		this.layer = layer;
		
		link = PPath.createLine(0, 0, 0, 0);
		addChild(link);
		setBounds(link.getBounds());
		link.setPickable(false);
		
		setText(absLink.getInterface().getName());
		
		update();
	}

	public void update() {
		Point2D start = host.getFullBoundsReference().getCenter2D();
		Point2D end = collisionDomain.getFullBoundsReference().getCenter2D();
		link.reset();
		link.moveTo((float)start.getX(), (float)start.getY());
		link.lineTo((float)end.getX(), (float)end.getY());
		setBounds(link.getBounds());
		if( text != null ) 
			text.centerFullBoundsOnPoint( link.getBounds().getCenterX(), link.getBounds().getCenterY() + 10 );
	}
	
	public void setText( String aText ) {
		if( text != null ) {
			text.removeFromParent();
		}
		text = new PText(aText);
		text.setPickable(false);
		text.setFont(new Font("", Font.BOLD, 14));
		layer.addChild(text);
		update();
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if( selected )
			link.setPaint(Color.red);
		else
			link.setPaint(Color.black);
	}
	
	@Override
	public void setMouseOver(boolean mouseOver) {
		if( !selected ) {
			if( mouseOver )
				link.setTransparency(0.8f);
			else
				link.setTransparency(1.0f);
		}
	}
	
	public GHost getHost() {
		return host;
	}
	
	public GCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}
	
	public AbstractLink getLogic() {
		return absLink;
	}

	public void delete() {
		super.delete();
		collisionDomain.removeLink( this );
		host.removeLink( this );
	}
}
