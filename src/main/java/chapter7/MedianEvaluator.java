package chapter7;

import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.Arrays;
import java.util.Random;

public class MedianEvaluator {

    int[] values;

    public MedianEvaluator() {
        values = randomValues(60);
        Arrays.toString(values);
    }

    public MedianEvaluator(int[] values) {
        this.values = values;
    }

    public int[] getValues() {
        return values;
    }

    public int partitionIt(int left, int right, double pivot) {
        int leftPtr = left - 1;           // right of first elem
        int rightPtr = right + 1;         // left of pivot
        while (true) {
            while (leftPtr < right && values[++leftPtr] < pivot) {// find bigger item
                ;  // (nop)
            }
            while (rightPtr > left && values[--rightPtr] > pivot) { // find smaller item
                ;  // (nop)
            }
            if (leftPtr >= rightPtr) {       // if pointers cross,
                break;
            }//    partition done
            else {                          // not crossed, so
                swap(leftPtr, rightPtr);    //    swap elements
            }
        }  // end while(true)
        return leftPtr;                   // return partition
    }  // end partitionIt()

    public int partitionIt3(int left, int right)    {
        int leftPtr = left - 1;
        int rightPtr = right;
        if(rightPtr - leftPtr <= 0)
        {
            System.out.println("Sub-array too small to sort");
            return -1;
        }
        long pivot = values[right];

        while(true) {
            while(leftPtr < right && values[++leftPtr] < pivot)
                ; //nop
            while(rightPtr > left && values[--rightPtr] > pivot)
                ; //nop
            if(leftPtr >= rightPtr) break;
            else swap(leftPtr, rightPtr);
        }
        swap(leftPtr, right); //move pivot to partition
        return leftPtr;
    }

    public void swap(int dex1, int dex2) { // swap two elements
        int temp;
        temp = values[dex1];             // A into temp
        values[dex1] = values[dex2];   // B into A
        values[dex2] = temp;             // temp into B
//        System.out.printf("after swap %d and %d: %s\n", dex1, dex2, Arrays.toString(values));

    }  // end swap()

    public double medianFromCommons() {
        Median median = new Median();
        double[]asDoubles = new double[values.length];
        for (int i = 0; i < values.length; i++ ) {
            asDoubles[i] = values[i];
        }
        median.setData(asDoubles);
        return median.evaluate();
    }

    public boolean isMiddle(int index) {
        return index == values.length / 2;
    }

    public int getMiddleIndex() {
        return values.length / 2;
    }

    public double median(int left, int right) {

        if (right - left < 1) {
            return values[values.length / 2];

        } else {
            int partition = partitionIt(left, right, values[right-1]);
            if (isMiddle(partition)) {
                return values[partition];

            } else if (partition < getMiddleIndex()) {
                return median(partition + 1, right);

            } else {
                return median(left, partition -1);
            }
        }
    }

    /**
     * Выбираем элемент, имеющий определённый (element) индекс при упорядочивании по размеру.
     * т.е., не конкретное значение, а, н-р, 3-ий по размеру элемент массива.
     * @param left  левый индекс
     * @param right правый индекс
     * @param seekIndex
     * @return
     */
    public int quickSelect(int left, int right, int seekIndex) {

        if (right - left <= 0) {
            return values[seekIndex];

        } else {
            int partition = partitionIt3(left, right);
            System.out.println("part="+partition);

            if (seekIndex == partition) {
                return values[partition];

            } else
            if (seekIndex > partition) {
                return quickSelect(partition + 1, right, seekIndex);

            } else {
                return quickSelect(left, partition-1, seekIndex);
            }
        }
    }

    public int quickSelect2(int left, int right, int seekIndex) {

        if (right - left <= 0) {
            return values[seekIndex];
        }

        int partition = partitionIt3(left, right);

        if (seekIndex == partition) {
            return values[partition];

        } else if (seekIndex > partition) {
            return quickSelect2(partition+1, right, seekIndex);

        } else {
            return quickSelect2(left, partition-1, seekIndex);
        }
    }

    public static void main(String[] args) {
        int indexToFind = 4;
        for (int i = 0; i < 1000; i++) {
            MedianEvaluator evaluator = new MedianEvaluator();
//        System.out.println(Arrays.toString(evaluator.getValues()));
            int found = evaluator.quickSelect2(0, evaluator.getValues().length-1, indexToFind);
//        int found = evaluator.findIndex(0, evaluator.getValues().length-1, 5);
            Arrays.sort(evaluator.getValues());
//        System.out.println(Arrays.toString(evaluator.getValues()));
            System.out.printf("found %d, expected %d, %b\n", found, evaluator.getValues()[indexToFind], found == evaluator.getValues()[indexToFind]);
        }

    }

    public static int[] predefinedValues() {
//        int[]result = new int[]{-157115963, 328908444, -1198962236, 663883518, -1932380074, 327466500, 321705107, -1700051241, 1725348878, 843313522};
        int[]result = new int[]{13, 2, 10, 44, 26, 13};

        return result;
    }

    public static int[] randomValues(int size) {
        int[]array = new int[size];
        Random rnd = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 50);
        }

        return array;
    }
// https://github.com/clesko/Solutions-to-Lafore-DataStructures-and-Algs-in-Java-Exercises/blob/master/src/ch7exercise4.java
    public int findIndex(int left, int right, int index)
    {
        if(right-left <= 0){
            return values[index];

        } else {
            int partition = partitionIt3(left, right);
            if(partition == index){
                return values[index];
            }
            else if(partition < index) {
                return findIndex(partition + 1, right, index);
            }
            else {
                return findIndex(left, partition-1, index);
            }
        }
    }

}
