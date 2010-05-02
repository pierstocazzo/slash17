package project.core;

public class Link implements AbstractLink {

	/** link's id */
	protected String id;
	
	/** Host's interface */
	protected Interface hostInterface;
	
	/** collision domain */
	protected CollisionDomain collisionDomain;
	
	/** Connect an host to a collision domain
	 * 
	 * @param hostInterface
	 * @param collisionDomain
	 */
	Link( AbstractInterface hostInterface, AbstractCollisionDomain collisionDomain ) {
		this.hostInterface = (Interface) hostInterface;
		this.collisionDomain = (CollisionDomain) collisionDomain;
	}
	
	
	/****************************
	 * Getter and Setter methods
	 ****************************/

	public CollisionDomain getCollisionDomain() {
		return collisionDomain;
	}

	public void setCollisionDomain(CollisionDomain collisionDomain) {
		this.collisionDomain = collisionDomain;
	}
	
	public Interface getHostInterface() {
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
		hostInterface.delete();
		collisionDomain.removeConnection( hostInterface );
	}
}
