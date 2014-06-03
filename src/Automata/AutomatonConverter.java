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

        /* Alphabet get */
        ArrayList<String> alphabet = copy.getAutomatonAlphabet();
        // Name + Vertex
        HashMap<String, Vertex> states = copy.getVertexes();

        /* Generate All possible state combinations */

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

    static private ArrayList<AdvancedState> generateStateCombinations(HashMap<String, Vertex> originalStates){
        ArrayList<AdvancedState> result = new ArrayList<>();

        OrderedPowerSet<Vertex> powerSet = new OrderedPowerSet<Vertex>(new ArrayList<Vertex>(originalStates.values()));

        return result;
    }

    public static String[] getAllLists(String[] elements, int lengthOfList)
    {
        //initialize our returned list with the number of elements calculated above
        String[] allLists = new String[(int)Math.pow(elements.length, lengthOfList)];

        //lists of length 1 are just the original elements
        if(lengthOfList == 1){
            return elements;
        }
        else
        {
            //the recursion--get all lists of length 3, length 2, all the way up to 1
            String[] allSublists = getAllLists(elements, lengthOfList - 1);

            //append the sublists to each element
            int arrayIndex = 0;

            for(int i = 0; i < elements.length; i++)
            {
                for(int j = 0; j < allSublists.length; j++)
                {
                    //add the newly appended combination to the list
                    allLists[arrayIndex] = elements[i] + allSublists[j];
                    arrayIndex++;
                }
            }

            return allLists;
        }
    }
}
