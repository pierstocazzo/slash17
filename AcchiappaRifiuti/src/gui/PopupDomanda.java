package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.*;

import logica.Domanda;
import logica.Risposta;

public class PopupDomanda extends JDialog {
	private static final long serialVersionUID = -8988072219139810648L;

	ButtonGroup group;
	JButton btnOk;
	JPanel inputPanel;
	Domanda dom;
	
	String title;
	String text;
	String img;
	
	JPanel p;
	
	public static void main(String[] args) {
		Domanda d = new Domanda("Come ti chiami?");
		d.setA(new Risposta("non lo so", 1, false));
		d.setB(new Risposta("panunziopanunzi opanunziopan unziopanunziop anunziopanunzio", 2, false));
		d.setC(new Risposta("Caio", 3, true));
		d.setD(new Risposta("ohoho", 4, false));
		d.setCorretta(new Risposta("Caio", 3, true));
		PopupDomanda p = new PopupDomanda(null, d, "img/sfondoDOMANDE.jpg");
		p.rispostaCorretta();
	}
	
	public boolean rispostaCorretta() {
		display();
		boolean b = false;
		if (group.getSelection() != null && group.getSelection().getActionCommand().equals(dom.getCorretta().getRisposta())) {
			b = true;
		}
		dispose();
		return b;
	}
	
	public void setDomanda(Domanda d) {
		this.dom = d;
	}
	
	public PopupDomanda(JPanel p, Domanda dom, final String sfondo) {
		super();
		text =  
	        "<html>" +
			"<body>" +
			"<font color=\"white\">" +
			"<p>" +
			dom.getQuesito() +
			"	<ol type=\"A\" style=\"margin-left:15px;\">" +
			"		<li>" + dom.getA().getRisposta() + "</li>" +
			"		<li>" + dom.getB().getRisposta() + "</li>" +
			"		<li>" + dom.getC().getRisposta() + "</li>" +
			(dom.getD() != null && !dom.getD().getRisposta().equals("null") ? 
			"		<li>" + dom.getD().getRisposta() + "</li>" : "") +
			"	</ol>" +	
			"</p>" +
			"</font>" +
			"</body>" +
			"</html>";
	   
		img = sfondo;
		title = "Domanda";
		this.p = p;

		setDomanda(dom);
		setModal(true);
		setAlwaysOnTop(true);
		group = new ButtonGroup();
		btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
	private void display() {
		getContentPane().removeAll();
		for (Enumeration<?> buttonGroup = group.getElements(); buttonGroup.hasMoreElements();) {
			group.remove((AbstractButton) buttonGroup.nextElement());
		}
		
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
		fancyLabel.setVerticalAlignment(SwingConstants.TOP);
		fancyLabel.setHorizontalAlignment(SwingConstants.LEFT);
		Dimension labelSize = new Dimension(300,307);
        fancyLabel.setPreferredSize(labelSize);
        fancyLabel.setMaximumSize(labelSize);
        fancyLabel.setMinimumSize(labelSize);
		content.add(fancyLabel, BorderLayout.CENTER);
		
		inputPanel = new JPanel();
		Dimension inputSize = new Dimension(300,35);
		inputPanel.setPreferredSize(inputSize);
		inputPanel.setMaximumSize(inputSize);
		inputPanel.setMinimumSize(inputSize);

		JRadioButton radioA = new JRadioButton("A");
		radioA.setActionCommand(dom.getA().getRisposta());
		group.add(radioA);
		inputPanel.add(radioA);

		JRadioButton radioB = new JRadioButton("B");
		radioB.setActionCommand(dom.getB().getRisposta());
		group.add(radioB);
		inputPanel.add(radioB);

		JRadioButton radioC = new JRadioButton("C");
		radioC.setActionCommand(dom.getC().getRisposta());
		group.add(radioC);
		inputPanel.add(radioC);

		if (dom.getD() != null && !dom.getD().getRisposta().equals("null")) {
			JRadioButton radioD = new JRadioButton("D");
			radioD.setActionCommand(dom.getD().getRisposta());
			group.add(radioD);
			inputPanel.add(radioD);
		}
		inputPanel.add(btnOk);
		content.add(inputPanel, BorderLayout.SOUTH);
		
        pack();
        
		setLocationRelativeTo(AcchiappaRifiuti.instance().getFramePrincipale());
		setVisible(true);
	}
}
