//package interfaces;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.geom.Rectangle2D;
//
//import javax.swing.JFrame;
//
//import org.jgrapht.DirectedGraph;
//import org.jgrapht.ListenableGraph;
//import org.jgrapht.ext.JGraphModelAdapter;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.DefaultListenableGraph;
//import org.jgrapht.graph.DefaultWeightedEdge;
//import org.jgrapht.graph.DirectedMultigraph;
//
//import demo.UndirectedWeightedGraph;
//
//
///**
// * A demo applet that shows how to use JGraph to visualize JGraphT graphs.
// *
// * @author Barak Naveh
// * @since Aug 3, 2003
// */
//public class PopupGrafo 
//{
//	//~ Static fields/initializers ---------------------------------------------
//
//	private static final long serialVersionUID = 3256444702936019250L;
//	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
//	private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
//
//	//~ Instance fields --------------------------------------------------------
//
//	//
//	private JGraphModelAdapter<String, DefaultEdge> jgAdapter;
//
//	//~ Methods ----------------------------------------------------------------
//
//	/**
//	 * An alternative starting point for this demo, to also allow running this
//	 * applet as an application.
//	 *
//	 * @param args ignored.
//	 */
//	//    public static void main(String [] args)
//	//    {
//	//        PopupGrafo applet = new PopupGrafo();
//	//        applet.init();
//	//
//	//        JFrame frame = new JFrame();
//	//        frame.getContentPane().add(applet);
//	//        frame.setTitle("JGraphT Adapter to JGraph Demo");
//	//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	//        frame.pack();
//	//        frame.setVisible(true);
//	//    }
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void show(UndirectedWeightedGraph g1, int size)
//	{
//		// create a JGraphT graph
//		ListenableGraph<String, DefaultEdge> g = 
//				new ListenableDirectedMultigraph<String, DefaultEdge>(
//						DefaultEdge.class);
//
//		// create a visualization using JGraph, via an adapter
//		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);
//
//
//		JGraph jgraph = new JGraph(jgAdapter);
//
//		//        adjustDisplaySettings(jgraph);
//
//		for (String v : g1.vertexSet())
//			g.addVertex(v);
//
//		for (DefaultWeightedEdge e : g1.edgeSet())
//			g.addEdge(g1.getEdgeSource(e), g1.getEdgeTarget(e));
//
//		for(int i=0; i<size; i++)
//			for (int j = 0; j < size; j++) {
//				if(g.containsVertex(j + "-" + i))
//					positionVertexAt(j + "-" + i, 150*i, 50*j);
//			}
//
//		JFrame f = new JFrame();
//		f.add(jgraph);
//		f.setVisible(true);
//		f.pack();
//
//	}
//	//
//	//    private void adjustDisplaySettings(JGraph jg)
//	//    {
//	//        jg.setPreferredSize(DEFAULT_SIZE);
//	//
//	//        Color c = DEFAULT_BG_COLOR;
//	//        String colorStr = null;
//	//
//	//        try {
//	//            colorStr = getParameter("bgcolor");
//	//        } catch (Exception e) {
//	//        }
//	//
//	//        if (colorStr != null) {
//	//            c = Color.decode(colorStr);
//	//        }
//	//
//	//        jg.setBackground(c);
//	//    }
//
//	@SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
//	private void positionVertexAt(Object vertex, int x, int y)
//	{
//		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
//		AttributeMap attr = cell.getAttributes();
//		Rectangle2D bounds = GraphConstants.getBounds(attr);
//
//		Rectangle2D newBounds =
//				new Rectangle2D.Double(
//						x,
//						y,
//						bounds.getWidth(),
//						bounds.getHeight());
//
//		GraphConstants.setBounds(attr, newBounds);
//
//		// TODO: Clean up generics once JGraph goes generic
//		AttributeMap cellAttr = new AttributeMap();
//		cellAttr.put(cell, attr);
//		jgAdapter.edit(cellAttr, null, null, null);
//	}
//
//	//~ Inner Classes ----------------------------------------------------------
//
//	/**
//	 * a listenable directed multigraph that allows loops and parallel edges.
//	 */
//	private static class ListenableDirectedMultigraph<V, E>
//	extends DefaultListenableGraph<V, E>
//	implements DirectedGraph<V, E>
//	{
//		private static final long serialVersionUID = 1L;
//
//		ListenableDirectedMultigraph(Class<E> edgeClass)
//		{
//			super(new DirectedMultigraph<V, E>(edgeClass));
//		}
//	}
//}
//
//// End JGraphAdapterDemo.java
