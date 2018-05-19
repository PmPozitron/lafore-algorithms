package chaper12.searchtreeheap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SearchTreeHeapApp {
    public static void main(String[] args) {
        SearchTreeHeap heap = new SearchTreeHeap();
        int size = 10;
//        List<Integer> values = asList(8, 5, 4, 1, 3, 6, 0, 2, 7);
        List<Integer> values = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            values.add(i);
        }
        Collections.shuffle(values);

        for (int i = 0; i < values.size(); i++) {
            heap.insert(values.get(i) * 2);
        }

//        for (int i = 0; i < values.size(); i++) {
//            System.out.println(heap.pollMax());
//        }

        System.out.println(heap);
        heap.change(18, 180);
        System.out.println(heap);
        heap.change(180, 13);
        System.out.println(heap);
        heap.change(2, 1);
        System.out.println(heap);
        heap.change(1, 11);
        System.out.println(heap);
        heap.change(12, 5);
        System.out.println(heap);
        heap.change(6, 15);
        System.out.println(heap);
        //        heap.change(0, -1);
//        System.out.println(heap);

    }
}

class Node {
    private int value;
    private Node left;
    private Node right;
    private Node parent;

    public Node() {
    }

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}

class SearchTreeHeap {
    private Node root;
    private int nodesCount;

    public SearchTreeHeap() {
    }

    public int getNodesCount() {
        return nodesCount;
    }

    public void setNodesCount(int nodesCount) {
        this.nodesCount = nodesCount;
    }

