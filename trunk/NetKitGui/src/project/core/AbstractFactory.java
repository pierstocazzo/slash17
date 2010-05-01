package project.core;

import project.common.ItemType;

public interface AbstractFactory {

	/** create an host of this type */
	public AbstractHost createHost( ItemType type );
	
	/** create a collision domain */
	public AbstractCollisionDomain createCollisionDomain();
	
	/** connect this host to this collision domain */
	public AbstractLink createLink( AbstractHost host, AbstractCollisionDomain collisionDomain ); 
	
	public AbstractProject createProject( String name, String directory );
}

