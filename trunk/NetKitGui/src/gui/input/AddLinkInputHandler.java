package gui.input;

import java.awt.geom.Point2D;

import util.Util;

import common.ItemType;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import gui.GCanvas;
import gui.GNode;

public class AddLinkInputHandler extends PBasicInputEventHandler {
	
	GNode node;
	GNode collisionDomain;
	
	GCanvas canvas;
	
	PPath link;	
	
	public AddLinkInputHandler( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	@Override
	public void mousePressed( PInputEvent event ) {
		super.mousePressed(event);
		
		try {
			GNode pressed = (GNode) event.getPickedNode();
			
			if( pressed.getType() == ItemType.AREA || pressed.getType() == ItemType.LINK ) 
				return;
			
			if( pressed.getType() == ItemType.COLLISIONDOMAIN ) {
				if( node != null && collisionDomain == null ) {
					collisionDomain = pressed;
					canvas.addLink( node, collisionDomain );
					reset();
				} else {
					if( collisionDomain == null ) {
						collisionDomain = pressed;
						link = PPath.createLine(0, 0, 0, 0);
						canvas.addLine( link );
					}
				}
			} else {
				if( collisionDomain != null && node == null ) {
					node = pressed;
					canvas.addLink( node, collisionDomain );
					reset();
				} else {
					if( node == null ) {
						node = pressed;
						link = PPath.createLine(0, 0, 0, 0);
						canvas.addLine( link );
					}
				}
			}
		} catch (Exception e) {
		}
		update(event);
	}
	
	private void reset() {
		node = null;
		collisionDomain = null;
		canvas.deleteLink(link);
		link = null;
	}
	
	@Override
	public void mouseMoved(PInputEvent event) {
		super.mouseMoved(event);
		
		update( event );
	}
	
	private void update( PInputEvent e ) {
		Point2D start = null;
		if( node != null ) {
			start = node.getFullBoundsReference().getCenter2D();
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
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage( Util.getImageIcon(node.getImageName(), Util.SELECTED).getImage() );
		} catch (Exception e) {}
		
		update(event);
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		try {
			GNode node = (GNode) event.getPickedNode();
			node.setImage( Util.getImageIcon(node.getImageName(), Util.DEFAULT).getImage() );
		} catch (Exception e) {}
		
		update(event);
	}
}
