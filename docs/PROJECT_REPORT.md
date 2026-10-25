# Linked List Management System (Java)

## Project Report

| Field                   | Details                                                            |
| ----------------------- | ------------------------------------------------------------------ |
| Project title           | Linked List Management System                                      |
| Student name            | Umar Khan                                                          |
| Roll number / System ID | 2025292151                                                         |
| Branch and year         | B.Tech Computer Science & Engineering, 2nd Year                    |
| Subject                 | Data Structures and Algorithms (DSA)                               |
| Institute               | Sharda University                                                  |
| Instructor              | Aniruddha Sir                                                      |
| Submission date         | 25/10/2026                                                         |
| GitHub repository       | https://github.com/umar-khanshb/Linked-List-Management-System-Java |

---

## Abstract

This project presents a menu-driven console application written in Java that implements the Singly Linked List and the Doubly Linked List from scratch, without using any built-in collection to store nodes. The application supports 19 operations on the singly list and 20 on the doubly list, including insertion at the beginning, end, position, before and after a value; deletion by position and value; searching; updating; reversing; sorting with Bubble Sort; and removing duplicates. A comparison module explains the differences between the two structures. Input is validated so that wrong input never crashes the program. The core classes were verified with 44 automated tests, all of which passed, and a recorded run of the application is included as sample output.

## 1. Introduction

A linked list is a linear data structure in which elements, called nodes, are stored at separate places in memory and are connected by references. Unlike an array, a linked list does not need contiguous memory and can grow or shrink one node at a time. Linked lists are the foundation for stacks, queues, hash-table chains, undo/redo history and many other structures, which makes them a core topic of Data Structures and Algorithms.

## 2. Problem Statement

Design and implement a menu-driven Java application that demonstrates the operations of a Singly Linked List and a Doubly Linked List, handles edge cases correctly, compares the two structures, and is submitted as a documented GitHub project.

## 3. Objectives

1. Implement a Singly Linked List and a Doubly Linked List manually using node classes and references.
2. Implement all required operations as separate methods.
3. Handle empty lists, single-node lists, invalid positions, missing values and duplicates.
4. Provide safe, user-friendly console input and output.
5. Compare the two structures in terms of memory, traversal and complexity.
6. Verify correctness with automated tests.
7. Document the project and prepare it for GitHub.

## 4. Scope

**Included:** integer data; 1-based positions; console interface; two list types; comparison table; self-contained tests; documentation.

**Not included:** generic types, graphical interface, file storage, multi-threading, circular lists, Merge Sort.

## 5. Hardware and Software Requirements

| Requirement                  | Details                                                                                    |
| ---------------------------- | ------------------------------------------------------------------------------------------ |
| Processor / RAM              | Any computer able to run a JDK (the program uses very little memory)                       |
| Operating system             | Windows, Linux or macOS                                                                    |
| Software                     | JDK 17 or newer; a terminal; optionally Maven 3.8+ and Git                                 |
| Development environment used | OpenJDK 21 on Linux; the code was compiled with `--release 17` so it is Java 17 compatible |

## 6. System Design

### 6.1 Architecture

```
          +-----------------+
          |      Main       |  menus, messages, program loop
          +--------+--------+
                   |
   +---------------+----------------+-----------------+
   |               |                |                 |
+--v-----------+ +-v------------+ +-v--------------+ +-v----------+
| SinglyLinked | | DoublyLinked | | ListComparison | | InputHelper|
| List         | | List         | | (table output) | | (safe int) |
+--+-----------+ +-+------------+ +----------------+ +------------+
   |               |
+--v--------+  +---v-------+
|SinglyNode |  |DoublyNode |
+-----------+  +-----------+
```

`Main` is the only class that talks to the user. The list classes contain only data-structure logic: they return results or throw exceptions, and never print. This separation makes the logic easy to test and to explain in a viva.

### 6.2 Error-handling design

