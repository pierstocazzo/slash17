package project.gui;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import project.util.Util;


public class GButton extends JButton {
	private static final long serialVersionUID = -635029680100083147L;
	
	public GButton( String text, String toolTip, String icon ) {
		super( text, new ImageIcon(icon) );
		setToolTipText( toolTip );
		setIconTextGap(0);
		
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		Dimension size = new Dimension(60,60);
		
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setSize(size);
		
		setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		setAlignmentY(JToolBar.CENTER_ALIGNMENT);
		
		ImageIcon selectedIcon = Util.getImageIcon(icon, Util.SELECTED);
		setSelectedIcon( selectedIcon );
		setPressedIcon( selectedIcon );
		setRolloverIcon( selectedIcon );
	}
}
