package com.netedit.gui.nodes;

import com.netedit.core.nodes.AbstractCollisionDomain;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;

public class GTap extends GCollisionDomain {
	private static final long serialVersionUID = -3926797807557137395L;
	
	public GTap( double x, double y, AbstractCollisionDomain tap, PLayer layer ) {
		super( x, y, tap, layer );
		
		defaultImage = new PImage("data/images/big/tap.png");
		selectedImage = new PImage("data/images/big/tap_selected.png");
		mouseOverImage = new PImage("data/images/big/tap_mouseover.png");
		
		setImage(defaultImage);
		
		update();
	}
}

