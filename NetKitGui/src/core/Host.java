package core;

import java.util.HashMap;

import common.ItemType;

/**
 * Class representing a generic Host (e.g. router, firewall, pc etc)
 * 
 * @author sal
 */
public class Host extends Node {

	/** host's network interfaces */
	protected HashMap<String, Interface> interfaces;

	/** Create a Host
	 * 
	 * @param name
	 * @param type
	 */
	public Host(String name, ItemType type) {
		super(name, type);
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
		if( interfaces.get( i.getId() ) == null ) {
			interfaces.put( i.getId(), i );
			return true;
		} else {
			return false;
		}
	}
	
	/** 
	 * Remove the interface with this id
	 * 
	 * @param id - (String) the interface name
	 * @return boolean
	 */
	public boolean removeInterface( String id ) {
		return interfaces.remove(id) != null;
	}

	/*********************************
	 * Getter and Setter methods
	 *********************************/
	
	public HashMap<String, Interface> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(HashMap<String, Interface> interfaces) {
		this.interfaces = interfaces;
	}
	
	public Interface getInterface( String id ) {
		return interfaces.get(id);
	}
}
