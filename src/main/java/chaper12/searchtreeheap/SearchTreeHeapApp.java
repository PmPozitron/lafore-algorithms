package chaper12.searchtreeheap;

public class SearchTreeHeapApp {
}

class Node {
    private int value;
    private Node left;
    private Node right;
    private Node parent;

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

class SearchTree {
    private Node root;
    private int nodesCount;

    public SearchTree() {
        this.root = new Node();
        nodesCount++;
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
     * @return nodes count as binary string
     */
    private String nodesCountAsBinary() {
        return Integer.toBinaryString(nodesCount).substring(1);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert (int value) {

    }

    public void trickleUp() {

    }

    public void trickleDown() {

    }

    public void change() {

    }

    public Node peekMax() {
        return null;
    }

    public Node pollMax() {
        return null;
    }
}
