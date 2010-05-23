package com.netedit.core.nodes.components;

import com.netedit.core.nodes.AbstractHost;

public class Route implements AbstractRoute {

	AbstractHost host;
	
	String net;
	String gw;
	
	public Route( AbstractHost host ) {
		this.host = host;
	}
	
	public AbstractHost getHost() {
		return host;
	}

	public void setHost(AbstractHost host) {
		this.host = host;
	}

	public String getNet() {
		return net;
	}

	public void setNet(String net) {
		this.net = net;
	}

	public String getGw() {
		return gw;
	}

	public void setGw(String gw) {
		this.gw = gw;
	}

	@Override
	public void delete() {
		host.deleteRoute(this);
	}

	@Override
	public String getConfCommand() {
		String command = "";
		if( net != null && gw != null ) {
			if( net.equals("0.0.0.0/0") )
				command += "route add default gw " + gw + "\n";
			else
				command += "route add -net " + net + " gw " + gw + "\n";
		}
		return command;
	}
}
