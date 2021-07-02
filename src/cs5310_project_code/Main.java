package cs5310_project_code;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This program finds minimum spanning trees for graphs using both Kruskal's and Prim's algorithms. It prints all edges
 * of an example graph along with edges of a minimum spanning tree of its using both Prim's and Kruskal's algorithms.
 * It also calculates and prints time spent on each algorithm for number of edges ranging from 500 to 5000 with
 * intervals of 500 and 20 number of trials. It then asks user whether he/she want to give path to a file that contains
 * information about edges of a graph and if the user chooses to do so, minimum spanning trees for that graph are
 * calculated and printed.
 *
 * @author M. Salman Khan
 */
class Main {

    public static void main(String[] args) {

        LinkedList<Edge> edges = new LinkedList<>();
        LinkedList<Edge> edgesCopy = new LinkedList<>();

        System.out.println("An example graph with 4 vertices and 10 edges:\n");

        int verticesCount = 4;

        edges.add(new Edge(0,0, 3));
        edges.add(new Edge(1,1, 12));
        edges.add(new Edge(2,2, 63));
        edges.add(new Edge(3,3, 8));

        edges.add(new Edge(0,1, 38));
        edges.add(new Edge(0,2, 13));
        edges.add(new Edge(0,3, 10));
        edges.add(new Edge(1,2, 4));
        edges.add(new Edge(1,3, 52));
        edges.add(new Edge(2,3, 20));

        for (Edge thisEdge : edges) System.out.println(thisEdge.toString());

        for (Edge thisEdge : edges) edgesCopy.add(thisEdge.returnCopy());

        ArrayList<Edge> minTreeEdges = minTreeKruskal(verticesCount, edges);
        System.out.println("\nMinimum Spanning Tree Edges Obtained Through Kruskal's Algorithm:");
        for (Edge thisEdge : minTreeEdges) System.out.println(thisEdge.toString());

        minTreeEdges = minTreePrim(verticesCount, edgesCopy);
        System.out.println("\nMinimum Spanning Tree Edges Obtained Through Prim's Algorithm:");
        for (Edge thisEdge : minTreeEdges) System.out.println(thisEdge.toString());

        System.out.println("\n\nTimes for randomly generated graphs:\n");

        Random randGen = new Random();
        long stTime, enTime;
        int numOfTrials = 20;

        for (int e = 500; e <= 5000; e += 500) {
            long totTimeKruskal = 0;
            long totTimePrim = 0;

            for (int j = 0; j < numOfTrials; j++) {
                edges.clear();
                edgesCopy.clear();

                for (int k = 1; k < e - 99; k++) edges.add(new Edge(k - 1, k, randGen.nextInt()));

                for (int k = 0; k < 100; k++) {
                    int vertexOne = randGen.nextInt(e - 99);
                    int vertexTwo = randGen.nextInt(e - 99);
                    edges.add(new Edge(vertexOne, vertexTwo, randGen.nextInt()));
                }

                for (Edge thisEdge : edges) edgesCopy.add(thisEdge.returnCopy());

                stTime = System.nanoTime();
                minTreeKruskal(e - 99, edges);
                enTime = System.nanoTime();
                totTimeKruskal += enTime - stTime;

                stTime = System.nanoTime();
                minTreePrim(e - 99, edgesCopy);
                enTime = System.nanoTime();
                totTimePrim += enTime - stTime;
            }

            System.out.printf("No. of Edges = %d\n" +
                    "Kruskal's: %d nanoseconds\n" +
                    "Prim's: %d nanoseconds\n\n", e, totTimeKruskal / numOfTrials, totTimePrim / numOfTrials);
        }

        System.out.println("Enter 'y' if you want to find Minimum Spanning Tree for your graph; otherwise enter any" +
                "other phrase:");

        Scanner input = new Scanner(System.in);

        if (input.nextLine().toLowerCase().equals("y")) {
            System.out.println("\nEnter path of file that contains the graph (lines are expected to be in the format " +
                    "'vertexOne,vertexTwo,weight'\n" +
                    "where all three are expected to be integers and the first two are expected to be between 0 and " +
                    "No. of Vertices - 1):");

            ArrayList<Integer> addedVertices = new ArrayList<>();
            ArrayList<Edge> unlinkedEdges = new ArrayList<>();
            edges.clear();
            edgesCopy.clear();

            try {
                Scanner inFile = new Scanner(new File(input.nextLine()));

                boolean firstIteration = true;

                while (inFile.hasNextLine()) {
                    String[] strTokens = inFile.nextLine().split(",");

                    if (strTokens.length == 1 && strTokens[0].equals("")) continue;
                    if (strTokens.length != 3) throw new Exception();

                    int[] intTokens = new int[3];
                    for (int i = 0; i < 3; i++) intTokens[i] = Integer.parseInt(strTokens[i]);

                    if (firstIteration) {
                        edges.add(new Edge(intTokens[0], intTokens[1], intTokens[2]));

                        addedVertices.add(intTokens[0]);
                        if (!addedVertices.contains(intTokens[1])) addedVertices.add(intTokens[1]);

                        firstIteration = false;
                    } else {
                        if (addedVertices.contains(intTokens[0]) || addedVertices.contains(intTokens[1])) {
                            edges.add(new Edge(intTokens[0], intTokens[1], intTokens[2]));

                            if (!addedVertices.contains(intTokens[0])) addedVertices.add(intTokens[0]);
                            if (!addedVertices.contains(intTokens[1])) addedVertices.add(intTokens[1]);

                            for (int i = 0; i < unlinkedEdges.size(); i++) {
                                if (unlinkedEdges.get(i).vertexOne == intTokens[0] ||
                                        unlinkedEdges.get(i).vertexTwo == intTokens[0] ||
                                        unlinkedEdges.get(i).vertexOne == intTokens[1] ||
                                        unlinkedEdges.get(i).vertexTwo == intTokens[1]) {
                                    edges.add(unlinkedEdges.get(i));

                                    if (!addedVertices.contains(unlinkedEdges.get(i).vertexOne))
                                        addedVertices.add(unlinkedEdges.get(i).vertexOne);

                                    if (!addedVertices.contains(unlinkedEdges.get(i).vertexTwo))
                                        addedVertices.add(unlinkedEdges.get(i).vertexTwo);

                                    unlinkedEdges.remove(i);
                                    i--;
                                }
                            }
                        } else {
                            unlinkedEdges.add(new Edge(intTokens[0], intTokens[1], intTokens[2]));
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("\nThe file was not found at the specified path\n" +
                        "The program will exit");

                System.exit(1);
            } catch (Exception e) {
                System.out.println("\nFile's contents do not match expected format\n" +
                        "The program will exit");

                System.exit(2);
            }

            if (unlinkedEdges.size() != 0) {
                System.out.println("\nThe graph contained in file is not connected\n" +
                        "The program will exit");

                System.exit(3);
            }

            Collections.sort(addedVertices);

            for (int i = 0; i < addedVertices.size(); i++) {
                if (addedVertices.get(i) != i) {
                    System.out.println("\nVertices are not numbered from 0 to No. of Vertices\n" +
                            "The program will exit");

                    System.exit(4);
                }
            }

            for (Edge thisEdge : edges) edgesCopy.add(thisEdge.returnCopy());

            minTreeEdges = minTreeKruskal(addedVertices.size(), edges);
            System.out.println("\nMinimum Spanning Tree Edges Obtained Through Kruskal's Algorithm:");
            for (Edge thisEdge : minTreeEdges) System.out.println(thisEdge.toString());

            minTreeEdges = minTreePrim(addedVertices.size(), edgesCopy);
            System.out.println("\nMinimum Spanning Tree Edges Obtained Through Prim's Algorithm:");
            for (Edge thisEdge : minTreeEdges) System.out.println(thisEdge.toString());
        }

    }

    /**
     * Finds a minimum spanning tree for the received graph using Kruskal's Algorithm
     * @param verticesCount the number of vertices in the graph
     * @param edges all edges of the graph
     * @return edges of a minimum spanning tree for the graph
     */
    private static ArrayList<Edge> minTreeKruskal(int verticesCount, LinkedList<Edge> edges) {
        edges.sort(Comparator.comparingInt(entry -> entry.weight));

        DisjointSet verticesSets = new DisjointSet(verticesCount);
        ArrayList<Edge> minEdges = new ArrayList<>();

        while (minEdges.size() < verticesCount - 1) {
            Edge thisEdge = edges.remove();

            int rootOne = verticesSets.collapsingFind(thisEdge.vertexOne);
            int rootTwo = verticesSets.collapsingFind(thisEdge.vertexTwo);

            if (rootOne != rootTwo) {
                verticesSets.weightedUnion(rootOne, rootTwo);
                minEdges.add(thisEdge);
            }
        }

        return minEdges;
    }

    /**
     * Finds a minimum spanning tree for the received graph using Prim's Algorithm
     * @param verticesCount the number of vertices in the graph
     * @param edges all edges of the graph
     * @return edges of a minimum spanning tree for the graph
     */
    private static ArrayList<Edge> minTreePrim(int verticesCount, LinkedList<Edge> edges) {
        LinkedList<Edge> availableEdges = new LinkedList<>();
        ArrayList<Edge> minEdges = new ArrayList<>();
        boolean[] addedVertices = new boolean[verticesCount];

        for (int i = 0; i < addedVertices.length; i++) addedVertices[i] = false;

        addedVertices[edges.getFirst().vertexOne] = true;
        addAvailableEdges(edges.getFirst().vertexOne, edges, addedVertices, availableEdges);

        while (minEdges.size() < verticesCount - 1) {
            Edge thisEdge = availableEdges.remove(findMinWeight(availableEdges));

            if (addedVertices[thisEdge.vertexOne] && !addedVertices[thisEdge.vertexTwo]) {
                minEdges.add(thisEdge);

                addedVertices[thisEdge.vertexTwo] = true;
                addAvailableEdges(thisEdge.vertexTwo, edges, addedVertices, availableEdges);
            } else if (addedVertices[thisEdge.vertexTwo] && !addedVertices[thisEdge.vertexOne]) {
                minEdges.add(thisEdge);

                addedVertices[thisEdge.vertexOne] = true;
                addAvailableEdges(thisEdge.vertexOne, edges, addedVertices, availableEdges);
            }
        }

        return minEdges;
    }

    /**
     * Used by 'minTreePrim' method. Adds edges connected with 'lastAddedVertex' to 'availableEdges'
     * @param lastAddedVertex the vertex that was just (last) added
     * @param edges unused edges of graph
     * @param addedVertices the vertices that have already been added to the minimum spanning tree under construction
     * @param availableEdges the edges that are connected with vertices already added to the minimum spanning tree
     *                       under construction
     */
    private static void addAvailableEdges(int lastAddedVertex, LinkedList<Edge> edges, boolean[] addedVertices,
                                          LinkedList<Edge> availableEdges) {
        Iterator<Edge> edgesIterator = edges.iterator();
        while (edgesIterator.hasNext()) {
            Edge thisEdge = edgesIterator.next();

            if((lastAddedVertex == thisEdge.vertexOne && !addedVertices[thisEdge.vertexTwo]) ||
                    (lastAddedVertex == thisEdge.vertexTwo && !addedVertices[thisEdge.vertexOne])) {
                availableEdges.add(thisEdge);
                edgesIterator.remove();
            }
        }
    }

    /**
     * Used by 'minTreePrim' method. Finds minimum weight in 'availableEdges'
     * @param availableEdges the edges that are connected with vertices already added to the minimum spanning tree
     *                       under construction
     * @return the index in 'availableEdges' with minimum weight
     */
    private static int findMinWeight(LinkedList<Edge> availableEdges) {
        int minWeight = availableEdges.getFirst().weight;
        int atIndex = 0;

        int i = 0;
        for (Edge thisEdge : availableEdges) {
            if (thisEdge.weight < minWeight) {
                minWeight = thisEdge.weight;
                atIndex = i;
            }

            i++;
        }

        return atIndex;
    }

}