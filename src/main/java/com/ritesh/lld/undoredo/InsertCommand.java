package com.ritesh.lld.undoredo;

import java.util.Objects;

/** Concrete command: bundles the receiver, arguments, and reversal behavior. */
public final class InsertCommand implements Command {
    private final Document document;
    private final int position;
    private final String text;

    public InsertCommand(Document document, int position, String text) {
        this.document = Objects.requireNonNull(document, "document");
        this.position = position;
        this.text = Objects.requireNonNull(text, "text");
    }

    @Override
    public void execute() {
        document.insert(position, text);
    }

    @Override
    public void undo() {
        document.delete(position, text.length());
    }
}
