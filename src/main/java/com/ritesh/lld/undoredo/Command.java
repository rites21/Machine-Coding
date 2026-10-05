package com.ritesh.lld.undoredo;

/**
 * Contract for one reversible edit. The manager knows this interface, so it can
 * manage different edits without inspecting their concrete types.
 * Each implementation owns the details and state needed to reverse its edit.
 * This exercise assumes fresh commands for new edits and no external mutations.
 */
public interface Command {
    // Apply the edit. The manager also calls this when redoing an undone edit.
    void execute();
    // Reverse this command's most recent successful execution.
    void undo();
}
