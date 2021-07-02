package cs5310_project_code;

/**
 * Objects of this class contain info about edges. This includes the vertices they connect and their weights
 *
 * @author M. Salman Khan
 */
class Edge {

    int vertexOne, vertexTwo, weight;

    /**
     * Creates an Edge Object
     * @param vertexOne one of the edges that this edge is connected to
     * @param vertexTwo the other edge that this edge is connected to
     * @param weight wight of the edge
     */
    Edge(int vertexOne, int vertexTwo, int weight) {
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
        this.weight = weight;
    }

    /**
     * Makes a copy of the calling object
     * @return a copy of the calling object
     */
    Edge returnCopy() {
        return new Edge(vertexOne, vertexTwo, weight);
    }

    @Override
    public String toString() {
        return "Edge between vertices " + vertexOne + " and " + vertexTwo + ",\t weight = " + weight;
    }

}