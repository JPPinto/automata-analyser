package Automata;

import org.jgraph.graph.DefaultEdge;

import java.util.Objects;

/**
 * Created by Jose on 03/04/2014.
 */
public class Edge extends DefaultEdge {

	private String simbol;
	private int number;

	public Edge(int n, String sim){
		number = n;
		simbol = sim;
	}

	public String getSimbol() {
		return simbol;
	}

	public void setSimbol(String simbol) {
		this.simbol = simbol;
	}
}
