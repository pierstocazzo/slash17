package com.netedit.generator;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.netedit.common.ItemType;
import com.netedit.core.AbstractFactory;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;
import com.netedit.core.project.AbstractProject;
import com.netedit.gui.Lab;
import com.netedit.gui.ProjectHandler;
import com.netedit.gui.nodes.GNode;
import com.netedit.gui.nodes.LabNode;

public class Topology {
	int maxFirewalls = 2;
	int maxRouters = 4;
	
	LinkedList<String> areas = new LinkedList<String>();
	{
		areas.add("DMZ-A");
		areas.add("RED");
		areas.add("Green1");
		areas.add("Green2");
	}
	
	boolean[][] matrix;
	
	ArrayList<String> services = new ArrayList<String>();
	{
		services.add("FTP");
		services.add("sFTP");
		services.add("Http");
		services.add("DNS");
		services.add("Telnet");
		services.add("SSH");
		services.add("SMTP");
		services.add("Https");
	}
	
	Random r;
	
	AbstractFactory factory;
	
	AbstractProject project;
	
	Lab lab;
	
	AbstractHost mainGateway; 
	
	AbstractCollisionDomain TAP;

	int firewallsNumber;
	int routersNumber;
	
	public Topology(String name, AbstractFactory factory) {
		this.factory = factory;
		this.r = new Random(System.currentTimeMillis());
		this.firewallsNumber = 0;
		this.routersNumber = 0;
		this.project = factory.createProject(name, "/home/sal/lab/top");
		this.lab = Lab.getInstance();
		this.lab.clear();
		
		initMatrix();
		
		Lab.getInstance().setProject(project);
		createTopology();
	}
	
	private void createTopology() {
		// create main gateway (connected to tap)
		AbstractHost mainGateway = addFirewall();
		setMainGateway(mainGateway);
		// create the tap
		addTap();
		// connect the main gateway to the tap (eth0)
		connect(mainGateway, TAP);
		
		// eth1 - cd1 (random area, random min ip)
		AbstractCollisionDomain cd1 = addDomain(true);
		if( cd1 != null ) {
			connect(mainGateway, cd1);
			popolate(cd1);
		}
		
		// eth2 - cd2 (subnet with a router as gateway)
		AbstractCollisionDomain cd2 = addDomain(false);
		connect(mainGateway, cd2);
		AbstractHost router = addHost(ItemType.ROUTER);
		
		// eth3 - cd3 (subnet with a firewall or a router as gateway)
		AbstractCollisionDomain cd3 = addDomain(false);
		connect(mainGateway, cd3);
		AbstractHost gateway = r.nextBoolean() ? addFirewall() : addRouter();
		
		// expand and populate the subnets
		if( gateway != null ) {
			if( gateway.getType() == ItemType.FIREWALL ) {
				expandFirewall(gateway);
			} else {
				expandRouter(gateway);
			}
			connect(gateway, cd3);
		} else {
			String area = getRandomArea();
			if( area != null ) {
				cd3.setArea(area);
				popolate(cd3);
			}
		}
		if( router != null ) {
			connect(router, cd2);
			expandRouter(router);
		}
		
		// save and open the project
		File file = ProjectHandler.getInstance().saveProject();
		ProjectHandler.getInstance().openProject(file);
	} 
	
	private void popolate(AbstractCollisionDomain cd) {
		System.out.println("Popolating " + cd.getName() +
				" (" + cd.getArea() + ") min Ip " + cd.getMinimumIp());
		
		String area = cd.getArea();
		int hosts = r.nextInt(2) + (area.matches(".*DMZ.*") ? 2 : 1);
		// connect one or two host to this domain
		for( int i = 0; i < hosts; i++ ) {
			AbstractHost host;
			int random = r.nextInt(100);
			if( area.matches(".*DMZ.*") ) {
				if( random < 70 ) {
					host = addHost(ItemType.SERVER);
				} else {
					host = addHost(ItemType.NATTEDSERVER);
				}
				host.setName(host.getName() + " - " + getRandomService());
			} else {
				host = addHost(ItemType.PC);
			}
			connect(host, cd);
			
			System.out.println(host.getName() + " connected to " + cd.getName());
		}
	}

	private void expandFirewall(AbstractHost fw) {
		String area = getRandomArea();
		
		int fwIfaces = r.nextInt(2) + 1;
		for( int i = 0; i < fwIfaces; i++ ) {
			if( r.nextInt(100) < 40 ) {
				AbstractHost anotherRouter = addRouter();
				if( anotherRouter != null ) {
					AbstractCollisionDomain domain = addDomain(false);
					connect(fw, domain);
					connect(anotherRouter, domain);
					expandRouter(anotherRouter);
				}
			} else {
				AbstractCollisionDomain domainInArea = addDomain(area);
				if( domainInArea != null ) {
					connect(fw, domainInArea);
					popolate(domainInArea);
				}
			}
		}
		AbstractHost router = addRouter();
		if( router != null ) {
			AbstractCollisionDomain cd = addDomain(false);
			connect(fw, cd);
			connect(router, cd);
			expandRouter(router);
		}
	}

