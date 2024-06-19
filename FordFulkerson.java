import java.util.*;

public class FordFulkerson {
    private int vertices;
    private int[][] capacity;
    private int[][] residualCapacity;
    private int[] parent;

    public FordFulkerson(int vertices) {
        this.vertices = vertices;
        this.capacity = new int[vertices][vertices];
        this.residualCapacity = new int[vertices][vertices];
        this.parent = new int[vertices];
    }

    public void addEdge(int u, int v, int capacity) {
        this.capacity[u][v] = capacity;
    }

    private boolean bfs(int source, int sink) {
        boolean[] visited = new boolean[vertices];
        Arrays.fill(visited, false);

        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && residualCapacity[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return visited[sink];
    }

    public int fordFulkerson(int source, int sink) {
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                residualCapacity[i][j] = capacity[i][j];
            }
        }

        int maxFlow = 0;

        while (bfs(source, sink)) {
            int pathFlow = Integer.MAX_VALUE;
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualCapacity[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualCapacity[u][v] -= pathFlow;
                residualCapacity[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static void main(String[] args) {
        FordFulkerson graph = new FordFulkerson(6);
        graph.addEdge(0, 1, 16);
        graph.addEdge(0, 2, 13);
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 3, 12);
        graph.addEdge(2, 1, 4);
        graph.addEdge(2, 4, 14);
        graph.addEdge(3, 2, 9);
        graph.addEdge(3, 5, 20);
        graph.addEdge(4, 3, 7);
        graph.addEdge(4, 5, 4);

        int source = 0;
        int sink = 5;

        System.out.println("The maximum possible flow is " + graph.fordFulkerson(source, sink));
    }
}
