package cs5310_project_code;

class Edge {

    int vertexOne, vertexTwo, weight;

    Edge(int vertexOne, int vertexTwo, int weight) {
        this.vertexOne = vertexOne;
        this.vertexTwo = vertexTwo;
        this.weight = weight;
    }

    Edge returnCopy() {
        return new Edge(vertexOne, vertexTwo, weight);
    }

    @Override
    public String toString() {
        return "Edge between vertices " + vertexOne + " and " + vertexTwo + ",\t weight = " + weight;
    }

}