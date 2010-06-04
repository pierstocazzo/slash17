package com.netedit;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.netedit.common.ItemType;
import com.netedit.core.AbstractFactory;
import com.netedit.core.Factory;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;
import com.netedit.core.nodes.components.AbstractInterface;
import com.netedit.core.project.AbstractProject;
import com.netedit.gui.Lab;
import com.netedit.gui.ProjectHandler;
import com.netedit.gui.nodes.GNode;
import com.netedit.gui.nodes.LabNode;

public class TopologyGenerator {

	int numberOfTopologies;
	int maxFirewalls = 2;
	int maxRouters = 4;
	
	LinkedList<String> areas;
	
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
	
	ArrayList<Topology> topologies;
	Topology currentTopology;
	
	public static void start() {
		new TopologyGenerator( new Factory() ).execute();
	}
	
	public TopologyGenerator(AbstractFactory factory) {
		this.factory = factory;
		topologies = new ArrayList<Topology>();
		areas = new LinkedList<String>();
		r = new Random(System.currentTimeMillis());

		areas.add("DMZ");
		areas.add("RED");
		areas.add("Green1");
		areas.add("Green2");
		
		init();
	}
	
	public void execute() {
		currentTopology = new Topology();
		topologies.add(currentTopology);
		currentTopology.project = factory.createProject("Proj"+topologies.size(), "/home/sal/lab/top");
		Lab.getInstance().setProject(currentTopology.project);
		createTopology();
	}
	
