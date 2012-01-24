package jmovie;

public class Film implements Comparable<Film> {

	String titolo;
	String regista;
	int anno;
	
	public Film(String titolo, String regista, int anno) {
		this.titolo = titolo;
		this.regista = regista;
		this.anno = anno;
	}

	public Film(FilmUPResult filmUPResult) {
		this.titolo = filmUPResult.titolo;
		this.regista = filmUPResult.regista;
		this.anno = new Integer(filmUPResult.anno);
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String nome) {
		this.titolo = nome;
	}

	public String getRegista() {
		return regista;
	}

	public void setRegista(String regista) {
		this.regista = regista;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	@Override
	public int compareTo(Film o) {
		return this.titolo.compareTo(o.titolo);
	}
}
