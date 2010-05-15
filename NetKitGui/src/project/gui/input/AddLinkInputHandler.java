package project.gui.input;

import java.awt.geom.Point2D;

import project.gui.GCanvas;
import project.gui.GCollisionDomain;
import project.gui.GHost;
import project.gui.GNode;
import project.gui.GuiManager;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;

public class AddLinkInputHandler extends PBasicInputEventHandler {
	
	GHost host;
	GCollisionDomain collisionDomain;
	
	GCanvas canvas;
	
	PPath link;	
	
	public AddLinkInputHandler( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed( PInputEvent event ) {
		super.mousePressed(event);
		
		PNode node = event.getPickedNode();
		
		if( node instanceof GHost ) {
			if( collisionDomain != null && host == null ) {
				host = (GHost) node;
				canvas.addLink( host, collisionDomain );
				GuiManager.getInstance().update();
				reset();
			} else {
				if( host == null ) {
					host = (GHost) node;
					link = PPath.createLine(0, 0, 0, 0);
					canvas.addLine( link );
				}
			}
		} else if( node instanceof GCollisionDomain ) {
			if( host != null && collisionDomain == null ) {
				collisionDomain = (GCollisionDomain) node;
				canvas.addLink( host, collisionDomain );
				GuiManager.getInstance().update();
				reset();
			} else {
				if( collisionDomain == null ) {
					collisionDomain = (GCollisionDomain) node;
					link = PPath.createLine(0, 0, 0, 0);
					canvas.addLine( link );
				}
			}
		} else {
			return;
		}
		
		update(event);
	}
	
	public void reset() {
		host = null;
		collisionDomain = null;
		canvas.deleteLink(link);
		link = null;
	}
	
	@Override
	public void mouseMoved(PInputEvent event) {
		update( event );
	}
	
	private void update( PInputEvent e ) {
		Point2D start = null;
		if( host != null ) {
			start = host.getFullBoundsReference().getCenter2D();
		} else if( collisionDomain != null ) {
			start = collisionDomain.getFullBoundsReference().getCenter2D();
		}
		if( start != null ) {
			Point2D end = e.getPosition();
			link.reset();
			link.moveTo((float)start.getX(), (float)start.getY());
			link.lineTo((float)end.getX(), (float)end.getY());
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		GNode node = (GNode) event.getPickedNode();
		node.setSelected(true);
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		GNode node = (GNode) event.getPickedNode();
		node.setSelected(false);
	}
}
