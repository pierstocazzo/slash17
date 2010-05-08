package project.core;

import java.util.Collection;

public interface AbstractProject {
	
	public String getName();
	
	public String getDirectory();
	
	public String getDescription();
	
	public String getAuthor();
	
	public String getEmail();
	
	public String getWeb();
	
	public String getVersion();
	
	public void addHost( AbstractHost host );
	
	public void addCollisionDomain( AbstractCollisionDomain cd );
	
	public void removeHost( AbstractHost abstractHost );
	
	public void removeCollisionDomain( AbstractCollisionDomain abstractCollisionDomain );
	
	public void removeLink( AbstractLink link );

	public Collection<AbstractHost> getHosts();
	
	public Collection<AbstractCollisionDomain> getCollisionDomains();

	public void setName(String projectName);

	public void setDirectory(String string);
}
