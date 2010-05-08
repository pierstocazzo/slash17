package project.gui;

import java.awt.geom.Point2D;

import project.core.AbstractLink;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PPath;

public class GLink extends PPath {
	private static final long serialVersionUID = 1L;
	
	GHost host;
	GCollisionDomain collisionDomain;
	
	AbstractLink absLink;
	
	PLayer layer;
	
	/** 
	 * Create a graph edge between two node
	 * 
	 * @param host
	 * @param abstractLink 
	 * @param collisionDomain2
	 */
	public GLink( GHost host, GCollisionDomain collisionDomain, AbstractLink abstractLink, PLayer layer ) {
		this.host = host;
		this.collisionDomain = collisionDomain;
		this.absLink = abstractLink;
		this.layer = layer;
		
		update();
	}

	public void update() {
		Point2D start = host.getFullBoundsReference().getCenter2D();
		Point2D end = collisionDomain.getFullBoundsReference().getCenter2D();
		reset();
		moveTo((float)start.getX(), (float)start.getY());
		lineTo((float)end.getX(), (float)end.getY());
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
		layer.removeChild(this);
		collisionDomain.removeLink( this );
		host.removeLink( this );
	}
}
