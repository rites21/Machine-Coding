package com.ritesh.lld.idfc_practice;

import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

        Arrays.sort(meetings, (a, b) -> Integer.compare(a[2], b[2]));

        boolean[] knows = new boolean[n];
        knows[0] = true;
        knows[firstPerson] = true;

        int i = 0;

        while (i < meetings.length) {
            int time = meetings[i][2];

            Map<Integer, List<Integer>> adj = new HashMap<>();

            // Build graph for the same timestamp
            while (i < meetings.length && meetings[i][2] == time) {
                int u = meetings[i][0];
                int v = meetings[i][1];

                adj.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
                adj.computeIfAbsent(v, k -> new ArrayList<>()).add(u);

                i++;
            }

            Queue<Integer> q = new LinkedList<>();

            // Find people who already know the secret
            for (int person : adj.keySet()) {
                if (knows[person]) {
                    q.offer(person);
                }
            }

            // Spread secret within this timestamp
            while (!q.isEmpty()) {
                int person = q.poll();

                for (int neighbor : adj.get(person)) {
                    if (!knows[neighbor]) {
                        knows[neighbor] = true;
                        q.offer(neighbor);
                    }
                }
            }
        }

        List<Integer> ans = new ArrayList<>();

        for (int person = 0; person < n; person++) {
            if (knows[person]) {
                ans.add(person);
            }
        }

        return ans;
    }
}
