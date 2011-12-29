package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

public class SettingsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public MainFrame mainFrame;
	
	public SettingsPanel(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
	}

	private void init() {
		setLayout(new GridLayout(4, 1));
		
		/** inizio: selezione tipo di ambiente */
		JPanel typeSelector = new JPanel(new GridLayout(5,1));
		typeSelector.setBorder(BorderFactory.createTitledBorder("Tipo di ambiente"));
		
		JLabel tipoCorrente = new JLabel("Attuale: " + mainFrame.env.getType());
		tipoCorrente.setHorizontalAlignment(SwingConstants.CENTER);
		typeSelector.add(tipoCorrente);
		
	    JRadioButton A = new JRadioButton("A: statico");
	    JRadioButton B = new JRadioButton("B: dinamico 1");
	    JRadioButton C = new JRadioButton("C: dinamico 2");
	    JRadioButton D = new JRadioButton("D: dinamico 3");
	    A.setSelected(true);
	    
	    ButtonGroup group = new ButtonGroup();
	    group.add(A);
	    group.add(B);
	    group.add(C);
	    group.add(D);

	    A.addActionListener(this);
	    B.addActionListener(this);
	    C.addActionListener(this);
	    D.addActionListener(this);
	    
	    typeSelector.add(A);
	    typeSelector.add(B);
	    typeSelector.add(C);
	    typeSelector.add(D);
	    
	    this.add(typeSelector);
	    /** fine: selezione tipo di ambiente */
	    
	    
	    /** inizio: selezione velocità di simulazione */
	  	JPanel speedSelector = new JPanel(new GridLayout(2, 1));
	  	speedSelector.setBorder(BorderFactory.createTitledBorder("Velocità simulazione"));
	  	
        JLabel sliderLabel = new JLabel("Attuale: " + mainFrame.env.getSpeed(), JLabel.CENTER);
        JSlider speedSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 5, 1);
//        framesPerSecond.addChangeListener(this);

        speedSlider.setSnapToTicks(true);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

        speedSelector.add(sliderLabel);
        speedSelector.add(speedSlider);
        
        add(speedSelector);
	    /** fine: selezione velocità di simulazione */
	    
	    
	    /** inizio: k e maxTime */
        JPanel kTimeSelector = new JPanel(new GridLayout(2, 1));
        kTimeSelector.setBorder(BorderFactory.createTitledBorder("Parametro k e turni disponibili"));
        
        
        add(kTimeSelector);
	    /** fine: k e maxTime */
	    
	    
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
