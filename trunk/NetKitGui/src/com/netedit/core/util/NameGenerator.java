package com.netedit.core.util;

import java.io.Serializable;

import com.netedit.common.ItemType;



public class NameGenerator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static int cdNumber = 0;
	private static int pcNumber = 0;
	private static int routerNumber = 0;
	private static int serverNumber = 0;
	private static int nattedServerNumber = 0;
	private static int firewallNumber = 0;
	private static int tapNumber = 0;
	
	public static String getNextName( ItemType type ) {
		String name = "";
		
		switch (type) {
		case SERVER:
			name = "S" + ++serverNumber;
			break;
		case FIREWALL:
			name = "F" + ++firewallNumber;
			break;
		case NATTEDSERVER:
			name = "SNatted" + ++nattedServerNumber;
			break;
		case PC:
			name = "PC" + ++pcNumber;
			break;
		case ROUTER:
			name = "R" + ++routerNumber;
			break;
		case TAP:
			name = "TAP" + ++tapNumber;
			break;
		case COLLISIONDOMAIN:
			name = "CD" + ++cdNumber;
			break;
		}
		
		return name;
	}
}
