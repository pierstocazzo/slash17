package gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GFrame extends JFrame {
	private static final long serialVersionUID = 3661490807594270819L;

	/** main panel */
	GPanel mainPanel;
	
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
	JMenuItem creditsItem;
	
	boolean dontAsk;
	
	boolean saved = false;
	
	public GFrame() {
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize( 1000, 600 );
		setLayout(new BorderLayout());
		
		createMenuBar();
		
		setupListeners();
		
		mainPanel = new GPanel(this);
		this.setContentPane(mainPanel);
		
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
		creditsItem = new JMenuItem("Credits");
		
		helpMenu.add(creditsItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void setupListeners() {
		// set windows clising event
		addWindowListener( new WindowAdapter() {
			@Override 
			public void windowClosing( WindowEvent e ) {
				closeApplication();
			}
		});
		
		exitItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				closeApplication();
			}
		});
		
		// TODO menu items listeners
	}
	
	public void closeApplication() {
		if( saved = false ) {
			if( dontAsk == true ) {
				this.dispose();
			} else {
				
				final JOptionPane exitDialog = new JOptionPane( "Do you want to quit the application without saving?",
						JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION );
				
				final Checkbox check = new Checkbox( "Never show this dialog again", false );
				check.addItemListener( new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						dontAsk = check.getState();
					}
				});
				exitDialog.add( check );
				check.setVisible(true);
				
				final JDialog dialog = new JDialog( this,
						"Click a button",
						true);
				dialog.setContentPane( exitDialog );
				dialog.setDefaultCloseOperation(
						JDialog.DO_NOTHING_ON_CLOSE);
	
	            exitDialog.addPropertyChangeListener(
	                    new PropertyChangeListener() {
	                        public void propertyChange(PropertyChangeEvent e) {
	                            String prop = e.getPropertyName();
	
	                            if (dialog.isVisible()
	                             && ( e.getSource() == exitDialog )
	                             && (JOptionPane.VALUE_PROPERTY.equals(prop))) {
	                                //If you were going to check something
	                                //before closing the window, you'd do
	                                //it here.
	                                dialog.setVisible(false);
	                            }
	                        }
	                    });
	
				
				dialog.pack();
	            dialog.setLocationRelativeTo( this );
	            dialog.setVisible(true);
	
				
				int answer = ((Integer) exitDialog.getValue()).intValue();
		
				if( answer == JOptionPane.YES_OPTION ) {
					this.dispose();
				}
			}
		} else {
			this.dispose();
		}
	}
	
	public static void main(String[] args) {
		new GFrame();
	}
}
