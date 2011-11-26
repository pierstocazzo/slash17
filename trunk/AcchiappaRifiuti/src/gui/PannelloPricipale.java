/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PannelloPricipèale.java
 *
 * Created on 16-nov-2011, 18.33.38
 */
package gui;

import input.KeyHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import sound.Player;

import logica.Casella;
import logica.GestioneCaselle;
import logica.Giocatore;
import logica.Pedina;

public class PannelloPricipale extends javax.swing.JPanel {

	private static final long serialVersionUID = -7171183377025829221L;
	int giocatoreCorrente = 0;
	Giocatore[] giocatori;
	int numeroGiocatori;
	boolean inputAttivo = false;
	KeyHandler handler;
	boolean dadoEnabled = false;
	boolean singlePlayer = false;

	/** Creates new form PannelloPricipale */
	public PannelloPricipale() {
		GestoreTurni.newInstance();
		
		String s = JOptionPane.showInputDialog(null, "Quanti giocatori?");
		while (!Pattern.matches("\\d+", s) || Integer.parseInt(s) < 1 || Integer.parseInt(s) > 5)
			s = JOptionPane.showInputDialog(null, "Quanti giocatori? Inserisci un numero tra 1 e 5!");

		numeroGiocatori = Integer.parseInt(s);
		if (numeroGiocatori == 1) 
			singlePlayer = true;
		else
			singlePlayer = false;
		
		creaInterfaccia();
		
		giocatori = new Giocatore[numeroGiocatori];
		GestioneCaselle g = new GestioneCaselle();
		g.collega();
		Casella centrale = GestioneCaselle.findCasella("-1");

		for (int i = 0; i < numeroGiocatori; i++) {
			int x = 866/2-15;
			int y = 560/2-15;
			
			String nome = JOptionPane.showInputDialog(null, "Qual e' il nome del giocatore " + (i + 1) + "?");
			
			ClassLoader cldr = this.getClass().getClassLoader();
			Pedina p = new Pedina(new ImageIcon(cldr.getResource("img/pedine/" + (i + 1) + ".png")).getImage(), x, y);

			giocatori[i] = new Giocatore(nome, centrale, p);
			
			pannelloTabellone.addGiocatore(giocatori[i]);
		}
		giocatoreCorrente = 0;
		
		aggiornaPunteggi();

		handler = new KeyHandler(this);
		addKeyListener(handler);

		this.requestFocus();
		this.requestFocusInWindow();

		GestoreTurni.instance().setGiocatori(giocatori);
		GestoreTurni.instance().setPannello(this);
		GestoreTurni.instance().start();
	}

	public boolean isInputAttivo() {
		return inputAttivo;
	}

	public void setInputAttivo(boolean b) {
		inputAttivo = b;
	}

	public void scriviAreaPrincipale(String s) {
		textPaneConsole.setText(textPaneConsole.getText()+s);
		textPaneConsole.setCaretPosition(textPaneConsole.getDocument().getLength());
		textPaneConsole.repaint();
	}

	public void aggiornaTabellone() {
		pannelloTabellone.repaint();
	}

	public void aggiornaPunteggi() {
		textEditorPunteggi.setText("");
		String s = 	"<html><body>" +
					"<center><b><font family='Arial' size='5'>Tabellone punteggi</font></b></center><p/>";
				
		for (int i = 0; i < numeroGiocatori; i++) {
			s += 	"<table width='230' border='0' cellpadding='0' cellspacing='0'>" +
					"<tr><td colspan='2'><b><font size='4'>G" + (i + 1) + ": " + giocatori[i].getNome() + "</font></b></td></tr>" +
					"<font size='4'>" +
					"<tr><td>Carta:</td><td>" + giocatori[i].getPunt_carta() + "</td></tr>" + 
					"<tr><td>Indifferenziato:</td><td>" + giocatori[i].getPunt_indifferenziata() + "</td></tr>" + 
					"<tr><td>Metallo:</td><td>" + giocatori[i].getPunt_metallo() + "</td></tr>" +
					"<tr><td>Vetro:</td><td>" + giocatori[i].getPunt_vetro() + "</td></tr>" +
					"<tr><td>Plastica:</td><td>" + giocatori[i].getPunt_plastica() + "</td></tr>" +
					"<tr><td>Organica:</td><td>" + giocatori[i].getPunt_organica() + "</td></tr>" +
					"</font>" +
					"</table>";
		}
		s += 	"</body></html>";
		textEditorPunteggi.setText(s);
	}

	public int getGiocatoreCorrente() {
		return giocatoreCorrente;
	}

	public Giocatore[] getGiocatori() {
		return giocatori;
	}

	public int getNumeroGiocatori() {
		return numeroGiocatori;
	}

