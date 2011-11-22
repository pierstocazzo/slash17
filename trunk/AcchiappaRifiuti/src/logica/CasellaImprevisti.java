package logica;

/**
 * 
 * @authors Diana e Paola (Grid&Cloud)
 * 
 * 
 */
public class CasellaImprevisti {

	static Giocatore giocatore;
	static String s;

	public static String[] imprevisti(Giocatore g) {

		giocatore = g;

		int k = (int) (Math.random() * 18);

		switch (k) {
		case 0:
			s= perdiDieciMetallo();
			break;
		case 1:
			s= perdiDieciPlastica();
			break;
		case 2:
			s= perdiDieciRifiutiorg();
			break;
		case 3:
			s= perdiDieciCarta();
			break;
		case 4:
			s= perdiDieciVetro();
			break;
		case 5:
			s= perdiDieciIndifferenziata();
			break;
		case 6:
			s= perdiVentiMetallo();
			break;
		case 7:
			s= perdiVentiRifiutiorg();
			break;
		case 8:
			s= perdiVentiCarta();
			break;
		case 9:
			s= perdiVentiVetro();
			break;
		case 10:
			s= perdiVentiIndifferenziata();
			break;
		case 11:
			s= perdiVentiPlastica();
			break;
		case 12:
			s= perdiCinquantaMetallo();
			break;
		case 13:
			s= perdiCinquantaRifiutiorg();
			break;
		case 14:
			s= perdiCinquantaCarta();
			break;
		case 15:
			s= perdiCinquantaVetro();
			break;
		case 16:
			s= perdiCinquantaIndifferenziata();
			break;
		case 17:
			s= perdiCinquantaPlastica();
			break;
		}

		return s.split("_"); // non dovrebbe mai entrare qua.

	}

	public static String perdiDieciMetallo() {// 0

		if (giocatore.getPunt_metallo() >= 10) {

			giocatore.punt_metallo -= 10;
			return "HAI ABBANDONATO UNA LATTINA IN ALLUMINIO SUL PRATO! CI RIMARRA' DA 20 A 100 ANNI! _ HAI PERSO 10 KG DI METALLO";
		}

		else {

			giocatore.punt_metallo = 0;
			return "HAI ABBANDONATO UNA LATTINA IN ALLUMINIO SUL PRATO! CI RIMARRA' DA 20 A 100 ANNI! _ HAI PERSO 10 KG DI METALLO";
		}
	}

	public static String perdiDieciPlastica() {// 1

		if (giocatore.getPunt_plastica() >= 10) {

			giocatore.punt_plastica -= 10;
			return "HAI ABBANDONATO UNA BOTTIGLIA DI PLASTICA SUL PRATO! CI RIMARRA' DA 100 A 1000 ANNI! _ HAI PERSO 10 KG DI PLASTICA";
		}

		else {

			giocatore.punt_plastica = 0;
			return "HAI ABBANDONATO UNA BOTTIGLIA DI PLASTICA SUL PRATO! CI RIMARRA' DA 100 A 1000 ANNI! _ HAI PERSO 10 KG DI PLASTICA";
		}

	}

	public static String perdiDieciRifiutiorg() {// 2

		if (giocatore.getPunt_organica() >= 10) {
			giocatore.punt_organica -= 10;
			return "HAI ABBANDONATO UN TORSOLO DI MELA SUL PRATO! CI RIMARRA' PER 2 MESI! _ HAI PERSO 10 KG DI ORGANICA";
		} else {
			giocatore.punt_organica = 0;
			return "HAI ABBANDONATO UN TORSOLO DI MELA SUL PRATO! CI RIMARRA' PER 2 MESI! _ HAI PERSO 10 KG DI ORGANICA";

		}

	}

