package com.ritesh.lld.undoredo;

import java.util.Objects;

/** Exercise: finish the reverse operation using the state captured by execute. */
public final class DeleteCommand implements Command {
    private final Document document;
    private final int position;
    private final int length;
    private String deletedText;

    public DeleteCommand(Document document, int position, int length) {
        this.document = Objects.requireNonNull(document, "document");
        this.position = position;
        this.length = length;
    }

    @Override
    public void execute() {
        deletedText = document.delete(position, length);
    }

    @Override
    public void undo() {
        // TODO: Restore the captured text at its original position.
        document.insert(position, deletedText);
    }
}
