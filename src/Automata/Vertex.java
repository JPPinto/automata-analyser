/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * Vertex class
 */

package Automata;

public class Vertex {
	private String name;
	private int numOfVertex;
	private boolean finalState;
	private boolean initialState;

	Vertex(String n, boolean f, boolean i){
		name = n;
		finalState = f;
		initialState = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumOfVertex() {
		return numOfVertex;
	}

	public void setnumOfVertex(int numOfVertex) {
		this.numOfVertex = numOfVertex;
	}

	public boolean isFinalState() {
		return finalState;
	}

	public void setFinalState(boolean finalState) {
		this.finalState = finalState;
	}

	public boolean isInitialState() {
		return initialState;
	}

	public void setInitialState(boolean initialS) {
		this.initialState = initialS;
	}
}
