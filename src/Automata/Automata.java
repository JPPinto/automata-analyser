/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * Joao Correia      (201208114)
 * Jose Pinto        (201203811)
 *
 * Automata class
 */

package Automata;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;

//import dk.brics.automaton.Automaton;
//import dk.brics.automaton.State;
import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.ListenableDirectedGraph;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.regex.Pattern;

public class Automata extends JPanel {

	public ListenableDirectedGraph g;
	public HashMap<String, Vertex> vertexes;
	public ArrayList<Edge> edges;
	public boolean isAutomataDFA;

	public static final long serialVersionUID = 3256444702936019250L;

	public JGraphModelAdapter<String, DefaultEdge> jgAdapter;

	public Automata(String graph) {

		edges = new ArrayList<Edge>();
		vertexes = new HashMap<String, Vertex>();

		g = new ListenableDirectedGraph(org.jgraph.graph.DefaultEdge.class);
		parseDottyFile(graph);

		isAutomataDFA = isDFA();

		init();
	}

	public Automata(ArrayList<Edge> e, HashMap<String, Vertex> v) {
		edges = e;
		vertexes = v;

		g = new ListenableDirectedGraph(org.jgraph.graph.DefaultEdge.class);
	}

	public Automata() {
		vertexes = new HashMap<>();
		edges = new ArrayList<>();

		g = new ListenableDirectedGraph(org.jgraph.graph.DefaultEdge.class);
	}

	public void parseDottyFile(String graph) {

		String[] lines = graph.split("\r\n");

		for (int i = 0; i < lines.length; i++) {
			parseLine(lines[i]);
		}

	}

	public Vertex getInitialVertex() {

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
			String[] label_sides = content[1].substring(0, content[1].length() - 1).split("=");

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
			g.addEdge(edges.get(i).getSource(), edges.get(i).getDestination(), edges.get(i).getSymbol());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void init() {
		// create a visualization using JGraph, via an adapter
		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(g);

		JGraph jgraph = new JGraph(jgAdapter);
		this.setPreferredSize(Constants.guiDefaultWindowSize);
		adjustDisplaySettings(jgraph); // Apply the results to the actual graph

		loadGraph();

		//Put circle layout
		JGraphFacade facade = new JGraphFacade(jgraph); // Pass the facade the JGraph instance
		JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE); // Create an instance of the circle layout
		layout.run(facade); // Run the layout on the facade.
		Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
		//GraphConstants.setLabelAlongEdge(nested, true);
		jgraph.getGraphLayoutCache().edit(nested);

		setPreferredSize(Constants.guiDefaultWindowSize);
		JScrollPane scroll = new JScrollPane(jgraph);
		scroll.setPreferredSize(Constants.guiDefaultWindowSize);
		add(scroll);
		setVisible(true);
		//add(jgraph);
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setMinimumSize(Constants.guiDefaultWindowSize);
		jg.setMaximumSize(Constants.guiDefaultWindowSize);
		//jg.setPreferredSize(Constants.guiDefaultWindowSize);
		//jg.setSize(Constants.guiDefaultWindowSize);
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

		AttributeMap cellAttr = new AttributeMap();
		cellAttr.put(cell, attr);
		jgAdapter.edit(cellAttr, null, null, null);
	}

