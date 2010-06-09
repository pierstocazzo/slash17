package com.jnetedit.generator;

import java.awt.geom.Rectangle2D;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;

/**
 * Interface witch define an automatic layouting for jNetEdit nodes
 */
public interface AbstractLayout {

	/**
	 * Called to get the position of an host in the canvas 
	 * for a automatic layouting
	 * 
	 * @param host - (AbstractHost) the node to bound
	 * @return the bounds for this node
	 */
	public Rectangle2D getBounds(AbstractHost host);
	
	/**
	 * Called to get the position of a domain in the canvas 
	 * for a automatic layouting
	 * 
	 * @param domain - (AbstractCollisionDomain) the domain to bound
	 * @return the bounds for this node
	 */
	public Rectangle2D getBounds(AbstractCollisionDomain domain);
}
