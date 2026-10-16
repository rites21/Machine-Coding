package com.ritesh.lld.TaskScheduler;

import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public final class TaskScheduler {
    private final PriorityBlockingQueue<ScheduledTask> queue = new PriorityBlockingQueue<>(
            11, Comparator.comparing((ScheduledTask s) -> s.nextRunTime));
    private final TaskDependencyGraph graph = new TaskDependencyGraph();
    private final Set<Integer> taskIds = new HashSet<>();
    private final List<TaskObserver> observers = new CopyOnWriteArrayList<>();
    private final ExecutorService workers;
    private Thread schedulerThread;
    private boolean started;
    private boolean stopped;

    public TaskScheduler(int workerCount) {
        workers = Executors.newFixedThreadPool(workerCount);
    }

    public void addObserver(TaskObserver observer) {
        observers.add(Objects.requireNonNull(observer));
    }

    // Configure tasks and the DAG before start(). One Task belongs to one scheduler.
    public synchronized void schedule(ScheduledTask scheduledTask) {
        requireConfigurable();
        Objects.requireNonNull(scheduledTask);
        int id = scheduledTask.task.getTaskId();
        if (!taskIds.add(id)) throw new IllegalArgumentException("Duplicate task id");
        graph.addTask(id);
        queue.offer(scheduledTask);
    }

    public synchronized void addDependency(int taskId, int prerequisiteId) {
        requireConfigurable();
        graph.addDependency(taskId, prerequisiteId);
    }

    private void requireConfigurable() {
        if (started || stopped) throw new IllegalStateException("Configure before start");
    }

    public synchronized void start() {
        if (stopped) throw new IllegalStateException("Scheduler is stopped");
        if (started) return;
        started = true;
        schedulerThread = new Thread(this::runLoop, "scheduler");
        schedulerThread.start();
    }

    private void runLoop() {
        try {
            while (dispatchDueTasks()) Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // The same monitor protects queue decisions, graph updates, and stop/submit.
    private synchronized boolean dispatchDueTasks() {
        if (stopped) return false;
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTask> waiting = new ArrayList<>();
        while (!queue.isEmpty() && !queue.peek().nextRunTime.isAfter(now)) {
            ScheduledTask entry = queue.poll();
            if (graph.canRun(entry.task.getTaskId())) {
                workers.execute(() -> executeTask(entry));
            } else {
                waiting.add(entry);
            }
        }
        // Reinsert only after scanning due tasks: a blocked head must not starve others.
        queue.addAll(waiting);
        return true;
    }

    private void executeTask(ScheduledTask entry) {
        Task task = entry.task;
        task.setTaskStatus(TaskStatus.RUNNING);
        notifyObservers(task);
        boolean succeeded = false;
        try {
            task.execute();
            succeeded = true;
            task.setTaskStatus(TaskStatus.SUCCESS);
        } catch (Throwable e) {
            task.setTaskStatus(TaskStatus.FAILED);
            System.err.println(task.getTaskName() + " failed: " + e);
        }
        notifyObservers(task);
        synchronized (this) {
            if (succeeded) graph.markSucceeded(task.getTaskId());
            if (!stopped && entry.scheduleType == ScheduleType.RECURRING) {
                LocalDateTime next = entry.calculateNextExecutionTime(LocalDateTime.now());
                queue.offer(new ScheduledTask(task, next, entry.scheduleType, entry.interval));
            }
        }
    }

    private void notifyObservers(Task task) {
        // No scheduler lock around user code; one bad listener cannot break execution.
        for (TaskObserver observer : observers) {
            try { observer.onStatusChanged(task); }
            catch (Throwable e) { System.err.println("Observer failed: " + e); }
        }
    }

    public synchronized void stop() {
        stopped = true;
        queue.clear();
        if (schedulerThread != null) schedulerThread.interrupt();
        workers.shutdown(); // Already submitted work finishes; no further recurrence.
    }

    public static void main(String[] args) throws InterruptedException {
        TaskScheduler scheduler = new TaskScheduler(3);
        CountDownLatch finished = new CountDownLatch(5);
        scheduler.addObserver(task -> {
            System.out.println(task.getTaskName() + " -> " + task.getTaskStatus());
            if (task.getTaskStatus() == TaskStatus.SUCCESS) finished.countDown();
        });
        LocalDateTime now = LocalDateTime.now();
        scheduler.schedule(new ScheduledTask(new Task(1, "A", () -> pause(150)),
                now, ScheduleType.ONE_TIME, null));
        scheduler.schedule(new ScheduledTask(new Task(2, "B after A", () -> pause(50)),
                now, ScheduleType.ONE_TIME, null));
        scheduler.schedule(new ScheduledTask(new Task(3, "Independent", () -> pause(100)),
                now, ScheduleType.ONE_TIME, null));
        scheduler.schedule(new ScheduledTask(new Task(4, "Recurring", () -> pause(20)),
                now, ScheduleType.RECURRING, Duration.ofMillis(300)));
        scheduler.addDependency(2, 1);
        try { scheduler.addDependency(1, 2); }
        catch (IllegalArgumentException e) { System.out.println("Cycle rejected"); }
        try {
            scheduler.start();
            if (!finished.await(5, TimeUnit.SECONDS)) throw new AssertionError("Demo timed out");
        } finally { scheduler.stop(); }
    }

    private static void pause(long millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
