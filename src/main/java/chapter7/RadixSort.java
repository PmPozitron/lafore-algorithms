package chapter7;

import java.util.Arrays;
import java.util.LinkedList;

public class RadixSort {

    private int[]values;

    private LinkedList<Integer>[]lists = new LinkedList[10];

    public RadixSort() {
        this.values = randomValues(10);
        for (int i=0; i<lists.length; i++) {
            lists[i] = new LinkedList<Integer>();
        }
    }

    public int[] getValues() {
        return values;
    }

    public LinkedList<Integer>[] getLists() {
        return lists;
    }

    private static int[] randomValues(int size) {
        int[]result = new int[size];
        result[0] = Integer.MAX_VALUE;

        for (int i=1; i<size-1; i++){
            if (i == size/2) {
                result[i] = Integer.MAX_VALUE;
            } else {
                result[i] = (int) (Math.random() * Integer.MAX_VALUE);
            }
        }
        result[size-1] = Integer.MAX_VALUE;

        return result;
    }

    public static void main(String[] args) {
        RadixSort sort  = new RadixSort();
        for (int i = 1; i > 0 && i < Integer.MAX_VALUE; i*=10) {
//        for (int i = 1; i < 214748364; i*=10) {
            for (int value : sort.getValues()) {
                int digit = value / i % 10;
                System.out.println(digit);
                sort.getLists()[digit].push(value);
            }
            int j = 0;
            for (LinkedList<Integer> list : sort.getLists()) {
                while (! list.isEmpty()) {
                    sort.getValues()[j] = list.pollLast();
                    j++;
                }
            }
            System.out.println(Arrays.toString(sort.getValues()));
        }

    }
}
