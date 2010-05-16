package project.core;

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
}
