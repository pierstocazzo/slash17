package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import logica.Giocatore;

public class PopupClassifica extends JDialog {
	
	private static final long serialVersionUID = 4746768348026079586L;
	
	/** classifica[0] = nome giocatore
	 *  classifica[1] = data e ora
	 *  classifica[2] = tempo impiegato
	 */
	ArrayList<String[]> classifica;
	JButton btnOk;
	JPanel inputPanel;	
	String title;
	String html;
	String text;
	String img;
	
	/* 10 minuti */
	int tempoOro = 600;
	/* 15 minuti */
	int tempoArgento = 900;
	/* 20 minuti */
	int tempoBronzo = 1200;
	
	
	public PopupClassifica(Giocatore vincitore, long time) {
		super();
		
		System.out.println(time);
		
		if (time < tempoOro) {
			text = "Complimenti "+vincitore.getNome()+", sei un vero esperto della raccolta differenziata, continua così!";
			img = "img/oro.jpg";
		} else if (time < tempoArgento) {
			text = "Mmm...cominci ad entrare nell'ottica della raccolta differenziata. non male, ma potresti fare meglio!";
			img = "img/argento.jpg";
		} else if (time < tempoBronzo) {
			text = "ok, può capitare a tutti all'inizio di impiegare del tempo per riconoscere la carta dalla plastica... ma quasi venti minuti mi sembra un pò tanto!,";
			img = "img/bronzo.jpg";
		} else {
			text = " ...qualcosa non quadra... ah, ho capito hai dimenticato il pc acceso e sei andato a prendere il caffè! troppo tempo!";
			img = "img/nc.jpg";
		}
		
		this.title = "Raccolta completata";

		setModal(true);
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension dialogSize = new Dimension(400, 500);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL url   = cldr.getResource(img);
		Container content = new PannelloSfondo(new ImageIcon(url).getImage(), dialogSize);
		add(content);
		
		content.setLayout(new BorderLayout());
		
		
		JLabel fancyLabel = new JLabel(html, JLabel.CENTER);
		fancyLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		fancyLabel.setVerticalAlignment(SwingConstants.TOP);
		fancyLabel.setHorizontalAlignment(SwingConstants.LEFT);
		Dimension d = new Dimension(400,465);
        fancyLabel.setPreferredSize(d);
        fancyLabel.setMaximumSize(d);
        fancyLabel.setMinimumSize(d);
		content.add(fancyLabel, BorderLayout.CENTER);
		
		inputPanel = new JPanel();
		Dimension inputSize = new Dimension(400,35);
		inputPanel.setPreferredSize(inputSize);
		inputPanel.setMaximumSize(inputSize);
		inputPanel.setMinimumSize(inputSize);
		inputPanel.add(btnOk);
		content.add(inputPanel, BorderLayout.SOUTH);
		
        pack();
        
		setLocationRelativeTo(AcchiappaRifiuti.instance().getFramePrincipale());
		setVisible(true);
	}
}
