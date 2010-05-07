package project.gui.input;

import project.gui.GCanvas;
import project.gui.GCollisionDomain;
import project.gui.GHost;
import project.gui.GuiManager;
import project.util.Util;
import edu.umd.cs.piccolo.PNode;
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
		
		canvas.delete( event.getPickedNode() );
		GuiManager.getInstance().update();
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			((GHost) node).setImage( Util.getImageIcon(((GHost) node).getImageName(), Util.SELECTED).getImage() );
		} else if( node instanceof GCollisionDomain ) {
			((GCollisionDomain) node).setImage( Util.getImageIcon(((GCollisionDomain) node).getImageName(), Util.SELECTED).getImage() );
		}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			((GHost) node).setImage( Util.getImageIcon(((GHost) node).getImageName(), Util.DEFAULT).getImage() );
		} else if( node instanceof GCollisionDomain ) {
			((GCollisionDomain) node).setImage( Util.getImageIcon(((GCollisionDomain) node).getImageName(), Util.DEFAULT).getImage() );
		}
	}
}
