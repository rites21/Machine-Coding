package com.ritesh.lld.TaskScheduler;

import java.util.Objects;

public final class Task {
    private final int taskId;
    private final String taskName;
    private final Runnable work;
    private volatile TaskStatus taskStatus = TaskStatus.PENDING;

    public Task(int taskId, String taskName, Runnable work) {
        this.taskId = taskId;
        this.taskName = Objects.requireNonNull(taskName);
        this.work = Objects.requireNonNull(work);
    }

    public int getTaskId() { return taskId; }
    public String getTaskName() { return taskName; }
    public TaskStatus getTaskStatus() { return taskStatus; }
    void setTaskStatus(TaskStatus status) { taskStatus = status; }
    void execute() { work.run(); }
}
