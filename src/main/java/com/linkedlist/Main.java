package com.linkedlist;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Menu-driven console application for the Linked List Management System.
 * All data-structure logic lives in {@link SinglyLinkedList} and {@link DoublyLinkedList};
 * this class only reads input and prints messages.
 */
public class Main {
    private static final String DOUBLE_LINE = "=".repeat(50);
    private static final String STAR_LINE = "*".repeat(10);
    private static final String MIDDLE_RULE =
            "(for an even number of nodes, the second of the two middle nodes is used)";

    private static final InputHelper input = new InputHelper(new Scanner(System.in));
    // The lists live for the whole run, so returning to the main menu keeps their data.
    private static final SinglyLinkedList singly = new SinglyLinkedList();
    private static final DoublyLinkedList doubly = new DoublyLinkedList();

    public static void main(String[] args) {
        try {
            runMainMenu();
        } catch (InputHelper.InputClosedException e) {
            System.out.println();
            System.out.println("Input ended. Exiting the program.");
        }
    }

    // ------------------------------------------------------------- main menu

    private static void runMainMenu() {
        while (true) {
            System.out.println();
            System.out.println(DOUBLE_LINE);
            System.out.println("          LINKED LIST MANAGEMENT SYSTEM");
            System.out.println(DOUBLE_LINE);
            System.out.println("1. Singly Linked List");
            System.out.println("2. Doubly Linked List");
            System.out.println("3. Compare Singly vs Doubly Linked List");
            System.out.println("4. Exit");
            int choice = input.readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    runSinglyMenu();
                    break;
                case 2:
                    runDoublyMenu();
                    break;
                case 3:
                    System.out.println();
                    ListComparison.print();
                    break;
                case 4:
                    System.out.println();
                    System.out.println("Thank you for using the Linked List Management System. Goodbye!");
                    return;
                default:
                    error("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
    }

    // ------------------------------------------------------ singly list menu

    private static void runSinglyMenu() {
        while (true) {
            System.out.println();
            System.out.println(STAR_LINE + " Operation on Singly Linked List " + STAR_LINE);
            System.out.println(" 1. Insert at Beginning");
            System.out.println(" 2. Insert at End");
            System.out.println(" 3. Insert at Position");
            System.out.println(" 4. Insert After a Node");
            System.out.println(" 5. Insert Before a Node");
            System.out.println(" 6. Delete from Beginning");
            System.out.println(" 7. Delete from End");
            System.out.println(" 8. Delete from Position");
            System.out.println(" 9. Delete by Value");
            System.out.println("10. Search");
            System.out.println("11. Update");
            System.out.println("12. Display");
            System.out.println("13. Count Nodes");
            System.out.println("14. Find Maximum");
            System.out.println("15. Find Minimum");
            System.out.println("16. Find Middle Element");
            System.out.println("17. Reverse");
            System.out.println("18. Sort");
            System.out.println("19. Remove Duplicates");
            System.out.println("20. Return to Main Menu");
            int choice = input.readInt("Enter the choice: ");
            if (choice == 20) {
                return;
            }
            System.out.println();
            try {
                handleSinglyChoice(choice);
            } catch (NoSuchElementException | IndexOutOfBoundsException e) {
                error(e.getMessage());
            }
        }
    }

    private static void handleSinglyChoice(int choice) {
        SinglyLinkedList list = singly;
        switch (choice) {
            case 1: {
                int value = input.readInt("Enter value to insert: ");
                list.insertAtBeginning(value);
                success(value + " inserted at the beginning.");
                showUpdated(list.toString());
                break;
            }
            case 2: {
                int value = input.readInt("Enter value to insert: ");
                list.insertAtEnd(value);
                success(value + " inserted at the end.");
                showUpdated(list.toString());
                break;
            }
            case 3: {
                int position = input.readInt("Enter position (1-based): ");
                int value = input.readInt("Enter value to insert: ");
                list.insertAtPosition(position, value);
                success(value + " inserted at position " + position + ".");
                showUpdated(list.toString());
                break;
            }
            case 4: {
                if (list.isEmpty()) {
                    error("The list is empty. There is no node to insert after.");
                    break;
                }
                int target = input.readInt("Enter the existing node value: ");
                int value = input.readInt("Enter value to insert: ");
                if (list.insertAfter(target, value)) {
                    success(value + " inserted after " + target + ".");
                    showUpdated(list.toString());
                } else {
                    error("Value " + target + " was not found. Nothing was inserted.");
                }
                break;
            }
            case 5: {
                if (list.isEmpty()) {
                    error("The list is empty. There is no node to insert before.");
                    break;
                }
                int target = input.readInt("Enter the existing node value: ");
                int value = input.readInt("Enter value to insert: ");
                if (list.insertBefore(target, value)) {
                    success(value + " inserted before " + target + ".");
                    showUpdated(list.toString());
                } else {
                    error("Value " + target + " was not found. Nothing was inserted.");
                }
                break;
            }
            case 6: {
                int deleted = list.deleteFromBeginning();
                success(deleted + " deleted from the beginning.");
                showUpdated(list.toString());
                break;
            }
            case 7: {
                int deleted = list.deleteFromEnd();
                success(deleted + " deleted from the end.");
                showUpdated(list.toString());
                break;
            }
            case 8: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to delete.");
                    break;
                }
                int position = input.readInt("Enter position to delete (1-based): ");
                int deleted = list.deleteFromPosition(position);
                success(deleted + " deleted from position " + position + ".");
                showUpdated(list.toString());
                break;
            }
            case 9: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to delete.");
                    break;
                }
                int value = input.readInt("Enter value to delete: ");
                if (list.deleteByValue(value)) {
                    success("First occurrence of " + value + " deleted.");
                    showUpdated(list.toString());
                } else {
                    error("Value " + value + " was not found. Nothing was deleted.");
                }
                break;
            }
            case 10: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to search.");
                    break;
                }
                int value = input.readInt("Enter value to search: ");
                int position = list.search(value);
                if (position == -1) {
                    error("Value " + value + " was not found in the list.");
                } else {
                    success("Value " + value + " found at position " + position + ".");
                }
                break;
            }
            case 11: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to update.");
                    break;
                }
                int oldValue = input.readInt("Enter the value to replace: ");
                int newValue = input.readInt("Enter the new value: ");
                if (list.update(oldValue, newValue)) {
                    success(oldValue + " updated to " + newValue + ".");
                    showUpdated(list.toString());
                } else {
                    error("Value " + oldValue + " was not found. Nothing was updated.");
                }
                break;
            }
            case 12:
                if (list.isEmpty()) {
                    System.out.println("The list is empty.");
                } else {
                    System.out.println("Linked List:");
                    System.out.println(list);
                }
                break;
            case 13:
                System.out.println("Number of nodes: " + list.size());
                break;
            case 14:
                System.out.println("Maximum value: " + list.findMaximum());
                break;
            case 15:
                System.out.println("Minimum value: " + list.findMinimum());
                break;
            case 16:
                System.out.println("Middle element: " + list.findMiddle());
                System.out.println(MIDDLE_RULE);
                break;
            case 17:
                if (list.size() < 2) {
                    System.out.println("The list has fewer than 2 nodes. It is unchanged.");
                } else {
                    list.reverse();
                    success("List reversed.");
                }
                showUpdated(list.toString());
                break;
            case 18:
                if (list.size() < 2) {
                    System.out.println("The list has fewer than 2 nodes. It is already sorted.");
                } else {
                    list.sort();
                    success("List sorted in ascending order.");
                }
                showUpdated(list.toString());
                break;
            case 19: {
                int removed = list.removeDuplicates();
                if (removed == 0) {
                    System.out.println("No duplicate values were found.");
                } else {
                    success(removed + " duplicate node(s) removed.");
                }
                showUpdated(list.toString());
                break;
            }
            default:
                error("Invalid choice. Please enter a number from 1 to 20.");
        }
    }

    // ------------------------------------------------------ doubly list menu

    private static void runDoublyMenu() {
        while (true) {
            System.out.println();
            System.out.println(STAR_LINE + " Operation on Doubly Linked List " + STAR_LINE);
            System.out.println(" 1. Insert at Beginning");
            System.out.println(" 2. Insert at End");
            System.out.println(" 3. Insert at Position");
            System.out.println(" 4. Insert After a Node");
            System.out.println(" 5. Insert Before a Node");
            System.out.println(" 6. Delete from Beginning");
            System.out.println(" 7. Delete from End");
            System.out.println(" 8. Delete from Position");
            System.out.println(" 9. Delete by Value");
            System.out.println("10. Search");
            System.out.println("11. Update");
            System.out.println("12. Display Forward");
            System.out.println("13. Display Backward");
            System.out.println("14. Count Nodes");
            System.out.println("15. Find Maximum");
            System.out.println("16. Find Minimum");
            System.out.println("17. Find Middle Element");
            System.out.println("18. Reverse");
            System.out.println("19. Sort");
            System.out.println("20. Remove Duplicates");
            System.out.println("21. Return to Main Menu");
            int choice = input.readInt("Enter the choice: ");
            if (choice == 21) {
                return;
            }
            System.out.println();
            try {
                handleDoublyChoice(choice);
            } catch (NoSuchElementException | IndexOutOfBoundsException e) {
                error(e.getMessage());
            }
        }
    }

    private static void handleDoublyChoice(int choice) {
        DoublyLinkedList list = doubly;
        switch (choice) {
            case 1: {
                int value = input.readInt("Enter value to insert: ");
                list.insertAtBeginning(value);
                success(value + " inserted at the beginning.");
                showUpdatedDoubly(list);
                break;
            }
            case 2: {
                int value = input.readInt("Enter value to insert: ");
                list.insertAtEnd(value);
                success(value + " inserted at the end.");
                showUpdatedDoubly(list);
                break;
            }
            case 3: {
                int position = input.readInt("Enter position (1-based): ");
                int value = input.readInt("Enter value to insert: ");
                list.insertAtPosition(position, value);
                success(value + " inserted at position " + position + ".");
                showUpdatedDoubly(list);
                break;
            }
            case 4: {
                if (list.isEmpty()) {
                    error("The list is empty. There is no node to insert after.");
                    break;
                }
                int target = input.readInt("Enter the existing node value: ");
                int value = input.readInt("Enter value to insert: ");
                if (list.insertAfter(target, value)) {
                    success(value + " inserted after " + target + ".");
                    showUpdatedDoubly(list);
                } else {
                    error("Value " + target + " was not found. Nothing was inserted.");
                }
                break;
            }
            case 5: {
                if (list.isEmpty()) {
                    error("The list is empty. There is no node to insert before.");
                    break;
                }
                int target = input.readInt("Enter the existing node value: ");
                int value = input.readInt("Enter value to insert: ");
                if (list.insertBefore(target, value)) {
                    success(value + " inserted before " + target + ".");
                    showUpdatedDoubly(list);
                } else {
                    error("Value " + target + " was not found. Nothing was inserted.");
                }
                break;
            }
            case 6: {
                int deleted = list.deleteFromBeginning();
                success(deleted + " deleted from the beginning.");
                showUpdatedDoubly(list);
                break;
            }
            case 7: {
                int deleted = list.deleteFromEnd();
                success(deleted + " deleted from the end.");
                showUpdatedDoubly(list);
                break;
            }
            case 8: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to delete.");
                    break;
                }
                int position = input.readInt("Enter position to delete (1-based): ");
                int deleted = list.deleteFromPosition(position);
                success(deleted + " deleted from position " + position + ".");
                showUpdatedDoubly(list);
                break;
            }
            case 9: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to delete.");
                    break;
                }
                int value = input.readInt("Enter value to delete: ");
                if (list.deleteByValue(value)) {
                    success("First occurrence of " + value + " deleted.");
                    showUpdatedDoubly(list);
                } else {
                    error("Value " + value + " was not found. Nothing was deleted.");
                }
                break;
            }
            case 10: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to search.");
                    break;
                }
                int value = input.readInt("Enter value to search: ");
                int position = list.search(value);
                if (position == -1) {
                    error("Value " + value + " was not found in the list.");
                } else {
                    success("Value " + value + " found at position " + position + ".");
                }
                break;
            }
            case 11: {
                if (list.isEmpty()) {
                    error("The list is empty. Nothing to update.");
                    break;
                }
                int oldValue = input.readInt("Enter the value to replace: ");
                int newValue = input.readInt("Enter the new value: ");
                if (list.update(oldValue, newValue)) {
                    success(oldValue + " updated to " + newValue + ".");
                    showUpdatedDoubly(list);
                } else {
                    error("Value " + oldValue + " was not found. Nothing was updated.");
                }
                break;
            }
            case 12:
                if (list.isEmpty()) {
                    System.out.println("The list is empty.");
                } else {
                    System.out.println("Forward:");
                    System.out.println(list.toForwardString());
                }
                break;
            case 13:
                if (list.isEmpty()) {
                    System.out.println("The list is empty.");
                } else {
                    System.out.println("Backward:");
                    System.out.println(list.toBackwardString());
                }
                break;
            case 14:
                System.out.println("Number of nodes: " + list.size());
                break;
            case 15:
                System.out.println("Maximum value: " + list.findMaximum());
                break;
            case 16:
                System.out.println("Minimum value: " + list.findMinimum());
                break;
            case 17:
                System.out.println("Middle element: " + list.findMiddle());
                System.out.println(MIDDLE_RULE);
                break;
            case 18:
                if (list.size() < 2) {
                    System.out.println("The list has fewer than 2 nodes. It is unchanged.");
                } else {
                    list.reverse();
                    success("List reversed.");
                }
                showUpdatedDoubly(list);
                break;
            case 19:
                if (list.size() < 2) {
                    System.out.println("The list has fewer than 2 nodes. It is already sorted.");
                } else {
                    list.sort();
                    success("List sorted in ascending order.");
                }
                showUpdatedDoubly(list);
                break;
            case 20: {
                int removed = list.removeDuplicates();
                if (removed == 0) {
                    System.out.println("No duplicate values were found.");
                } else {
                    success(removed + " duplicate node(s) removed.");
                }
                showUpdatedDoubly(list);
                break;
            }
            default:
                error("Invalid choice. Please enter a number from 1 to 21.");
        }
    }

    // --------------------------------------------------------------- output

    private static void success(String message) {
        System.out.println("Success: " + message);
    }

    private static void error(String message) {
        System.out.println("Error: " + message);
    }

    private static void showUpdated(String listText) {
        System.out.println();
        System.out.println("Updated Linked List:");
        System.out.println(listText);
    }

    private static void showUpdatedDoubly(DoublyLinkedList list) {
        System.out.println();
        System.out.println("Updated Linked List (forward):");
        System.out.println(list.toForwardString());
        System.out.println("Updated Linked List (backward):");
        System.out.println(list.toBackwardString());
    }
}
