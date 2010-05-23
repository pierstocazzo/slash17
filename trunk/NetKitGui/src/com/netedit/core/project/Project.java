package com.netedit.core.project;

import java.util.ArrayList;
import java.util.Collection;

import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;
import com.netedit.core.nodes.components.AbstractInterface;


/** Class representig a netkit project
 * 
 * @author sal
 */
public class Project implements AbstractProject {
	
	/** project's name */
	protected String name;
	
	/** project's description */
	protected String description;
	
	/** project's author */
	protected String author;
	
	/** project's directory */
	protected String directory;
	
	/** project's version */
	protected String version;
	
	/** author's email */
	protected String email;
	
	/** web site */
	protected String web;
	
	/** Hosts */
	protected ArrayList<AbstractHost> hosts;
	
	/** Collision Domains */
	protected ArrayList<AbstractCollisionDomain> collisionDomains;
	
	/** the tap */
	protected AbstractCollisionDomain tap;
	
	/**
	 * Create a new empty netkit project with this name in this directory
	 * 
	 * @param name (String) - the project's name
	 * @param directory (String) - the project's directory
	 */
	public Project( String directory, String name ) {
		hosts = new ArrayList<AbstractHost>();
		collisionDomains = new ArrayList<AbstractCollisionDomain>();
		this.directory = directory;
		this.name = name;
	}
	
	/** Add a new host to the project
	 * 
	 * @param host (Host) the new host to add
	 */
	public void addHost( AbstractHost host ) {
		hosts.add( host );
	}
	
	/** Add a new collision domain to the project
	 * 
	 * @param cd (CollisionDomain) the new collisiondomain
	 */
	public void addCollisionDomain( AbstractCollisionDomain cd ) {
		if( cd.isTap() ) {
			this.tap = cd;
		} else {
			collisionDomains.add( cd );
		}
	}
	
	/** Remove the host with this name
	 * 
	 * @param host (Host) the host to remove
	 */
	public void removeHost( AbstractHost host ) {
		if( hosts.remove(host) )
			host.delete();
	}
	
	/** Remove the collision domain with this name
	 * 
	 * @param cd (CollisionDomain) the collision domain to remove
	 */
	public void removeCollisionDomain( AbstractCollisionDomain cd ) {
		if( collisionDomains.remove(cd) )
			cd.delete();
	}
	
	/** 
	 * topology example TODO random topology
	 */
	public void createTopology() {
	}
	
	/*******************************
	 * Getter and Setter methods
	 *******************************/
	
	public Collection<AbstractHost> getHosts() {
		return hosts;
	}
	
	public Collection<AbstractCollisionDomain> getCollisionDomains() {
		return collisionDomains;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Override
	public void removeLink(AbstractLink link) {
		link.delete();
	}
	
	public String getLabConfFile() {
		String text = "# 'lab.conf' created by NetKit GUI\n\n";
		
		for( AbstractHost host : hosts ) {
			String hostName = host.getName();
			
			for( AbstractInterface iface : host.getInterfaces() ) {
				String ifaceName =  iface.getName();
				String cdName = iface.getCollisionDomain().getName();
				
				text += hostName + "[" + ifaceName + "]=\"" + cdName + "\"\n";
			}
			text += "\n";
		}
		return text;
	}
}

