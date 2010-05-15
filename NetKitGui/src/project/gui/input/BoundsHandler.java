package project.gui.input;

import project.gui.GArea;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class BoundsHandler extends PBasicInputEventHandler {

	GArea selectedNode;
	
	@Override
	public void mouseClicked( PInputEvent e ) {
		if( e.getPickedNode() instanceof GArea ) {
			final GArea node = (GArea) e.getPickedNode();
			if( selectedNode != null ) {
				selectedNode.unSelect();
			}
			selectedNode = node;
			selectedNode.select();
		} else {
			if( selectedNode != null ) {
				selectedNode.unSelect();
			}
		}
	}
}