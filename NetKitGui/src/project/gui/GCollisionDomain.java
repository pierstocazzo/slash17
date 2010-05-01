package project.gui;

import java.util.ArrayList;

import project.core.AbstractCollisionDomain;

import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class GCollisionDomain extends PImage {

	private static final long serialVersionUID = 1L;

	protected static String image = "data/images/images/collisionDomain.png";
	
	protected ArrayList<GLink> links;
	
	protected PText text;
	
	protected AbstractCollisionDomain absCollisionDomain;
	
	public GCollisionDomain( double x, double y, AbstractCollisionDomain collisionDomain ) {
		super(image);
		this.links = new ArrayList<GLink>();
		this.absCollisionDomain = collisionDomain;

		setName(collisionDomain.getName());
		
		this.centerFullBoundsOnPoint(x, y);
	}
	
	public void update() {
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
	
	public AbstractCollisionDomain getLogic() {
		return absCollisionDomain;
	}
}

