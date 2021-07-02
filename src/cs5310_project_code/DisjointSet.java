package cs5310_project_code;

class DisjointSet {

    private int[] vertices;

    DisjointSet(int size) {
        vertices = new int[size];
        for (int i = 0; i < size; i++) vertices[i] = -1;
    }

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