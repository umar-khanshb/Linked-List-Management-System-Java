package com.linkedlist;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Safe console input. Re-prompts until the user types a valid whole number,
 * so typing letters never crashes the program.
 */
public class InputHelper {

    /** Thrown when the input stream ends (for example Ctrl+D) so the program can exit cleanly. */
    public static class InputClosedException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InputClosedException() {
            super("Input stream closed.");
        }
    }

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    /** Prompts until a valid int is entered. */
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line;
            try {
                line = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                throw new InputClosedException();
            }
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: please enter a whole number (for example 25).");
            }
        }
    }
}
