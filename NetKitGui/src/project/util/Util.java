package project.util;

import edu.umd.cs.piccolo.nodes.PImage;

public class Util {

	public static final String SELECTED = "_selected";
	public static final String DELETED = "_deleted";
	public static final String STARTED = "_started";
	public static final String STARTEDSELECTED = "_started_selected";
	public static final String DEFAULT = "";
	
	public static PImage getImage( String image, String type ) {
		int index = image.lastIndexOf(".");
		String newImage = image.substring( 0, index ) + type + image.substring( index );
		return new PImage( newImage );
	}
}
