/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * Vertex class (Transition)
 */

package Automata;

public class Vertex {
	private String name;
	private boolean acceptanceState;
	private boolean initialState;

	Vertex(String n, boolean f, boolean i){
		name = n;
		acceptanceState = f;
		initialState = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAcceptanceState() {
		return acceptanceState;
	}

	public void setAcceptanceState(boolean acceptanceState) {
		this.acceptanceState = acceptanceState;
	}

	public boolean isInitialState() {
		return initialState;
	}

	public void setInitialState(boolean initialS) {
		this.initialState = initialS;
	}

    public boolean equals(Vertex compare){
        if (!name.equals(compare.getName())){
            return false;
        }

        if (acceptanceState != compare.isAcceptanceState()){
            return false;
        }

        if (initialState != compare.isInitialState()) {
            return false;
        }

        return true;
    }

    public Vertex getCopy(){
        return new Vertex(name, acceptanceState, initialState);
    }
}
