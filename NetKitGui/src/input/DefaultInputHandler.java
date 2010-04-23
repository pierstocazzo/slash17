package input;

import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import gui.GNode;

public class DefaultInputHandler extends PDragEventHandler {
	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		event.getPickedNode().setTransparency(0.8f);
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		event.getPickedNode().setTransparency(0.8f);
	}
	
	protected void startDrag(PInputEvent e) {
		super.startDrag(e);
		e.setHandled(true);
		e.getPickedNode().moveToFront();
	}
	
	@Override
	public void drag(PInputEvent event) {
		super.drag(event);
		
		GNode node = (GNode) event.getPickedNode();
		node.update();
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		event.getPickedNode().setTransparency(1);
	}
}
