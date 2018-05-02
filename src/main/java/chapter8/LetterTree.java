package chapter8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class LetterTree {
    private enum TRAVERSE_MODE {
        PREFIX, INFIX, POSTFIX
    }

    private final static String TASK_STRING =
            "Accept a text message, possibly of more than one line.\n" +
            "Create a Huffman tree for this message.\n" +
            "Create a code table.\n" +
            "Encode the message into binary.\n" +
            "Decode the message from binary back to text.";

    public static void main(String[] args) {
        String theString = "SUSIE SAYS IT IS EASY\n";
//        String theString = "SUSIE SAYS\n";
        LetterTree driver = new LetterTree();
        driver.huffmanProcessor_85(TASK_STRING);


    }

    private void parsingTestDrive() {
        Tree tree = parseArithmetic_84("AB+CD+*");

        for (TRAVERSE_MODE mode : TRAVERSE_MODE.values()) {
            tree.traverse(tree.getRoot(), mode);
        }
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

        while (!nodes.isEmpty()) {
            Node compound = new Node('+', nodes.poll(), null);
            if (!nodes.isEmpty()) {
                compound.setRight(nodes.poll());
            }
            compoundNodes.addLast(compound);
        }

        while (compoundNodes.size() > 1) {
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

        for (int i = 0, j = 1; i < nodes.length; i++) {
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

    private Tree parseArithmetic_84(String expression) {
        List<Character> operators = Arrays.asList('+', '-', '*', '/');
        LinkedList<Node> stack = new LinkedList<Node>();

        for (int i = 0; i < expression.length(); i++) {
            if (!operators.contains(expression.charAt(i))) {
                stack.push(new Node(expression.charAt(i), null, null));

            } else {
                stack.push(new Node(expression.charAt(i), stack.poll(), stack.poll()));
            }
        }

        Tree tree = new Tree(stack.poll());
        tree.displayTree();
        return tree;
    }

    private void huffmanProcessor_85(String aString) {
        Tree tree = createHuffmanTree(aString);
        Map<Character, String> encoding = createCodeTable(tree);
        String[] encoded = encode(aString, encoding);
        String decoded = decode(encoded, convertEncoding(encoding));
        System.out.println(Arrays.toString(encoded));
        System.out.println(decoded);

    }

    private Tree createHuffmanTree(String aString) {
        LinkedHashMap<Character, Integer> charsWeights = new LinkedHashMap<Character, Integer>();
        for (char letter : aString.toCharArray()) {
            Integer currentLetterWeight = charsWeights.get(letter);
            if (currentLetterWeight == null) {
                charsWeights.put(letter, 1);

            } else {
                charsWeights.put(letter, currentLetterWeight + 1);
            }
        }

        PriorityQueue<Node> forest = new PriorityQueue<Node>(charsWeights.size(), new Comparator<Node>() {
            public int compare(Node o1, Node o2) {
//              They are ordered by frequency, with the smallest frequency having the highest priority.
//              That is, when you remove a tree, it’s always the one with the least-used character.
                return o1.getWeight() - o2.getWeight();
            }
        });
        for (Map.Entry<Character, Integer> entry : charsWeights.entrySet()) {
            forest.add(new Node(entry.getKey(), entry.getValue(), null, null));
        }

        while (forest.size() > 1) {
            Node current = forest.poll();
            Node nextToCurrent = forest.poll();
            forest.add(new Node('_', current.getWeight() + nextToCurrent.getWeight(), current, nextToCurrent));
        }

        return new Tree(forest.poll());
    }

    private Map<Character, String> createCodeTable(Tree aTree) {

//        String[] encoding = new String['Z' - '\n'];
        LinkedHashMap<Character, String> encoding = new LinkedHashMap<Character, String>();
        huffmanCreator(aTree.getRoot(), encoding, new StringBuilder());
        return encoding;
    }

    private String decode(String[] toBeEncoded, Map<String, Character> encoding) {
        StringBuilder result = new StringBuilder();
        for (String token : toBeEncoded) {
            result.append(encoding.get(token));
        }
        return result.toString();
    }

    private String[] encode(String toBeDecoded, Map<Character, String> encoding) {
        String[] result = new String[toBeDecoded.length()];
        for (int i = 0; i < toBeDecoded.toCharArray().length; i++) {
            result[i] = encoding.get(toBeDecoded.charAt(i));
        }
        return result;
    }

    private Map<String, Character> convertEncoding(Map<Character, String> encoding) {
        Map<String, Character> result = new HashMap<String, Character>();
        for (Map.Entry<Character, String> entry : encoding.entrySet()) {
            result.put(entry.getValue(), entry.getKey());
        }

        return result;
    }

    public void huffmanCreator(Node current, Map<Character, String> result, StringBuilder token) {
        Node next  = current.getLeft();
        if (next != null) {
            token.append('0');
            huffmanCreator(next, result, token);
        }

        next = current.getRight();
        if (next != null) {
            token.append('1');
            huffmanCreator(next, result, token);
        }

        if ('_' != current.getValue()) {
            result.put(current.getValue(), token.toString());

        }
        if (token.length() > 0) {
//            token.delete(token.length() - 1, token.length());
            token.deleteCharAt(token.length()-1);
        }

        return;

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

        public void traverse(Node current, TRAVERSE_MODE mode) {
            if (TRAVERSE_MODE.PREFIX == mode) {
                prefixTraverse(current);

            } else if (TRAVERSE_MODE.INFIX == mode) {
                infixTraverse(current);

            } else if (TRAVERSE_MODE.POSTFIX == mode) {
                postfixTraverse(current);
            }
            System.out.println();
        }

        public void prefixTraverse(Node current) {
            if (current != null) {
                System.out.print(current.getValue());
                prefixTraverse(current.getLeft());
                prefixTraverse(current.getRight());
            }
        }

        public void infixTraverse(Node current) {
            if (current != null) {
                System.out.print('(');
                infixTraverse(current.getLeft());
                System.out.print(current.getValue());
                infixTraverse(current.getRight());
                System.out.print(')');
            }
        }

        public void postfixTraverse(Node current) {
            if (current != null) {
                postfixTraverse(current.getLeft());
                postfixTraverse(current.getRight());
                System.out.print(current.getValue());
            }
        }

        public void display() {
            LinkedList<Node> global = new LinkedList<Node>(Arrays.asList(getRoot()));
            boolean rowEmpty = false;
            while (!rowEmpty) {
                LinkedList<Node> local = new LinkedList<Node>();
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
                while (!local.isEmpty()) {
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
            while (!isRowEmpty) {
                Stack localStack = new Stack();
                isRowEmpty = true;

                for (int j = 0; j < nBlanks; j++) {
                    System.out.print(' ');
                }

                while (!globalStack.isEmpty()) {
                    Node temp = (Node) globalStack.pop();

                    if (temp != null) {
                        System.out.print(new String(new char[]{temp.getValue()}) + temp.getWeight());
                        localStack.push(temp.getLeft());
                        localStack.push(temp.getRight());

                        if (temp.getLeft() != null || temp.getRight() != null) {
                            isRowEmpty = false;
                        }

                    } else {
                        System.out.print("--");
                        localStack.push(null);
                        localStack.push(null);

                    }
                    for (int j = 0; j < nBlanks * 2 - 2; j++) {
                        System.out.print(' ');
                    }
                }  // end while globalStack not empty

                System.out.println();
                nBlanks /= 2;

                while (localStack.isEmpty() == false) {
                    globalStack.push(localStack.pop());
                }
            }  // end while isRowEmpty is false
            System.out.println("......................................................");
        }  // end displayTree()
    }

    static class Node {
        private char value;
        private int weight;
        private Node left;
        private Node right;

        public Node() {
        }

        public Node(char value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public Node(char value, int weight, Node left, Node right) {
            this(value, left, right);
            this.weight = weight;
        }

        public char getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
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
