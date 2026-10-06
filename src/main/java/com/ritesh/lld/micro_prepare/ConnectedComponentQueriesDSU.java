package com.ritesh.lld.micro_prepare;

import java.util.*;

public class ConnectedComponentQueriesDSU {

    public static int[] reachableNodes(int n, int[][] edges, int[] queries) {

        DSU dsu = new DSU(n);

        // Build connected components
        for (int[] edge : edges) {
            dsu.union(edge[0], edge[1]);
        }

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int node = queries[i];

            // -1 because don't count the node itself
            result[i] = dsu.componentSize(node) - 1;
        }

        return result;
    }

    public static void main(String[] args) {

        int n = 8;

        int[][] edges = {{1, 2}, {2, 3}, {3, 4}, {5, 6}, {6, 7}};

        int[] queries = {1, 3, 5, 7, 8};

        System.out.println(Arrays.toString(reachableNodes(n, edges, queries)));

        // [3, 3, 2, 2, 0]
    }

    static class DSU {

        int[] parent;
        int[] size;

        DSU(int n) {

            parent = new int[n + 1];
            size = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        int find(int x) {

            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }

            return parent[x];
        }

        void union(int a, int b) {

            int rootA = find(a);
            int rootB = find(b);

            if (rootA == rootB) {
                return;
            }

            // Union by size
            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }

            parent[rootB] = rootA;

            size[rootA] += size[rootB];
        }

        int componentSize(int node) {
            return size[find(node)];
        }
    }
}