package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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

		JPanel kPanel = new JPanel();
		kPanel.setBorder(BorderFactory.createTitledBorder("Parametro k"));
		kTimeSelector.add(kPanel);
		JLabel l = new JLabel("K:");
		kPanel.add(l);
		final JSpinner spinnerK = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
		l.setLabelFor(spinnerK);
		kPanel.add(spinnerK);
		spinnerK.setEditor(new JSpinner.NumberEditor(spinnerK, "#"));
		JButton setK = new JButton("set");
		setK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int val = (Integer) spinnerK.getValue();
				env.setK(val);
			}
		});
		kPanel.add(setK);

		JPanel timePanel = new JPanel();
		timePanel.setBorder(BorderFactory.createTitledBorder("Tempo disponibile"));
		kTimeSelector.add(timePanel);
		JLabel l1 = new JLabel("T:");
		timePanel.add(l1);
		final JSpinner spinnerT = new JSpinner(new SpinnerNumberModel(30, 1, 100, 1));
		l1.setLabelFor(spinnerT);
		timePanel.add(spinnerT);
		spinnerT.setEditor(new JSpinner.NumberEditor(spinnerT, "#"));
		JButton setTime = new JButton("set");
		setTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int val = (Integer) spinnerT.getValue();
				env.setMaxTime(val);
			}
		});
		timePanel.add(setTime);

		add(kTimeSelector);
		/** fine: k e maxTime */


		/** inizio: info */
		JPanel infoPanel = new JPanel(new GridLayout(5, 1));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Stato"));

		mealReady = new JLabel("Meal Ready: " + env.isMeal_ready());
		infoPanel.add(mealReady);

		suitUp = new JLabel("Suit Up: " + env.isSuit_up());
		infoPanel.add(suitUp);

		tableReady = new JLabel("Table Ready: " + env.isTable_ready());
		infoPanel.add(tableReady);

		flowerTaken = new JLabel("Flowers Taken: " + env.isFlower_taken());
		infoPanel.add(flowerTaken);

		entrance = new JLabel("Entrance: " + env.isEntrance());
		infoPanel.add(entrance);

		add(infoPanel);
		/** fine: info */

		setVisible(true);
	}

	public void update() {
		/** update pannello stato */
		mealReady.setText("Meal Ready: " + env.isMeal_ready());
		suitUp.setText("Suit Up: " + env.isSuit_up());
		tableReady.setText("Table Ready: " + env.isTable_ready());
		flowerTaken.setText("Flowers Taken: " + env.isFlower_taken());
		entrance.setText("Entrance: " + env.isEntrance());

		/** update tipo di ambiente corrente */
		tipoCorrente.setText("Attuale: " + mainFrame.env.getType());

		/** update velocita' simulazione corrente */
		speedLabel.setText("Attuale: " + mainFrame.env.getSpeed());
	}
}