    /**
     * It turns out there’s a simple relationship between the number of nodes in the tree
     * and the binary number that codes the path to the last node.
     * Assume the root is numbered 1; the next row has nodes 2 and 3; the third row has nodes 4, 5, 6, and 7; and so on.
     * Start with the node number you want to find. This will be the last node or the first null node.
     * Convert the node number to binary. For example, say there are 29 nodes in the tree and you want to find the last node.
     * The number 29 decimal is 11101 binary. Remove the initial 1, leaving 1101.
     * This is the path from the root to node 29: right, right, left, right.
     * The first available null node is 30, which (after removing the initial 1) is 1110 binary: right, right, right, left.
     *
     * @return nodes count as binary string
     */
    private String nodesCountAsBinary() {
        return Integer.toBinaryString(nodesCount).substring(1);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(int value) {
        Node newNode = insertNewNode(value);
        trickleUp(newNode);
    }

    public void change(int oldValue, int newValue) {
        List<Node> nodes = traverseIntoNodes();
        Node toBeChanged = null;
        for (Node node : nodes) {
            if (node != null && node.getValue() == oldValue) {
                toBeChanged = node;
            }
        }
        if (toBeChanged == null) {
            return;
        }
        toBeChanged.setValue(newValue);
        if (nodes.size() == 1) {
            return;
        }

        boolean isRoot = toBeChanged == root;
        boolean isLeaf = toBeChanged.getLeft() == null;
        Node bigger =
                toBeChanged.getRight() == null ||
                        toBeChanged.getLeft().getValue() > toBeChanged.getRight().getValue()
                ? toBeChanged.getLeft()
                : toBeChanged.getRight();

        if (isRoot && newValue > bigger.getValue()) {
            return;
        }

        if (isRoot && newValue < bigger.getValue()) {
            trickleDown(toBeChanged);
            return;
        }

        if (isLeaf && newValue < toBeChanged.getParent().getValue()) {
            return;
        }

        if (isLeaf && newValue > toBeChanged.getParent().getValue()) {
            trickleUp(toBeChanged);
            return;
        }

        if (newValue > toBeChanged.getParent().getValue()) {
            trickleUp(toBeChanged);

        } else if (newValue < bigger.getValue()) {
            trickleDown(toBeChanged);
        }
    }

    public int peekMax() {
        return root.getValue();
    }

    public int pollMax() {
        Node exRoot = root;
        Node last = findLastNode();
        if (last == root) {
            root = null;
            return exRoot.getValue();
        }
        Node parentOfLast = last.getParent();
        boolean isLeft = last == parentOfLast.getLeft() ? true : false;
        if (isLeft) {
            parentOfLast.setLeft(null);
        } else {
            parentOfLast.setRight(null);
        }
        last.setParent(null);
        root = last;
        root.setLeft(exRoot.getLeft());
        root.setRight(exRoot.getRight());
        if (exRoot.getLeft() != null) {
            exRoot.getLeft().setParent(root);
        }

        if (exRoot.getRight() != null) {
            exRoot.getRight().setParent(root);
        }

        trickleDown(root);
        nodesCount--;

        return exRoot.getValue();
    }

    public void traverse() {
        traverse(root);
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        List<Integer> numbers = traverseIntoIntegers();
        int i = 0;
        for (Integer integer : numbers) {
            result.append(integer).append(" ");
        }

        return result.toString();
    }

    public List<Node> traverseIntoNodes() {
        List<Node> result = new ArrayList<Node>();
        LinkedList<Node> stack = new LinkedList<Node>();
        stack.addLast(root);
        while (stack.size() != this.getNodesCount()) {
            Node current = stack.peek();
            if (current == null) {
                continue;
            }
            result.add(stack.poll());
            stack.addLast(current.getLeft());
            stack.addLast(current.getRight());
        }

        return result;
    }

    public List<Integer> traverseIntoIntegers() {
        List<Integer> result = new ArrayList<Integer>();
        LinkedList<Node> stack = new LinkedList<Node>();
        stack.addLast(root);

        while (! stack.isEmpty()) {
            Node current = stack.poll();
            if (current == null) {
                continue;
            }
            result.add(current.getValue());
            stack.addLast(current.getLeft());
            stack.addLast(current.getRight());
        }
        return result;
    }

    public boolean containsDups() {
        List<Integer> contents = traverseIntoIntegers();
        return new HashSet<Integer>(contents).size() != contents.size();
    }

    private void traverse(Node localRoot) {
        if (localRoot != null) {
            traverse(localRoot.getLeft());
            System.out.print(localRoot.getValue() + " ");
            traverse(localRoot.getRight());
        }
    }

    private Node insertNewNode(int value) {
        if (root == null) {
            root = new Node(value);
            nodesCount++;
            return root;
        }

        String pathToNull = Integer.toBinaryString(nodesCount + 1).substring(1);
//        if (pathToNull.isEmpty()) {
//            Node newNode = new Node(value);
//            newNode.setParent(root);
//            root.setLeft(newNode);
//            nodesCount++;
//            return newNode;
//        }

        Node parent = null;
        Node current = root;
        boolean isLeft = true;

        for (char elememt : pathToNull.toCharArray()) {
            if ('0' == elememt) {
                parent = current;
                current = current.getLeft();
                isLeft = true;

            } else if ('1' == elememt) {
                parent = current;
                current = current.getRight();
                isLeft = false;
            }
        }
        current = new Node(value);
        current.setParent(parent);
        if (isLeft) {
            parent.setLeft(current);
        } else {
            parent.setRight(current);
        }
        nodesCount++;
        return current;
    }

    private Node findAPlaceForNew() {
        String pathToNull = Integer.toBinaryString(nodesCount + 1).substring(1);
        return findByPath(pathToNull);
    }

    private Node findLastNode() {
        String pathToLast = Integer.toBinaryString(nodesCount).substring(1);
        return findByPath(pathToLast);
    }

    private Node findByPath(String path) {
        Node parent = null;
        Node current = root;

        for (char elememt : path.toCharArray()) {
            if ('0' == elememt) {
                current = current.getLeft();
                parent = current;
            } else {
                current = current.getRight();
                parent = current;

            }
        }
        return current;
    }

    private void trickleUp(Node trickled) {
        if (root == trickled) {
            return;
        }
        Node current = trickled;
        int temp = trickled.getValue();
        while (current != root && current.getParent().getValue() < temp) {
            current.setValue(current.getParent().getValue());
            current = current.getParent();
        }
        current.setValue(temp);
    }

    private void trickleDown(Node trickled) {

        Node current = trickled;
        Node parent = null;
        int temp = trickled.getValue();

        while (current.getLeft() != null) {
            parent = current;
            if (current.getRight() == null || current.getLeft().getValue() > current.getRight().getValue()) {
                current = current.getLeft();

            } else {
                current = current.getRight();
            }

            if (current.getValue() > temp) {
                parent.setValue(current.getValue());
                current.setValue(temp);

            } else {
                parent.setValue(temp);
            }
        }
    }
}
