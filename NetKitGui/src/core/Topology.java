package core;

import java.util.Collection;
import java.util.HashMap;

import common.HostType;

public class Topology {
	
	/** Hosts */
	protected HashMap<String, Host> hosts;
	
	/** Collision Domains */
	protected HashMap<String, CollisionDomain> collisionDomains;
	
	public Topology() {
		hosts = new HashMap<String, Host>();
		collisionDomains = new HashMap<String, CollisionDomain>();
		
		createTopology();
	}
	
	private void createTopology() {
		Host pc = new Host("pc", HostType.PC);
		Host router = new Host("r", HostType.ROUTER);
		
		hosts.put( pc.id, pc );
		hosts.put( router.id, router );
		
		CollisionDomain CD1 = new CollisionDomain("CD1");
		
		pc.addInterface( new Interface("eth0", "10.0.0.2", "10.0.0.0", "255.255.255.0", "10.0.0.254", CD1, pc));
		router.addInterface( new Interface("eth0", "10.0.0.1", "10.0.0.0", "255.255.255.0", "10.0.0.254", CD1, router));
	}
	
	public Collection<Host> getHosts() {
		return hosts.values();
	}
	
	public Collection<CollisionDomain> getCollisionDomains() {
		return collisionDomains.values();
	}
}

