package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
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
	Image img;
	
	public static void main(String[] args) {
		new PopupVittoria("Antonio");
	}
	
	public PopupVittoria(String giocatore) {
		super();
		this.text = 
	        "<html>" +
			"<body>" +
			"<font size=\"5\" color=\"white\">" +
			"<p align=\"center\">Complimenti " + giocatore + " hai VINTO!</p>" +
			"</font>" +
			"</body>" +
			"</html>";

		setTitle(giocatore + " hai VINTO!");
		setModal(true);
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL url   = cldr.getResource("img/vittoria.jpg");
		img = new ImageIcon(url).getImage();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setContentPane(new PannelloSfondo(img));
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		JLabel empty = new JLabel();
		Dimension d = new Dimension(img.getWidth(null)+15, 15);
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
		Dimension d1 = new Dimension(img.getWidth(null)+15, img.getHeight(null)+70);
		setPreferredSize(d1);
		setSize(d1);
		setMinimumSize(d1);
		setMaximumSize(d1);
		
		setLocationRelativeTo(AcchiappaRifiuti.getFramePrincipale());
		setVisible(true);
	}
}
