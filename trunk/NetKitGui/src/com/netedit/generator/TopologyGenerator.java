package com.netedit.generator;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import com.netedit.common.ItemType;
import com.netedit.core.AbstractFactory;
import com.netedit.core.Factory;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;
import com.netedit.core.nodes.components.AbstractInterface;
import com.netedit.gui.Lab;
import com.netedit.gui.ProjectHandler;
import com.netedit.gui.gcomponents.GCanvas;
import com.netedit.gui.nodes.GNode;
import com.netedit.gui.nodes.LabNode;

public class TopologyGenerator {

	int numberOfTopologies;
	int maxFirewalls = 2;
	int maxRouters = 4;
	
	LinkedList<String> areas;
	
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
	GCanvas canvas;
	
	/** MAIN **/
	public static void main(String[] args) {
		new TopologyGenerator(Factory.getInstance(), null).execute();
	}
	
	public static void start(GCanvas canvas) {
		new TopologyGenerator(Factory.getInstance(), canvas).execute();
	}
	
	public TopologyGenerator(AbstractFactory factory, GCanvas canvas) {
		this.factory = factory;
		this.canvas = canvas;
		topologies = new ArrayList<Topology>();
		areas = new LinkedList<String>();
		r = new Random(System.currentTimeMillis());
//		if( getInput() == false )
//			return;
		
		areas.add("DMZ");
		areas.add("RED");
		areas.add("Green1");
		areas.add("Green2");
	}
	
	public boolean getInput() {
		// get the number of topologies to create
		String givenNumber = JOptionPane.showInputDialog("Number of topologies");
		if( givenNumber == null )
			return false;
		if( givenNumber.matches("[1-9][0-9]*") ) {
			numberOfTopologies = Integer.parseInt(givenNumber);
			System.out.println("Creation of " + numberOfTopologies + " topologies");
		}
		// get the network areas to create
		String givenAreas = JOptionPane.showInputDialog("Network Areas, separated by space. (eg: DMZ RED GREEN)");
		if( givenAreas == null ) 
			return false;
		while( !givenAreas.matches("[aA-zZ]+[0-9]*( [aA-zZ]+[0-9]*)+") ) {
			givenAreas = JOptionPane.showInputDialog("Incorrect format.\nNetwork Areas, separated by space. (eg: DMZ RED GREEN)");
		}
		System.out.println("Network areas to use: " + givenAreas);
		String[] ss = givenAreas.split(" ");
		for( String s : ss ) 
			areas.add(s);
		return true;
	}
	
	public void execute() {
		currentTopology = new Topology();
		topologies.add(currentTopology);
		
		createTopology();
	}
	
