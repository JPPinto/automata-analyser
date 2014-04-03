package com.Compiladores;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.*;

public class Automata {

	private ListenableGraph g;
	private static int edge_count = 0;

	public Automata(){
		g = new ListenableDirectedWeightedGraph(DefaultEdge.class);
	}

	public void addVertex(String vertex_name){
		g.addVertex(vertex_name);
	}

	public void addEdge(String vertex_1, String vertex_2, String edge_simbol){
		Edge edge = new Edge(edge_count, edge_simbol);
		g.addEdge(vertex_1, vertex_2, edge);
	}

	public static void main(String[] args) {
	}
}
