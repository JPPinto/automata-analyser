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

public class Vertex  {
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

    @Override
    public boolean equals(Object inputO){
        if (this == inputO){
            return true;
        }

        if (!(inputO instanceof Vertex)){
            return false;
        }

        Vertex compare = (Vertex) inputO;

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

    @Override
    public String toString(){
        String result = name + " ";
        if (initialState){
            result += "I";
        }
        if (acceptanceState){
            result += "A";
        }

        return result;
    }

}
