package interfaces;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

import vacuumCleaner.AbstractAgent;
import vacuumCleaner.Agent;
import vacuumCleaner.Environment;
import vacuumCleaner.Floor;
import vacuumCleaner.Square;
import vacuumCleaner.AbstractAgent.VisibilityType;
/**
 * Graphic interface, allow to create the environment configuration 
 * and display the sequence of actions performed by the agent
 *
 */
public class MainJFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar jMenuBar;
	private JMenu menuFile;
	private JMenuItem closeMenuItem;
	private JMenuItem	openMenuItem;
	
	public GridPanel gridPanel;
	private SettingsPanel settingsPanel;
	
	int size = 6;
	
	public Environment env;
	AbstractAgent agent;
	protected boolean stopped = true;
	private JMenuItem	saveMenuItem;
	
	public static void main(String[] args) {
		new MainJFrame();
	}
	
	public MainJFrame() {
		super();
		initGUI();
	}
	
	/**
	 * Generate a default configuration 
	 * 		Set the display Layout and menu component.
	 * 		Create a default configuration, set the agent on position 0,0 
	 * 		and the environment as static.
	 */
	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
//		setResizable(false);
		{
			jMenuBar = new JMenuBar();
			setJMenuBar(jMenuBar);
			{
				menuFile = new JMenu();
				jMenuBar.add(menuFile);
				menuFile.setText("File");
				{
					openMenuItem = new JMenuItem();
					openMenuItem.setText("Open");
					openMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Serializzatore<Floor> serializzatore = new Serializzatore<Floor>();
							Floor floor = serializzatore.carica();
							if (floor != null){
								env.floor.load(floor);
								remove(gridPanel);
								gridPanel = new GridPanel(env);
								getContentPane().add(gridPanel, BorderLayout.EAST);
								pack();
							}
							serializzatore.dispose();
						}
					});
					saveMenuItem = new JMenuItem();
					saveMenuItem.setText("Save");
					saveMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Serializzatore<Floor> serializzatore = new Serializzatore<Floor>();
							serializzatore.salva(env.floor);
							serializzatore.dispose();
						}
					});
					closeMenuItem = new JMenuItem();
					closeMenuItem.setText("Close");
					closeMenuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					});
					menuFile.add(openMenuItem);
					menuFile.add(saveMenuItem);
					menuFile.addSeparator();
					menuFile.add(closeMenuItem);
				}
			}
		}
		
		agent = new Agent(0, 0, Agent.VisibilityType.MY_NEIGHBOURS, 200);
		env = new Environment(size, size, agent, Environment.Type.STATIC);
		
		settingsPanel = new SettingsPanel(this);
		getContentPane().add(settingsPanel, BorderLayout.WEST);
		
		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel, BorderLayout.EAST);
		
		pack();
		this.setVisible(true);
	}
    /**
     * Execute a loop until the goal is reached and 
     * the max number of step (opBound) is not reached
     */
	public void mainLoop(){
		env.show();
		agent.actionList.clear();
		System.out.println("goal reached " + agent.goalReached());
		System.out.println("stopped " + stopped);
		System.out.println("energy " + agent.energy);
		while(!agent.goalReached() && !stopped && agent.energy>0){
			agent.energy--;
			env.update();
			env.show();
			gridPanel.update();
			settingsPanel.update();
			System.out.println("-------------------");
		}
		stopped = true;
		settingsPanel.update();
		System.out.println("Num actions: " + agent.actionList.size());
		agent.showActions();
		System.out.println("Performance: " + env.performanceMeasure() );
		System.out.println("-- End --");
	}
	
	/**
     * Execute only a loop
     */
	public void mainLoopOnes(){
		env.show();
		if(!agent.goalReached()){
			env.update();
			env.show();
			gridPanel.update();
			settingsPanel.update();
			System.out.println("-------------------");
		}
		System.out.println("Num actions: " + agent.actionList.size());
		agent.showActions();
		System.out.println("Performance: " + env.performanceMeasure() );
		System.out.println("-- End --");
	}
	/**
	 * Create a new configuration according of environment
	 */
	public void newConfig(int newSize, Environment.Type envType, VisibilityType visType, int energy) {
		System.out.println("Received size: " + newSize);
		agent = new Agent(0, 0, visType, energy);
		env.floor = new Floor(newSize, newSize, Square.Type.CLEAN);
//		env.floor.generateObject(dirt,obstacles);
//		env.floor.initialDirt = dirt;
		env.floor.set(agent.x, agent.y, Square.Type.CLEAN);
		env.agent = agent;
		env.type = envType;
		getContentPane().remove(gridPanel);
		gridPanel = new GridPanel(env);
		getContentPane().add(gridPanel, BorderLayout.EAST);
		gridPanel.update();
		pack();
	}
}
