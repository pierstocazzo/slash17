package gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

public class GButton extends JButton {
	private static final long serialVersionUID = -635029680100083147L;
	
	public GButton( String text, String toolTip, Icon icon ) {
		super( text, icon );
		setToolTipText( toolTip );
		
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		setBorder(null);
		setOpaque(false);
		
		setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		setAlignmentY(JToolBar.CENTER_ALIGNMENT);
	}
	
	public GButton( String text, String toolTip, Icon icon, boolean border ) {
		super( text, icon );
		setToolTipText( toolTip );
		
		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		if( border ) 
			setBorder( new GButtonBorder(2, 10, 2, 10) );
		else 
			setBorder(null);
		
		setOpaque(false);
		
		setAlignmentX(JToolBar.CENTER_ALIGNMENT);
		setAlignmentY(JToolBar.CENTER_ALIGNMENT);
	}

	class GButtonBorder extends AbstractBorder {
		private static final long serialVersionUID = 1L;

		protected int left, right, top, bottom;

		/**
		 * Creates an empty border with the specified insets.
		 * @param top the top inset of the border
		 * @param left the left inset of the border
		 * @param bottom the bottom inset of the border
		 * @param right the right inset of the border
		 */
		public GButtonBorder( int top, int left, int bottom, int right ) {
			this.top = top; 
			this.right = right;
			this.bottom = bottom;
			this.left = left;
		}

		/**
		 * Creates an empty border with the specified insets.
		 * @param borderInsets the insets of the border
		 */
		public GButtonBorder( Insets borderInsets )   {
			this.top = borderInsets.top; 
			this.right = borderInsets.right;
			this.bottom = borderInsets.bottom;
			this.left = borderInsets.left;
		}

		/**
		 * Does no drawing by default.
		 */
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		}

		/**
		 * Returns the insets of the border.
		 * @param c the component for which this border insets value applies
		 * @since 1.3
		 */
		public Insets getBorderInsets(Component c)       {
			return getBorderInsets();
		}

		/** 
		 * Reinitialize the insets parameter with this Border's current Insets. 
		 * @param c the component for which this border insets value applies
		 * @param insets the object to be reinitialized
		 */
		public Insets getBorderInsets(Component c, Insets insets) {
			insets.left = left;
			insets.top = top;
			insets.right = right;
			insets.bottom = bottom;
			return insets;
		}

		/**
		 * Returns the insets of the border.
		 * @since 1.3
		 */
		public Insets getBorderInsets() {
			return new Insets(top, left, bottom, right);
		}

		/**
		 * Returns whether or not the border is opaque.
		 * Returns false by default.
		 */
		public boolean isBorderOpaque() { return false; }
	}
}
