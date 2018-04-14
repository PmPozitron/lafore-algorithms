package chapter6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercises {
    public static void main(String[] args) {
        Exercises exercises = new Exercises();
        exercises.knapsack_64(new int[]{1,3,5}, 0, 8, new int[3], 0);
    }

    private long recursiveMultiplication_61(int x, int y) {
        if (x == 0 || y == 0) {
            return 0;
        }

        int counter = y;

        if (counter < 0) {
            if (counter == -1) {
                return -x;

            } else {
                return -(x - recursiveMultiplication_61(x, ++counter));
            }

        } else {
            if (counter == 1) {
                return x;

            } else {
                return x + recursiveMultiplication_61(x, --counter);
            }
        }
    }

    private double recursiveRaisingToPower_63(int x, int y) {
        if (x == 0) {
            return 0.;
        }

        if (y == 0) {
            return 1.;
        }

        if (x == 1) {
            return 1.;
        }

        if (y == 1) {
            return x;
        }

        if (y == -1) {
            return 1. / x;
        }

        int counter = y;

        if (counter < 0) {
            return recursiveRaisingToPower_63(x, ++counter);
        }

        if (counter > 0) {
            return x * recursiveRaisingToPower_63(x, --counter);
        }

        return 1;
    }

    private void makeBranches_62(int initialLength, int currentLength, List<Character> result) {
        char[]line = new char[currentLength];
        Arrays.fill(line, '-');
        line[line.length / 2] = 'X';


        for (int i = 0; i < initialLength / currentLength; i++) {
            for (char ch : line) {
                result.add(ch);
            }
        }
        if (currentLength == 1) {
            return;
        }
        makeBranches_62(initialLength, currentLength/2, result);
    }

    private void displayBranches() {
        ArrayList<Character> result = new ArrayList<Character>();
        int initialLength = 16;
        makeBranches_62(initialLength, initialLength, result);

        for (int i = 1; i <= result.size(); i++) {
            System.out.print(result.get(i-1));
            if (i % initialLength == 0) {
                System.out.println();
            }
        }
    }

    private int[] knapsack_64(int[]items, int index, int capacity, int[]placedItems, int placedCapacity) {
        for (int i = index; i < items.length; i++) {
            placedItems[i] = items[i];
            placedCapacity += items[i];
            System.out.printf("placed items are %s, their capacity is %d, index is %d, i is %s\n", Arrays.toString(placedItems), placedCapacity, index, i);

            if (placedCapacity == capacity) {
                System.out.println(Arrays.toString(placedItems));
                return placedItems;

            } else if (placedCapacity < capacity) {
                knapsack_64(items, ++i, capacity, placedItems, placedCapacity);

            } else if (placedCapacity > capacity) {
                knapsack_64(items, ++index, capacity, new int[items.length], 0);
            }
        }

        System.out.println("not found");
        return null;
    }
}
