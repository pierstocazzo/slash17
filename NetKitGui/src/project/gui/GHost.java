package project.gui;

import java.util.ArrayList;

import project.common.ItemType;
import project.core.AbstractHost;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;

public class GHost extends GNode {
	private static final long serialVersionUID = 1L;

	protected String imagePath;
	protected String selectedImagePath;
	
	protected PImage image;
	
	protected ArrayList<GLink> links;
	
	AbstractHost absHost;
	
	public GHost( double x, double y, String imagePath, AbstractHost host, PLayer layer ) {
		super( GNode.host, layer );
		
		this.links = new ArrayList<GLink>();
		this.imagePath = imagePath;
		this.absHost = host;
		
		int index = imagePath.lastIndexOf(".");
		selectedImagePath = imagePath.substring( 0, index ) + "_selected" + imagePath.substring( index );
		
		setImage( imagePath );
		
		setText(host.getName());
		
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
		super.setSelected(selected);
		if( selected ) {
			setImage( selectedImagePath );
		} else {
			setImage( imagePath );
		}
	}

	public void update() {
		super.update();
		
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

	public String getImagePath() {
		return imagePath;
	}

	public ItemType getHostType() {
		return absHost.getType();
	}
	
	public AbstractHost getLogic() {
		return absHost;
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
