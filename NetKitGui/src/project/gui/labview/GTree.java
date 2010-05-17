package project.gui.labview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class GTree extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int INTERFACES = 0;
	public static final int FIREWALLING = 1;
	public static final int ROUTING = 2;
	public static final int LABSTRUCTURE = 3;
	protected GTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    
    public GTree( String stringLabel, String projectName ) {
        super(new BorderLayout());
        
		JPanel northPanel = new JPanel(new BorderLayout());
		northPanel.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 0));
		northPanel.add( new JLabel( stringLabel ), BorderLayout.WEST );
		
		Dimension size = new Dimension(20, 20);
		
		JButton coll = new JButton(new ImageIcon("data/images/16x16/collapse_icon.png"));
		coll.setPreferredSize(size);
		coll.setToolTipText("Collapse All");
		coll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				collapseAll();
			}
		});
		
		JButton exp = new JButton(new ImageIcon("data/images/16x16/expand_icon.png"));
		exp.setPreferredSize(size);
		exp.setToolTipText("Expand All");
		exp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				expandAll(tree);
			}
		});
		
		JToolBar buttonsBar = new JToolBar();
		buttonsBar.setFloatable(false);
		buttonsBar.add(coll);
		buttonsBar.add(exp);
		
		northPanel.add(buttonsBar, BorderLayout.EAST);
		
		add( northPanel, BorderLayout.NORTH );
        
        rootNode = new GTreeNode(projectName, GTreeNode.PROJECTFOLDER, this);
        treeModel = new DefaultTreeModel(rootNode);
        
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.addMouseListener( new MyListener() );
        
        MyRenderer renderer = new MyRenderer();
        tree.setCellRenderer(renderer);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
    
    /** expand the node with this name */
    public void expandNode( String nodeName ) {
    	collapseAll();
    	
    	for( int i = 0; i < rootNode.getChildCount(); i++ ) {
    		GTreeNode node = ((GTreeNode) rootNode.getChildAt(i));
    		if( node.getUserObject().equals( nodeName ) ) {
    			TreePath path = ( node.getChildCount() > 0 ) ?
	    				new TreePath(((GTreeNode) node.getLastChild()).getPath()) :
	    				new TreePath(((GTreeNode) node).getPath());
	    				
				tree.scrollPathToVisible( path );
    			return;
    		}
			for( int j = 0; j < node.getChildCount(); j++ ) {
				GTreeNode child = ((GTreeNode) node.getChildAt(i));
	    		if( child.getUserObject().equals( nodeName ) ) {
	    			TreePath path = ( child.getChildCount() > 0 ) ?
	    				new TreePath(((GTreeNode) child.getLastChild()).getPath()) :
	    				new TreePath(((GTreeNode) child).getPath());
	    				
    				tree.scrollPathToVisible( path );
	    			return;
	    		}
    		}
    	}
    }
    
    /** set the name of this tree's root node */
    public void setName( String name ) {
		rootNode.setUserObject( name );
	}

	public void collapseAll() {
    	int row = tree.getRowCount() - 1;
    	while (row >= 0) {
    		tree.collapseRow(row);
    		row--;
    	}
    }

    public void expandAll(JTree tree) {
    	int row = 0;
    	while (row < tree.getRowCount()) {
    		tree.expandRow(row);
    		row++;
    	}
    }

    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        } 
    }

    /** Add child to the root node. */
    public GTreeNode addNode( Object child, int type ) {
        return addNode( rootNode, child, type );
    }

    /** Add child to the parent node. */
    public GTreeNode addNode( GTreeNode parent, Object obj, int type ) {
    	GTreeNode node = new GTreeNode(obj, type, this);
    	
        treeModel.insertNodeInto(node, parent, parent.getChildCount());
        
        collapseAll();
        tree.scrollPathToVisible(new TreePath(node.getPath()));
        
    	return node;
    }

    /** the mouse listeners for the tree nodes */
    class MyListener extends MouseAdapter {
    	@Override
    	public void mousePressed(MouseEvent e) {
    		int selRow = tree.getRowForLocation(e.getX(), e.getY());
    		if( selRow < 0 )
    			return;

    		// get the selected node
    		TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
    		tree.setSelectionPath(selPath); 
    		
			GTreeNode node = (GTreeNode) selPath.getLastPathComponent();

			if (e.isPopupTrigger())
				node.showMenu(e);
			
			if( e.getClickCount() >= 2 )
				node.showConfDialog();
		}
    }

    /** Renderer for the tree */
    class MyRenderer extends DefaultTreeCellRenderer {
    	private static final long serialVersionUID = 1L;

    	public MyRenderer() {
    		super();
    	}

    	public Component getTreeCellRendererComponent( JTree tree, Object value,
    			boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

    		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

    		GTreeNode node = (GTreeNode) value;
    		setIcon( node.getIcon() );

    		return this;
    	}
    }
}