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
        for (int i=0; i<size; i++){
            result[i] = (int)(Math.random() * 1000);
        }

        return result;
    }

    public static void main(String[] args) {
        RadixSort sort  = new RadixSort();
        for (int i = 1; i < 1000; i*=10) {
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
