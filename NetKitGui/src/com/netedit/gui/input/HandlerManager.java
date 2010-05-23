package com.netedit.gui.input;

import java.awt.Cursor;

import com.netedit.common.ItemType;
import com.netedit.gui.GuiManager;
import com.netedit.gui.gcomponents.GCanvas;
import com.netedit.gui.gcomponents.GFrame;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;


public class HandlerManager {
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;

	GCanvas canvas;
	GFrame frame;
	
	PLayer nodeLayer;
	PLayer linkLayer;
	PLayer areaLayer;
	
	public void start() {
		this.canvas = GuiManager.getInstance().getCanvas();
		this.frame = GuiManager.getInstance().getFrame();
		nodeLayer = canvas.getNodeLayer();
		linkLayer = canvas.getLinkLayer();
		areaLayer = canvas.getAreaLayer();
		
		defaultHandler = new DefaultInputHandler();
		deleteHandler = new DeleteInputHandler();
		addLinkHandler = new AddLinkInputHandler();
		
		switchToDefaultHandler();
	}
	
	public void switchToAddHandler( ItemType type ) {
		removeCurrentHandler();
		
		if( type == ItemType.LINK ) {
			canvas.addInputEventListener(addLinkHandler);
			currentHandler = addLinkHandler;
		} else {
			canvas.addInputEventListener(addNodeHandler);
			currentHandler = addNodeHandler;
		}
	}
	
	public void switchToDeleteHandler() {
		removeCurrentHandler();
		
		canvas.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		canvas.addInputEventListener(deleteHandler);
		currentHandler = deleteHandler;
	}
	
	public void switchToDefaultHandler() {
		removeCurrentHandler();
		
		canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		canvas.addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
	}
	
	private void removeCurrentHandler() {
		canvas.removeInputEventListener(currentHandler);
	}
	
	public void adding( ItemType type ) {
		if( (currentHandler == addNodeHandler && addNodeHandler.getNodeType() == type) ||
				(currentHandler == addLinkHandler && type == ItemType.LINK) ) {
			switchToDefaultHandler();
			return;
		}
		if( type != ItemType.LINK ) {
			// remove previously created addhandler
			if( currentHandler.equals(addNodeHandler) ) {
				canvas.removeInputEventListener(addNodeHandler);
			}
			// create a new addhandler for this node type
			addNodeHandler = new AddNodeInputHandler(type);
		}
		switchToAddHandler( type );
	}
	
	public void deleting() {
		if( currentHandler == deleteHandler )
			switchToDefaultHandler();
		else 
			switchToDeleteHandler();
	}
}
