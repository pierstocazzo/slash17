package project.gui.input;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;

import project.common.ItemType;
import project.gui.GCanvas;

public class HandlerManager {
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;

	GCanvas canvas;
	
	PLayer nodeLayer;
	PLayer linkLayer;
	PLayer areaLayer;
	
	public HandlerManager( GCanvas canvas ) {
		this.canvas = canvas;
		nodeLayer = canvas.getNodeLayer();
		linkLayer = canvas.getLinkLayer();
		areaLayer = canvas.getAreaLayer();
	}
	
	public void switchToAddHandler( ItemType type ) {
		if( type == ItemType.LINK ) {
			if( !currentHandler.equals(addLinkHandler) ) {
				nodeLayer.removeInputEventListener(currentHandler);
				canvas.addInputEventListener(addLinkHandler);
				currentHandler = addLinkHandler;
			}
		} else {
			if( !currentHandler.equals(addNodeHandler) ) {
				nodeLayer.removeInputEventListener(defaultHandler);
				canvas.addInputEventListener(addNodeHandler);
				currentHandler = addNodeHandler;
			}
		}
	}
	
	public void switchToDeleteHandler() {
		if( !currentHandler.equals(deleteHandler) ) {
			nodeLayer.removeInputEventListener(defaultHandler);
			canvas.addInputEventListener(deleteHandler);
			currentHandler = deleteHandler;
		}
	}
	
	public void switchToDefaultHandler() {
		canvas.removeInputEventListener(addNodeHandler);
		canvas.removeInputEventListener(addLinkHandler);
		canvas.removeInputEventListener(deleteHandler);
		nodeLayer.addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
	}
}
