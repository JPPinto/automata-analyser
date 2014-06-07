package Automata;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class AdvancedState {
    private SortedSet<String> names;
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

    public String getFirstName(){
        Iterator iter = names.iterator();
        String first = (String) iter.next();
        return first;
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

        if (!(inputO instanceof AdvancedState)){
            return false;
        }

        AdvancedState compare = (AdvancedState) inputO;

        if (!((names.containsAll(compare.getNames()) && (compare.getNames().containsAll(names))))){
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
        boolean firstRun = true;
        for (String name : names){
            if (firstRun){
                simpleName = simpleName + name;
                firstRun = false;
            } else {
                simpleName = simpleName + "_" + name;
            }
        }
        return simpleName;
    }

    Vertex convertToVertex(){
        return new Vertex(createSimpleName(), acceptanceState, initialState);
    }

    public static AdvancedState mergeStates(AdvancedState st1, AdvancedState st2){
        if (st1.equals(st2)) {
            return st1;
        }

        AdvancedState out = new AdvancedState();

        for (String name : st1.names){
            out.addName(name);
        }

        for (String name : st2.names){
            out.addName(name);
        }

        return out;
    }
}
