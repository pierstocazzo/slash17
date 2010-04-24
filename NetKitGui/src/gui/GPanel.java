package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import tests.GUITest;

public class GPanel extends JPanel {
	private static final long serialVersionUID = -8438576029794021570L;

	/** main window */
	GFrame frame;
	
	JToolBar toolbar;
	JButton newFile;
	JButton open;
	JButton save;
	JButton router;
	JButton pc;
	JButton collisionDomain;
	JButton server;
	JButton nattedServer;
	JButton area;
	JButton tap;
	JButton link;
	JButton delete;
	JButton start;
	JButton stop;
	
	JLabel stateLabel;
	JPanel statePanel;
	
	public GPanel( GFrame frame ) {
		this.frame = frame;
		
		setLayout(new BorderLayout());
		
		createToolBar();
		
		createStateBar();
		
		setupListeners();
		
		add(new GUITest(), BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private void createToolBar() {
		// creating toolbar
		toolbar = new JToolBar();
		newFile = new JButton("New", new ImageIcon("data/images/32x32/new.png"));
		newFile.setToolTipText("Create a new project");
		open = new JButton("Open", new ImageIcon("data/images/32x32/open.png"));
		save = new JButton("Save", new ImageIcon("data/images/32x32/floppy.png"));
		router = new JButton("Router");
		server = new JButton("Server");
		nattedServer = new JButton("NattedServer");
		pc = new JButton("PC");
		collisionDomain = new JButton("CollisionDomain");
		area = new JButton("Area");
		tap = new JButton("Tap");
		link = new JButton("Link");
		start = new JButton("Start");
		stop = new JButton("Stop");
		delete = new JButton("Delete");
		
		toolbar.setFloatable(false);
		toolbar.add( newFile );
		toolbar.add( open );
		toolbar.add( save );
		toolbar.addSeparator();
		toolbar.add( pc );
		toolbar.add( server );
		toolbar.add( nattedServer );
		toolbar.add( router );
		toolbar.add( collisionDomain );
		toolbar.add( area );
		toolbar.add( tap );
		toolbar.add( link );
		toolbar.addSeparator();
		toolbar.add( delete );
		toolbar.addSeparator();
		toolbar.add( start );
		toolbar.add( stop );
		
		toolbar.setBorder(BorderFactory.createEtchedBorder());
		
		add( toolbar, BorderLayout.NORTH );
	}
	
	private void createStateBar() {
		// creating state bar TODO change status
		stateLabel = new JLabel("Status: editing", JLabel.TRAILING);
		statePanel = new JPanel(new GridLayout(1, 1));
		
		statePanel.setBorder(BorderFactory.createLoweredBevelBorder());
		statePanel.add(stateLabel);
		
		add( statePanel, BorderLayout.SOUTH );
	}
	
	void setupListeners() {
		open.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Open");
				
			}
		});
		
		save.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Save");
			}
		});
	}
}
