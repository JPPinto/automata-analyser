package Automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AutomataConverter {

    static Automata convertNFAtoDFA(Automata originalAutomaton){
        Automata copy = originalAutomaton.getCopy();

        /* If the input automata is already an DFA no need to do anything to it */
        if (copy.isDFA()){
            return copy;
        }

        /* Get starting vertex */
        Vertex startState = copy.getStartState();
        Set<Vertex> destinations;




        ArrayList<Edge> resultEdges = new ArrayList<Edge>();
        HashMap<String, Vertex> resultVertexes = new HashMap<String, Vertex>();

        return new Automata(resultEdges, resultVertexes);
    }
}
