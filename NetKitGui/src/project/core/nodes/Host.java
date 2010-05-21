package project.core.nodes;

import java.util.ArrayList;

import project.common.ItemType;
import project.core.nodes.components.AbstractChain;
import project.core.nodes.components.AbstractInterface;
import project.core.nodes.components.AbstractRoute;
import project.core.nodes.components.AbstractRule;
import project.core.nodes.components.Chain;
import project.core.nodes.components.Interface;
import project.core.nodes.components.Route;


/**
 * Class representing a generic Host (e.g. router, firewall, pc etc)
 * 
 * @author sal
 */
public class Host implements AbstractHost {

	protected String name;
	
	/** host's network interfaces */
	protected ArrayList<AbstractInterface> interfaces;
	/** host's routes */
	protected ArrayList<AbstractRoute> routes;
	/** host's iptables chains */
	protected ArrayList<AbstractChain> chains;

	protected ItemType type;

	private int ifaceNumber = 0;

	/** Create a Host
	 * 
	 * @param name
	 * @param type
	 */
	public Host(String name, ItemType type) {
		this.name = name;
		this.type = type;
		
		interfaces = new ArrayList<AbstractInterface>();
		routes = new ArrayList<AbstractRoute>();
		chains = new ArrayList<AbstractChain>();
		
		if( type == ItemType.FIREWALL ) {
			chains.add(new Chain(this, "INPUT"));
			chains.add(new Chain(this, "OUTPUT"));
			chains.add(new Chain(this, "FORWARD"));
		}
	}
	
	/**
	 * Add this interface to the host's interfaces list (returns true) <br>
	 * if there is an existing interface with the same id it returns false
	 * 
	 * @param i - (Interface) the interface to add
	 * @return boolean
	 */
	public void addInterface( Interface i ) {
		interfaces.add( i );
	}
	
	public ArrayList<AbstractInterface> getInterfaces() {
		return interfaces;
	}

	@Override
	public AbstractInterface addInterface( AbstractCollisionDomain cd ) {
		if( ifaceNumber < 4 ) {
			String ifaceName = "eth" + ifaceNumber++;
			Interface iface = new Interface( ifaceName, (CollisionDomain) cd, this);
			interfaces.add(iface);
			return iface;
		} else {
			return null;
		}
	}

	@Override
	public boolean delete() {
		while( !interfaces.isEmpty() ) {
			interfaces.get(0).delete();
		}
		interfaces.clear();
		routes.clear();
		return true;
	}

	@Override
	public boolean deleteInterface( AbstractInterface iface ) {
		return interfaces.remove( iface );
	}

	@Override
	public AbstractInterface getInterface(String ifaceName) {
		String name = ifaceName.split(" :")[0];
		for( AbstractInterface i : interfaces ) {
			if( i.getName().equals(name) ) 
				return i;
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public ItemType getType() {
		return type;
	}

	@Override
	public boolean isConnectedTo(AbstractCollisionDomain collisionDomain) {
		for( AbstractInterface iface : interfaces ) {
			if( iface.getCollisionDomain().equals(collisionDomain)) 
				return true;
		}
		return false;
	}

	@Override
	public AbstractRoute getRoute(String net) {
		for( AbstractRoute route : routes ) {
			if( route.getNet().equals(net) ) 
				return route;
		}
		return null;
	}

	@Override
	public ArrayList<AbstractRoute> getRoutes() {
		return routes;
	}

	@Override
	public AbstractRoute addRoute() {
		AbstractRoute route = new Route(this);
		routes.add(route);
		return route;
	}

	@Override
	public void deleteRoute(AbstractRoute route) {
		routes.remove(route);
	}

	@Override
	public AbstractChain addChain() {
		AbstractChain chain = new Chain(this);
		chains.add(chain);
		return chain;
	}

	@Override
	public void deleteChain(AbstractChain chain) {
		chains.remove(chain);
	}

	@Override
	public AbstractChain getChain(String chain) {
		for( AbstractChain c : chains ) {
			if( c.getName().equals(chain) )
				return c;
		}
		return null;
	}

	@Override
	public ArrayList<AbstractChain> getChains() {
		return chains;
	}
	
	public String getStartupFile() {
		String text = "";
		text = "# '" + name + ".startup' created by NetKit GUI\n\n";
		
		text += "# Interfaces configuration\n";
		for( AbstractInterface iface : interfaces ) {
			text += iface.getConfCommand();
		}
		text += "\n\n";
		
		text += "# Routing configuration\n";
		for( AbstractRoute route : routes ) {
			text += route.getConfCommand();
		}
		text += "\n\n";
		
		text += "# Firewalling configuration\n";
		for( AbstractChain chain : chains ) {
			text += chain.getConfCommand();
			for( AbstractRule rule : chain.getRules() ) {
				text += rule.getRule();
			}
			text += "\n\n";
		}
		
		return text;
	}
}
