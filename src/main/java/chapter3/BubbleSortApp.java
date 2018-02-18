package chapter3;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BubbleSortApp {
    public static void main(String[] args) {
        int maxSize = 20;            // array size
        ArrayBub arr;                 // reference to array

        arr = new ArrayBub(maxSize);  // create the array
        for (int i = 0; i < maxSize; i++) {
            long value = new Random().nextLong();
//                long value = Long.MAX_VALUE - i;
//                long value = i;
            arr.insert(value);
        }
        arr.oddEvenSort();
//        arr.display();
//        System.out.println("===");

        arr = new ArrayBub(maxSize);  // create the array
        for (int i = 0; i < maxSize; i++) {
            long value = new Random().nextLong();
//                long value = Long.MAX_VALUE - i;
//                long value = i;
            arr.insert(value);
        }
        arr.multiThreadedOddEvenSort();
        arr.display();
        // display them again
    }  // end main()
}  // end class BubbleSortApp

class ArrayBub {
    private long[] a;                 // ref to array a
    private int nElems;               // number of data items

    //--------------------------------------------------------------
    public ArrayBub(int max)          // constructor
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
            System.out.println(a[j] + " ");  // display it
    }

    //--------------------------------------------------------------
    public void bubbleSort() {
        int out, in;
        int comparesCounter = 0;
        int swapsCounter = 0;

        for (out = nElems - 1; out > 1; out--)   // outer loop (backward)
            for (in = 0; in < out; in++) {     // inner loop (forward)
                comparesCounter++;
                if (a[in] > a[in + 1]) {    // out of order?
                    swapsCounter++;
                    swap(in, in + 1);          // swap them
                }
            }
        System.out.printf("compares %d, swaps %d; more compares than swaps %b\n", comparesCounter, swapsCounter, comparesCounter > swapsCounter);
    }  // end bubbleSort()

    public void bubbleSortBiDirectional() {
        int outRightToLeft, outLeftToRight, in;

        for (outRightToLeft = nElems - 1, outLeftToRight = 0;
             outRightToLeft > 1 && outLeftToRight < outRightToLeft;
             outRightToLeft--, outLeftToRight++) {   // outer loop (backward)
            for (in = 0; in < outRightToLeft; in++) {        // inner loop (forward)
                if (a[in] > a[in + 1]) {       // out of order?
                    swap(in, in + 1);          // swap them
                }
            }

            for (in = outRightToLeft; in > outLeftToRight; in--) {
                if (a[in] < a[in - 1]) {
                    swap(in, in - 1);
                }
            }
        }
    }

    public double median() {
        bubbleSortBiDirectional();

        if ((nElems % 2) == 1) {
            return a[nElems / 2];

        } else {
            return 0.5 * (a[(nElems - 1) / 2 + 1] + a[(nElems - 1) / 2]);
        }
    }

    public void oddEvenSort() {
        long start = System.nanoTime();
        boolean oddFlag = true;
        boolean evenFlag = true;

        while (oddFlag || evenFlag) {
            int cnt = 0;

            for (int i = 1; i < nElems - 1; i += 2) {
                if (a[i] > a[i + 1]) {
                    swap(i, i + 1);
                    cnt++;
                }
            }

            if (cnt == 0) {
                oddFlag = false;
            }
            cnt = 0;

            for (int i = 0; i < nElems - 1; i += 2) {
                if (a[i] > a[i + 1]) {
                    swap(i, i + 1);
                    cnt++;
                }
            }
            if (cnt == 0) {
                evenFlag = false;
            }
        }
        long end = System.nanoTime();
        System.out.println("ST Duration is " + (end - start));
    }

    public void multiThreadedOddEvenSort() {
        long start = System.nanoTime();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        final AtomicBoolean oddFlag = new AtomicBoolean(true);
        final AtomicBoolean evenFlag = new AtomicBoolean(true);

        class Runner implements Runnable {
            private AtomicInteger index = new AtomicInteger(0);

            public Runnable setIndex(final int index) {
                this.index.set(index);
                return this;
            }
            public void run() {
                if (a[this.index.intValue()] > a[this.index.intValue() + 1]) {
                    swap(this.index.intValue(), this.index.intValue() + 1);
                    oddFlag.compareAndSet(false, true);
                }
            }
        }

        Runner oddRunner = new Runner();
        Runner evenRunner = new Runner();

        Runner[] runners = new Runner[Runtime.getRuntime().availableProcessors() + 1];
        for (int i = 0; i < runners.length; i++) {
            runners[i] = new Runner();
        }
        while (oddFlag.get() || evenFlag.get()) {
            oddFlag.set(false);
            evenFlag.set(false);

            for (int i = 1, j = 0; i < nElems - 1; i += 2, j++) {
                final int index = i;
                executorService.submit(new Runnable() {
                    public void run() {
                        if (a[index] > a[index + 1]) {
                            swap(index, index + 1);
                            oddFlag.compareAndSet(false, true);
                        }
                    }
                });
//                executorService.submit(runners[j].setIndex(i));
            }

            for (int i = 0, j = 0; i < nElems - 1; i += 2, j++) {
                final int index = i;
                executorService.submit(new Runnable() {
                    public void run() {
                        if (a[index] > a[index + 1]) {
                            swap(index, index + 1);
                            evenFlag.compareAndSet(false, true);

                        }
                    }
                });
//                executorService.submit(runners[j].setIndex(i));
            }
        }
        executorService.shutdown();
        long end = System.nanoTime();
        System.out.println("MT Duration is " + (end - start));
    }

    //--------------------------------------------------------------
    private void swap(int one, int two) {
        long temp = a[one];
        a[one] = a[two];
        a[two] = temp;
    }

    protected double[] getArrayAsDoubles() {
        double[] result = new double[nElems];

        for (int i = 0; i < nElems; i++) {
            result[i] = a[i];
        }
        return result;
    }

    protected int getnElems() {
        return nElems;
    }
//--------------------------------------------------------------
}  // end class ArrayBub
////////////////////////////////////////////////////////////////
