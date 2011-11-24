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
	protected int punt_carta;
	protected int punt_plastica;
	protected int punt_vetro;
	protected int punt_metallo;
	protected int punt_indifferenziata;
	protected int punt_organica;

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
	 * 
	 */
	public Giocatore(String nome, Casella c) {
		this.nome = nome;
		this.casella = c;
		dado = new Dado();
		
		punt_carta = 0;
		punt_plastica = 0;
		punt_vetro = 0;
		punt_metallo = 0;
		punt_indifferenziata = 0;
		punt_organica = 0;
	}

	/*
	 * Metodo che richiama la funzione lancia del Dado
	 */

	public int lancia() {
		lancio = dado.tiraDado();
		this.numLanci++;
		return lancio;
	}

	public void aggiorna(String rifiuto, int quantità) {
		if (rifiuto.equalsIgnoreCase(ORGANICA)) {
			punt_organica += quantità;
			if (punt_organica < 0) 
				punt_organica = 0;
			if (punt_organica > 150)
				punt_organica = 150;
		} else if (rifiuto.equalsIgnoreCase(PLASTICA)) {
			punt_plastica += quantità;
			if (punt_plastica < 0) 
				punt_plastica = 0;
			if (punt_plastica > 150)
				punt_plastica = 150;
		} else if (rifiuto.equalsIgnoreCase(METALLO)) {
			punt_metallo += quantità;
			if (punt_metallo < 0) 
				punt_metallo = 0;
			if (punt_metallo > 150)
				punt_metallo = 150;
		} else if (rifiuto.equalsIgnoreCase(CARTA)) {
			punt_carta += quantità;
			if (punt_carta < 0) 
				punt_carta = 0;
			if (punt_carta > 150)
				punt_carta = 150;
		} else if (rifiuto.equalsIgnoreCase(VETRO)) {
			punt_vetro += quantità;
			if (punt_vetro < 0) 
				punt_vetro = 0;
			if (punt_vetro > 150)
				punt_vetro = 150;
		} else if (rifiuto.equalsIgnoreCase(INDIFFERENZIATA)) {
			punt_indifferenziata += quantità;
			if (punt_indifferenziata < 0) 
				punt_indifferenziata = 0;
			if (punt_indifferenziata > 150)
				punt_indifferenziata = 150;
		} else {
			System.out.println("Fuck " + rifiuto + " " + quantità);
		}
	}
	
	/*
	 * Funzione di uscita nel caso in cui si deve passare il turno se si �
	 * raggiunto il numero di lanci a disposizione, oppure, nel caso in cui si
	 * � risposto in modo errato
	 */
	public boolean possoLanciare() {

		return this.numLanci < 3;
	}

	/*
	 * Controllo del punteggio del giocatore
	 */
	public int getPunt_carta() {
		return punt_carta;
	}

	public void setPunt_carta(int punt_carta) {
		this.punt_carta = punt_carta;
	}

	public int getPunt_plastica() {
		return punt_plastica;
	}

	public void setPunt_plastica(int punt_plastica) {
		this.punt_plastica = punt_plastica;
	}

	public int getPunt_vetro() {
		return punt_vetro;
	}

	public void setPunt_vetro(int punt_vetro) {
		this.punt_vetro = punt_vetro;
	}

	public int getPunt_metallo() {
		return punt_metallo;
	}

	public void setPunt_metallo(int punt_metallo) {
		this.punt_metallo = punt_metallo;
	}

	public int getPunt_indifferenziata() {
		return punt_indifferenziata;
	}

	public void setPunt_indifferenziata(int punt_indifferenziata) {
		this.punt_indifferenziata = punt_indifferenziata;
	}

	public int getPunt_organica() {
		return punt_organica;
	}

	public void setPunt_organica(int punt_organica) {
		this.punt_organica = punt_organica;
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