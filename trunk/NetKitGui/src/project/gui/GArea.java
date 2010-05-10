package project.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.handles.PBoundsHandle;

public class GArea extends PPath {
	private static final long serialVersionUID = 3492362844970509196L;

	JPopupMenu menu;
	PLayer layer;
	PText text;
	
	public GArea( int x, int y, PLayer layer ) {
		super(new Rectangle(x, y, 100, 100));
		this.layer = layer;
		
		setPaint(Color.cyan);
		
		menu = new JPopupMenu();
		
		JMenuItem setText = new JMenuItem("Set Name");
		setText.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setName( e );
			}
		});
		menu.add(setText);
		
		JMenuItem selectcolor = new JMenuItem("Select Color", new ImageIcon("data/images/16x16/color_icon.png"));
		selectcolor.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(GuiManager.getInstance().getFrame(), "Area Color", Color.cyan);
				if( color != null )
					setPaint( color );
			}
		});
		menu.add(selectcolor);
		
		JMenuItem delete = new JMenuItem("Delete", new ImageIcon("data/images/16x16/delete_icon.png"));
		delete.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		menu.add(delete);
		
		layer.addChild(this);
	}

	protected void setName(ActionEvent e) {
		String name = JOptionPane.showInputDialog("Insert the name:");
		if( name != null && !name.equals("") ) {
			text = new PText(name);
			text.setPickable(false);
			text.setFont(new Font("SansSerif", Font.BOLD, 16));
			addChild(text);
			text.centerFullBoundsOnPoint( getX() + getWidth() - text.getWidth(), getY() + text.getHeight() );
		}
	}

	@Override
	public boolean setBounds(Rectangle2D newBounds) {
		boolean result = super.setBounds(newBounds);
		if( text != null )
			text.centerFullBoundsOnPoint( getX() + getWidth() - text.getWidth(), getY() + text.getHeight() );
		return result;
	}
	
	public void select() {
		PBoundsHandle.addBoundsHandlesTo(this);
	}
	
	public void unSelect() {
		PBoundsHandle.removeBoundsHandlesFrom(this);
	}

	public void popUpMenu( PInputEvent e ) {
		menu.show((Component) e.getComponent(), (int) e.getPosition().getX(), (int) e.getPosition().getY());
	}
	
	public void delete() {
		layer.removeChild(this);
	}
}
