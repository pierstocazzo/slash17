package project.gui;

import java.awt.Cursor;
import java.awt.Graphics2D;
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
import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
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
	}

	public void saveImage() {
		int width = getWidth();
		int height = getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = image.createGraphics();
		this.paint(g2);
		g2.dispose();
		
		JFileChooser saveImg = new JFileChooser("data/images");
		saveImg.setFileFilter(new ImgFileFilter(".png"));
		saveImg.setFileFilter(new ImgFileFilter(".jpg"));
		saveImg.setFileFilter(new ImgFileFilter(".gif"));
		saveImg.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		saveImg.showSaveDialog(frame);
		
		File f = saveImg.getSelectedFile();
		
		String ext = saveImg.getFileFilter().getDescription();
		
		try {
			ImageIO.write(image, ext, f);
		} catch (IOException e) {
			System.out.println(e.getMessage());
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

	public void delete( PNode node ) {
		try {
			if( node instanceof GLink ) {
				GLink link = ((GLink) node);
				link.delete();
				GuiManager.getInstance().getProject().removeLink( link.getLogic() );
				switchToDefaultHandler();
				
			} else if( node instanceof GCollisionDomain ) {
				GCollisionDomain cd = ((GCollisionDomain) node);
				cd.delete();
				GuiManager.getInstance().getProject().removeCollisionDomain( cd.getLogic() );
				switchToDefaultHandler();
				
			} else if( node instanceof GHost ){
				GHost host = ((GHost) node);
				host.delete();
				GuiManager.getInstance().getProject().removeHost( host.getLogic() );
				switchToDefaultHandler();
			} else if( node instanceof PPath ) {
				areaLayer.removeChild(node);
				switchToDefaultHandler();
			} else {
				switchToDefaultHandler();
			}
		} catch (Exception e) {
			e.printStackTrace();
			switchToDefaultHandler();
		}
	}

	private void switchToAddHandler( ItemType type ) {
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
	
	private void switchToDeleteHandler() {
		frame.setCursor( new Cursor(Cursor.CROSSHAIR_CURSOR));
		if( !currentHandler.equals(deleteHandler) ) {
			mainLayer.removeInputEventListener(defaultHandler);
			addInputEventListener(deleteHandler);
			currentHandler = deleteHandler;
		}
	}
	
	private void switchToDefaultHandler() {
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
