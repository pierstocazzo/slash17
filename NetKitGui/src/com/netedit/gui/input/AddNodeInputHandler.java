package com.netedit.gui.input;

import java.awt.event.MouseEvent;

import com.netedit.common.ItemType;
import com.netedit.gui.GuiManager;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class AddNodeInputHandler extends PBasicInputEventHandler {
	
	ItemType nodeType;
	
	public ItemType getNodeType() {
		return nodeType;
	}

	public AddNodeInputHandler( ItemType nodeType ) {
		this.nodeType = nodeType;
	}
	
	@Override
	public void mouseClicked( PInputEvent event ) {
		super.mousePressed(event);
		
		if( event.getButton() == MouseEvent.BUTTON1 ) {
			GuiManager.getInstance().getCanvas().addNode( nodeType, event.getPosition() );
			GuiManager.getInstance().update();
		} else {
			GuiManager.getInstance().getHandler().switchToDefaultHandler();
		}
	}
}
