package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import env.Env;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8026416994513756565L;

	private SettingsPanel settingsPanel;
	private GridPanel gridPanel;

	Env env;

	public MainFrame() {
		env = new Env(5,8);
	}

	public void startGui() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Una Cena per Due");
		setResizable(false);

		setLayout(new BorderLayout());

		settingsPanel = new SettingsPanel(this);
		getContentPane().add(settingsPanel, BorderLayout.WEST);

		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel, BorderLayout.EAST);

		pack();

		setVisible(true);
	}
}
