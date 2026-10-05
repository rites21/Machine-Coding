package com.ritesh.lld.undoredo;

import java.util.Objects;

/**
 * Receiver: owns text operations; does not decide history policy.
 */
public final class Document {
    private final StringBuilder text = new StringBuilder();

    public void insert(int position, String value) {
        Objects.requireNonNull(value, "value");
        if (position < 0 || position > text.length()) {
            throw new IndexOutOfBoundsException("Invalid insert position: " + position);
        }
        text.insert(position, value);
    }

    public String delete(int position, int length) {
        if (position < 0 || length < 0 || position > text.length() || length > text.length() - position) {
            throw new IndexOutOfBoundsException("Invalid delete range");
        }
        String removed = text.substring(position, position + length);
        text.delete(position, position + length);
        return removed;
    }

    public String getText() {
        return text.toString();
    }
}
