package gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import logica.Pedina;

/**
 * 
 * @author PiGix
 */
public class PannelloTabellone extends javax.swing.JPanel {

	private static final long serialVersionUID = 4138680276042468306L;
	Font font = new Font("TimesRoman", Font.BOLD, 14);
	JLabel imageLabel;
	ArrayList<Pedina> pedine;
	
	public static final String DEFAULT = "img/mappe/Tabellone.jpg";
	public static final String CENTRALE = "img/mappe/tabelloneCENTRO.jpg";
	public static final String NE = "img/mappe/tabelloneNE.jpg";
	public static final String E = "img/mappe/tabelloneE.jpg";
	public static final String SE = "img/mappe/tabelloneSE.jpg";
	public static final String SO = "img/mappe/tabelloneSO.jpg";
	public static final String O = "img/mappe/tabelloneO.jpg";
	public static final String NO = "img/mappe/tabelloneNO.jpg";

	/** Creates new form PanelloTabellone */
	public PannelloTabellone() {
		pedine = new ArrayList<Pedina>();
		imageLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(DEFAULT)));
		add(imageLabel);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
//		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		for (int j = 0; j < pedine.size(); j++) {
			Pedina p = pedine.get(j);
			Image immp = p.getImmaginePersonale();
			g.setFont(font);
			// g.setColor(Color.YELLOW);
			g.drawString("G" + (j + 1), p.getX() + 10, p.getY() - 5);
			g.drawImage(immp, p.getX(), p.getY(), immp.getWidth(null), immp.getHeight(null), null);
		}
	}
	
	public void setImage(String image) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL imageURL   = cldr.getResource(image);
		this.imageLabel.setIcon(new ImageIcon(imageURL));
		repaint();
	}

	public void addpedina(Pedina p) {
		pedine.add(p);
		repaint();
	}
}
