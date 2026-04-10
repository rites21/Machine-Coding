package com.ritesh.lld.DSA;

import java.util.HashSet;
import java.util.Set;

public class first {
    public static class Main {

        // Implement your logic here
        public static int countUniquePairs(int[][] matrix, int target) {
            // TODO: write your code
            Set<Integer> seen = new HashSet<>();
            Set<String> pair = new HashSet<>();

            for (int[] row : matrix) {
                for (int num : row) {
                    int comp = target - num;
                    if (seen.contains(comp)) {
                        int a = Math.min(num, comp);
                        int b = Math.max(num, comp);
                        String p = a + " " + b;

                        pair.add(p);
                    }
                    seen.add(num);
                }
            }


            return pair.size();
        }

        public static void main(String[] args) {

            // Test Case 1
            int[][] matrix1 = {
                    {1, 2, 3},
                    {4, 5, 6}
            };
            int target1 = 7;
            System.out.println(countUniquePairs(matrix1, target1)); // Expected: 3

            // Test Case 2
            int[][] matrix2 = {
                    {1, 5, 7},
                    {1, 5, 3}
            };
            int target2 = 6;
            System.out.println(countUniquePairs(matrix2, target2)); // Expected: 1

            // Test Case 3
            int[][] matrix3 = {
                    {10, 20},
                    {30, 40}
            };
            int target3 = 15;
            System.out.println(countUniquePairs(matrix3, target3)); // Expected: 0

            // Test Case 4
            int[][] matrix4 = {
                    {-1, 2, 3},
                    {4, -2, 1}
            };
            int target4 = 1;
            System.out.println(countUniquePairs(matrix4, target4)); // Expected: 2

            // Test Case 5
            int[][] matrix5 = {
                    {2, 2, 2, 2}
            };
            int target5 = 4;
            System.out.println(countUniquePairs(matrix5, target5)); // Expected: 1
        }
    }
}
