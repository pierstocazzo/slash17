package project.core;

import java.util.ArrayList;

import project.common.ItemType;


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
	Host(String name, ItemType type) {
		this.name = name;
		this.type = type;
		
		interfaces = new ArrayList<AbstractInterface>();
		routes = new ArrayList<AbstractRoute>();
		chains = new ArrayList<AbstractChain>();
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
}
