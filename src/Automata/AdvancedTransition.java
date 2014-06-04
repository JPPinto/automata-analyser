package Automata;


import java.util.ArrayList;

public class AdvancedTransition {
    private String transitionSymbol;
    private AdvancedState sourceState;
    private AdvancedState destinationState;

    AdvancedTransition(String symbol, AdvancedState source){
        transitionSymbol = symbol;
        sourceState = source;

    }

    void setDestination(AdvancedState destination) {
        destinationState = destination;
    }

    Edge convertToEdge(){
        System.out.print("");
        return new Edge(transitionSymbol, sourceState.createSimpleName(), destinationState.createSimpleName());
    }

    public boolean hasDestination(){
        if (destinationState != null) {
            return true;
        } else {
            return false;
        }
    }

}
