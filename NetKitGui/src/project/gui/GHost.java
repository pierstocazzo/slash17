package project.gui;

import java.util.ArrayList;

import project.common.ItemType;
import project.core.AbstractHost;


import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class GHost extends PImage {
	private static final long serialVersionUID = 1L;

	protected String image;
	
	protected ArrayList<GLink> links;
	
	protected PText text;
	
	AbstractHost host;
	
	public GHost( double x, double y, String image, AbstractHost host ) {
		super(image);
		this.links = new ArrayList<GLink>();
		this.image = image;
		this.host = host;

		setName(host.getName());
		
		this.centerFullBoundsOnPoint(x, y);
	}
	
	public void update() {
		// update all related links
		for( GLink gl : links ) {
			gl.update();
		}
	}
		
	public GLink getLink( int index ) {
		return links.get( index );
	}
	
	public void addLink( GLink edge ) {
		links.add(edge);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}

	public void setName( String name ) {
		text = new PText(name);
		text.centerFullBoundsOnPoint((getWidth()/2), getHeight());
		text.setPickable(false);
		text.setScale(1.5);
		this.addChild(text);
	}

	public String getImageName() {
		return image;
	}

	public ItemType getType() {
		return host.getType();
	}
}
