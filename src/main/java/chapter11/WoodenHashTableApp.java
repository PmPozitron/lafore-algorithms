package chapter11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WoodenHashTableApp {
    public static void main(String[] args) throws IOException {

        boolean switchedOn = true;
        int value;
        WoodenHashTable table = new WoodenHashTable(10);

//        theTree.insert(50);
//        theTree.insert(40);
//        theTree.insert(60);
//        theTree.insert(30);
//        theTree.insert(70);
//        theTree.insert(75);
//        theTree.insert(76);
//        theTree.insert(45);
//        theTree.insert(35);
//        theTree.insert(15);
//        int[] values = new int[]{12, 13, 59, 5, 57, 34, 89, 65, 28, 15};
        List<Integer> values = new ArrayList<Integer>();

        for (int i = 0; i < 25; i++) {
            int newNumber = (int) (Math.random() * 100);
            while (values.contains(newNumber)) {
                newNumber = (int) (Math.random() * 100);
            }
            values.add(newNumber);
        }

        for (int number : values) {
            table.insert(number);
        }

        while (switchedOn) {
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, find, delete or quit: ");
            char choice = getChar();
            switch (choice) {
                case 's':
                    table.displayTable();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value = getInt();
                    table.insert(value);
                    break;
                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getInt();
                    boolean found = table.find(value) == null ? false : true;
                    System.out.println(value + " found " + found);
                    break;
                case 'q':
                    switchedOn = false;
                    break;
                case 'd':
                    System.out.print("Enter value to delete: ");
                    value = getInt();
                    table.delete(value);
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }  // end switch
        }  // end while

    }
    //--------------------------------------------------------------
    public static String getString() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    //--------------------------------------------------------------
    public static char getChar() throws IOException
    {
        String s = getString();
        return s.charAt(0);
    }
    //-------------------------------------------------------------
    public static int getInt() throws IOException
    {
        String s = getString();
        return Integer.parseInt(s);
    }


}
class Node {
    public int iData;              // data item (key)
    public double dData;           // data item
    public Node leftChild;         // this node's left child
    public Node rightChild;        // this node's right child

    public void displayNode(){      // display ourself
        System.out.print('{');
        System.out.print(iData);
        System.out.print(", ");
        System.out.print(dData);
        System.out.print("} ");
    }
}  // end class Node
////////////////////////////////////////////////////////////////
class Tree{
    private Node root;             // first node of tree

