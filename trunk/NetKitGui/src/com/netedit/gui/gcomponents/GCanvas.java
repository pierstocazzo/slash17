package com.netedit.gui.gcomponents;

import java.awt.Cursor;
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
import com.netedit.core.project.AbstractProject;
import com.netedit.gui.GFactory;
import com.netedit.gui.GuiManager;
import com.netedit.gui.input.AddLinkInputHandler;
import com.netedit.gui.input.AddNodeInputHandler;
import com.netedit.gui.input.DefaultInputHandler;
import com.netedit.gui.input.DeleteInputHandler;
import com.netedit.gui.input.MouseWheelZoomEventHandler;
import com.netedit.gui.nodes.GCollisionDomain;
import com.netedit.gui.nodes.GHost;
import com.netedit.gui.nodes.GLink;
import com.netedit.gui.nodes.GNode;

import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.nodes.PPath;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	PLayer nodeLayer;
	PLayer linkLayer;
	PLayer areaLayer;
	
	GFrame frame;
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;
	
	ConfigurationPanel confPanel;

	MouseWheelZoomEventHandler zoomEventHandler;
	
	double originalScale;
	
	public GCanvas( GFrame gFrame, AbstractProject project, ConfigurationPanel confPanel ) {
		this.frame = gFrame;
		this.confPanel = confPanel;
		
		createCanvas();
	}
	
	public void createCanvas() {	
		nodeLayer = getLayer();
		linkLayer = new PLayer();
		areaLayer = new PLayer();
		nodeLayer.addChild(linkLayer);
		linkLayer.addChild(areaLayer);
		
		defaultHandler = new DefaultInputHandler();
		deleteHandler = new DeleteInputHandler(this);
		addLinkHandler = new AddLinkInputHandler(this);
		
		/* replace the default zoom event handler with the mouse wheel zoom event handler */
		removeInputEventListener(getZoomEventHandler());
		zoomEventHandler = new MouseWheelZoomEventHandler();
		addInputEventListener(zoomEventHandler);

		/* use the right mouse button for the pan event handler, instead of the left button */
		getPanEventHandler().setEventFilter(new PInputEventFilter(InputEvent.BUTTON3_MASK));
		
		switchToDefaultHandler();
		
		originalScale = getCamera().getViewScale();
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

	public void adding( ItemType type ) {
		if( (currentHandler == addNodeHandler && addNodeHandler.getNodeType() == type) ||
				(currentHandler == addLinkHandler && type == ItemType.LINK) ) {
			switchToDefaultHandler();
			return;
		}
		
		if( type != ItemType.LINK ) {
			// remove previously created addhandler
			if( currentHandler.equals(addNodeHandler) ) {
				removeInputEventListener(addNodeHandler);
			}
			
			// create a new addhandler for this node type
			addNodeHandler = new AddNodeInputHandler(this, type);
		}
		
		switchToAddHandler( type );
	}

	public void addNode( ItemType nodeType, Point2D pos ) {
		if( nodeType == ItemType.COLLISIONDOMAIN ) {
			GCollisionDomain cd = GFactory.getInstance().createCollisionDomain( pos.getX(), pos.getY(), nodeLayer );
			GuiManager.getInstance().getProject().addCollisionDomain(cd.getLogic());
			
		} else if( nodeType == ItemType.AREA ) {
			GFactory.getInstance().createArea( pos.getX(), pos.getY(), areaLayer );
			switchToDefaultHandler();
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
	
	public void deleting() {
		if( currentHandler == deleteHandler )
			switchToDefaultHandler();
		else 
			switchToDeleteHandler();
	}

	public void delete( GNode node ) {
		try {
			switch( node.getType() ) {
			
			case GNode.host:
				GHost host = ((GHost) node);
				host.delete();
				GuiManager.getInstance().getProject().removeHost( host.getLogic() );
				break;
				
			case GNode.domain:
				GCollisionDomain cd = ((GCollisionDomain) node);
				GuiManager.getInstance().getProject().removeCollisionDomain( cd.getLogic() );
				break;
				
			case GNode.area:
				// TODO delete area..
				break;
				
			case GNode.link:
				GLink link = ((GLink) node);
				GuiManager.getInstance().getProject().removeLink( link.getLogic() );
				break;
			}
			node.delete();
				
		} catch (Exception e) {
			e.printStackTrace();
			switchToDefaultHandler();
		}
	}

	public void switchToAddHandler( ItemType type ) {
		removeCurrentHandler();
		
		if( type == ItemType.LINK ) {
			addInputEventListener(addLinkHandler);
			currentHandler = addLinkHandler;
		} else {
			addInputEventListener(addNodeHandler);
			currentHandler = addNodeHandler;
		}
	}
	
	public void switchToDeleteHandler() {
		removeCurrentHandler();
		
		frame.setCursor( new Cursor(Cursor.CROSSHAIR_CURSOR));
		addInputEventListener(deleteHandler);
		currentHandler = deleteHandler;
	}
	
	public void switchToDefaultHandler() {
		removeCurrentHandler();
		
		frame.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
		addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
	}
	
	private void removeCurrentHandler() {
//		nodeLayer.removeInputEventListener(currentHandler);
//		linkLayer.removeInputEventListener(currentHandler);
//		areaLayer.removeInputEventListener(currentHandler);
		removeInputEventListener(currentHandler);
	}

	public void addLine( PPath line ) {
		linkLayer.addChild(line);
	}

	public void deleteLink(PPath link) {
		try {
			linkLayer.removeChild(link);
		} catch (Exception e) {
		}
	}

	public void setConfPanel(ConfigurationPanel confpanel) {
		this.confPanel = confpanel;
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
