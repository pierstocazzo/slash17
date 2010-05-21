package com.netedit.gui.input;

import com.netedit.gui.GuiManager;
import com.netedit.gui.gcomponents.GCanvas;
import com.netedit.gui.nodes.GNode;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class DeleteInputHandler extends PBasicInputEventHandler {
	
	GCanvas canvas;
	
	public DeleteInputHandler( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed(PInputEvent event) {
		super.mousePressed(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			canvas.delete( (GNode) event.getPickedNode() );
			GuiManager.getInstance().update();
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setMouseOver(true);
		}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setMouseOver(false);
		}
	}
}
