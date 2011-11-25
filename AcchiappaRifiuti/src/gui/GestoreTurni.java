/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import logica.Casella;
import logica.CasellaDomanda;
import logica.CasellaIsola;
import logica.CasellaStart;
import logica.Domanda;
import logica.FactoryDomande;
import logica.FactoryImprevisti;
import logica.FactoryJolly;
import logica.Giocatore;
import logica.Imprevisto;
import logica.Jolly;
import sound.Player;

/**
 * 
 * @author Sal
 */
public class GestoreTurni extends Thread {

	private static GestoreTurni t;
	private Giocatore giocatoreCorrente;
	private int corrente;
	private Giocatore[] giocatori;
	private PannelloPricipale p;
	private static int tastoPremuto = -100;
	boolean dadoLanciato;
	
	public static GestoreTurni instance() {
		if (t == null) {
			t = new GestoreTurni();
		}
		return t;
	}
	
	public static GestoreTurni newInstance() {
		t = new GestoreTurni();
		return t;
	}

	public void setGiocatori(Giocatore[] giocatori) {
		this.giocatori = giocatori;
	}

	public void setPannello(PannelloPricipale p) {
		this.p = p;
	}

	public void setTastoPremuto(int t) {
		tastoPremuto = t;
	}

	public int getTastoPremuto() {
		return tastoPremuto;
	}

