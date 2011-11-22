package logica;

public class Risposta {

	protected String risposta;
	private int codice;
	private boolean corretta;

	/**
	 * Costruttore
	 * 
	 * @param risposta
	 *            testo risposta
	 * @param codice
	 *            codice della risposta, � uguale per ogni 4 risposte e una
	 *            domanda
	 * @param corretta
	 *            se la risposta � corretta o meno
	 */
	public Risposta(String risposta, int codice, boolean corretta) {
		this.risposta = risposta;
		this.corretta = corretta;
		this.codice = codice;

	}

	/**
	 * Propriet�: restituisce il testo della risposta
	 * 
	 * @return testo risposta
	 */
	public String getRisposta() {
		return risposta;
	}

	/**
	 * Propriet�: segnala se la risposta � corretta o meno
	 * 
	 * @return true/false
	 */
	public boolean getCorretta() {
		return corretta;
	}

	/**
	 * Propriet�: codice della risposta
	 * 
	 * @return stringa identificativa gruppo di 4 risposte
	 */
	public int getCodice() {
		return codice;
	}

	public String toString() {
		return "" + risposta + ", " + codice + ", " + corretta;
	}
}
