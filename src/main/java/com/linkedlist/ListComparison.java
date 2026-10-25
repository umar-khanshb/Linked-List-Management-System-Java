package com.linkedlist;

/**
 * Prints a comparison between Singly and Doubly Linked Lists
 * as implemented in this project.
 */
public final class ListComparison {
    private static final String ROW = "| %-26s | %-32s | %-32s |%n";
    private static final String RULE =
            "+" + "-".repeat(28) + "+" + "-".repeat(34) + "+" + "-".repeat(34) + "+";

    private ListComparison() {
    }

    public static void print() {
        System.out.println(RULE);
        System.out.printf(ROW, "Feature", "Singly Linked List", "Doubly Linked List");
        System.out.println(RULE);
        row("Node structure", "data + next", "data + prev + next");
        row("References per node", "1", "2");
        row("Forward traversal", "Yes", "Yes");
        row("Backward traversal", "No (needs extra work)", "Yes (using prev)");
        row("Memory overhead", "Lower (1 reference/node)", "Higher (2 references/node)");
        row("Insert at beginning", "O(1)", "O(1)");
        row("Insert at end", "O(n) (no tail in this project)", "O(1) (tail maintained)");
        row("Delete at beginning", "O(1)", "O(1)");
        row("Delete at end", "O(n) (find 2nd-last node)", "O(1) (tail.prev is known)");
        row("Delete a known node", "O(n) (needs predecessor)", "O(1) (has prev and next)");
        row("Search by value", "O(n)", "O(n)");
        row("Reverse", "O(n) (re-point next)", "O(n) (swap prev and next)");
        row("Typical use cases", "Stacks, simple queues,", "Browser history, undo/redo,");
        row("", "adjacency lists", "playlists, LRU caches");
        System.out.println(RULE);
        System.out.println();
        System.out.println("Key points:");
        System.out.println("1. A singly linked list stores fewer references per node, so it uses less memory.");
        System.out.println("2. A doubly linked list can be traversed in both directions, but every node");
        System.out.println("   stores an extra reference and every insert or delete must update more links.");
        System.out.println("3. The complexities above depend on the implementation. Here the singly list has");
        System.out.println("   only a head reference, while the doubly list keeps head and tail.");
        System.out.println("4. A linked list is not always faster than an array. Arrays give O(1) access by");
        System.out.println("   index and are cache-friendly; linked lists are useful when frequent insertion");
        System.out.println("   and deletion happen at known nodes and index access is not needed.");
    }

    private static void row(String feature, String singly, String doubly) {
        System.out.printf(ROW, feature, singly, doubly);
    }
}
