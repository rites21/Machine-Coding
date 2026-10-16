package com.ritesh.lld.TaskScheduler;

import java.util.*;

/** DAG state, accessed only while holding the scheduler's lock. Edge: prerequisite -> dependent. */
public final class TaskDependencyGraph {
    private final Map<Integer, Set<Integer>> dependencies = new HashMap<>();
    private final Map<Integer, Set<Integer>> dependents = new HashMap<>();
    private final Set<Integer> succeeded = new HashSet<>();

    void addTask(int id) {
        dependencies.put(id, new HashSet<>());
        dependents.put(id, new HashSet<>());
    }

    void addDependency(int taskId, int prerequisiteId) {
        if (!dependencies.containsKey(taskId) || !dependencies.containsKey(prerequisiteId)) {
            throw new IllegalArgumentException("Register both tasks before adding an edge");
        }
        if (taskId == prerequisiteId || reachable(taskId, prerequisiteId)) {
            throw new IllegalArgumentException("Dependency would create a cycle");
        }
        dependencies.get(taskId).add(prerequisiteId);
        dependents.get(prerequisiteId).add(taskId);
    }

    private boolean reachable(int from, int target) {
        Deque<Integer> stack = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();
        stack.push(from);
        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (current == target) return true;
            if (visited.add(current)) stack.addAll(dependents.get(current));
        }
        return false;
    }

    boolean canRun(int taskId) { return succeeded.containsAll(dependencies.get(taskId)); }
    void markSucceeded(int taskId) { succeeded.add(taskId); }
    Set<Integer> dependentsOf(int id) { return Set.copyOf(dependents.get(id)); }
}