	private void createTopology() {
		Lab.getInstance().clear();
		
		// create main gateway (connected to tap)
		AbstractHost mainGateway = factory.createHost(ItemType.FIREWALL);
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, mainGateway));
		currentTopology.setMainGateway(mainGateway);
		System.out.println("Main Gateway: " + mainGateway.getName());
		
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, currentTopology.TAP));
		// connect the main gateway to the tap (eth0)
		AbstractLink link1 = factory.createLink(mainGateway, currentTopology.TAP);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link1));
		
		// create a collision domain for the first interface of the gateway
		AbstractCollisionDomain cd1 = factory.createCollisionDomain(false);
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd1));
		AbstractLink link2 = factory.createLink(mainGateway, cd1);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
		cd1.setArea(getRandomArea());
		cd1.setMinimumIp(r.nextInt(512));
		
		// 
		AbstractCollisionDomain cd2 = factory.createCollisionDomain(false);
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd2));
		AbstractLink link3 = factory.createLink(mainGateway, cd2);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link3));
		AbstractHost host1 = factory.createHost(ItemType.ROUTER);
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, host1));
		currentTopology.routersNumber++;
		
		//
		AbstractCollisionDomain cd3 = factory.createCollisionDomain(false);
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd3));
		AbstractLink link4 = factory.createLink(mainGateway, cd3);
		Lab.getInstance().addNode(new LabNode(null, GNode.link, link4));
		AbstractHost host2 = factory.createHost(
				r.nextBoolean() ? ItemType.FIREWALL : ItemType.ROUTER
		);
		Lab.getInstance().addNode(new LabNode(
				new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, host2));
		if( host2.getType() == ItemType.FIREWALL ) {
			currentTopology.firewallsNumber++;
		} else {
			currentTopology.routersNumber++;
		}
		
		if( r.nextBoolean() ) {
			cd3.setArea(getRandomArea());
			cd3.setMinimumIp(r.nextInt(256));
			popolate(cd3);
		}
		
		popolate(cd1);
		expand(host1);
		expand(host2);
		
		System.out.println("\nRouterNumber " + currentTopology.routersNumber + 
				" firewall number " + currentTopology.firewallsNumber);
		
		ProjectHandler.getInstance().saveProject();
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
				if( random < 90 ) {
					host = factory.createHost(ItemType.PC);
				} else if( random > 98 ) {
					host = factory.createHost(ItemType.NATTEDSERVER);
					host.setName(host.getName() + " - " + getRandomService());
				} else {
					host = factory.createHost(ItemType.SERVER);
					host.setName(host.getName() + " - " + getRandomService());
				}
			}
			Lab.getInstance().addNode(new LabNode(
					new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, host));
			AbstractLink link = factory.createLink(host, cd);
			Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
			
			System.out.println(host.getName() + " connected to " + cd.getName());
		}
	}

	private String getRandomService() {
		int nServices = services.size();
		if( nServices == 0 ) 
			return null;
		return services.remove(r.nextInt(nServices));
	}

	private String getRandomArea() {
		int nAreas = areas.size();
		if( nAreas == 0 ) 
			return null;
		return areas.remove(r.nextInt(nAreas));
	}

	private void expand(AbstractHost gw) {
		System.out.println("Expand " + gw.getName());
		
		if( gw.getType() == ItemType.FIREWALL ) {
			if( currentTopology.routersNumber < maxRouters ) {
				AbstractCollisionDomain cd = factory.createCollisionDomain(false);
				Lab.getInstance().addNode(new LabNode(
						new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd));
				AbstractLink link = factory.createLink(gw, cd);
				Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
				AbstractHost router = factory.createHost(ItemType.ROUTER);
				Lab.getInstance().addNode(new LabNode(
						new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, router));
				currentTopology.routersNumber++;
				AbstractLink link2 = factory.createLink(router, cd);
				Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
				expand(router);
			}
			
			int fwIfaces = r.nextInt(2) + 1;
			for( int i = 0; i < fwIfaces; i++ ) {
				AbstractCollisionDomain cd = factory.createCollisionDomain(false);
				Lab.getInstance().addNode(new LabNode(
						new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd));
				AbstractLink link = factory.createLink(gw, cd);
				Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
				if( currentTopology.routersNumber < maxRouters ) {
					int prob = r.nextInt(100);
					if( prob < 40 ) {
						AbstractHost router = factory.createHost(ItemType.ROUTER);
						Lab.getInstance().addNode(new LabNode(
								new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, router));
						currentTopology.routersNumber++;
						AbstractLink link2 = factory.createLink(router, cd);
						Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
						expand(router);
					} else {
						String area = getRandomArea();
						if( area != null ) {
							cd.setArea(area);
							popolate(cd);
						} else
							cd.delete();
					}
				} else {
					String area = getRandomArea();
					if( area != null ) {
						cd.setArea(area);
						popolate(cd);
					} else
						cd.delete();
				}
			}
		} else {
			currentTopology.routersNumber++;
			if( currentTopology.firewallsNumber < maxFirewalls ) {
				AbstractCollisionDomain cd = factory.createCollisionDomain(false);
				Lab.getInstance().addNode(new LabNode(
						new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd));
				AbstractLink link = factory.createLink(gw, cd);
				Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
				AbstractHost fw = factory.createHost(ItemType.FIREWALL);
				Lab.getInstance().addNode(new LabNode(
						new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, fw));
				currentTopology.firewallsNumber++;
				AbstractLink link2 = factory.createLink(fw, cd);
				Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
				expand(fw);
			}
			
			int routerIfaces = r.nextInt(2) + 1;
			for( int i = 0; i < routerIfaces; i++ ) {
				AbstractCollisionDomain cd = factory.createCollisionDomain(false);
				Lab.getInstance().addNode(new LabNode(
						new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.domain, cd));
				AbstractLink link = factory.createLink(gw, cd);
				Lab.getInstance().addNode(new LabNode(null, GNode.link, link));
				if( currentTopology.routersNumber < maxRouters ) {
					int prob = r.nextInt(100);
					if( prob < 40 ) {
						AbstractHost router = factory.createHost(ItemType.ROUTER);
						Lab.getInstance().addNode(new LabNode(
								new Rectangle(r.nextInt(600), r.nextInt(600), 64, 64), GNode.host, router));
						currentTopology.routersNumber++;
						AbstractLink link2 = factory.createLink(router, cd);
						Lab.getInstance().addNode(new LabNode(null, GNode.link, link2));
						expand(router);
					} else {
						String area = getRandomArea();
						if( area != null ) {
							cd.setArea(area);
							popolate(cd);
						} else
							cd.delete();
					}
				} else {
					String area = getRandomArea();
					if( area != null ) {
						cd.setArea(area);
						popolate(cd);
					} else
						cd.delete();
				}
			}
		}
	}

	public class Topology {
		/** the main gateway, connected to the TAP
		 */
		private AbstractHost mainGateway; 
		
		/** the TAP 
		 */
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
