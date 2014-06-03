package Automata;

import java.util.*;

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

        /* Alphabet get */
        ArrayList<String> alphabet = copy.getAutomatonAlphabet();
        // Name + Vertex
        HashMap<String, Vertex> originalStates = copy.getVertexes();
        ArrayList<Edge> originalEdges = copy.getEdges();

        /* Generate All possible state combinations */
        ArrayList<AdvancedState> stateCombinations = generateStateCombinations(originalStates);

        /* Go trough all new states */
        for (int sc = 0; sc < stateCombinations.size(); sc++){
            AdvancedState currentState = stateCombinations.get(sc);
            Set<String> oldStates = currentState.getNames();

            /* Check all possible transitions */
            for (int i = 0; i < alphabet.size(); i++){
                String currentTransition = alphabet.get(i);

                /* Iterate composite state */
                for (String oldStateName : oldStates){

                    /* From the original edges */
                    for (int j = 0; j < originalEdges.size(); j++){

                        /* Add transition here */
                        if (originalEdges.get(j).getSymbol().equals(currentTransition) && originalEdges.get(j).getSource().equals(oldStateName)){
                            // State matches
                            
                        }

                    }
                }



            }

        }



        ArrayList<Edge> resultEdges = new ArrayList<Edge>();
        HashMap<String, Vertex> resultVertexes = new HashMap<String, Vertex>();

        Automata result = new Automata(resultEdges, resultVertexes);

        result.cleanUpDeadStates();

        return result;
    }

    static private ArrayList<AdvancedState> generateStateCombinations(HashMap<String, Vertex> originalStates){
        ArrayList<AdvancedState> result = new ArrayList<>();

        /* Get vertexes power set */
        OrderedPowerSet<Vertex> powerSet = new OrderedPowerSet<Vertex>(new ArrayList<Vertex>(originalStates.values()));
        List<LinkedHashSet<Vertex>> permutations = powerSet.getPermutationsList(originalStates.size());

        /* Convert power set into advanced states */
        for (int i=0; i < permutations.size(); i++) {
            AdvancedState insert = new AdvancedState();

            LinkedHashSet<Vertex> line = permutations.get(i);
            Iterator it = line.iterator();

            while (it.hasNext()){
                Vertex temp = (Vertex) it.next();

                insert.addState(temp);
            }

            result.add(insert);
        }

        return result;
    }


}
