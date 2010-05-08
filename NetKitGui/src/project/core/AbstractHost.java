package project.core;

import java.util.ArrayList;

import project.common.ItemType;

public interface AbstractHost {

	public String getName();
	
	public void setName( String name );
	
	public AbstractInterface addInterface( AbstractCollisionDomain cd );
	
	public AbstractInterface getInterface( String name );
	
	public ArrayList<AbstractInterface> getInterfaces();

	public boolean deleteInterface( AbstractInterface iface );
	
	public boolean delete();

	public ItemType getType();

	public boolean isConnectedTo( AbstractCollisionDomain collisionDomain );
}
