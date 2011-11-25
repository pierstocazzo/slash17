package logica;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */
public class Giocatore {

	public static final String ORGANICA = "organica";
	public static final String PLASTICA = "plastica";
	public static final String INDIFFERENZIATA = "indifferenziata";
	public static final String VETRO = "vetro";
	public static final String CARTA = "carta";
	public static final String METALLO = "metallo";
	
	/**
	 * E' la casella su cui � posto attualmente il Giocatore. Durante il gioco
	 * cambia ad ogni turno del Giocatore.
	 */
	protected Casella casella;
	Pedina pedina;
	/**
	 * E' il dado
	 */
	protected Dado dado;
	/**
	 * I vari punteggi del Giocatore
	 */
	private int punt_carta;
	private int punt_plastica;
	private int punt_vetro;
	private int punt_metallo;
	private int punt_indifferenziata;
	private int punt_organica;

	int punteggioIniziale = 150;
	
	/**
	 * E' il nome del Giocatore
	 */
	protected String nome;
	/**
	 * E' il lancio del giocatore
	 */
	protected int lancio;
	/**
	 * Numero lanci a disposizione del giocatore. Ogni risposta positiva
	 * permette di rilanciare il dado fino ad un massimo di 3 volte consecutive
	 */
	protected int numLanci;
	
	/**
	 * la mia casella precedente
	 */
	private Casella casellaPrecedente;

	public int getNumLanci() {
		return numLanci;
	}

	public void setNumLanci(int numLanci) {
		this.numLanci = numLanci;
	}

	/**
	 * E' il costruttore della classe Giocatore
	 * 
	 * @param nome
	 * @param p 
	 * 
	 */
	public Giocatore(String nome, Casella c, Pedina p) {
		setNome(nome);
		setPedina(p);
		setCasella(c);
		dado = new Dado();
		
		punt_carta = punteggioIniziale;
		punt_plastica = punteggioIniziale;
		punt_vetro = punteggioIniziale;
		punt_metallo = punteggioIniziale;
		punt_indifferenziata = punteggioIniziale;
		punt_organica = punteggioIniziale;
	}

	/*
	 * Metodo che richiama la funzione lancia del Dado
	 */

	public int lancia() {
		lancio = dado.tiraDado();
		this.numLanci++;
		return lancio;
	}

	public void aggiorna(String rifiuto, int quantita) {
		if (rifiuto.equalsIgnoreCase(ORGANICA)) {
			punt_organica += quantita;
			if (punt_organica < 0) 
				punt_organica = 0;
			if (punt_organica > 150)
				punt_organica = 150;
		} else if (rifiuto.equalsIgnoreCase(PLASTICA)) {
			punt_plastica += quantita;
			if (punt_plastica < 0) 
				punt_plastica = 0;
			if (punt_plastica > 150)
				punt_plastica = 150;
		} else if (rifiuto.equalsIgnoreCase(METALLO)) {
			System.out.println("aggiungo " + quantita + " di metallo");
			punt_metallo += quantita;
			if (punt_metallo < 0) 
				punt_metallo = 0;
			if (punt_metallo > 150)
				punt_metallo = 150;
		} else if (rifiuto.equalsIgnoreCase(CARTA)) {
			punt_carta += quantita;
			if (punt_carta < 0) 
				punt_carta = 0;
			if (punt_carta > 150)
				punt_carta = 150;
		} else if (rifiuto.equalsIgnoreCase(VETRO)) {
			punt_vetro += quantita;
			if (punt_vetro < 0) 
				punt_vetro = 0;
			if (punt_vetro > 150)
				punt_vetro = 150;
		} else if (rifiuto.equalsIgnoreCase(INDIFFERENZIATA)) {
			punt_indifferenziata += quantita;
			if (punt_indifferenziata < 0) 
				punt_indifferenziata = 0;
			if (punt_indifferenziata > 150)
				punt_indifferenziata = 150;
		} else {
			System.out.println("ERRORE aggiorna " + rifiuto + " " + quantita);
		}
	}
	
	public boolean possoLanciare() {
		return this.numLanci < 3;
	}

	public int getPunt_carta() {
		return punt_carta;
	}

	public int getPunt_plastica() {
		return punt_plastica;
	}

	public int getPunt_vetro() {
		return punt_vetro;
	}

	public int getPunt_metallo() {
		return punt_metallo;
	}

	public int getPunt_indifferenziata() {
		return punt_indifferenziata;
	}

	public int getPunt_organica() {
		return punt_organica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Casella getCasella() {
		return casella;
	}

	public void setCasella(Casella casella) {
		setCasellaPrecedente(this.casella);
		this.casella = casella;
		this.pedina.setPosizione(Integer.parseInt(casella.getX()), Integer.parseInt(casella.getY()));
	}

	public void setPedina(Pedina pedina) {
		this.pedina = pedina;
	}

	public Pedina getPedina() {
		return pedina;
	}

	public Casella getCasellaPrecedente() {
		return casellaPrecedente;
	}
	
	public void setCasellaPrecedente(Casella c) {
		this.casellaPrecedente = c;
	}
}