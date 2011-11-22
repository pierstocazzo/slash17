package logica;

public class CasellaStart extends Casella {

	protected String laterale1;
	protected String laterale2;
	protected String laterale3;
	protected String laterale4;
	protected String laterale5;
	protected String laterale6;
	Casella est;
	Casella ovest;
	Casella nordest;
	Casella nordovest;
	Casella sudest;
	Casella sudovest;

	public CasellaStart(Casella est, Casella ovest, Casella nordest, Casella nordovest, Casella sudest, Casella sudovest, String id, String nome, String x,
			String y, String down, String up, String laterale) {
		super(id, nome, x, y, down, up, laterale);
		this.est = est;
		this.ovest = ovest;
		this.nordest = nordest;
		this.nordovest = nordovest;
		this.sudest = sudest;
		this.sudovest = sudovest;
	}

	public CasellaStart(String id, String nome, String x, String y, String down, String up, String laterale, String laterale1, String laterale2,
			String laterale3, String laterale4, String laterale5, String laterale6) {
		super(id, nome, x, y, down, up, laterale);
		this.laterale1 = laterale1;
		this.laterale2 = laterale2;
		this.laterale3 = laterale3;
		this.laterale4 = laterale4;
		this.laterale5 = laterale5;
		this.laterale6 = laterale6;
	}

	public void setLaterali() {
		this.nordest = GestioneCaselle.findCasella(laterale1);
		this.est = GestioneCaselle.findCasella(laterale2);
		this.sudest = GestioneCaselle.findCasella(laterale3);
		this.sudovest = GestioneCaselle.findCasella(laterale4);
		this.ovest = GestioneCaselle.findCasella(laterale5);
		this.nordovest = GestioneCaselle.findCasella(laterale6);
	}

	public String toString() {
		return ("" + id + "" + nome + "" + x + "" + "" + y + "" + down + "" + up + "" + laterale + "" + laterale1 + "" + laterale2 + "" + laterale3 + ""
				+ laterale4 + "" + laterale5 + "" + laterale6);
	}

	public Casella getEst() {
		return est;
	}

	public Casella getNordest() {
		return nordest;
	}

	public Casella getNordovest() {
		return nordovest;
	}

	public Casella getOvest() {
		return ovest;
	}

	public Casella getSudest() {
		return sudest;
	}

	public Casella getSudovest() {
		return sudovest;
	}

}
