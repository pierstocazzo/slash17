package com.netedit.generator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

import com.netedit.common.ItemType;
import com.netedit.core.AbstractFactory;
import com.netedit.core.Factory;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.components.AbstractInterface;

public class TopologyGenerator {

	int numberOfTopologies;
	int maxFirewalls = 3;
	int maxSubLevels = 3;
	
	LinkedList<String> areas;
	
	Random r;
	
	AbstractFactory factory;
	
	ArrayList<Topology> topologies;
	Topology currentTopology;
	
	public TopologyGenerator(AbstractFactory factory) {
		this.factory = factory;
		topologies = new ArrayList<Topology>();
		areas = new LinkedList<String>();
		r = new Random(System.currentTimeMillis());
		if( getInput() == false )
			return;
		
		currentTopology = new Topology();
		topologies.add(currentTopology);
		
		getMainGateway();
	}
	
	private boolean getInput() {
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
	
	private void getMainGateway() {
//		boolean firewall = r.nextBoolean();
//		if( firewall ) {
			// the main gateway is a firewall
			currentTopology.mainGateway = factory.createHost(ItemType.FIREWALL);
			currentTopology.numberOfFirewall++;
//		} else {
//			// the main gateway is a router
//			currentTopology.mainGateway = factory.createHost(ItemType.ROUTER);
//		}
		factory.createLink(currentTopology.mainGateway, currentTopology.TAP);
		System.out.println("Main Gateway: " + currentTopology.mainGateway.getName());
		
		// randomize the number of interfaces of the main gateway (1-4)
		int mainGatewayInterfaces = r.nextInt(4) + 2;
		System.out.println("Interfaces of the main gateway: " + mainGatewayInterfaces);
		
		// create a collision domain for the first interface of the gateway
		AbstractCollisionDomain cd = factory.createCollisionDomain(false);
		AbstractInterface iface = currentTopology.mainGateway.addInterface(cd);
		cd.addConnection(iface);
		// get the other gateway connected to this domain
		AbstractHost gw = getGateway(cd);
		System.out.println(cd.getName() + " connect " + 
				currentTopology.mainGateway.getName() + " to " + gw.getName());
		// expand the network from this gateway
		expand(gw);
		
		for( int i = 1; i < mainGatewayInterfaces; i++ ) {
			// create a collision domain for each interface of the gateway
			AbstractCollisionDomain cd1 = factory.createCollisionDomain(false);
			AbstractInterface iface1 = currentTopology.mainGateway.addInterface(cd1);
			cd1.addConnection(iface1);
			// randomize if this is a collision domain connecting two gateway (~30%) or not (~70%)
			int percent = r.nextInt(100);
			if( percent < 30 ) {
				// get the other gateway connected to this domain
				AbstractHost gw1 = getGateway(cd1);
				System.out.println(cd1.getName() + " connect " + 
						currentTopology.mainGateway.getName() + " to " + gw1.getName());
				expand(gw1);
			} else {
				// randomize the network area of this domain
				int area = r.nextInt(areas.size());
				cd1.setArea(areas.get(area));
				System.out.println("Collision domain: " + cd1.getName() + " area: " + cd1.getArea()); 
				// connect an host to this domain
				// randomize the type of host 
				ItemType hostType;
				if( cd1.getArea().equals("DMZ") ) {
					// if the area is a DMZ create a server (~70% server, ~30% nattedserver)
					int randomizeHost = r.nextInt(100);
					if( randomizeHost < 70 ) {
						hostType = ItemType.SERVER;
					} else {
						hostType = ItemType.NATTEDSERVER;
					}
				} else {
					// else pc ~60%, server ~30%, nattedServer ~10%
					int randomizeHost = r.nextInt(100);
					if( randomizeHost < 60 ) {
						hostType = ItemType.PC;
					} else if( randomizeHost > 90 ) {
						hostType = ItemType.NATTEDSERVER;
					} else {
						hostType = ItemType.SERVER;
					}
				}
				AbstractHost pc = factory.createHost(hostType);
				AbstractInterface pcIface = pc.addInterface(cd1);
				cd1.addConnection(pcIface);
				System.out.println(pc.getName() + " connected to " + cd1.getName());
			}
		}
	}
	
	private void expand(AbstractHost gw) {
		currentTopology.sublevels++;
		int ifacesNumber = 2;
		System.out.println("Interfaces of " + gw.getName() + ": " + ifacesNumber); 
		for( int i = 0; i < ifacesNumber; i++ ) {
			// create a collision domain for each interface of the gateway
			AbstractCollisionDomain cd = factory.createCollisionDomain(false);
			AbstractInterface iface = gw.addInterface(cd);
			cd.addConnection(iface);
			// randomize if this is a collision domain connecting two gateway (~30%) or not (~70%)
			int percent = r.nextInt(100);
			if( percent < 30 && currentTopology.sublevels < maxSubLevels ) {
				// get the other gateway connected to this domain
				AbstractHost gw1 = getGateway(cd);
				System.out.println(cd.getName() + " connect " + 
						gw.getName() + " to " + gw1.getName());
				expand(gw1);
			} else {
				// randomize the network area of this domain
				int area = r.nextInt(areas.size());
				cd.setArea(areas.get(area));
				System.out.println("Collision domain: " + cd.getName() + " from " + gw.getName() + " area: " + cd.getArea()); 
				// connect an host to this domain
				// randomize the type of host 
				ItemType hostType;
				if( cd.getArea().equals("DMZ") ) {
					// if the area is a DMZ create a server (~70% server, ~30% nattedserver)
					int randomizeHost = r.nextInt(100);
					if( randomizeHost < 70 ) {
						hostType = ItemType.SERVER;
					} else {
						hostType = ItemType.NATTEDSERVER;
					}
				} else {
					// else pc ~60%, server ~30%, nattedServer ~10%
					int randomizeHost = r.nextInt(100);
					if( randomizeHost < 60 ) {
						hostType = ItemType.PC;
					} else if( randomizeHost > 90 ) {
						hostType = ItemType.NATTEDSERVER;
					} else {
						hostType = ItemType.SERVER;
					}
				}
				AbstractHost pc = factory.createHost(hostType);
				AbstractInterface pcIface = pc.addInterface(cd);
				cd.addConnection(pcIface);
				System.out.println(pc.getName() + " connected to " + cd.getName());
			}
		}
	}

	private AbstractHost getGateway(AbstractCollisionDomain cd) {
		AbstractHost gw;
		// router or firewall? 
		if( currentTopology.numberOfFirewall < maxFirewalls ) {
			if( r.nextInt(100) < 40 ) {
				// ~40% of probability to create a firewall
				gw = factory.createHost(ItemType.FIREWALL);
			} else {
				gw = factory.createHost(ItemType.ROUTER);
			}
		} else {
			gw = factory.createHost(ItemType.ROUTER);
		}
		AbstractInterface iface = gw.addInterface(cd);
		cd.addConnection(iface);
		return gw;
	}

	public static void main(String[] args) {
		new TopologyGenerator(Factory.getInstance());
	}
	
	private class Topology {
		/** the main gateway, connected to the TAP
		 */
		AbstractHost mainGateway; 
		
		/** the TAP 
		 */
		AbstractCollisionDomain TAP;
		
		int numberOfFirewall;
		
		int sublevels;
		
		
		public Topology() {
			TAP = factory.createCollisionDomain(true);
			numberOfFirewall = 0;
			sublevels = 0;
		}
	}
}
