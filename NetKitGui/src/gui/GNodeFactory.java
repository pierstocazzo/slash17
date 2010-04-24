package gui;

import common.ItemType;

public class GNodeFactory {

	private static final String serverImage = "data/images/128x128/server.png";
	private static final String nattedServerImage = "data/images/128x128/server.png";
	private static final String pcImage = "data/images/128x128/system.png";
	private static final String routerImage = "data/images/128x128/router.png";
	private static final String firewallImage = "data/images/128x128/firewall.png";
	private static final String tapImage = "data/images/128x128/tap.png";
	private static final String collisionDomainImage = "data/images/128x128/hub.png";
	
	private static int serverCounter = 0;
	private static int nattedServerCounter = 0;
	private static int pcCounter = 0;
	private static int routerCounter = 0;
	private static int firewallCounter = 0;
	private static int tapCounter = 0;
	private static int collisionDomainCounter = 0;
	
	
	public static GNode createGNode( ItemType type, int x, int y ) {
		GNode node = null;
		
		switch (type) {
		case SERVER:
			node = new GNode( getNextName(type), x, y, serverImage );
			break;
		case FIREWALL:
			node = new GNode( getNextName(type), x, y, firewallImage );
			break;
		case NATTEDSERVER:
			node = new GNode( getNextName(type), x, y, nattedServerImage );
			break;
		case PC:
			node = new GNode( getNextName(type), x, y, pcImage );
			break;
		case ROUTER:
			node = new GNode( getNextName(type), x, y, routerImage );
			break;
		case TAP:
			node = new GNode( getNextName(type), x, y, tapImage );
			break;
		case COLLISIONDOMAIN:
			node = new GNode( getNextName(type), x, y, collisionDomainImage );
			break;
		case AREA:
			node = new GNode( getNextName(type), x, y, collisionDomainImage );
			break;
		case LINK:
			// TODO case link
			node = new GNode( getNextName(type), x, y, collisionDomainImage );
			break;
		}
		
		return node;
	}
	
	public static GNode createGNode( ItemType type, String name, int x, int y ) {
		GNode node = null;
		
		switch (type) {
		case SERVER:
			node = new GNode( name, x, y, serverImage );
			break;
		case FIREWALL:
			node = new GNode( name, x, y, firewallImage );
			break;
		case NATTEDSERVER:
			node = new GNode( name, x, y, nattedServerImage );
			break;
		case PC:
			node = new GNode( name, x, y, pcImage );
			break;
		case ROUTER:
			node = new GNode( name, x, y, routerImage );
			break;
		case TAP:
			node = new GNode( name, x, y, tapImage );
			break;
		case COLLISIONDOMAIN:
			node = new GNode( name, x, y, collisionDomainImage );
			break;
		case AREA:
			node = new GNode( name, x, y, collisionDomainImage );
			break;
		case LINK:
			// TODO case link
			node = new GNode( name, x, y, collisionDomainImage );
			break;
		}
		
		return node;
	}

	private static String getNextName(ItemType type) {
		String name = "";
		
		switch (type) {
		case SERVER:
			name = "S" + ++serverCounter;
			break;
		case FIREWALL:
			name = "S" + ++firewallCounter;
			break;
		case NATTEDSERVER:
			name = "SNatted" + ++nattedServerCounter;
			break;
		case PC:
			name = "PC" + ++pcCounter;
			break;
		case ROUTER:
			name = "R" + ++routerCounter;
			break;
		case TAP:
			name = "TAP" + ++tapCounter;
			break;
		case COLLISIONDOMAIN:
			name = "CD" + ++collisionDomainCounter ;
			break;
		}
		
		return name;
	}
}
