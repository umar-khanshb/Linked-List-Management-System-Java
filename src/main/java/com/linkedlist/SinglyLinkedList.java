package com.linkedlist;

import java.util.NoSuchElementException;

/**
 * A Singly Linked List of int values, implemented from scratch.
 *
 * <p>Design notes:</p>
 * <ul>
 *   <li>The list keeps only a {@code head} reference (no tail), so insertion at the end
 *       and deletion at the end are O(n).</li>
 *   <li>A {@code size} counter is maintained after every insertion and deletion,
 *       so {@link #size()} is O(1).</li>
 *   <li>Positions are 1-based.</li>
 *   <li>Middle rule: for an even number of nodes, the SECOND of the two middle nodes
 *       is returned (e.g. 10, 20, 30, 40 gives 30).</li>
 *   <li>The class does no printing. It returns values or throws exceptions, and
 *       {@code Main} turns those into user messages.</li>
 * </ul>
 */
public class SinglyLinkedList {
    private SinglyNode head;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // ------------------------------------------------------------------ basics

    public boolean isEmpty() {
        return head == null;
    }

    /** Number of nodes. O(1) because the size counter is maintained. */
    public int size() {
        return size;
    }

    // --------------------------------------------------------------- insertion

    /** Insert at the beginning. O(1). */
    public void insertAtBeginning(int value) {
        SinglyNode node = new SinglyNode(value);
        node.setNext(head);
        head = node;
        size++;
    }

    /** Insert at the end. O(n) because there is no tail reference. */
    public void insertAtEnd(int value) {
        SinglyNode node = new SinglyNode(value);
        if (head == null) {
            head = node;
        } else {
            SinglyNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(node);
        }
        size++;
    }

    /**
     * Insert at a 1-based position. Valid range is 1 to size + 1.
     *
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    public void insertAtPosition(int position, int value) {
        if (position < 1 || position > size + 1) {
            throw new IndexOutOfBoundsException(
                    "Invalid position " + position + ". Valid range is 1 to " + (size + 1) + ".");
        }
        if (position == 1) {
            insertAtBeginning(value);
            return;
        }
        SinglyNode previous = nodeAt(position - 1);
        SinglyNode node = new SinglyNode(value);
        node.setNext(previous.getNext());
        previous.setNext(node);
        size++;
    }

    /**
     * Insert a new value immediately after the first node holding {@code target}.
     *
     * @return true if the target was found and the value inserted
     */
    public boolean insertAfter(int target, int value) {
        SinglyNode found = findNode(target);
        if (found == null) {
            return false;
        }
        SinglyNode node = new SinglyNode(value);
        node.setNext(found.getNext());
        found.setNext(node);
        size++;
        return true;
    }

    /**
     * Insert a new value immediately before the first node holding {@code target}.
     *
     * @return true if the target was found and the value inserted
     */
    public boolean insertBefore(int target, int value) {
        if (head == null) {
            return false;
        }
        if (head.getData() == target) {
            insertAtBeginning(value);
            return true;
        }
        SinglyNode previous = head;
        while (previous.getNext() != null && previous.getNext().getData() != target) {
            previous = previous.getNext();
        }
        if (previous.getNext() == null) {
            return false;
        }
        SinglyNode node = new SinglyNode(value);
        node.setNext(previous.getNext());
        previous.setNext(node);
        size++;
        return true;
    }

    // ---------------------------------------------------------------- deletion

    /**
     * Delete the first node. O(1).
     *
     * @return the deleted value
     * @throws NoSuchElementException if the list is empty
     */
    public int deleteFromBeginning() {
        requireNonEmpty();
        int value = head.getData();
        head = head.getNext();
        size--;
        return value;
    }

    /**
     * Delete the last node. O(n) because we must find the second-last node.
     *
     * @return the deleted value
     * @throws NoSuchElementException if the list is empty
     */
    public int deleteFromEnd() {
        requireNonEmpty();
        if (head.getNext() == null) {
            return deleteFromBeginning();
        }
        SinglyNode secondLast = head;
        while (secondLast.getNext().getNext() != null) {
            secondLast = secondLast.getNext();
        }
        int value = secondLast.getNext().getData();
        secondLast.setNext(null);
        size--;
        return value;
    }

    /**
     * Delete the node at a 1-based position. Valid range is 1 to size.
     *
     * @return the deleted value
     * @throws NoSuchElementException    if the list is empty
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    public int deleteFromPosition(int position) {
        requireNonEmpty();
        if (position < 1 || position > size) {
            throw new IndexOutOfBoundsException(
                    "Invalid position " + position + ". Valid range is 1 to " + size + ".");
        }
        if (position == 1) {
            return deleteFromBeginning();
        }
        SinglyNode previous = nodeAt(position - 1);
        int value = previous.getNext().getData();
        previous.setNext(previous.getNext().getNext());
        size--;
        return value;
    }

    /**
     * Delete the FIRST node whose data equals {@code value}.
     *
     * @return true if a node was deleted, false if the value was not found
     */
    public boolean deleteByValue(int value) {
        if (head == null) {
            return false;
        }
        if (head.getData() == value) {
            deleteFromBeginning();
            return true;
        }
        SinglyNode previous = head;
        while (previous.getNext() != null && previous.getNext().getData() != value) {
            previous = previous.getNext();
        }
        if (previous.getNext() == null) {
            return false;
        }
        previous.setNext(previous.getNext().getNext());
        size--;
        return true;
    }

