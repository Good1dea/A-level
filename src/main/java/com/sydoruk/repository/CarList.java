package com.sydoruk.repository;

import com.sydoruk.annotation.Singleton;
import com.sydoruk.model.Car;

import java.util.List;
import java.util.Optional;

@Singleton
public class CarList <T extends Car> implements InterfaceRepository<T> {
    private Node head = null;
    private Node tail = null;
    private int length = 0;
    private static CarList instance;

    public static CarList getInstance() {
        if (instance == null) {
            instance = new CarList();
        }
        return instance;
    }


    public <T extends Car> void addNodeInHead(T car) {
        Node newNode = new Node(car);
        if (head == null) {
            head = tail = newNode;
            head.previous = null;
            tail.next = null;
        } else {
            head.previous = newNode;
            newNode.next = head;
            head = newNode;
            head.previous = null;
        }
        length++;
    }

    public <T extends Car> void addNodeInTail(T car) {
        if(tail == null) {
            addNodeInHead(car);
        } else {
           Node newNode = new Node(car);
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
            tail.next = null;
            length++;
        }
    }


    public <T extends Car> int getPosition(T car) {
        int index = -1;
        boolean found = false;
       Node current = head;
        while(current.next != null){
            index++;
            if(car.getId().equals(current.getValue().getId())){
                found = true;
                break;
            }
            current = current.next;
        };

        if (found == false) {
            index = -1;
        }

        return index;
    }

    public <T extends Car> void insertPosition(int position, T car) {
        if(position <= 0){
            addNodeInHead(car);
        } else if(position >= length ){
            addNodeInTail(car);
        } else {
            Node newNode = new Node(car);
            Node current = head;
            for(int i = 0; i < position; i++){
                current = current.next;
            }
            newNode.previous = current.previous;
            newNode.previous.next = newNode;
            newNode.next = current;
            current.previous = newNode;

            length++;
        }
    }

    public void deleteByPosition(int position){
        if(length > 0) {
            if (position <= 0) {
                Node newHead = head.next;
                head.next = null;
                head.previous = null;
                newHead.previous = null;
                head = newHead;
            }else if (position >= length){
                Node newTail = tail.previous;
                tail.next = null;
                tail.previous = null;
                newTail.next = null;
                tail = newTail;
            }else{
               Node current = head;
                for (int i = 0; i < position; i++) {
                    current = current.next;
                }
                current.previous.next = current.next;
                current.next.previous = current.previous;
            }
            length--;
        }
    }

    public int getTotalCarCount() {
    Node current = head;
     int count = 0;
     while (current.next != null) {
         count += current.getValue().getCount();
         current = current.next;
     }

     return count;
    }

    public void printNodes() {
        Node current = head;
        if (head == null) {
            System.out.println("CarList is empty");
            return;
        }
        System.out.println("Nodes of CarList: ");
        while (current != null) {
            System.out.println(current.getValue().getId() + " ");
            current = current.next;
        }
    }

    @Override
    public void save(T object) {

    }

    @Override
    public List<T> getAll() {
        return null;

    }

    @Override
    public Optional<T> getById(String id) {

        return null;
    }

    @Override
    public void delete(String id) {
    }
}