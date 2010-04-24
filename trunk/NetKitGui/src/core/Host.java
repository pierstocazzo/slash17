package core;

import java.util.HashMap;

import common.ItemType;

/**
 * Class representing a generic Host (e.g. router, firewall, pc etc)
 * 
 * @author sal
 */
public class Host {

	/** host's id */
	protected String id;
	
	/** host's type */
	protected ItemType type;
	
	/** host's network interfaces */
	protected HashMap<String, Interface> interfaces;

	/** Create an Host
	 * 
	 * @param id
	 * @param type
	 */
	public Host(String id, ItemType type) {
		super();
		this.id = id;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

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