| Situation                         | How it is reported by the list class                                  | What the user sees                                             |
| --------------------------------- | --------------------------------------------------------------------- | -------------------------------------------------------------- |
| Operation on an empty list        | `NoSuchElementException("The list is empty.")`                        | `Error: The list is empty.`                                    |
| Invalid position                  | `IndexOutOfBoundsException("Invalid position p. Valid range is ...")` | `Error: Invalid position 9. Valid range is 1 to 6.`            |
| Value not found                   | method returns `false` or `-1`                                        | `Error: Value 99 was not found...`                             |
| Letters typed instead of a number | `InputHelper` catches `NumberFormatException`                         | `Invalid input: please enter a whole number...` and asks again |
| Input stream closed               | `InputClosedException`                                                | `Input ended. Exiting the program.`                            |

## 7. Class Descriptions

| Class              | Fields                                           | Main responsibilities                                                                                                       |
| ------------------ | ------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------- |
| `SinglyNode`       | `int data`, `SinglyNode next`                    | One node of the singly list with getters and setters                                                                        |
| `DoublyNode`       | `int data`, `DoublyNode prev`, `DoublyNode next` | One node of the doubly list                                                                                                 |
| `SinglyLinkedList` | `head`, `size`                                   | All 19 singly list operations                                                                                               |
| `DoublyLinkedList` | `head`, `tail`, `size`                           | All 20 doubly list operations; private helpers `linkBefore`, `linkAfter`, `unlink`, `nodeAt` keep link updates in one place |
| `ListComparison`   | none                                             | Prints the comparison table and key points                                                                                  |
| `InputHelper`      | `Scanner`                                        | Reads integers safely                                                                                                       |
| `Main`             | the two list objects                             | Main menu, two sub-menus, messages                                                                                          |
| `ListTests`        | none                                             | 44 tests for both lists                                                                                                     |

## 8. Algorithms

Let `n` be the number of nodes. Positions are 1-based.

### 8.1 Insert at beginning (singly) – O(1)

1. Create a new node holding the value.
2. Set `newNode.next = head`.
3. Set `head = newNode`. Increase `size`.

### 8.2 Insert at position (singly) – O(n)

1. If `position < 1` or `position > size + 1`, reject it.
2. If `position == 1`, insert at the beginning.
3. Otherwise walk to the node at `position - 1` (call it `previous`).
4. Set `newNode.next = previous.next`, then `previous.next = newNode`. Increase `size`.

The order in step 4 matters: if you set `previous.next` first, you lose the rest of the list.

### 8.3 Delete from end (singly) – O(n)

1. If the list is empty, report an error.
2. If there is only one node, delete it like a delete from the beginning.
3. Otherwise walk to the second-last node and set its `next` to `null`. Decrease `size`.

### 8.4 Delete by value – O(n)

1. If the list is empty, report that nothing can be deleted.
2. If the head holds the value, move `head` to `head.next`.
3. Otherwise walk with `previous` until `previous.next` holds the value, then set `previous.next = previous.next.next`.
4. If the end is reached, report that the value was not found. Only the first match is deleted.

### 8.5 Search – O(n)

Walk from the head, counting positions from 1. Return the position of the first node holding the value, or -1 if the end is reached.

### 8.6 Middle element (slow and fast references) – O(n)

Start `slow` and `fast` at the head. In each step move `slow` by one node and `fast` by two nodes, stopping when `fast` or `fast.next` is `null`. When `fast` reaches the end, `slow` has covered half the distance, so it is at the middle.

**Even-length rule used here:** the _second_ of the two middle nodes. For `10, 20, 30, 40`, the answer is `30`.

### 8.7 Reverse a singly list (iterative) – O(n) time, O(1) extra space

```
previous = null; current = head
while current != null:
    next = current.next        # remember the rest
    current.next = previous    # point backward
    previous = current         # move previous forward
    current = next             # move current forward
head = previous
```

### 8.8 Bubble Sort on a linked list – O(n²)

Repeatedly walk the list comparing each node with the next one and swapping their **data** when they are out of order. After each pass the largest remaining value is in its final place at the end, so the next pass can stop one node earlier (the `end` marker). If a full pass makes no swap, the list is sorted and the loop stops early.

### 8.9 Remove duplicates – O(n²)

