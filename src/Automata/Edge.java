package Automata;

import org.jgraph.graph.DefaultEdge;

import java.util.Objects;

/**
 * Created by Jose on 03/04/2014.
 */
public class Edge extends DefaultEdge {

	private String symbol;
	private int number;

	public Edge(int n, String sim){
		number = n;
		symbol = sim;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
