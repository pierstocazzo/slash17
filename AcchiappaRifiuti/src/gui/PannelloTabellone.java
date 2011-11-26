package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import logica.Casella;
import logica.Giocatore;

public class PannelloTabellone extends JPanel {

	private static final long serialVersionUID = 4138680276042468306L;
	Image sfondo;
	Image over;
	
	ArrayList<Giocatore> giocatori;
	
	public static final String DEFAULT = "img/mappe/Tabellone.jpg";
	public static final String CENTRALE = "img/mappe/tabelloneCENTRO.PNG";
	public static final String NE = "img/mappe/tabelloneNE.PNG";
	public static final String E = "img/mappe/tabelloneE.PNG";
	public static final String SE = "img/mappe/tabelloneSE.PNG";
	public static final String SO = "img/mappe/tabelloneSO.PNG";
	public static final String O = "img/mappe/tabelloneO.PNG";
	public static final String NO = "img/mappe/tabelloneNO.PNG";

	/** Creates new form PanelloTabellone */
	public PannelloTabellone() {
		giocatori = new ArrayList<Giocatore>();
		sfondo = new ImageIcon(getClass().getClassLoader().getResource(DEFAULT)).getImage();
		over = new ImageIcon(getClass().getClassLoader().getResource(DEFAULT)).getImage();
		Dimension d = new Dimension(sfondo.getWidth(null), sfondo.getHeight(null));
		setMinimumSize(d);
		setMaximumSize(d);
		setPreferredSize(d);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(sfondo, 5, 0, null);
		g.drawImage(over, 5, 0, null);
		
		HashMap<Casella, int[]> hash = new HashMap<Casella, int[]>();
		
		for (int i = 0; i < giocatori.size(); i++) {
			Image immp = giocatori.get(i).getPedina().getImmaginePersonale();
			Casella c = giocatori.get(i).getCasella();
			
			int x = Integer.parseInt(c.getX());
			int y = Integer.parseInt(c.getY());
			
			if (c.getTipo() == Casella.CENTRALE) {
				switch (i) {
				case 0:
					x = x - 30;
					break;
				case 1:
					y = y - 30;
					break;
				case 2:
					break;
				case 3:
					x = x + 30;
					break;
				case 4:
					y = y + 30;
					break;
				}
			} else if (hash.containsKey(c)) {
				x = hash.get(c)[0] + 7;
				y = hash.get(c)[1] + 3;
			}
			
			int[] val = {x,y};
			hash.put(c, val);
			
			g.drawImage(immp, x, y, immp.getWidth(null), immp.getHeight(null), null);
		}
	}
	
	public void setImage(String image) {
		ClassLoader cldr = this.getClass().getClassLoader();
		URL imageURL   = cldr.getResource(image);
		over = new ImageIcon(imageURL).getImage();
		repaint();
	}
	
	public void addGiocatore(Giocatore g) {
		giocatori.add(g);
	}
}
