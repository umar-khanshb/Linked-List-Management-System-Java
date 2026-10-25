package com.linkedlist;

/**
 * A single node of a Singly Linked List.
 * It stores one integer and a reference to the next node.
 */
public class SinglyNode {
    private int data;
    private SinglyNode next;

    public SinglyNode(int data) {
        this.data = data;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public SinglyNode getNext() {
        return next;
    }

    public void setNext(SinglyNode next) {
        this.next = next;
    }
}
