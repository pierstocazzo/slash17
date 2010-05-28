package com.netedit.common;

public class IpAddress {
	
	/** TODO netmask regural expression */
	public static final String maskRx = 
		"(2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[1-9])\\." +
		"((2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){2}" + 
		"(2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[0-9])";

	/** ip address regular expression */
	public static final String ipRx = 
		"(2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[1-9])\\." +
		"((2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){2}" + 
		"(2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[0-9])";

	public static String netRx = 
		"(2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[1-9])\\." +
		"((2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){2}" + 
		"0" +
		"/(30|2[0-9]|1[0-9]|[0-9])";
	
	/** ip address string */
	protected String ip;
	
	public IpAddress( String ip ) {
		if( ip.matches(ipRx) ) {
			this.ip = ip;
		} else {
			System.err.println("Ip format incorrect: " + ip);
		}
	}
	
	public String toString() {
		return ip;
		
	}
	
	public boolean isValidIp( String ip ) {
		return ip.matches(ipRx);
	}
	
	public boolean isValidMask( String mask ) {
		return mask.matches(maskRx);
	}
	
	public boolean equals( Object o ) {
		return this.ip.equals( ((IpAddress) o).toString() );
	}
}
