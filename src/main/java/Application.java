

import Parsing.Link;
import Parsing.Node;
import Parsing.Parser;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

/**
 * Created by Michael on 4/28/2017.
 */

public class Application extends JApplet{
    private static final long serialVersionUID = 3256444702936019250L;
    private static final Color DEFAULT_BG_COLOR = Color.decode("#AD915E");
    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    //~ Instance fields --------------------------------------------------------

    //
    private JGraphModelAdapter jgAdapter;

    //~ Methods ----------------------------------------------------------------

    /**
     * An alternative starting point for this demo, to also allow running this
     * applet as an application.
     *
     * @param args ignored.
     */
    public static void main(String [] args)
    {
        Application applet = new Application();
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);

    }

    /**
     * {@inheritDoc}
     */
    public void init()
    {

        Parser parser = Parser.getInstance();

        // create a JGraphT graph
        DefaultDirectedGraph<Node, Link> g = parser.parse("/test.gfa");
        System.out.println(g.toString());

        // create a visualization using JGraph, via an adapter
        jgAdapter = new JGraphModelAdapter<Node, Link>(g);

        JGraph jgraph = new JGraph(jgAdapter);

        adjustDisplaySettings(jgraph);
        getContentPane().add(jgraph);
        resize(DEFAULT_SIZE);



//        // position vertices nicely within JGraph component
//        positionVertexAt(v1, 130, 40);
//        positionVertexAt(v2, 60, 200);
//        positionVertexAt(v3, 310, 230);
//        positionVertexAt(v4, 380, 70);

        // that's all there is to it!...
    }

    private void adjustDisplaySettings(JGraph jg)
    {
        jg.setPreferredSize(DEFAULT_SIZE);

        Color c = DEFAULT_BG_COLOR;
        String colorStr = null;

        try {
            colorStr = getParameter("bgcolor");
        } catch (Exception e) {
        }

        if (colorStr != null) {
            c = Color.decode(colorStr);
        }

        jg.setBackground(c);
    }

    private void positionVertexAt(Object vertex, int x, int y)
    {
        DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
        AttributeMap attr = cell.getAttributes();
        Rectangle2D bounds = GraphConstants.getBounds(attr);

        Rectangle2D newBounds =
                new Rectangle2D.Double(
                        x,
                        y,
                        bounds.getWidth(),
                        bounds.getHeight());

        GraphConstants.setBounds(attr, newBounds);

        AttributeMap cellAttr = new AttributeMap();
        cellAttr.put(cell, attr);
        jgAdapter.edit(cellAttr, null, null, null);
    }

}
