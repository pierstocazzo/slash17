package logica;

import logica.Risposta;

public class Domanda {

	private String quesito;
	private int codice;
	Risposta a;
	Risposta b;
	Risposta c;
	Risposta d;
	Risposta corretta;

	/**
	 * Costruttore
	 * 
	 * @param quesito
	 *            testo della domanda
	 * @param colore
	 *            colore associato alla domanda (categoria)
	 * @param codice
	 *            codice univoco della domanda
	 * @param risposte
	 *            array di 4 risposte alla domanda
	 */
	public Domanda(String quesito, int codice) {
		// _risposte = new Risposta[4];
		this.quesito = quesito;
		this.codice = codice;

	}

	public Domanda(String quesito) {
		this.quesito = quesito;
		codice = -1;
	}

	public Risposta getA() {
		return a;
	}

	public void setA(Risposta a) {
		this.a = a;
	}

	public Risposta getB() {
		return b;
	}

	public void setB(Risposta b) {
		this.b = b;
	}

	public Risposta getC() {
		return c;
	}

	public void setC(Risposta c) {
		this.c = c;
	}

	public Risposta getD() {
		return d;
	}

	public void setD(Risposta d) {
		this.d = d;
	}

	public Risposta getCorretta() {
		return corretta;
	}

	/**
	 * Ritorna il testo della domanda
	 * 
	 * @return testo domanda
	 */
	public void setCodice(int c) {
		codice = c;
	}

	public String getQuesito() {
		return quesito;
	}

	/**
	 * Ritorna il codice univoco della domanda
	 * 
	 * @return stringa codice
	 */
	public String getCodicetoString() {
		return "" + codice;
	}

	public int getCodice() {
		return codice;
	}

	public void setRispostaA(Risposta r) {
		a = r;
	}

	public void setRispostaB(Risposta r) {
		b = r;
	}

	public void setRispostaC(Risposta r) {
		c = r;
	}

	public void setRispostaD(Risposta r) {
		d = r;
	}

	public void setCorretta(Risposta r) {
		corretta = r;
	}

	public String toString() {
		return "" + codice + " " + quesito;
	}
}
