package project.gui;

import project.common.ItemType;
import project.core.AbstractFactory;
import project.core.nodes.AbstractHost;
import project.core.nodes.AbstractLink;
import project.core.project.AbstractProject;
import edu.umd.cs.piccolo.PLayer;

public class GFactory {

	protected static final String serverImage = "data/images/big/server.png";
	protected static final String nattedServerImage = "data/images/big/nattedserver.png";
	protected static final String pcImage = "data/images/big/pc.png";
	protected static final String routerImage = "data/images/big/router.png";
	protected static final String firewallImage = "data/images/big/firewall.png";
	protected static final String tapImage = "data/images/big/tap.png";
	protected static final String collisionDomainImage = "data/images/big/collisionDomain.png";
	
	protected static int serverCounter = 0;
	protected static int nattedServerCounter = 0;
	protected static int pcCounter = 0;
	protected static int routerCounter = 0;
	protected static int firewallCounter = 0;
	protected static int tapCounter = 0;
	protected static int collisionDomainCounter = 0;
	
	protected AbstractFactory absFactory;
	
	protected static GFactory gFactory;
	
	private GFactory( AbstractFactory factory ) {
		this.absFactory = factory;
	}
	
	public static void init( AbstractFactory absFactory ) {
		gFactory = new GFactory(absFactory);
	}
	
	public static GFactory getInstance() {
		return gFactory;
	}
	
	public AbstractProject createProject( String name, String directory ) {
		return absFactory.createProject(name, directory);
	}
	
	public GHost createGHost( ItemType type, double x, double y, PLayer layer ) {
		GHost host = null;
		
		AbstractHost absHost = absFactory.createHost(type);
		
		switch (type) {
		case SERVER:
			host = new GHost( x, y, serverImage, absHost, layer );
			break;
		case FIREWALL:
			host = new GHost( x, y, firewallImage, absHost, layer );
			break;
		case NATTEDSERVER:
			host = new GHost( x, y, nattedServerImage, absHost, layer );
			break;
		case PC:
			host = new GHost( x, y, pcImage, absHost, layer );
			break;
		case ROUTER:
			host = new GHost( x, y, routerImage, absHost, layer );
			break;
		case TAP:
			host = new GHost( x, y, tapImage, absHost, layer );
			break;
		}
		
		return host;
	}
	
	public GCollisionDomain createCollisionDomain( double x, double y, PLayer layer ) {
		return new GCollisionDomain(x, y, absFactory.createCollisionDomain(), layer);
	}
	
	public GLink createLink( GHost host, GCollisionDomain collisionDomain, PLayer layer ) {
		AbstractLink link = absFactory.createLink(host.absHost, collisionDomain.absCollisionDomain);
		if( link != null ) 
			return new GLink(host, collisionDomain, link, layer );
		return null;
	}

	public GArea createArea(double x, double y, PLayer layer) {
		GArea area = new GArea( (int) x, (int) y, layer );
		return area;
	}
}
