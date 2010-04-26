package gui.input;

import util.Util;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import gui.GNode;

public class DefaultInputHandler extends PDragEventHandler {
	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage( Util.getImageIcon(node.getImageName(), Util.SELECTED).getImage() );
		} catch (Exception e) {}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage( Util.getImageIcon(node.getImageName(), Util.SELECTED).getImage() );
		} catch (Exception e) {}
	}
	
	public void startDrag(PInputEvent event) {
		super.startDrag(event);
		event.setHandled(true);
		event.getPickedNode().moveToFront();
	}
	
	@Override
	public void drag(PInputEvent event) {
		super.drag(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.update();
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
