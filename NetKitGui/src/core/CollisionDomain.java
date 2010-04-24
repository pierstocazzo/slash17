package core;

public class CollisionDomain {
	
	/** collision domain name */
	protected String name;
	
	/** Area */
	protected String area;
	
	/** minimum number of ip address needed */
	protected int minIpAddr;
	
	/** subnet */
	protected String subnet;
	
	public CollisionDomain( String name ) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
}
