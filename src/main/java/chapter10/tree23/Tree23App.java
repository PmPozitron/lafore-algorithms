package chapter10.tree23;

// tree234.java
// demonstrates 234 tree
// to run this program: C>java Tree234App

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

////////////////////////////////////////////////////////////////
class DataItem {
    public long dData;          // one data item

    //--------------------------------------------------------------
    public DataItem(long dd)    // constructor
    {
        dData = dd;
    }

    //--------------------------------------------------------------
    public void displayItem()   // display item, format "/27"
    {
        System.out.print("/" + dData);
    }
//--------------------------------------------------------------
}  // end class DataItem

////////////////////////////////////////////////////////////////
class Node {
    private static final int ORDER = 3;
    private int numItems;
    private Node parent;
    private Node childArray[] = new Node[ORDER];
    private DataItem itemArray[] = new DataItem[ORDER - 1];

    // -------------------------------------------------------------
    // connect child to this node
    public void connectChild(int childNum, Node child) {
        childArray[childNum] = child;
        if (child != null)
            child.parent = this;
    }

    // -------------------------------------------------------------
    // disconnect child from this node, return it
    public Node disconnectChild(int childNum) {
        Node tempNode = childArray[childNum];
        childArray[childNum] = null;
        return tempNode;
    }

    // -------------------------------------------------------------
    public Node getChild(int childNum) {
        return childArray[childNum];
    }

    // -------------------------------------------------------------
    public Node getParent() {
        return parent;
    }

    // -------------------------------------------------------------
    public boolean isLeaf() {
        return (childArray[0] == null) ? true : false;
    }

    // -------------------------------------------------------------
    public int getNumItems() {
        return numItems;
    }

    // -------------------------------------------------------------
    public DataItem getItem(int index)   // get DataItem at index
    {
        return itemArray[index];
    }

    // -------------------------------------------------------------
    public boolean isFull() {
        return (numItems == ORDER - 1) ? true : false;
    }

    // -------------------------------------------------------------
    public int findItem(long key)       // return index of
    {                                    // item (within node)
        for (int j = 0; j < ORDER - 1; j++)         // if found,
        {                                 // otherwise,
            if (itemArray[j] == null)          // return -1
                break;
            else if (itemArray[j].dData == key)
                return j;
        }
        return -1;
    }  // end findItem

    // -------------------------------------------------------------
    public int insertItem(DataItem newItem) {
        // assumes node is not full
        numItems++;                          // will add new item
        long newKey = newItem.dData;         // key of new item

        for (int j = ORDER - 2; j >= 0; j--)        // start on right,
        {                                 //    examine items
            if (itemArray[j] == null)          // if item null,
                continue;                      // go left one cell
            else                              // not null,
            {                              // get its key
                long itsKey = itemArray[j].dData;
                if (newKey < itsKey)            // if it's bigger
                    itemArray[j + 1] = itemArray[j]; // shift it right
                else {
                    itemArray[j + 1] = newItem;   // insert new item
                    return j + 1;                 // return index to
                }                           //    new item
            }  // end else (not null)
        }  // end for                     // shifted all items,
        itemArray[0] = newItem;              // insert new item
        return 0;
    }  // end insertItem()

    // -------------------------------------------------------------
    public DataItem removeItem()        // remove largest item
    {
        // assumes node not empty
        DataItem temp = itemArray[numItems - 1];  // save item
        itemArray[numItems - 1] = null;           // disconnect it
        numItems--;                             // one less item
        return temp;                            // return item
    }

    // -------------------------------------------------------------
    public void displayNode()           // format "/24/56/74/"
    {
        for (int j = 0; j < numItems; j++)
            itemArray[j].displayItem();   // "/56"
        System.out.println("/");         // final "/"
    }
// -------------------------------------------------------------
}  // end class Node

////////////////////////////////////////////////////////////////
class Tree23 {
    private Node root = new Node();            // make root node

