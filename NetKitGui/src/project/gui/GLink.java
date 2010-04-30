package project.gui;

import java.awt.geom.Point2D;

import project.core.AbstractLink;

import edu.umd.cs.piccolo.nodes.PPath;

public class GLink extends PPath {
	private static final long serialVersionUID = 1L;
	
	GHost node;
	GCollisionDomain collisionDomain;
	
	AbstractLink link;
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param host
	 * @param abstractLink 
	 * @param collisionDomain2
	 */
	public GLink( GHost host, GCollisionDomain collisionDomain, AbstractLink abstractLink ) {
		this.node = host;
		this.collisionDomain = collisionDomain;
		this.link = abstractLink;
		
		update();
	}

	public void update() {
		Point2D start = node.getFullBoundsReference().getCenter2D();
		Point2D end = collisionDomain.getFullBoundsReference().getCenter2D();
		reset();
		moveTo((float)start.getX(), (float)start.getY());
		lineTo((float)end.getX(), (float)end.getY());
	}
	
	public GHost getNode() {
		return node;
	}
	
	public GCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}
}
