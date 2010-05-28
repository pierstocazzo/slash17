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
	JMenu menu;
	JMenuItem selectedItem;

	public DropDownButton(String text, String toolTip, String icon) { 
		super(text + " ▾", toolTip, icon, GButton.standard); 
		setPreferredSize(new Dimension(75, 30));
		menu = new JMenu();
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
				else
					if( selectedItem != null )
						selectedItem.doClick(0);
			}
		});
	}

	public JMenu getMenu() { 
		return menu; 
	}

	public boolean isEmpty() {
		return (menu.getItemCount() == 0);
	}

	public void setSelectedItem( JMenuItem item ) {
		this.selectedItem = item;
	}
}