	public void run() {
		FactoryDomande.acquisizioneDomande();
		FactoryImprevisti.acquisizioneImprevisti();
		FactoryJolly.acquisizioneJolly();
		corrente = 0;
		giocatoreCorrente = giocatori[corrente];
		
		/** startTime in secondi */
		long startTime = System.currentTimeMillis()/1000;
		long time = System.currentTimeMillis()/1000 - startTime;
		/**
		 * main loop
		 * cicla finche' non vince qualcuno
		 */
		Giocatore vincitore;
		while ((vincitore = vincitore()) == null) {
			
			if (giocatoreCorrente.possoLanciare()) {
				/**
				 *  se il giocatore corrente ha fatto meno di 3 lanci (o non ha sbagliato domande) pu� lanciare
				 */
				String message = giocatoreCorrente.getNome() + " e' il tuo turno!\n";
	
				p.scriviAreaPrincipale(message);
				new PopupDado(message).display();
				
				setDadoLanciato(false);
				int passiDisponibili = giocatoreCorrente.lancia();
				
				message = giocatoreCorrente.getNome() + " hai fatto " + passiDisponibili + ", muovi la pedina. \n" +
						"Ti rimangono " + (3 - giocatoreCorrente.getNumLanci()) + " lanci\n\n";
				JOptionPane.showMessageDialog(p, message, "Lancio dado", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getClassLoader().getResource("img/dado.gif")));
	
				p.scriviAreaPrincipale(message);
	
				/**
				 *  il giocatore muove la pedina di tanti passi quanto ha ottenuto con il lancio del dado
				 */
				while (passiDisponibili > 0) {
					if (giocatoreCorrente.getCasella().isCentrale()) {
						/**
						 * se il giocatore si trova nella casella
						 * centrale
						 */
						CasellaStart cs = (CasellaStart) giocatoreCorrente.getCasella();
						
						p.setSfondo(PannelloTabellone.CENTRALE);
						
						p.requestFocusInWindow();
						p.requestFocus();
						p.setInputAttivo(true);
						waitForInput();
						p.setInputAttivo(false);
	
						p.setSfondo(PannelloTabellone.DEFAULT);
						
						switch (tastoPremuto) {
						case KeyEvent.VK_E:
							giocatoreCorrente.setCasella(cs.getNordest());
							break;
						case KeyEvent.VK_D:
							giocatoreCorrente.setCasella(cs.getEst());
							break;
						case KeyEvent.VK_C:
							giocatoreCorrente.setCasella(cs.getSudest());
							break;
						case KeyEvent.VK_Z:
							giocatoreCorrente.setCasella(cs.getSudovest());
							break;
						case KeyEvent.VK_A:
							giocatoreCorrente.setCasella(cs.getOvest());
							break;
						case KeyEvent.VK_Q:
							giocatoreCorrente.setCasella(cs.getNordovest());
							break;
						default:
							break;
						}
						tastoPremuto = -100;
						p.aggiornaTabellone();
	
					} else if (giocatoreCorrente.getCasella().isBivio()) {
						/**
						 * se il giocatore si trova in una casella bivio
						 */
						String img;
						String casella = giocatoreCorrente.getCasella().getId();
						if (casella.equals("6")) {
							img = PannelloTabellone.NE;
						} else if (casella.equals("13")) {
							img = PannelloTabellone.E;
						} else if (casella.equals("20")) {
							img = PannelloTabellone.SE;
						} else if (casella.equals("29")) {
							img = PannelloTabellone.SO;
						} else if (casella.equals("36")) {
							img = PannelloTabellone.O;
						} else if (casella.equals("43")) {
							img = PannelloTabellone.NO;
						} else {
							img = PannelloTabellone.DEFAULT;
						}
						
						p.setSfondo(img);
						
						p.requestFocusInWindow();
						p.requestFocus();
						p.setInputAttivo(true);
						waitForInput();
						p.setInputAttivo(false);
	
						p.setSfondo(PannelloTabellone.DEFAULT);
						
						switch (tastoPremuto) {
						case KeyEvent.VK_D:
							giocatoreCorrente.setCasella(giocatoreCorrente.getCasella().sensoOrario());
							break;
						case KeyEvent.VK_A:
							giocatoreCorrente.setCasella(giocatoreCorrente.getCasella().sensoAntiOrario());
							break;
						case KeyEvent.VK_S:
							giocatoreCorrente.setCasella(giocatoreCorrente.getCasella().getCasellaLaterale());
							break;
						default:
							break;
						}
						p.aggiornaTabellone();
						tastoPremuto = -100;
	
					} else {
						/**
						 * se il giocatore si trova in una casella
						 * normale
						 */
						p.requestFocusInWindow();
						p.requestFocus();
						
						p.setInputAttivo(true);
						
						waitForInput();
						
						p.setInputAttivo(false);
	
						switch (tastoPremuto) {
						case KeyEvent.VK_D:
							giocatoreCorrente.setCasella(giocatoreCorrente.getCasella().sensoOrario());
							break;
						case KeyEvent.VK_A:
							giocatoreCorrente.setCasella(giocatoreCorrente.getCasella().sensoAntiOrario());
							break;
						}
						p.aggiornaTabellone();
						tastoPremuto = -100;
					}
	
					passiDisponibili--;
				}
	
				/**
				 *  Azione in base alla casella in cui � andato a finire
				 */
				switch (giocatoreCorrente.getCasella().getTipo()) {
				case Casella.DOMANDA:
					Player.play(Player.DOMANDA);
					Domanda d = FactoryDomande.dammiDomanda();
					PopupDomanda pop = new PopupDomanda(p, d, "img/sfondoDOMANDE.jpg");
					if (pop.rispostaCorretta()) {
						Player.play(Player.RISPOSTACORRETTA);
						String mess = CasellaDomanda.AssegnaPunteggio(giocatoreCorrente);
						JOptionPane.showMessageDialog(p, mess, "Risposta esatta!", JOptionPane.INFORMATION_MESSAGE, null);
					} else {
						Player.play(Player.RISPOSTAERRATA);
						String mess = "";
						if (p.isSinglePlayer())
							mess = "Risposta errata!";
						else 
							mess = "Risposta errata! Passa il turno al prossimo giocatore.";
						JOptionPane.showMessageDialog(p, mess, "Risposta errata!",
								JOptionPane.ERROR_MESSAGE, null);
						giocatoreCorrente.setNumLanci(3);
					}
					break;
				case Casella.JOLLY:
					Player.play(Player.JOLLY);
					Jolly j = FactoryJolly.dammiJolly();
					if (j.isLancioExtra()) {
						giocatoreCorrente.setNumLanci(giocatoreCorrente.getNumLanci()-1);
					} else {
						giocatoreCorrente.aggiorna(j.getRifiuto(), j.getQuantita());
					}
					new PopupInformazione(j.getTestoPiccolo(), j.getTestoGrande(), "Jolly!", "img/sfondoJOLLY.jpg");
					break;
				case Casella.IMPREVISTO:
					Player.play(Player.IMPREVISTO);
					Imprevisto i = FactoryImprevisti.dammiImprevisto();
					if (i.isPassaTurno()) {
						giocatoreCorrente.setNumLanci(3);
					} else {
						giocatoreCorrente.aggiorna(i.getRifiuto(), i.getQuantita());
					}
					new PopupInformazione(i.getTestoPiccolo(), i.getTestoGrande(), "Ops...un imprevisto!", "img/sfondoIMPREVISTI.jpg");
					break;
				case Casella.ISOLA:
					String mess2 = CasellaIsola.isola(giocatoreCorrente);
					new PopupInformazione("", mess2, "Isola", "img/sfondoISOLA.jpg");
					break;
				case Casella.CENTRALE:
					// non fare nulla..
					break;
				default:
					break;
				}
				p.aggiornaTabellone();
				
			} else { /* if giocatoreCorrente.possoLanciare() == false */
				/**
				 * Se ha fatto pi� di 3 lanci (o ha sbagliato una domanda) passa il turno al prossimo giocatore
				 */
				giocatoreCorrente.setNumLanci(0);
				giocatoreCorrente.setCasellaPrecedente(null);
				giocatoreCorrente = giocatori[++corrente % giocatori.length];
			}
			p.aggiornaPunteggi();
			if (p.isSinglePlayer()) 
				time = System.currentTimeMillis()/1000 - startTime;
		} /** fine while */
		
