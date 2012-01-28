package jmovie;

public class FilmUPResult {
	String titolo;
	String regia;
	String anno;
	String url;
	
	public FilmUPResult(String titolo, String url) {
		super();
		this.titolo = titolo;
		this.url = url;
	}

	public FilmUPResult(String titolo, String regista, String anno, String url) {
		this.titolo = titolo;
		this.regia = regista;
		this.anno = anno;
		this.url = url;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getRegista() {
		return regia;
	}

	public void setRegista(String regista) {
		this.regia = regista;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return titolo + " - " + regia + " - " + anno;
	}
}
