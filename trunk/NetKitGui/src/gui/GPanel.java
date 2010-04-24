package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import common.ItemType;


public class GPanel extends JPanel {
	private static final long serialVersionUID = -8438576029794021570L;

	/** main window */
	GFrame frame;
	
	GCanvas canvas;
	
	/** tool bar */
	JToolBar toolbar;
	JButton newFile;
	JButton open;
	JButton save;
	JButton start;
	JButton stop;
	
	JToolBar verticalToolbar;
	JButton router;
	JButton pc;
	JButton collisionDomain;
	JButton server;
	JButton nattedServer;
	JButton area;
	JButton tap;
	JButton link;
	JButton delete;
	
	JLabel stateLabel;
	JPanel statePanel;
	
	public GPanel( GFrame frame ) {
		this.frame = frame;
		
		setLayout(new BorderLayout());
		
		createToolBar();
		
		createVericalToolBar();
		
		createStateBar();
		
		setupListeners();
		
		canvas = new GCanvas(this);
		add(canvas, BorderLayout.CENTER);
	}
	
	private void createVericalToolBar() {
		// creating toolbar
		verticalToolbar = new JToolBar();
		verticalToolbar.setOrientation(JToolBar.VERTICAL);
		
		router = new GButton("Router", "Add a router", new ImageIcon("data/images/32x32/router.png"));
		
		server = new GButton("Server", "Add a server", new ImageIcon("data/images/32x32/server.png"));
		
		
		nattedServer = new GButton("NatServer", "Add a natted server", new ImageIcon("data/images/32x32/nattedserver.png"));
		
		pc = new GButton("PC", "Add a pc", new ImageIcon("data/images/32x32/system.png"));
		
		collisionDomain = new GButton("Domain", "Add a collision domain", new ImageIcon("data/images/32x32/cloud.png"));
		
		area = new GButton("Area", "Create a Network Area", new ImageIcon("data/images/32x32/cloud.png"));
		
		tap = new GButton("Tap", "Add TAP interface", new ImageIcon("data/images/32x32/tap.png"));
		
		link = new GButton("Link", "Add a link", new ImageIcon("data/images/32x32/rj45_cable.png"));
		
		delete = new GButton("Delete", "Delete a node", new ImageIcon("data/images/32x32/delete.png"));
		
		verticalToolbar.addSeparator();
		verticalToolbar.add( pc );
		verticalToolbar.addSeparator();
		verticalToolbar.add( server );
		verticalToolbar.addSeparator();
		verticalToolbar.add( nattedServer );
		verticalToolbar.addSeparator();
		verticalToolbar.add( router );
		verticalToolbar.addSeparator();
		verticalToolbar.add( collisionDomain );
		verticalToolbar.addSeparator();
		verticalToolbar.add( area );
		verticalToolbar.addSeparator();
		verticalToolbar.add( tap );
		verticalToolbar.addSeparator();
		verticalToolbar.add( link );
		verticalToolbar.addSeparator();
		verticalToolbar.add( delete );
		verticalToolbar.addSeparator();
		
		verticalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
		
		add( verticalToolbar, BorderLayout.WEST );
	}

	private void createToolBar() {
		// creating toolbar
		toolbar = new JToolBar();
		
		newFile = new GButton("New", "Create a new Project", new ImageIcon("data/images/32x32/new.png"), true);
		
		open = new GButton("Open", "Open a Project", new ImageIcon("data/images/32x32/open.png"), true);
		
		save = new GButton("Save", "Save the Project", new ImageIcon("data/images/32x32/floppy.png"), true);
		
		start = new GButton("Start", "Start the lab", new ImageIcon("data/images/32x32/run.png"), true);
		
		stop = new GButton("Stop", "Stop the lab", new ImageIcon("data/images/32x32/exit.png"), true);
		
		toolbar.add( newFile );
		toolbar.add( open );
		toolbar.add( save );
		toolbar.addSeparator();
		toolbar.add( start );
		toolbar.add( stop );
		
		toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		
		add( toolbar, BorderLayout.NORTH );
	}
	
	private void createStateBar() {
		stateLabel = new JLabel("Status: editing", JLabel.TRAILING);
		statePanel = new JPanel(new GridLayout(1, 1));
		
		statePanel.setBorder(BorderFactory.createLoweredBevelBorder());
		statePanel.add(stateLabel);
		
		add( statePanel, BorderLayout.SOUTH );
	}
	
	private void setupListeners() {
		newFile.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("NewProject");
			}
		});
		
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
		
		router.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.ROUTER);
			}
		});
		
		server.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.SERVER);
			}
		});
		
		nattedServer.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.NATTEDSERVER);
			}
		});
		
		pc.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.PC);
			}
		});
		
		collisionDomain.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.COLLISIONDOMAIN);
			}
		});
		
		link.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("add link");
			}
		});
		
		tap.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.TAP);
			}
		});
		
		area.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("create area");
			}
		});
		
		delete.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("delete");
			}
		});
		
		start.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("start lab");
			}
		});
		
		stop.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("stop lab");
			}
		});
	}
}
