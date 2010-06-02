package com.netedit.core.nodes;

import com.netedit.core.nodes.components.AbstractInterface;

public interface AbstractCollisionDomain {

	public String getName();
	
	public void setName( String name );
	
	public boolean delete();
	
	public void addConnection( AbstractInterface hostInterface );
	
	public void removeConnection( AbstractInterface hostInterface );

	public boolean isTap();

	public void setIsTap(boolean isTap);
	
	public void setArea( String area );
	
	public String getArea();

	public void setMinimumIp(int min);

	public int getMinimumIp();
}
