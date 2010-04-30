package project.gui;

import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.nodes.PPath;

public class GLink extends PPath {
	private static final long serialVersionUID = 1L;
	
	GHost node;
	GCollisionDomain collisionDomain;
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param host
	 * @param collisionDomain2
	 */
	public GLink( GHost host, GCollisionDomain collisionDomain ) {
		this.node = host;
		this.collisionDomain = collisionDomain;
		
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
