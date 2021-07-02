package cs5310_project_code;

/**
 * Objects of this class contain all vertices of a graph grouped in sets
 *
 * @author M. Salman Khan
 */
class DisjointSet {

    private int[] vertices;

    /**
     * Creates a set for each vertex
     * @param size number of vertices in graph
     */
    DisjointSet(int size) {
        vertices = new int[size];
        for (int i = 0; i < size; i++) vertices[i] = -1;
    }

    /**
     * Finds root vertex of the received vertex
     * @param vertex the vertex to find
     * @return root of the found vertex
     */
    int collapsingFind(int vertex) {
        int vertexCopy = vertex;

        while (vertices[vertex] > -1) vertex = vertices[vertex];

        while (vertex != vertexCopy) {
            int temp = vertices[vertexCopy];
            vertices[vertexCopy] = vertex;
            vertexCopy = temp;
        }

        return vertex;
    }

    /**
     * Merges two set of vertices
     * @param rootOne root of one of the set of vertices
     * @param rootTwo root of the other set of vertices
     */
    void weightedUnion(int rootOne, int rootTwo) {
        if((vertices[rootOne] * -1) < (vertices[rootTwo] * -1)) {
            vertices[rootTwo] = ((vertices[rootTwo] * -1) + (vertices[rootOne] * -1)) * -1;
            vertices[rootOne] = rootTwo;
        } else {
            vertices[rootOne] = ((vertices[rootTwo] * -1) + (vertices[rootOne] * -1)) * -1;
            vertices[rootTwo] = rootOne;
        }
    }

}