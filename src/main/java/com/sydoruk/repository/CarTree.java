package com.sydoruk.repository;

import com.sydoruk.annotation.Autowired;
import com.sydoruk.annotation.Singleton;
import com.sydoruk.model.Car;

import java.util.*;

@Singleton
public class CarTree<T extends Car> implements InterfaceRepository<T> {

    private T car;
    private Node root;
    private final CarComparator<T> carComparator;
    private static CarTree instance;

    public CarTree() {
        carComparator = new CarComparator<>();
        root = null;
    }

    public static CarTree getInstance() {
        if (instance == null) {
            instance = new CarTree();
        }
        return instance;
    }

    public CarTree(T car) {
        carComparator = new CarComparator<>();
        this.car = car;
    }

    public void insertNode(T car) {
        int position = -2;
       Node newNode = new Node<>(car);
        if (root == null) {
            root = newNode;
        } else {
            Node current = root;
            while (true) {
                position = carComparator.compare((T) newNode.getValue(), (T) current.getValue());
                if (position == -1) {
                    if (current.getLeftChild() == null) {
                        current.setLeftChild(newNode);
                        return;
                    }
                    current = current.getLeftChild();
                } else if (position == 1 || position == 0) {
                    if (current.getRightChild() == null) {
                        current.setRightChild(newNode);
                        return;
                    }
                    current = current.getRightChild();
                } else return;
            }
        }
    }

    public int getLeftCarCount() {
        LinkedList<Node> queueParents = new LinkedList<>();
        Node current = root;
        int totalCount = 0;
        if (current == null) {
            return totalCount;
        }
        while (true) {
            while (current != null) {
                if (current.getRightChild() != null) {
                    if (current != root) {
                        queueParents.push(current.getRightChild());
                    }
                }
                totalCount += current.getValue().getCount();
                current = current.getLeftChild();
            }
            while (queueParents.size() > 0) {
                current = queueParents.pop();
                break;
            }
            if (queueParents.size() == 0 && current == null) {
                break;
            }
        }
        return totalCount;
    }

    public int getRightCarCount() {
        LinkedList<Node> queueParents = new LinkedList<>();
        Node current = root;
        int totalCount = 0;
        if (current == null) {
            return totalCount;
        }
        while (true) {
            while (current != null) {
                if (current.getLeftChild() != null) {
                    if (current != root) {
                        queueParents.push(current.getLeftChild());
                    }
                }
                totalCount += current.getValue().getCount();
                current = current.getRightChild();
            }
            while (queueParents.size() > 0) {
                current = queueParents.pop();
                break;
            }
            if (queueParents.size() == 0 && current == null) {
                break;
            }
        }
        return totalCount;
    }

    @Override
    public void save(T car) {

    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T getById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}