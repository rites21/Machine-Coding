package com.ritesh.lld.TaskScheduler;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ScheduledTask {
    final Task task;
    final LocalDateTime nextRunTime;
    final ScheduleType scheduleType;
    final Duration interval;

    public ScheduledTask(Task task, LocalDateTime nextRunTime,
                         ScheduleType scheduleType, Duration interval) {
        this.task = Objects.requireNonNull(task);
        this.nextRunTime = Objects.requireNonNull(nextRunTime);
        this.scheduleType = Objects.requireNonNull(scheduleType);
        if (scheduleType == ScheduleType.RECURRING &&
                (interval == null || interval.isNegative() || interval.isZero())) {
            throw new IllegalArgumentException("Recurring interval must be positive");
        }
        this.interval = interval;
    }

    public LocalDateTime calculateNextExecutionTime(LocalDateTime completedAt) {
        if (scheduleType != ScheduleType.RECURRING) {
            throw new IllegalStateException("One-time task has no next execution");
        }
        return completedAt.plus(interval);
    }
}
