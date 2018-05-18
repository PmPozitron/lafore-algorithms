package chaper12.binarytreeheap;

import java.io.IOException;

import static chaper12.binarytreeheap.BinaryTreeHeapApp.getChar;
import static chaper12.binarytreeheap.BinaryTreeHeapApp.getInt;

public class PriorityQueue {
    
    private Tree tree;

    public PriorityQueue() {
        this.tree = new Tree();
    }
    
    public void display() {
        tree.displayTree();
    }
    
    public void insert(int anInt, double aDouble) {
        tree.insert(anInt, aDouble);
    }
    
    public Node peekMax() {
        return tree.peekMax();
    }
    
    public Node pollMax() {
        return tree.pollMax();
    }

    public static void main(String[] args) throws IOException {
        int value;
        PriorityQueue queue = new PriorityQueue();

        queue.insert(50, 1.5);
        queue.insert(25, 1.2);
        queue.insert(75, 1.7);
        queue.insert(12, 1.5);
        queue.insert(37, 1.2);
        queue.insert(43, 1.7);
        queue.insert(30, 1.5);
        queue.insert(33, 1.2);
        queue.insert(87, 1.7);
        queue.insert(93, 1.5);
        queue.insert(97, 1.5);
        
        boolean run = true;
        while(run) {
            System.out.print("Enter first letter of show, insert, max or quit: ");
            int choice = getChar();
            switch(choice) {
                case 's':
                    queue.display();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value = getInt();
                    queue.insert(value, value + 0.9);
                    break;
                case 'q' :
                    System.out.println("quitting");
                    run = false;
                    break;
                case 'm' :
                    queue.pollMax().displayNode();
                    System.out.println();
                    break;
                default:
                    System.out.print("Invalid entry\n");
                    break;
            }  // end switch
        }  // end while
    }  // end main()
}
