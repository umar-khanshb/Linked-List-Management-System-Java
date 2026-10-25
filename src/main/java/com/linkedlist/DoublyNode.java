package com.linkedlist;

/**
 * A single node of a Doubly Linked List.
 * It stores one integer, a reference to the previous node and a reference to the next node.
 */
public class DoublyNode {
    private int data;
    private DoublyNode prev;
    private DoublyNode next;

    public DoublyNode(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public DoublyNode getPrev() {
        return prev;
    }

    public void setPrev(DoublyNode prev) {
        this.prev = prev;
    }

    public DoublyNode getNext() {
        return next;
    }

    public void setNext(DoublyNode next) {
        this.next = next;
    }
}
