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
import logica.CasellaImprevisti;
import logica.CasellaIsola;
import logica.CasellaJolly;
import logica.CasellaStart;
import logica.Domanda;
import logica.FactoryDomande;
import logica.Giocatore;

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
		if (tastoPremuto != -100) 
			Monitor.instance().release();
	}

	public int getTastoPremuto() {
		return tastoPremuto;
	}

	public void run() {
		FactoryDomande.acquisizioneDomande();
		corrente = 0;
		giocatoreCorrente = giocatori[corrente];
	
		/**
		 * main loop
		 * cicla finche' non vince qualcuno
		 */
		Giocatore vincitore;
		while ((vincitore = vincitore()) == null) {
			if (giocatoreCorrente.possoLanciare()) {
				/**
				 *  se il giocatore corrente ha fatto meno di 3 lanci (o non ha sbagliato domande) può lanciare
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
	
						new PopupBivio(giocatoreCorrente.getCasella().getId()).display();
	
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
						new PopupBivio(giocatoreCorrente.getCasella().getId()).display();
	
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
				 *  Azione in base alla casella in cui è andato a finire
				 */
				switch (giocatoreCorrente.getCasella().getTipo()) {
				case Casella.DOMANDA:
					Domanda d = FactoryDomande.dammiDomanda();
					PopupDomanda pop = new PopupDomanda(p, d, "img/sfondoDOMANDE.jpg");
					if (pop.rispostaCorretta()) {
						String mess = CasellaDomanda.AssegnaPunteggio(giocatoreCorrente);
						JOptionPane.showMessageDialog(p, mess, "Risposta esatta!", JOptionPane.INFORMATION_MESSAGE, null);
					} else {
						JOptionPane.showMessageDialog(p, "Risposta errata! Passa il turno al prossimo giocatore.", "Risposta errata!",
								JOptionPane.ERROR_MESSAGE, null);
						giocatoreCorrente.setNumLanci(3);
					}
					break;
				case Casella.JOLLY:
					String[] mess = CasellaJolly.jolly(giocatoreCorrente);
					new PopupInformazione(mess, "Un bonus per te!", "img/sfondoJOLLY.jpg");
					break;
				case Casella.IMPREVISTO:
					String[] mess1 = CasellaImprevisti.imprevisti(giocatoreCorrente);
					new PopupInformazione(mess1, "Ops...un imprevisto!", "img/sfondoIMPREVISTI.jpg");
					break;
				case Casella.ISOLA:
					String[] mess2 = {"", CasellaIsola.isola(giocatoreCorrente)};
					new PopupInformazione(mess2, "Isola", "img/sfondoISOLA.jpg");
					break;
				case Casella.CENTRALE:
					// non fare nulla..
					break;
				default:
					break;
				}
				
			} else { /* if giocatoreCorrente.possoLanciare() == false */
				/**
				 * Se ha fatto più di 3 lanci (o ha sbagliato una domanda) passa il turno al prossimo giocatore
				 */
				giocatoreCorrente.setNumLanci(0);
				giocatoreCorrente.setCasellaPrecedente(null);
				giocatoreCorrente = giocatori[++corrente % giocatori.length];
			}
			p.aggiornaPunteggi();
		} /** fine while */
		
		/**
		 * Vittoria
		 */
		new PopupVittoria(vincitore.getNome());
		AcchiappaRifiuti.instance().restart();
	}
	
	private void waitForInput() {
		while (tastoPremuto == -100) {
			try {
//				p.requestFocusInWindow();
//				p.requestFocus();
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
		if (dadoLanciato) Monitor.instance().release();
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