For each node `current`, use a `runner` to scan the nodes after it. Whenever `runner.next` holds the same value as `current`, unlink it (`runner.next = runner.next.next`) and reduce `size`. The first occurrence of every value is kept.

### 8.10 Doubly list: link and unlink helpers

Almost every doubly list operation is built from three helpers so the four references are always updated in one place:

- `linkBefore(target, node)`: `node.next = target`; `node.prev = target.prev`; fix the old predecessor's `next` (or set `head` if there was none); `target.prev = node`.
- `linkAfter(target, node)`: the mirror image, and sets `tail` if `target` was the tail.
- `unlink(node)`: connect `node.prev` and `node.next` to each other, update `head` if the node was first and `tail` if it was last, then decrease `size`.

### 8.11 Reverse a doubly list – O(n)

For every node, swap its `prev` and `next` references (moving on to the node that _used to be_ next), then swap `head` and `tail`. Afterwards forward traversal gives the old backward order and vice versa.

### 8.12 Delete from end (doubly) – O(1)

`tail` is known, so the node is unlinked directly: `tail = tail.prev`, and the new tail's `next` is set to `null` (or `head` becomes `null` if the list is now empty).

## 9. Implementation Details

- **Size counter:** both lists update `size` after every insertion and deletion, so counting nodes is O(1).
- **Position search in the doubly list:** `nodeAt` starts from `head` when the position is in the first half and from `tail` otherwise.
- **Java memory:** nodes are created with `new`. Java has no explicit `delete`; a removed node becomes unreachable and the garbage collector reclaims it. The `unlink` helper also sets the removed node's references to `null`, which is good hygiene but not required for correctness.
- **Validation helpers:** `isValid()` in both lists checks that the number of reachable nodes equals `size`; the doubly version also checks `head.prev == null`, `tail.next == null`, that every `prev` agrees with the previous `next`, and that the forward count equals the backward count.
- **Compiler settings:** compiled with `javac --release 17 -Xlint:all` with no warnings.

## 10. Sample Outputs

The complete unedited run is in `sample-output/output.txt`. Excerpts:

```
Enter the choice: 3
Enter position (1-based): 9
Enter value to insert: 99
Error: Invalid position 9. Valid range is 1 to 6.
```

```
Success: List reversed.
Updated Linked List (forward):
NULL <- 30 <-> 20 <-> 18 <-> 15 <-> 10 <-> 5 -> NULL
Updated Linked List (backward):
NULL <- 5 <-> 10 <-> 15 <-> 18 <-> 20 <-> 30 -> NULL
```

## 11. Testing Methodology

- **Approach:** a self-contained Java test harness (`ListTests`) is used because the development environment had no access to Maven Central to download JUnit. Each test creates its own fresh lists, so the result does not depend on the order of tests.
- **What is checked:** returned values, list contents (as arrays), the text produced for display, exceptions for invalid operations, the `size` counter, and the structural validity of the lists (`isValid()`).
- **Doubly list consistency:** after operations, the test checks that the backward traversal is exactly the reverse of the forward traversal, and that `head` and `tail` are correct.
- **Stress test:** a 2000-step random sequence of insertions, deletions and reversals (fixed random seed, so it is repeatable) re-validates the doubly list after every step.
- **Test sensitivity check:** to confirm the tests can detect real faults, three deliberate bugs were temporarily introduced into a scratch copy of the code (doubly `reverse` not swapping head/tail; singly `deleteFromEnd` not decreasing `size`; doubly `unlink` not updating `tail`). The suite reported failures for each (2, 2 and 8 failing tests respectively). The scratch copy was then discarded; the project code contains none of these bugs.
- **Interactive run:** the real application was driven through a scripted terminal session covering invalid input, empty-list errors, every major operation family, the comparison table and exit; no exception or crash occurred.

## 12. Test Cases and Results

The results below were produced by running `ListTests` on the final code (prefix `S` = singly, `D` = doubly).

