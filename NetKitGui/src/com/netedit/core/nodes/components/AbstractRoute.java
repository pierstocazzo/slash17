package com.netedit.core.nodes.components;

import com.netedit.core.nodes.AbstractHost;

public interface AbstractRoute {

	public AbstractHost getHost();
	
	public String getNet();
	
	public String getGw();
	
	public void setNet( String net );
	
	public void setGw( String gw );
	
	public void delete();
	
	public String getConfCommand();
}
