package com.Compiladores;

/**
 * Created by Jose on 09/04/2014.
 */
public class Vertex {

	private String name;
	private int num_of_Vertex;
	private boolean final_state;
	private boolean inicial_state;

	Vertex(String n, boolean f, boolean i, int num){
		name = n;
		num_of_Vertex = num;
		final_state = f;
		inicial_state = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum_of_Vertex() {
		return num_of_Vertex;
	}

	public void setNum_of_Vertex(int num_of_Vertex) {
		this.num_of_Vertex = num_of_Vertex;
	}

	public boolean isFinal_state() {
		return final_state;
	}

	public void setFinal_state(boolean final_state) {
		this.final_state = final_state;
	}

	public boolean isInicial_state() {
		return inicial_state;
	}

	public void setInicial_state(boolean inicial_state) {
		this.inicial_state = inicial_state;
	}
}