| #   | Test case                                                                    | Result |
| --- | ---------------------------------------------------------------------------- | ------ |
| 1   | S empty list: display, size, search, max/min/middle                          | PASS   |
| 2   | S insert into empty list (beginning and end)                                 | PASS   |
| 3   | S insert at beginning and end keeps order                                    | PASS   |
| 4   | S insert at position: first, middle, last valid                              | PASS   |
| 5   | S insert at position on empty list (position 1 is valid)                     | PASS   |
| 6   | S insert at invalid positions throws and leaves list unchanged               | PASS   |
| 7   | S insert after a node (middle, last, missing, empty)                         | PASS   |
| 8   | S insert before a node (head, middle, missing, empty)                        | PASS   |
| 9   | S insert after/before uses the FIRST matching node                           | PASS   |
| 10  | S delete from beginning (multi, single, empty)                               | PASS   |
| 11  | S delete from end (multi, single, empty)                                     | PASS   |
| 12  | S delete from position: first, middle, last, invalid, empty                  | PASS   |
| 13  | S delete by value: head, middle, last, missing, duplicates, empty            | PASS   |
| 14  | S search finds first position and reports missing                            | PASS   |
| 15  | S update existing, missing, and first occurrence only                        | PASS   |
| 16  | S max/min with negatives, zero and duplicates                                | PASS   |
| 17  | S middle element: odd, even (second middle), single                          | PASS   |
| 18  | S reverse: empty, single, multi, twice returns original                      | PASS   |
| 19  | S sort: empty, single, example, duplicates, negatives, sorted, descending    | PASS   |
| 20  | S remove duplicates: none, many, all same, empty, single                     | PASS   |
| 21  | S zero and negative values are stored correctly                              | PASS   |
| 22  | S size counter stays correct after a mixed sequence                          | PASS   |
| 23  | D empty list: display both ways, size, search, max/min/middle                | PASS   |
| 24  | D insert into empty list sets both head and tail                             | PASS   |
| 25  | D insert at beginning/end: forward and backward agree                        | PASS   |
| 26  | D display format matches the specification                                   | PASS   |
| 27  | D insert at position: first, middle (both halves), last valid                | PASS   |
| 28  | D insert at position on empty list                                           | PASS   |
| 29  | D invalid positions throw and leave list unchanged                           | PASS   |
| 30  | D insert after a node (middle, tail, missing, empty)                         | PASS   |
| 31  | D insert before a node (head, middle, missing, empty)                        | PASS   |
| 32  | D delete from beginning: head/tail stay correct                              | PASS   |
| 33  | D delete from end: head/tail stay correct                                    | PASS   |
| 34  | D delete from position: first, middle, last, empty                           | PASS   |
| 35  | D delete by value: head, middle, tail, missing, duplicates, empty            | PASS   |
| 36  | D search and update                                                          | PASS   |
| 37  | D max/min with negatives and zero                                            | PASS   |
| 38  | D middle element: odd, even (second middle), single                          | PASS   |
| 39  | D reverse: empty, single, multi; head/tail swapped; both directions agree    | PASS   |
| 40  | D sort: empty, single, example, duplicates, negatives; both directions agree | PASS   |
| 41  | D remove duplicates: none, many, all same, head/tail duplicates              | PASS   |
| 42  | D head and tail correct after every kind of deletion                         | PASS   |
| 43  | D size counter stays correct after a mixed sequence                          | PASS   |
| 44  | D randomized stress test keeps the list consistent                           | PASS   |

**Summary: Passed: 44 Failed: 0 Total: 44 (ALL TESTS PASSED)**

### Edge-case checklist (from the assignment)

| Edge case                                            | Covered by                                                                          |
| ---------------------------------------------------- | ----------------------------------------------------------------------------------- |
| Empty list (display, search, delete, max/min/middle) | `S empty list...`, `D empty list...`, delete tests                                  |
| One-node list                                        | single-node cases inside delete, reverse, sort, middle and duplicate tests          |
| Insert/delete at position 1                          | position tests (both lists)                                                         |
| Insert at last valid position                        | `S/D insert at position...`                                                         |
| Invalid or out-of-range position                     | `S insert at invalid positions...`, `D invalid positions...`, delete-position tests |
| Delete a value that does not exist                   | delete-by-value tests                                                               |
| Duplicate values                                     | delete-by-value, search, update, remove-duplicates tests                            |
| Zero and negative values                             | `S zero and negative...`, max/min tests, sort tests                                 |
| Sort an empty or single-node list                    | sort tests (both lists)                                                             |
| Reverse an empty or single-node list                 | reverse tests (both lists)                                                          |
| Head/tail correct after deletion (doubly)            | `D delete from beginning/end...`, `D head and tail correct...`                      |
| References consistent after reversal (doubly)        | `D reverse...` and the stress test                                                  |

