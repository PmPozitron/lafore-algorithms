package interview;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class Sorting {

    public static void main(String[] args) {
        int size = 50;
        int[] toBeSorted = new int[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            toBeSorted[i] = Math.abs(rnd.nextInt());
        }
        int[] ethalon = Arrays.copyOf(toBeSorted, size);
        long startTime = System.nanoTime();
        Arrays.sort(ethalon);
        System.out.println(size + " items took " + (System.nanoTime() - startTime));

        startTime = System.nanoTime();
//        shellSort(toBeSorted);
//        mergeSort(toBeSorted);
        ForkJoinPool pool = new ForkJoinPool(12);
//        ForkJoinTask<?> task = new MergeSorterAction(toBeSorted, new int[toBeSorted.length], 0, toBeSorted.length - 1);
        ForkJoinTask<?> task = new QuickSortAction(toBeSorted, 0, toBeSorted.length - 1);
            pool.execute(task);
        task.join();
        System.out.println(size + " items took " + (System.nanoTime() - startTime));
        System.out.println("equals " + Arrays.equals(toBeSorted, ethalon));
//        for (int i = 0; i < size; i++) {
//            System.out.println(toBeSorted[i] + " " + ethalon[i]);
//        }
    }


    public static void insertionSort(int[] toBeSorted, int start, int end) {
        for (int i = start; i <= end; i++) {
            for (int j = i; j > 0 && toBeSorted[j] < toBeSorted[j - 1]; j--) {
                int temp = toBeSorted[j];
                toBeSorted[j] = toBeSorted[j - 1];
                toBeSorted[j - 1] = temp;
            }
        }
    }

    public static void insertionSort(int[] toBeSorted) {
        for (int i = 1; i < toBeSorted.length; i++) {
            for (int j = i; j > 0 && toBeSorted[j] < toBeSorted[j - 1]; j--) {
                int temp = toBeSorted[j];
                toBeSorted[j] = toBeSorted[j - 1];
                toBeSorted[j - 1] = temp;
            }
        }
    }

    public static void insertionSort(int[] toBeSorted, int h) {
        for (int i = h; i < toBeSorted.length; i++) {
            for (int j = i; j >= h && toBeSorted[j] < toBeSorted[j - h]; j -= h) {
                int temp = toBeSorted[j];
                toBeSorted[j] = toBeSorted[j - h];
                toBeSorted[j - h] = temp;
            }
        }
    }

    public static void shellSort(int[] toBeSorted) {
        int h = 1;
        while (h < toBeSorted.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            insertionSort(toBeSorted, h);
            h /= 3;
        }
    }

    public static void merge(int[] anArray, int[] temp, int start, int middle, int end) {
        int i = start, j = middle + 1;
        for (int k = start; k <= end; k++) {
            temp[k] = anArray[k];
        }

        for (int k = start; k <= end; k++) {
            if (i > middle) anArray[k] = temp[j++];
            else if (j > end) anArray[k] = temp[i++];
            else if (temp[j] < temp[i]) anArray[k] = temp[j++];
            else anArray[k] = temp[i++];
        }

    }

    public static void mergeSort(int[] toBeSorted) {
        mergeSort(toBeSorted, new int[toBeSorted.length], 0, toBeSorted.length - 1);
    }

    public static void mergeSort(int[] toBeSorted, int[] temp, int start, int end) {
        if (end <= start) {
            return;
        }

        int middle = start + (end - start) / 2;
        mergeSort(toBeSorted, temp, start, middle);
        mergeSort(toBeSorted, temp, middle + 1, end);
        merge(toBeSorted, temp, start, middle, end);
    }

    public static void mergeSort2(int[] toBeSorted, int[] temp, int start, int end) {

        int middle = start + (end - start) / 2;
        mergeSort(toBeSorted, temp, start, middle);
        mergeSort(toBeSorted, temp, middle + 1, end);
        merge(toBeSorted, temp, start, middle, end);
    }

    private static class MergeSorterAction extends RecursiveAction {
        int[] toBeSorted;
        int[] temp;
        int start;
        int end;

        public MergeSorterAction(int[] toBeSorted, int[] temp, int start, int end) {
            this.toBeSorted = toBeSorted;
            this.temp = temp;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
//            if (end <= start) {
//                return;
//            }

            if (end - start < 100) {
                Arrays.sort(toBeSorted, start, end);
//                insertionSort(toBeSorted, start, end);
                return;
            }
            int middle = start + (end - start) / 2;
            MergeSorterAction left = new MergeSorterAction(toBeSorted, temp, start, middle);
            MergeSorterAction right = new MergeSorterAction(toBeSorted, temp, middle + 1, end);
            invokeAll(left, right);
//            mergeSort2(toBeSorted, temp, start, middle);
//            mergeSort2(toBeSorted, temp, middle + 1, end);
            merge(toBeSorted, temp, start, middle, end);

        }
    }

    private static class QuickSortAction extends RecursiveAction {
        int[]toBeSorted;
        int left;
        int right;


        public QuickSortAction(int[] toBeSorted, int left, int right) {
            this.toBeSorted = toBeSorted;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            int size = right - left + 1;
            if(size < 10)
                insertionSort(toBeSorted, left, right);
            else
            {
                long median = medianOf3(left, right);
                int partition = partitionIt(left, right, median);
                QuickSortAction first = new QuickSortAction(toBeSorted, left, partition - 1);
                QuickSortAction second = new QuickSortAction(toBeSorted, partition, right);
                invokeAll(first, second);
            }
        }

        public long medianOf3(int left, int right)
        {
            int center = (left+right)/2;

            if( toBeSorted[left] > toBeSorted[center] )
                swap(left, center);

            if( toBeSorted[left] > toBeSorted[right] )
                swap(left, right);

            if( toBeSorted[center] > toBeSorted[right] )
                swap(center, right);

            swap(center, right-1);
            return toBeSorted[right-1];
        }

        public void swap(int dex1, int dex2)
        {
            int temp = toBeSorted[dex1];
            toBeSorted[dex1] = toBeSorted[dex2];
            toBeSorted[dex2] = temp;
        }

        public int partitionIt(int left, int right, long pivot)
        {
            int leftPtr = left;
            int rightPtr = right - 1;
            while(true)
            {
                while( toBeSorted[++leftPtr] < pivot )
                    ;
                while( toBeSorted[--rightPtr] > pivot )
                    ;
                if(leftPtr >= rightPtr)
                    break;
                else
                    swap(leftPtr, rightPtr);
            }
            swap(leftPtr, right-1);
            return leftPtr;
        }
    }
}
