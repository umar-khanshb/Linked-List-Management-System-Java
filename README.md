# Linked List Management System (Java)

A menu-driven Java console application that implements a **Singly Linked List** and a **Doubly Linked List** from scratch, with a side-by-side comparison of the two. Built as a second-year B.Tech CSE Data Structures and Algorithms (DSA) mini project.

> Author: **Umar Khan** | Roll No / System ID: **2025292151** | Course: **B.Tech CSE** | Institute: **Sharda University**

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Problem Statement](#2-problem-statement)
3. [Objectives](#3-objectives)
4. [Features](#4-features)
5. [Technologies Used](#5-technologies-used)
6. [Project Architecture](#6-project-architecture)
7. [Directory Structure](#7-directory-structure)
8. [Singly Linked List Explained](#8-singly-linked-list-explained)
9. [Doubly Linked List Explained](#9-doubly-linked-list-explained)
10. [Operations Supported](#10-operations-supported)
11. [Time Complexity](#11-time-complexity)
12. [Installation Prerequisites](#12-installation-prerequisites)
13. [How to Compile and Run](#13-how-to-compile-and-run)
14. [Maven Instructions](#14-maven-instructions)
15. [Testing Instructions](#15-testing-instructions)
16. [Sample Output](#16-sample-output)
17. [Known Limitations](#17-known-limitations)
18. [Future Enhancements](#18-future-enhancements)
19. [Viva Preparation](#19-viva-preparation)
20. [Author Information](#20-author-information)

---

## 1. Project Overview

The program lets a user create and manipulate two kinds of linked lists through console menus. Every node is created dynamically, and all links (`next`, and `prev` for the doubly list) are managed by hand. **No `java.util.LinkedList`, `ArrayList` or array is used to store the nodes.**

## 2. Problem Statement

Design and implement a menu-driven application that demonstrates insertion, deletion, traversal, searching, updating, sorting, reversing and other standard operations on singly and doubly linked lists, and that compares the two structures.

## 3. Objectives

- Understand dynamic memory use through node objects and references.
- Implement every standard linked list operation without library collections.
- Handle edge cases safely: empty list, one-node list, invalid positions, missing values, duplicates, zero and negative values.
- Compare singly and doubly linked lists (structure, memory, complexity, use cases).
- Practise clean object-oriented design, input validation and automated testing.

## 4. Features

- Two complete lists with 19 (singly) and 20 (doubly) operations respectively, plus a return-to-menu option.
- 1-based positions with clear error messages for invalid ones.
- Input validation: typing letters instead of numbers never crashes the program; it simply asks again.
- Doubly list keeps `head`, `tail` and a `size` counter; forward and backward displays always agree.
- Comparison table printed in the console.
- Lists keep their data when you go back to the main menu and return.
- 44 automated tests (self-contained, no JUnit download needed), including a randomized stress test.

## 5. Technologies Used

| Item      | Details                                                               |
| --------- | --------------------------------------------------------------------- |
| Language  | Java (code compiled with `--release 17`, so it is Java 17 compatible) |
| Paradigm  | Object-oriented programming                                           |
| Input     | `java.util.Scanner`                                                   |
| Build     | `javac` (verified) or Maven (`pom.xml` provided, see section 14)      |
| Libraries | None                                                                  |

## 6. Project Architecture

| Class                       | Responsibility                                                  |
| --------------------------- | --------------------------------------------------------------- |
| `Main`                      | Menus, user messages, program loop. Contains **no** list logic. |
| `SinglyLinkedList`          | Singly list logic. Holds `head` and `size`.                     |
| `DoublyLinkedList`          | Doubly list logic. Holds `head`, `tail` and `size`.             |
| `SinglyNode`                | Node with `data` and `next`.                                    |
| `DoublyNode`                | Node with `data`, `prev` and `next`.                            |
| `ListComparison`            | Prints the comparison table.                                    |
| `InputHelper`               | Safe integer input; re-prompts on bad input.                    |
| `ListTests` (in `src/test`) | Test harness for both lists.                                    |

The list classes never print. They return values or throw `NoSuchElementException` (empty list) or `IndexOutOfBoundsException` (bad position), and `Main` converts those into friendly messages. This keeps the data-structure code easy to test and easy to explain.

## 7. Directory Structure

```
Linked-List-Management-System-Java/
├── src/
│   ├── main/java/com/linkedlist/
│   │   ├── Main.java
│   │   ├── SinglyLinkedList.java
│   │   ├── DoublyLinkedList.java
│   │   ├── SinglyNode.java
│   │   ├── DoublyNode.java
│   │   ├── ListComparison.java
│   │   └── InputHelper.java
│   └── test/java/com/linkedlist/
│       └── ListTests.java
├── docs/
│   └── PROJECT_REPORT.md
├── sample-output/
│   ├── output.txt            (real recorded session)
│   └── test-results.txt      (real test run)
├── README.md
├── .gitignore
└── pom.xml
```

## 8. Singly Linked List Explained

Each node stores `data` and a reference `next` to the following node. The list remembers only `head`, the first node. The last node's `next` is `null`.

```
head
 |
 v
[10 | next] --> [20 | next] --> [30 | null]
```

You can only move forward. To reach the end you must walk the whole list, which is why insertion and deletion at the end are O(n) here.

## 9. Doubly Linked List Explained

Each node stores `data`, `prev` and `next`. The list remembers both `head` and `tail`.

```
head                                   tail
 |                                       |
 v                                       v
[null | 10 | next] <--> [prev | 20 | next] <--> [prev | 30 | null]
```

Because every node knows its predecessor, the list can be traversed backward, and the last node can be deleted in O(1) using `tail`. The price is one extra reference per node and more links to update on every change.

## 10. Operations Supported

| #   | Singly menu           | Doubly menu           |
| --- | --------------------- | --------------------- |
| 1   | Insert at Beginning   | Insert at Beginning   |
| 2   | Insert at End         | Insert at End         |
| 3   | Insert at Position    | Insert at Position    |
| 4   | Insert After a Node   | Insert After a Node   |
| 5   | Insert Before a Node  | Insert Before a Node  |
| 6   | Delete from Beginning | Delete from Beginning |
| 7   | Delete from End       | Delete from End       |
| 8   | Delete from Position  | Delete from Position  |
| 9   | Delete by Value       | Delete by Value       |
| 10  | Search                | Search                |
| 11  | Update                | Update                |
| 12  | Display               | Display Forward       |
| 13  | Count Nodes           | Display Backward      |
| 14  | Find Maximum          | Count Nodes           |
| 15  | Find Minimum          | Find Maximum          |
| 16  | Find Middle Element   | Find Minimum          |
| 17  | Reverse               | Find Middle Element   |
| 18  | Sort (Bubble Sort)    | Reverse               |
| 19  | Remove Duplicates     | Sort (Bubble Sort)    |
| 20  | Return to Main Menu   | Remove Duplicates     |
| 21  |                       | Return to Main Menu   |

**Rules used in this project**

- Positions are **1-based**. Insert accepts `1` to `size + 1`; delete accepts `1` to `size`.
- _Delete by Value_, _Insert After/Before_ and _Update_ act on the **first** matching node.
- **Middle element:** for an odd number of nodes it is the exact centre. For an even number it is the **second** of the two middle nodes (`10, 20, 30, 40` gives `30`).
- _Remove Duplicates_ keeps the first occurrence of each value.
- _Sort_ is Bubble Sort that swaps node data (not links), ascending.

## 11. Time Complexity

`n` = number of nodes. These are the complexities of _this implementation_.

| Operation                     | Singly (head only)       | Doubly (head + tail)     |
| ----------------------------- | ------------------------ | ------------------------ |
| Insert at beginning           | O(1)                     | O(1)                     |
| Insert at end                 | O(n)                     | O(1)                     |
| Insert at position            | O(n)                     | O(n)                     |
| Insert after / before a value | O(n)                     | O(n)                     |
| Delete from beginning         | O(1)                     | O(1)                     |
| Delete from end               | O(n)                     | O(1)                     |
| Delete from position          | O(n)                     | O(n)                     |
| Delete by value               | O(n)                     | O(n)                     |
| Search / Update               | O(n)                     | O(n)                     |
| Display (either direction)    | O(n)                     | O(n)                     |
| Count nodes                   | O(1) (size counter kept) | O(1) (size counter kept) |
| Find maximum / minimum        | O(n)                     | O(n)                     |
| Find middle                   | O(n)                     | O(n)                     |
| Reverse                       | O(n), extra space O(1)   | O(n), extra space O(1)   |
| Bubble Sort                   | O(n²)                    | O(n²)                    |
| Remove duplicates (simple)    | O(n²)                    | O(n²)                    |

Without a maintained `size` counter, counting would be O(n). Actual complexity always depends on the implementation: a singly list that also kept a `tail` reference could insert at the end in O(1).

## 12. Installation Prerequisites

- **JDK 17 or newer** (a JDK, not just a JRE, because `javac` is needed). Check with `java -version` and `javac -version`.
- Optional: Apache Maven 3.8+ (only for the Maven route).
- Optional: Git, to publish the project.

## 13. How to Compile and Run

Run these from the project root folder (the one containing `src`).

**Linux / macOS / Git Bash**

```bash
mkdir -p out
javac -d out src/main/java/com/linkedlist/*.java
java -cp out com.linkedlist.Main
```

**Windows (Command Prompt)**

```bat
mkdir out
javac -d out src\main\java\com\linkedlist\*.java
java -cp out com.linkedlist.Main
```

The Linux commands were run and verified while building this project. The Windows commands are the direct equivalents; they were not run on a Windows machine here.

## 14. Maven Instructions

A `pom.xml` is included (Java 17, no dependencies). On a computer with Maven and internet access:

```bash
mvn compile
java -cp target/classes com.linkedlist.Main
```

or build a runnable jar:

```bash
mvn package
java -jar target/linked-list-management-system-1.0.0.jar
```

> **Honesty note:** Maven was installed in the development environment, but it could not download its plugins there (Maven Central was not reachable), so the `pom.xml` was checked only for valid XML and has **not** been run through a real Maven build. The `javac` route in section 13 is the verified one. If `mvn` gives any problem on your machine, use section 13.

## 15. Testing Instructions

The tests are a plain Java program (`ListTests`) so nothing needs to be downloaded. Each test builds its own fresh lists, so tests do not depend on each other or on the order they run.

**Linux / macOS / Git Bash**

```bash
mkdir -p out out-test
javac -d out src/main/java/com/linkedlist/*.java
javac -cp out -d out-test src/test/java/com/linkedlist/ListTests.java
java -cp out:out-test com.linkedlist.ListTests
```

**Windows (Command Prompt)** – same, but the classpath separator is `;`:

```bat
java -cp "out;out-test" com.linkedlist.ListTests
```

Expected ending: `Passed: 44   Failed: 0   Total: 44` and `ALL TESTS PASSED`. The program exits with code 1 if any test fails. The full recorded run is in `sample-output/test-results.txt`.

The tests cover insertion, deletion, search, update, reverse, sorting, duplicate removal, middle element, invalid positions, empty lists, single-node lists, head/tail consistency, forward/backward consistency, the size counter, and a 2000-step randomized stress test on the doubly list that re-validates every link after each step.

## 16. Sample Output

Excerpts from a real run (full transcript: `sample-output/output.txt`).

**Safe input handling**

```
Enter your choice: abc
Invalid input: please enter a whole number (for example 25).
Enter your choice: 9
Error: Invalid choice. Please enter a number from 1 to 4.
```

**Singly linked list**

```
Enter the choice: 2
Enter value to insert: 30
Success: 30 inserted at the end.

Updated Linked List:
10 -> 20 -> 30 -> NULL

Enter position (1-based): 9
Enter value to insert: 99
Error: Invalid position 9. Valid range is 1 to 6.

Success: List sorted in ascending order.
5 -> 10 -> 15 -> 20 -> 21 -> 25 -> 30 -> NULL

Success: List reversed.
30 -> 25 -> 21 -> 20 -> 15 -> 10 -> 5 -> NULL
```

**Doubly linked list**

```
Forward:
NULL <- 5 <-> 10 <-> 20 <-> 30 -> NULL
Backward:
NULL <- 30 <-> 20 <-> 10 <-> 5 -> NULL
```

## 17. Known Limitations

- Stores `int` values only (no generics).
- Bubble Sort and simple duplicate removal are O(n²); they are chosen for clarity, not speed.
- The singly list has no `tail`, so insertion/deletion at the end is O(n) (deliberate, to match the course description).
- The lists are not thread-safe.
- Console only; no file saving of list contents.
- `pom.xml` is not verified by a real Maven build (see section 14).

## 18. Future Enhancements

- Generic `LinkedList<T>` using Java generics and `Comparable<T>`.
- Merge Sort for linked lists (O(n log n)).
- Circular linked list and a tail-based singly list variant.
- Save/load the list to a file.
- Convert the self-contained tests to JUnit 5.
- A simple JavaFX or Swing visualisation of nodes and links.

## 19. Viva Preparation

A set of 24 viva questions with simple answers is in the Appendix of [`docs/PROJECT_REPORT.md`](docs/PROJECT_REPORT.md).

## 20. Author Information

| Field               | Value                |
| ------------------- | -------------------- |
| Name                | Umar Khan            |
| Roll No / System ID | 2025292151           |
| Course / Branch     | B.Tech CSE, 2nd Year |
| Institute           | Sharda University    |
| Instructor          | Aniruddha Sir        |
| GitHub              | umar-khanshb         |
