package chapter5.circular;

public class CircularListApp {
    public static void main(String[] args) {
        new CircularListApp().josephusProblem(17, 5, 5);

    }

    public void testDrive() {
        CircularList list = new CircularList(1L);
        list.display();

        list.insert(5L);
        list.display();
        list.insert(9L);
        list.display();
        list.insert(3L);

        list.display();

        System.out.println(list.search(9L).getValue());
        System.out.println(list.delete(9L).getValue());

        list.display();
    }


    public long josephusProblem(int itemsQuantity, int startPosition, int count) {
        CircularList theList = new CircularList(1L);
        for (int i = 2; i <= itemsQuantity; i++) {
            theList.insert(i);
        }

        theList.display();

        for (int i = 0; i < startPosition; i++) {
            theList.step();
        }

        do {
            theList.step(count);
            System.out.println(theList.delete(theList.getCurrent().getValue()).getValue());
            theList.step();

        } while (!theList.hasSingleElement());

        theList.display();

        return theList.getCurrent().getValue();
    }
}

class Link {
    private long value;
    private Link next;

    Link(long value) {
        this.value = value;
    }

    void setNext(Link next) {
        this.next = next;
    }

    Link getNext() {
        return this.next;
    }

    long getValue() {
        return this.value;
    }
}

class CircularList {
    private Link current;

    public Link getCurrent() {
        return current;
    }

    public CircularList(long current) {
        this.current = new Link(current);
        this.current.setNext(this.current);
    }

    public void insert(long value) {
        Link aNewOne = new Link(value);
        aNewOne.setNext(this.current.getNext());
        current.setNext(aNewOne);
        step();
    }

    public Link search(long value) {
        if (isEmpty()) {
            System.out.println("is empty");
            return null;
        }

        Link startPosition = current;

        do {
            if (current.getValue() == value) {
                return current;
            }
            step();

        } while (startPosition != current);

        return null;
    }

    public Link delete(long value) {
        if (isEmpty()) {
            System.out.println("is empty");
            return null;
        }

        Link toBeDeleted = current;
        Link startPosition = current.getNext();

        do {
            if (current.getNext().getValue() == value) {
                toBeDeleted = current.getNext();
                current.setNext(current.getNext().getNext());
                return toBeDeleted;
            }
            step();

        } while (current.getNext() != startPosition);

        return null;
    }

    public Link step() {
        Link next = current.getNext();
        this.current = next;
        return current;
    }

    public Link step(int count) {
        for (int i = 0; i < count; i++) {
            step();
        }

        return current;
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("is empty");
            return;
        }

        Link startPosition = current;

        do {
            System.out.printf("%d, ", current.getValue());
            step();

        } while (current != startPosition);

        System.out.println();
    }

    public boolean isEmpty() {
        return current == null;
    }

    public boolean hasSingleElement() {
        return current.getNext() == current;
    }
}
