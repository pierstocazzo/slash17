package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import env.Env;

public class SettingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	MainFrame mainFrame;
	Env env;

	private JLabel mealReady;

	private JLabel suitUp;

	private JLabel tableReady;

	private JLabel flowerTaken;

	private JLabel entrance;

	private JLabel tipoCorrente;

	private JLabel speedLabel;
	
	private JPanel kPanel;
	
	JButton start, pause;
	
	ImageIcon trueIcon = new ImageIcon("img/true.png");
	ImageIcon falseIcon = new ImageIcon("img/false.png");

	public SettingsPanel(final MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		env = mainFrame.env;
		init();
	}

	private void init() {
		setLayout(new GridLayout(4, 1));

		/** inizio: selezione tipo di ambiente */
		JPanel typeSelector = new JPanel(new GridLayout(5,1));
		typeSelector.setBorder(BorderFactory.createTitledBorder("Tipo di ambiente"));

		tipoCorrente = new JLabel("Attuale: " + mainFrame.env.getType());
		tipoCorrente.setHorizontalAlignment(SwingConstants.CENTER);
		typeSelector.add(tipoCorrente);

		JRadioButton A = new JRadioButton("A: Statico");
		JRadioButton B = new JRadioButton("B: Dyn. Un oggetto nella sua stanza");
		JRadioButton C = new JRadioButton("C: Dyn. Molti oggetti nelle proprie stanze");
		JRadioButton D = new JRadioButton("D: Dyn. Tutti si muovono verso tutto");
		A.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(A);
		group.add(B);
		group.add(C);
		group.add(D);

		A.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				env.setType('A');
				update();
			}
		});
		B.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				env.setType('B');
				update();
			}
		});
		C.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				env.setType('C');
				update();
			}
		});
		D.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				env.setType('D');
				update();
			}
		});

		typeSelector.add(A);
		typeSelector.add(B);
		typeSelector.add(C);
		typeSelector.add(D);

		this.add(typeSelector);
		/** fine: selezione tipo di ambiente */


		/** inizio: selezione velocita' di simulazione */
		JPanel speedSelector = new JPanel(new GridLayout(2, 1));
		speedSelector.setBorder(BorderFactory.createTitledBorder("Velocita' simulazione"));

		speedLabel = new JLabel("Attuale: " + mainFrame.env.getSpeed(), JLabel.CENTER);
		final JSlider speedSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 5, 1);
		speedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int speed = speedSlider.getValue();
				env.setSpeed(speed);
				update();
			}
		});

		speedSlider.setSnapToTicks(true);
		speedSlider.setMajorTickSpacing(1);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

		speedSelector.add(speedLabel);
		speedSelector.add(speedSlider);

		add(speedSelector);
		/** fine: selezione velocita' di simulazione */


		/** inizio: k e maxTime */
		JPanel kTimeSelector = new JPanel(new GridLayout(2, 1));

		kPanel = new JPanel();
		kPanel.setBorder(BorderFactory.createTitledBorder("Parametro k"));
		kTimeSelector.add(kPanel);
		JLabel labelK = new JLabel("K:");
		kPanel.add(labelK);
		
		final JSpinner spinnerK = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		int val = 1;
		spinnerK.setValue(val);
		env.setK(val);
		labelK.setLabelFor(spinnerK);
		spinnerK.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int val = (Integer) spinnerK.getValue();
				System.out.println("Set K " +  val);
				env.setK(val);
			}
		});
		kPanel.add(spinnerK);
		spinnerK.setEditor(new JSpinner.NumberEditor(spinnerK, "#"));

		JPanel timePanel = new JPanel();
		timePanel.setBorder(BorderFactory.createTitledBorder("Tempo disponibile"));
		kTimeSelector.add(timePanel);
		
		JLabel labelT = new JLabel("T:");
		timePanel.add(labelT);
		final JSpinner spinnerT = new JSpinner(new SpinnerNumberModel(30, 1, 100, 1));
		labelT.setLabelFor(spinnerT);
		timePanel.add(spinnerT);
		spinnerT.setEditor(new JSpinner.NumberEditor(spinnerT, "#"));
		spinnerT.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int val = (Integer) spinnerT.getValue();
				env.setMaxTime(val);
				System.out.println("Set T " + val);
			}
		});

		add(kTimeSelector);
		/** fine: k e maxTime */


		/** inizio: info */
		JPanel infoNstart = new JPanel(new BorderLayout());
		JPanel infoPanel = new JPanel(new GridLayout(5, 1));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Stato"));

		mealReady = new JLabel("Meal Ready: ", falseIcon, SwingConstants.CENTER);
		mealReady.setHorizontalTextPosition(SwingConstants.LEFT);
		infoPanel.add(mealReady);

		suitUp = new JLabel("Suit Up: ", falseIcon, SwingConstants.CENTER);
		suitUp.setHorizontalTextPosition(SwingConstants.LEFT);
		infoPanel.add(suitUp);

		tableReady = new JLabel("Table Ready: ", falseIcon, SwingConstants.CENTER);
		tableReady.setHorizontalTextPosition(SwingConstants.LEFT);
		infoPanel.add(tableReady);

		flowerTaken = new JLabel("Flowers Taken: ", falseIcon, SwingConstants.CENTER);
		flowerTaken.setHorizontalTextPosition(SwingConstants.LEFT);
		infoPanel.add(flowerTaken);

		entrance = new JLabel("Entrance: ", falseIcon, SwingConstants.CENTER);
		entrance.setHorizontalTextPosition(SwingConstants.LEFT);
		infoPanel.add(entrance);

		infoNstart.add(infoPanel, BorderLayout.CENTER);
		
		
		JPanel p = new JPanel();
		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setPausa(false);
			}
		});
		pause = new JButton("Pausa");
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.setPausa(true);
			}
		});
		p.add(start);
		p.add(pause);
		infoNstart.add(p, BorderLayout.SOUTH);
		
		add(infoNstart);
		/** fine: info */

		setVisible(true);
	}

	public void update() {
		/** update pannello stato */
		mealReady.setIcon(env.isMealReady() ? trueIcon : falseIcon);
		suitUp.setIcon(env.isSuitUp() ? trueIcon : falseIcon);
		tableReady.setIcon(env.isTableReady() ? trueIcon : falseIcon);
		flowerTaken.setIcon(env.isFlowerTaken() ? trueIcon : falseIcon);
		entrance.setIcon(env.isWaitRenata() ? trueIcon : falseIcon);

		/** update tipo di ambiente corrente */
		tipoCorrente.setText("Attuale: " + mainFrame.env.getType());

		/** update velocita' simulazione corrente */
		speedLabel.setText("Attuale: " + mainFrame.env.getSpeed());
	}

	public void enableStart(boolean b) {
		start.setEnabled(b);
	}
}
