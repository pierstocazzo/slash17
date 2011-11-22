package logica;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */
public class CasellaIsola {

	static Giocatore giocatore;

	public static String isola(Giocatore g) {

		giocatore = g;

		if (giocatore.numLanci < 3) {
			return " Isola! lancia di nuovo il dado...";

		} else {

			return " Isola! hai terminato i lanci, passi il turno...";

			// passa il turno all'altro giocatore
		}

	}

}
