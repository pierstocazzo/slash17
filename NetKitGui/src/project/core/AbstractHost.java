package project.core;

import java.util.Collection;

import project.common.ItemType;

public interface AbstractHost {

	public String getName();
	
	public void setName( String name );
	
	public AbstractInterface addInterface( AbstractCollisionDomain cd );
	
	public AbstractInterface getInterface( String name );
	
	public Collection<AbstractInterface> getInterfaces();

	public boolean deleteInterface( String name );
	
	public boolean delete();

	public ItemType getType();
}
