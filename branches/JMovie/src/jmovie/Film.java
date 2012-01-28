package jmovie;

public class Film implements Comparable<Film> {

	String titolo = "";
	String regia = "";
	String anno = "";
	String trama = "";
	String pathLocandina = "";
	String genere = "";
	String cast = "";
	String durata = "";
	String titoloOriginale = "";
	String elencoLingue = "";
	String homepage = "";
	
	boolean inListaDesideri = false;
	
	public Film() {
	}
	
	public Film(String titolo, String regia, String anno) {
		this.titolo = titolo;
		this.regia = regia;
		this.anno = anno;
	}

	public Film(FilmUPResult filmUPResult) {
		this.titolo = filmUPResult.titolo;
		this.regia = filmUPResult.regia;
		this.anno = filmUPResult.anno;
	}

	@Override
	public int compareTo(Film o) {
		return this.titolo.compareTo(o.titolo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Film) {
			Film f = (Film) obj;
			return titolo.equals(f.titolo) && regia.equals(f.regia) && anno.equals(f.anno);
		}
		return false;
	}
	
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		if (titolo != null)
			this.titolo = titolo;
	}

	public String getRegia() {
		return regia;
	}

	public void setRegia(String regia) {
		if (regia != null)
			this.regia = regia;
	}	

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		if (anno != null && anno.matches("(19[0-9][0-9]|20[0-9][0-9])"))
			this.anno = anno;
	}

	public String getTrama() {
		return trama;
	}

	public void setTrama(String trama) {
		if (trama != null)
			this.trama = trama;
	}

	public String getPathLocandina() {
		return pathLocandina;
	}

	public void setPathLocandina(String pathLocandina) {
		if (pathLocandina != null)
			this.pathLocandina = pathLocandina;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		if (genere != null)
			this.genere = genere;
	}

	public String getCast() {
		return cast;
	}

	public void setCast(String cast) {
		if (cast != null)
			this.cast = cast;
	}

	public String getDurata() {
		return durata;
	}

	public void setDurata(String durata) {
		if (durata != null)
			this.durata = durata;
	}

	public boolean isInListaDesideri() {
		return inListaDesideri;
	}

	public void setInListaDesideri(boolean inListaDesideri) {
		this.inListaDesideri = inListaDesideri;
	}

	public String getTitoloOriginale() {
		return titoloOriginale;
	}

	public void setTitoloOriginale(String titoloOriginale) {
		if (titoloOriginale != null)
			this.titoloOriginale = titoloOriginale;
	}

	public String getElencoLingue() {
		return elencoLingue;
	}

	public void setElencoLingue(String elencoLingue) {
		if (elencoLingue != null)
			this.elencoLingue = elencoLingue;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		if (homepage != null)
			this.homepage = homepage;
	}
}
