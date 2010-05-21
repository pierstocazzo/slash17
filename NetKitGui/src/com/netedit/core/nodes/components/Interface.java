/**
 * 
 */
package com.netedit.core.nodes.components;

import com.netedit.common.IpAddress;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.CollisionDomain;
import com.netedit.core.nodes.Host;


/**
 * Class representing a network interface 
 * 
 * @author sal
 */
public class Interface implements AbstractInterface {
	
	/** Interface's name (e.g. eht0) */
	protected String name;
	
	/** ip address (e.g. 10.0.0.1) */
	protected String ip;
	
	/** network (e.g. 10.0.0.0) */
	protected String network;
	
	/** netmask (e.g. 255.255.255.0) */
	protected String netmask;
	
	/** broadcast (e.g. 10.0.0.254) */
	protected String broadcast;
	
	/** Interface's collision domain (e.g. CD1) */
	protected CollisionDomain collisionDomain;
	
	/** host owner of this interface */
	protected Host host;
	
	/** the ifconfig command for this interface's configuration */
	protected String confCommand;
	
	
	/** Interface constructor<br>
	 * Create a new Interface
	 * 
	 * @param id - (String) interface's name (e.g. eht0)
	 * @param ip - (String) ip address (e.g. 10.0.0.1)
	 * @param network - (String) network (e.g. 10.0.0.0)
	 * @param netmask - (String) netmask (e.g. 255.255.255.0)
	 * @param broadcast - (String) broadcast (e.g. 10.0.0.254)
	 * @param collisionDomain - (String) Interface's collision domain (e.g. CD1)
	 * @param host - (String) host (e.g. r1)
	 */
	Interface( String id, String ip, String network, String netmask,
			String broadcast, CollisionDomain collisionDomain, Host host ) {
		
		this.name = id;
		this.ip = ip;
		this.network = network;
		this.netmask = netmask;
		this.broadcast = broadcast;
		this.collisionDomain = collisionDomain;
		this.host = host;
	}

	/** Interface constructor<br>
	 * Create a new Interface
	 * 
	 * @param id - (String) interface's name (e.g. eht0)
	 * @param collisionDomain - (String) Interface's collision domain (e.g. CD1)
	 * @param host - (String) host (e.g. r1)
	 */
	public Interface( String id, CollisionDomain collisionDomain, Host host ) {
		this.name = id;
		this.collisionDomain = collisionDomain;
		this.host = host;
	}
	
	
	/***************************
	 * Getter and Setter methods
	 ***************************/
	
	public String getIp() {
		return ip;
	}

	public boolean setIp(String ip) {
		if( ip.matches( IpAddress.ipRx ) ) {
			this.ip = ip;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public AbstractHost getHost() {
		return host;
	}

	@Override
	public void reset() {
		this.ip = "";
		this.broadcast = "";
		this.netmask = "";
	}

	@Override
	public String getBCast() {
		return broadcast;
	}

	@Override
	public String getMask() {
		return netmask;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean setBCast(String bcast) {
		if( bcast.matches(IpAddress.ipRx) ) {
			this.broadcast = bcast;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean setMask(String mask) {
		if( mask.matches(IpAddress.ipRx) ) {
			this.netmask = mask;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setCollisionDomain(AbstractCollisionDomain collisionDomain) {
		this.collisionDomain = (CollisionDomain) collisionDomain;
	}

	@Override
	public AbstractCollisionDomain getCollisionDomain() {
		return collisionDomain;
	}

	@Override
	public void setHost(AbstractHost host) {
		this.host = (Host) host;
	}

	public void delete() {
		reset();
		host.deleteInterface(this);
		collisionDomain.removeConnection(this);
	}
	
	public String getConfCommand() {
		String ifconfig = "";
		if( ip != null && netmask != null && broadcast != null ) {
			ifconfig += "ifconfig " + name + " " + ip + " netmask " + 
							netmask + " broadcast " + broadcast + " up\n";
		} else {
			ifconfig += "ifconfig " + name + " up # not configured \n";
		}
		return ifconfig;
	}
}