    // ------------------------------------------------------ search and update

    /**
     * Search for a value.
     *
     * @return the 1-based position of the first match, or -1 if not found
     */
    public int search(int value) {
        int position = 1;
        SinglyNode current = head;
        while (current != null) {
            if (current.getData() == value) {
                return position;
            }
            current = current.getNext();
            position++;
        }
        return -1;
    }

    /**
     * Replace the first occurrence of {@code oldValue} with {@code newValue}.
     *
     * @return true if the value was found and updated
     */
    public boolean update(int oldValue, int newValue) {
        SinglyNode found = findNode(oldValue);
        if (found == null) {
            return false;
        }
        found.setData(newValue);
        return true;
    }

    // -------------------------------------------------------------- statistics

    /** @throws NoSuchElementException if the list is empty */
    public int findMaximum() {
        requireNonEmpty();
        int max = head.getData();
        for (SinglyNode current = head.getNext(); current != null; current = current.getNext()) {
            if (current.getData() > max) {
                max = current.getData();
            }
        }
        return max;
    }

    /** @throws NoSuchElementException if the list is empty */
    public int findMinimum() {
        requireNonEmpty();
        int min = head.getData();
        for (SinglyNode current = head.getNext(); current != null; current = current.getNext()) {
            if (current.getData() < min) {
                min = current.getData();
            }
        }
        return min;
    }

    /**
     * Find the middle element using slow and fast references.
     * For an even number of nodes the SECOND middle node is returned.
     *
     * @throws NoSuchElementException if the list is empty
     */
    public int findMiddle() {
        requireNonEmpty();
        SinglyNode slow = head;
        SinglyNode fast = head;
        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow.getData();
    }

    // -------------------------------------------------- reverse, sort, cleanup

    /** Reverse the list iteratively using previous, current and next. O(n), O(1) extra space. */
    public void reverse() {
        SinglyNode previous = null;
        SinglyNode current = head;
        while (current != null) {
            SinglyNode next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }
        head = previous;
    }

    /** Sort ascending with Bubble Sort, swapping node data. O(n^2). */
    public void sort() {
        if (size < 2) {
            return;
        }
        SinglyNode end = null; // nodes from 'end' onwards are already in final position
        boolean swapped;
        do {
            swapped = false;
            SinglyNode current = head;
            while (current.getNext() != end) {
                SinglyNode next = current.getNext();
                if (current.getData() > next.getData()) {
                    int temp = current.getData();
                    current.setData(next.getData());
                    next.setData(temp);
                    swapped = true;
                }
                current = next;
            }
            end = current;
        } while (swapped);
    }

    /**
     * Remove repeated values, keeping the first occurrence of each. O(n^2).
     *
     * @return the number of nodes removed
     */
    public int removeDuplicates() {
        int removed = 0;
        for (SinglyNode current = head; current != null; current = current.getNext()) {
            SinglyNode runner = current;
            while (runner.getNext() != null) {
                if (runner.getNext().getData() == current.getData()) {
                    runner.setNext(runner.getNext().getNext());
                    size--;
                    removed++;
                } else {
                    runner = runner.getNext();
                }
            }
        }
        return removed;
    }

    // ----------------------------------------------------- display and testing

    /** Returns text such as "10 -> 20 -> 30 -> NULL", or "NULL" for an empty list. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (SinglyNode current = head; current != null; current = current.getNext()) {
            sb.append(current.getData()).append(" -> ");
        }
        sb.append("NULL");
        return sb.toString();
    }

    /** Copies the values into an array (used by the tests and nothing else). */
    public int[] toArray() {
        int[] result = new int[size];
        int index = 0;
        for (SinglyNode current = head; current != null && index < size; current = current.getNext()) {
            result[index++] = current.getData();
        }
        return result;
    }

    /**
     * Checks internal consistency: the number of reachable nodes equals the size counter,
     * there is no cycle, and an empty list has a null head.
     */
    public boolean isValid() {
        int count = 0;
        SinglyNode current = head;
        while (current != null) {
            count++;
            if (count > size) {
                return false; // more nodes than size says (or a cycle)
            }
            current = current.getNext();
        }
        return count == size;
    }

    // ---------------------------------------------------------------- helpers

    private void requireNonEmpty() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        }
    }

    private SinglyNode findNode(int value) {
        SinglyNode current = head;
        while (current != null && current.getData() != value) {
            current = current.getNext();
        }
        return current;
    }

    /** Returns the node at a 1-based position (caller guarantees 1 <= position <= size). */
    private SinglyNode nodeAt(int position) {
        SinglyNode current = head;
        for (int i = 1; i < position; i++) {
            current = current.getNext();
        }
        return current;
    }
}
