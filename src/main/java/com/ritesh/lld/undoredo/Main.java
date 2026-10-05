package com.ritesh.lld.undoredo;

/** Client: creates the receiver, manager, and concrete commands. */
public final class Main {
    public static void main(String[] args) {
//        basicEditing();
//        multipleUndoRedo();
//        boundedHistory();
        newEditAfterUndo();
    }

    private static void basicEditing() {
        // Target: undoing the deletion restores "Ritesh Kumar".
        System.out.println("\n--- Basic editing ---");
        Document document = new Document();
        UndoRedoManager manager = new UndoRedoManager(10);
        manager.execute(new InsertCommand(document, 0, "Ritesh"));
        show("Insert first name", document, manager);
        manager.execute(new InsertCommand(document, 6, " Kumar"));
        show("Insert surname", document, manager);
        manager.undo();
        show("Undo", document, manager);
        manager.redo();
        show("Redo", document, manager);
        manager.execute(new DeleteCommand(document, 6, 6));
        show("Delete surname", document, manager);
        manager.undo();
        show("Undo delete", document, manager);
    }

    private static void multipleUndoRedo() {
        // Target after two undos: "A". Then each redo yields "BA", then "CBA".
        System.out.println("\n--- Multiple undo/redo ---");
        Document document = new Document();
        UndoRedoManager manager = new UndoRedoManager(10);
        for (String text : new String[]{"A", "B", "C"}) {
            manager.execute(new InsertCommand(document, 0, text));
        }
        show("Three inserts at position 0", document, manager);
        for (int i = 0; i < 2; i++) {
            manager.undo();
            show("Undo", document, manager);
        }
        for (int i = 0; i < 2; i++) {
            manager.redo();
            show("Redo", document, manager);
        }
    }

    private static void boundedHistory() {
        // Target: at most 3 undo entries; undoing everything retained leaves "BA".
        System.out.println("\n--- History limit: 3 ---");
        Document document = new Document();
        UndoRedoManager manager = new UndoRedoManager(3);
        for (String text : new String[]{"A", "B", "C", "D", "E"}) {
            manager.execute(new InsertCommand(document, 0, text));
            show("Insert " + text, document, manager);
        }
        for (int i = 0; i < 5; i++) {
            boolean changed = manager.undo();
            show("Undo returned " + changed, document, manager);
        }
    }

    private static void newEditAfterUndo() {
        // Target: after inserting X, redo returns false and the text stays "XA".
        System.out.println("\n--- New edit after two undos ---");
        Document document = new Document();
        UndoRedoManager manager = new UndoRedoManager(10);
        for (String text : new String[]{"A", "B", "C"}) {
            manager.execute(new InsertCommand(document, 0, text));
        }
        manager.undo();
        manager.undo();
        show("Undo twice", document, manager);
        manager.execute(new InsertCommand(document, 0, "X"));
        show("Insert X", document, manager);
        boolean changed = manager.redo();
        show("Redo returned " + changed, document, manager);
    }

    private static void show(String action, Document document, UndoRedoManager manager) {
        System.out.printf("%-28s text=\"%s\"  undo=%d  redo=%d%n",
                action, document.getText(), manager.undoCount(), manager.redoCount());
    }
}
