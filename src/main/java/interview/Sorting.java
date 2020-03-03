package interview;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class Sorting {

    public static void main(String[] args) {
        int size = 100;
        int[] toBeSorted = new int[size];
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            toBeSorted[i] = Math.abs(rnd.nextInt());
        }

//        shellSort(toBeSorted);
//        mergeSort(toBeSorted, 0, toBeSorted.length -1);
        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<?> task = new MergeSorterAction(toBeSorted, 0, toBeSorted.length);
        pool.execute(task);
        System.out.println("");
    }


    public static void insertionSort(int[] toBeSorted) {
        for (int i = 1; i < toBeSorted.length; i++) {
            for (int j = i; j > 0 && toBeSorted[j] < toBeSorted[j-1]; j--) {
                int temp = toBeSorted[j];
                toBeSorted[j] = toBeSorted[j-1];
                toBeSorted[j - 1] = temp;
            }
        }
    }
    public static void insertionSort(int[] toBeSorted, int h) {
        for (int i = h; i < toBeSorted.length; i++) {
            for (int j = i; j >= h && toBeSorted[j] < toBeSorted[j-h]; j-=h) {
                int temp = toBeSorted[j];
                toBeSorted[j] = toBeSorted[j-h];
                toBeSorted[j - h] = temp;
            }
        }
    }
    public static void shellSort(int[] toBeSorted) {
        int h = 1;
        while (h < toBeSorted.length/3) {
            h = h*3 +1;
        }
        while (h > 0) {
            insertionSort(toBeSorted, h);
            h /= 3;
        }
    }
    public static void merge(int[] anArray, int[]temp, int start, int middle, int end){
        int i = start, j = middle + 1;
        for (int k = start; k <= end; k++) {
            temp[k] = anArray[k];
        }

        for (int k = start; k <= end; k++ ){
            if (i > middle) anArray[k] = temp[j++];
            else if (j > end) anArray[k] = temp[i++];
            else if (temp[j] < temp[i]) anArray[k] = temp[j++];
            else  anArray[k] = temp[i++];
        }

    }
    public static void mergeSort(int[]toBeSorted, int start, int end) {
        if (end <= start) {
            return;
        }

        int middle = start + (end - start) / 2;
        mergeSort(toBeSorted, start, middle);
        mergeSort(toBeSorted, middle + 1, end);
        merge(toBeSorted, new int[toBeSorted.length], start, middle, end);
    }

    private static class MergeSorterAction extends RecursiveAction {
        int[]toBeSorted;
        int start;
        int end;

        public MergeSorterAction(int[] toBeSorted, int start, int end) {
            this.toBeSorted = toBeSorted;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            int middle = start + (end - start) / 2;
            MergeSorterAction left = new MergeSorterAction(toBeSorted, start, middle);
            MergeSorterAction right = new MergeSorterAction(toBeSorted, middle+1, end);
            invokeAll(left, right);
            left.join();
            right.join();
            mergeSort(toBeSorted, start, end);

        }
    }
}
