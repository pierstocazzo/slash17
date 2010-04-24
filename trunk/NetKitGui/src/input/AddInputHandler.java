package input;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import gui.GCanvas;
import gui.GNode;

public class AddInputHandler extends PBasicInputEventHandler {
	
	GCanvas canvas;
	GNode node;
	
	public AddInputHandler( GCanvas canvas, GNode node ) {
		this.canvas = canvas;
		this.node = node;
	}
	
	@Override
	public void mousePressed(PInputEvent event) {
		super.mousePressed(event);
		
		canvas.addNode( node, event.getPosition() );
	}
}
