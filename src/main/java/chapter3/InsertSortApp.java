package chapter3;

import java.util.Random;

public class InsertSortApp {
    public static void main(String[] args) {

        int maxSize = 2000;            // array size
        ArrayIns arr;                 // reference to array
        arr = new ArrayIns(maxSize);  // create the array

        for (int i = 0; i < maxSize; i++) {
            long value = new Random().nextLong();
//                long value = Long.MAX_VALUE - i;
//                long value = i;
            arr.insert(value);
        }

        arr.insertionSort();

    }  // end main()
}

class ArrayIns {
    private long[] a;                 // ref to array a
    private int nElems;               // number of data items

    //--------------------------------------------------------------
    public ArrayIns(int max)          // constructor
    {
        a = new long[max];                 // create the array
        nElems = 0;                        // no items yet
    }

    //--------------------------------------------------------------
    public void insert(long value)    // put element into array
    {
        a[nElems] = value;             // insert it
        nElems++;                      // increment size
    }

    //--------------------------------------------------------------
    public void display()             // displays array contents
    {
        for (int j = 0; j < nElems; j++)       // for each element,
            System.out.print(a[j] + " ");  // display it
        System.out.println("");
    }

    //--------------------------------------------------------------
    public void insertionSort() {
        long start = System.nanoTime();
        int in, out;
        for (out = 1; out < nElems; out++)     // out is dividing line
        {
            long temp = a[out];            // remove marked item
            in = out;                      // start shifts at out
            while (in > 0 && a[in - 1] >= temp) // until one is smaller,
            {
                a[in] = a[in - 1];            // shift item to right
                --in;                       // go left one position
            }
            a[in] = temp;                  // insert marked item
        }  // end for
        long end = System.nanoTime();
        System.out.println("Insertion sort " + (end - start));
    }  //

    public void killDuplicates() {
        int dupsCounter = 0;
        for (int i = 0; i < nElems; i++) {
            if (a[i] != a[i+1] && a[i+1] != -1) {
                continue;
            }

            a[i+1] = -1;
            int j = i + 2;
            while ((j < nElems - 2 || a[i] != 0) &&
                    (a[j] == -1 || a[j] == a[i])) {
                a[j] = -1;
                j++;
            }
            a[i+1] = a[j];
            a[j] = -1;
            dupsCounter++;
            if (j == nElems - 1) {
                System.out.println(dupsCounter);
                nElems -= dupsCounter;
                return;
            }
        }
    }
//--------------------------------------------------------------
}  // end class ArrayIns
