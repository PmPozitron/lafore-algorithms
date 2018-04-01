package chapter5.doubly;

class Deque {
    private DoublyLinkedList list;

    public Deque() {
        list = new DoublyLinkedList();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void insertLeft(long dd) {
        list.insertFirst(dd);
    }

    public void insertRight(long dd) {
        list.insertLast(dd);
    }

    public long popLeft() {
        checkRequired();
        return list.deleteFirst().getdData();
    }

    public long popRight() {
        checkRequired();
        return list.deleteLast().getdData();
    }

    public long peekLeft() {
        checkRequired();
        return list.getFirst().getdData();
    }

    public long peekRight() {
        checkRequired();
        return list.getLast().getdData();
    }

    public void display() {
        list.displayForward();
    }

    private void checkRequired() {
        if (isEmpty()) {
            throw new IllegalStateException("is empty");
        }
    }

}

public class DequeApp {
    public static void main(String[] args) {
        new DequeApp().testDrive();
    }

    private void testDrive() {
        Deque deque = new Deque();
        deque.display();

        deque.insertLeft(10L);
        deque.insertLeft(15L);
        deque.insertLeft(5L);

        deque.display();

        deque.insertRight(99L);
        deque.insertRight(90L);
        deque.insertRight(76L);

        deque.display();

        System.out.println(deque.peekLeft());
        System.out.println(deque.peekRight());

        System.out.println(deque.popLeft());
        System.out.println(deque.popRight());
        deque.display();
        System.out.println(deque.popLeft());
        System.out.println(deque.popRight());
        System.out.println(deque.popRight());
        System.out.println(deque.popRight());
        System.out.println(deque.popRight());
        System.out.println(deque.popRight());
        System.out.println(deque.peekRight());
    }
}