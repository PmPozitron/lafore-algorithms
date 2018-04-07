package chapter5.circular;

public class StackOnCircleApp {
    public static void main(String[] args) {
        new StackOnCircleApp().testDrive();
    }

    public void testDrive() {
        Stack theStack = new Stack(1L);
        theStack.display();

        theStack.push(3L);
        theStack.push(5L);
        theStack.push(13L);
        theStack.display();

        System.out.println(theStack.peek());
        System.out.println(theStack.pop());
        System.out.println(theStack.pop());
        System.out.println(theStack.pop());
        System.out.println(theStack.pop());
    }
}

class Stack {
    private CircularList list;

    public Stack(long initialValue) {
        list = new CircularList(initialValue);
    }

    public void push(long value) {
        list.insert(value);
    }

    public long peek() {
       return list.getCurrent().getValue();
    }

    public long pop() {
        return list.delete(peek()).getValue();
    }

    public void display() {
        list.display();
    }
}

