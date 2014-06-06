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

    public AdvancedState getSourceState(){
        return sourceState;
    }

    public AdvancedState getDestinationState(){
        return destinationState;
    }

    public void setSourceState(AdvancedState in){
        sourceState = in;
    }

    @Override
    public boolean equals(Object inputO){
        if (this == inputO){
            return true;
        }

        if (!(inputO instanceof AdvancedTransition)){
            return false;
        }

        AdvancedTransition temp = (AdvancedTransition) inputO;

        if (!transitionSymbol.equals(temp.transitionSymbol)){
            return false;
        }

        if (sourceState != temp.getSourceState()) {
            return false;
        }

        if (destinationState != temp.getDestinationState()){
            return false;
        }
        return  true;
    }

}
