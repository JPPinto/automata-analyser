package Automata;

import org.jgraph.graph.DefaultEdge;

import java.util.Objects;

/**
 * Created by Jose on 03/04/2014.
 */

public class Edge extends DefaultEdge {

	private String symbol;
<<<<<<< HEAD
	private String source;
	private String destination;

	public Edge(String sym, String src, String dst){
		symbol = sym;
		source = src;
		destination = dst;
	}

	public String getSimbol() {
		return symbol;
	}

	public void setSimbol(String simbol) {
		this.symbol = simbol;
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
=======
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
>>>>>>> 17442f1b6b9984c0e7de4c1a235bd6290213fe39
	}
}
