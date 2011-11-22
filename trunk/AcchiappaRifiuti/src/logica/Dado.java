package logica;

import java.util.*;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */
public class Dado {

	/**
	 * E' il valore della faccia del dado relativo all'ultimo lancio eseguito
	 */
	protected int valore;

	/**
	 * E' il costruttore
	 */
	public Dado() {

		valore = 0;
	}

	/**
	 * Esegue un lancio del Dado e restituisce il valore
	 */
	public int tiraDado() {
		Random rand = new Random();
		valore = rand.nextInt(5) + 1;
		return valore;
	}

}