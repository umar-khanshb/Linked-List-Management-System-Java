package com.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Self-contained test harness (no JUnit needed).
 * Every test builds its own fresh lists, so tests do not depend on each other or on run order.
 *
 * Run: see README.md. Exit code is 0 if all tests pass, 1 otherwise.
 */
public class ListTests {

    private interface Test {
        void run() throws Exception;
    }

    private static int passed = 0;
    private static final List<String> failures = new ArrayList<>();

    // ------------------------------------------------------------ assertions

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void checkArray(int[] expected, int[] actual, String what) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(what + ": expected " + Arrays.toString(expected)
                    + " but was " + Arrays.toString(actual));
        }
    }

    private static void checkEquals(int expected, int actual, String what) {
        if (expected != actual) {
            throw new AssertionError(what + ": expected " + expected + " but was " + actual);
        }
    }

    private static void checkEquals(String expected, String actual, String what) {
        if (!expected.equals(actual)) {
            throw new AssertionError(what + ": expected \"" + expected + "\" but was \"" + actual + "\"");
        }
    }

    /** Fails unless running the code throws the given exception type. */
    private static void checkThrows(Class<? extends Throwable> type, Test code, String what) {
        try {
            code.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new AssertionError(what + ": expected " + type.getSimpleName() + " but got " + t);
        }
        throw new AssertionError(what + ": expected " + type.getSimpleName() + " but nothing was thrown");
    }

    /** Verifies a doubly list is internally consistent AND both directions agree. */
    private static void checkDoublyConsistent(DoublyLinkedList list, String what) {
        check(list.isValid(), what + ": isValid() is false");
        int[] forward = list.toArray();
        int[] backward = list.toBackwardArray();
        int[] reversedBackward = new int[backward.length];
        for (int i = 0; i < backward.length; i++) {
            reversedBackward[i] = backward[backward.length - 1 - i];
        }
        checkArray(forward, reversedBackward, what + ": backward is not the reverse of forward");
        checkEquals(forward.length, list.size(), what + ": size counter");
    }

    private static SinglyLinkedList singly(int... values) {
        SinglyLinkedList list = new SinglyLinkedList();
        for (int v : values) {
            list.insertAtEnd(v);
        }
        return list;
    }

    private static DoublyLinkedList doubly(int... values) {
        DoublyLinkedList list = new DoublyLinkedList();
        for (int v : values) {
            list.insertAtEnd(v);
        }
        return list;
    }

    private static void run(String name, Test test) {
        try {
            test.run();
            passed++;
            System.out.println("PASS  " + name);
        } catch (Throwable t) {
            failures.add(name + " -> " + t.getMessage());
            System.out.println("FAIL  " + name + " -> " + t);
        }
    }

    // ----------------------------------------------------------------- main

    public static void main(String[] args) {
        singlyTests();
        doublyTests();

        System.out.println();
        System.out.println("Passed: " + passed + "   Failed: " + failures.size()
                + "   Total: " + (passed + failures.size()));
        if (!failures.isEmpty()) {
            System.out.println("Failures:");
            for (String f : failures) {
                System.out.println(" - " + f);
            }
            System.exit(1);
        }
        System.out.println("ALL TESTS PASSED");
    }

    // ------------------------------------------------------- singly tests

    private static void singlyTests() {
        System.out.println("=== SinglyLinkedList ===");

        run("S empty list: display, size, search, max/min/middle", () -> {
            SinglyLinkedList l = new SinglyLinkedList();
            check(l.isEmpty(), "isEmpty");
            checkEquals(0, l.size(), "size");
            checkEquals("NULL", l.toString(), "display");
            checkEquals(-1, l.search(5), "search");
            checkThrows(NoSuchElementException.class, l::findMaximum, "max");
            checkThrows(NoSuchElementException.class, l::findMinimum, "min");
            checkThrows(NoSuchElementException.class, l::findMiddle, "middle");
            check(l.isValid(), "valid");
        });

        run("S insert into empty list (beginning and end)", () -> {
            SinglyLinkedList a = new SinglyLinkedList();
            a.insertAtBeginning(7);
            checkArray(new int[]{7}, a.toArray(), "beginning");
            SinglyLinkedList b = new SinglyLinkedList();
            b.insertAtEnd(7);
            checkArray(new int[]{7}, b.toArray(), "end");
            check(a.isValid() && b.isValid(), "valid");
        });

        run("S insert at beginning and end keeps order", () -> {
            SinglyLinkedList l = singly(10, 20, 30);
            l.insertAtBeginning(5);
            l.insertAtEnd(40);
            checkArray(new int[]{5, 10, 20, 30, 40}, l.toArray(), "list");
            checkEquals("5 -> 10 -> 20 -> 30 -> 40 -> NULL", l.toString(), "display");
            checkEquals(5, l.size(), "size");
            check(l.isValid(), "valid");
        });

        run("S insert at position: first, middle, last valid", () -> {
            SinglyLinkedList l = singly(10, 30);
            l.insertAtPosition(1, 5);
            l.insertAtPosition(3, 20);
            l.insertAtPosition(5, 40); // size is 4, so 5 = last valid
            checkArray(new int[]{5, 10, 20, 30, 40}, l.toArray(), "list");
            check(l.isValid(), "valid");
        });

        run("S insert at position on empty list (position 1 is valid)", () -> {
            SinglyLinkedList l = new SinglyLinkedList();
            l.insertAtPosition(1, 9);
            checkArray(new int[]{9}, l.toArray(), "list");
        });

        run("S insert at invalid positions throws and leaves list unchanged", () -> {
            SinglyLinkedList l = singly(1, 2, 3);
            checkThrows(IndexOutOfBoundsException.class, () -> l.insertAtPosition(0, 9), "pos 0");
            checkThrows(IndexOutOfBoundsException.class, () -> l.insertAtPosition(-2, 9), "pos -2");
            checkThrows(IndexOutOfBoundsException.class, () -> l.insertAtPosition(5, 9), "pos 5");
            checkArray(new int[]{1, 2, 3}, l.toArray(), "list unchanged");
            check(l.isValid(), "valid");
        });

        run("S insert after a node (middle, last, missing, empty)", () -> {
            SinglyLinkedList l = singly(10, 30);
            check(l.insertAfter(10, 20), "after 10");
            check(l.insertAfter(30, 40), "after last");
            check(!l.insertAfter(99, 1), "missing target");
            checkArray(new int[]{10, 20, 30, 40}, l.toArray(), "list");
            check(!new SinglyLinkedList().insertAfter(1, 2), "empty list");
            check(l.isValid(), "valid");
        });

        run("S insert before a node (head, middle, missing, empty)", () -> {
            SinglyLinkedList l = singly(20, 40);
            check(l.insertBefore(20, 10), "before head");
            check(l.insertBefore(40, 30), "before middle");
            check(!l.insertBefore(99, 1), "missing target");
            checkArray(new int[]{10, 20, 30, 40}, l.toArray(), "list");
            check(!new SinglyLinkedList().insertBefore(1, 2), "empty list");
            check(l.isValid(), "valid");
        });

        run("S insert after/before uses the FIRST matching node", () -> {
            SinglyLinkedList a = singly(5, 7, 5);
            a.insertAfter(5, 1);
            checkArray(new int[]{5, 1, 7, 5}, a.toArray(), "after");
            SinglyLinkedList b = singly(5, 7, 5);
            b.insertBefore(5, 1);
            checkArray(new int[]{1, 5, 7, 5}, b.toArray(), "before");
        });

        run("S delete from beginning (multi, single, empty)", () -> {
            SinglyLinkedList l = singly(1, 2, 3);
            checkEquals(1, l.deleteFromBeginning(), "deleted");
            checkArray(new int[]{2, 3}, l.toArray(), "list");
            SinglyLinkedList one = singly(8);
            checkEquals(8, one.deleteFromBeginning(), "single");
            check(one.isEmpty() && one.size() == 0 && one.isValid(), "now empty");
            checkThrows(NoSuchElementException.class, () -> new SinglyLinkedList().deleteFromBeginning(), "empty");
        });

        run("S delete from end (multi, single, empty)", () -> {
            SinglyLinkedList l = singly(1, 2, 3);
            checkEquals(3, l.deleteFromEnd(), "deleted");
            checkArray(new int[]{1, 2}, l.toArray(), "list");
            l.insertAtEnd(9); // the new end must attach correctly
            checkArray(new int[]{1, 2, 9}, l.toArray(), "append after delete");
            SinglyLinkedList one = singly(8);
            checkEquals(8, one.deleteFromEnd(), "single");
            check(one.isEmpty() && one.isValid(), "now empty");
            checkThrows(NoSuchElementException.class, () -> new SinglyLinkedList().deleteFromEnd(), "empty");
        });

        run("S delete from position: first, middle, last, invalid, empty", () -> {
            SinglyLinkedList l = singly(10, 20, 30, 40, 50);
            checkEquals(10, l.deleteFromPosition(1), "first");
            checkEquals(30, l.deleteFromPosition(2), "middle");
            checkEquals(50, l.deleteFromPosition(3), "last");
            checkArray(new int[]{20, 40}, l.toArray(), "list");
            checkThrows(IndexOutOfBoundsException.class, () -> l.deleteFromPosition(0), "pos 0");
            checkThrows(IndexOutOfBoundsException.class, () -> l.deleteFromPosition(3), "pos 3");
            checkArray(new int[]{20, 40}, l.toArray(), "unchanged after invalid");
            checkThrows(NoSuchElementException.class, () -> new SinglyLinkedList().deleteFromPosition(1), "empty");
            check(l.isValid(), "valid");
        });

        run("S delete by value: head, middle, last, missing, duplicates, empty", () -> {
            SinglyLinkedList l = singly(1, 2, 3, 2, 4);
            check(l.deleteByValue(1), "head");
            check(l.deleteByValue(2), "first 2 only");
            checkArray(new int[]{3, 2, 4}, l.toArray(), "duplicate handling");
            check(l.deleteByValue(4), "last");
            check(!l.deleteByValue(99), "missing");
            checkArray(new int[]{3, 2}, l.toArray(), "list");
            check(!new SinglyLinkedList().deleteByValue(1), "empty");
            check(l.isValid(), "valid");
        });

        run("S search finds first position and reports missing", () -> {
            SinglyLinkedList l = singly(4, 8, 15, 8);
            checkEquals(1, l.search(4), "head");
            checkEquals(2, l.search(8), "first of duplicates");
            checkEquals(3, l.search(15), "middle");
            checkEquals(-1, l.search(99), "missing");
        });

        run("S update existing, missing, and first occurrence only", () -> {
            SinglyLinkedList l = singly(1, 2, 2);
            check(l.update(2, 9), "existing");
            checkArray(new int[]{1, 9, 2}, l.toArray(), "list");
            check(!l.update(77, 1), "missing");
            check(!new SinglyLinkedList().update(1, 2), "empty");
            checkEquals(3, l.size(), "size unchanged");
        });

        run("S max/min with negatives, zero and duplicates", () -> {
            SinglyLinkedList l = singly(-5, 0, 12, -20, 12);
            checkEquals(12, l.findMaximum(), "max");
            checkEquals(-20, l.findMinimum(), "min");
            SinglyLinkedList one = singly(-3);
            checkEquals(-3, one.findMaximum(), "single max");
            checkEquals(-3, one.findMinimum(), "single min");
        });

        run("S middle element: odd, even (second middle), single", () -> {
            checkEquals(30, singly(10, 20, 30, 40, 50).findMiddle(), "odd");
            checkEquals(30, singly(10, 20, 30, 40).findMiddle(), "even uses second middle");
            checkEquals(20, singly(10, 20).findMiddle(), "two nodes");
            checkEquals(10, singly(10).findMiddle(), "single");
        });

        run("S reverse: empty, single, multi, twice returns original", () -> {
            SinglyLinkedList empty = new SinglyLinkedList();
            empty.reverse();
            check(empty.isEmpty() && empty.isValid(), "empty");
            SinglyLinkedList one = singly(1);
            one.reverse();
            checkArray(new int[]{1}, one.toArray(), "single");
            SinglyLinkedList l = singly(10, 20, 30, 40, 50);
            l.reverse();
            checkArray(new int[]{50, 40, 30, 20, 10}, l.toArray(), "multi");
            l.reverse();
            checkArray(new int[]{10, 20, 30, 40, 50}, l.toArray(), "twice");
            check(l.isValid(), "valid");
        });

        run("S sort: empty, single, example, duplicates, negatives, sorted, descending", () -> {
            SinglyLinkedList empty = new SinglyLinkedList();
            empty.sort();
            check(empty.isEmpty(), "empty");
            SinglyLinkedList one = singly(3);
            one.sort();
            checkArray(new int[]{3}, one.toArray(), "single");
            SinglyLinkedList a = singly(40, 10, 30, 20, 50);
            a.sort();
            checkArray(new int[]{10, 20, 30, 40, 50}, a.toArray(), "example");
            SinglyLinkedList b = singly(3, -1, 3, 0, -1);
            b.sort();
            checkArray(new int[]{-1, -1, 0, 3, 3}, b.toArray(), "duplicates/negatives");
            SinglyLinkedList c = singly(1, 2, 3);
            c.sort();
            checkArray(new int[]{1, 2, 3}, c.toArray(), "already sorted");
            SinglyLinkedList d = singly(5, 4, 3, 2, 1);
            d.sort();
            checkArray(new int[]{1, 2, 3, 4, 5}, d.toArray(), "descending");
            check(a.isValid() && b.isValid() && d.isValid(), "valid");
        });

        run("S remove duplicates: none, many, all same, empty, single", () -> {
            SinglyLinkedList a = singly(1, 2, 3);
            checkEquals(0, a.removeDuplicates(), "none removed");
            SinglyLinkedList b = singly(1, 2, 1, 3, 2, 1);
            checkEquals(3, b.removeDuplicates(), "removed count");
            checkArray(new int[]{1, 2, 3}, b.toArray(), "keeps first occurrences");
            checkEquals(3, b.size(), "size counter");
            SinglyLinkedList c = singly(4, 4, 4, 4);
            checkEquals(3, c.removeDuplicates(), "all same");
            checkArray(new int[]{4}, c.toArray(), "all same list");
            checkEquals(0, new SinglyLinkedList().removeDuplicates(), "empty");
            checkEquals(0, singly(1).removeDuplicates(), "single");
            check(b.isValid() && c.isValid(), "valid");
        });

        run("S zero and negative values are stored correctly", () -> {
            SinglyLinkedList l = singly(0, -1, -100);
            checkEquals("0 -> -1 -> -100 -> NULL", l.toString(), "display");
            checkEquals(2, l.search(-1), "search negative");
            check(l.deleteByValue(0), "delete zero");
            checkArray(new int[]{-1, -100}, l.toArray(), "list");
        });

        run("S size counter stays correct after a mixed sequence", () -> {
            SinglyLinkedList l = new SinglyLinkedList();
            l.insertAtEnd(1);
            l.insertAtBeginning(2);
            l.insertAtPosition(2, 3);
            l.insertAfter(3, 4);
            l.insertBefore(1, 5);
            l.deleteFromEnd();
            l.deleteFromBeginning();
            l.deleteByValue(3);
            l.deleteFromPosition(1);
            checkEquals(l.toArray().length, l.size(), "size vs actual nodes");
            check(l.isValid(), "valid");
        });
    }

    // ------------------------------------------------------- doubly tests

    private static void doublyTests() {
        System.out.println();
        System.out.println("=== DoublyLinkedList ===");

        run("D empty list: display both ways, size, search, max/min/middle", () -> {
            DoublyLinkedList l = new DoublyLinkedList();
            check(l.isEmpty(), "isEmpty");
            checkEquals(0, l.size(), "size");
            checkEquals("NULL", l.toForwardString(), "forward");
            checkEquals("NULL", l.toBackwardString(), "backward");
            checkEquals(-1, l.search(5), "search");
            checkThrows(NoSuchElementException.class, l::findMaximum, "max");
            checkThrows(NoSuchElementException.class, l::findMinimum, "min");
            checkThrows(NoSuchElementException.class, l::findMiddle, "middle");
            checkDoublyConsistent(l, "empty");
        });

        run("D insert into empty list sets both head and tail", () -> {
            DoublyLinkedList a = new DoublyLinkedList();
            a.insertAtBeginning(7);
            checkDoublyConsistent(a, "beginning");
            checkArray(new int[]{7}, a.toBackwardArray(), "backward single");
            DoublyLinkedList b = new DoublyLinkedList();
            b.insertAtEnd(7);
            checkDoublyConsistent(b, "end");
        });

        run("D insert at beginning/end: forward and backward agree", () -> {
            DoublyLinkedList l = doubly(10, 20, 30);
            l.insertAtBeginning(5);
            l.insertAtEnd(40);
            checkArray(new int[]{5, 10, 20, 30, 40}, l.toArray(), "forward");
            checkArray(new int[]{40, 30, 20, 10, 5}, l.toBackwardArray(), "backward");
            checkEquals("NULL <- 5 <-> 10 <-> 20 <-> 30 <-> 40 -> NULL", l.toForwardString(), "forward text");
            checkEquals("NULL <- 40 <-> 30 <-> 20 <-> 10 <-> 5 -> NULL", l.toBackwardString(), "backward text");
            checkDoublyConsistent(l, "after inserts");
        });

        run("D display format matches the specification", () -> {
            DoublyLinkedList l = doubly(10, 20, 30);
            checkEquals("NULL <- 10 <-> 20 <-> 30 -> NULL", l.toForwardString(), "forward");
            checkEquals("NULL <- 30 <-> 20 <-> 10 -> NULL", l.toBackwardString(), "backward");
        });

        run("D insert at position: first, middle (both halves), last valid", () -> {
            DoublyLinkedList l = doubly(10, 30, 50, 70);
            l.insertAtPosition(1, 5);   // first
            l.insertAtPosition(3, 20);  // first half
            l.insertAtPosition(6, 60);  // second half (search from tail)
            l.insertAtPosition(8, 80);  // size is 7, so 8 = last valid
            checkArray(new int[]{5, 10, 20, 30, 50, 60, 70, 80}, l.toArray(), "list");
            checkDoublyConsistent(l, "after position inserts");
        });

        run("D insert at position on empty list", () -> {
            DoublyLinkedList l = new DoublyLinkedList();
            l.insertAtPosition(1, 9);
            checkArray(new int[]{9}, l.toArray(), "list");
            checkDoublyConsistent(l, "empty->1");
        });

        run("D invalid positions throw and leave list unchanged", () -> {
            DoublyLinkedList l = doubly(1, 2, 3);
            checkThrows(IndexOutOfBoundsException.class, () -> l.insertAtPosition(0, 9), "insert 0");
            checkThrows(IndexOutOfBoundsException.class, () -> l.insertAtPosition(5, 9), "insert 5");
            checkThrows(IndexOutOfBoundsException.class, () -> l.deleteFromPosition(0), "delete 0");
            checkThrows(IndexOutOfBoundsException.class, () -> l.deleteFromPosition(4), "delete 4");
            checkArray(new int[]{1, 2, 3}, l.toArray(), "unchanged");
            checkDoublyConsistent(l, "after invalid ops");
        });

        run("D insert after a node (middle, tail, missing, empty)", () -> {
            DoublyLinkedList l = doubly(10, 30);
            check(l.insertAfter(10, 20), "after 10");
            check(l.insertAfter(30, 40), "after tail updates tail");
            check(!l.insertAfter(99, 1), "missing");
            check(!new DoublyLinkedList().insertAfter(1, 2), "empty");
            checkArray(new int[]{10, 20, 30, 40}, l.toArray(), "list");
            l.insertAtEnd(50); // proves tail was set correctly
            checkArray(new int[]{50, 40, 30, 20, 10}, l.toBackwardArray(), "backward");
            checkDoublyConsistent(l, "after insertAfter");
        });

        run("D insert before a node (head, middle, missing, empty)", () -> {
            DoublyLinkedList l = doubly(20, 40);
            check(l.insertBefore(20, 10), "before head updates head");
            check(l.insertBefore(40, 30), "before middle");
            check(!l.insertBefore(99, 1), "missing");
            check(!new DoublyLinkedList().insertBefore(1, 2), "empty");
            checkArray(new int[]{10, 20, 30, 40}, l.toArray(), "list");
            checkDoublyConsistent(l, "after insertBefore");
        });

        run("D delete from beginning: head/tail stay correct", () -> {
            DoublyLinkedList l = doubly(1, 2, 3);
            checkEquals(1, l.deleteFromBeginning(), "deleted");
            checkArray(new int[]{2, 3}, l.toArray(), "forward");
            checkArray(new int[]{3, 2}, l.toBackwardArray(), "backward");
            checkDoublyConsistent(l, "after delete");
            DoublyLinkedList one = doubly(8);
            checkEquals(8, one.deleteFromBeginning(), "single");
            check(one.isEmpty(), "empty");
            checkDoublyConsistent(one, "single deleted (head and tail null)");
            checkThrows(NoSuchElementException.class, () -> new DoublyLinkedList().deleteFromBeginning(), "empty");
        });

        run("D delete from end: head/tail stay correct", () -> {
            DoublyLinkedList l = doubly(1, 2, 3);
            checkEquals(3, l.deleteFromEnd(), "deleted");
            checkArray(new int[]{1, 2}, l.toArray(), "forward");
            checkArray(new int[]{2, 1}, l.toBackwardArray(), "backward");
            l.insertAtEnd(9);
            checkArray(new int[]{1, 2, 9}, l.toArray(), "append after delete");
            checkDoublyConsistent(l, "after delete+append");
            DoublyLinkedList one = doubly(8);
            checkEquals(8, one.deleteFromEnd(), "single");
            checkDoublyConsistent(one, "single deleted");
            checkThrows(NoSuchElementException.class, () -> new DoublyLinkedList().deleteFromEnd(), "empty");
        });

        run("D delete from position: first, middle, last, empty", () -> {
            DoublyLinkedList l = doubly(10, 20, 30, 40, 50);
            checkEquals(10, l.deleteFromPosition(1), "first");
            checkEquals(30, l.deleteFromPosition(2), "middle");
            checkEquals(50, l.deleteFromPosition(3), "last");
            checkArray(new int[]{20, 40}, l.toArray(), "forward");
            checkArray(new int[]{40, 20}, l.toBackwardArray(), "backward");
            checkDoublyConsistent(l, "after deletes");
            checkThrows(NoSuchElementException.class, () -> new DoublyLinkedList().deleteFromPosition(1), "empty");
        });

        run("D delete by value: head, middle, tail, missing, duplicates, empty", () -> {
            DoublyLinkedList l = doubly(1, 2, 3, 2, 4);
            check(l.deleteByValue(1), "head");
            check(l.deleteByValue(2), "first 2 only");
            checkArray(new int[]{3, 2, 4}, l.toArray(), "duplicate handling");
            check(l.deleteByValue(4), "tail");
            check(!l.deleteByValue(99), "missing");
            checkArray(new int[]{3, 2}, l.toArray(), "list");
            checkArray(new int[]{2, 3}, l.toBackwardArray(), "backward");
            check(!new DoublyLinkedList().deleteByValue(1), "empty");
            checkDoublyConsistent(l, "after deletes");
        });

        run("D search and update", () -> {
            DoublyLinkedList l = doubly(4, 8, 15, 8);
            checkEquals(2, l.search(8), "first of duplicates");
            checkEquals(-1, l.search(99), "missing");
            check(l.update(8, 9), "update existing");
            checkArray(new int[]{4, 9, 15, 8}, l.toArray(), "first occurrence only");
            check(!l.update(77, 1), "update missing");
            check(!new DoublyLinkedList().update(1, 2), "update empty");
            checkDoublyConsistent(l, "after update");
        });

        run("D max/min with negatives and zero", () -> {
            DoublyLinkedList l = doubly(-5, 0, 12, -20, 12);
            checkEquals(12, l.findMaximum(), "max");
            checkEquals(-20, l.findMinimum(), "min");
        });

        run("D middle element: odd, even (second middle), single", () -> {
            checkEquals(30, doubly(10, 20, 30, 40, 50).findMiddle(), "odd");
            checkEquals(30, doubly(10, 20, 30, 40).findMiddle(), "even uses second middle");
            checkEquals(10, doubly(10).findMiddle(), "single");
        });

        run("D reverse: empty, single, multi; head/tail swapped; both directions agree", () -> {
            DoublyLinkedList empty = new DoublyLinkedList();
            empty.reverse();
            checkDoublyConsistent(empty, "empty reverse");
            DoublyLinkedList one = doubly(1);
            one.reverse();
            checkArray(new int[]{1}, one.toArray(), "single");
            checkDoublyConsistent(one, "single reverse");
            DoublyLinkedList l = doubly(10, 20, 30, 40);
            l.reverse();
            checkArray(new int[]{40, 30, 20, 10}, l.toArray(), "forward after reverse");
            checkArray(new int[]{10, 20, 30, 40}, l.toBackwardArray(), "backward after reverse");
            checkDoublyConsistent(l, "after reverse");
            l.insertAtEnd(5);       // uses the new tail
            l.insertAtBeginning(99); // uses the new head
            checkArray(new int[]{99, 40, 30, 20, 10, 5}, l.toArray(), "head/tail usable after reverse");
            checkDoublyConsistent(l, "after reverse + inserts");
            l.reverse();
            l.reverse();
            checkArray(new int[]{99, 40, 30, 20, 10, 5}, l.toArray(), "double reverse");
            checkDoublyConsistent(l, "double reverse");
        });

        run("D sort: empty, single, example, duplicates, negatives; both directions agree", () -> {
            DoublyLinkedList empty = new DoublyLinkedList();
            empty.sort();
            checkDoublyConsistent(empty, "empty sort");
            DoublyLinkedList one = doubly(3);
            one.sort();
            checkDoublyConsistent(one, "single sort");
            DoublyLinkedList a = doubly(40, 10, 30, 20, 50);
            a.sort();
            checkArray(new int[]{10, 20, 30, 40, 50}, a.toArray(), "example forward");
            checkArray(new int[]{50, 40, 30, 20, 10}, a.toBackwardArray(), "example backward");
            DoublyLinkedList b = doubly(3, -1, 3, 0, -1);
            b.sort();
            checkArray(new int[]{-1, -1, 0, 3, 3}, b.toArray(), "duplicates/negatives");
            checkDoublyConsistent(a, "a");
            checkDoublyConsistent(b, "b");
        });

        run("D remove duplicates: none, many, all same, head/tail duplicates", () -> {
            DoublyLinkedList a = doubly(1, 2, 3);
            checkEquals(0, a.removeDuplicates(), "none");
            DoublyLinkedList b = doubly(1, 2, 1, 3, 2, 1);
            checkEquals(3, b.removeDuplicates(), "removed count");
            checkArray(new int[]{1, 2, 3}, b.toArray(), "forward");
            checkArray(new int[]{3, 2, 1}, b.toBackwardArray(), "backward");
            checkDoublyConsistent(b, "b");
            DoublyLinkedList c = doubly(4, 4, 4, 4);
            checkEquals(3, c.removeDuplicates(), "all same");
            checkArray(new int[]{4}, c.toArray(), "all same list");
            checkDoublyConsistent(c, "c (head == tail)");
            DoublyLinkedList d = doubly(1, 2, 1); // duplicate is the tail
            checkEquals(1, d.removeDuplicates(), "tail duplicate");
            d.insertAtEnd(3); // proves tail was updated
            checkArray(new int[]{1, 2, 3}, d.toArray(), "list after tail duplicate");
            checkDoublyConsistent(d, "d");
            checkEquals(0, new DoublyLinkedList().removeDuplicates(), "empty");
        });

        run("D head and tail correct after every kind of deletion", () -> {
            DoublyLinkedList l = doubly(1, 2, 3, 4, 5);
            l.deleteFromBeginning();
            checkDoublyConsistent(l, "after delete beginning");
            l.deleteFromEnd();
            checkDoublyConsistent(l, "after delete end");
            l.deleteFromPosition(2);
            checkDoublyConsistent(l, "after delete position");
            l.deleteByValue(2);
            checkDoublyConsistent(l, "after delete value");
            checkArray(new int[]{4}, l.toArray(), "remaining");
            l.deleteFromEnd();
            check(l.isEmpty(), "empty");
            checkDoublyConsistent(l, "after emptying");
            l.insertAtEnd(1); // list must be reusable after being emptied
            checkDoublyConsistent(l, "reused after emptying");
        });

        run("D size counter stays correct after a mixed sequence", () -> {
            DoublyLinkedList l = new DoublyLinkedList();
            l.insertAtEnd(1);
            l.insertAtBeginning(2);
            l.insertAtPosition(2, 3);
            l.insertAfter(3, 4);
            l.insertBefore(1, 5);
            l.deleteFromEnd();
            l.deleteFromBeginning();
            l.deleteByValue(3);
            l.deleteFromPosition(1);
            checkEquals(l.toArray().length, l.size(), "size vs actual nodes");
            checkDoublyConsistent(l, "mixed sequence");
        });

        run("D randomized stress test keeps the list consistent", () -> {
            java.util.Random random = new java.util.Random(12345); // fixed seed = repeatable
            DoublyLinkedList l = new DoublyLinkedList();
            for (int i = 0; i < 2000; i++) {
                int value = random.nextInt(10) - 5;
                switch (random.nextInt(8)) {
                    case 0: l.insertAtBeginning(value); break;
                    case 1: l.insertAtEnd(value); break;
                    case 2: l.insertAtPosition(1 + random.nextInt(l.size() + 1), value); break;
                    case 3: if (!l.isEmpty()) l.deleteFromBeginning(); break;
                    case 4: if (!l.isEmpty()) l.deleteFromEnd(); break;
                    case 5: if (!l.isEmpty()) l.deleteFromPosition(1 + random.nextInt(l.size())); break;
                    case 6: l.deleteByValue(value); break;
                    default: l.reverse(); break;
                }
                checkDoublyConsistent(l, "stress step " + i);
            }
            l.sort();
            checkDoublyConsistent(l, "stress after sort");
            l.removeDuplicates();
            checkDoublyConsistent(l, "stress after removeDuplicates");
        });
    }
}