    // -------------------------------------------------------------
    public Tree()                  // constructor
    { root = null; }            // no nodes in tree yet
    public boolean isEmpty() {
        return this.root == null;
    }
    // -------------------------------------------------------------
    public Node find(int key)      // find node with given key
    {                           // (assumes non-empty tree)
        Node current = root;               // start at root
        while(current.iData != key)        // while no match,
        {
            if(key < current.iData)         // go left?
                current = current.leftChild;
            else                            // or go right?
                current = current.rightChild;
            if(current == null)             // if no child,
                return null;                 // didn't find it
        }
        return current;                    // found it
    }  // end find()
    // -------------------------------------------------------------
    public void insert(int id, double dd)
    {
        Node newNode = new Node();    // make new node
        newNode.iData = id;           // insert data
        newNode.dData = dd;
        if(root==null)                // no node in root
            root = newNode;
        else                          // root occupied
        {
            Node current = root;       // start at root
            Node parent;
            while(true)                // (exits internally)
            {
                parent = current;
                if(id < current.iData)  // go left?
                {
                    current = current.leftChild;
                    if(current == null)  // if end of the line,
                    {                 // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                }  // end if go left
                else                    // or go right?
                {
                    current = current.rightChild;
                    if(current == null)  // if end of the line
                    {                 // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                }  // end else go right
            }  // end while
        }  // end else not root
    }  // end insert()
    // -------------------------------------------------------------
    public boolean delete(int key) // delete node with given key
    {                           // (assumes non-empty list)
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while(current.iData != key)        // search for node
        {
            parent = current;
            if(key < current.iData)         // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            }
            else                            // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if(current == null)             // end of the line,
                return false;                // didn't find it
        }  // end while
        // found node to delete

        // if no children, simply delete it
        if(current.leftChild==null &&
                current.rightChild==null)
        {
            if(current == root)             // if root,
                root = null;                 // tree is empty
            else if(isLeftChild)
                parent.leftChild = null;     // disconnect
            else                            // from parent
                parent.rightChild = null;
        }

        // if no right child, replace with left subtree
        else if(current.rightChild==null)
            if(current == root)
                root = current.leftChild;
            else if(isLeftChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;

            // if no left child, replace with right subtree
        else if(current.leftChild==null)
            if(current == root)
                root = current.rightChild;
            else if(isLeftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;

        else  // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);

            // connect parent of current to successor instead
            if(current == root)
                root = successor;
            else if(isLeftChild)
                parent.leftChild = successor;
            else
                parent.rightChild = successor;

            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        }  // end else two children
        // (successor cannot have a left child)
        return true;                                // success
    }  // end delete()
    // -------------------------------------------------------------
    // returns node with next-highest value after delNode
    // goes to right child, then right child's left descendents
    private Node getSuccessor(Node delNode)
    {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild;   // go to right child
        while(current != null)               // until no more
        {                                 // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild;      // go to left child
        }
        // if successor not
        if(successor != delNode.rightChild)  // right child,
        {                                 // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }
    // -------------------------------------------------------------
    public void traverse(int traverseType)
    {
        switch(traverseType)
        {
            case 1: System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2: System.out.print("\nInorder traversal:  ");
                inOrder(root);
                break;
            case 3: System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }
    // -------------------------------------------------------------
    private void preOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            System.out.print(localRoot.iData + " ");
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }
    // -------------------------------------------------------------
    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.leftChild);
            System.out.print(localRoot.iData + " ");
            inOrder(localRoot.rightChild);
        }
    }
    // -------------------------------------------------------------
    private void postOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.print(localRoot.iData + " ");
        }
    }
    // -------------------------------------------------------------
    public void displayTree(){
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while(isRowEmpty==false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;

            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');

            while(globalStack.isEmpty()==false)
            {
                Node temp = (Node)globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.iData);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);

                    if(temp.leftChild != null ||
                            temp.rightChild != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<nBlanks*2-2; j++)
                    System.out.print(' ');
            }  // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }  // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    }  // end displayTree()
// -------------------------------------------------------------
}  // end class Tree
////////////////////////////////////////////////////////////////
class WoodenHashTable {
    private Tree[] hashArray;   // array of lists
    private int arraySize;
    // -------------------------------------------------------------
    public WoodenHashTable(int size)        // constructor
    {
        arraySize = size;
        hashArray = new Tree[arraySize];  // create array
        for(int j=0; j<arraySize; j++)          // fill array
            hashArray[j] = new Tree();     // with lists
    }
    // -------------------------------------------------------------
    public void displayTable()
    {
        for(int j=0; j<arraySize; j++) // for each cell,
        {
            System.out.print(j + ". "); // display cell number
//            hashArray[j].displayTree(); // display list
            hashArray[j].traverse(2);
        }
    }
    // -------------------------------------------------------------
    public int hashFunc(int key)      // hash function
    {
        return key % arraySize;
    }
    // -------------------------------------------------------------
    public void insert(int value)  // insert a link
    {
        int hashVal = hashFunc(value);   // hash the key
        if (this.hashArray[hashVal] != null &&
                ! this.hashArray[hashVal].isEmpty() &&
                this.hashArray[hashVal].find(value) != null) {
            System.out.println(value + " already exists");
            return;
        }
        hashArray[hashVal].insert(value, value); // insert at hashVal

    }  // end insert()
    // -------------------------------------------------------------
    public void delete(int key)       // delete a link
    {
        int hashVal = hashFunc(key);   // hash the key
        hashArray[hashVal].delete(key); // delete link
    }  // end delete()
    // -------------------------------------------------------------
    public Node find(int key)         // find link
    {
        int hashVal = hashFunc(key);   // hash the key
        Node theLink = hashArray[hashVal].find(key);  // get link
        return theLink;                // return link
    }
// -------------------------------------------------------------
}  // end class HashTable
////////////////////////////////////////////////////////////////
