package project.core;

import java.util.HashMap;

import project.common.ItemType;


/**
 * Class representing a generic Host (e.g. router, firewall, pc etc)
 * 
 * @author sal
 */
public class Host implements AbstractHost {

	protected String name;
	
	/** host's network interfaces */
	protected HashMap<String, Interface> interfaces;

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
		this.interfaces = new HashMap<String, Interface>();
	}
	
	/**
	 * Add this interface to the host's interfaces list (returns true) <br>
	 * if there is an existing interface with the same id it returns false
	 * 
	 * @param i - (Interface) the interface to add
	 * @return boolean
	 */
	public boolean addInterface( Interface i ) {
		if( interfaces.get( i.getName() ) == null ) {
			interfaces.put( i.getName(), i );
			return true;
		} else {
			return false;
		}
	}
	
	public HashMap<String, Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(HashMap<String, Interface> interfaces) {
		this.interfaces = interfaces;
	}
	
	@Override
	public AbstractInterface addInterface( AbstractCollisionDomain cd ) {
		if( ifaceNumber < 4 ) {
			return getNextIFace( cd );
		} else {
			return null;
		}
	}

	private AbstractInterface getNextIFace( AbstractCollisionDomain cd ) {
		String ifaceName = "eth" + ifaceNumber;
		ifaceNumber++;
		Interface iface = new Interface( ifaceName, (CollisionDomain) cd, this);
		interfaces.put(ifaceName, iface);
		
		return iface;
	}

	@Override
	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteInterface(String name) {
		if( interfaces.remove(name) != null )
			return true;
		else 
			return false;
	}

	@Override
	public AbstractInterface getInterface(String name) {
		return interfaces.get(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
