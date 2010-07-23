/**
 * jNetEdit - Copyright (c) 2010 Salvatore Loria
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package com.jnetedit.common;

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
		"(2([0-4][0-9]|5[0-5])|1[0-9][0-9]|[1-9][0-9]|[1-9])" +
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
