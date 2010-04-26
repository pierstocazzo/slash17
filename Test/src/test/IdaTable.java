package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class IdaTable extends JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new IdaTable();
	}
	
	IdaTable() {
//		setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
//		setBounds(0, 0, 500,500);
		size = new Dimension(700,7);
		setSize( size );
		setLayout(new BorderLayout());
		
		JPanel p = new JPanel();
//		p.setSize(size);
//		p.setPreferredSize(size);
//		p.setMinimumSize(size);
//		p.setMaximumSize(size);
		
//		size = new Dimension( 500, p.getHeight() );
		int rows = 40; int cols = 40; 
		JTable table = new JTable(rows, cols); 
		table.setRowHeight( size.height / rows );
		table.setSize(size);
		table.setPreferredSize(size);
		table.setMaximumSize(size);
		table.setMinimumSize(size);
		
		p.add(table);
		
		add(p, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}
