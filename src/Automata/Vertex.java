package com.Compiladores;

/**
 * Created by Jose on 09/04/2014.
 */
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
