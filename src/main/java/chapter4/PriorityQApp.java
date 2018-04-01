package chapter4;

public class PriorityQApp {
    public static void main(String[] args) {
        new PriorityQApp().testDriveRevisedPriorityQ();

    }  // end main()

    public void testDrivePriorityQ() {
        PriorityQ thePQ = new PriorityQ(5);
        thePQ.insert(30);
        thePQ.insert(50);
        thePQ.insert(10);
        thePQ.insert(40);
        thePQ.insert(20);

        while( !thePQ.isEmpty()) {
            long item = thePQ.remove();
            System.out.print(item + " ");  // 10, 20, 30, 40, 50
        }  // end while
        System.out.println("");
    }

    public void testDriveRevisedPriorityQ() {
        PriorityQRevised queue = new PriorityQRevised(5);
        queue.display();

        queue.insert(3L);
        queue.insert(1L);
        queue.insert(5L);
        queue.insert(9L);
        queue.insert(7L);
        queue.display();

        System.out.println(queue.peekMax());
        System.out.println(queue.removeMax());
        System.out.println(queue.removeMax());
        queue.display();
        System.out.println(queue.peekMax());
        System.out.println(queue.removeMax());
        queue.display();
    }
//-------------------------------------------------------------
}  // end class PriorityQApp
////////////////////////////////////////////////////////////////

// priorityQ.java
// demonstrates priority queue
// to run this program: C>java PriorityQApp
////////////////////////////////////////////////////////////////
class PriorityQ
{
    // array in sorted order, from max at 0 to min at size-1
    protected int maxSize;
    protected long[] queArray;
    protected int nItems;
    //-------------------------------------------------------------
    public PriorityQ(int s)          // constructor
    {
        maxSize = s;
        queArray = new long[maxSize];
        nItems = 0;
    }
    //-------------------------------------------------------------
    public void insert(long item)    // insert item
    {
        int j;

        if(nItems==0)                         // if no items,
            queArray[nItems++] = item;         // insert at 0
        else                                // if items,
        {
            for(j=nItems-1; j>=0; j--)         // start at end,
            {
                if( item > queArray[j] )      // if new item larger,
                    queArray[j+1] = queArray[j]; // shift upward
                else                          // if smaller,
                    break;                     // done shifting
            }  // end for
            queArray[j+1] = item;            // insert it
            nItems++;
        }  // end else (nItems > 0)
    }  // end insert()
    //-------------------------------------------------------------
    public long remove()             // remove minimum item
    { return queArray[--nItems]; }
    //-------------------------------------------------------------
    public long peekMin()            // peek at minimum item
    { return queArray[nItems-1]; }
    //-------------------------------------------------------------
    public boolean isEmpty()         // true if queue is empty
    { return (nItems==0); }
    //-------------------------------------------------------------
    public boolean isFull()          // true if queue is full
    { return (nItems == maxSize); }
//-------------------------------------------------------------
}  // end class PriorityQ
////////////////////////////////////////////////////////////////

class PriorityQRevised extends PriorityQ {

    public PriorityQRevised(int s) {
        super(s);
    }

    @Override
    public void insert(long item) {
        if (isFull()) {
            System.out.println("is full");
            return;
        }

        this.queArray[nItems++] = item;
    }

    public long peekMax() {
        long maxItem = Long.MIN_VALUE;

        if (isEmpty()) {
            System.out.println("is empty");
            return maxItem;
        }

        for (int i = 0; i < nItems; i++) {
            if (queArray[i] > maxItem) {
                maxItem = queArray[i];
            }
        }

        return maxItem;
    }

    public long removeMax() {
        long maxItem = Long.MIN_VALUE;

        if (isEmpty()) {
            System.out.println("is empty");
            return maxItem;
        }

        int minItemIndex = -1;
        for (int i = 0; i < nItems; i++) {
            if (queArray[i] > maxItem) {
                maxItem = queArray[i];
                minItemIndex = i;
            }
        }

        for (; minItemIndex < nItems - 1; minItemIndex++) {
            queArray[minItemIndex] = queArray[minItemIndex + 1];
        }

        nItems--;

        return maxItem;
    }

    public void display() {
        System.out.println("=======");
        if (isEmpty()) {
            System.out.println("is empty");
        }

        for (int i = 0; i < nItems; i++) {
            System.out.println(queArray[i]);
        }
        System.out.println("=======");
    }
}

