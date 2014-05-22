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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
		jgAdapter = new JGraphModelAdapter<String, DefaultEdge>(this.g);

		JGraph jgraph = new JGraph(jgAdapter);
		adjustDisplaySettings(jgraph); // Apply the results to the actual graph

		loadGraph();

		//Put circle layout
		JGraphFacade facade = new JGraphFacade(jgraph); // Pass the facade the JGraph instance
		JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE); // Create an instance of the circle layout
		layout.run(facade); // Run the layout on the facade.
		Map nested = facade.createNestedMap(true, true); // Obtain a map of the resulting attribute changes from the facade
		jgraph.getGraphLayoutCache().edit(nested);

		add(jgraph);
	}

	private void adjustDisplaySettings(JGraph jg) {
		jg.setPreferredSize(Constants.guiDefaultWindowSize);
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


		// TODO: Clean up generics once JGraph goes generic
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
					vertex = new Vertex(entry.getValue().getName() + ", " + entry2.getValue().getName(), true, false);
				} else if (entry.getValue().isInitialState() && entry2.getValue().isInitialState()) {
					vertex = new Vertex(entry.getValue().getName() + ", " + entry2.getValue().getName(), false, true);
				} else if (entry.getValue().isInitialState() && entry.getValue().isAcceptanceState() && entry2.getValue().isInitialState() && entry2.getValue().isAcceptanceState()) {
					vertex = new Vertex(entry.getValue().getName() + ", " + entry2.getValue().getName(), true, true);
				} else {
					vertex = new Vertex(entry.getValue().getName() + ", " + entry2.getValue().getName(), false, false);
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
				if (this.edges.get(i).getSource().equals(node1) && a2.edges.get(j).getSource().equals(node2) /*&& node1.equals(node2)*/) {
					if (this.edges.get(i).getSymbol().equals(a2.edges.get(j).getSymbol())) {
						edges.add(new Edge(this.edges.get(i).getSymbol(), node1 + ", " + node2, this.edges.get(i).getDestination() + ", " + a2.edges.get(j).getDestination()));
						//System.out.println("1---- "+node1+", "+node2);
					}
				}
			}
		}

		return edges;
	}

    public Automata convertAutomatonToNFA(){
        // TODO faltam os estados de aceitacao e finais

        /* Get the automata alphabet */
        ArrayList<String> alphabet = getAutomatonAlphabet();

        /* Sanity checks */
        Vertex startState = getStartState();

        if (startState == null) {
            try {
				throw new Exception("No start state or more than one start state");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
                return null;
			}
        }

        /* Composed states */
        ArrayList<ArrayList<Vertex>> vertexesTemp= new ArrayList<>();
        /* Transitions for above states */
        ArrayList<HashMap<String, ArrayList<Vertex>>> transitionTableLines = new ArrayList<>();

        /* Do subset construction for non derived states here */
        for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
            /* For this state create a table line with all the symbols plus
             * all the possible destination vertexes for each symbol on the alphabet
             */
            ArrayList<Vertex> states = new ArrayList<>();
            vertexesTemp.add(states);
            transitionTableLines.add(getAllPossibleTransitionsFromState(entry.getValue(), alphabet));
            states.add(entry.getValue());
        }

        /* Sanity check */
        if (vertexesTemp.size() != transitionTableLines.size()) {
            //throw new Exception("Invalid intenal state on NFA to DFA converter");
            return null;
        }

        /* Search for new (derived) states */
        boolean derivedStatesSearchWorking = true;

        while (derivedStatesSearchWorking) {
            for (int i = 0; i < transitionTableLines.size(); i++) {
                HashMap<String, ArrayList<Vertex>> transtitionTableLine = transitionTableLines.get(i);

                /* Check every destination state for new states */
                for (Map.Entry<String, ArrayList<Vertex>> entry : transtitionTableLine.entrySet()) {
                    ArrayList<Vertex> currentStatus = entry.getValue();

                    boolean doesItExist = false;
                    /* Check if the state already exists */
                    for (int j = 0; j < vertexesTemp.size(); j++){
                        if (vertexesTemp.get(j) == currentStatus) {
                            doesItExist = true;
                            break;
                        }
                    }

                    if (!doesItExist){
                        vertexesTemp.add(currentStatus);
                    }
                }
            }

            /* Fill new status transition table */
            int linesToFill = vertexesTemp.size() - transitionTableLines.size();

            for (int i = transitionTableLines.size(); i < vertexesTemp.size();i++){
                transitionTableLines.add(getAllPossibleTransitionsFromStates(vertexesTemp.get(i), alphabet));
            }

            /* Check if there are new states  if not we're done */
            if (linesToFill == 0) {
                derivedStatesSearchWorking = false;
            }
        }

        /* Simplify states and create final automata */
        HashMap<String, Vertex> vertexesN = new HashMap<>();
        ArrayList<Edge> edgesN = new ArrayList<>();

        /* Create new states */
        for (Map.Entry<String, Vertex> entry : vertexes.entrySet()) {
            Vertex temp =  new Vertex("NewState" + Integer.toString(vertexesN.size()), false, false);
            vertexesN.put(temp.getName(), temp);
        }

        // TODO: Dead state management

        /* Transitions for above states */
        //ArrayList<HashMap<String, ArrayList<Vertex>>> transitionTableLines = new ArrayList<>();
        Automata finalDfa = new Automata(edgesN, vertexesN);
        finalDfa.init();
        return finalDfa;
    }

    public HashMap<String, ArrayList<Vertex>> getAllPossibleTransitionsFromState(Vertex state, ArrayList<String> alphabet) {
        // Symbol + Destination Vertex combo
        HashMap<String, ArrayList<Vertex>> destinationVertexesGroup = new HashMap<>();

        // Get all possible destinations for each alphabet entry
        for (int i=0; i < alphabet.size(); i++) {
            String searchSymbol = alphabet.get(i);
            ArrayList<Vertex> possibleDestinations = getAllPossibleStatesFromTransition(state, searchSymbol);
            destinationVertexesGroup.put(searchSymbol, possibleDestinations);
        }

        return destinationVertexesGroup;
    }

    public HashMap<String, ArrayList<Vertex>> getAllPossibleTransitionsFromStates(ArrayList<Vertex> states, ArrayList<String> alphabet) {
        // Symbol + Destination Vertex combo
        HashMap<String, ArrayList<Vertex>> destinationVertexesGroup = new HashMap<>();

        // Get all states in the list
        for (int j=0; j < states.size(); j++) {

            // Get all possible destinations for each alphabet entry
            for (int i=0; i < alphabet.size(); i++) {
                String searchSymbol = alphabet.get(i);

                ArrayList<Vertex> possibleDestinations = getAllPossibleStatesFromTransition(states.get(j), searchSymbol);
                destinationVertexesGroup.put(searchSymbol, possibleDestinations);
            }

        }

        return destinationVertexesGroup;
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
}