	private void editVertex(Vertex x) {
		DefaultGraphCell cell = jgAdapter.getVertexCell(x.getName());
		AttributeMap attr = cell.getAttributes();

		if (x.isInitialState()) {
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

	public Automata getCopy() {
		HashMap<String, Vertex> tempVertexes = new HashMap<>(vertexes);
		ArrayList<Edge> tempEdges = new ArrayList<>();

		for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
			tempVertexes.put(entry.getKey(), entry.getValue().getCopy());
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
		automata.refresh();
		return automata;
	}

	private void refresh() {
		removeAll();
		init();
	}

	public HashMap<String, Vertex> getVertexes() {
		return vertexes;
	}

	public boolean isDFA() {

		Edge tempEdge;
		for (int j = 0; j < edges.size(); j++) {

			tempEdge = edges.get(j);

			if (tempEdge.getSymbol() == "?!")
				return false;

			for (int i = 0; i < edges.size(); i++) {

				if (tempEdge.getSymbol().equals(edges.get(i).getSymbol())
						&& !tempEdge.getDestination().equals(edges.get(i).getDestination())
						&& tempEdge.getSource().equals(edges.get(i).getSource()))
					return false;
			}
		}
		return true;
	}

	public boolean acceptsSequence(String sequence, Vertex currentVertex) {

		if(sequence.equals("") && currentVertex.isAcceptanceState())
			return true;
		
		boolean accepted = false;
		Edge tempEdge;
		String exceptFirstChar = "";
		String firstChar = "";
		Vertex nextVertex = null;

		for (int i = 0; i < edges.size(); i++) {
			tempEdge = edges.get(i);
			firstChar = sequence.substring(0, 1);
			
			if (tempEdge.getSource().equals(currentVertex.getName()) && tempEdge.getSymbol().equals(firstChar)) {
				nextVertex = vertexes.get(tempEdge.getDestination());
				if (sequence.length() == 1){
					if(!nextVertex.isAcceptanceState())
						return false;
					return true;
				}
				exceptFirstChar = sequence.substring(1, sequence.length());
				accepted = acceptsSequence(exceptFirstChar, nextVertex);
				
				if(accepted) break;
			}
		}
		return accepted;
	}

	
	
	public Edge getInitialEdge() {
		
		Vertex tempVertex = null;
		
		for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
			if (entry.getValue().isInitialState()) {
				tempVertex = entry.getValue();
				break;
			}
		}
		
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getSource().equals(tempVertex.getName()))
				return edges.get(i);
		}
		
