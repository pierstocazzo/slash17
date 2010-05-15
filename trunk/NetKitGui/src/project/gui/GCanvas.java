package project.gui;

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

import project.common.ItemType;
import project.core.AbstractProject;
import project.gui.input.AddLinkInputHandler;
import project.gui.input.AddNodeInputHandler;
import project.gui.input.BoundsHandler;
import project.gui.input.DefaultInputHandler;
import project.gui.input.DeleteInputHandler;
import project.gui.input.MouseWheelZoomEventHandler;
import project.gui.labview.LabConfPanel;
import edu.umd.cs.piccolo.PCamera;
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.nodes.PPath;

public class GCanvas extends PCanvas {
	private static final long serialVersionUID = 1L;
	
	PLayer mainLayer;
	PLayer secondLayer;
	PLayer areaLayer;
	
	GFrame frame;
	
	DefaultInputHandler defaultHandler;
	DeleteInputHandler deleteHandler;
	AddNodeInputHandler addNodeHandler;
	AddLinkInputHandler addLinkHandler;
	
	PBasicInputEventHandler currentHandler;
	
	LabConfPanel confPanel;

	MouseWheelZoomEventHandler zoomEventHandler;
	
	double originalScale;
	
	public GCanvas( GFrame gFrame, AbstractProject project, LabConfPanel confPanel ) {
		this.frame = gFrame;
		this.confPanel = confPanel;
		
		createCanvas();
	}
	
	public void createCanvas() {	
		mainLayer = getLayer();
		secondLayer = new PLayer();
		areaLayer = new PLayer();
		mainLayer.addChild(secondLayer);
		secondLayer.addChild(areaLayer);
		
		defaultHandler = new DefaultInputHandler();
		deleteHandler = new DeleteInputHandler(this);
		addLinkHandler = new AddLinkInputHandler(this);
		
		/* replace the default zoom event handler with the mouse wheel zoom event handler */
		removeInputEventListener(getZoomEventHandler());
		zoomEventHandler = new MouseWheelZoomEventHandler();
		addInputEventListener(zoomEventHandler);

		/* use the right mouse button for the pan event handler, instead of the left button */
		getPanEventHandler().setEventFilter(new PInputEventFilter(InputEvent.BUTTON3_MASK));
		
		/* add the bounds handler for areas creation */
		addInputEventListener(new BoundsHandler());
		
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
		
		System.out.println(camera.getViewScale());
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
			GCollisionDomain cd = GFactory.getInstance().createCollisionDomain( pos.getX(), pos.getY(), mainLayer );
			GuiManager.getInstance().getProject().addCollisionDomain(cd.getLogic());
			
		} else if( nodeType == ItemType.AREA ) {
			GFactory.getInstance().createArea( pos.getX(), pos.getY(), areaLayer );
			
		} else {
			GHost host = GFactory.getInstance().createGHost( nodeType, pos.getX(), pos.getY(), mainLayer );
			GuiManager.getInstance().getProject().addHost(host.getLogic());
		}
		
		switchToDefaultHandler();
	}
	
	public void addLink( GHost host, GCollisionDomain collisionDomain ) {
		GLink link = GFactory.getInstance().createLink( host, collisionDomain, secondLayer );
		
		if( link == null ) {
			JOptionPane.showMessageDialog(this, "Can't add another link");
		} else {
			host.addLink(link);
			collisionDomain.addLink(link);
		}
		
		switchToDefaultHandler();
	}
	
	public void deleting() {
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
			switchToDefaultHandler();
				
		} catch (Exception e) {
			e.printStackTrace();
			switchToDefaultHandler();
		}
	}

	public void switchToAddHandler( ItemType type ) {
		if( type == ItemType.LINK ) {
			if( !currentHandler.equals(addLinkHandler) ) {
				mainLayer.removeInputEventListener(defaultHandler);
				addInputEventListener(addLinkHandler);
				currentHandler = addLinkHandler;
			}
		} else {
			if( !currentHandler.equals(addNodeHandler) ) {
				mainLayer.removeInputEventListener(defaultHandler);
				addInputEventListener(addNodeHandler);
				currentHandler = addNodeHandler;
			}
		}
	}
	
	public void switchToDeleteHandler() {
		frame.setCursor( new Cursor(Cursor.CROSSHAIR_CURSOR));
		if( !currentHandler.equals(deleteHandler) ) {
			mainLayer.removeInputEventListener(defaultHandler);
			addInputEventListener(deleteHandler);
			currentHandler = deleteHandler;
		}
	}
	
	public void switchToDefaultHandler() {
		frame.setCursor( new Cursor(Cursor.DEFAULT_CURSOR));
		removeInputEventListener(addNodeHandler);
		removeInputEventListener(addLinkHandler);
		removeInputEventListener(deleteHandler);
		mainLayer.addInputEventListener(defaultHandler);
		currentHandler = defaultHandler;
	}

	public void addLine( PPath line ) {
		secondLayer.addChild(line);
	}

	public void deleteLink(PPath link) {
		try {
			secondLayer.removeChild(link);
		} catch (Exception e) {
		}
	}

	public void setConfPanel(LabConfPanel confpanel) {
		this.confPanel = confpanel;
	}

	public PLayer getNodeLayer() {
		return mainLayer;
	}

	public PLayer getAreaLayer() {
		return areaLayer;
	}

	public PLayer getLinkLayer() {
		return secondLayer;
	}
}
