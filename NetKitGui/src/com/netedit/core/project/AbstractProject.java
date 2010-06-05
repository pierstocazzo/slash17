package com.netedit.core.project;

import java.util.ArrayList;
import java.util.Collection;

import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;


public interface AbstractProject {
	
	public String getName();
	
	public String getDirectory();
	
	public String getDescription();
	
	public String getAuthor();
	
	public String getEmail();
	
	public String getWeb();
	
	public String getVersion();
	
	public void setName(String projectName);

	public void setDirectory(String string);
	
	
	public void addHost( AbstractHost host );
	
	public void addCollisionDomain( AbstractCollisionDomain cd );
	
	public void addLink( AbstractLink link );
	
	
	public void removeHost( AbstractHost abstractHost );
	
	public void removeCollisionDomain( AbstractCollisionDomain abstractCollisionDomain );
	
	public void removeLink( AbstractLink link );
	

	public Collection<AbstractHost> getHosts();
	
	public Collection<AbstractCollisionDomain> getCollisionDomains();

	public String getLabConfFile();

	public ArrayList<AbstractLink> getLinks();
}
