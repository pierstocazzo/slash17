/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * 
 * @author Sal
 */
public class PopupBivio extends JDialog {

	private static final long serialVersionUID = -102513773664586915L;

	ImageIcon img;

	enum Tipo {
		nordest, est, sudest, sudovest, ovest, nordovest, centrale
	};

	Tipo tipo;
	String casella;

	public PopupBivio(String casella) {
		super();
		this.casella = casella;
		setTitle("Dove vuoi andare?");
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModal(true);
		
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if (GestoreTurni.instance().check(e.getKeyCode())) {
					GestoreTurni.instance().setTastoPremuto(e.getKeyCode());
					dispose();
				}
			}
		});
	}
	
	public void display() {
		ClassLoader cldr = this.getClass().getClassLoader();
		if (casella.equals("6")) {
			tipo = Tipo.nordest;
			img = new ImageIcon(cldr.getResource("img/mappe/NE.png"));
		} else if (casella.equals("13")) {
			tipo = Tipo.est;
			img = new ImageIcon(cldr.getResource("img/mappe/E.png"));
		} else if (casella.equals("20")) {
			tipo = Tipo.sudest;
			img = new ImageIcon(cldr.getResource("img/mappe/SE.png"));
		} else if (casella.equals("29")) {
			tipo = Tipo.sudovest;
			img = new ImageIcon(cldr.getResource("img/mappe/SO.png"));
		} else if (casella.equals("36")) {
			tipo = Tipo.ovest;
			img = new ImageIcon(cldr.getResource("img/mappe/O.png"));
		} else if (casella.equals("43")) {
			tipo = Tipo.nordovest;
			img = new ImageIcon(cldr.getResource("img/mappe/NO.png"));
		} else {
			tipo = Tipo.centrale;
			img = new ImageIcon(cldr.getResource("img/mappe/CENTRALE.png"));
		}

		add(new JLabel(img), BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(AcchiappaRifiuti.getFramePrincipale());
		setVisible(true);
	}
}
