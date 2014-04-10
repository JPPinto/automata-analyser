import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Automata {

	private ListenableGraph g;
	private static int edge_count = 0;
	private static int vertex_count = 0;
	private static final int MAX_SIZE = 2048;

	public Automata() {
		g = new ListenableDirectedWeightedGraph(DefaultEdge.class);
	}

	public void parseDottyfile(File file){
		String graph = "";
		try {
			FileReader fr = new FileReader(file);

			char[] content = new char[MAX_SIZE];
			fr.read(content);

			graph = new String(content);

		} catch (FileNotFoundException e) {
			System.out.println("File " + file.getName() + "not found!");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] lines = graph.split("\n");

		for (int i = 0; i < lines.length; i++)
			parseLine(lines[i]);


	}

	public void parseLine(String s){

		if(Pattern.compile("(\"\"|[a-zA-Z][a-zA-Z0-9]*)->[a-zA-Z][a-zA-Z0-9]*").matcher(s).matches()){
			String[] sides = s.split("->");

			if(!g.containsVertex(sides[0]))
				addVertex(sides[0], false, false);

			if(!g.containsVertex(sides[1]))
				addVertex(sides[1], false, false);

			addEdge(sides[0], sides[1],"");
			return;

		}

		if(Pattern.compile("\"\"\\[.*\\]").matcher(s).matches()){

			if(!g.containsVertex("\"\""))
				addVertex("\"\"", false, true);

			return;
		}

		if(Pattern.compile("(\"\"|[a-zA-Z][a-zA-Z0-9]*)->(\"\"|[a-zA-Z][a-zA-Z0-9]*)\\[.*\\]").matcher(s).matches()){
			String[] content = s.split("\\[");
			String[] label_sides = content[1].substring(0, content[1].length() - 1).split("=");

			String[] sides = content[0].split("->");

			if(!g.containsVertex(sides[0]))
				addVertex(sides[0], false, false);

			if(!g.containsVertex(sides[1]))
				addVertex(sides[1], false, false);

			if(label_sides[0] == "label")
				addEdge(sides[0], sides[1], label_sides[1]);
			else
				addEdge(sides[0], sides[1], "");

			return;
		}

		if(Pattern.compile(".*\\[.*\\]").matcher(s).matches()){
			String[] content = s.split("\\[");
			String shape = content[1].substring(0, content[1].length() - 1);


			if(shape == "shape=\"doublecircle\"]")
				addVertex(content[0], true, false);


			return;
		}

		if(Pattern.compile(".*\\{|\\}").matcher(s).matches())
			return;

	}

	public void addVertex(String vertex_name, boolean final_state, boolean inicial_state) {
		Vertex vertex = new Vertex(vertex_name, final_state, inicial_state, vertex_count);
		g.addVertex(vertex_name);
		vertex_count++;
	}

	public void addEdge(String vertex_1, String vertex_2, String edge_simbol) {
		Edge edge = new Edge(edge_count, edge_simbol);
		g.addEdge(vertex_1, vertex_2, edge);
		edge_count++;
	}

	public static void main(String[] args) {
	}
}
