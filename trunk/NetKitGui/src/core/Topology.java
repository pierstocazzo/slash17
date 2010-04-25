package core;

import java.util.Collection;
import java.util.HashMap;

import common.ItemType;

public class Topology {
	
	/** Hosts */
	protected HashMap<String, Node> nodes;
	
	/** Collision Domains */
	protected HashMap<String, CollisionDomain> collisionDomains;
	
	public Topology() {
		nodes = new HashMap<String, Node>();
		collisionDomains = new HashMap<String, CollisionDomain>();
		
		createTopology();
	}
	
	private void createTopology() {
		Host pc1 = new Host("pc1", ItemType.PC);
		Host pc2 = new Host("pc2", ItemType.PC);
		Host router = new Host("r", ItemType.ROUTER);
		Host server = new Host("s", ItemType.SERVER);
		Host nattedServer = new Host("snatted", ItemType.NATTEDSERVER);
		Host firewall = new Host("fw", ItemType.FIREWALL);
		Host tap = new Host("tap", ItemType.TAP);
		
		nodes.put( pc1.getName(), pc1 );
		nodes.put( router.getName(), router );
		nodes.put( pc2.getName(), pc2 );
		nodes.put( server.getName(), server);
		nodes.put(nattedServer.getName(), nattedServer);
		nodes.put(firewall.getName(), firewall);
		nodes.put(tap.getName(), tap);
		
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
	
	public Collection<Node> getNodes() {
		return nodes.values();
	}
	
	public Collection<CollisionDomain> getCollisionDomains() {
		return collisionDomains.values();
	}
}

