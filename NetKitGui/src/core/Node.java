package core;

import common.ItemType;

/** 
 * Superclass for a generic network item
 * 
 * @author sal
 */
public class Node {

	/** item name */
	protected String name;
	
	/** item type */
	protected ItemType type;

	/** Create a network item of the specified type
	 * 
	 * @param name - (String) item's name
	 * @param type - (ItemType) item's type
	 */
	public Node(String name, ItemType type) {
		this.name = name;
		this.type = type;
	}

	/**************************
	 * Getter and setter methods
	 *************************/
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}
}
