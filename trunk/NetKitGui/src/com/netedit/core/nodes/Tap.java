package com.netedit.core.nodes;

public class Tap extends CollisionDomain {

	public Tap(String name) {
		super(name);
	}
	
	@Override
	public boolean isTap() {
		return true;
	}
}
