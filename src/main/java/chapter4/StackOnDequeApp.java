package chapter4;

public class StackOnDequeApp {

    public static void main(String[] args) {
        new StackOnDequeApp().testDrive();
    }

    private void testDrive() {
        StackOnDeque stack = new StackOnDeque();
        stack.display();
        System.out.println(stack.isEmpty());
        System.out.println(stack.isFull());

        stack.push(1L);
        stack.push(2L);
        stack.push(3L);
        stack.push(4L);
        stack.push(5L);
        stack.display();
        System.out.println(stack.isEmpty());
        System.out.println(stack.isFull());

        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.display();
        System.out.println(stack.isEmpty());
        System.out.println(stack.isFull());

        stack.push(6L);
        stack.push(7L);
        stack.push(8L);
        stack.push(9L);
        stack.push(10L);
        stack.push(11L);
        stack.display();
        System.out.println(stack.isEmpty());
        System.out.println(stack.isFull());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());

        stack.push(12L);
        stack.push(13L);
        stack.push(14L);
        stack.push(15L);
        stack.display();
        System.out.println(stack.isEmpty());
        System.out.println(stack.isFull());
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.pop());


    }
}

class StackOnDeque {

    private Deque core;

    public StackOnDeque() {
        core = new Deque(10);
    }

    public void push(long item) {
        core.insertRight(item);
    }

    public long pop() {
        return core.popRight();
    }

    public long peek() {
        return core.peekRight();
    }

    public boolean isEmpty() {
        return core.isEmpty();
    }

    public boolean isFull() {
        return core.isFull();
    }

    public void display() {
        core.display();
    }
}
