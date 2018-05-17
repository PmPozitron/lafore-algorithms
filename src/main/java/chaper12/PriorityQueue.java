package chaper12;

import java.io.IOException;

import static chaper12.HeapApp.getChar;
import static chaper12.HeapApp.getInt;

public class PriorityQueue {

    private Heap heap;

    public PriorityQueue() {
        this.heap = new Heap(50);
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    public boolean insert (int value) {
        return heap.insert(value);
    }

    public int remove() {
        return heap.remove().getKey();
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void display() {
        heap.displayHeap();
    }

    public static void main(String[] args) throws IOException {
        boolean switchedOn = true;
        PriorityQueue queue = new PriorityQueue();
        int value, value2;
        boolean success;

        queue.insert(70);           // insert 10 items
        queue.insert(40);
        queue.insert(50);
        queue.insert(20);
        queue.insert(60);
        queue.insert(100);
        queue.insert(80);
        queue.insert(30);
        queue.insert(10);
        queue.insert(90);
        
        while (switchedOn) {
            System.out.print("Enter first letter of ");
            System.out.print("show, insert, remove, quit: ");
            int choice = getChar();

            switch(choice) {
                case 's':                        // show
//                    System.out.println(queue.toString());
                    queue.display();
                    break;
                case 'i':                        // insert
                    System.out.print("Enter value to insert: ");
                    value = getInt();
                    success = queue.insert(value);
                    if( !success )
                        System.out.println("Can't insert; heap full");
                    break;
                case 'r':                        // remove
                    if( !queue.isEmpty() )
                        System.out.println(queue.remove());
                    else
                        System.out.println("Can't remove; heap empty");
                    break;
                case 'q':
                    switchedOn = false;
                    break;
                default:
                    System.out.println("Invalid entry\n");

            }
        }
    }

}
