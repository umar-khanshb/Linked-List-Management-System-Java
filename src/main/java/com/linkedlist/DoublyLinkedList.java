package com.linkedlist;

import java.util.NoSuchElementException;

/**
 * A Doubly Linked List of int values, implemented from scratch.
 *
 * <p>Design notes:</p>
 * <ul>
 *   <li>Both {@code head} and {@code tail} references are maintained, so insertion at the end
 *       and deletion at the end are O(1), and the list can be traversed backward.</li>
 *   <li>A {@code size} counter is updated after every insertion and deletion.</li>
 *   <li>Positions are 1-based. For a position in the second half of the list the search
 *       starts from the tail, which halves the number of steps.</li>
 *   <li>Middle rule: for an even number of nodes, the SECOND of the two middle nodes
 *       is returned (e.g. 10, 20, 30, 40 gives 30). This matches the singly linked list.</li>
 *   <li>The class does no printing. It returns values or throws exceptions, and
 *       {@code Main} turns those into user messages.</li>
 * </ul>
 */
public class DoublyLinkedList {
    private DoublyNode head;
    private DoublyNode tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
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
        DoublyNode node = new DoublyNode(value);
        if (head == null) {
            head = node;
            tail = node;
            size++;
        } else {
            linkBefore(head, node);
        }
    }

    /** Insert at the end. O(1) because the tail reference is maintained. */
    public void insertAtEnd(int value) {
        DoublyNode node = new DoublyNode(value);
        if (tail == null) {
            head = node;
            tail = node;
            size++;
        } else {
            linkAfter(tail, node);
        }
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
        } else if (position == size + 1) {
            insertAtEnd(value);
        } else {
            linkBefore(nodeAt(position), new DoublyNode(value));
        }
    }

    /**
     * Insert a new value immediately after the first node holding {@code target}.
     *
     * @return true if the target was found and the value inserted
     */
    public boolean insertAfter(int target, int value) {
        DoublyNode found = findNode(target);
        if (found == null) {
            return false;
        }
        linkAfter(found, new DoublyNode(value));
        return true;
    }

    /**
     * Insert a new value immediately before the first node holding {@code target}.
     *
     * @return true if the target was found and the value inserted
     */
    public boolean insertBefore(int target, int value) {
        DoublyNode found = findNode(target);
        if (found == null) {
            return false;
        }
        linkBefore(found, new DoublyNode(value));
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
        unlink(head);
        return value;
    }

    /**
     * Delete the last node. O(1) because of the tail and prev references.
     *
     * @return the deleted value
     * @throws NoSuchElementException if the list is empty
     */
    public int deleteFromEnd() {
        requireNonEmpty();
        int value = tail.getData();
        unlink(tail);
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
        DoublyNode node = nodeAt(position);
        int value = node.getData();
        unlink(node);
        return value;
    }

    /**
     * Delete the FIRST node whose data equals {@code value}.
     *
     * @return true if a node was deleted, false if the value was not found
     */
    public boolean deleteByValue(int value) {
        DoublyNode found = findNode(value);
        if (found == null) {
            return false;
        }
        unlink(found);
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
        DoublyNode current = head;
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
        DoublyNode found = findNode(oldValue);
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
        for (DoublyNode current = head.getNext(); current != null; current = current.getNext()) {
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
        for (DoublyNode current = head.getNext(); current != null; current = current.getNext()) {
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
        DoublyNode slow = head;
        DoublyNode fast = head;
        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        return slow.getData();
    }

    // -------------------------------------------------- reverse, sort, cleanup

    /**
     * Reverse the list by swapping the prev and next references of every node,
     * then swapping head and tail. O(n).
     */
    public void reverse() {
        DoublyNode current = head;
        while (current != null) {
            DoublyNode oldNext = current.getNext();
            current.setNext(current.getPrev());
            current.setPrev(oldNext);
            current = oldNext; // continue with the node that used to be next
        }
        DoublyNode temp = head;
        head = tail;
        tail = temp;
    }

    /** Sort ascending with Bubble Sort, swapping node data. O(n^2). */
    public void sort() {
        if (size < 2) {
            return;
        }
        DoublyNode end = null; // nodes from 'end' onwards are already in final position
        boolean swapped;
        do {
            swapped = false;
            DoublyNode current = head;
            while (current.getNext() != end) {
                DoublyNode next = current.getNext();
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
        for (DoublyNode current = head; current != null; current = current.getNext()) {
            DoublyNode runner = current.getNext();
            while (runner != null) {
                DoublyNode following = runner.getNext();
                if (runner.getData() == current.getData()) {
                    unlink(runner);
                    removed++;
                }
                runner = following;
            }
        }
        return removed;
    }

    // ----------------------------------------------------- display and testing

    /** Forward text such as "NULL <- 10 <-> 20 <-> 30 -> NULL", or "NULL" for an empty list. */
    public String toForwardString() {
        if (head == null) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder("NULL <- ");
        for (DoublyNode current = head; current != null; current = current.getNext()) {
            sb.append(current.getData());
            sb.append(current.getNext() != null ? " <-> " : " -> NULL");
        }
        return sb.toString();
    }

    /** Backward text such as "NULL <- 30 <-> 20 <-> 10 -> NULL", or "NULL" for an empty list. */
    public String toBackwardString() {
        if (tail == null) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder("NULL <- ");
        for (DoublyNode current = tail; current != null; current = current.getPrev()) {
            sb.append(current.getData());
            sb.append(current.getPrev() != null ? " <-> " : " -> NULL");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toForwardString();
    }

    /** Values from head to tail (used by the tests and nothing else). */
    public int[] toArray() {
        int[] result = new int[size];
        int index = 0;
        for (DoublyNode current = head; current != null && index < size; current = current.getNext()) {
            result[index++] = current.getData();
        }
        return result;
    }

    /** Values from tail to head, following prev references (used by the tests only). */
    public int[] toBackwardArray() {
        int[] result = new int[size];
        int index = 0;
        for (DoublyNode current = tail; current != null && index < size; current = current.getPrev()) {
            result[index++] = current.getData();
        }
        return result;
    }

    /**
     * Checks internal consistency: head.prev and tail.next are null, every next/prev pair
     * agrees, the last forward node is the tail, and forward and backward counts equal size.
     */
    public boolean isValid() {
        if (head == null || tail == null) {
            return head == null && tail == null && size == 0;
        }
        if (head.getPrev() != null || tail.getNext() != null) {
            return false;
        }
        int forward = 0;
        DoublyNode last = null;
        for (DoublyNode current = head; current != null; current = current.getNext()) {
            if (++forward > size) {
                return false;
            }
            if (current.getPrev() != last) {
                return false;
            }
            last = current;
        }
        if (forward != size || last != tail) {
            return false;
        }
        int backward = 0;
        for (DoublyNode current = tail; current != null; current = current.getPrev()) {
            if (++backward > size) {
                return false;
            }
        }
        return backward == size;
    }

    // ---------------------------------------------------------------- helpers

    private void requireNonEmpty() {
        if (head == null) {
            throw new NoSuchElementException("The list is empty.");
        }
    }

    private DoublyNode findNode(int value) {
        DoublyNode current = head;
        while (current != null && current.getData() != value) {
            current = current.getNext();
        }
        return current;
    }

    /** Returns the node at a 1-based position, starting from the nearer end. */
    private DoublyNode nodeAt(int position) {
        DoublyNode current;
        if (position <= size / 2) {
            current = head;
            for (int i = 1; i < position; i++) {
                current = current.getNext();
            }
        } else {
            current = tail;
            for (int i = size; i > position; i--) {
                current = current.getPrev();
            }
        }
        return current;
    }

    /** Links {@code node} immediately before {@code target}, fixing all four references. */
    private void linkBefore(DoublyNode target, DoublyNode node) {
        node.setNext(target);
        node.setPrev(target.getPrev());
        if (target.getPrev() == null) {
            head = node;
        } else {
            target.getPrev().setNext(node);
        }
        target.setPrev(node);
        size++;
    }

    /** Links {@code node} immediately after {@code target}, fixing all four references. */
    private void linkAfter(DoublyNode target, DoublyNode node) {
        node.setPrev(target);
        node.setNext(target.getNext());
        if (target.getNext() == null) {
            tail = node;
        } else {
            target.getNext().setPrev(node);
        }
        target.setNext(node);
        size++;
    }

    /** Removes {@code node} from the list, fixing head, tail and neighbouring references. */
    private void unlink(DoublyNode node) {
        DoublyNode before = node.getPrev();
        DoublyNode after = node.getNext();
        if (before == null) {
            head = after;
        } else {
            before.setNext(after);
        }
        if (after == null) {
            tail = before;
        } else {
            after.setPrev(before);
        }
        node.setPrev(null);
        node.setNext(null);
        size--;
    }
}