	private void expandRouter(AbstractHost router) {
		String area = getRandomArea();
		
		int routerIfaces = r.nextInt(2) + 2;
		for( int i = 0; i < routerIfaces; i++ ) {
			if( r.nextInt(100) < 40 ) {
				AbstractHost anotherRouter = addRouter();
				if( anotherRouter != null ) {
					AbstractCollisionDomain routerToRouter = addDomain(false);
					connect(router, routerToRouter);
					connect(anotherRouter, routerToRouter);
					expandFirewall(anotherRouter);
				}
			} else {
				if( area != null ) {
					AbstractCollisionDomain domain = addDomain(area);
					if( domain != null ) {
						connect(router, domain);
						popolate(domain);
					}
				}
			}
		}
		// create a subnet with a firewall if possible
		AbstractHost fw = addFirewall();
		if( fw != null ) {
			AbstractCollisionDomain routerToFw = addDomain(false);
			connect(router, routerToFw);
			connect(fw, routerToFw);
			expandFirewall(fw);
		}
	}
	
	/** return an int between 4 and 512 */
	private int getMinIp() {
		return r.nextInt(508) + 4;
	}

	private String getRandomService() {
		int nServices = services.size();
		if( nServices == 0 ) 
			return "port: " + (r.nextInt(6000) + 1024);
		return services.remove(r.nextInt(nServices));
	}

	private String getRandomArea() {
		int nAreas = areas.size();
		if( nAreas == 0 ) 
			return null;
		return areas.remove(r.nextInt(nAreas));
	}
	
	private void initMatrix() {
		matrix = new boolean[8][8];
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				matrix[i][j] = false;
			}
		}
	}

	private Rectangle2D getBounds() {
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				if( matrix[i][j] == false ) {
					matrix[i][j] = true;
					return new Rectangle(j*120, i*120, 64, 64);
				}
			}
		}
		return null;
	}
	
	private AbstractHost addFirewall() {
		if( firewallsNumber >= maxFirewalls ) 
			return null;
		AbstractHost fw = factory.createHost(ItemType.FIREWALL);
		project.addHost(fw);
		lab.addNode(new LabNode(getBounds(), GNode.host, fw));
		firewallsNumber++;
		return fw;
	}

	private AbstractHost addRouter() {
		if( routersNumber >= maxRouters ) 
			return null;
		AbstractHost router = factory.createHost(ItemType.ROUTER);
		project.addHost(router);
		lab.addNode(new LabNode(getBounds(), GNode.host, router));
		routersNumber++;
		return router;
	}
	
	private AbstractHost addHost(ItemType type) {
		AbstractHost host = factory.createHost(type);
		project.addHost(host);
		lab.addNode(new LabNode(getBounds(), GNode.host, host));
		return host;
	}
	
	private void addTap() {
		TAP = factory.createCollisionDomain(true);
		lab.addNode(new LabNode(getBounds(), GNode.domain, TAP));
		project.addCollisionDomain(TAP);
	}
	
	private AbstractCollisionDomain addDomain(boolean withArea) {
		AbstractCollisionDomain cd;
		if( withArea ) {
			String area = getRandomArea();
			if( area == null ) 
				return null;
			cd = factory.createCollisionDomain(false);
			project.addCollisionDomain(cd);
			lab.addNode(new LabNode(getBounds(), GNode.domain, cd));
			cd.setArea(area);
			cd.setMinimumIp(getMinIp());
		} else {
			cd = factory.createCollisionDomain(false);
			project.addCollisionDomain(cd);
			lab.addNode(new LabNode(getBounds(), GNode.domain, cd));
		}
		return cd;
	}
	
	private AbstractCollisionDomain addDomain(String area) {
		if( area == null ) 
			return null;
		AbstractCollisionDomain cd = factory.createCollisionDomain(false);
		project.addCollisionDomain(cd);
		lab.addNode(new LabNode(getBounds(), GNode.domain, cd));
		cd.setArea(area);
		cd.setMinimumIp(getMinIp());
		return cd;
	}
	
	private void connect( AbstractHost host, AbstractCollisionDomain cd ) {
		AbstractLink link = factory.createLink(host, cd);
		lab.addNode(new LabNode(null, GNode.link, link));
	}
	
	public AbstractCollisionDomain getTAP() {
		return TAP;
	}

	public void setTAP(AbstractCollisionDomain tAP) {
		TAP = tAP;
	}
	
	public int getFirewallsNumber() {
		return firewallsNumber;
	}


	public void setFirewallsNumber(int firewallsNumber) {
		this.firewallsNumber = firewallsNumber;
	}

	public void setMainGateway(AbstractHost mainGateway) {
		this.mainGateway = mainGateway;
	}


	public AbstractHost getMainGateway() {
		return mainGateway;
	}
}
