package jmovie;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class JMovieFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	JTable table;
	JButton aggiungiFilm;
	JButton cancella;
	JButton salvaModifiche;
	Object[][] data;
	
	ArrayList<Film> listaFilm;
	
	boolean modificato;
	
	Frame frame;
	
	public static void main(String[] args) {		 
//		JMovieFrame mF = new JMovieFrame();
//		mF.showGUI();
		FilmUPWrapper.query("hulk");
	}
	
	public JMovieFrame() {
		listaFilm = new ArrayList<Film>();
		modificato = false;
		frame = this;
	}
	
	public void showGUI() {
		setTitle("JMovie");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
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
		});
		
		Dimension d = new Dimension(700, 500);
		setSize(d);
		
		setLayout(new BorderLayout(5,5));
		
		table = new JTable();
		JScrollPane pane = new JScrollPane(table);
		add(pane,BorderLayout.CENTER);
		
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
		
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		
		caricaDaFile();
		aggiornaTabella();
		setVisible(true);
	}
	
	public void caricaDaFile() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File("film.db")));
			String s;
			while((s = in.readLine()) != null) {
				String ss[] = s.split("\\|");
				String titolo = ss[0];
				String regista = ss[1];
				String annoStringa = ss[2];
				int anno = -1;
				if (!annoStringa.isEmpty() && annoStringa.matches("(19[0-9][0-9]|20[0-9][0-9])"))
					anno = Integer.parseInt(annoStringa);
				Film m = new Film(titolo, regista, anno);
				listaFilm.add(m);
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
			String annoStringa = table.getModel().getValueAt(i, 3).toString();
			if (titolo.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Impossibile salvare, manca il titolo del film #"+(i+1));
				table.setRowSelectionInterval(i, i);
				return;
			} else if (!annoStringa.isEmpty() && !annoStringa.matches("(19[0-9][0-9]|20[0-9][0-9])")){
				JOptionPane.showMessageDialog(this, "Impossibile salvare, formato dell'anno scorretto per il film #"+(i+1));
				table.setRowSelectionInterval(i, i);
				return;
			} 

			m.setTitolo(titolo);
			m.setRegista(regista);
			if (!annoStringa.isEmpty())
				m.setAnno(Integer.parseInt(annoStringa));
		}
		
		// salvo il tutto su file
		File f = new File("film.db");
		try {
			PrintWriter out = new PrintWriter(f);
			for (Film m : listaFilm) {
				out.println(m.getTitolo() + "|" + m.getRegista() + "|" + m.getAnno());
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		aggiornaTabella();
		JOptionPane.showMessageDialog(frame, "Tutte le modifiche sono state salvate");
		
		modificato = false;
	}

	public void aggiungiFilm() {
//		PopupAggiungiFilm pop = new PopupAggiungiFilm(this);
//		Film m = pop.display();
		
		String titolo = JOptionPane.showInputDialog(frame, "Inserisci il titolo del film");
		
		if (titolo == null || titolo.isEmpty())
			return;
		
		final JDialog d = new JDialog(frame);
		d.setModal(true);
		
		DefaultListModel listModel = new DefaultListModel();
		
		final ArrayList<FilmUPResult> list = FilmUPWrapper.query(titolo);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i) + " - URL: " + list.get(i).getUrl());
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
			data[i][2] = listaFilm.get(i).getRegista();
			data[i][3] = listaFilm.get(i).getAnno() != -1 ? "" + listaFilm.get(i).getAnno() : "";
		}
		
		TableModel model = new DefaultTableModel(data, headers) {
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int column) {
				Class<?> returnValue;
				if (getRowCount() > 0 && (column >= 0) && (column < getColumnCount())) {
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
				f.setRegista(table.getModel().getValueAt(row, 2).toString());
				if (table.getModel().getValueAt(row, 3) instanceof Integer)
					f.setAnno((Integer) table.getModel().getValueAt(row, 3));
				else if (table.getModel().getValueAt(row, 3).toString().isEmpty())
					f.setAnno(-1);
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
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setMaxWidth(30);
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
}
