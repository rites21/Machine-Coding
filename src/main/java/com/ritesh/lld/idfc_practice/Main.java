package com.ritesh.lld.idfc_practice;

import java.util.*;

public class Main {

    public static Object[] solve(String ring) {
        int n = ring.length();

        char[] ans = new char[n];
        Arrays.fill(ans, '.');

        long shifts = 0;

        int firstWall = ring.indexOf('#');

        if (firstWall == -1) {
            throw new IllegalArgumentException("Need a wall");
        }

        ans[firstWall] = '#';

        int write = firstWall + 1;

        for (int step = 1; step < n; step++) {

            // Unwrapped position
            int ind = firstWall + step;

            // Actual position in circular ring
            int actualIndex = ind % n;

            char ch = ring.charAt(actualIndex);

            if (ch == '#') {

                ans[actualIndex] = '#';

                // Wall cannot hold an item
                write = ind + 1;

            } else if (ch == '~') {

                // Preserve sticky slot if unused
                ans[actualIndex] = '~';

                // Next approaching item can
                // occupy this sticky slot
                write = ind;

            } else if (ch >= 'a' && ch <= 'z') {

                // Calculate distance travelled
                shifts += ind - write;

                // Place at destination
                // This also replaces ~ if occupied
                ans[write % n] = ch;

                // Next item settles behind it
                write++;
            }
        }

        return new Object[]{new String(ans), shifts};
    }

    public static void main(String[] args) {

        // Single character
        test("#..a..", "#a....", 2);

        // Multiple characters
        test("#..a.b..c.", "#abc......", 10);

        // Multiple walls
        test("#..a.b#...c.", "#ab...#c....", 8);

        // Circular wraparound
        test("a.b..#..", ".....#ab", 5);

        // Already settled
        test("#abc...", "#abc...", 0);

        // No characters
        test("#...#...", "#...#...", 0);

        // Characters across circular boundary
        test("a..b#..c..", "....#cab..", 12);

        // Adjacent walls
        test("##..a.b.", "##ab....", 5);

        // Sticky: single item caught
        test("#..a.~.b..", "#a...b....", 4);

        // Sticky: multiple items
        test("#..~..ab.", "#..ab....", 6);

        // Sticky remains empty
        test("#..~....", "#..~....", 0);

        // Item already next to wall
        test("#a.~..b.", "#a.b....", 3);

        // Multiple sticky slots
        test("#.~a.~..bc.", "#.a..bc....", 7);

        // Sticky with circular wraparound
        test("~..a#..b.", "a...#b...", 5);
    }

    static void test(String input, String expected, long expectedShifts) {

        Object[] result = solve(input);

        String actual = (String) result[0];
        long shifts = (long) result[1];

        boolean passed = actual.equals(expected) && shifts == expectedShifts;

        System.out.println((passed ? "PASS" : "FAIL") + " | Input: " + input + " | Expected: " + expected + ", " + expectedShifts + " | Actual: " + actual + ", " + shifts);
    }
}
