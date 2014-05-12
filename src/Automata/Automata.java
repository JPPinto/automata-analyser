package Automata;

import org.jgraph.JGraph;
import org.jgraph.graph.*;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.demo.JGraphAdapterDemo;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Automata extends JFrame {

	public ListenableDirectedGraph g;
	public static int edge_count = 0;
	public static int vertex_count = 0;
	public static final int MAX_SIZE = 2048;
	public HashMap<String, Vertex> vertexs;
	public ArrayList<Edge> edges;
	//private String[] accepted_alphabeth;

	public static final long serialVersionUID = 3256444702936019250L;
	public static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	public static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

	public JGraphModelAdapter<String, DefaultEdge> jgAdapter;

	public Automata(File file) {

		edges = new ArrayList<Edge>();
		vertexs = new HashMap<String, Vertex>();

		g = new ListenableDirectedGraph(org.jgraph.graph.DefaultEdge.class);
		parseDottyfile(file);

		init();
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setTitle("Auto Analyze");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		loadGraph();
	}

	public void parseDottyfile(File file) {
		String graph = "";
		try {
			FileReader fr = new FileReader(file);

			char[] content = new char[MAX_SIZE];
			fr.read(content);

			graph = new String(content);

		} catch (FileNotFoundException e) {
			System.out.println("File " + file.getName() + "not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] lines = graph.split("\r\n");

		for (int i = 0; i < lines.length; i++)
			parseLine(lines[i]);

	}

	public boolean isSequenceAccpeted(String[] sequence) {

		return false;
	}

	public Edge getInitialEdge() {

		for (Edge e : edges) {
			if (e.getSource().equals(""))
				return e;
		}

		return null;
	}

	public void parseLine(String s) {

		if (Pattern.compile("[a-zA-Z][a-zA-Z0-9]*->[a-zA-Z][a-zA-Z0-9]*\\[.*\\]").matcher(s).matches()) {
			String[] content = s.split("->");
			String[] label_sides = content[1].substring(0, content[1].length() - 1).split("=");

			String[] sides = content[1].split("\\[");

			//if(!g.containsVertex(sides[0]))
			//	addVertex(sides[0], false, false);

			Vertex tempVertex = new Vertex(sides[0], false, false);
			if (!vertexs.containsKey(sides[0]))
				vertexs.put(sides[0],tempVertex);

			tempVertex = new Vertex(content[0], false, false);
			if (!vertexs.containsKey(content[0]))
				vertexs.put(content[0],tempVertex);

			//if(!g.containsVertex(content[0]))
			//	addVertex(content[0], false, false);

			//if(!g.containsEdge(new Edge(label_sides[1], content[0], sides[0])))
			//	addEdge(content[0], sides[0], label_sides[1]);

			Edge tempEdge = new Edge(label_sides[1], content[0], sides[0]);
			edges.add(tempEdge);

			return;
		}

		if (Pattern.compile("\\\"\\\"->[a-zA-Z][a-zA-Z0-9]*").matcher(s).matches()) {
			String[] content = s.split("->");
			String right_content = content[1].substring(0, content[1].length());

			Vertex tempVertex = new Vertex(right_content, false, true);
			if (!vertexs.containsKey(right_content))
				vertexs.put(right_content,tempVertex);

			return;
		}

		if (Pattern.compile(".*\\[.*\\]").matcher(s).matches()) {
			String[] content = s.split("\\[");
			String shape = content[1].substring(0, content[1].length() - 1);

			if (shape.equals("shape=doublecircle")) {
				Vertex tempVertex = new Vertex(content[0], true, false);
				if (!vertexs.containsKey(content[0]))
					vertexs.put(content[0],tempVertex);
			}

			return;
		}
	}

	public void loadGraph(){

		for (Map.Entry<String, Vertex> entry : vertexs.entrySet()) {
			g.addVertex(entry.getValue().getName());
			//positionVertexAt(entry.getValue().getName(), 130, 40);
		}

		for (int i = 0; i < edges.size(); i++){
			g.addEdge(edges.get(i).getSource(), edges.get(i).getDestination(), edges.get(i).getSimbol());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void init()
	{
		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(this.g);

		JGraph jgraph = new JGraph(jgAdapter);

		adjustDisplaySettings(jgraph);
		getContentPane().add(jgraph);

		/*String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";

		// add some sample data (graph manipulated via JGraphT)
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);

		g.addEdge(v1, v2);
		g.addEdge(v2, v3);
		g.addEdge(v3, v1);
		g.addEdge(v4, v3);

		// position vertices nicely within JGraph component
		positionVertexAt(v1, 130, 40);
		positionVertexAt(v2, 60, 200);
		positionVertexAt(v3, 310, 230);
		positionVertexAt(v4, 380, 70);*/
	}

	private void adjustDisplaySettings(JGraph jg)
	{
		jg.setPreferredSize(DEFAULT_SIZE);

		Color c = DEFAULT_BG_COLOR;

		jg.setBackground(c);
	}

	@SuppressWarnings("unchecked") // FIXME hb 28-nov-05: See FIXME below
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

		// TODO: Clean up generics once JGraph goes generic
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	private static class ListenableDirectedMultigraph<V, E>
			extends DefaultListenableGraph<V, E>
			implements DirectedGraph<V, E>
	{
		private static final long serialVersionUID = 1L;

		ListenableDirectedMultigraph(Class<E> edgeClass)
		{
			super(new DirectedMultigraph<V, E>(edgeClass));
		}
	}

	public static void main(String[] args) {
<<<<<<< HEAD
		Automata automata = new Automata(new File("./graph1.gv"));
		System.out.println();
=======

>>>>>>> 17442f1b6b9984c0e7de4c1a235bd6290213fe39
	}
}
