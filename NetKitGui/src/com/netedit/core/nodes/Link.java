package com.netedit.core.nodes;

import com.netedit.core.nodes.components.AbstractInterface;
import com.netedit.core.nodes.components.Interface;
import com.netedit.core.util.IpGenerator;

public class Link implements AbstractLink {

	/** link's id */
	protected String id;
	
	/** Host's interface */
	protected AbstractInterface hostInterface;
	
	/** collision domain */
	protected AbstractCollisionDomain collisionDomain;
	
	/** Connect an host to a collision domain
	 * 
	 * @param hostInterface
	 * @param collisionDomain
	 */
	public Link( AbstractInterface hostInterface, AbstractCollisionDomain collisionDomain ) {
		this.hostInterface = hostInterface;
		this.collisionDomain = collisionDomain;
		if( this.collisionDomain.isTap() ) {
			this.hostInterface.setIp(IpGenerator.getNextTapIp());
			this.hostInterface.setConnectedToTap(true);
		}
	}
	
	
	/****************************
	 * Getter and Setter methods
	 ****************************/

	public AbstractCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}

	public void setCollisionDomain(CollisionDomain collisionDomain) {
		this.collisionDomain = collisionDomain;
	}
	
	public AbstractInterface getHostInterface() {
		return hostInterface;
	}

	public void setHostInterface(Interface hostInterface) {
		this.hostInterface = hostInterface;
	}
	
	public AbstractHost getHost() {
		return hostInterface.getHost();
	}


	@Override
	public void delete() {
		collisionDomain.removeConnection( hostInterface );
		hostInterface.delete();
	}


	@Override
	public AbstractInterface getInterface() {
		return hostInterface;
	}
}
