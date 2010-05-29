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

public class TopologyGenerator {

	int number;
	
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
			number = Integer.parseInt(givenNumber);
			System.out.println(number);
		}
		// get the network areas to create
		String givenAreas = JOptionPane.showInputDialog("Network Areas, separated by space. (eg: DMZ RED GREEN)");
		if( givenAreas == null ) 
			return false;
		while( !givenAreas.matches("[aA-zZ]+[0-9]*( [aA-zZ]+[0-9]*)+") ) {
			givenAreas = JOptionPane.showInputDialog("Incorrect format.\nNetwork Areas, separated by space. (eg: DMZ RED GREEN)");
		}
		System.out.println(givenAreas);
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
//		} else {
//			// the main gateway is a router
//			currentTopology.mainGateway = factory.createHost(ItemType.ROUTER);
//		}
		factory.createLink(currentTopology.mainGateway, currentTopology.TAP);
		System.out.println("Main Gateway: " + currentTopology.mainGateway.getName());
		
		int mainGatewayInterfaces = r.nextInt(4) + 1;
		for( int i = 0; i < mainGatewayInterfaces; i++ ) {
			AbstractCollisionDomain cd = factory.createCollisionDomain(false);
			currentTopology.mainGateway.addInterface(cd);
			int area = r.nextInt(areas.size());
			cd.setArea(areas.get(area));
			System.out.println("Collision domain: " + cd.getName() + " area: " + cd.getArea()); 
		}
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
		
		
		
		public Topology() {
			TAP = factory.createCollisionDomain(true);
		}
	}
}
