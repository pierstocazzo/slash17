package project.gui.input;

import project.gui.ConfPanel;
import project.gui.GCollisionDomain;
import project.gui.GHost;
import project.util.Util;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class DefaultInputHandler extends PDragEventHandler {
	
	ConfPanel confPanel;
	
	public DefaultInputHandler(ConfPanel confPanel) {
		this.confPanel = confPanel;
	}

	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			((GHost) node).setImage( Util.getImageIcon(((GHost) node).getImageName(), Util.SELECTED).getImage() );
			confPanel.update((GHost) node);
		} else if( node instanceof GCollisionDomain ) {
			((GCollisionDomain) node).setImage( Util.getImageIcon(((GCollisionDomain) node).getImageName(), Util.SELECTED).getImage() );
		}
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
	
	public void startDrag(PInputEvent event) {
		super.startDrag(event);
		event.setHandled(true);
		event.getPickedNode().moveToFront();
	}
	
	@Override
	public void drag(PInputEvent event) {
		super.drag(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			((GHost) node).setImage( Util.getImageIcon(((GHost) node).getImageName(), Util.SELECTED).getImage() );
			((GHost) node).update();
		} else if( node instanceof GCollisionDomain ) {
			((GCollisionDomain) node).setImage( Util.getImageIcon(((GCollisionDomain) node).getImageName(), Util.SELECTED).getImage() );
			((GCollisionDomain) node).update();
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