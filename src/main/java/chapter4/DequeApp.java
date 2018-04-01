package chapter4;

import java.util.Arrays;

public class DequeApp {

    public static void main(String[] args) {
        new DequeApp().testDriveBothSides();

    }

    private void testDriveRightSide() {
        Deque deque = new Deque(10);
        deque.display();

        deque.insertRight(1);
        deque.insertRight(2);
        deque.insertRight(3);
        deque.insertRight(4);
        deque.insertRight(5);
        deque.display();

        System.out.println(deque.peekRight());
        System.out.println(deque.popRight());
        System.out.println(deque.popRight());
        System.out.println(deque.popRight());

        deque.display();

        deque.insertRight(5);
        deque.insertRight(6);
        deque.insertRight(7);
        deque.insertRight(8);
        deque.display();

        System.out.println(deque.peekLeft());
        System.out.println(deque.popLeft());
        System.out.println(deque.popLeft());
        System.out.println(deque.popLeft());
        System.out.println(deque.peekLeft());
        deque.display();

        deque.insertRight(9);
        deque.insertRight(10);
        deque.insertRight(11);
        deque.insertRight(12);
        System.out.println(deque.peekLeft());
        deque.display();
        deque.insertRight(13);
        deque.insertRight(14);
        System.out.println(deque.peekLeft());
        deque.display();
        deque.insertRight(15);
        deque.insertRight(16);
        deque.display();
    }

    private void testDriveLeftSide() {
        Deque deque = new Deque(10);
        deque.insertLeft(1);
        deque.insertLeft(2);
        deque.insertLeft(3);
        deque.insertLeft(4);
        deque.insertLeft(5);
        deque.display();
        System.out.println(deque.peekLeft());
        System.out.println(deque.popLeft());
        System.out.println(deque.popLeft());
        deque.display();
    }

    private void testDriveBothSides() {

        Deque deque = new Deque(10);
        deque.insertRight(1);
        deque.insertRight(2);
        deque.insertRight(3);
        deque.insertRight(4);
        deque.insertRight(5);
        deque.display();

        System.out.println(deque.popLeft());
        System.out.println(deque.popLeft());
        System.out.println(deque.popLeft());
        deque.display();

        deque.insertLeft(6);
        deque.insertLeft(7);
        deque.insertLeft(8);
        deque.insertLeft(9);
        deque.display();
    }
}

class Deque {
    private long[] array;
    private int maxSize;
    private int currentSize;
    private int front;
    private int rear;

    public Deque(int maxSize) {
        this.maxSize = maxSize;
        this.array = new long[maxSize];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == maxSize;
    }

    public void insertRight(long value) {
        if (isFull()) {
            System.out.println("is full");
            return;
        }

        if (rear == maxSize) {
            rear = 0;
        }

        array[rear++] = value;
        currentSize++;
    }

    public long peekRight() {
        return array[rear - 1];
    }

    public long popRight() {
        currentSize--;
        return array[--rear];
    }

    public void insertLeft(long value) {
        if (isFull()) {
            System.out.println("is full");
            return;
        }

        if (front == 0) {
            front = maxSize;
        }

        currentSize++;
        array[--front] = value;
    }

    public long peekLeft() {
        return array[front + 1];
    }

    public long popLeft() {
        currentSize--;
        return array[front++];
    }

    public void display() {
        System.out.println("-----");
        System.out.printf("front %d, rear %d, current size %d, max size %d\n", front, rear, currentSize, maxSize);
        System.out.println(Arrays.toString(array));
        System.out.println("-----");
    }
}
