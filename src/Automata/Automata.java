/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * Automata class
 */

package Automata;

import org.jgraph.JGraph;
import org.jgraph.graph.*;
import org.jgraph.graph.DefaultGraphModel.GraphModelEdit;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.*;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Automata extends JPanel {

	public ListenableDirectedGraph g;
	public static int edgeCount = 0;
	public static int vertexCount = 0;
	public HashMap<String, Vertex> vertexes;
	public ArrayList<Edge> edges;
	//private String[] acceptedAlphabet;

	public static final long serialVersionUID = 3256444702936019250L;

	public JGraphModelAdapter<String, DefaultEdge> jgAdapter;

	public Automata(String graph) {

		edges = new ArrayList<Edge>();
		vertexes = new HashMap<String, Vertex>();

		g = new ListenableDirectedGraph(org.jgraph.graph.DefaultEdge.class);
		parseDottyFile(graph);

		init();

		loadGraph();
	}

	public Automata(ArrayList<Edge> e, HashMap<String, Vertex> v) {

		edges = e;
		vertexes = v;

		g = new ListenableDirectedGraph(org.jgraph.graph.DefaultEdge.class);

		init();

		loadGraph();
	}

	public void parseDottyFile(String graph) {

		String[] lines = graph.split("\r\n");

		for (int i = 0; i < lines.length; i++) {
			parseLine(lines[i]);
		}

	}

	public boolean isSequenceAccpeted(String[] sequence) {

		return false;
	}

	public Vertex getInitialEdge() {

		for (Map.Entry<String, Vertex> entry : vertexes.entrySet())
			if (entry.getValue().isInitialState())
				return entry.getValue();

		return null;
	}

	public void parseLine(String s) {

		if (Pattern
				.compile("[a-zA-Z][a-zA-Z0-9]*->[a-zA-Z][a-zA-Z0-9]*\\[.*\\]")
				.matcher(s).matches()) {
			String[] content = s.split("->");
			String[] label_sides = content[1].substring(0,
					content[1].length() - 1).split("=");

			String[] sides = content[1].split("\\[");

			Vertex tempVertex = new Vertex(sides[0], false, false);
			if (!vertexes.containsKey(sides[0]))
				vertexes.put(sides[0], tempVertex);

			tempVertex = new Vertex(content[0], false, false);
			if (!vertexes.containsKey(content[0]))
				vertexes.put(content[0], tempVertex);

			Edge tempEdge = new Edge(label_sides[1], content[0], sides[0]);
			edges.add(tempEdge);

			return;
		}

		if (Pattern.compile("\\\"\\\"->[a-zA-Z][a-zA-Z0-9]*").matcher(s)
				.matches()) {
			String[] content = s.split("->");
			String right_content = content[1].substring(0, content[1].length());

			Vertex tempVertex = new Vertex(right_content, false, true);
			if (!vertexes.containsKey(right_content))
				vertexes.put(right_content, tempVertex);
			else {
				vertexes.remove(right_content);
				tempVertex = new Vertex(right_content, true, true);
				vertexes.put(right_content, tempVertex);
			}

			return;
		}

		if (Pattern.compile(".*\\[.*\\]").matcher(s).matches()) {
			String[] content = s.split("\\[");
			String shape = content[1].substring(0, content[1].length() - 1);

			if (shape.equals(Constants.dottyAcceptanceState)) {
				Vertex tempVertex = new Vertex(content[0], true, false);
				if (!vertexes.containsKey(content[0]))
					vertexes.put(content[0], tempVertex);
			}

			return;
		}
	}

	public void loadGraph() {

		for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
			Vertex tempVertex = entry.getValue();

			g.addVertex(tempVertex.getName());

			editVertex(tempVertex);
		}

		for (int i = 0; i < edges.size(); i++) {
			g.addEdge(edges.get(i).getSource(), edges.get(i).getDestination(),
					edges.get(i).getSymbol());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void init() {
		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(this.g);

		JGraph jgraph = new JGraph(jgAdapter);

		JGraphFacade facade = new JGraphFacade(jgraph); // Pass the facade the JGraph instance
		JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE); // Create an instance of the circle layout
		layout.run(facade); // Run the layout on the facade.
		Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
		// jgraph.getGraphLayoutCache().edit(nested);
		adjustDisplaySettings(jgraph); // Apply the results to the actual graph
		add(jgraph);
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(Constants.guiDefaultWindowSize);
		jg.setBackground(Constants.guiDefaultBackgroundColor);
	}

	private void positionVertexAt(Object vertex, int x, int y) {
		DefaultGraphCell cell = jgAdapter.getVertexCell(vertex);
		AttributeMap attr = cell.getAttributes();
		Rectangle2D bounds = GraphConstants.getBounds(attr);

		/*
		 * Rectangle2D newBounds = new Rectangle2D.Double( x, y,
		 * bounds.getWidth(), bounds.getHeight());
		 */

		// GraphConstants.setBounds(attr, newBounds);
		GraphConstants.setBackground(attr, Color.green.darker());
		GraphConstants.setEditable(attr, false);
		GraphConstants.setBorderColor(attr, Constants.guiDefaultStateColor);
		Border borderAutomata = BorderFactory.createLineBorder(Constants.guiDefaultStateBorderColor, 2);
		GraphConstants.setBorder(attr, borderAutomata);

		// GraphConstants.setBorderColor(attr, Color.ORANGE);
		// Border borderAutomata = BorderFactory.createLineBorder(new
		// Color(247,150,70), 2);
		// GraphConstants.setBorder(attr, borderAutomata);
		// GraphConstants.setOpaque(attr, false);


		// TODO: Clean up generics once JGraph goes generic
		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	private void editVertex(Vertex x) {
		DefaultGraphCell cell = jgAdapter.getVertexCell(x.getName());
		AttributeMap attr = cell.getAttributes();

		if ( x.isInitialState()) {
			GraphConstants.setBackground(attr, Color.green);
		}
		if (x.isAcceptanceState()) {
			Border vertexBorder = BorderFactory.createLineBorder(Constants.guiAccepetanceStateBorderColor, 4); // Blue color from icon
			GraphConstants.setBorder(attr, vertexBorder);
		}

		GraphConstants.setEditable(attr, false);

		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	public boolean equivalentTo(Automata otherAutomata) {

		return true;
	}

    public Automata getCopy(){
        HashMap<String, Vertex> tempVertexes = new HashMap<>(vertexes);
        ArrayList<Edge> tempEdges = new ArrayList<>(edges);

        for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
            tempVertexes.put(entry.getKey(),entry.getValue().getCopy());
        }

        for (int i = 0; i < edges.size(); i++) {
            tempEdges.add(i, edges.get(i).getCopy());
        }

        Automata automata = new Automata(tempEdges, tempVertexes);

        return automata;
    }

	public Automata getComplement() {
        Automata automata = getCopy();

		for (Map.Entry<String, Vertex> entry : automata.getVertexes().entrySet()) {
			entry.getValue().setAcceptanceState(!entry.getValue().isAcceptanceState());
		}

		return automata;
	}

    public HashMap<String, Vertex> getVertexes(){
        return vertexes;
    }
	
	public Automata getCartesianProduct(Automata a) {
        Automata newAutomata = getCopy();

		for (Map.Entry<String, Vertex> entry : newAutomata.getVertexes().entrySet()) {
			entry.getValue().setAcceptanceState(!entry.getValue().isAcceptanceState());
		}

		return newAutomata;
	}
	
	public String convertToDotty(){
		String total="digraph{\n\"\"[shape=none]\r\n";
		String temp="";
		
		for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
			if (entry.getValue().isAcceptanceState()) {
				total+=entry.getValue().getName()+"[shape=doublecircle]\r\n";
			}
			if (entry.getValue().isInitialState()) {
				temp=entry.getValue().getName();
			}
		}
		total+="\"\"->"+temp+"\r\n";

		for (int i = 0; i < edges.size(); i++) {
			total+=edges.get(i).getSource()+"->"+edges.get(i).getDestination()+"[label="+edges.get(i).getSymbol()+"]\r\n";
		}
		total+="}";

		return total;
	}
}
