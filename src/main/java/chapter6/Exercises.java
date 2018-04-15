package chapter6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Exercises {
    public static void main(String[] args) {
        Exercises exercises = new Exercises();
        int[] items = new int[]{11, 8, 7, 6, 5};
//        int[]items = new int[]{1,3,5};
        int capacity = 13;
//        exercises.knapsack_64(items, 0, 0, capacity, new int[items.length], 0);
//        exercises.knapsack_64_2(items, 0, capacity, new int[items.length]);

        exercises.showTeams(new LinkedList<Character>(Arrays.asList('A','B','C','D')), 2, new LinkedList<Character>(), new ArrayList<List<Character>>());
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
        char[] line = new char[currentLength];
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
        makeBranches_62(initialLength, currentLength / 2, result);
    }

    private void displayBranches() {
        ArrayList<Character> result = new ArrayList<Character>();
        int initialLength = 16;
        makeBranches_62(initialLength, initialLength, result);

        for (int i = 1; i <= result.size(); i++) {
            System.out.print(result.get(i - 1));
            if (i % initialLength == 0) {
                System.out.println();
            }
        }
    }

    private int[] knapsack_64(int[] items, int innerIndex, int outerIndex, int capacity, int[] placedItems, int placedCapacity) {
        for (int i = innerIndex; i < items.length; i++) {
            placedItems[i] = items[i];
            placedCapacity += items[i];
            System.out.printf("placed items are %s, their capacity is %d, innerIndex is %d, outerIndex is %d, i is %s\n", Arrays.toString(placedItems), placedCapacity, innerIndex, outerIndex, i);

            if (placedCapacity == capacity) {
                System.out.println("found " + Arrays.toString(placedItems));
                return placedItems;

            } else if (placedCapacity < capacity) {
                knapsack_64(items, ++innerIndex, outerIndex, capacity, placedItems, placedCapacity);

            } else if (placedCapacity > capacity) {
                knapsack_64(items, ++outerIndex, outerIndex, capacity, new int[items.length], 0);
            }
        }

        System.out.println("not found");
        return null;
    }

    private void knapsack_64_2(int[] items, int index, int capacity, int[] placed) {
        for (int i = index; i < items.length; i++) {
            placed[i] = items[i];
            if (items[i] == capacity) {
                System.out.println(items[i]);

            } else if (items[i] < capacity) {
                knapsack_64_2(items, i + 1, capacity - items[i], placed);

            } else if (items[i] > capacity) {
                placed[i] = 0;
                knapsack_64_2(items, i + 1, capacity, placed);
            }
        }
    }

    private void showTeams(LinkedList<Character> players, int k, LinkedList<Character> placed, List<List<Character>> result) {
        if (k == 1) {
            for (Character character : players) {
                List<Character> localResult = new ArrayList<Character>(placed);
                localResult.add(character);
                System.out.println(localResult.toString());
//                result.add(localResult);
            }
        } else {
//            for (int i = 0; i < players.size(); i++) {
            for (Iterator<Character> iterator = players.iterator(); iterator.hasNext();) {
                LinkedList<Character> localPlaced = new LinkedList<Character>(placed);
                localPlaced.add(iterator.next());
                iterator.remove();
                LinkedList<Character> localPlayers = new LinkedList<Character>(players);
                showTeams(localPlayers, k - 1, localPlaced, result);
            }
        }
    }
}
