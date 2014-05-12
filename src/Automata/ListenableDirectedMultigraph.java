/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * ListenableDirectedMultigraph class
 */

package Automata;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;
import org.jgrapht.graph.DirectedMultigraph;

public class ListenableDirectedMultigraph<V, E> extends DefaultListenableGraph<V, E> implements DirectedGraph<V, E>  {
    private static final long serialVersionUID = 1L;

    ListenableDirectedMultigraph(Class<E> edgeClass) {
        super(new DirectedMultigraph<V, E>(edgeClass));
    }
}