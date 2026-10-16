package com.ritesh.lld.TaskScheduler;

@FunctionalInterface
public interface TaskObserver {
    // Callbacks for different tasks can happen concurrently; keep them short.
    void onStatusChanged(Task task);
}
