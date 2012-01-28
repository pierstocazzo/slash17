package jmovie;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PopupAggiungiFilm extends JDialog {

	private static final long serialVersionUID = -7309546146134920513L;

	JButton buttonAggiungi;
	JPanel inputPanel;	
	
	JTextField title, director, year;
	
	public PopupAggiungiFilm(Frame frame) {
		super(frame);
		
		setTitle("Aggiungi Film");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setModal(true);
		
		buttonAggiungi = new JButton("Aggiungi");
		buttonAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String titolo = title.getText();
				String anno = year.getText();
				
				if (titolo.isEmpty() || titolo.equals("Inserisci il titolo")) {
					title.setText("Inserisci il titolo");
				} else if (!anno.isEmpty() && !anno.matches("(19[0-9][0-9]|20[0-9][0-9])")){
					year.setText("Inserisci l'anno");
				} else {
					setVisible(false);
					dispose();
				}
			}
		});
		add(buttonAggiungi, BorderLayout.SOUTH);
		
		JLabel l = new JLabel("Inserisci i dati del film", SwingConstants.LEFT);
		l.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(l, BorderLayout.NORTH);
		
		JPanel p = new JPanel(new GridBagLayout());
		add(p, BorderLayout.CENTER);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.ipadx = 5;
		c.ipady = 5;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		
		JLabel labelNome = new JLabel("Titolo", SwingConstants.RIGHT);
		title = new JTextField(20);
		c.gridy = 0;
		c.gridx = 0;
		c.gridwidth = 1;
		p.add(labelNome, c);
		c.gridy = 0;
		c.gridx = 1;
		c.gridwidth = 2;
		p.add(title, c);
		
		JLabel labelRegista = new JLabel("Regista", SwingConstants.RIGHT);
		director = new JTextField(20);
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 1;
		p.add(labelRegista,c);
		c.gridy = 1;
		c.gridx = 1;
		c.gridwidth = 2;
		p.add(director,c);
		
		JLabel labelAnno = new JLabel("Anno", SwingConstants.RIGHT);
		year = new JTextField(20);
		c.gridy = 2;
		c.gridx = 0;
		c.gridwidth = 1;
		p.add(labelAnno,c);
		c.gridy = 2;
		c.gridx = 1;
		c.gridwidth = 2;
		p.add(year,c);
		
        pack();
        
        setLocationRelativeTo(frame);
	}
	
	Film display() {
		setVisible(true);
		String titolo = title.getText();
		String regista = director.getText();
		String anno = year.getText();
		if (!titolo.isEmpty() && !titolo.equals("Inserisci il titolo"))
			return new Film(titolo, regista, anno);
		return null;
	}
}
