package project.core;

import java.util.Collection;
import java.util.HashMap;

import project.common.ItemType;


/** Class representig a netkit project
 * 
 * @author sal
 */
public class Project {
	
	/** project's name */
	protected String name;
	
	/** project's description */
	protected String description;
	
	/** project's author */
	protected String author;
	
	/** project's directory */
	protected String directory;
	
	/** project's version */
	protected String version;
	
	/** author's email */
	protected String email;
	
	/** web site */
	protected String web;
	
	/** Hosts */
	protected HashMap<String, Host> hosts;
	
	/** Collision Domains */
	protected HashMap<String, CollisionDomain> collisionDomains;
	
	/**
	 * Create a new empty netkit project with this name in this directory
	 * 
	 * @param name (String) - the project's name
	 * @param directory (String) - the project's directory
	 */
	Project( String directory, String name ) {
		hosts = new HashMap<String, Host>();
		collisionDomains = new HashMap<String, CollisionDomain>();
		this.directory = directory;
		this.name = name;
	}
	
	/** Add a new host to the project
	 * 
	 * @param host (Host) the new host to add
	 */
	public void addHost( Host host ) {
		hosts.put( host.getName(), host );
	}
	
	/** Add a new collision domain to the project
	 * 
	 * @param cd (CollisionDomain) the new collisiondomain
	 */
	public void addCollisionDomain( CollisionDomain cd ) {
		collisionDomains.put( cd.getName(), cd );
	}
	
	/** Remove the host with this name
	 * 
	 * @param host (Host) the host to remove
	 */
	public void removeHost( String host ) {
		hosts.remove(host);
	}
	
	/** Remove the collision domain with this name
	 * 
	 * @param cd (CollisionDomain) the collision domain to remove
	 */
	public void removeCollisionDomain( String cd ) {
		collisionDomains.remove(cd);
	}
	
	/** 
	 * topology example TODO random topology
	 */
	public void createTopology() {
		Host pc1 = new Host("pc1", ItemType.PC);
		Host pc2 = new Host("pc2", ItemType.PC);
		Host router = new Host("r", ItemType.ROUTER);
		Host server = new Host("s", ItemType.SERVER);
		Host nattedServer = new Host("snatted", ItemType.NATTEDSERVER);
		Host firewall = new Host("fw", ItemType.FIREWALL);
		Host tap = new Host("tap", ItemType.TAP);
		
		hosts.put( pc1.getName(), pc1 );
		hosts.put( router.getName(), router );
		hosts.put( pc2.getName(), pc2 );
		hosts.put( server.getName(), server);
		hosts.put(nattedServer.getName(), nattedServer);
		hosts.put(firewall.getName(), firewall);
		hosts.put(tap.getName(), tap);
		
		CollisionDomain CD1 = new CollisionDomain("CD1");
		CollisionDomain CD2 = new CollisionDomain("CD2");
		CollisionDomain RF = new CollisionDomain("RF");
		CollisionDomain FT = new CollisionDomain("FT");
		
		pc1.addInterface( new Interface("eth0", CD1, pc1));
		pc2.addInterface( new Interface("eth0", CD1, pc1));
		router.addInterface( new Interface("eth0", CD1, router));
		router.addInterface( new Interface("eth1", CD2, router));
		router.addInterface( new Interface("eth2", RF, router));
		server.addInterface( new Interface("eth0", CD2, server) );
		nattedServer.addInterface( new Interface("eth0", CD2, nattedServer) );
		firewall.addInterface( new Interface("eth0", RF, firewall));
		firewall.addInterface( new Interface("eth1", FT, firewall));
		tap.addInterface( new Interface("eth0", FT, tap));
	}
	
	/*******************************
	 * Getter and Setter methods
	 *******************************/
	
	public Collection<Host> getHosts() {
		return hosts.values();
	}
	
	public Collection<CollisionDomain> getCollisionDomains() {
		return collisionDomains.values();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}
}