		/**
		 * Vittoria
		 */
		Player.play(Player.VITTORIA);
		if (p.isSinglePlayer()) {
			new PopupVittoria(vincitore.getNome(), time);
		} else {
			new PopupVittoria(vincitore.getNome(), -1);
		}
		AcchiappaRifiuti.instance().finished();
	}
	
	private void waitForInput() {
		while (tastoPremuto == -100) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	private Giocatore vincitore() {
		for (Giocatore g : giocatori) {
			if (g.getPunt_carta() >= 150 && g.getPunt_indifferenziata() >= 150 && g.getPunt_metallo() >= 150 && g.getPunt_organica() >= 150
					&& g.getPunt_plastica() >= 150 && g.getPunt_vetro() >= 150) {
				return g;
			}
		}
		return null;
	}


	public boolean isDadoLanciato() {
		return dadoLanciato;
	}

	public synchronized void setDadoLanciato(boolean dadoLanciato) {
		this.dadoLanciato = dadoLanciato;
	}

	public PannelloPricipale getPannello() {
		return p;
	}
	
	
	public boolean check(int keyCode) {
		Casella next = null;
		if (giocatoreCorrente.getCasella().isCentrale()) {
			/**
			 * se il giocatore si trova nella casella
			 * centrale
			 */
			CasellaStart cs = (CasellaStart) giocatoreCorrente.getCasella();
			switch (keyCode) {
			case KeyEvent.VK_E:
				next = cs.getNordest();
				break;
			case KeyEvent.VK_D:
				next = cs.getEst();
				break;
			case KeyEvent.VK_C:
				next = cs.getSudest();
				break;
			case KeyEvent.VK_Z:
				next = cs.getSudovest();
				break;
			case KeyEvent.VK_A:
				next = cs.getOvest();
				break;
			case KeyEvent.VK_Q:
				next = cs.getNordovest();
				break;
			}

		} else if (giocatoreCorrente.getCasella().isBivio()) {
			/**
			 * se il giocatore si trova in una casella bivio
			 */
			switch (keyCode) {
			case KeyEvent.VK_D:
				next = giocatoreCorrente.getCasella().sensoOrario();
				break;
			case KeyEvent.VK_A:
				next = giocatoreCorrente.getCasella().sensoAntiOrario();
				break;
			case KeyEvent.VK_S:
				next = giocatoreCorrente.getCasella().getCasellaLaterale();
				break;
			}
		} else {
			/**
			 * se il giocatore si trova in una casella
			 * normale
			 */
			switch (keyCode) {
			case KeyEvent.VK_D:
				next = giocatoreCorrente.getCasella().sensoOrario();
				break;
			case KeyEvent.VK_A:
				next = giocatoreCorrente.getCasella().sensoAntiOrario();
				break;
			}
		}
		if (next == null || next == giocatoreCorrente.getCasellaPrecedente()) 
			return false;
		return true;
	}
}
