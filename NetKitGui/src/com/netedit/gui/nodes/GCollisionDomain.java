package com.netedit.gui.nodes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.gui.GuiManager;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;

public class GCollisionDomain extends GNode {

	private static final long serialVersionUID = 1L;

	protected PImage defaultImage;
	protected PImage selectedImage;
	protected PImage mouseOverImage;
	
	protected PImage currentImage;
	
	protected ArrayList<GLink> links;
	
	protected AbstractCollisionDomain absCollisionDomain;
	
	public GCollisionDomain( double x, double y, AbstractCollisionDomain collisionDomain, PLayer layer ) {
		super( GNode.domain, layer );
		
		this.links = new ArrayList<GLink>();
		this.absCollisionDomain = collisionDomain;
		
		defaultImage = new PImage("data/images/big/collisionDomain.png");
		selectedImage = new PImage("data/images/big/collisionDomain_selected.png");
		mouseOverImage = new PImage("data/images/big/collisionDomain_mouseover.png");
		
		setImage(defaultImage);
		
		setText(absCollisionDomain.getName());
		
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
		if( selected ) {
			setImage(selectedImage);
		} else {
			setImage(defaultImage);
		}
	}
	
	public void setMouseOver( boolean mouseOver ) {
		if( !selected ) {
			if( mouseOver ) 
				setImage(mouseOverImage);
			else 
				setImage(defaultImage);
		}
	}
	
	public void update() {
		super.update();
		
		for( GLink gl : links ) {
			gl.update();
		}
	}
		
	public GLink getLink( int index ) {
		return links.get( index );
	}
	
	public void addLink( GLink link ) {
		links.add(link);
	}
	
	public ArrayList<GLink> getLinks() {
		return links;
	}
	
	public AbstractCollisionDomain getLogic() {
		return absCollisionDomain;
	}
	
	public void delete() {
		while( !links.isEmpty() ) {
			links.get(0).delete();
		}
		super.delete();
		GuiManager.getInstance().getProject().removeCollisionDomain( absCollisionDomain );
		GuiManager.getInstance().update();
	}

	public void removeLink(GLink gLink) {
		links.remove(gLink);
	}
}

