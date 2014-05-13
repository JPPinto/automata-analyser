/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * Edge class
 */

package Automata;

import org.jgraph.graph.DefaultEdge;

public class Edge extends DefaultEdge {
	private String symbol;

	private String source;
	private String destination;

    private int number;

    public Edge(String sym, String src, String dst){
		symbol = sym;
		source = src;
		destination = dst;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Edge(int n, String sym){
		number = n;
		symbol = sym;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