## 13. Time Complexity Analysis

| Operation                  | Singly | Doubly | Reason                                                      |
| -------------------------- | ------ | ------ | ----------------------------------------------------------- |
| Insert at beginning        | O(1)   | O(1)   | Only `head` changes                                         |
| Insert at end              | O(n)   | O(1)   | Singly must walk to the last node; doubly uses `tail`       |
| Insert at position         | O(n)   | O(n)   | Must reach the position first                               |
| Delete at beginning        | O(1)   | O(1)   | Only `head` changes                                         |
| Delete at end              | O(n)   | O(1)   | Singly needs the second-last node                           |
| Delete by value / position | O(n)   | O(n)   | Must find the node first                                    |
| Search, update, display    | O(n)   | O(n)   | Visit nodes one by one                                      |
| Count                      | O(1)   | O(1)   | `size` counter is maintained (O(n) if counted by traversal) |
| Max / Min / Middle         | O(n)   | O(n)   | One pass                                                    |
| Reverse                    | O(n)   | O(n)   | One pass, constant extra space                              |
| Bubble Sort                | O(n²)  | O(n²)  | Up to n passes of up to n comparisons                       |
| Simple duplicate removal   | O(n²)  | O(n²)  | For each node, scan the rest                                |

Actual complexity depends on the implementation. For example, giving the singly list a `tail` reference would make insertion at the end O(1).

## 14. Advantages

- No need for contiguous memory; the list grows one node at a time.
- Insertion and deletion at the beginning are O(1) without shifting elements.
- The doubly list supports backward traversal and O(1) deletion at the end.
- Clear separation between logic (`*LinkedList`) and user interface (`Main`).

## 15. Limitations

- No O(1) access by index (unlike arrays); reaching position `k` takes O(k).
- Each node needs extra memory for its reference(s); the doubly list needs two.
- Poorer cache locality than arrays because nodes may be scattered in memory.
- Only `int` values; Bubble Sort and duplicate removal are O(n²).
- `pom.xml` was not verified by a real Maven build in the development environment.

## 16. Future Scope

Generic lists with `Comparable<T>`; Merge Sort (O(n log n)); circular linked list; tail-based singly list; saving to a file; JUnit 5 tests; a graphical visualisation.

## 17. Conclusion

The project successfully implements a Singly Linked List and a Doubly Linked List from scratch in Java, with all required operations, safe input handling and a comparison module. All 44 automated tests passed on the final code, and a scripted run of the real application showed correct behaviour for normal and edge-case inputs. The work reinforces how references define the structure of linked data, why `head` and `tail` must be updated carefully, and how the choice between singly and doubly lists is a trade-off between memory and capability.

## 18. References

1. Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein, _Introduction to Algorithms_, MIT Press.
2. Robert Lafore, _Data Structures and Algorithms in Java_, Sams Publishing.
3. Oracle, _The Java Tutorials_ and _Java SE Documentation_, https://docs.oracle.com/en/java/
4. Course notes and the assignment document "Linked List Management System – DSA Mini Project".

---

## Appendix A: Viva Questions and Answers

**1. What is a linked list?**
A linear data structure made of nodes. Each node stores data and a reference to another node, so the elements are connected by references instead of being stored side by side like in an array.

**2. What is a node?**
The basic building block of a linked list. In this project a `SinglyNode` holds `data` and `next`; a `DoublyNode` holds `data`, `prev` and `next`.

**3. What is the purpose of `head`?**
`head` refers to the first node. It is the only entry point to a singly list: if you lose `head`, you lose the whole list. An empty list has `head == null`.