	public static String perdiDieciCarta() {// 3

		if (giocatore.getPunt_carta() >= 10) {
			giocatore.punt_carta -= 10;
			return "HAI ABBANDONATO UN GIORNALE SUL PRATO! CI RIMARRA' PER 6 SETTIMANE! _ HAI PERSO 10 KG DI CARTA";
		} else {
			giocatore.punt_carta = 0;
			return "HAI ABBANDONATO UN GIORNALE SUL PRATO! CI RIMARRA' PER 6 SETTIMANE! _ HAI PERSO 10 KG DI CARTA";

		}

	}

	public static String perdiDieciVetro() {// 4

		if (giocatore.getPunt_vetro() >= 10) {

			giocatore.punt_vetro -= 10;
			return "HAI ABBANDONATO UNA BOTTIGLIA DI VETRO SUL PRATO! CI RIMARRA' PER 4000 ANNI! _ HAI PERSO 10 KG DI VETRO";
		} else {
			giocatore.punt_vetro = 0;
			return "HAI ABBANDONATO UNA BOTTIGLIA DI VETRO SUL PRATO! CI RIMARRA' PER 4000 ANNI! _ HAI PERSO 10 KG DI VETRO";
		}

	}

	public static String perdiDieciIndifferenziata() {// 5

		if (giocatore.getPunt_indifferenziata() >= 10) {

			giocatore.punt_indifferenziata -= 10;
			return "HAI ABBANDONATO UN FAZZOLETTO DI CARTA SUL PRATO! CI RIMARRA' PER 3 MESI! _ HAI PERSO 10 KG DI INDIFFERENZIATA";

		} else {
			giocatore.punt_indifferenziata = 0;
			return "HAI ABBANDONATO UN FAZZOLETTO DI CARTA SUL PRATO! CI RIMARRA' PER 3 MESI! _ HAI PERSO 10 KG DI INDIFFERENZIATA";

		}

	}

	public static String perdiVentiMetallo() {// 6

		if (giocatore.getPunt_metallo() >= 20) {
			giocatore.punt_metallo -= 20;
			return "HAI ACQUISTATO UNO STEREO NUOVO E NON TI SEI PREOCCUPATO DI VERIFICARE CHE SIA A BASSO CONSUMO ENERGETICO. _ HAI PERSO 20 KG DI METALLO";

		} else {
			giocatore.punt_metallo = 0;
			return "HAI ACQUISTATO UNO STEREO NUOVO E NON TI SEI PREOCCUPATO DI VERIFICARE CHE SIA A BASSO CONSUMO ENERGETICO. _ HAI PERSO 20 KG DI METALLO";
		}

	}

	public static String perdiVentiRifiutiorg() {// 7
		if (giocatore.getPunt_organica() >= 20) {
			giocatore.punt_organica -= 20;
			return "VAI AL SUPERMERCATO E PREFERISCI I PRODOTTI MONOPORZIONE INVECE DI QUELLI 'FORMATO FAMIGLIA'. _ HAI PERSO 20 KG DI ORGANICA";
		} else {
			giocatore.punt_organica = 0;
			return "VAI AL SUPERMERCATO E PREFERISCI I PRODOTTI MONOPORZIONE INVECE DI QUELLI 'FORMATO FAMIGLIA'. _ HAI PERSO 20 KG DI ORGANICA";
		}

	}

	public static String perdiVentiCarta() {// 8

		if (giocatore.getPunt_carta() >= 20) {
			giocatore.punt_carta -= 20;
			return "QUANDO DEVI SCEGLIERE UN PRODOTTO LO FAI SOLO IN BASE AL PREZZO, SENZA PREOCCUPARTI SE IL PRODOTTO DANNEGGIA L'AMBIENTE. _ HAI PERSO 20 KG DI CARTA";
		} else {
			giocatore.punt_carta = 0;
			return "QUANDO DEVI SCEGLIERE UN PRODOTTO LO FAI SOLO IN BASE AL PREZZO, SENZA PREOCCUPARTI SE IL PRODOTTO DANNEGGIA L'AMBIENTE. _ HAI PERSO 20 KG DI CARTA";
		}

	}

