package project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import project.common.ItemType;
import project.netkit.Shell;

public class GFrame extends JFrame {
	private static final long serialVersionUID = 3661490807594270819L;

	/** Menus and menus items */
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem saveAsItem;
	JMenuItem exitItem;
	JMenu projectMenu;
	JMenuItem addRouterItem;
	JMenuItem addCollisionDomainItem;
	JMenuItem addLinkItem;
	JMenuItem addPcItem;
	JMenuItem addServerItem;
	JMenuItem addNattedServerItem;
	JMenuItem addFirewallItem;
	JMenuItem addAreaItem;
	JMenu helpMenu;
	JMenuItem infoItem;
	
	/** tool bars and buttons */
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
	
	/** state bar's components */
	JLabel stateLabel;
	JPanel statePanel;

	private GCanvas canvas;
	
	public GFrame() {
		super("NetKit GUI");
		
		createFrame();
	}
	
	private void createFrame() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			} catch (Exception e1) {}
		} 
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		setSize( screenSize );

		createMenuBar();
		
		createToolBar();
		
		createVericalToolBar();
		
		createStateBar();
		
		setupListeners();
		
		setVisible(true);
	}

	private void createMenuBar() {
		// creating the menu bar
		menuBar = new JMenuBar();
		
		// creating the file menu
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("SaveAs");
		exitItem = new JMenuItem("Exit");
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		// creating the project menu
		projectMenu = new JMenu("Project");
		addRouterItem = new JMenuItem("add router");
		addCollisionDomainItem = new JMenuItem("add collision domain");
		addLinkItem = new JMenuItem("add link");
		addPcItem = new JMenuItem("add pc");
		addServerItem = new JMenuItem("add server");
		addNattedServerItem = new JMenuItem("add natted server");
		addFirewallItem = new JMenuItem("add firewall");
		addAreaItem = new JMenuItem("add area");
		
		projectMenu.add(addRouterItem);
		projectMenu.add(addCollisionDomainItem);
		projectMenu.add(addLinkItem);
		projectMenu.add(addPcItem);
		projectMenu.add(addServerItem);
		projectMenu.add(addNattedServerItem);
		projectMenu.add(addFirewallItem);
		projectMenu.add(addAreaItem);
		menuBar.add(projectMenu);
		
		// creating help menu
		helpMenu = new JMenu("Help");
		infoItem = new JMenuItem("Info");
		
		helpMenu.add(infoItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void createVericalToolBar() {
		// creating toolbar
		verticalToolbar = new JToolBar();
		verticalToolbar.setOrientation(JToolBar.VERTICAL);
		
		router = new GButton("Router", "Add a router", "data/images/32x32/router_icon.png");
		server = new GButton("Server", "Add a server", "data/images/32x32/server_icon.png");
		firewall = new GButton("Firewall", "Add a firewall", "data/images/32x32/firewall_icon.png");
		nattedServer = new GButton("NatServer", "Add a natted server", "data/images/32x32/nattedserver_icon.png");
		pc = new GButton("PC", "Add a pc", "data/images/32x32/pc_icon.png");
		collisionDomain = new GButton("Domain", "Add a collision domain", "data/images/32x32/collisionDomain_icon.png");
		area = new GButton("Area", "Create a Network Area", "data/images/32x32/area_icon.png");
		tap = new GButton("Tap", "Add TAP interface", "data/images/32x32/tap_icon.png");
		link = new GButton("Link", "Add a link", "data/images/32x32/link_icon.png");
		delete = new GButton("Delete", "Delete a node", "data/images/32x32/delete_icon.png");
		
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
		
		newFile = new GButton("New", "Create a new Project", "data/images/32x32/new_icon.png");
		open = new GButton("Open", "Open a Project", "data/images/32x32/open_icon.png");
		save = new GButton("Save", "Save the Project", "data/images/32x32/save_icon.png");
		start = new GButton("Start", "Start the lab", "data/images/32x32/start_icon.png");
		stop = new GButton("Stop", "Stop the lab", "data/images/32x32/stop_icon.png");
		
		toolbar.add( newFile );
		toolbar.add( open );
		toolbar.add( save );
		toolbar.addSeparator();
		toolbar.add( start );
		toolbar.add( stop );
		toolbar.setFloatable(false);
		
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
	
	public void setCanvas( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	private void setupListeners() {
		/** set windows clising event */
		addWindowListener( new WindowAdapter() {
			@Override 
			public void windowClosing( WindowEvent e ) {
				closeApplication();
			}
		});
		
		/********************************
		 * Menu items listeners
		 ********************************/
		
		exitItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				closeApplication();
			}
		});
		
		infoItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String credits = "<html><center><b><font size=6>NetKit GUI</font></b></center><br>" +
						"Released under GNU General Public License version 3. <a href=http://www.gnu.org/licenses/gpl-3.0-standalone.html>GPLv3</a><br><br>" +
						"Copyright © 2010 <i>Loria Salvatore</i><br>" +
						"Visit <a href=http://slash17.googlecode.com>http://slash17.googlecode.com</a></html>";
				
				JEditorPane editorPane = new JEditorPane ("text/html", credits);
				editorPane.setEditable (false);

				editorPane.addHyperlinkListener (new HyperlinkListener () {
					public void hyperlinkUpdate (HyperlinkEvent evt) {
						if (evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
							try {
								Desktop.getDesktop().browse(new URI(evt.getDescription()));
							} catch (Exception e) {
							}
						}
					}
				});

				JOptionPane.showMessageDialog(null, editorPane, "Info", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		// TODO menu items listeners
		
		/********************************
		 *  Buttons listeners
		 ********************************/
		
		newFile.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("NewProject");
//				canvas.workspace.newProject( null );
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
//				canvas.saveProject();
			}
		});
		
		router.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.ROUTER);
			}
		});
		
		firewall.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.FIREWALL);
			}
		});
		
		server.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.SERVER);
			}
		});
		
		nattedServer.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.NATTEDSERVER);
			}
		});
		
		pc.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.PC);
			}
		});
		
		collisionDomain.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.COLLISIONDOMAIN);
			}
		});
		
		link.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.LINK);
			}
		});
		
		tap.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.TAP);
			}
		});
		
		area.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.adding(ItemType.AREA);
			}
		});
		
		delete.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				canvas.deleting();
			}
		});
		
		start.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("starting lab");
				Shell.startLab( canvas.project.getDirectory() );
			}
		});
		
		stop.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("stopping lab");
				Shell.stopLab( canvas.project.getDirectory(), false );
			}
		});
	}
	
	public void closeApplication() {
		this.dispose();
	}
}
