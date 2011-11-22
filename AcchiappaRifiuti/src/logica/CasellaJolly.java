package logica;

public class CasellaJolly {

	static Giocatore giocatore;
	static String s;

	public static String[] jolly(Giocatore g) {

		giocatore = g;

		int k = (int) (Math.random() * 18);

		switch (k) {
		case 0:
			s= vinciDieciMetallo();
			break;
		case 1:
			s= vinciDieciPlastica();
			break;
		case 2:
			s= vinciDieciRifiutiorg();
			break;
		case 3:
			s= vinciDieciCarta();
			break;
		case 4:
			s= vinciDieciVetro();
			break;
		case 5:
			s= vinciDieciIndifferenziata();
			break;
		case 6:
			s= vinciCentoMetallo();
			break;
		case 7:
			s= vinciCentoRifiutiorg();
			break;
		case 8:
			s= vinciCentoCarta();
			break;
		case 9:
			s= vinciCentoVetro();
			break;
		case 10:
			s= vinciCentoIndifferenziata();
			break;
		case 11:
			s= vinciCentoPlastica();
			break;
		case 12:
			s= vinciCinquantaMetallo();
			break;
		case 13:
			s= vinciCinquantaRifiutiorg();
			break;
		case 14:
			s= vinciCinquantaCarta();
			break;
		case 15:
			s= vinciCinquantaVetro();
			break;
		case 16:
			s= vinciCinquantaIndifferenziata();
			break;
		case 17:
			s= vinciCinquantaPlastica();
			break;
		}

		return s.split("_"); 
	}

	public static String vinciDieciMetallo() { // 0
		giocatore.punt_metallo += 10;
		return "Il tuo computer non funziona bene. Invece di sostituirlo con uno nuovo, lo fai riparare. Ben fatto. _ ACCHIAPPI 10 kg di metallo";
	}

	public static String vinciDieciRifiutiorg() {// 1
		giocatore.punt_organica += 10;
		return "Sei andato a fare la spesa e hai acquistato prodotti di stagione. Ben fatto. _ ACCHIAPPI 10 kg di organica";
	}

	public static String vinciDieciCarta() {// 2
		giocatore.punt_carta += 10;
		return "Vai a fare la spesa e, invece di utilizzare i sacchetti di plastica, alla cassa tiri fuori un sacchetto in materiale naturale portato da casa. Ben Fatto. _ ACCHIAPPI 10 kg di carta";
	}

	public static String vinciDieciVetro() {// 3
		giocatore.punt_vetro += 10;
		return "Tempo fa hai comprato un vestito che adesso non ti piace piu'. Invece di buttarlo lo regali a qualcuno che lo trova ancora bello. Ben fatto. _ ACCHIAPPI 10 kg di vetro";
	}

	public static String vinciDieciPlastica() {// 4
		giocatore.punt_plastica += 10;
		return "Vai a fare la spesa e, invece di utilizzare i sacchetti di plastica, porti con te un imballaggio in cartone ripiegabile e riutilizzabile piu' volte. Ben Fatto. _ ACCHIAPPI 10 kg di plastica";
	}

	public static String vinciDieciIndifferenziata() { // 5
		giocatore.punt_indifferenziata += 10;
		return "Hai sete e, invece di bere dell'acqua minerale imbottigliata, ti disseti con l'acqua del rubinetto. Ben fatto. _ ACCHIAPPI 10 kg di indifferenziata";
	}

	public static String vinciCentoMetallo() { // 6
		giocatore.punt_metallo += 100;
		return "Riduci lo spreco di materiale da imballaggio comprando le ricariche dove e quando disponibili. Ben fatto. _ ACCHIAPPI 100 kg di metalli ";
	}

	public static String vinciCentoRifiutiorg() { // 7
		giocatore.punt_organica += 100;
		return "Usi la lavatrice solo a pieno carico. Ben fatto. _ ACCHIAPPI 100 kg di organica";
	}

	public static String vinciCentoCarta() { // 8
		giocatore.punt_carta += 100;
		return "Non getti mai l'olio residuo dalla frittura nel lavandino perche' estremamente inquinante. Ben fatto. _ ACCHIAPPI 100 kg di carta";
	}

	public static String vinciCentoPlastica() {// 9
		giocatore.punt_plastica += 100;
		return "Curi la manutenzione degli oggetti e degli elettrodomestici di tua proprieta' per allungarne il ciclo di vita. Ben fatto. _ ACCHIAPPI 100 kg di plastica";
	}

	public static String vinciCentoVetro() {// 10
		giocatore.punt_vetro += 100;
		return "Utilizzi i tovaglioli di stoffa oppure quelli usa e getta con il marchio Ecolabel. Ben fatto. _ ACCHIAPPI 100 kg di vetro";
	}

	public static String vinciCentoIndifferenziata() {// 11
		giocatore.punt_indifferenziata += 100;
		return "Dosi il detersivo nella lavatrice in base alla sporco e alla durezza dell'acqua. Ben fatto. _ ACCHIAPPI 100 kg di indifferenziata ";
	}

	public static String vinciCinquantaMetallo() {// 12
		giocatore.punt_metallo += 50;
		return "Devi stampare un documento che hai salvato sul computer. Eviti la stampa delle parti che non ti interessano. Ben fatto. _ ACCHIAPPI 50 kg di metalli";
	}

	public static String vinciCinquantaRifiutiorg() {// 13
		giocatore.punt_organica += 50;
		return "Sei al supermercato e acquisti i legumi sfusi invece di quelli inscatolati o surgelati. Ben fatto. _ ACCHIAPPI 50 kg di organica";
	}

	public static String vinciCinquantaCarta() { // 14
		giocatore.punt_carta += 50;
		return "Sei al supermercato e devi comprare te' e tisane. Preferisci gli infusi contenuti in barattoli di vetro e non in confezioni monodose. Ben fatto. _ ACCHIAPPI 50 kg di carta";
	}

	public static String vinciCinquantaPlastica() {// 15
		giocatore.punt_plastica += 50;
		return "Vuoi regalare un libro. Ne acquisti uno realizzato con carta riciclata. Ben fatto. _ ACCHIAPPI 50 kg di plastica";
	}

	public static String vinciCinquantaVetro() {// 16
		giocatore.punt_vetro += 50;
		return "Apparecchi la tavola ed utilizzi piatti e bicchieri di vetro invece di quelli usa e getta. Ben fatto. _ ACCHIAPPI 50 kg di vetro";
	}

	public static String vinciCinquantaIndifferenziata() {// 17
		giocatore.punt_indifferenziata += 50;
		return "Vuoi regalare un peluche e ne scegli uno in fibre naturali. Ben fatto. _ ACCHIAPPI 50 kg di indifferenziata";
	}
}
