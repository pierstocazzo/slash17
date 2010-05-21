package com.netedit.common;

public class Config {

	protected static boolean exitWithoutAsking = false;
	
	public static boolean exitWithoutAsking() {
		return exitWithoutAsking();
	}
	
	public static void setExitWithoutAsking( boolean bool ) {
		exitWithoutAsking = bool;
	}
	
	// TODO read configuration from file
}
