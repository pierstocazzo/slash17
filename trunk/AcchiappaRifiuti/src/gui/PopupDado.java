/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import sound.Player;

/**
 * 
 * @author Sal
 */
public class PopupDado extends JDialog {

	private static final long serialVersionUID = -102513773664586915L;

	public PopupDado(String message) {
		super();
		
		setAlwaysOnTop(true);
		setModal(true);
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL url   = cldr.getResource("img/lancioDado.jpg");
		setContentPane(new PannelloSfondo(new ImageIcon(url).getImage()));

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel dialog = new JPanel(new BorderLayout());
		dialog.setBorder(new EmptyBorder(10, 10, 50, 10));
		dialog.setOpaque(false);
		add(dialog);

		JLabel b = new JLabel(message);
		b.setFont(new Font("TimesRoman", Font.BOLD, 18));
		b.setOpaque(false);
		dialog.add(b, BorderLayout.NORTH);

		url   = cldr.getResource("img/dado.gif");
		JButton dado = new JButton(new ImageIcon(url));
		Dimension d = new Dimension(55, 55);
		dado.setPreferredSize(d);
		dado.setMinimumSize(d);
		dado.setMaximumSize(d);
		dado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player.play(Player.LANCIADADO);
				GestoreTurni.instance().setDadoLanciato(true);
				dispose();
			}
		});
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.add(dado);
		dialog.add(p, BorderLayout.CENTER);
	}

	public void display() {
		pack();
		
		setLocationRelativeTo(AcchiappaRifiuti.instance().getFramePrincipale());
		setVisible(true);
	}
}
