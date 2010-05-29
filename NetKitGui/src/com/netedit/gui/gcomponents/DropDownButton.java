package com.netedit.gui.gcomponents;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class DropDownButton extends GButton {
	private static final long serialVersionUID = 8106596726298999650L;
	
	JButton dropDownButton;
	GMenu menu;
	JMenuItem selectedItem;

	public DropDownButton(String text, String toolTip, String icon) { 
		super(text + " ▾", toolTip, icon, GButton.standard); 
		
		Dimension size = new Dimension(75, 30);
		setPreferredSize(size);
		setSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		menu = new GMenu();
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		bar.setMaximumSize(new Dimension(0,100));
		bar.setMinimumSize(new Dimension(0,1));
		bar.setPreferredSize(new Dimension(0,1));
		add(bar);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				if( p.x > 55 )
					menu.doClick(0);
				else {
					setSelectedItem(menu.getSelectedItem());
					if( selectedItem != null ) {
						selectedItem.doClick(0);
					}
				}
			}
		});
	}

	public GMenu getMenu() { 
		return menu; 
	}

	public boolean isEmpty() {
		return (menu.getItemCount() == 0);
	}

	public void setSelectedItem(JMenuItem item) {
		selectedItem = item;
	}
	
	public class GMenu extends JMenu {
		private static final long serialVersionUID = 7322259617999175980L;
		JMenuItem selectedItem;
		
		public GMenu() {
			super();
		}
		
		public void setSelectedItem(JMenuItem it) {
			selectedItem = it;
		}
		
		public JMenuItem getSelectedItem() {
			for( int i = 0; i < getItemCount(); i++ ) {
				if( getItem(i).isSelected() ) {
					selectedItem = getItem(i);
				}
			}
			return selectedItem;
		}
	}
}
