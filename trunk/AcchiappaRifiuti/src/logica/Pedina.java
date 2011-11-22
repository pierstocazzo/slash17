package logica;

import java.applet.Applet;
import java.awt.Image;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */

/** Rappresenta una pedina sul tabellone **/
public class Pedina {

	/** coordinata x della pedina **/
	int x;
	/** coordinata y della pedina **/
	int y;
	/** immagine personale della pedina **/
	Image immaginePersonale;

	/**
	 * Costructtore perdina
	 * 
	 * @param <img><immagine personale>
	 * @param <x><coordinata x>
	 * @param <y><coordinata y>
	 * **/
	public Pedina(Image img, int x, int y) {

		this.immaginePersonale = img;
		this.x = x;
		this.y = y;
	}

	/**
	 * Disegna la pedina in un particolare context es. in un applet
	 * 
	 * @param <context><contesto in cui disegnare la pedina>
	 * 
	 * **/
	public void disegna(Object context) {

		/*
		 * Conversione context in Applet
		 */
		Applet apl = (Applet) context;
		apl.getGraphics().drawImage(this.immaginePersonale, this.x, this.y, apl);
	}

	/**
	 * Prende la coordinata X
	 * 
	 */
	public int getX() {
		return this.x;

	}

	/**
	 * Prende la coordinata y
	 * 
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * muovi la pedine in una posizione specifica
	 */
	public boolean muovi(int x, int y) {
		this.setPosizione(x, y);
		return true;
	}

	/**
	 * cambia la posizione
	 */
	public void setPosizione(int x, int y) {
		this.x = x;
		this.y = y;

	}

	public void disegna() {
		return;

	}

	public int getLunghezza() {
		return immaginePersonale.getWidth(null);
	}

	public int getLarghezza() {
		return immaginePersonale.getHeight(null);
	}

	public Image getImmaginePersonale() {
		return immaginePersonale;
	}

}
