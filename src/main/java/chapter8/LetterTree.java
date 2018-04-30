package chapter8;

import java.util.Arrays;
import java.util.LinkedList;

public class LetterTree {
    public static void main(String[] args) {
        new LetterTree().parseString();

    }

    private void testDrive() {

        Node a = new Node('a', null, null);
        Node b = new Node('b', null, null);
        Node c = new Node('c', null, null);
        Node d = new Node('d', null, null);
        Node e = new Node('e', null, null);

        Node ab = new Node('+', a, b);
        Node cd = new Node('+', c, d);
//        Node eAndNull = new Node('+', e, null);

        Node abcd = new Node('+', ab, cd);
//        Node eAndNullAndNull = new Node('+', eAndNull, null);

        Node root = new Node('+', abcd, e);
        Tree tree = new Tree(root);

        tree.display();

    }

    private void parseString() {
        String aString = "abcde";
        LinkedList<Node> nodes = new LinkedList<Node>();
        for (char letter : aString.toCharArray()) {
            nodes.push(new Node(letter, null, null));
        }
        LinkedList<Node> compoundNodes = new LinkedList<Node>();

        while (! nodes.isEmpty()) {
            Node compound = new Node('+', nodes.poll(), null);
            if (! nodes.isEmpty()) {
                compound.setRight(nodes.poll());
            }
            compoundNodes.push(compound);
        }

        LinkedList<Node> result = new LinkedList<Node>(compoundNodes);
        while(result.size() > 1) {
            Node aNewOne = new Node('+', result.poll(), result.poll());
            result.push(aNewOne);
        }

        System.out.println(result.size());
        Tree tree = new Tree(result.poll());
        tree.display();
    }

    static class Tree {
        private Node root;

        public Tree() {
            this.root = new Node('+', null, null);
        }

        public Tree(Node root) {
            this.root = root;
        }

        public Node getRoot() {
            return root;
        }

        public void setRoot(Node root) {
            this.root = root;
        }

        public void traverse(Node current) {
            if (current != null) {
                traverse(current.getLeft());
                System.out.println(current.getValue());
                traverse(current.getRight());
            }
        }

        public void display() {
            LinkedList<Node> global = new LinkedList<Node>(Arrays.asList(getRoot()));
            boolean rowEmpty = false;
            while (! rowEmpty) {
                LinkedList<Node>local = new LinkedList<Node>();
                rowEmpty = true;

                while (!global.isEmpty()) {
                    Node current = global.poll();
                    if (current != null) {
                        System.out.print(current.getValue());
                        local.push(current.getLeft());
                        local.push(current.getRight());

                        if (current.getLeft() != null || current.getRight() != null) {
                            rowEmpty = false;
                        }
                    } else {
                        local.push(null);
                        local.push(null);
                    }
                }
                System.out.println();
                while (! local.isEmpty()) {
                    global.push(local.poll());
                }
            }
        }
    }

    static class Node {
        private char value;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(char value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public char getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
