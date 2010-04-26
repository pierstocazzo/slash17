package gui;

import common.ItemType;

public class GNodeFactory {

	private static final String serverImage = "data/images/images/server.png";
	private static final String nattedServerImage = "data/images/images/nattedserver.png";
	private static final String pcImage = "data/images/images/pc.png";
	private static final String routerImage = "data/images/images/router.png";
	private static final String firewallImage = "data/images/images/firewall.png";
	private static final String tapImage = "data/images/images/tap.png";
	private static final String collisionDomainImage = "data/images/images/collisionDomain.png";
	
	private static int serverCounter = 0;
	private static int nattedServerCounter = 0;
	private static int pcCounter = 0;
	private static int routerCounter = 0;
	private static int firewallCounter = 0;
	private static int tapCounter = 0;
	private static int collisionDomainCounter = 0;
	
	
	public static GNode createGNode( ItemType type, double x, double y ) {
		GNode node = null;
		
		switch (type) {
		case SERVER:
			node = new GNode( getNextName(type), x, y, serverImage, ItemType.SERVER );
			break;
		case FIREWALL:
			node = new GNode( getNextName(type), x, y, firewallImage, ItemType.FIREWALL );
			break;
		case NATTEDSERVER:
			node = new GNode( getNextName(type), x, y, nattedServerImage, ItemType.NATTEDSERVER );
			break;
		case PC:
			node = new GNode( getNextName(type), x, y, pcImage, ItemType.PC );
			break;
		case ROUTER:
			node = new GNode( getNextName(type), x, y, routerImage, ItemType.ROUTER );
			break;
		case TAP:
			node = new GNode( getNextName(type), x, y, tapImage, ItemType.TAP );
			break;
		case COLLISIONDOMAIN:
			node = new GNode( getNextName(type), x, y, collisionDomainImage, ItemType.COLLISIONDOMAIN );
			break;
		case AREA:
			node = new GNode( getNextName(type), x, y, collisionDomainImage, ItemType.AREA );
			break;
		case LINK:
			// TODO case link
			node = new GNode( getNextName(type), x, y, collisionDomainImage, ItemType.LINK );
			break;
		}
		
		return node;
	}
	
	public static GNode createGNode( ItemType type, String name, int x, int y ) {
		GNode node = null;
		
		switch (type) {
		case SERVER:
			node = new GNode( name, x, y, serverImage, ItemType.SERVER );
			break;
		case FIREWALL:
			node = new GNode( name, x, y, firewallImage, ItemType.FIREWALL );
			break;
		case NATTEDSERVER:
			node = new GNode( name, x, y, nattedServerImage, ItemType.NATTEDSERVER );
			break;
		case PC:
			node = new GNode( name, x, y, pcImage, ItemType.PC );
			break;
		case ROUTER:
			node = new GNode( name, x, y, routerImage, ItemType.ROUTER );
			break;
		case TAP:
			node = new GNode( name, x, y, tapImage, ItemType.TAP );
			break;
		case COLLISIONDOMAIN:
			node = new GNode( name, x, y, collisionDomainImage, ItemType.COLLISIONDOMAIN );
			break;
		case AREA:
			node = new GNode( name, x, y, collisionDomainImage, ItemType.AREA );
			break;
		case LINK:
			// TODO case link
			node = new GNode( name, x, y, collisionDomainImage, ItemType.LINK );
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
			name = "F" + ++firewallCounter;
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
