package project.gui;

import java.util.ArrayList;

import project.core.AbstractCollisionDomain;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class GCollisionDomain extends PImage {

	private static final long serialVersionUID = 1L;

	protected static String image = "data/images/images/collisionDomain.png";
	
	protected ArrayList<GLink> links;
	
	protected PText text;
	
	protected AbstractCollisionDomain absCollisionDomain;
	
	PLayer layer;
	
	public GCollisionDomain( double x, double y, AbstractCollisionDomain collisionDomain, PLayer layer ) {
		super(image);
		this.links = new ArrayList<GLink>();
		this.absCollisionDomain = collisionDomain;
		this.layer = layer;
		
		centerFullBoundsOnPoint(x, y);
		
		text = new PText(absCollisionDomain.getName());
		text.setPickable(false);
		layer.addChild(text);
		update();
	}
	
	public void update() {
		double x = getGlobalTranslation().getX();
		double y = getGlobalTranslation().getY();
		text.centerFullBoundsOnPoint(x + getWidth()/2, y + getHeight() + 10);
		for( GLink gl : links ) {
			gl.update();
		}
	}
		
	public GLink getLink( int index ) {
		return links.get( index );
	}
	
	public void addLink( GLink link ) {
		links.add(link);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}
	
	public String getImageName() {
		return image;
	}
	
	public AbstractCollisionDomain getLogic() {
		return absCollisionDomain;
	}
	
	public void delete() {
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		layer.removeChild(this);
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}

