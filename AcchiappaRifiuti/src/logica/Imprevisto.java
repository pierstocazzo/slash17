package logica;

public class Imprevisto {
	int quantita;
	String rifiuto;
	boolean passaTurno;
	String testoPiccolo;
	String testoGrande;
	
	public String getTestoPiccolo() {
		return testoPiccolo;
	}

	public void setTestoPiccolo(String testoPiccolo) {
		this.testoPiccolo = testoPiccolo;
	}

	public String getTestoGrande() {
		return testoGrande;
	}

	public void setTestoGrande(String testoGrande) {
		this.testoGrande = testoGrande;
	}

	public Imprevisto(String testoPiccolo, String testoGrande, int quantita, String rifiuto) {
		this.quantita = quantita;
		this.rifiuto = rifiuto;
		this.testoPiccolo = testoPiccolo;
		this.testoGrande = testoGrande;
		this.passaTurno = false;
	}

	public Imprevisto(String testoPiccolo, String testoGrande, boolean passoTurno) {
		this.passaTurno = passoTurno;
		this.testoGrande = testoGrande;
		this.testoPiccolo = testoPiccolo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getRifiuto() {
		return rifiuto;
	}

	public void setRifiuto(String rifiuto) {
		this.rifiuto = rifiuto;
	}

	public boolean isPassaTurno() {
		return passaTurno;
	}

	public void setPassaTurno(boolean passaTurno) {
		this.passaTurno = passaTurno;
	}

}
