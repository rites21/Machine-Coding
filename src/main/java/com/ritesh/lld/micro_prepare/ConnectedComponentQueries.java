package com.ritesh.lld.micro_prepare;

import java.util.*;

public class ConnectedComponentQueries {

    public static int[] reachableNodes(int n, int[][] edges, int[] queries) {

        List<List<Integer>> graph = new ArrayList<>();

        // Nodes are 1...n
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Build graph
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int[] visited = new int[n + 1];
        int[] count = new int[n + 1];

        Arrays.fill(visited, -1);

        // Check every node
        for (int i = 1; i <= n; i++) {

            if (visited[i] == -1) {

                // ONLY nodes belonging to current component
                Set<Integer> component = new HashSet<>();

                // Your counter style
                int[] c = {0};

                dfs(graph, visited, i, c, component);

                /*
                 * c[0] = number of OTHER nodes reachable
                 *
                 * Example:
                 * component = {1,2,3,4}
                 * c[0] = 3
                 */

                for (int node : component) {
                    count[node] = c[0];
                }
            }
        }

        // Answer only requested queries
        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            result[i] = count[queries[i]];
        }

        return result;
    }

    private static void dfs(List<List<Integer>> graph, int[] visited, int node, int[] c, Set<Integer> component) {

        visited[node] = 1;

        // Current component only
        component.add(node);

        for (int neighbour : graph.get(node)) {

            if (visited[neighbour] == -1) {

                // Same logic you originally wrote
                c[0]++;

                dfs(graph, visited, neighbour, c, component);
            }
        }
    }

    public static void main(String[] args) {

        int n = 8;

        int[][] edges = {{1, 2}, {2, 3}, {3, 4}, {5, 6}, {6, 7}};

        int[] queries = {1, 3, 5, 7, 8};

        int[] result = reachableNodes(n, edges, queries);

        System.out.println(Arrays.toString(result));

        // Expected:
        // [3, 3, 2, 2, 0]
    }
}