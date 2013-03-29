package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import env.Env;

public class MainFrame extends JFrame {

	static final long serialVersionUID = -8026416994513756565L;

	SettingsPanel settingsPanel;
	GridPanel gridPanel;

	Env env;

	boolean pausa = true;

	public MainFrame() {
		env = new Env(5,8);
	}

	public void startGui() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Una Cena per Due");
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				env.setFinished(true);
				super.windowClosing(e);
			}
		});
		
		setLayout(new BorderLayout());

		settingsPanel = new SettingsPanel(this);
		getContentPane().add(settingsPanel, BorderLayout.WEST);

		gridPanel = new GridPanel(this, env);
		getContentPane().add(gridPanel, BorderLayout.EAST);

		pack();

		setVisible(true);
	}
	
	public void mainLoop() {
		while (!env.isFinished()) {
			if (!pausa) {
				env.update();
				settingsPanel.update();
				gridPanel.update();
			}
			try {
				Thread.sleep(1000/env.getSpeed());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public boolean isPausa() {
		return pausa;
	}

	public void setPausa(boolean isPausa) {
		this.pausa = isPausa;
		settingsPanel.enableStart(isPausa);
	}
}
