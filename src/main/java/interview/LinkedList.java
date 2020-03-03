package interview;

public class LinkedList {

    Node head;

    public LinkedList(Node head) {
        this.head = head;
    }

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public static void main(String[] args) {
        Node third = new Node("third", null);
        Node second = new Node("second", third);
        Node first = new Node("first", second);
        LinkedList list = new LinkedList(first);

        reverse(list);

        System.out.println("");
    }

    public static void reverse(LinkedList aList) {
        Node reversed = null;
        Node current = aList.head;
        Node next = null;

        while (current != null) {
            next = current.next;
            current.next = reversed;
            reversed = current;
            current = next;

        }

        aList.head = reversed;
    }

    private static class Node {
        String item;
        Node next;

        public Node(String item, Node next) {
            this.item = item;
            this.next = next;
        }

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
}
