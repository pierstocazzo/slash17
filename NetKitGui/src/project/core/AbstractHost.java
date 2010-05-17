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
	
	
	public AbstractRoute addRoute();
	
	public AbstractRoute getRoute( String net );
	
	public ArrayList<AbstractRoute> getRoutes();
	
	public void deleteRoute( AbstractRoute route );
	
	
	public AbstractChain addChain();
	
	public AbstractChain getChain( String chain );
	
	public ArrayList<AbstractChain> getChains();
	
	public void deleteChain( AbstractChain chain );
	

	public boolean delete();

	public ItemType getType();

	public boolean isConnectedTo( AbstractCollisionDomain collisionDomain );
	
	public String getStartupFile();
}
