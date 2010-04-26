package gui;

import java.util.ArrayList;

import common.ItemType;

import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class GNode extends PImage {
	private static final long serialVersionUID = 1L;

	protected String image;
	
	protected String name;
	
	protected ArrayList<GLink> links;
	
	protected PText text;
	
	protected ItemType type;
	
	public GNode( String name, double x, double y, String image, ItemType type ) {
		super(image);
		this.links = new ArrayList<GLink>();
		this.image = image;
		this.type = type;

		setName(name);
		
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
		this.name = name;
		text = new PText(name);
		text.centerFullBoundsOnPoint((getWidth()/2), getHeight());
		text.setPickable(false);
		text.setScale(1.5);
		this.addChild(text);
	}

	public String getName() {
		return name;
	}
	
	public String getImageName() {
		return image;
	}

	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}
}
