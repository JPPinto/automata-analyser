package Automata;

import java.util.Set;
import java.util.TreeSet;

public class AdvancedState {
    private Set<String> names;
    private boolean acceptanceState;
    private boolean initialState;

    AdvancedState(){
        names = new TreeSet<>();
        acceptanceState = false;
        initialState = false;
    }

    public void addState(Vertex in){
        if (in.isAcceptanceState()) {
            acceptanceState = true;
        }

        names.add(in.getName());
    }

    public Set<String> getNames(){
        return names;
    }

    public void addName(String name){
        names.add(name);
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

        AdvancedState compare = (AdvancedState) inputO;

        if (!names.equals(compare.getNames())){
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

    String createSimpleName(){
        String simpleName = "";
        for (String name : names){
            simpleName = simpleName + name + ";";
        }
        return simpleName;
    }
    Vertex convertToVertex(){
        return new Vertex(createSimpleName(), acceptanceState, initialState);
    }
}
