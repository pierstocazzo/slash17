package project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	JMenuItem newItem;
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

	/** the canvas */
	GCanvas canvas;
	
	/**
	 * Create the NetKit GUI main Frame
	 */
	public GFrame() {
		super("NetKit GUI");
		
		createFrame();
	}
	
	private void createFrame() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			} catch (Exception e1) {}
		} 
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		setSize( screenSize );
		setSize( 1280, 800 );

		createMenuBar();
		createToolBar();
		createVericalToolBar();
		createStateBar();
		setupListeners();
		
		setVisible(true);
	}

	/** Create the menu bar and add it to the frame
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		
		// creating the file menu
		fileMenu = new JMenu("File");
		newItem = new JMenuItem("New Project", new ImageIcon("data/images/16x16/new_icon.png"));
		openItem = new JMenuItem("Open", new ImageIcon("data/images/16x16/open_icon.png"));
		saveItem = new JMenuItem("Save", new ImageIcon("data/images/16x16/save_icon.png"));
		saveAsItem = new JMenuItem("Save as", new ImageIcon("data/images/16x16/save_icon.png"));
		exitItem = new JMenuItem("Exit", new ImageIcon("data/images/16x16/exit_icon.png"));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		// creating the project menu
		projectMenu = new JMenu("Project");
		addRouterItem = new JMenuItem("Add router", new ImageIcon("data/images/16x16/router_icon.png"));
		addCollisionDomainItem = new JMenuItem("Add collision domain", new ImageIcon("data/images/16x16/collisionDomain_icon.png"));
		addLinkItem = new JMenuItem("Add link", new ImageIcon("data/images/16x16/link_icon.png"));
		addPcItem = new JMenuItem("Add pc", new ImageIcon("data/images/16x16/pc_icon.png"));
		addServerItem = new JMenuItem("Add server", new ImageIcon("data/images/16x16/server_icon.png"));
		addNattedServerItem = new JMenuItem("Add natted server", new ImageIcon("data/images/16x16/nattedserver_icon.png"));
		addFirewallItem = new JMenuItem("Add firewall", new ImageIcon("data/images/16x16/firewall_icon.png"));
		addAreaItem = new JMenuItem("Add area", new ImageIcon("data/images/16x16/area_icon.png"));
		projectMenu.add(addRouterItem);
		projectMenu.add(addCollisionDomainItem);
		projectMenu.add(addPcItem);
		projectMenu.add(addServerItem);
		projectMenu.add(addNattedServerItem);
		projectMenu.add(addFirewallItem);
		projectMenu.add(addAreaItem);
		projectMenu.addSeparator();
		projectMenu.add(addLinkItem);
		menuBar.add(projectMenu);
		// creating help menu
		helpMenu = new JMenu("Help");
		infoItem = new JMenuItem("Info", new ImageIcon("data/images/16x16/info_icon.png"));
		helpMenu.add(infoItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	/** Create the vertical toolbar and add it to the west side of the frame
	 */
	private void createVericalToolBar() {
		verticalToolbar = new JToolBar();
		verticalToolbar.setOrientation(JToolBar.VERTICAL);
		verticalToolbar.setFloatable(false);
		verticalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.lightGray));
		
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
		
		add( verticalToolbar, BorderLayout.WEST );
	}

	/** Create the standard toolbar and add it to the north side of the frame
	 */
	private void createToolBar() {
		toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		
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
		
		add( toolbar, BorderLayout.NORTH );
	}
	
	/** Create the stateBar and add it to the south side of the frame
	 */
	private void createStateBar() {
		stateLabel = new JLabel("Status: editing", JLabel.TRAILING);
		statePanel = new JPanel(new GridLayout(1, 1));
		
		statePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
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

				JOptionPane.showMessageDialog( GuiManager.getInstance().getFrame(), editorPane, "Info", 
						JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/images/images/GNU.png"));
			}
		});
		
		// TODO menu items listeners
		
		/********************************
		 *  Buttons listeners
		 ********************************/
		
		newFile.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ProjectHandler.getInstance().newProject();
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
				ProjectHandler.getInstance().saveProject();
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
				Shell.startLab( GuiManager.getInstance().getProject().getDirectory() );
			}
		});
		
		stop.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("stopping lab");
				Shell.stopLab( GuiManager.getInstance().getProject().getDirectory(), false );
			}
		});
	}
	
	public void closeApplication() {
		
		if( ProjectHandler.getInstance().isSaved() == false ) {
			int close = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit without saving the project?", 
					"Project not saved", JOptionPane.YES_NO_OPTION);
			if( close == JOptionPane.YES_OPTION ) {
				this.dispose();
			}
		} else {
			this.dispose();
		}
	}
}
