package Automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AutomataConverter {

    static Automata convertNFAtoDFA(Automata originalAutomaton){
        /* Work on an automaton copy, in case something goes wrong */
        Automata copy = originalAutomaton.getCopy();

        /* If the input automata is already an DFA no need to do anything to it */
        if (copy.isDFA()){
            return copy;
        }

        /* Alphabet get */
        ArrayList<String> alphabet = copy.getAutomatonAlphabet();
        // Name + Vertex
        HashMap<String, Vertex> states = copy.getVertexes();

        /* Get starting vertex */
        Vertex startState = copy.getStartState();

        for (int i=0; i < alphabet.size(); i++){
            //
        }






        ArrayList<Edge> resultEdges = new ArrayList<Edge>();
        HashMap<String, Vertex> resultVertexes = new HashMap<String, Vertex>();

        return new Automata(resultEdges, resultVertexes);
    }
}
