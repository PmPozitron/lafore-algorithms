package chapter7;

import org.apache.commons.math3.stat.descriptive.rank.Median;

// partition.java
// demonstrates partitioning an array
// to run this program: C>java PartitionApp
////////////////////////////////////////////////////////////////
class ArrayPar {
    private long[] theArray;          // ref to array theArray
    private int nElems;               // number of data items
    private int middleCell;

    //--------------------------------------------------------------
    public ArrayPar(int max)          // constructor
    {
        theArray = new long[max];      // create the array
        nElems = 0;                    // no items yet
        middleCell = max/2;
    }

    //--------------------------------------------------------------
    public void insert(long value)    // put element into array
    {
        theArray[nElems] = value;      // insert it
        nElems++;                      // increment size
    }

    //--------------------------------------------------------------
    public int size()                 // return number of items
    {
        return nElems;
    }

    //--------------------------------------------------------------
    public void display()             // displays array contents
    {
        System.out.print("A=");
        for (int j = 0; j < nElems; j++)    // for each element,
            System.out.print(theArray[j] + " ");  // display it
        System.out.println("");
    }

    //--------------------------------------------------------------
    public int partitionIt(int left, int right, long pivot) {
        int leftPtr = left - 1;           // right of first elem
        int rightPtr = right + 1;         // left of pivot
        while (true) {
            while (leftPtr < right && theArray[++leftPtr] < pivot) {// find bigger item
                ;  // (nop)
            }
            while (rightPtr > left && theArray[--rightPtr] > pivot) { // find smaller item
                ;  // (nop)
            }
            if (leftPtr >= rightPtr) {       // if pointers cross,
                break;
            }//    partition done
            else {                          // not crossed, so
                swap(leftPtr, rightPtr);    //    swap elements
                System.out.print("after swap ");
                display();
            }
        }  // end while(true)
        return leftPtr;                   // return partition
    }  // end partitionIt()

    public int partitionIt(int left, int right) {
        if (left == right) {
            return right;
        }
        if (left + 1 == right) {
            if (theArray[left] > theArray[right]) {
                swap(left, right);
            }
            return right;
        }
        if (left + 2 == right) {
            if (theArray[left] > theArray[left + 1]) {
                swap(left, left + 1);
            }
            if (theArray[left] > theArray[right]) {
                swap(left, right);
            }
            if (theArray[left + 1] > theArray[right]) {
                swap(left + 1, right);
            }
            return left + 1;
        }

        return partitionIt(left, right, theArray[right]);
    }

    //--------------------------------------------------------------
    public void swap(int dex1, int dex2)  // swap two elements
    {
        long temp;
        temp = theArray[dex1];             // A into temp
        theArray[dex1] = theArray[dex2];   // B into A
        theArray[dex2] = temp;             // temp into B
    }  // end swap()

    public long get(int index) {
        return theArray[index];
    }

    public long quickMedian(int left, int right) {
        if (right - left <=0) {
            return theArray[theArray.length/2];
        }

        int partition = partitionIt(left, right, theArray[right]);

        if (theArray.length % 2 == 1 && partition == theArray.length / 2) {
            return theArray[partition];

        } else if (theArray.length % 2 == 0 && partition == theArray.length / 2) {
            System.out.printf("median is %f\n", (theArray[partition - 2] + theArray[partition-1]) / 2.);
            return theArray[partition + 1];

        } else if (theArray.length % 2 == 0 && partition == theArray.length / 2 + 1) {
            System.out.printf("median is %f\n", (theArray[partition - 2] + theArray[partition-1]) / 2.);
            return theArray[partition + 1];

        } else if (partition < (left + right) / 2) {
            return quickMedian(partition+1, right);

        } else {
            return quickMedian(left, partition - 1);
        }
    }

    public int partitionIt3(int left, int right)
    {
        int leftPtr = left - 1;
        int rightPtr = right;
        if(rightPtr - leftPtr <= 0)
        {
            System.out.println("Sub-array too small to sort");
            return -1;
        }
        long pivot = theArray[right];

        while(true)
        {
            while(leftPtr < right && theArray[++leftPtr] < pivot)
                ; //nop
            while(rightPtr > left && theArray[--rightPtr] > pivot)
                ; //nop
            if(leftPtr >= rightPtr) break;
            else swap(leftPtr, rightPtr);
        }
        swap(leftPtr, right); //move pivot to partition
        return leftPtr;
    }

    public double medianFromCommons (ArrayPar values) {
        double[] asDoubles = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            asDoubles[i] = (double) values.get(i);
        }
        Median evaluator = new Median();
        evaluator.setData(asDoubles);
        return evaluator.evaluate();
    }

//--------------------------------------------------------------
}  // end class ArrayPar

////////////////////////////////////////////////////////////////
public class PartitionApp {
    public static void main(String[] args) {
        int maxSize = 7;             // array size
        ArrayPar arr;                 // reference to array
        arr = new ArrayPar(maxSize);  // create the array

        for(int j=0; j<maxSize; j++) { // fill array with  random numbers
            long n = (int)(java.lang.Math.random()*9);
            arr.insert(n);
        }
//        arr.insert(2);
//        arr.insert(3);
//        arr.insert(8);
//        arr.insert(5);
//        arr.insert(6);
//        arr.insert(8);

        arr.display();                // display unsorted array

//        long pivot = 1;              // pivot value
//        System.out.print("Pivot is " + pivot);
//        System.out.print("Pivot is " + arr.get(arr.size()-1));
//        int size = arr.size();
        // partition array
//        int partDex = arr.partitionIt(0, size-1, pivot);
//        int partDex = arr.partitionIt(0, size-1);

//        System.out.println(", Partition is at index " + partDex);
        double fromCommons = arr.medianFromCommons(arr);
        System.out.println("from commons " + fromCommons);
        long median = arr.quickMedian(0, arr.size() - 1);
        System.out.println(median);
        arr.display();                // display partitioned array
    }  // end main()
}  // end class PartitionApp
////////////////////////////////////////////////////////////////
