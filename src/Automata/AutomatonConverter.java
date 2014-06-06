package Automata;

import java.util.*;

public class AutomatonConverter {

    public static Automata convertNFAtoDFA(Automata originalAutomaton) {
        // TODO epsilon transitions
        /* Work on an automaton copy, since we are going to modify it */
        Automata copy = originalAutomaton.getCopy();

        /* If the input automaton is already an DFA no need to do anything to it */
        if (copy.isDFA()) {
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

        ArrayList<AdvancedTransition> advancedTransitions = new ArrayList<>();

        /* Go trough all new states */
        for (AdvancedState currentState : stateCombinations) {
            Set<String> oldStates = currentState.getNames();

            /* Check all possible transitions */
            for (String currentTransition : alphabet) {
                Set advancedStateDestinations = new TreeSet<String>();

                /* Iterate composite state */
                for (String oldStateName : oldStates) {

                    /* From the original edges */
                    for (Edge edge : originalEdges) {

                        /* Add transition here */
                        if (edge.getSymbol().equals(currentTransition) && edge.getSource().equals(oldStateName)) {
                            // State matches, save destinations
                            advancedStateDestinations.add(edge.getDestination());
                        }

                    }
                }

                // advancedStateDestinations now contains the destination advancedState name
                AdvancedTransition newTransition = new AdvancedTransition(currentTransition, currentState);

                for (int scc = 0; scc < stateCombinations.size(); scc++) {
                    if (stateCombinations.get(scc).getNames().equals(advancedStateDestinations)) {
                        newTransition.setDestination(stateCombinations.get(scc));
                        break;
                    }
                }

                advancedTransitions.add(newTransition);
            }

        }

        // Table is done

        // Convert to simple edge / vertex
        ArrayList<Edge> resultEdges = new ArrayList<Edge>();
        HashMap<String, Vertex> resultVertexes = new HashMap<String, Vertex>();

        for (AdvancedState state : stateCombinations) {
            Vertex newVertex = state.convertToVertex();
            resultVertexes.put(newVertex.getName(), newVertex);
        }

        for (AdvancedTransition transition : advancedTransitions) {
            if (transition.hasDestination()) {
                resultEdges.add(transition.convertToEdge());
            }
        }

        // Set initial state
        for (Map.Entry<String, Vertex> entry : resultVertexes.entrySet()) {
            /* Check if state is the same */
            if (entry.getValue().getName().equals(originalAutomaton.getStartState().getName())) {
                entry.getValue().setInitialState(true);
            }
        }

        Automata result = new Automata(resultEdges, resultVertexes);

        result.cleanUpDeadStates();

        result.init();

        return result;
    }

    static private ArrayList<AdvancedState> generateStateCombinations(HashMap<String, Vertex> originalStates) {
        ArrayList<AdvancedState> result = new ArrayList<>();

        /* Get vertexes power set */
        OrderedPowerSet<Vertex> powerSet = new OrderedPowerSet<Vertex>(new ArrayList<Vertex>(originalStates.values()));
        List<LinkedHashSet<Vertex>> permutations = new ArrayList<LinkedHashSet<Vertex>>();

        for (int i = 1; i <= originalStates.size(); i++) {
            permutations.addAll(powerSet.getPermutationsList(i));
        }

        /* Convert power set into advanced states */
        for (LinkedHashSet<Vertex> line : permutations) {
            AdvancedState insert = new AdvancedState();

            for (Vertex vertex : line) {
                insert.addState(vertex);
            }

            result.add(insert);
        }

        return result;
    }

    public static Automata intersection(Automata automaton1, Automata automaton2){
        /* Make sure the automatons are already dfas */
        Automata automaton1Copy = convertNFAtoDFA(automaton1);
        Automata automaton2Copy = convertNFAtoDFA(automaton2);




        // Convert to simple edge / vertex
        ArrayList<Edge> resultEdges = new ArrayList<Edge>();
        HashMap<String, Vertex> resultVertexes = new HashMap<String, Vertex>();

        Automata result = new Automata(resultEdges, resultVertexes);

        return result;
    }
}