	private void creaInterfaccia() {
		/**** inizio pannello principale ****/
		setOpaque(false);
		setLayout(new BorderLayout());
		Dimension sizePrincipale = new Dimension(1138, 664);
		setMaximumSize(sizePrincipale);
		setMinimumSize(sizePrincipale);
		setPreferredSize(sizePrincipale); 
		
		/**** inizio pannello punteggi ****/
		pannelloPunteggi = new JPanel();
		pannelloPunteggi.setFocusable(false);
		pannelloPunteggi.setOpaque(false);
		pannelloPunteggi.setBorder(BorderFactory.createEmptyBorder());
		
		scrollPanePunteggi = new JScrollPane();
		scrollPanePunteggi.setFocusable(false);
		
		textEditorPunteggi = new JEditorPaneConSfondo(new ImageIcon(getClass().getClassLoader().getResource("img/pannelloLaterale.jpg")).getImage()); 
		textEditorPunteggi.setFocusable(false);
		textEditorPunteggi.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		textEditorPunteggi.setNumeroGiocatori(numeroGiocatori);
		textEditorPunteggi.setEditable(false);
		Dimension sizePunteggi = new Dimension(252,654);
		scrollPanePunteggi.setMaximumSize(sizePunteggi);
		scrollPanePunteggi.setMinimumSize(sizePunteggi);
		scrollPanePunteggi.setPreferredSize(sizePunteggi);
		scrollPanePunteggi.setViewportView(textEditorPunteggi);
		pannelloPunteggi.add(scrollPanePunteggi);
		
		add(pannelloPunteggi, BorderLayout.WEST);
		/**** fine pannello punteggi ****/
		
		/**** inizio pannello centrale ****/
		pannelloCentrale = new JPanel();
		pannelloCentrale.setOpaque(false);
		pannelloCentrale.setLayout(new BorderLayout());
		pannelloCentrale.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		Dimension sizeCentrale = new Dimension(867, 649);
		pannelloCentrale.setPreferredSize(sizeCentrale);
		pannelloCentrale.setMinimumSize(sizeCentrale);
		pannelloCentrale.setMaximumSize(sizeCentrale);
		
		/**** inizio pannello tabellone ****/
		pannelloTabellone = new PannelloTabellone();
		pannelloTabellone.setOpaque(false);
		pannelloTabellone.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		pannelloCentrale.add(pannelloTabellone, BorderLayout.NORTH);
		/**** fine pannello tabellone ****/
		
		/**** inizio pannello console ****/
		pannelloSud = new JPanel();
		pannelloSud.setOpaque(false);
		Dimension sizePannelloConsole = new Dimension(871, 100);
		pannelloSud.setPreferredSize(sizePannelloConsole);
		pannelloSud.setMinimumSize(sizePannelloConsole);
		pannelloSud.setMaximumSize(sizePannelloConsole);
		
		textPaneConsole = new JTextPane();
		textPaneConsole.setFocusable(false);
		textPaneConsole.setEditable(false);
		textPaneConsole.setFont(new Font("RIM", Font.BOLD, 16));
		textPaneConsole.setBackground(new Color(151, 193, 31));
		StyledDocument doc = textPaneConsole.getStyledDocument();
		SimpleAttributeSet cc = new SimpleAttributeSet();
		StyleConstants.setAlignment(cc, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), cc, false);
		
		scrollPaneConsole = new JScrollPane();
		scrollPaneConsole.setOpaque(false);
		scrollPaneConsole.setFocusable(false);
		Dimension sizeConsole = new Dimension(676, 90);
		scrollPaneConsole.setPreferredSize(sizeConsole);
		scrollPaneConsole.setMinimumSize(sizeConsole);
		scrollPaneConsole.setMaximumSize(sizeConsole);
		scrollPaneConsole.setViewportView(textPaneConsole);
		pannelloSud.add(scrollPaneConsole);
		
		/**** inizio pulsanti ****/
		Dimension sizeButton = new Dimension(90, 90);
		
		lanciaDado = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img/buttonDado.jpg")));
		lanciaDado.setPreferredSize(sizeButton);
		lanciaDado.setMinimumSize(sizeButton);
		lanciaDado.setMaximumSize(sizeButton);
		lanciaDado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.play(Player.LANCIADADO);
				GestoreTurni.instance().setDadoLanciato(true);
			}
		});
		pannelloSud.add(lanciaDado);
		
		regolamento = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img/buttonRegolamento.jpg")));
		regolamento.setPreferredSize(sizeButton);
		regolamento.setMinimumSize(sizeButton);
		regolamento.setMaximumSize(sizeButton);
		regolamento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupRegolamento();
			}
		});
		pannelloSud.add(regolamento);
		
		/**** fine pulsanti ****/
		
		pannelloCentrale.add(pannelloSud, BorderLayout.SOUTH);
		/**** fine pannello console ****/
		
		add(pannelloCentrale, BorderLayout.CENTER);
		/**** fine pannello centrale ****/
		/**** fine pannello principale ****/
	}

	private JTextPane textPaneConsole;
	private JPanel pannelloPunteggi;
	private JPanel pannelloCentrale;
	private JPanel pannelloSud;
	private JScrollPane scrollPanePunteggi;
	private JScrollPane scrollPaneConsole;
	private JEditorPaneConSfondo textEditorPunteggi;
	private PannelloTabellone pannelloTabellone;
	private JButton regolamento;
	private JButton lanciaDado;

	public void setDadoAttivo(boolean b) {
		lanciaDado.setEnabled(b);
	}
	
	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}

	public void setSfondo(String image) {
		pannelloTabellone.setImage(image);
	}
}
