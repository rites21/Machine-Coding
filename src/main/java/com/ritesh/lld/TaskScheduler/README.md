# Minimal interview task scheduler

Seven types: Task, TaskStatus, ScheduleType, ScheduledTask, TaskScheduler,
TaskDependencyGraph and TaskObserver. Start reading TaskScheduler.main().

```bash
javac -d target/task-scheduler src/main/java/com/ritesh/lld/TaskScheduler/*.java
java -cp target/task-scheduler com.ritesh.lld.TaskScheduler.TaskScheduler
```

Checks (no dependencies):

```bash
javac -d target/task-scheduler src/main/java/com/ritesh/lld/TaskScheduler/*.java src/test/java/com/ritesh/lld/TaskScheduler/TaskSchedulerChecks.java
java -cp target/task-scheduler com.ritesh.lld.TaskScheduler.TaskSchedulerChecks
```

## Flow

1. Register uniquely identified tasks and dependencies before start().
2. Poll the priority queue every 100 ms, ordered by nextRunTime.
3. Submit due tasks whose prerequisites have succeeded to the fixed worker pool.
4. Notify observers when work starts and succeeds/fails.
5. After recurring work finishes, enqueue a new entry with completion time + interval.

Blocked tasks are reinserted after scanning all due entries. This prevents a
blocked head from starving independent tasks. Cycle detection rejects an edge
A -> B if B already reaches A. Both adjacency maps form the DAG.

## Deliberate boundaries

- A prerequisite needs to succeed once, not once per recurring workflow run.
- A failed one-time prerequisite leaves its dependents PENDING. Recurring failures
  try again on the next interval; dependents can run after a later success.
- Recurrence uses a positive Duration, with no cron expression or calendar policy.
- One Task instance belongs to one scheduler. A recurring task never overlaps itself.
- stop() discards queued schedules and shuts down the executor. Already submitted
  work finishes. No cancellation events, restart, or forced interruption of work.
- Polling adds roughly up to 100 ms of detection latency under normal conditions;
  OS scheduling, worker saturation and listeners can add more.
- LocalDateTime uses the local wall clock; clock changes affect scheduling.
- Task status retains its last outcome between recurring executions.

## Thread safety

The TaskScheduler monitor protects graph state, lifecycle and compound queue
operations (peek/poll/check/submit). Submission and stop use the same lock to
avoid shutdown races. Worker code and observer callbacks run outside that lock.
Task.status is volatile for visible reads. CopyOnWriteArrayList supports safe
observer registration during notification. Observers must be fast and thread-safe.
PriorityBlockingQueue provides safe ordered queue operations, but does not make
multi-step scheduling decisions atomic by itself. With all operations under one
monitor, a regular PriorityQueue would also suffice; the concurrent queue is kept
here to match the exercise's requested structure.

Cycle checks cost O(V + E); queue operations O(log N); eligibility checks O(number
of prerequisites). A polling pass revisits blocked due tasks: a deliberate
simplicity tradeoff. Queue and worker backlog are unbounded in this practice code.

Java queue reference:
https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/PriorityBlockingQueue.html
