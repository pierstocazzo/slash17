package com.netedit.gui.gcomponents;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class GButton extends JButton {
	private static final long serialVersionUID = -635029680100083147L;
	
	public static final int standard = 0;
	public static final int toolbar = 1;
	
	private boolean selected = false;
	
	public GButton( String text, String toolTip, String icon ) {
		this( text, toolTip, icon, toolbar );
	}
	
	public GButton( String text, String toolTip, String icon, int type ) {
		super( text, new ImageIcon(icon) );
		setToolTipText( toolTip );
		
		setFont( new Font("", Font.PLAIN, 10));
		
		if( type == toolbar ) {
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);
			setPreferredSize(new Dimension(50, 50));
		} else {
			setVerticalTextPosition(SwingConstants.CENTER);
			
			setFont( new Font("", Font.PLAIN, 11));

			setIconTextGap(5);
		}
		
		setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		setAlignmentY(JToolBar.CENTER_ALIGNMENT);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = b;
	}
	
	public void toggleSelected() {
		selected = !selected;
	}
}
