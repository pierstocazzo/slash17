package com.netedit.gui.nodes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.netedit.common.ItemType;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.gui.GuiManager;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;

public class GHost extends GNode {
	private static final long serialVersionUID = 1L;

	protected PImage defaultImage;
	protected PImage selectedImage;
	protected PImage mouseOverImage;
	protected PImage connectImage;
	
	protected PImage currentImage;
	
	protected ArrayList<GLink> links;
	
	AbstractHost absHost;
	
	public GHost( double x, double y, String imagePath, AbstractHost host, PLayer layer ) {
		super( GNode.host, layer );
		
		this.links = new ArrayList<GLink>();
		this.absHost = host;
		
		/* get the images needed */
		int index = imagePath.lastIndexOf(".");
		String extension = imagePath.substring( index );
		String imageName = imagePath.substring( 0, index );
		
		String selectedImagePath = imageName + "_selected" + extension;
		String mouseOverImagePath = imageName + "_mouseover" + extension;
		String connectImagePath = imageName + "_connecting" + extension;
		
		defaultImage = new PImage(imagePath);
		selectedImage = new PImage(selectedImagePath);
		mouseOverImage = new PImage(mouseOverImagePath);
		connectImage = new PImage(connectImagePath);
		
		setImage( defaultImage );
		
		setText(host.getName());
		
		centerFullBoundsOnPoint(x, y);
		
		createPopupMenu();
		
		update();
	}
	
	private void createPopupMenu() {
		menu = new JPopupMenu();
		
		JMenuItem delete = new JMenuItem("Delete", new ImageIcon("data/images/16x16/delete_icon.png"));
		delete.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		menu.add(delete);
	}
	
	@Override
	public void showMenu(PInputEvent e) {
		super.showMenu(e);
		menu.show((Component) e.getComponent(), (int) e.getPosition().getX(), (int) e.getPosition().getY());
	}
	
	private void setImage( PImage newImage ) {
		if( currentImage != null ) {
			removeChild(currentImage);
		}
		currentImage = newImage;
		addChild(currentImage);
		setBounds(currentImage.getBounds());
		currentImage.centerFullBoundsOnPoint(getBounds().getCenterX(), getBounds().getCenterY());
		currentImage.setPickable(false);
	}
	
	public void setSelected( boolean selected ) {
		super.setSelected(selected);
		if( selected )
			setImage(selectedImage);
		else 
			setImage(defaultImage);
	}
	
	public void setMouseOver( boolean mouseOver ) {
		if( !selected ) {
			if( mouseOver ) 
				setImage(mouseOverImage);
			else 
				setImage(defaultImage);
		}
	}
	
	public void setConnecting( boolean connecting ) {
		if( connecting ) 
			setImage(connectImage);
		else 
			setImage(defaultImage);
	}

	public void update() {
		super.update();
		
		for( GLink gl : links ) {
			gl.update();
		}
	}
	
	public void addLink( GLink link ) {
		links.add(link);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}

	public ItemType getHostType() {
		return absHost.getType();
	}
	
	public AbstractHost getLogic() {
		return absHost;
	}

	public void delete() {
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		
		super.delete();
		GuiManager.getInstance().getProject().removeHost( absHost );
		GuiManager.getInstance().update();
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}
