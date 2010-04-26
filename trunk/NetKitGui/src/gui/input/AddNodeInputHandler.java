package gui.input;

import common.ItemType;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import gui.GCanvas;

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
