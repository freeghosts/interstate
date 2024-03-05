package interstate;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.alg.*;

import java.util.*;
import java.io.*;


public class interstate
{
    public static void main (String[] args) throws IOException
    {
        SimpleWeightedGraph<String, DefaultWeightedEdge> g =
        new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        Scanner in = new Scanner(System.in);

        // open file for input
		File file = new File("interstate.txt");
		Scanner infile = new Scanner(file);

		Double TotalDistance = 0.0;
		String Line = "";
		String[] Parts;
		while(infile.hasNext())
		{
			Line = infile.nextLine();
			if (!Line.startsWith("//") && !Line.equals(""))
			{
				Parts = Line.split(" ");
				g.addVertex(Parts[1] + " " + Parts[2]); // add city/state from
				g.addVertex(Parts[3] + " " + Parts[4]); // add city/state to
				g.addEdge(Parts[1] + " " + Parts[2],Parts[3] + " " + Parts[4]);
				g.setEdgeWeight(g.getEdge(Parts[1] + " " + Parts[2],Parts[3] + " " + Parts[4]),Double.parseDouble(Parts[5]));
				TotalDistance += Double.parseDouble(Parts[5]);
			}
		}
		System.out.println();

		// user menu
		int Choice = 0;

		while(Choice != 5)
		{
			System.out.println("--------------------------------------------");
			System.out.println("(1) Graph summary information");
			System.out.println("(2) Show all vertices");
			System.out.println("(3) Vertex summary information");
			System.out.println("(4) Get path between two vertices");
			System.out.println("(5) Exit");
			System.out.println();
			System.out.println("Choose an action: ");
			Choice = in.nextInt();
			System.out.println("--------------------------------------------");

			if(Choice == 1) // graph summary info = total # of vertices & edges + total distance of edges
			{
				System.out.println();
				System.out.println("The graph has a total of " + g.vertexSet().size() + " vertices and " + g.edgeSet().size() + " edges.");
				System.out.println("The total distance in miles of all edges is " + TotalDistance + " miles.");
				System.out.println();
			}
			if(Choice == 2) // show all vertices
			{
				System.out.println();
				System.out.println("This graph has the following vertices:");
				System.out.println();
				System.out.println(g.vertexSet());
				System.out.println();
			}
			if(Choice == 3) // vertex summary info = whether/not vertex exists; lists all edges of a vertex
			{
				System.out.println();
				in.nextLine(); // need this for switching from int to String input; w/o this line, can't input vertex name
				System.out.print("Enter name of vertex: ");
				String Location = in.nextLine();
				if (g.containsVertex(Location))
				{
					System.out.println("Vertex Exists!");
					System.out.println(Location + " has " + g.edgesOf(Location).size() + " edges: ");
					System.out.println(Location + " Edges: " + g.edgesOf(Location)); // need to count # of edges
				}
				else
					System.out.println("Vertex Does Not Exist");
				System.out.println();
			}
			if(Choice == 4) // get path btwn 2 vertices
			{
				System.out.println();
				in.nextLine(); // need this for switching from int to String input; w/o this line, can't input vertex names
				System.out.println("Enter the the city & state where you'd like to begin: ");
				String From = in.nextLine();
				if (g.containsVertex(From))
				{
					System.out.println("Enter the city & state where you'd like to end: ");
					String To = in.nextLine();
					if (g.containsVertex(To))
					{
						DijkstraShortestPath Path = new DijkstraShortestPath<String, DefaultWeightedEdge>(g, From, To);
						//DijkstraShortestPath Path = new DijkstraShortestPath<>(g, From, To);
						System.out.println();
						System.out.println("Length = " + Path.getPathLength() + " miles.");
						System.out.println();
						System.out.println("Path = " + Path.getPathEdgeList());
				        System.out.println();
					}
					else
						System.out.println("Vertex does not exist.");
				}
				else
					System.out.println("Vertex does not exist.");
				System.out.println();
			}
		}
		System.out.println("Goodbye!");
    }
}