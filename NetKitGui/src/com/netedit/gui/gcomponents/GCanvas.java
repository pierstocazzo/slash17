package com.netedit.gui.gcomponents;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.netedit.common.ItemType;
import com.netedit.gui.GFactory;
import com.netedit.gui.GuiManager;
import com.netedit.gui.input.HandlerManager;
import com.netedit.gui.input.MouseWheelZoomEventHandler;
import com.netedit.gui.nodes.GArea;
import com.netedit.gui.nodes.GCollisionDomain;
import com.netedit.gui.nodes.GHost;
import com.netedit.gui.nodes.GLink;
import com.netedit.gui.nodes.GNode;
import com.netedit.gui.util.ImgFileFilter;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEventFilter;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	PLayer nodeLayer, linkLayer, areaLayer;
	
	GFrame frame;
	HandlerManager handler;
	ConfigurationPanel confPanel;

	MouseWheelZoomEventHandler zoomEventHandler;
	
	double originalScale;
	
	public GCanvas() {
		this.frame = GuiManager.getInstance().getFrame();
		this.confPanel = GuiManager.getInstance().getConfPanel();
		this.handler = GuiManager.getInstance().getHandler();
		
		createCanvas();
	}
	
	public void createCanvas() {
		// init layers
		nodeLayer = getLayer();
		linkLayer = new PLayer();
		areaLayer = new PLayer();
		nodeLayer.addChild(linkLayer);
		linkLayer.addChild(areaLayer);
		
		/* replace the default zoom event handler with the mouse wheel zoom event handler */
		removeInputEventListener(getZoomEventHandler());
		zoomEventHandler = new MouseWheelZoomEventHandler();
		addInputEventListener(zoomEventHandler);

		/* use the right mouse button for the pan event handler, instead of the left button */
		getPanEventHandler().setEventFilter(new PInputEventFilter(InputEvent.BUTTON3_MASK));
		
		originalScale = getCamera().getViewScale();
	}
	
	/** 
	 * Clear the canvas deleting everything
	 */
	public void clear() {
		for( Object node : nodeLayer.getAllNodes() )
			if( node instanceof GNode ) 
				((GNode) node).delete();
		for( Object node : linkLayer.getAllNodes() )
			if( node instanceof GNode ) 
				((GNode) node).delete();
		for( Object node : areaLayer.getAllNodes() )
			if( node instanceof GNode ) 
				((GNode) node).delete();

		GuiManager.getInstance().update();
	}
	
	public void zoomIn() {
		zoom( 1.2 );
	}
	
	public void zoomOut() {
		zoom( 0.8 );
	}
	
	public void zoomOriginal() {
		double delta = originalScale / getCamera().getViewScale();
		zoom(delta);
	}
	
	private void zoom( double delta ) {
		PCamera camera = getCamera();
		
		double currentScale = camera.getViewScale();
		double newScale = currentScale * delta;
		
		double minScale = zoomEventHandler.getMinScale();
		double maxScale = zoomEventHandler.getMaxScale();

		if ( newScale < minScale ) {
			delta = minScale / currentScale;
		}
		if ( (maxScale > 0) && (newScale > maxScale) ) {
			delta = maxScale / currentScale;
		}
		Point p = new Point( getBounds().width / 2, getBounds().height / 2 );
		camera.scaleViewAboutPoint(delta, p.x, p.y);
	}
	
	/**
	 * Export the canvas as an image
	 */
	public void export() {
		int width = getWidth();
		int height = getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		this.paint(g2);
		g2.dispose();
		
		String[] options = {"png", "gif", "jpg"};
		String extension = (String) JOptionPane.showInputDialog(frame, "Choose the image type", "Export as..", JOptionPane.QUESTION_MESSAGE, null, options, ".png");
		
		if( extension == null ) 
			return;
		
		JFileChooser saveImg = new JFileChooser();
		saveImg.setMultiSelectionEnabled(false);
		saveImg.setFileFilter(new ImgFileFilter(extension));
		saveImg.showSaveDialog(frame);
		
		File f = saveImg.getSelectedFile();
		if( f != null ) {
			while( f.exists() ) {
				int choose = JOptionPane.showConfirmDialog(frame, "Are you sure you want to overwrite this file?");
				if( choose == JOptionPane.NO_OPTION ) 
					saveImg.showSaveDialog(frame);
				else if( choose == JOptionPane.CANCEL_OPTION )
					return;
				else
					break;
			}
			try {
				if( f.getPath().endsWith("." + extension) ) 
					ImageIO.write(image, extension, f);
				else
					ImageIO.write(image, extension, new File(f.getPath() + "." + extension));
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void addNode( ItemType nodeType, Point2D pos ) {
		if( nodeType == ItemType.COLLISIONDOMAIN ) {
			GCollisionDomain cd = GFactory.getInstance().createCollisionDomain( pos.getX(), pos.getY(), nodeLayer );
			GuiManager.getInstance().getProject().addCollisionDomain(cd.getLogic());
		} else if( nodeType == ItemType.TAP ) {
			GCollisionDomain tap = GFactory.getInstance().createTap( pos.getX(), pos.getY(), nodeLayer );
			if( tap == null ) {
				JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), 
						"Can't add more than one tap", "", JOptionPane.ERROR_MESSAGE);
				return;
			}
			GuiManager.getInstance().getProject().addCollisionDomain(tap.getLogic());
		} else if( nodeType == ItemType.AREA ) {
			GFactory.getInstance().createArea( pos.getX(), pos.getY(), areaLayer );
			handler.switchToDefaultHandler();
		} else {
			GHost host = GFactory.getInstance().createGHost( nodeType, pos.getX(), pos.getY(), nodeLayer );
			GuiManager.getInstance().getProject().addHost(host.getLogic());
		}
	}
	
	public void addLink( GHost host, GCollisionDomain collisionDomain ) {
		GLink link = GFactory.getInstance().createLink( host, collisionDomain, linkLayer );
		
		if( link == null ) {
			JOptionPane.showMessageDialog(this, "Can't add another link");
		} else {
			host.addLink(link);
			collisionDomain.addLink(link);
		}
	}
	
	public void delete( GNode node ) {
		try {
			switch( node.getType() ) {
			case GNode.host:
				GHost host = (GHost) node;
				host.delete();
				break;
			case GNode.domain:
				GCollisionDomain cd = (GCollisionDomain) node;
				cd.delete();
				break;
			case GNode.area:
				GArea area = (GArea) node;
				area.delete();
				break;
			case GNode.link:
				GLink link = (GLink) node;
				link.delete();
				break;
			default:
				node.delete();
				break;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			handler.switchToDefaultHandler();
		}
	}

	public PLayer getNodeLayer() {
		return nodeLayer;
	}

	public PLayer getAreaLayer() {
		return areaLayer;
	}

	public PLayer getLinkLayer() {
		return linkLayer;
	}
}
