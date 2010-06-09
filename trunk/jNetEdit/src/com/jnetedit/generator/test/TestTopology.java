package com.jnetedit.generator.test;

import java.util.LinkedList;

import com.jnetedit.core.Factory;
import com.jnetedit.generator.Topology;
import com.jnetedit.generator.VeryBasicLayouting;

import junit.framework.TestCase;

public class TestTopology extends TestCase {

	public TestTopology(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCreateTopology() {
		LinkedList<String> areas = new LinkedList<String>();
		areas.add("red");
		areas.add("dmz");
		areas.add("green");
		Topology t = new Topology("test", "tests", areas, new Factory(), new VeryBasicLayouting())
			.createTopology();
		
		assertNotNull(t.getTAP());
		assertNotNull(t.getMainGateway());
		
		assertEquals(2, t.getFirewallsNumber());
		
	}

}