    // -------------------------------------------------------------
    public int find(long key) {
        Node curNode = root;
        int childNumber;
        while (true) {
            if ((childNumber = curNode.findItem(key)) != -1)
                return childNumber;               // found it
            else if (curNode.isLeaf())
                return -1;                        // can't find it
            else                                 // search deeper
                curNode = getNextChild(curNode, key);
        }  // end while
    }

    // -------------------------------------------------------------
    // insert a DataItem
    public void insert(long dValue) {
        Node curNode = root;
        DataItem tempItem = new DataItem(dValue);

        while (true) {
            if (curNode.isFull())               // if node full,
            {
                split(curNode);                   // split it
                curNode = curNode.getParent();    // back up
                // search once
                curNode = getNextChild(curNode, dValue);
            }  // end if(node is full)

            else if (curNode.isLeaf())          // if node is leaf,
                break;                            // go insert
                // node is not full, not a leaf; so go to lower level
            else
                curNode = getNextChild(curNode, dValue);
        }  // end while

        curNode.insertItem(tempItem);       // insert new DataItem
    }  // end insert()

    public void insert23(long value) {
        Node current = root;

        while (!current.isLeaf()) {
            current = getNextChild(current, value);
        }

        DataItem newDataItem = new DataItem(value);
        if (!current.isFull()) {
            current.insertItem(newDataItem);

        } else {
            split23(current, newDataItem, null);

        }
    }

    public void split23(Node toBeSplit, DataItem newItem, Node toBeConnected) {
        if (toBeSplit == root) {
            Node newRoot = new Node();
            newRoot.connectChild(0, toBeSplit);
            root = newRoot;
        }
        // decide what to shift up and what to put in new right sibling
        DataItem right = toBeSplit.removeItem();
        DataItem left = toBeSplit.removeItem();
        DataItem toBeShifted = null;
        Node newRight = new Node();

        if (newItem.dData < left.dData) {
            toBeShifted = left;
            newRight.insertItem(right);
            toBeSplit.insertItem(newItem);

        } else if (right.dData < newItem.dData) {
            toBeShifted = right;
            newRight.insertItem(newItem);
            toBeSplit.insertItem(left);

        } else {
            toBeShifted = newItem;
            newRight.insertItem(right);
            toBeSplit.insertItem(left);
        }
        // split parent providing new right sibling for distributing parent's children purposes
        if (toBeSplit.getParent().isFull()) {
            split23(toBeSplit.getParent(), toBeShifted, newRight);

        } else {
            toBeSplit.getParent().insertItem(toBeShifted);

        }
        Node[] nodes = new Node[4];
        for (int i = 0; i < 3; i++) {
            nodes[i] = toBeSplit.getChild(i);
        }
        nodes[3] = toBeConnected;
//      distribute children bitween node which is being split and new right sibling
        for (Node node : nodes) {
            if (node == null || node.getItem(0) == null) {
                continue;
            }
            if (node.getItem(0).dData < toBeSplit.getItem(0).dData) {
                toBeSplit.connectChild(0, node);

            } else if (node.getItem(0).dData < toBeShifted.dData) {
                toBeSplit.connectChild(1, node);

            } else if (node.getItem(0).dData < newRight.getItem(0).dData) {
                newRight.connectChild(0, node);

            } else {
                newRight.connectChild(1, node);
            }
        }
//      shift children of parent if node being split is on the left of parent
        if (toBeSplit.getParent().getChild(0) == toBeSplit) {
            toBeSplit.getParent().connectChild(2, toBeSplit.getParent().disconnectChild(1));
            toBeSplit.getParent().connectChild(1, newRight);

        } else {
            toBeSplit.getParent().connectChild(2, newRight);
        }
    }