	public static String perdiVentiVetro() { // 9
		if (giocatore.getPunt_vetro() >= 20) {
			giocatore.punt_vetro -= 20;
			return "UTILIZZI PIATTI 'USA E GETTA' CHE AUMENTANO IL VOLUME DEI RIFIUTI PRODOTTI. _ HAI PERSO 20 KG DI VETRO";
		} else {
			giocatore.punt_vetro = 0;
			return "UTILIZZI PIATTI 'USA E GETTA' CHE AUMENTANO IL VOLUME DEI RIFIUTI PRODOTTI. _ HAI PERSO 20 KG DI VETRO";

		}

	}

	public static String perdiVentiIndifferenziata() {// 10

		if (giocatore.getPunt_indifferenziata() >= 20) {
			giocatore.punt_indifferenziata -= 20;
			return "HAI ACQUISTATO UN NUOVO ELETTRODOMESTICO E NON TI SEI PREOCCUPATO DI VERIFICARE CHE SIA A BASSO CONSUMO ENERGETICO. _ HAI PERSO 20 KG DI INDIFFERENZIATA";
		} else {
			giocatore.punt_indifferenziata = 0;
			return "HAI ACQUISTATO UN NUOVO ELETTRODOMESTICO E NON TI SEI PREOCCUPATO DI VERIFICARE CHE SIA A BASSO CONSUMO ENERGETICO. _ HAI PERSO 20 KG DI INDIFFERENZIATA";
		}

	}

	public static String perdiVentiPlastica() {// 11

		if (giocatore.getPunt_plastica() >= 20) {
			giocatore.punt_plastica -= 20;
			return "QUANDO LAVI I PIATTI, UTILIZZI MOLTO DETERSIVO E MOLTA ACQUA, SENZA PREOCCUPARTI DELLE CONSEGUENZE AMBIENTALI. _ HAI PERSO 20 KG DI PLASTICA";
		} else {
			giocatore.punt_plastica = 0;
			return "QUANDO LAVI I PIATTI, UTILIZZI MOLTO DETERSIVO E MOLTA ACQUA, SENZA PREOCCUPARTI DELLE CONSEGUENZE AMBIENTALI. _ HAI PERSO 20 KG DI PLASTICA";
		}

	}

	public static String perdiCinquantaMetallo() {// 12

		if (giocatore.getPunt_metallo() >= 50) {
			giocatore.punt_metallo -= 50;
			return "DEVI BUTTARE VIA UNA SCATOLA DI CARTONE E, PRIMA DI GETTARLA NELL'APPOSITO CONTENITORE, NON LA SCHIACCI, RIDUCENDONE IL VOLUME. _ HAI PERSO 50 KG DI METALLO";
		} else {
			giocatore.punt_metallo = 0;
			return "DEVI BUTTARE VIA UNA SCATOLA DI CARTONE E, PRIMA DI GETTARLA NELL'APPOSITO CONTENITORE, NON LA SCHIACCI, RIDUCENDONE IL VOLUME. _ HAI PERSO 50 KG DI METALLO";
		}

	}

	public static String perdiCinquantaRifiutiorg() {// 13

		if (giocatore.getPunt_organica() >= 50) {
			giocatore.punt_organica -= 50;
			return "APPARECCHI LA TAVOLA ED UTILIZZI PIATTI E BICCHIERI USA E GETTA INVECE DI QUELLI DI VETRO, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI AD INQUINARE IL PIANETA. _ HAI PERSO 50 KG DI organica";
		} else {
			giocatore.punt_organica = 0;
			return "APPARECCHI LA TAVOLA ED UTILIZZI PIATTI E BICCHIERI USA E GETTA INVECE DI QUELLI DI VETRO, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI AD INQUINARE IL PIANETA. _ HAI PERSO 50 KG DI organica";
		}

	}

