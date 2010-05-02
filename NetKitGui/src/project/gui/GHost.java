package project.gui;

import java.util.ArrayList;

import project.common.ItemType;
import project.core.AbstractHost;


import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class GHost extends PImage {
	private static final long serialVersionUID = 1L;

	protected String image;
	
	protected ArrayList<GLink> links;
	
	protected PText text;
	
	AbstractHost absHost;
	
	PLayer layer;
	
	public GHost( double x, double y, String image, AbstractHost host, PLayer layer ) {
		super(image);
		this.links = new ArrayList<GLink>();
		this.image = image;
		this.absHost = host;
		this.layer = layer;

		setText(host.getName());
		
		centerFullBoundsOnPoint(x, y);
	}
	
	public void update() {
		// update all related links
		for( GLink gl : links ) {
			gl.update();
		}
	}
	
	public void addLink( GLink link ) {
		links.add(link);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}

	private void setText( String name ) {
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
		return absHost.getType();
	}
	
	public AbstractHost getLogic() {
		return absHost;
	}

	public void delete() {
		layer.removeChild(this);
		for( GLink l : links ) {
			l.delete();
		}
		absHost.delete();
	}
}
