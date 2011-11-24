package logica;

public class CasellaDomanda {

	public static String AssegnaPunteggio(Giocatore g) {

		Casella corrente = g.getCasella();
		if (corrente.getNome().equals("dieciCarta")) {

			g.aggiorna(Giocatore.CARTA, 10);
			return "risposta corretta! +10 Kg di carta";

		} else if (corrente.getNome().equals("dieciMetallo")) {

			g.aggiorna(Giocatore.METALLO, 10);
			return "risposta corretta! +10 Kg di metallo";

		} else if (corrente.getNome().equals("dieciPlastica")) {

			g.aggiorna(Giocatore.PLASTICA, 10);
			return "risposta corretta! +10 Kg di plastica";

		} else if (corrente.getNome().equals("dieciIndif")) {

			g.aggiorna(Giocatore.INDIFFERENZIATA, 10);
			return "risposta corretta! +10 Kg di rifiuti indifferenziati";

		} else if (corrente.getNome().equals("dieciOrg")) {

			g.aggiorna(Giocatore.ORGANICA, 10);
			return "risposta corretta! +10 Kg di rifiuti organici";

		} else if (corrente.getNome().equals("dieciVetro")) {

			g.aggiorna(Giocatore.VETRO, 10);
			return "risposta corretta! +10 Kg di vetro";

		} else if (corrente.getNome().equals("cinqCarta")) {

			g.aggiorna(Giocatore.CARTA, 50);
			return "risposta corretta! +50 Kg di carta";

		} else if (corrente.getNome().equals("cinqMetallo")) {

			g.aggiorna(Giocatore.METALLO, 50);
			return "risposta corretta! +50 Kg di metallo";

		} else if (corrente.getNome().equals("cinqPlastica")) {

			g.aggiorna(Giocatore.PLASTICA, 50);
			return "risposta corretta! +50 Kg di plastica";

		} else if (corrente.getNome().equals("cinqIndif")) {

			g.aggiorna(Giocatore.INDIFFERENZIATA, 50);
			return "risposta corretta! +50 Kg di rifiuti indifferenziati";

		} else if (corrente.getNome().equals("cinqOrg")) {

			g.aggiorna(Giocatore.ORGANICA, 50);
			return "risposta corretta! +50 Kg di rifiuti organici";

		} else if (corrente.getNome().equals("cinqVetro")) {

			g.aggiorna(Giocatore.VETRO, 50);
			return "risposta corretta! +50 Kg di vetro";

		} else if (corrente.getNome().equals("centoCarta")) {

			g.aggiorna(Giocatore.CARTA, 100);
			return "risposta corretta! +100 Kg di carta";

		} else if (corrente.getNome().equals("centoMetallo")) {

			g.aggiorna(Giocatore.METALLO, 100);
			return "risposta corretta! +100 Kg di metallo";

		} else if (corrente.getNome().equals("centoPlastica")) {

			g.aggiorna(Giocatore.PLASTICA, 100);
			return "risposta corretta! +100 Kg di plastica";

		} else if (corrente.getNome().equals("centoIndif")) {

			g.aggiorna(Giocatore.INDIFFERENZIATA, 100);
			return "risposta corretta! +100 Kg di rifiuti indifferenziati";

		} else if (corrente.getNome().equals("centoOrg")) {

			g.aggiorna(Giocatore.ORGANICA, 100);
			return "risposta corretta! +100 Kg di rifiuti organici";

		} else if (corrente.getNome().equals("centoVetro")) {

			g.aggiorna(Giocatore.VETRO, 100);
			return "risposta corretta! +100 Kg di vetro";

		}

		return "-1_assegnaPunteggio(Giocatore g)";

	} // chiusura AssegnaPunteggio
	/*
	 * public boolean isCorrect(String domanda, String risposta) {
	 * 
	 * String real = dom.get(domanda);
	 * 
	 * 
	 * if (risposta.compareToIgnoreCase(real) == 0) { return true; } else {
	 * return false; }
	 * 
	 * }
	 */
}
