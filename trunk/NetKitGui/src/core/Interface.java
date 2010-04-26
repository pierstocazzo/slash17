/**
 * 
 */
package core;

/**
 * Class representing a network interface 
 * 
 * @author sal
 */
public class Interface {
	
	/** Interface's name (e.g. eht0) */
	protected String id;
	
	/** ip address (e.g. 10.0.0.1) */
	protected String ip;
	
	/** network (e.g. 10.0.0.0) */
	protected String network;
	
	/** netmask (e.g. 255.255.255.0) */
	protected String netmask;
	
	/** broadcast (e.g. 10.0.0.254) */
	protected String broadcast;
	
	/** Interface's collision domain (e.g. CD1) */
	protected CollisionDomain collisionDomain;
	
	/** host owner of this interface */
	protected Host host;
	
	
	/** Interface constructor<br>
	 * Create a new Interface
	 * 
	 * @param id - (String) interface's name (e.g. eht0)
	 * @param ip - (String) ip address (e.g. 10.0.0.1)
	 * @param network - (String) network (e.g. 10.0.0.0)
	 * @param netmask - (String) netmask (e.g. 255.255.255.0)
	 * @param broadcast - (String) broadcast (e.g. 10.0.0.254)
	 * @param collisionDomain - (String) Interface's collision domain (e.g. CD1)
	 * @param host - (String) host (e.g. r1)
	 */
	public Interface( String id, String ip, String network, String netmask,
			String broadcast, CollisionDomain collisionDomain, Host host ) {
		
		this.id = id;
		this.ip = ip;
		this.network = network;
		this.netmask = netmask;
		this.broadcast = broadcast;
		this.collisionDomain = collisionDomain;
		this.host = host;
	}

	/** Interface constructor<br>
	 * Create a new Interface
	 * 
	 * @param id - (String) interface's name (e.g. eht0)
	 * @param collisionDomain - (String) Interface's collision domain (e.g. CD1)
	 * @param host - (String) host (e.g. r1)
	 */
	public Interface( String id, CollisionDomain collisionDomain, Host host ) {
		this.id = id;
		this.collisionDomain = collisionDomain;
		this.host = host;
	}
	
	
	/***************************
	 * Getter and Setter methods
	 ***************************/
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

	public CollisionDomain getCollisionDomain() {
		return collisionDomain;
	}

	public void setCollisionDomain(CollisionDomain collisionDomain) {
		this.collisionDomain = collisionDomain;
	}

	public Host getHost() {
		return host;
	}

	public void setHost(Host host) {
		this.host = host;
	}
}
