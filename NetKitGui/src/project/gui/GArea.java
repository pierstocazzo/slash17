package project.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Paint;
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

public class GArea extends GNode {
	private static final long serialVersionUID = 3492362844970509196L;
	
	PPath shape;
	
	public GArea( int x, int y, PLayer layer ) {
		super(GNode.area, layer);
		
		setShape(x, y);
		
		menu = new JPopupMenu();
		
		JMenuItem setText = new JMenuItem("Set Name", new ImageIcon("data/images/16x16/text_icon.png"));
		setText.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setText();
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
	}

	private void setShape( int x, int y ) {
		if( shape != null ) {
			removeChild(shape);
		}
		shape = new PPath(new Rectangle(x, y, 100, 100));
		addChild(shape);
		setBounds(shape.getBounds());
		shape.centerFullBoundsOnPoint(getBounds().getCenterX(), getBounds().getCenterY());
		shape.setPickable(false);
		shape.setPaint(Color.cyan);
	}

	protected void setText() {
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
	public void update() {
		if( text != null )
			text.centerFullBoundsOnPoint( getX() + getWidth() - text.getWidth(), getY() + text.getHeight() );
	}

	@Override
	public boolean setBounds(Rectangle2D newBounds) {
		boolean result = super.setBounds(newBounds);
		shape.setBounds(newBounds);
		update();
		return result;
	}
	
	public void setSelected( boolean selected ) {
		super.setSelected(selected);
		if( selected )
			PBoundsHandle.addBoundsHandlesTo(this);
		else 
			PBoundsHandle.removeBoundsHandlesFrom(this);
	}
	
	public void setMouseOver( boolean mouseOver ) {
		if( !selected ) {
			if( mouseOver )
				setTransparency(0.8f);
			else 
				setTransparency(1);
		}
	}

	public void showMenu( PInputEvent e ) {
		menu.show((Component) e.getComponent(), (int) e.getPosition().getX(), (int) e.getPosition().getY());
	}
	
	@Override
	public void setPaint(Paint newPaint) {
		shape.setPaint(newPaint);
	}
	
	public void delete() {
		layer.removeChild(this);
	}
}
