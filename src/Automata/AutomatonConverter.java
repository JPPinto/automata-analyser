package Automata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AutomatonConverter {

    static Automata convertNFAtoDFA(Automata originalAutomaton){
        /* Work on an automaton copy, since we are going to modify it */
        Automata copy = originalAutomaton.getCopy();

        /* If the input automaton is already an DFA no need to do anything to it */
        if (copy.isDFA()){
            return copy;
        }

        /* Clean up any dead states */
        copy.cleanUpDeadStates();

        /* Generate All possible state copmbination */

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

        Automata result = new Automata(resultEdges, resultVertexes);

        result.cleanUpDeadStates();

        return result;
    }
}
