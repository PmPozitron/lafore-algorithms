package chaper12.searchtreeheap;

public class SearchTreeHeapApp {
    public static void main(String[] args) {
        SearchTreeHeap heap = new SearchTreeHeap();

        for (int i = 0; i < 13; i++) {
            heap.insert(i);
        }

        System.out.println(heap);
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

    public void change() {

    }

    public int peekMax() {
        return root.getValue();
    }

    public Node pollMax() {
        return null;
    }

    private Node insertNewNode(int value) {
        if (root == null) {
            root = new Node(value);
            nodesCount++;
            return root;
        }

        String pathToNull = Integer.toBinaryString(nodesCount + 1).substring(1);
        if (pathToNull.isEmpty()) {
            Node newNode = new Node(value);
            newNode.setParent(root);
            root.setLeft(newNode);
            nodesCount++;
            return newNode;
        }

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
            while (current != null) {
                if ('0' == elememt) {
                    current = current.getLeft();
                    parent = current;
                } else {
                    current = current.getRight();
                    parent = current;
                }
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

    private void trickleDown() {

    }
}
