package gui;

import java.awt.geom.Point2D;

import edu.umd.cs.piccolo.nodes.PPath;

public class GLink extends PPath {
	private static final long serialVersionUID = 1L;
	
	GNode node;
	GCollisionDomain collisionDomain;
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param host
	 * @param cd
	 */
	public GLink( GNode host, GCollisionDomain cd ) {
		this.node = host;
		this.collisionDomain = cd;
		
		update();
	}

	public void update() {
		Point2D start = node.getFullBoundsReference().getCenter2D();
		Point2D end = collisionDomain.getFullBoundsReference().getCenter2D();
		reset();
		moveTo((float)start.getX(), (float)start.getY());
		lineTo((float)end.getX(), (float)end.getY());
	}
	
	public GNode getNode() {
		return node;
	}
	
	public GCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}
}
