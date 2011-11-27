package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class PopupVittoria extends JDialog {

	private static final long serialVersionUID = -7309546146134920513L;

	JButton btnOk;
	JPanel pane;
	
	String text;
	Image sfondo;
	
	/* 10 minuti */
	int tempoOro = 600;
	/* 15 minuti */
	int tempoArgento = 900;
	/* 20 minuti */
	int tempoBronzo = 1200;
	
	public PopupVittoria(String giocatore, long time) {
		super((Frame) AcchiappaRifiuti.instance().getFramePrincipale());
		
		int minuti = (int) time / 60;
		int secondi = (int) time % 60;
		
		String img;
		if (time == -1) {
			img = "img/vittoria.jpg";
		} else if (time < tempoOro) {
			img = "img/oro.jpg";
		} else if (time < tempoArgento) {
			img = "img/argento.jpg";
		} else if (time < tempoBronzo) {
			img = "img/bronzo.jpg";
		} else {
			img = "img/nc.jpg";
		}
		
		this.text = 
	        "<html>" +
			"<body>" +
			"<p align=\"center\">" +
			(time != -1 ? 
					"<font size=\"4\" color=\"white\">" +
					giocatore + ", hai vinto in "+minuti + "min e " + secondi + "s!" 
					: 
					"<font size=\"5\" color=\"white\">" +
					"<p align=\"center\">" +
					 "Complimenti " + giocatore + " hai VINTO!") +
			"</font>" +
			"</p>" +
			"</body>" +
			"</html>";

		setTitle(giocatore + " hai VINTO!");
		setModal(true);
		setResizable(false);
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL url   = cldr.getResource(img);
		sfondo = new ImageIcon(url).getImage();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(new PannelloSfondo(sfondo));
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		JLabel empty = new JLabel();
		Dimension d;
		if (time == -1) 
			d = new Dimension(sfondo.getWidth(null)+15, 15);
		else 
			d = new Dimension(sfondo.getWidth(null)+15, -15);
		empty.setPreferredSize(d);
		empty.setSize(d);
		empty.setMinimumSize(d);
		empty.setMaximumSize(d);
		content.add(empty, BorderLayout.NORTH);
		
		JLabel fancyLabel = new JLabel(text, JLabel.CENTER);
		fancyLabel.setVerticalAlignment(SwingConstants.CENTER);
		fancyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		content.add(fancyLabel, BorderLayout.CENTER);
		
		pane = new JPanel();
		add(pane, BorderLayout.SOUTH);

		pane.add(btnOk);
		Dimension d1 = new Dimension(sfondo.getWidth(null)+15, sfondo.getHeight(null)+70);
		setPreferredSize(d1);
		setSize(d1);
		setMinimumSize(d1);
		setMaximumSize(d1);
		
		setLocationRelativeTo(AcchiappaRifiuti.instance().getPannello());
		setVisible(true);
	}
}
