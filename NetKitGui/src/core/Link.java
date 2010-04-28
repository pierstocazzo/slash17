package core;

public class Link {

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
	public Link( Interface hostInterface, CollisionDomain collisionDomain ) {
		this.hostInterface = hostInterface;
		this.collisionDomain = collisionDomain;
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
	
	public Host getHost() {
		return hostInterface.getHost();
	}
}