	public static String perdiCinquantaCarta() {// 14

		if (giocatore.getPunt_carta() >= 50) {
			giocatore.punt_carta -= 50;
			return "SEI AL SUPERMERCATO E DEVI COMPRARE SALUMI E FORMAGGI. PREFERISCI QUELLI CONFEZIONATI IN VASCHETTE DI PLASTICA O ALLUMINIO E NON QUELLI AFFETTATI O TAGLIATI AL MOMENTO AL BANCO, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI A PRODURRE RIFIUTI DA IMBALLAGGIO. _  HAI PERSO 50 KG DI CARTA";
		} else {
			giocatore.punt_carta = 0;
			return "SEI AL SUPERMERCATO E DEVI COMPRARE SALUMI E FORMAGGI. PREFERISCI QUELLI CONFEZIONATI IN VASCHETTE DI PLASTICA O ALLUMINIO E NON QUELLI AFFETTATI O TAGLIATI AL MOMENTO AL BANCO, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI A PRODURRE RIFIUTI DA IMBALLAGGIO. _  HAI PERSO 50 KG DI CARTA";
		}

	}

	public static String perdiCinquantaVetro() {// 15

		if (giocatore.getPunt_vetro() >= 50) {
			giocatore.punt_vetro -= 50;
			return "SEI AL SUPERMERCATO E ACQUISTI LA VERDURA GIA' TAGLIATA E CONFEZIONATA, PRONTA PER ESSERE MANGIATA INVECE DI QUELLA SFUSA, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI A PRODURRE RIFIUTI DA IMBALLAGGIO. _ HAI PERSO 50 KG DI VETRO";
		} else {
			giocatore.punt_vetro = 0;
			return "SEI AL SUPERMERCATO E ACQUISTI LA VERDURA GIA' TAGLIATA E CONFEZIONATA, PRONTA PER ESSERE MANGIATA INVECE DI QUELLA SFUSA, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI A PRODURRE RIFIUTI DA IMBALLAGGIO. _ HAI PERSO 50 KG DI VETRO";
		}

	}

	public static String perdiCinquantaIndifferenziata() {// 16
		if (giocatore.getPunt_indifferenziata() >= 50) {
			giocatore.punt_indifferenziata -= 50;
			return "VUOI COMPRARE DEL PESCE. PREFERISCI QUELLO SURGELATO A QUELLO FRESCO, SENZA PREOCCUPARTI DI QUANTA ENERGIA H STATA CONSUMATA PER LA SUA CONSERVAZIONE. _ HAI PERSO 50 KG DI INDIFFERENZIATA";
		} else {
			giocatore.punt_indifferenziata = 0;
			return "VUOI COMPRARE DEL PESCE. PREFERISCI QUELLO SURGELATO A QUELLO FRESCO, SENZA PREOCCUPARTI DI QUANTA ENERGIA H STATA CONSUMATA PER LA SUA CONSERVAZIONE. _ HAI PERSO 50 KG DI INDIFFERENZIATA";
		}

	}

	public static String perdiCinquantaPlastica() {// 17
		if (giocatore.getPunt_plastica() >= 50) {
			giocatore.punt_plastica -= 50;
			return "SEI AL SUPERMERCATO E DEVI COMPRARE THE E TISANE. PREFERISCI GLI INFUSI CONTENUTI IN CONFEZIONI MONODOSE E NON IN BARATTOLI DI VETRO, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI A PRODURRE RIFIUTI DA IMBALLAGGIO. _ HAI PERSO 50 KG DI PLASTICA";

		} else {
			giocatore.punt_plastica = 0;
			return "SEI AL SUPERMERCATO E DEVI COMPRARE THE E TISANE. PREFERISCI GLI INFUSI CONTENUTI IN CONFEZIONI MONODOSE E NON IN BARATTOLI DI VETRO, SENZA PREOCCUPARTI DI QUANTO CONTRIBUISCI A PRODURRE RIFIUTI DA IMBALLAGGIO. _ HAI PERSO 50 KG DI PLASTICA";
		}

	}
}
