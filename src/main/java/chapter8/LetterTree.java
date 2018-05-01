package chapter8;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class LetterTree {
    public static void main(String[] args) {
        new LetterTree().createTreeFromString_83("abcdefghijklmno");

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

    private void createTreeFromString_82(String aString) {
        LinkedList<Node> nodes = new LinkedList<Node>();
        for (char letter : aString.toCharArray()) {
            nodes.addLast(new Node(letter, null, null));
        }
        LinkedList<Node> compoundNodes = new LinkedList<Node>();

        while (! nodes.isEmpty()) {
            Node compound = new Node('+', nodes.poll(), null);
            if (! nodes.isEmpty()) {
                compound.setRight(nodes.poll());
            }
            compoundNodes.addLast(compound);
        }

        while(compoundNodes.size() > 1) {
            Node aNewOne = new Node('+', compoundNodes.poll(), compoundNodes.poll());
            compoundNodes.addLast(aNewOne);
        }

        System.out.println(compoundNodes.size());
        Tree tree = new Tree(compoundNodes.poll());
        tree.displayTree();
    }

    private void createTreeFromString_83(String aString) {
        Node[] nodes = new Node[aString.length()];
        for (int i = 0; i < aString.length(); i++) {
            nodes[i] = new Node(aString.charAt(i), null, null);
        }

        for (int i=0,j=1; i<nodes.length; i++) {
            if (j < nodes.length) {
                nodes[i].setLeft(nodes[j++]);
            }
            if (j < nodes.length) {
                nodes[i].setRight(nodes[j++]);
            }
        }

        Tree tree = new Tree(nodes[0]);
        tree.displayTree();
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

        public void displayTree() {
            Stack globalStack = new Stack();
            globalStack.push(root);
            int nBlanks = 32;
            boolean isRowEmpty = false;
            System.out.println("......................................................");
            while(! isRowEmpty) {
                Stack localStack = new Stack();
                isRowEmpty = true;

                for(int j=0; j<nBlanks; j++) {
                    System.out.print(' ');
                }

                while(! globalStack.isEmpty()) {
                    Node temp = (Node)globalStack.pop();

                    if(temp != null) {
                        System.out.print(temp.getValue());
                        localStack.push(temp.getLeft());
                        localStack.push(temp.getRight());

                        if(temp.getLeft() != null || temp.getRight() != null) {
                            isRowEmpty = false;
                        }

                    } else {
                        System.out.print("--");
                        localStack.push(null);
                        localStack.push(null);

                    } for(int j=0; j<nBlanks*2-2; j++) {
                        System.out.print(' ');
                    }
                }  // end while globalStack not empty

                System.out.println();
                nBlanks /= 2;

                while(localStack.isEmpty()==false) {
                    globalStack.push(localStack.pop());
                }
            }  // end while isRowEmpty is false
            System.out.println("......................................................");
        }  // end displayTree()
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