		return null;
	}

	public String convertToDotty() {
		String total = "digraph{\n\"\"[shape=none]\r\n";
		String temp = "";

		for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
			if (entry.getValue().isAcceptanceState()) {
				total += entry.getValue().getName() + "[shape=doublecircle]\r\n";
			}
			if (entry.getValue().isInitialState()) {
				temp = entry.getValue().getName();
			}
		}
		total += "\"\"->" + temp + "\r\n";

		for (int i = 0; i < edges.size(); i++) {
			total += edges.get(i).getSource() + "->" + edges.get(i).getDestination() + "[label=" + edges.get(i).getSymbol() + "]\r\n";
		}
		total += "}";

		return total;
	}

	public Automata getCartesianProduct(Automata a) {
		Automata newAutomata = new Automata();
		Vertex vertex = null;

		//TODO So funciona para grafos com a mesma linguagem
		for (Map.Entry<String, Vertex> entry : this.getVertexes().entrySet()) {
			for (Map.Entry<String, Vertex> entry2 : a.getVertexes().entrySet()) {
				if (entry.getValue().isAcceptanceState() && entry2.getValue().isAcceptanceState()) {
					vertex = new Vertex(entry.getValue().getName() + "_" + entry2.getValue().getName(), true, false);
				} else if (entry.getValue().isInitialState() && entry2.getValue().isInitialState()) {
					vertex = new Vertex(entry.getValue().getName() + "_" + entry2.getValue().getName(), false, true);
				} else if (entry.getValue().isInitialState() && entry.getValue().isAcceptanceState() && entry2.getValue().isInitialState() && entry2.getValue().isAcceptanceState()) {
					vertex = new Vertex(entry.getValue().getName() + "_" + entry2.getValue().getName(), true, true);
				} else {
					vertex = new Vertex(entry.getValue().getName() + "_" + entry2.getValue().getName(), false, false);
				}
				newAutomata.vertexes.put(vertex.getName(), vertex);
				newAutomata.edges.addAll(getCartesianProductEdges(a, entry.getValue().getName(), entry2.getValue().getName()));
			}
		}

		newAutomata.refresh();

		return newAutomata;
	}

	public ArrayList<Edge> getCartesianProductEdges(Automata a2, String node1, String node2) {
		ArrayList<Edge> edges = new ArrayList<Edge>();

		for (int i = 0; i < this.edges.size(); i++) {
			for (int j = 0; j < a2.edges.size(); j++) {
				if (this.edges.get(i).getSource().equals(node1) && a2.edges.get(j).getSource().equals(node2)) {
					if (this.edges.get(i).getSymbol().equals(a2.edges.get(j).getSymbol())) {
						edges.add(new Edge(this.edges.get(i).getSymbol(), node1 + "_" + node2, this.edges.get(i).getDestination() + "_" + a2.edges.get(j).getDestination()));
						//System.out.println("1---- "+node1+", "+node2);
					}
				}
			}
		}

		return edges;
	}

    public ArrayList<Vertex> getAllPossibleStatesFromTransition(Vertex state, String symbol){
        ArrayList<Vertex> possibleDestinations = new ArrayList<>();

        for (int i = 0; i < edges.size(); i++) {
            if (state.getName().equals(edges.get(i).getSource()) && edges.get(i).getSymbol().equals(symbol)) {
                possibleDestinations.add(vertexes.get(edges.get(i).getDestination()));
            }
        }

        return possibleDestinations;
    }

    public Vertex getStartState(){
        int numberOfHits = 0;
        Vertex startState = null;

        for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {

            if (entry.getValue().isInitialState()) {
                numberOfHits++;
                startState = entry.getValue();
            }
        }

        if (numberOfHits != 1) {
            return null;
        }

        return startState;
    }

    /*
    * Returns an ArrayList of strings with all the unique symbols of the automaton
    */
    public ArrayList<String> getAutomatonAlphabet(){
        ArrayList<String> alphabet = new ArrayList<>();
        boolean addToAlphabet;

        /* Check all edges (transitions) */
        for (int i = 0; i < edges.size(); i++) {
            addToAlphabet = true;

            /* Check if symbol already exists on the alphabet */
            for(int j = 0; j < alphabet.size(); j++) {
                if (edges.get(i).getSymbol().equals(alphabet.get(j))){
                    addToAlphabet = false;
                    break;
                }
            }

            /* Add it if it doesn't already exist */
            if (addToAlphabet) {
                alphabet.add(edges.get(i).getSymbol());
            }
        }

        return alphabet;
    }

	/*public Automata toAutomata(Automaton automaton){

		ArrayList<Edge> tempEdges = new ArrayList<>();
		HashMap<String, Vertex> tempVertexes= new HashMap<>();

		Set<State> automatonStates = automaton.getStates();

		//TODO ADD all vertexes and create all the edges

		//return new Automata(e,v);
		return null;
	}*/

	public boolean compareRE(String re){

		/*dk.brics.automaton.RegExp regular_Ex = new dk.brics.automaton.RegExp(re);

		Automaton temp_automaton = new Automaton();
		temp_automaton = regular_Ex.toAutomaton();*/

		return false;
	}

	public static void main(String[] args) {

		Automata temp_automata = new Automata();

		temp_automata.compareRE("(101*)*");
	}

    public void cleanUpDeadStates() {
        boolean stateDeleted = true;
        while (stateDeleted) {
        for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
            stateDeleted = false;
            /* Don't remove the start state ever, since it might be the only state and it is the start state */
            if (entry != getStartState()) {

                boolean deleteState = true;

                for (int i = 0; i < edges.size(); i++) {
                /* If the current state is a destination or a source state keep it */
                    if (edges.get(i).getDestination().equals(entry.getKey()) || edges.get(i).getSource().equals(entry.getKey())){
                        deleteState = false;
                        break;
                    }
                }

                /* No reference found this state, remove it */
                if (deleteState){

                    for (Edge edge : edges){
                        if(edge.getSource().equals(entry.getValue().getName())){
                            edges.remove(edge);
                        }
                    }

                    vertexes.remove(entry);
                    stateDeleted = true;
                }
            }

        }
        }
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }
}
