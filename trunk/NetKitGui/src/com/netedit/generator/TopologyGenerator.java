package com.netedit.generator;

import javax.swing.JOptionPane;

public class TopologyGenerator {

	int number;
	
	String[] areas;
	
	public TopologyGenerator() {
		String givenNumber = JOptionPane.showInputDialog("Number of topologies");
		if( givenNumber != null && givenNumber.matches("[1-9][0-9]*") ){
			number = Integer.parseInt(givenNumber);
			System.out.println(number);
		}
		String givenAreas = JOptionPane.showInputDialog("Network Areas, separated by space. (eg: DMZ RED GREEN)");
		if( givenAreas == null ) 
			return;
		
		while( !givenAreas.matches("[aA-zZ]+[0-9]*( [aA-zZ]+[0-9]*)+") ) {
			givenAreas = JOptionPane.showInputDialog("Incorrect format.\nNetwork Areas, separated by space. (eg: DMZ RED GREEN)");
		}
		System.out.println(givenAreas);
		areas = givenAreas.split(" ");
	}
	
	public static void main(String[] args) {
		new TopologyGenerator();
	}
}
