package Automata;


import java.util.ArrayList;

public class AdvancedTranstition {
    private String transitionSymbol;
    private AdvancedState sourceState;
    private ArrayList<String> destinations;

    AdvancedTranstition(String symbol, AdvancedState source){
        transitionSymbol = symbol;
        sourceState = source;

    }

}
