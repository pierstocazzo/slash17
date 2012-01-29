package jmovie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public class JMovieFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	JTable table;
	JScrollPane scrollPane;
	JButton aggiungiFilm, cancella, importa, salvaModifiche;
	Object[][] data;
	
	ArrayList<Film> listaFilm;
	
	boolean modificato;
	
	Frame frame;
	
	/** menu bar */
	JMenuBar menuBar;
	
	/** file menu */
	JMenu fileMenu;
	/** file menu item */
	JMenuItem newItem, openItem, saveItem, exitItem;
	
	/** view menu */
	JMenu viewMenu;
	/** view menu items */
	JMenuItem listViewItem, iconViewItem, wishListViewItem;
	
	/** help menu */
	JMenu helpMenu;
	/** help menu item */
	JMenuItem aboutItem, licenceItem;
	
	/** horizontal tool bar */
	JToolBar horizontalToolbar;
	/** horizontal tool bar's button */
	JButton newDB, openDB, saveDB;
	
	public static void main(String[] args) {		 
		 JMovieFrame mF = new JMovieFrame();
		 mF.showGUI();
//		 FilmUPWrapper.query("spiderman");
//		new JMovieFrame().importFilmUpDB("MyFilm.mdb");
	}
	
	
	public JMovieFrame() {
		listaFilm = new ArrayList<Film>();
		modificato = false;
		frame = this;
	}
	
	public void showGUI() {
		String os = System.getProperty("os.name");
		if( os.equals("Linux") ) {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
			} catch (Exception e) { 
				System.err.println("Cannot set the GTK Look and Feel. Setting the default"); 
			}
		} else {
			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
			} catch (Exception e) { 
				System.err.println("Cannot set the system Look and Feel. Setting the Java L&F"); 
			}
		} 
		
		setTitle("JMovie");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		setLayout(new BorderLayout(5,5));
		
		createMenuBar();
		createToolBar();
		setupListeners();
		
		table = new JTable();
		scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel p = new JPanel();
		add(p, BorderLayout.SOUTH);
		
		aggiungiFilm = new JButton("Aggiungi");
		aggiungiFilm.setPreferredSize(new Dimension(130, 30));
		aggiungiFilm.setToolTipText("Aggiungi Film");
		aggiungiFilm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aggiungiFilm();
			}
		});
		p.add(aggiungiFilm);
		
		salvaModifiche = new JButton("Salva");
		salvaModifiche.setPreferredSize(new Dimension(130, 30));
		salvaModifiche.setToolTipText("Salve tutte le modifiche");
		salvaModifiche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvaSuFile();
			}
		});
		p.add(salvaModifiche);
		
		cancella = new JButton("Cancella");
		cancella.setPreferredSize(new Dimension(130, 30));
		cancella.setToolTipText("Cancella i film selezionati");
		cancella.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> toDelete = new ArrayList<Integer>();
				String stringa = "Vuoi eliminare i film: \n";
				for (int row : table.getSelectedRows()) {
					toDelete.add((Integer) table.getValueAt(row, 0) - 1);
					stringa += " - " + table.getValueAt(row, 1) + "\n";
				}
				int res = JOptionPane.showConfirmDialog(frame, stringa, 
						"Elimina film", JOptionPane.YES_NO_OPTION);
				if (res == JOptionPane.NO_OPTION)
					return;
				delete(toDelete);
			}
		});
		p.add(cancella);
		
		importa = new JButton("importa");
		importa.setPreferredSize(new Dimension(130, 30));
		importa.setToolTipText("importa i film da FilmUP");
		importa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Seleziona il file Database (.mdb) di FilmUP da importare");
				fc.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "FilmUP Database file (.mdb)";
					}
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) 
							return true;
						int dotIndex = f.getPath().lastIndexOf('.');
						String extension = f.getPath().substring(dotIndex);
						if (extension.equals(".mdb"))
							return true;
						return false;
					}
				});
				fc.setAcceptAllFileFilterUsed(false);
				int choose = fc.showOpenDialog(frame);
				if( choose == JFileChooser.APPROVE_OPTION ) {
					File dbFile = fc.getSelectedFile();
					importFilmUpDB(dbFile);
					aggiornaTabella();
				} 
			}
		});
		p.add(importa);
		
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		
		caricaDaFile();
		aggiornaTabella();
		setVisible(true);
	}
	
	/** Create the menu bar and add it to the frame
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		
		// creating the file menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newItem = new JMenuItem("Nuovo DB", new ImageIcon("data/images/16x16/new_icon.png"));
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		openItem = new JMenuItem("Apri DB", new ImageIcon("data/images/16x16/open_icon.png"));
		saveItem = new JMenuItem("Salva DB corrente", new ImageIcon("data/images/16x16/save_icon.png"));
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		exitItem = new JMenuItem("Esci", new ImageIcon("data/images/16x16/exit_icon.png"));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		// creating view menu
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		menuBar.add(viewMenu);
		// creating help menu
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		aboutItem = new JMenuItem("About", new ImageIcon("data/images/16x16/about_icon.png"));
		aboutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		licenceItem = new JMenuItem("Licenza", new ImageIcon("data/images/16x16/license_icon.png"));
		helpMenu.add(aboutItem);
		helpMenu.add(licenceItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}
	
	/** Create the standard toolbar and add it to the north side of the frame
	 */
	private void createToolBar() {
		horizontalToolbar = new JToolBar();
		horizontalToolbar.setFloatable(false);
		horizontalToolbar.setPreferredSize(new Dimension(40, 30));
		horizontalToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
		
		newDB = new JButton("Nuovo", new ImageIcon("data/images/16x16/new_icon.png"));
		openDB = new JButton("Apri", new ImageIcon("data/images/16x16/open_icon.png"));
		saveDB = new JButton("Salva", new ImageIcon("data/images/16x16/save_icon.png"));
		
		horizontalToolbar.add( newDB );
		horizontalToolbar.add( openDB );
		horizontalToolbar.add( saveDB );
		horizontalToolbar.addSeparator();
		 
		add( horizontalToolbar, BorderLayout.NORTH );
	}
	
	public void showInfo() {
		String credits = "<html><center><b><font size=6>jMovieDB</font></b></center><br>" +
			"Released under GNU General Public License version 3. " +
			"<a href=http://www.gnu.org/licenses/gpl-3.0-standalone.html>GPLv3</a><br><br>" +
			"Copyright \u00a9 2012 <i>Loria Salvatore</i><br>" +
			"Visit <a href=http://slash17.googlecode.com>http://slash17.googlecode.com</a></html>";

		JEditorPane editorPane = new JEditorPane("text/html", credits);
		editorPane.setEditable(false);

		editorPane.addHyperlinkListener( new HyperlinkListener() {
			public void hyperlinkUpdate( HyperlinkEvent evt ) {
				if( evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED ) {
					try {
						Desktop.getDesktop().browse( new URI(evt.getDescription()) );
					} catch (Exception e) {
					}
				}
			}
		});

		JOptionPane.showMessageDialog( frame, editorPane, "About...", 
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon("data/images/big/GNU.png"));
	}
	
	public void showLicence() {
		File f = new File("licence.txt");
		String text = "";
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			String s;
			while( (s = r.readLine() ) != null ) {
				text = text + s + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JDialog d = new JDialog(frame);
		d.setTitle("Licence");
		d.setContentPane(new JPanel(new BorderLayout()));
		JLabel title = new JLabel("jMovie Licence", JLabel.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		title.setFont(new Font("Times New Roman", Font.BOLD, 24));
		d.add(title, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(new JTextArea(text));
		d.add(scrollPane);
		d.setSize(600, 600);
		d.setLocationRelativeTo(frame);
		d.setVisible(true);
	}
	
	private void setupListeners() {
		/** set the window's closing event 
		 */
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				chiudiApplicazione();
			}
		});
		
		/** Set the window's minimum size
		 */
		addComponentListener( new ComponentAdapter() {
			public void componentResized( ComponentEvent e ) {
				JFrame tmp = (JFrame) e.getSource();
				if ( tmp.getWidth() < 600 || tmp.getHeight() < 600 ) {
					tmp.setSize( 700, 630 );
				}
			}
		});
		
		/********************************
		 * Menu's items listeners
		 ********************************/
		
		newItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO nuovo database				
			}
		} );
		openItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				caricaDaFile();
			}
		} );
		saveItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvaSuFile();
			}
		});
		exitItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chiudiApplicazione();
			}
		} );
		
		aboutItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showInfo();
			}
		});
		licenceItem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showLicence();
			}
		});
		
		
		/********************************
		 * Buttons listeners
		 ********************************/
		
		newDB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		} );
		openDB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		} );
		saveDB.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		} );
	}
	
	private void chiudiApplicazione() {
		if(modificato) {
			int res = JOptionPane.showConfirmDialog(null, "Vuoi salvare le modifiche prima di uscire?");
			if (res == JOptionPane.YES_OPTION) {
				salvaSuFile();
			} else if (res == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		dispose();
	}
	
	public void caricaDaFile() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(new File("FilmDB.xml"));
			Element root = doc.getRootElement();
			@SuppressWarnings("unchecked")
			Iterator<Element> it = root.getChildren().iterator();
			while (it.hasNext()) {
				Element el = it.next();
				Film film = new Film();
				
				film.setTitolo(el.getChild("titolo").getText());
				film.setTitoloOriginale(el.getChild("titoloOriginale").getText());
				film.setTrama(el.getChild("trama").getText());
				film.setGenere(el.getChild("genere").getText());
				film.setPathLocandina(el.getChild("pathLocandina").getText());
				film.setAnno(el.getChild("anno").getText());
				film.setElencoLingue(el.getChild("elencoLingue").getText());
				film.setDurata(el.getChild("durata").getText());
				film.setCast(el.getChild("cast").getText());
				film.setRegia(el.getChild("regia").getText());
				film.setHomepage(el.getChild("homepage").getText());
				
				listaFilm.add(film);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void salvaSuFile() {
		int rows = table.getModel().getRowCount();
		
		// aggiorno la lista di film a partire dalla tabella
		for (int i = 0; i < rows; i++) {
			int index = (Integer) table.getModel().getValueAt(i, 0) - 1;
			Film m = listaFilm.get(index); 
			String titolo = table.getModel().getValueAt(i, 1).toString();
			String regista = table.getModel().getValueAt(i, 2).toString();
			String anno = table.getModel().getValueAt(i, 3).toString();
			if (titolo.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Impossibile salvare, manca il titolo del film #"+(i+1));
				table.setRowSelectionInterval(i, i);
				return;
			} else if (!anno.isEmpty() && !anno.matches("(19[0-9][0-9]|20[0-9][0-9])")){
				JOptionPane.showMessageDialog(this, "Impossibile salvare, formato dell'anno scorretto per il film #"+(i+1));
				table.setRowSelectionInterval(i, i);
				
				return;
			} 

			m.setTitolo(titolo);
			m.setRegia(regista);
			m.setAnno(anno);
		}

		try {
			Element root = new Element("FilmDB");
			Document document = new Document(root);
			
			for (Film film : listaFilm) {
				Element filmElement = new Element("film");
				root.addContent(filmElement);
				
				Element titolo = new Element("titolo");
				titolo.addContent(film.getTitolo());
				filmElement.addContent(titolo);
				
				Element titoloOriginale = new Element("titoloOriginale");
				titoloOriginale.addContent(film.getTitoloOriginale());
				filmElement.addContent(titoloOriginale);
				
				Element regia = new Element("regia");
				regia.addContent(film.getRegia());
				filmElement.addContent(regia);
				
				Element anno = new Element("anno");
				anno.addContent(film.getAnno());
				filmElement.addContent(anno);
				
				Element trama = new Element("trama");
				trama.addContent(film.getTrama());
				filmElement.addContent(trama);
				
				Element genere = new Element("genere");
				genere.addContent(film.getGenere());
				filmElement.addContent(genere);
				
				Element cast = new Element("cast");
				cast.addContent(film.getCast());
				filmElement.addContent(cast);
				
				Element durata = new Element("durata");
				durata.addContent(film.getDurata());
				filmElement.addContent(durata);
				
				Element homepage = new Element("homepage");
				homepage.addContent(film.getHomepage());
				filmElement.addContent(homepage);
				
				Element elencoLingue = new Element("elencoLingue");
				elencoLingue.addContent(film.getElencoLingue());
				filmElement.addContent(elencoLingue);
				
				Element pathLocandina = new Element("pathLocandina");
				pathLocandina.addContent(film.getPathLocandina());
				filmElement.addContent(pathLocandina);
			}
			
			XMLOutputter outputter = new XMLOutputter();
			outputter.setFormat( Format.getPrettyFormat() );
			outputter.output( document, new FileOutputStream("FilmDB.xml") );
			
			aggiornaTabella();
			JOptionPane.showMessageDialog(frame, "Tutte le modifiche sono state salvate");
			
			modificato = false;
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(frame, "ERRORE: impossibile salvare");
			e1.printStackTrace();
		}
	}

	public void aggiungiFilm() {
		String titolo = JOptionPane.showInputDialog(frame, "Inserisci il titolo del film");
		
		if (titolo == null || titolo.isEmpty())
			return;
		
		final JDialog d = new JDialog(frame);
		d.setModal(true);
		
		DefaultListModel listModel = new DefaultListModel();
		
		final ArrayList<FilmUPResult> list = FilmUPWrapper.query(titolo);
		for (int i = 0; i < list.size(); i++) {
			listModel.addElement(list.get(i).toString());
		}
		final JList jlist = new JList(listModel);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setSelectedIndex(0);
        jlist.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(jlist);
        d.add(listScrollPane, BorderLayout.CENTER);
        
        JButton add = new JButton("Aggiungi");
        add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = jlist.getSelectedIndex();
				Film m = new Film(list.get(index));
				listaFilm.add(m);
				Collections.sort(listaFilm);
				aggiornaTabella();
				modificato = true;
				d.dispose();
			}
		});
        d.add(add,BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(frame);
		d.setVisible(true);
	}
	
	public void delete(ArrayList<Integer> toDelete) {
		for(int i = toDelete.size()-1; i >= 0; i--) {
			listaFilm.remove((int) toDelete.get(i));
			modificato = true;
		}
		aggiornaTabella();
	}

	public void aggiornaTabella() {
		Object[] headers = {"#","Titolo","Regista","Anno"};
		data = new Object[listaFilm.size()][4];
		
		for (int i = 0; i < listaFilm.size(); i++) {
			data[i][0] = new Integer(i+1);
			data[i][1] = listaFilm.get(i).getTitolo();
			data[i][2] = listaFilm.get(i).getRegia();
			data[i][3] = listaFilm.get(i).getAnno();
		}
		
		TableModel model = new DefaultTableModel(data, headers) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				Class<?> returnValue;
				if (getRowCount() > 0 && (column >= 0) && (column < getColumnCount()) && getValueAt(0, column) != null) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}
		};
		
		
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				Film f = listaFilm.get(row);
				f.setTitolo(table.getModel().getValueAt(row, 1).toString());
				f.setRegia(table.getModel().getValueAt(row, 2).toString());
				f.setAnno(table.getModel().getValueAt(row, 3).toString());
				modificato = true;
			}
		});
		
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		Comparator<Object> comparator = new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				if (o1 instanceof Integer && o2 instanceof Integer)
					return ((Integer) o1).compareTo((Integer) o2);
				else if (o1 instanceof String && o2 instanceof String)
					return ((String) o1).compareTo((String) o2);
				else if (o1 instanceof String && o2 instanceof Integer) {
					Integer i1 = new Integer((String) o1);
					Integer i2 = (Integer) o2;
					return i1.compareTo(i2);
				} else if (o1 instanceof Integer && o2 instanceof String) {
					Integer i1 = (Integer) o1;
					Integer i2 = new Integer((String) o2);
					return i1.compareTo(i2);
				} else 
					return -1;
					
			}
		};
		sorter.setComparator(0, comparator);
		sorter.setComparator(3, comparator);
		
		table.setRowSorter(sorter);
		
		table.setModel(model);
		
		table.setRowMargin(5);
		table.setRowHeight(35);
		table.getColumnModel().setColumnMargin(5);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(0).setMinWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		table.getColumnModel().getColumn(1).setMinWidth(400);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setMinWidth(150);
		table.getColumnModel().getColumn(2).setMaxWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(3).setMaxWidth(60);
		table.getColumnModel().getColumn(3).setMinWidth(60);
	}
	
	public void importFilmUpDB(File dbFile) {
		try {
			Table table;
			Database db = Database.open(dbFile);
			table = db.getTable("FILM");
			
			for(Map<String, Object> row : table) {
				Film film = new Film();
				
				film.setTitolo((String) row.get("TITOLO_FILM"));
				film.setTitoloOriginale((String) row.get("TITOLO_ORIGINALE"));
				film.setTrama((String) row.get("TRAMA"));
				film.setGenere((String) row.get("GENERE"));
				film.setPathLocandina((String) row.get("PATH_LOCANDINA"));
				film.setAnno((String) row.get("ANNO_PUBBLICAZIONE"));
				film.setElencoLingue((String) row.get("ELENCO_LINGUE"));
				film.setDurata((String) row.get("DURATA"));
				film.setCast((String) row.get("CAST"));
				film.setRegia((String) row.get("REGIA"));
				film.setHomepage((String) row.get("HOMEPAGE"));
				
				listaFilm.add(film);
			}
			Collections.sort(listaFilm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