    // -------------------------------------------------------------
    public void split(Node thisNode)     // split the node
    {
        // assumes node is full
        DataItem itemB, itemC;
        Node parent, child2, child3;
        int itemIndex;

        itemC = thisNode.removeItem();    // remove items from
        itemB = thisNode.removeItem();    // this node
        child2 = thisNode.disconnectChild(2); // remove children
        child3 = thisNode.disconnectChild(3); // from this node

        Node newRight = new Node();       // make new node

        if (thisNode == root)                // if this is the root,
        {
            root = new Node();                // make new root
            parent = root;                    // root is our parent
            root.connectChild(0, thisNode);   // connect to parent
        } else                              // this node not the root
            parent = thisNode.getParent();    // get parent

        // deal with parent
        itemIndex = parent.insertItem(itemB); // item B to parent
        int n = parent.getNumItems();         // total items?

        for (int j = n - 1; j > itemIndex; j--)          // move parent's
        {                                      // connections
            Node temp = parent.disconnectChild(j); // one child
            parent.connectChild(j + 1, temp);        // to the right
        }
        // connect newRight to parent
        parent.connectChild(itemIndex + 1, newRight);

        // deal with newRight
        newRight.insertItem(itemC);       // item C to newRight
        newRight.connectChild(0, child2); // connect to 0 and 1
        newRight.connectChild(1, child3); // on newRight
    }  // end split()

    // -------------------------------------------------------------
    // gets appropriate child of node during search for value
    public Node getNextChild(Node theNode, long theValue) {
        int j;
        // assumes node is not empty, not full, not a leaf
        int numItems = theNode.getNumItems();
        for (j = 0; j < numItems; j++)          // for each item in node
        {                               // are we less?
            if (theValue < theNode.getItem(j).dData)
                return theNode.getChild(j);  // return left child
        }  // end for                   // we're greater, so
        return theNode.getChild(j);        // return right child
    }

    // -------------------------------------------------------------
    public void displayTree() {
        recDisplayTree(root, 0, 0);
    }

    // -------------------------------------------------------------
    private void recDisplayTree(Node thisNode, int level, int childNumber) {
        System.out.print("level=" + level + " child=" + childNumber + " ");
        thisNode.displayNode();               // display this node

        // call ourselves for each child of this node
        int numItems = thisNode.getNumItems();
        for (int j = 0; j < numItems + 1; j++) {
            Node nextNode = thisNode.getChild(j);
            if (nextNode != null)
                recDisplayTree(nextNode, level + 1, j);
            else
                return;
        }
    }  // end recDisplayTree()
// -------------------------------------------------------------\
}  // end class Tree234

////////////////////////////////////////////////////////////////
public class Tree23App {
    public static void main(String[] args) throws IOException {
        boolean switchedOn = true;

        long value;
        Tree23 theTree = new Tree23();

//        theTree.insert23(50);
//        theTree.insert23(60);
//        theTree.insert23(90);
//        theTree.insert23(40);
//        theTree.insert23(70);
//        theTree.insert23(80);
//        theTree.insert23(30);
//        theTree.insert23(95);
//        theTree.insert23(35);
//        theTree.insert23(55);
//        theTree.insert23(3);
//        theTree.insert23(52);
//        theTree.insert23(92);
//        theTree.insert23(93);
//        theTree.insert23(94);

        for (int i = 0; i < 25; i++) {
            long newValue = (long) (Math.random() * 100);
            while (theTree.find(newValue) != -1) {
                newValue = (long) (Math.random() * 100);
            }
            theTree.insert23(newValue);
        }
        while (switchedOn) {
            System.out.print("Enter first letter of ");
            System.out.print("display, insert, find, minimum, traverse or quit: ");
            char choice = getChar();
            switch (choice) {
                case 'd':
                    theTree.displayTree();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value = getInt();
                    theTree.insert23(value);
                    break;
                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getInt();
                    int found = theTree.find(value);
                    if (found != -1)
                        System.out.println("Found " + value);
                    else
                        System.out.println("Could not find " + value);
                    break;
                case 'q':
                    switchedOn = false;
                    break;
                case 'm':
                    System.out.println();
                    break;
                case 't':
                    break;
                case 's':
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }  // end switch
        }  // end while
    }  // end main()

    //--------------------------------------------------------------
    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    //--------------------------------------------------------------
    public static char getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }

    //-------------------------------------------------------------
    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }
//-------------------------------------------------------------
}  // end class Tree234App
////////////////////////////////////////////////////////////////


