package com.netedit.gui.input;

import java.awt.event.MouseEvent;

import com.netedit.common.ItemType;
import com.netedit.gui.GuiManager;
import com.netedit.gui.gcomponents.GCanvas;


import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class AddNodeInputHandler extends PBasicInputEventHandler {
	
	GCanvas canvas;
	ItemType nodeType;
	
	public ItemType getNodeType() {
		return nodeType;
	}

	public AddNodeInputHandler( GCanvas canvas, ItemType nodeType ) {
		this.canvas = canvas;
		this.nodeType = nodeType;
	}
	
	@Override
	public void mouseClicked( PInputEvent event ) {
		super.mousePressed(event);
		
		if( event.getButton() == MouseEvent.BUTTON1 ) {
			canvas.addNode( nodeType, event.getPosition() );
			GuiManager.getInstance().update();
		} else {
			canvas.switchToDefaultHandler();
		}
	}
}
