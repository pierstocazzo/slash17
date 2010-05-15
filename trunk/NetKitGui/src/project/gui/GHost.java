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
		
//		setText(host.getName());
		
		centerFullBoundsOnPoint(x, y);
		text = new PText(absHost.getName());
		text.setPickable(false);
		layer.addChild(text);
		update();
		
		layer.addChild(this);
	}
	
	public void update() {
		double x = getGlobalTranslation().getX();
		double y = getGlobalTranslation().getY();
		text.centerFullBoundsOnPoint(x + getWidth()/2, y + getHeight() + 10);
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
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		layer.removeChild(this);
		layer.removeChild(text);
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}
