package project.gui;

import java.util.ArrayList;

import project.core.AbstractCollisionDomain;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;

public class GCollisionDomain extends GNode {

	private static final long serialVersionUID = 1L;

	protected static String imagePath = "data/images/images/collisionDomain.png";
	protected static String selectedImagePath = "data/images/images/collisionDomain_selected.png";
	
	PImage image;
	
	protected ArrayList<GLink> links;
	
	protected AbstractCollisionDomain absCollisionDomain;
	
	public GCollisionDomain( double x, double y, AbstractCollisionDomain collisionDomain, PLayer layer ) {
		super( GNode.domain, layer );
		
		this.links = new ArrayList<GLink>();
		this.absCollisionDomain = collisionDomain;
		
		setSelected(false);
		
		setText(absCollisionDomain.getName());
		
		centerFullBoundsOnPoint(x, y);
		
		update();
	}
	
	private void setImage( String newImage ) {
		if( image != null ) {
			removeChild(image);
		}
		image = new PImage(newImage);
		addChild(image);
		setBounds(image.getBounds());
		image.centerFullBoundsOnPoint(getBounds().getCenterX(), getBounds().getCenterY());
		image.setPickable(false);
	}
	
	public void setSelected( boolean selected ) {
		if( selected ) {
			setImage(selectedImagePath);
		} else {
			setImage(imagePath);
		}
	}
	
	public void update() {
		super.update();
		
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
	
	public String getImagePath() {
		return imagePath;
	}
	
	public AbstractCollisionDomain getLogic() {
		return absCollisionDomain;
	}
	
	public void delete() {
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		super.delete();
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}