**4. What is the difference between an array and a linked list?**
An array stores elements in contiguous memory and gives O(1) access by index, but inserting or deleting in the middle needs shifting. A linked list stores nodes anywhere in memory joined by references, so insertion and deletion need no shifting once the place is found, but reaching an index takes O(n).

**5. What is a Singly Linked List?**
A list where each node points only to the next node. It can be traversed in one direction, from head to the end.

**6. What is a Doubly Linked List?**
A list where each node points to both the previous and the next node, so it can be traversed in both directions.

**7. Why does a Doubly Linked List use `prev` and `next`?**
`next` allows forward traversal and `prev` allows backward traversal. `prev` also lets us delete a node or the last node without searching for its predecessor.

**8. What is traversal?**
Visiting every node one by one, usually from `head` following `next` until `null`. Display, search, count and max/min are all traversals.

**9. Why is searching O(n)?**
There is no index, so in the worst case (the value is last or missing) we must look at all `n` nodes.

**10. Why is insertion at the beginning O(1)?**
We only create a node, point its `next` to the old head, and move `head`. The number of steps does not depend on the list size.

**11. How do you reverse a Singly Linked List?**
Walk the list with three references (`previous`, `current`, `next`). For each node, save `next`, make `current.next` point to `previous`, then move both forward. At the end set `head = previous`. It takes O(n) time and O(1) extra space.

**12. How do slow and fast references find the middle?**
`slow` moves one node per step and `fast` moves two. When `fast` reaches the end, `slow` has travelled half as far, so it is at the middle. In this project, for an even count, the second middle node is returned.

**13. Why is Bubble Sort O(n²)?**
It makes up to `n` passes, and each pass compares up to `n` neighbours, so the work grows like `n × n`. (With the early-stop check, an already sorted list needs only one pass, but the worst case is still O(n²).)

**14. How do you remove duplicates?**
For each node, scan the rest of the list and delete every later node with the same value by changing the previous node's `next`. That keeps the first occurrence and takes O(n²).

**15. How do you handle an empty list?**
Check `head == null` before using `head`. Insertions into an empty list make the new node the head (and, in the doubly list, the tail too). Deletions, max, min and middle report that the list is empty instead of crashing.

**16. What happens when a node is deleted?**
The neighbours are linked around it. In a singly list the previous node's `next` skips the deleted node. In a doubly list both the previous node's `next` and the next node's `prev` are fixed, and `head` or `tail` are updated if the first or last node was deleted.

**17. Why does Java not require an explicit delete for ordinary objects?**
Java manages memory automatically. When no reference points to an object any more, it becomes unreachable and the garbage collector can reclaim it. In C++ the programmer must call `delete`.

**18. What is garbage collection?**
The automatic process in which the JVM finds objects that can no longer be reached from the running program and frees their memory.

**19. What is the difference between a reference and a C++ pointer?**
A Java reference refers to an object but you cannot do pointer arithmetic on it or see a memory address, and it is either `null` or points to a valid object. A C++ pointer holds a memory address, supports arithmetic, and can become dangling if memory is freed incorrectly.

**20. What is the purpose of a `tail` reference?**
It points to the last node, so insertion at the end becomes O(1) and, in a doubly list, deletion at the end is O(1) as well. It must be kept correct after every insertion, deletion and reversal.

**21. What is the difference between O(1) and O(n)?**
O(1) means the time does not depend on the number of elements (constant). O(n) means the time grows in proportion to the number of elements.

**22. Why must `prev` and `next` both be updated in a Doubly Linked List?**
Every link exists in two places: A's `next` points to B and B's `prev` points to A. If only one is updated, forward and backward traversal disagree and the list becomes corrupt. That is why this project routes changes through `linkBefore`, `linkAfter` and `unlink`.

**23. (Extra) Why did you swap data instead of links in Bubble Sort?**
It is simpler and less error-prone because the node structure stays unchanged. The cost is copying values, which is fine for `int`. Swapping links is more complex and is needed only when nodes are large or must keep their identity.

**24. (Extra) What is the middle rule for an even number of nodes?**
This project returns the second of the two middle nodes. For `10, 20, 30, 40` the middle is `30`.
