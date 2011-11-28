package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PopupDado extends JDialog {

	private static final long serialVersionUID = 5824048206691380401L;
	ImageIcon icon;
	
	public PopupDado(String giocatore, String icon) {
		super((Frame) AcchiappaRifiuti.instance().getFramePrincipale(), true);
		
		setContentPane(new PannelloSfondo(new ImageIcon(getClass().getClassLoader().getResource("img/lancioDado.png")).getImage()));
		getContentPane().setLayout(new BorderLayout());
		
		JLabel fancyLabel = new JLabel(
				"<html><body>" +
				"<font size=3 color=white><p align=center>"+giocatore+" muovi la pedina!</p></font>" +
				"</body></html>", JLabel.CENTER);
		
		fancyLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource(icon))));
		fancyLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		fancyLabel.setVerticalAlignment(SwingConstants.CENTER);
		fancyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fancyLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		fancyLabel.setIconTextGap(20);
		fancyLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		fancyLabel.setBackground(Color.WHITE);
		
		setTitle("Lancio del dado!");
		add(fancyLabel, BorderLayout.NORTH);
		
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		Dimension size = new Dimension(250, 200);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setResizable(false);
		
		setLocationRelativeTo(AcchiappaRifiuti.instance().getPannello());
		add(ok, BorderLayout.SOUTH);
		setVisible(true);
	}
}
