package project.core.nodes.components;

import project.core.nodes.AbstractCollisionDomain;
import project.core.nodes.AbstractHost;

public interface AbstractInterface {
	
	public String getName();
	
	public String getIp();
	
	public String getMask();
	
	public String getBCast();
	
	public AbstractCollisionDomain getCollisionDomain();
	
	public AbstractHost getHost();
	
	public void setName( String name );
	
	public boolean setIp( String ip );
	
	public boolean setMask( String mask );
	
	public boolean setBCast( String bcast );
	
	public void setCollisionDomain( AbstractCollisionDomain collisionDomain );
	
	public void setHost( AbstractHost host );
	
	public void reset();

	public void delete();
	
	public String getConfCommand();
}
