package gui.input;

import util.Util;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import gui.GCanvas;
import gui.GNode;

public class DeleteInputHandler extends PBasicInputEventHandler {
	
	GCanvas canvas;
	
	public DeleteInputHandler( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(PInputEvent event) {
		super.mousePressed(event);
		
		GNode node = (GNode) event.getPickedNode();
		
		canvas.deleteNode( node );
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage( Util.getImageIcon(node.getImageName(), Util.SELECTED).getImage() );
		} catch (Exception e) {}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage( Util.getImageIcon(node.getImageName(), Util.DEFAULT).getImage() );
		} catch (Exception e) {}
	}
}
