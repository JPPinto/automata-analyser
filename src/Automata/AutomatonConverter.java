package Automata;

import java.util.*;

public class AutomatonConverter {

    public static Automata convertNFAtoDFA(Automata originalAutomaton) {
        // TODO epsilon transitions
        /* Work on an automaton copy, since we are going to modify it */
        Automata copy = originalAutomaton.getCopy();

        /* If the input automaton is already an DFA no need to do anything to it */
        if (copy.isDFA()) {
            copy.init();
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

        /* Merge states that are the same due to epsilon transitions */
        for (AdvancedState currentState : stateCombinations) {
            Set<String> oldStates = currentState.getNames();

            ArrayList<String> addNames = new ArrayList<>();
            /* Check all possible epsilon transitions */
            /* Iterate composite state */
            for (String oldStateName : oldStates) {

                /* From the original edges */
                for (Edge edge : originalEdges) {

                    /* Check for epsilon transitions */
                    if (edge.getSymbol().equals(Constants.epsilonString) && edge.getSource().equals(oldStateName)) {
                        // Epsilon transition found

                        for (Map.Entry<String, Vertex> entry : originalStates.entrySet()){
                            Vertex originalState = entry.getValue();

                            if (originalState.getName().equals(oldStateName)){

                                addNames.add(edge.getDestination());
                            }
                        }

                    }

                }
            }

            // Add names here
            for (String name : addNames){
                currentState.addName(name);
            }
        }

        // Clean up duplicated entries on the table
        ArrayList<AdvancedState> statesToRemove = new ArrayList<>();

        for (AdvancedState compareState1 : stateCombinations){

            for (AdvancedState compareState2 : stateCombinations){

                /* Check if we have two different states with same set of simple states */
                if (compareState1.equals(compareState2) && compareState1 != compareState2){

                    for (AdvancedTransition transition : advancedTransitions) {

                        if (transition.getSourceState() == compareState2){
                            transition.setSourceState(compareState1);
                            statesToRemove.add(compareState2);
                        }

                    }
                }
            }
        }
        stateCombinations.removeAll(statesToRemove);

        // Clean up duplicated transitions
        ArrayList<AdvancedTransition> transitionsToRemove = new ArrayList<>();
        for (AdvancedTransition compareTransition1 : advancedTransitions){
            for (AdvancedTransition compareTransition2 : advancedTransitions){
                if (compareTransition1 != compareTransition2 && compareTransition1.equals(compareTransition2)){
                    transitionsToRemove.add(compareTransition2);
                }
            }
        }
        advancedTransitions.removeAll(transitionsToRemove);

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
