package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PopupRegolamento extends JDialog {
	private static final long serialVersionUID = -7309546146134920513L;

	JButton btnOk;
	JPanel inputPanel;	
	String title;
	String img;
	
	public PopupRegolamento() {
		super();
		this.img = "img/Regolamento.jpg";
		this.title = "Regole del gioco";

		setModal(true);
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AcchiappaRifiuti.getPannello().requestFocusInWindow();
				AcchiappaRifiuti.getPannello().requestFocus();
				dispose();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				AcchiappaRifiuti.getPannello().requestFocusInWindow();
				AcchiappaRifiuti.getPannello().requestFocus();
				super.windowClosed(e);
			}
		});
		
		setTitle(title);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		ClassLoader cldr = this.getClass().getClassLoader();
		java.net.URL imageURL   = cldr.getResource(img);
		JLabel label = new JLabel(new ImageIcon(imageURL));
        add(label, BorderLayout.NORTH);
		
		inputPanel = new JPanel();
		Dimension inputSize = new Dimension(300,35);
		inputPanel.setPreferredSize(inputSize);
		inputPanel.setMaximumSize(inputSize);
		inputPanel.setMinimumSize(inputSize);
		inputPanel.add(btnOk);
		add(inputPanel, BorderLayout.SOUTH);
		
        pack();
        
		setLocationRelativeTo(AcchiappaRifiuti.getFramePrincipale());
		setVisible(true);
	}
}
