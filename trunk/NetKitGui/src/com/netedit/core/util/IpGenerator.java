package com.netedit.core.util;

public class IpGenerator {

	static String tapNet = "200.0.0.";
	static int n = 2;
	
	public static String getNextTapIp() {
		return tapNet + n++;
	}
}
