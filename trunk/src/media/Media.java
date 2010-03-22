package media;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
	
public class Media extends JFrame {
	private static final long serialVersionUID = -8303752957813950165L;

	Dimension dim;
	JMenuBar menuBar;
	
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exit;
	
	JToolBar toolbar;
	JButton open;
	JButton save;
	
	JTextArea textArea;
	JScrollPane textAreaContainer;
	JTextField nomeEsame;
	JTextField voto;
	JPanel textPanel;
	
	JLabel stateLabel;
	JPanel statePanel;

	boolean dontAsk;
	
	public Media() {
		createWindow();
	}

	private void createWindow() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Apri");
		saveItem = new JMenuItem("Salva");
		exit = new JMenuItem("Exit");
		
		toolbar = new JToolBar();
		open = new JButton("Apri");
		save = new JButton("Salva");
		
		textArea = new JTextArea(20, 40);
		textAreaContainer = new JScrollPane(textArea);
		nomeEsame = new JTextField( 30 );
		voto = new JTextField( 30 );
		
		stateLabel = new JLabel("Status: editing", JLabel.TRAILING);
		statePanel = new JPanel(new GridLayout(1, 1));
		
		setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exit);
		menuBar.add(fileMenu);
		
		toolbar.setFloatable(false);
		toolbar.add( open );
		toolbar.add( save );
		
		textArea.setEditable(false);
		
		textPanel = new JPanel( new BorderLayout() );
		textPanel.add( textAreaContainer, BorderLayout.NORTH );
		JPanel p = new JPanel( new GridLayout(1, 2));
		textPanel.add( p, BorderLayout.CENTER );
		p.add( new JLabel("Nome esame"), 0 );
		p.add( nomeEsame, 1 );
		p.add( new JLabel("Voto"), 0);
		p.add( voto, 1 );
		
		statePanel.setBorder(BorderFactory.createLoweredBevelBorder());
		statePanel.add(stateLabel);
		
		setJMenuBar(menuBar);
		setLayout(new BorderLayout());
		add( toolbar, BorderLayout.NORTH );
		add( textPanel, BorderLayout.CENTER );
		add( statePanel, BorderLayout.SOUTH );
		
		setupListeners();
		
		pack();
		setVisible(true);
	}
	
	void setupListeners() {
		addWindowListener( new WindowAdapter() {
			@Override 
			public void windowClosing( WindowEvent e ) {
				closeApplication();
			}
		});
		
		exit.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e ) {
				closeApplication();
			}
		});
		
		open.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Apri");
			}
		});
		
		save.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Salva");
			}
		});
		
		nomeEsame.addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
					printText( "sal", nomeEsame.getText() );
				}
			}
		});
	}

	void printText( String user, String text ) {
		nomeEsame.setText("");
		textArea.append( (user + ": ").toUpperCase() + text + "\n" );
	}
	
	private void closeApplication() {
		if( dontAsk == true ) {
			this.dispose();
		} else {
			
			final JOptionPane exitDialog = new JOptionPane( "Sei sicuro di voler chiudere l'applicazione?",
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
	}
	
	public static void main(String[] args) {
		new Media();
	}
}
