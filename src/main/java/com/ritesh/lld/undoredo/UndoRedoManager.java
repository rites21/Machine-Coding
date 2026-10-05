package com.ritesh.lld.undoredo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Invoker: controls command lifecycle and history policy, not text manipulation.
 * Single-threaded history. Submit a fresh command for each new edit.
 * Practice task: trace both deque ends, branching edits, and capacity boundaries.
 * See EXERCISE.md for behavioral requirements; this is an unfinished exercise.
 */
public final class UndoRedoManager {
    private final Deque<Command> undoHistory = new ArrayDeque<>();
    private final Deque<Command> redoHistory = new ArrayDeque<>();
    private final int historyLimit;

    /** Zero disables undo retention; negative limits are invalid. */
    public UndoRedoManager(int historyLimit) {
        if (historyLimit < 0) {
            throw new IllegalArgumentException("History limit must be nonnegative");
        }
        this.historyLimit = historyLimit;
    }

    public void execute(Command command) {
        Objects.requireNonNull(command, "command").execute();
        remember(command);
        redoHistory.clear();
    }

    public boolean undo() {
        if (undoHistory.isEmpty()) {
            return false;
        }
        Command command = undoHistory.peekLast();
        command.undo();
        undoHistory.removeLast();
        redoHistory.addLast(command);
        return true;
    }

    public boolean redo() {
        if (redoHistory.isEmpty()) {
            return false;
        }
        Command command = redoHistory.peekLast();
        command.execute();
        redoHistory.removeLast();
        remember(command);
        return true;
    }

    private void remember(Command command) {
        if (historyLimit == 0) {
            return;
        }
        if (undoHistory.size() >= historyLimit) {
            undoHistory.removeFirst();
        }
        undoHistory.addLast(command);
    }

    public int undoCount() {
        return undoHistory.size();
    }

    public int redoCount() {
        return redoHistory.size();
    }
}
