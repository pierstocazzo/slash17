package gui;

import java.util.ArrayList;

import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;

public class GNode extends PImage {
	private static final long serialVersionUID = 1L;

	public static final String SELECTED = "_selected";
	public static final String DELETED = "Del";
	public static final String STARTED = "Started";
	public static final String STARTEDSELECTED = "StartedSelected";
	public static final String DEFAULT = "";
	
	protected String image;
	
	protected String name;
	
	protected ArrayList<GLink> links;
	
	protected PText text;
	
	public GNode( String name, int x, int y, String image ) {
		super(image);
		this.links = new ArrayList<GLink>();
		this.image = image;

		setName(name);
		
		this.centerFullBoundsOnPoint(x, y);
	}
	
	public void update() {
		// update all related links
		for( GLink gl : links ) {
			gl.update();
		}
	}
		
	public void setImage( String type ) {
		int index = image.lastIndexOf(".");
		String newImage = image.substring( 0, index ) + type + image.substring( index );
		super.setImage( newImage );
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
}
