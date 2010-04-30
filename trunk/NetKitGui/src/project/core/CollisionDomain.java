package project.core;

import java.util.ArrayList;

import project.common.IpAddress;


/** 
 * Class representing a collision domain
 * 
 * @author sal
 */
public class CollisionDomain implements AbstractCollisionDomain {
	
	protected String name;
	
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
	CollisionDomain( String name, int minIpAddr ) {
		this.minIpAddr = minIpAddr;
		this.name = name;
	}

	/** 
	 * Create a collision domain
	 * 
	 * @param name - (String) collision domain's name
	 */
	CollisionDomain( String name ) {
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

	@Override
	public boolean delete() {
		return true;
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
