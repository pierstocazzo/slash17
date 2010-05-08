package project.gui;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JMenuItem;

public class GMenuItem extends JMenuItem {
	private static final long serialVersionUID = -1942864015296942487L;

	public GMenuItem( String text, Icon icon ) {
		super( text, icon );
		
		setFont( new Font("SansSerif", Font.PLAIN, 12));
		
		setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
	}
}
