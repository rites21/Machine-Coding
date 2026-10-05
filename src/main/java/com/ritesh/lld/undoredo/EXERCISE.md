# Undo/Redo: guided implementation and debugging

This is deliberately unfinished practice code. Keep the existing classes and
repair them; you do not need to rebuild the project. There are four seeded issues:
one incomplete operation and three logic/design issues. The challenges below
describe observable failures without prescribing the code changes.

## Understand the collaboration first

`Main -> UndoRedoManager -> Command -> Document`

- **Main (client)** creates concrete commands with their document and arguments.
- **Document (receiver)** performs text mutations and returns removed text.
- **Command** defines the operations the manager can request.
- **InsertCommand / DeleteCommand** store edit-specific data and implement those operations.
- **UndoRedoManager (invoker)** chooses when to execute or undo commands and retains history.

Why put execute and undo in an interface? The manager needs a common contract
for reversible edits. It should not need an `if` or `switch` for each edit type.
The interface promises behavior; the concrete class supplies the implementation
and remembers the data that behavior requires. Not all Command Pattern designs
require undo; this interface includes it because our subsystem requires reversible edits.

Before editing, explain how `manager.execute(new InsertCommand(...))` eventually
changes the StringBuilder. Which object chooses the operation, which stores its
arguments, and which actually changes the text?

## Your implementation tasks

### 1. Complete reversal of deletion

The forward deletion works, but undoing it does not restore the document.
Finish the existing TODO. Decide what state must be captured and when.

Acceptance: `Ritesh Kumar -> delete(6, 6) -> Ritesh -> undo -> Ritesh Kumar`.
Then verify redo and another undo. Also try deleting text in the middle.

### 2. Investigate multiple undo/redo ordering

A single undo/redo works, but multiple operations do not reconstruct the original
edit sequence correctly. Inspect how the deque is being used as a history structure.

Acceptance: insert A, B, C at position zero to get CBA; undo twice to get A;
redo once to get BA; redo again to get CBA.

Draw both deque ends after every operation before choosing a fix.

### 3. Enforce the configured limit

The configured limit is not consistently respected. Check boundaries, including
zero, rather than only one large history.

Acceptance: with limit 3 and five inserts A through E at position zero, retain
exactly three undoable commands. Three undos leave BA; another undo returns false.
With limit 0, edits work and undo returns false. Also check limit 1.

Eviction forgets an operation's undo capability; it does not reverse that edit.

### 4. Handle a new edit after undo

The current implementation permits a redo after the user has chosen a new edit
path. Decide what the history should represent after that change.

Acceptance: create CBA, undo twice to A, insert X at position zero to get XA.
Redo must return false and leave XA unchanged. Undo must still reverse X.

## How to demonstrate each fix

For each task, provide:

1. A small operation sequence that exposes the problem.
2. Expected and actual text, undo history, and redo history.
3. The rule the implementation violates and why that matters.
4. Your proposed change and a boundary case that might defeat it.
5. Your implementation, followed by a rerun of the demonstration.

The demo prints observations; it is not an automated test suite. Add small
assertion-based checks if helpful. Keep one document per manager, use fresh command
objects for new edits, and route edits through the manager for this exercise.

## Interview follow-ups — answer after the repairs

1. **Medium:** Why use a Command interface instead of manager methods such as
   insert(position, text) and delete(position, length)? What tradeoff does it introduce?
2. **Medium:** Why are position and length insufficient to undo a deletion?
   When should the command capture the missing information: construction or execution?
3. **Medium:** Why can redo call execute again here? Under what assumptions is that
   valid? Would you add redo to the interface for other command types?
4. **Medium:** State the ordering invariant for each deque. Why is ArrayDeque a
   reasonable choice compared with LinkedList, Stack, or an ArrayList?
5. **Medium–hard:** Give separate complexity estimates for history bookkeeping
   and StringBuilder mutation. Include text length, edit length, and retained memory.
6. **Hard:** If execute, undo, or redo throws, what should happen to both histories?
   What if the command changes half the document before throwing?
7. **Hard:** What breaks if the same command object is submitted twice, or someone
   changes Document directly? Where would you enforce the lifecycle rules?
8. **Hard:** Add a hypothetical ReplaceCommand without editing the manager's type
   logic. What state would it store, and how would you make a failed replacement safe?
9. **Hard:** Compare full-document snapshots with command/delta history for large
   deletions and tiny frequent inserts. Does a count limit guarantee bounded bytes?
10. **Hard:** How would you group typing a word into one undo step? Describe a
    composite command's execute/undo order and what happens when a child fails.

These are discussion questions, not requests to implement extra features yet.
