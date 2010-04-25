package core;

import java.util.ArrayList;

import common.IpAddress;
import common.ItemType;

/** 
 * Class representing a collision domain
 * 
 * @author sal
 */
public class CollisionDomain extends Node {
	
	/** network area of this domain */
	protected String area;
	
	/** minimum number of ip address needed */
	protected int minIpAddr;
	
	/** subnet */
	protected String subnet;
	
	/** subnet mask */
	protected String subnetMask;
	
	/** range of usable ip addresses */
	protected String ipRange;
	
	/** hosts interfaces on this collision domain */
	protected ArrayList<Interface> hostsInterfaces;
	
	/** 
	 * Create a collision domain with a minimum ip addresses required
	 * 
	 * @param name - (String) collision domain's name
	 * @param minIpAddr - (int) minimum ip addresses required
	 */
	public CollisionDomain( String name, int minIpAddr ) {
		super( name, ItemType.COLLISIONDOMAIN );
		this.minIpAddr = minIpAddr;
		this.name = name;
	}

	/** 
	 * Create a collision domain
	 * 
	 * @param name - (String) collision domain's name
	 */
	public CollisionDomain( String name ) {
		super( name, ItemType.COLLISIONDOMAIN );
		this.name = name;
	}
	
	
	/*******************************
	 * Getter and setter methods
	 ******************************/
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getMinIpAddr() {
		return minIpAddr;
	}

	public void setMinIpAddr(int minIpAddr) {
		this.minIpAddr = minIpAddr;
	}

	public String getSubnet() {
		return subnet;
	}

	public boolean setSubnet(String subnet) {
		if( subnet.matches( IpAddress.ipRx )) {
			this.subnet = subnet;
			return true;
		} else {
			return false;
		}
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public boolean setSubnetMask(String subnetMask) {
		if( subnetMask.matches( IpAddress.maskRx )) {
			this.subnetMask = subnetMask;
			return true;
		} else {
			return false;
		}
	}

	public String getIpRange() {
		return ipRange;
	}

	public boolean setIpRange(String ipRange) {
		if( ipRange.matches( IpAddress.ipRx + "-" + IpAddress.ipRx )) {
			this.ipRange = ipRange;
			return true;
		} else {
			return false;
		}
	}
}
