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

}
