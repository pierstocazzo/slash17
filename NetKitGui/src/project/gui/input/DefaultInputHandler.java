package project.gui.input;

import project.gui.GCollisionDomain;
import project.gui.GHost;
import project.gui.GuiManager;
import project.util.Util;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

public class DefaultInputHandler extends PDragEventHandler {
	
	@Override
	public void mouseClicked(PInputEvent event) {
		super.mouseClicked(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			GHost host = ((GHost) node);
			host.setImage( Util.getImageIcon( host.getImageName(), Util.SELECTED).getImage() );
			// expand this host's configuration in the trees
			GuiManager.getInstance().getConfPanel().selectHost( host.getLogic().getName() );
			
		} else if( node instanceof GCollisionDomain ) {
			GCollisionDomain cd = ((GCollisionDomain) node);
			cd.setImage( Util.getImageIcon( cd.getImageName(), Util.SELECTED).getImage() );
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			GHost host = ((GHost) node);
			host.setImage( Util.getImageIcon( host.getImageName(), Util.SELECTED).getImage() );
			
		} else if( node instanceof GCollisionDomain ) {
			GCollisionDomain cd = ((GCollisionDomain) node);
			cd.setImage( Util.getImageIcon( cd.getImageName(), Util.SELECTED).getImage() );
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
			GHost host = ((GHost) node);
			host.setImage( Util.getImageIcon( host.getImageName(), Util.SELECTED).getImage() );
			host.update();
			
		} else if( node instanceof GCollisionDomain ) {
			GCollisionDomain cd = ((GCollisionDomain) node);
			cd.setImage( Util.getImageIcon( cd.getImageName(), Util.SELECTED).getImage() );
			cd.update();
		}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			GHost host = ((GHost) node);
			host.setImage( Util.getImageIcon( host.getImageName(), Util.DEFAULT).getImage() );
			
		} else if( node instanceof GCollisionDomain ) {
			GCollisionDomain cd = ((GCollisionDomain) node);
			cd.setImage( Util.getImageIcon( cd.getImageName(), Util.DEFAULT).getImage() );
		}
	}
}
