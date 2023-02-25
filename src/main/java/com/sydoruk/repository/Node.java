package com.sydoruk.repository;

import com.sydoruk.model.Car;

public class Node<T extends Car>{
    private T value;
    private Node leftChild;
    private Node rightChild;
    Node previous;
    Node next;

    public Node(T value){
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(final Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(final Node rightChild) {
        this.rightChild = rightChild;
    }
}