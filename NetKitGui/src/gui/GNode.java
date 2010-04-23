package gui;

import java.util.ArrayList;

import edu.umd.cs.piccolo.nodes.PImage;

public class GNode extends PImage {
	private static final long serialVersionUID = 1L;

	protected PImage image;
	
	protected PImage selectedImage;
	
	protected PImage deletedImage;
	
	protected String id;
	
	protected ArrayList<GLink> links;
	
	public GNode( String name, int x, int y, String image ) {
		super(image);
		this.id = name;
		this.links = new ArrayList<GLink>();
		this.setX(x);
		this.setY(y);
		this.image = new PImage(image);
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

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
