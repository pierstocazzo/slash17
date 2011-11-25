package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class PopupInformazione extends JDialog {

	private static final long serialVersionUID = -7309546146134920513L;

	JButton btnOk;
	JPanel inputPanel;	
	String title;
	String text;
	String img;
	
	public PopupInformazione(String testoPiccolo, String testoGrande, String title, final String sfondo) {
		super();
		this.text = 
	        "<html>" +
			"<body>" +
			"<p align=\"center\"><font size=\"3\" color=\"white\">" + testoPiccolo + "</font></p>" +
			"<p align=\"center\"><font size=\"6\" color=\"white\">" + testoGrande + "</font></p>" +
			"</p>" +
			"</body>" +
			"</html>";
		this.img = sfondo;
		this.title = title;

		setModal(true);
//		setAlwaysOnTop(true);
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension dialogSize = new Dimension(300, 472);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL url   = cldr.getResource(img);
		Container content = new PannelloSfondo(new ImageIcon(url).getImage(), dialogSize);
		add(content);
		
		content.setLayout(new BorderLayout());
		
		JLabel empty = new JLabel();
		Dimension emptySize = new Dimension(300,130);
        empty.setPreferredSize(emptySize);
        empty.setMaximumSize(emptySize);
        empty.setMinimumSize(emptySize);
        content.add(empty, BorderLayout.NORTH);
		
		JLabel fancyLabel = new JLabel(text, JLabel.CENTER);
		fancyLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		fancyLabel.setVerticalAlignment(SwingConstants.CENTER);
		fancyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		Dimension d = new Dimension(250,214);
        fancyLabel.setPreferredSize(d);
        fancyLabel.setMaximumSize(d);
        fancyLabel.setMinimumSize(d);
		content.add(fancyLabel, BorderLayout.CENTER);
		
		inputPanel = new JPanel();
		Dimension inputSize = new Dimension(300,35);
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
