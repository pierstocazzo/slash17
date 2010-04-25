package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
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
	JButton firewall;
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
		
		router = new GButton("Router", "Add a router", "data/images/icon/router_icon.png");
		
		server = new GButton("Server", "Add a server", "data/images/icon/server_icon.png");

		firewall = new GButton("Firewall", "Add a firewall", "data/images/icon/firewall_icon.png");
		
		nattedServer = new GButton("NatServer", "Add a natted server", "data/images/icon/nattedserver_icon.png");
		
		pc = new GButton("PC", "Add a pc", "data/images/icon/pc_icon.png");
		
		collisionDomain = new GButton("Domain", "Add a collision domain", "data/images/icon/collisionDomain_icon.png");
		
		area = new GButton("Area", "Create a Network Area", "data/images/icon/area_icon.png");
		
		tap = new GButton("Tap", "Add TAP interface", "data/images/icon/tap_icon.png");
		
		link = new GButton("Link", "Add a link", "data/images/icon/link_icon.png");
		
		delete = new GButton("Delete", "Delete a node", "data/images/icon/delete_icon.png");
		
		verticalToolbar.addSeparator();
		verticalToolbar.add( pc );
		verticalToolbar.add( server );
		verticalToolbar.add( nattedServer );
		verticalToolbar.add( router );
		verticalToolbar.add( firewall );
		verticalToolbar.add( collisionDomain );
		verticalToolbar.add( area );
		verticalToolbar.add( tap );
		verticalToolbar.add( link );
		verticalToolbar.addSeparator();
		verticalToolbar.add( delete );
		
		verticalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
		
		add( verticalToolbar, BorderLayout.WEST );
	}

	private void createToolBar() {
		// creating toolbar
		toolbar = new JToolBar();
		
		newFile = new GButton("New", "Create a new Project", "data/images/icon/new_icon.png");
		
		open = new GButton("Open", "Open a Project", "data/images/icon/open_icon.png");
		
		save = new GButton("Save", "Save the Project", "data/images/icon/save_icon.png");
		
		start = new GButton("Start", "Start the lab", "data/images/icon/start_icon.png");
		
		stop = new GButton("Stop", "Stop the lab", "data/images/icon/stop_icon.png");
		
		toolbar.add( newFile );
		toolbar.addSeparator();
		toolbar.add( open );
		toolbar.addSeparator();
		toolbar.add( save );
		toolbar.addSeparator();
		toolbar.addSeparator();
		toolbar.add( start );
		toolbar.addSeparator();
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
		
		firewall.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.addNode(ItemType.FIREWALL);
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
				// TODO add link
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
				canvas.addNode(ItemType.AREA);
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
