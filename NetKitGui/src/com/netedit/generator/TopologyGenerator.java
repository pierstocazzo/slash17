package com.netedit.generator;

import java.util.ArrayList;

import com.netedit.core.AbstractFactory;
import com.netedit.core.Factory;

public class TopologyGenerator {

	ArrayList<Topology> topologies;
	
	public static void start() {
		new TopologyGenerator( new Factory() );
	}
	
	public TopologyGenerator(AbstractFactory factory) {
		topologies = new ArrayList<Topology>();
		
		Topology t = new Topology("proj" + topologies.size(), factory);
		topologies.add(t);
	}
}
