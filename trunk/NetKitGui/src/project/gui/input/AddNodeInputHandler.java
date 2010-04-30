package project.gui.input;

import project.common.ItemType;
import project.gui.GCanvas;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class AddNodeInputHandler extends PBasicInputEventHandler {
	
	GCanvas canvas;
	ItemType nodeType;
	
	public AddNodeInputHandler( GCanvas canvas, ItemType nodeType ) {
		this.canvas = canvas;
		this.nodeType = nodeType;
	}
	
	@Override
	public void mousePressed( PInputEvent event ) {
		super.mousePressed(event);
		
		canvas.addNode( nodeType, event.getPosition() );
	}
}
