package interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Strings {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String first = scanner.next();
//        String second = scanner.next();
//        System.out.println(areStringsAnagrams(first, second));
//        for (int i = 0; i < 10_000; i++) {
//            System.out.printf("%s %d\n", Integer.toBinaryString(i), getLongestOnesSubstringLength_2(i));
//        }
//        System.out.println(getLongestOnesSubstringLength_2(scanner.nextInt()));


//        int counter = scanner.nextInt();
//        char[] array = new char[counter];

//        int[]array = new int[counter];
//        ArrayList<Integer> list = new ArrayList<>(counter);
//        int[] array = new int[counter];
//        for (int i = 0; i < counter; i++) {
//            array[i] = scanner.nextInt();
//        }

//        System.out.println(getLongestOnesSubstringLength_3(new String(array)));
//        for (int value : killDuplicates_3(array)) {
//            if (value == 1_000_001) {
//                continue;
//            }
//            System.out.println(value);
//        }

//        int rowsNumber = scanner.nextInt();
//        int columnsNumber = 0;
//        int[][] values = new int[rowsNumber][];
//        for (int i = 0; i < rowsNumber; i++) {
//            columnsNumber = scanner.nextInt();
//            values[i] = new int[columnsNumber];
//            for (int j = 0; j < columnsNumber; j++) {
//                values[i][j] = scanner.nextInt();
//            }
//        }
//
//        for (int value : mergeArrays(values)){
//            if (value != 0) System.out.print(value + " ");
//        }

//        System.out.println(Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
        ListNode first = new ListNode(9);
//        first.next = new ListNode(4);
//        first.next.next = new ListNode(3);

        ListNode second = new ListNode(1);
        second.next = new ListNode(9);
        second.next.next = new ListNode(9);
        second.next.next.next = new ListNode(9);
        second.next.next.next.next = new ListNode(9);
        second.next.next.next.next.next = new ListNode(9);
        second.next.next.next.next.next.next = new ListNode(9);
        second.next.next.next.next.next.next.next = new ListNode(9);
        second.next.next.next.next.next.next.next.next = new ListNode(9);
        second.next.next.next.next.next.next.next.next.next = new ListNode(9);

//        ListNode result = addTwoNumbers(first, second);

//        ArrayList<char[]> rawResult = zigzagConversion("PAYPALISHIRING", 3);
//        prettyPrintZigzag(rawResult);
//        System.out.println(notSoPrettyPrintZigzag(rawResult));
//        zigzagConversion("", 4);
//        System.out.println(convert("PAYPALISHIRING", 4));

        brackets(3);
    }

    public static int areStringsAnagrams(String first, String second) {
        char[] firstAsArray = first.toCharArray();
        Arrays.sort(firstAsArray);

        char[] secondAsArray = second.toCharArray();
        Arrays.sort(secondAsArray);

        return new String(firstAsArray).equals(new String(secondAsArray)) ? 1 : 0;
    }

    public static int getLongestOnesSubstringLength(int input) {
        String binary = Integer.toBinaryString(input);
        int max = 0;
        for (int i = 0; i < binary.length() - 1; i++) {
            if (binary.charAt(i) == '1') {
                int length = 1;
                for (int j = i + 1; j < binary.length(); j++) {
                    if (binary.charAt(j) == '1') {
                        length++;
                    } else {
                        break;
                    }
                }
                max = max < length ? length : max;
            }
        }
        if (max == 0 && binary.charAt(binary.length() - 1) == '1') {
            max = 1;
        }
        return max;
    }

    public static int getLongestOnesSubstringLength_2(int input) {
        String binary = Integer.toBinaryString(input);
        String[] split = binary.split("0+");
        int max = 0;
        for (String str : split) {
            System.out.println(str);
            max = max < str.length() ? str.length() : max;
        }
        return max;
    }

    public static int getLongestOnesSubstringLength_3(String binary) {
//        String binary = Integer.toBinaryString(input);
        String[] split = binary.split("0+");
        int max = 0;
        for (String str : split) {
            System.out.println(str);
            max = max < str.length() ? str.length() : max;
        }
        return max;
    }

    public static List<Integer> killDuplicates(List<Integer> raw) {
        HashSet<Integer> filtered = new HashSet<>(raw);
        List<Integer> result = new ArrayList<>(filtered);
        Collections.sort(result);
        return result;
    }

    public static List<Integer> killDuplicates_2(List<Integer> raw) {
        List<Integer> result = new ArrayList<>(raw.size());
        for (Integer value : raw) {
            if (result.contains(value)) {
                continue;
            }
            result.add(value);
        }

        return result;
    }

    public static int[] killDuplicates_3(int[] raw) {
        if (raw.length == 1) {
            return raw;
        }
        for (int i = 0; i < raw.length - 1; i++) {
            int current = raw[i];
            for (int j = i + 1; j <= raw.length - 1; j++) {
                if (current == raw[j]) {
                    raw[j] = 1_000_001;
                } else {
                    break;
                }
            }
        }

        return raw;
    }

    public static int[] mergeArrays(int[][] toBeMerged) {
        int[] result = new int[toBeMerged.length * 10];
        int k = 0;
        for (int i = 0; i < toBeMerged.length; i++) {
            for (int j = 0; j < toBeMerged[i].length; j++) {
                result[k] = toBeMerged[i][j];
                k++;
            }
        }
        Arrays.sort(result);
        return result;
    }

    public static int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            int current = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (current + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long first = 0;
        long second = 0;
        long result = 0;

        int counter = 0;
        ListNode current = l1;

        while (current != null) {
            first += current.val * Math.pow(10, counter);
            counter++;
            current = current.next;
        }

        counter = 0;
        current = l2;
        while (current != null) {
            second += current.val * Math.pow(10, counter);
            counter++;
            current = current.next;
        }

        result = first + second;

        String resultAsString = String.valueOf(result);
        ListNode realResult = new ListNode(resultAsString.charAt(resultAsString.length()-1) - '0');
        current = realResult;
        for (int i = resultAsString.length() - 2; i >= 0; i-- ) {
            current.next = new ListNode(resultAsString.charAt(i) - '0');
            current = current.next;

        }

        return realResult;

    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static ArrayList<char[]> zigzagConversion(String toBeConverted, int numRows) {
        ArrayList<char[]> rawResult = new ArrayList<>();
        int current = 0;
        int j = numRows - 2;
        int size = 0;

        while (true) {
            if (toBeConverted.length() - current <= numRows) {
                char[] chars = new char[toBeConverted.length() - current];
//                char[] chars = new char[numRows];
//                Arrays.fill(chars, ' ');
                for (int i = 0; i <= toBeConverted.length() - current - 1; i++) {
                    chars[i] = toBeConverted.charAt(current + i);
                }
                rawResult.add(chars);
                break;
            } else {
                // создаём "простой", т.е. не зигзаговый м-в для num символов
                char[] chars = new char[numRows];
                for (int i = 0; i < numRows; i++) {
                    chars[i] = toBeConverted.charAt(current + i);
                }
                rawResult.add(chars);
                current += numRows;
            }

            // проверяем корнеркейс, что после создания м-ва из первых num символов,
            // количество оставшихся символов достаточно для того, что бы сделать num-2 массивов (которые и вносят зигзаговость)
            // если недостаточно, делаем столько зигзаговых массивов, сколько можем и выходим из while
            if (toBeConverted.length() - current <= j) {
                for (int i = 0; i <= toBeConverted.length() - current - 1; i++) {
                    char[] chars = new char[toBeConverted.length() - current];
//                    char[] chars = new char[numRows];
//                    Arrays.fill(chars, ' ');
                    chars[j - i] = toBeConverted.charAt(current + i);
                    rawResult.add(chars);
                }
                break;
                // просто создаём зигзаговые м-вы
            } else {
                for (int i = 0; i < j; i++) {
                    char[] chars = new char[numRows];
//                    Arrays.fill(chars, ' ');
                    chars[j - i] = toBeConverted.charAt(current + i);
                    rawResult.add(chars);
                }
                current+=j;
            }
        }

        return rawResult;

    }

    public static String notSoPrettyPrintZigzag(List<char[]> rawResult) {
        StringBuilder result = new StringBuilder(rawResult.size() * rawResult.get(0).length);
        int i = 0;
        while (true) {
            if (rawResult.get(0).length <= i) {
                break;
            }
            for (int j = 0; j < rawResult.size(); j++) {
                if (rawResult.get(j).length <= i) {
                    continue;
                }
                char ch = rawResult.get(j)[i];
                if (ch != ' ' && ch != 0) {
                    result.append(ch);
                }
            }
            System.out.println();
            i++;
        }
        return result.toString();
    }

    public static void prettyPrintZigzag(List<char[]> rawResult) {
        int i = 0;
        while (true) {
            if (rawResult.get(0).length <= i) {
                break;
            }
            for (int j = 0; j < rawResult.size(); j++) {
                if (rawResult.get(j).length <= i) {
                    continue;
                }
                System.out.print(rawResult.get(j)[i]);
            }
            System.out.println();
            i++;
        }
    }

    // типа, правильный ответ
    public static String convert(String s, int numRows) {

        if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0 || curRow == numRows - 1) goingDown = !goingDown;
            curRow += goingDown ? 1 : -1;
        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();
    }

    public static void brackets(int size) {
        char[] brackets = new char[size*2];
        Arrays.fill(brackets, 0, size, '(');
        Arrays.fill(brackets, size, size*2, ')');

        Set<String> result = new HashSet<>();
        createSequences(brackets, brackets.length, result);
        for (String seq : result) {
            System.out.println(seq);
        }
    }

    public static void createSequences(char[] chars, int newSize, Set<String> result) {
        if (newSize == 1) return;

        for (int i = 0; i<newSize; i++) {
            createSequences(chars, newSize-1, result);
            if (newSize == 2) {
                if (checkCorrectness(chars)) result.add(new String(chars));
            }
            rotate(newSize, chars);
        }

    }

    public static void rotate (int start, char[] chars) {
        start = chars.length - start;
        char temp = chars[start];
        for (int i = start; i < chars.length-1; i++) {
            chars[i] = chars[i+1];
        }
        chars[chars.length-1] = temp;
//        System.out.println(new String(chars));
    }

    public static boolean checkCorrectness(char[] chars) {
        int counter = 0;
        for (int i = 0; i< chars.length; i++) {
            if (chars[i] == '(') counter++;
            else counter--;

            if (counter<0) return false;
        }

        return counter == 0;
    }
}
