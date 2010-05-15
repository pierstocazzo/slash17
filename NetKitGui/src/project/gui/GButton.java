package project.gui;

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
	
	public GButton( String text, String toolTip, String icon ) {
		this( text, toolTip, icon, toolbar );
	}
	
	public GButton( String text, String toolTip, String icon, int type ) {
		super( text, new ImageIcon(icon) );
		setToolTipText( toolTip );
		setIconTextGap(0);
		
		setFont( new Font("SansSerif", Font.PLAIN, 10));
		
		if( type == toolbar ) {
			setVerticalTextPosition(SwingConstants.BOTTOM);
			setHorizontalTextPosition(SwingConstants.CENTER);
			setPreferredSize(new Dimension(50, 50));
		} else {
			setVerticalTextPosition(SwingConstants.CENTER);
			setIconTextGap(5);
		}
		
		setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		setAlignmentY(JToolBar.CENTER_ALIGNMENT);
	}
}
