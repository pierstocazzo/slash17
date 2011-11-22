package logica;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */
public class Casella {

	public static final int DOMANDA = 0;
	public static final int JOLLY = 1;
	public static final int IMPREVISTO = 2;
	public static final int ISOLA = 3;
	public static final int CENTRALE = 4;
	
	/**
	 * E' l'id della Casella (ES. 1,...,n) E' il nome della Casella (ES.
	 * 100Organica, etc.) coordinata x coordinata y
	 */
	protected String id;
	protected String nome;
	protected String x;
	protected String y;
	protected String up;
	protected String down;
	protected String laterale;
	Casella casellaup;
	Casella caselladown;
	Casella casellalaterale;

	String domanda;
	String imprevisto;
	String jolly;

	/**
	 * 
	 * E' il costruttore della classe Casella. Prende come argomento il numero
	 * della casella
	 * 
	 * @param numeroCasella
	 * 
	 */
	public Casella(String id, String nome, String x, String y, String down, String up, String laterale) {
		this.id = id;
		this.nome = nome;
		this.x = x;
		this.y = y;
		this.down = down;
		this.up = up;
		this.laterale = laterale;
	}

	public boolean isBivio() {
		return (this.casellalaterale == null) ? false : true;
	}

	public boolean isCentrale() {
		return (this instanceof CasellaStart) ? true : false;
	}

	public int getTipo() {
		if (this.nome.matches("imprevisti"))
			return IMPREVISTO;
		else if (this.nome.matches("jolly"))
			return JOLLY;
		else if (this.nome.matches("isola.*"))
			return ISOLA;
		else if (this.nome.matches("start"))
			return CENTRALE;
		else
			return DOMANDA;
	}

	public String getDomanda() {
		return domanda;
	}

	public void setDomanda(String domanda) {
		this.domanda = domanda;
	}

	public String getImprevisto() {
		return imprevisto;
	}

	public void setImprevisto(String imprevisto) {
		this.imprevisto = imprevisto;
	}

	public String getJolly() {
		return jolly;
	}

	public void setJolly(String jolly) {
		this.jolly = jolly;
	}

	public Casella sensoAntiOrario() {
		return caselladown;
	}

	public void setProssimaCasellaSensoAntiOrario(Casella caselladown) {
		this.caselladown = caselladown;
	}

	public Casella getCasellaLaterale() {
		return casellalaterale;
	}

	public void setCasellaLaterale(Casella casellalaterale) {
		this.casellalaterale = casellalaterale;
	}

	public Casella sensoOrario() {
		return casellaup;
	}

	public void setProssimaCasellaSensoOrario(Casella casellaup) {
		this.casellaup = casellaup;
	}

	@Override
	public String toString() {
		return ("" + id + "," + nome + "," + x + "," + y + "," + down + "," + up + "," + laterale);

	}

	public String getLaterale() {
		return laterale;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
	}

	/**
	 * Restituisce l'id della casella
	 */
	public String getId() {
		return id;
	}

	/**
	 * Restituisce il nome della casella
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Imposta l'id della casella passato come parametro
	 * 
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void setLaterale(String laterale) {
		this.laterale = laterale;
	}

	/**
	 * Imposta il nome della casella passato come parametro
	 * 
	 * @param nome
	 * 
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
}