	private void createTopology() {
		Lab.getInstance().clear();
		
		// create main gateway (connected to tap)
		AbstractHost mainGateway = factory.createHost(ItemType.FIREWALL);
		Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, mainGateway));
		currentTopology.project.addHost(mainGateway);
		currentTopology.setMainGateway(mainGateway);
		System.out.println("Main Gateway: " + mainGateway.getName());
		currentTopology.firewallsNumber++;
		// connect the main gateway to the tap (eth0)
		Lab.getInstance().addNode(new LabNode(getBounds(), GNode.domain, currentTopology.TAP));
		currentTopology.project.addCollisionDomain(currentTopology.TAP);
		AbstractLink link1 = factory.createLink(mainGateway, currentTopology.TAP);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link1));
		
		// eth1 - cd1
		AbstractCollisionDomain cd1 = factory.createCollisionDomain(false);
		currentTopology.project.addCollisionDomain(cd1);
		Lab.getInstance().addNode(new LabNode(getBounds(), GNode.domain, cd1));
		AbstractLink link2 = factory.createLink(mainGateway, cd1);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
		cd1.setArea(getRandomArea());
		cd1.setMinimumIp(getMinIp());
		popolate(cd1);
		
		// 
		AbstractCollisionDomain cd2 = factory.createCollisionDomain(false);
		currentTopology.project.addCollisionDomain(cd2);
		Lab.getInstance().addNode(new LabNode(getBounds(), GNode.domain, cd2));
		AbstractLink link3 = factory.createLink(mainGateway, cd2);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link3));
		AbstractHost router = factory.createHost(ItemType.ROUTER);
		currentTopology.project.addHost(router);
		Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, router));
		currentTopology.routersNumber++;
		AbstractLink link5 = factory.createLink(router, cd2);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link5));
		expandRouter(router);
		
		//
		AbstractCollisionDomain cd3 = factory.createCollisionDomain(false);
		currentTopology.project.addCollisionDomain(cd3);
		Lab.getInstance().addNode(new LabNode(getBounds(), GNode.domain, cd3));
		AbstractLink link4 = factory.createLink(mainGateway, cd3);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link4));
		AbstractHost host2 = factory.createHost(
				r.nextBoolean() ? ItemType.FIREWALL : ItemType.ROUTER
		);
		currentTopology.project.addHost(host2);
		Lab.getInstance().addNode(new LabNode(
				getBounds(), GNode.host, host2));
		if( host2.getType() == ItemType.FIREWALL ) {
			currentTopology.firewallsNumber++;
			expandFirewall(host2);
		} else {
			currentTopology.routersNumber++;
			expandRouter(host2);
		}
		AbstractLink link6 = factory.createLink(host2, cd3);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link6));
		
		File file = ProjectHandler.getInstance().saveProject();
		ProjectHandler.getInstance().openProject(file);
	} 
	
	private void popolate(AbstractCollisionDomain cd) {
		System.out.println("Popolating " + cd.getName() +
				" (" + cd.getArea() + ") min Ip " + cd.getMinimumIp());
		
		String area = cd.getArea();
		int hosts = r.nextInt(2) + (area.equals("DMZ") ? 2 : 1);
		// connect one or two host to this domain
		for( int i = 0; i < hosts; i++ ) {
			AbstractHost host;
			int random = r.nextInt(100);
			if( area.equals("DMZ") ) {
				if( random < 70 ) {
					host = factory.createHost(ItemType.SERVER);
				} else {
					host = factory.createHost(ItemType.NATTEDSERVER);
				}
				host.setName(host.getName() + " - " + getRandomService());
			} else {
				host = factory.createHost(ItemType.PC);
			}
			currentTopology.project.addHost(host);
			Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, host));
			AbstractLink link = factory.createLink(host, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
			
			System.out.println(host.getName() + " connected to " + cd.getName());
		}
	}

	private void expandFirewall(AbstractHost fw) {
		currentTopology.firewallsNumber++;
		if( currentTopology.routersNumber < maxRouters ) {
			AbstractCollisionDomain cd = factory.createCollisionDomain(false);
			currentTopology.project.addCollisionDomain(cd);
			Lab.getInstance().addNode(new LabNode(getBounds(), GNode.domain, cd));
			AbstractLink link = factory.createLink(fw, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
			AbstractHost router = factory.createHost(ItemType.ROUTER);
			currentTopology.project.addHost(router);
			Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, router));
			currentTopology.routersNumber++;
			AbstractLink link2 = factory.createLink(router, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
			expandRouter(router);
		}
		
		int fwIfaces = r.nextInt(2) + 1;
		for( int i = 0; i < fwIfaces; i++ ) {
			AbstractCollisionDomain cd = factory.createCollisionDomain(false);
			currentTopology.project.addCollisionDomain(cd);
			LabNode labNode = new LabNode(getBounds(), GNode.domain, cd);
			Lab.getInstance().addNode(labNode);
			AbstractLink link = factory.createLink(fw, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
			if( currentTopology.routersNumber < maxRouters ) {
				int prob = r.nextInt(100);
				if( prob < 40 ) {
					AbstractHost router = factory.createHost(ItemType.ROUTER);
					currentTopology.project.addHost(router);
					Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, router));
					currentTopology.routersNumber++;
					AbstractLink link2 = factory.createLink(router, cd);
					Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
					expandRouter(router);
				} else {
					String area = getRandomArea();
					cd.setArea(area);
					popolate(cd);
				}
			} else {
				String area = getRandomArea();
				cd.setArea(area);
				popolate(cd);
			}
		}
	}

	private void expandRouter(AbstractHost router) {
		String area = getRandomArea();
		currentTopology.routersNumber++;
		if( currentTopology.firewallsNumber < maxFirewalls ) {
			AbstractCollisionDomain cd = factory.createCollisionDomain(false);
			currentTopology.project.addCollisionDomain(cd);
			Lab.getInstance().addNode(new LabNode(getBounds(), GNode.domain, cd));
			AbstractLink link = factory.createLink(router, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
			AbstractHost fw = factory.createHost(ItemType.FIREWALL);
			currentTopology.project.addHost(fw);
			Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, fw));
			currentTopology.firewallsNumber++;
			AbstractLink link2 = factory.createLink(fw, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
			expandFirewall(fw);
		}
		
		int routerIfaces = r.nextInt(2) + 1;
		for( int i = 0; i < routerIfaces; i++ ) {
			AbstractCollisionDomain cd = factory.createCollisionDomain(false);
			currentTopology.project.addCollisionDomain(cd);
			LabNode labNode = new LabNode(getBounds(), GNode.domain, cd);
			Lab.getInstance().addNode(labNode);
			AbstractLink link = factory.createLink(router, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
			if( currentTopology.routersNumber < maxRouters ) {
				int prob = r.nextInt(100);
				if( prob < 40 ) {
					AbstractHost router1 = factory.createHost(ItemType.ROUTER);
					currentTopology.project.addHost(router1);
					Lab.getInstance().addNode(new LabNode(getBounds(), GNode.host, router1));
					currentTopology.routersNumber++;
					AbstractLink link2 = factory.createLink(router1, cd);
					Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
					expandRouter(router1);
				} else {
					cd.setArea(area);
					popolate(cd);
				}
			} else {
				cd.setArea(area);
				popolate(cd);
			}
		}
	}
	
	/** return an int between 4 and 512 */
	private int getMinIp() {
		return r.nextInt(508) + 4;
	}

	private String getRandomService() {
		int nServices = services.size();
		if( nServices == 0 ) 
			return "port: " + r.nextInt(6000) + 1024;
		return services.remove(r.nextInt(nServices));
	}

	private String getRandomArea() {
		int nAreas = areas.size();
		if( nAreas == 0 ) 
			return "NewArea";
		return areas.remove(r.nextInt(nAreas));
	}
	
	private void initIfaces(AbstractHost host, int number) {
		for( int i = 0; i < number; i++ ) {
			host.addInterface(null);
		}
	}
	
	private AbstractInterface getRandomIface(AbstractHost host) {
		ArrayList<AbstractInterface> ifaces = host.getInterfaces();
		return ifaces.get(r.nextInt(ifaces.size()));
	}
	
	private void init() {
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
	
	public class Topology {
		AbstractProject project;
		
		AbstractHost mainGateway; 
		
		AbstractCollisionDomain TAP;

		int firewallsNumber;
		int routersNumber;
		
		public Topology() {
			TAP = factory.createCollisionDomain(true);
			firewallsNumber = 0;
			routersNumber = 0;
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
}
