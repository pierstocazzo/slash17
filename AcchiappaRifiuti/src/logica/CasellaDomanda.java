package logica;

public class CasellaDomanda {

	public static String AssegnaPunteggio(Giocatore g) {

		Casella corrente = g.getCasella();
		if (corrente.getNome().equals("dieciCarta")) {

			g.punt_carta += 10;
			return "risposta corretta! +10 Kg di carta";

		} else if (corrente.getNome().equals("dieciMetallo")) {

			g.punt_metallo += 10;
			return "risposta corretta! +10 Kg di metallo";

		} else if (corrente.getNome().equals("dieciPlastica")) {

			g.punt_plastica += 10;
			return "risposta corretta! +10 Kg di plastica";

		} else if (corrente.getNome().equals("dieciIndif")) {

			g.punt_indifferenziata += 10;
			return "risposta corretta! +10 Kg di rifiuti indifferenziati";

		} else if (corrente.getNome().equals("dieciOrg")) {

			g.punt_organica += 10;
			return "risposta corretta! +10 Kg di rifiuti organici";

		} else if (corrente.getNome().equals("dieciVetro")) {

			g.punt_vetro += 10;
			return "risposta corretta! +10 Kg di vetro";

		} else if (corrente.getNome().equals("cinqCarta")) {

			g.punt_carta += 50;
			return "risposta corretta! +50 Kg di carta";

		} else if (corrente.getNome().equals("cinqMetallo")) {

			g.punt_metallo += 50;
			return "risposta corretta! +50 Kg di metallo";

		} else if (corrente.getNome().equals("cinqPlastica")) {

			g.punt_plastica += 50;
			return "risposta corretta! +50 Kg di plastica";

		} else if (corrente.getNome().equals("cinqIndif")) {

			g.punt_indifferenziata += 50;
			return "risposta corretta! +50 Kg di rifiuti indifferenziati";

		} else if (corrente.getNome().equals("cinqOrg")) {

			g.punt_organica += 50;
			return "risposta corretta! +50 Kg di rifiuti organici";

		} else if (corrente.getNome().equals("cinqVetro")) {

			g.punt_vetro += 50;
			return "risposta corretta! +50 Kg di vetro";

		} else if (corrente.getNome().equals("centoCarta")) {

			g.punt_carta += 100;
			return "risposta corretta! +100 Kg di carta";

		} else if (corrente.getNome().equals("centoMetallo")) {

			g.punt_metallo += 100;
			return "risposta corretta! +100 Kg di metallo";

		} else if (corrente.getNome().equals("centoPlastica")) {

			g.punt_plastica += 100;
			return "risposta corretta! +100 Kg di plastica";

		} else if (corrente.getNome().equals("centoIndif")) {

			g.punt_indifferenziata += 100;
			return "risposta corretta! +100 Kg di rifiuti indifferenziati";

		} else if (corrente.getNome().equals("centoOrg")) {

			g.punt_organica += 100;
			return "risposta corretta! +100 Kg di rifiuti organici";

		} else if (corrente.getNome().equals("centoVetro")) {

			g.punt_vetro += 100;
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
