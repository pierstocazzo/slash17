package project.core.nodes;

import project.core.nodes.components.AbstractInterface;

public interface AbstractCollisionDomain {

	public String getName();
	
	public void setName( String name );
	
	public boolean delete();
	
	public void addConnection( AbstractInterface hostInterface );
	
	public void removeConnection( AbstractInterface hostInterface );
}
