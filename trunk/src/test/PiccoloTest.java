package test;

import java.awt.Image;

import javax.swing.ImageIcon;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventListener;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.PFrame;

public class PiccoloTest extends PFrame {
	private static final long serialVersionUID = 1L;
	
	PNode rootNode;
	
	public void initialize() {	
		// getting the root node
		rootNode = getCanvas().getLayer();
		
		// creating a text node
		PText text = new PText("First Piccolo Test");
		rootNode.addChild(text);
		
		// adding an image
		Image img = new ImageIcon("src/tux.png").getImage();
		final PImage image = new PImage(img);
		// set the image scale
		image.setScale(0.3);
		// set the image position
		image.setX(500);
		image.setY(500);
		// 
		PInputEventListener listener = new PInputEventListener() {
			
			@Override
			public void processEvent(PInputEvent aEvent, int type) {
				if( aEvent.isLeftMouseButton() ) {
					System.out.println("FUck");
				}
			}
		};
		image.addInputEventListener(listener);
		rootNode.addChild(image);
	}

	public static void main(String[] args) {
		new PiccoloTest();
	}
}
