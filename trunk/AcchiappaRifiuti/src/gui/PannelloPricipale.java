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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

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
		initComponents();
		
		GestoreTurni.newInstance();
		
		String s = JOptionPane.showInputDialog(null, "Quanti giocatori?");

		while (!Pattern.matches("\\d+", s) || Integer.parseInt(s) < 1 || Integer.parseInt(s) > 5)
			s = JOptionPane.showInputDialog(null, "Quanti giocatori? Inserisci un numero tra 1 e 5!");

		numeroGiocatori = Integer.parseInt(s);
		if (numeroGiocatori == 1) 
			setSinglePlayer(true);
		
		giocatori = new Giocatore[numeroGiocatori];
		GestioneCaselle g = new GestioneCaselle();
		g.collega();
		Casella centrale = GestioneCaselle.findCasella("-1");

		for (int i = 0; i < numeroGiocatori; i++) {
			int x = 866/2-15;
			int y = 560/2-15;
			
			String nome = JOptionPane.showInputDialog(null, "Qual e' il nome del giocatore " + (i + 1) + "?");
			
			switch (i) {
			case 0:
				x = x - 30;
				break;
			case 1:
				y = y - 30;
				break;
			case 2:
				break;
			case 3:
				x = x + 30;
				break;
			case 4:
				y = y + 30;
				break;
			}
			if (singlePlayer) {
				x = 866/2-15;
				y = 560/2-15;
			}
			
			ClassLoader cldr = this.getClass().getClassLoader();
			Pedina p = new Pedina(new ImageIcon(cldr.getResource("img/pedine/" + (i + 1) + ".png")).getImage(), x, y);

			giocatori[i] = new Giocatore(nome, centrale, p);
			
			panelloTabellone.addpedina(p);
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
		textAreaConsole.append(s);
		textAreaConsole.setCaretPosition(textAreaConsole.getDocument().getLength());
	}

	public void aggiornaTabellone() {
		panelloTabellone.repaint();
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

	public JTextArea getAreaPrincipale() {
		return textAreaConsole;
	}

	private void initComponents() {
		setOpaque(false);
		pannelloPunteggi = new javax.swing.JPanel();
		pannelloPunteggi.setOpaque(false);
		scrollPanePunteggi = new javax.swing.JScrollPane();
		textEditorPunteggi = new JEditorPaneConSfondo(new ImageIcon(getClass().getClassLoader().getResource("img/pannelloLaterale.jpg")).getImage()); 
		textEditorPunteggi.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
		
		JPanel center = new JPanel();
		center.setOpaque(false);
		JPanel pannelloConsole = new JPanel();
		pannelloConsole.setOpaque(false);
		
		panelloTabellone = new gui.PanelloTabellone();
		panelloTabellone.setOpaque(false);
		scrollPaneConsole = new javax.swing.JScrollPane();
		textAreaConsole = new javax.swing.JTextArea();

		setMaximumSize(new java.awt.Dimension(1150, 680));
		setMinimumSize(new java.awt.Dimension(1150, 680));
		setPreferredSize(new java.awt.Dimension(1150, 680));
		setRequestFocusEnabled(false);

		textEditorPunteggi.setEditable(false);
		textEditorPunteggi.setFont(textEditorPunteggi.getFont().deriveFont(textEditorPunteggi.getFont().getStyle() | java.awt.Font.BOLD, 13));
		Dimension size = new Dimension(252,670);
		scrollPanePunteggi.setMaximumSize(size);
		scrollPanePunteggi.setMinimumSize(size);
		scrollPanePunteggi.setPreferredSize(size);
		scrollPanePunteggi.setViewportView(textEditorPunteggi);
		pannelloPunteggi.add(scrollPanePunteggi);

		scrollPaneConsole.setFocusable(false);
		textAreaConsole.setFocusable(false);
		pannelloPunteggi.setFocusable(false);
		scrollPanePunteggi.setFocusable(false);
		textEditorPunteggi.setFocusable(false);
		
		setLayout(new BorderLayout());
		add(pannelloPunteggi, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		
		center.setLayout(new BorderLayout());
		center.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		center.setPreferredSize(new java.awt.Dimension(866, 659));
		center.setMinimumSize(new java.awt.Dimension(866, 659));
		center.setMaximumSize(new java.awt.Dimension(866, 659));
		center.add(panelloTabellone, BorderLayout.NORTH);
		center.add(pannelloConsole, BorderLayout.SOUTH);
		
		scrollPaneConsole.setPreferredSize(new java.awt.Dimension(770, 90));
		scrollPaneConsole.setMinimumSize(new java.awt.Dimension(770, 90));
		scrollPaneConsole.setMaximumSize(new java.awt.Dimension(770, 90));
		scrollPaneConsole.setViewportView(textAreaConsole);
		pannelloConsole.add(scrollPaneConsole);
		
		regolamento = new JButton(new ImageIcon(getClass().getClassLoader().getResource("img/buttonRegolamento.jpg")));
		regolamento.setPreferredSize(new java.awt.Dimension(88, 88));
		regolamento.setMinimumSize(new java.awt.Dimension(88, 88));
		regolamento.setMaximumSize(new java.awt.Dimension(88, 88));
		pannelloConsole.add(regolamento);
		regolamento.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new PopupRegolamento();
			}
		});
	}

	private javax.swing.JTextArea textAreaConsole;
	private javax.swing.JPanel pannelloPunteggi;
	private javax.swing.JScrollPane scrollPanePunteggi;
	private javax.swing.JScrollPane scrollPaneConsole;
	private JEditorPaneConSfondo textEditorPunteggi;
	private gui.PanelloTabellone panelloTabellone;
	private JButton regolamento;

	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}
}
