package input;

import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import gui.GNode;

public class DefaultInputHandler extends PDragEventHandler {
	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		GNode node = (GNode) event.getPickedNode();
		node.setImage(GNode.SELECTED);
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage(GNode.SELECTED);
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
			node.setImage(GNode.DEFAULT);
		} catch (Exception e) {}
	}
}
