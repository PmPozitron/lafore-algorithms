package interview;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Brackets {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        brackets(Integer.parseInt(scanner.next()));

        Scanner sc = new Scanner(System.in);
        int kol=sc.nextInt();
        Set<String> result = new TreeSet<>();
        kas(kol, 0, "", "", result);
        for (String seq : result) System.out.println(seq);
    }

    public static void brackets(int size) {
        char[] brackets = new char[size * 2];
        Arrays.fill(brackets, 0, size, '(');
        Arrays.fill(brackets, size, size * 2, ')');

        Set<String> result = new TreeSet<>();
        createSequences(brackets, brackets.length, result);
        for (String seq : result) {
            System.out.println(seq);
        }
    }

    public static void createSequences(char[] chars, int newSize, Set<String> result) {
        if (newSize <= 1) return;

        for (int i = 0; i < newSize; i++) {
            createSequences(chars, newSize - 1, result);
            if (newSize == 2) {
                if (checkCorrectness(chars)) result.add(new String(chars));
            }
            rotate(newSize, chars);
        }

    }

    public static void rotate(int start, char[] chars) {
        start = chars.length - start;
        char temp = chars[start];
        for (int i = start; i < chars.length - 1; i++) {
            chars[i] = chars[i + 1];
        }
        chars[chars.length - 1] = temp;
//        System.out.println(new String(chars));
    }

    public static boolean checkCorrectness(char[] chars) {
        int counter = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') counter++;
            else counter--;

            if (counter < 0) return false;
        }

        return counter == 0;
    }

    public static void kas(int n, int co, String p, String otv, Set<String> result){
        if (co==n && p.length()==0){
            result.add(otv);
            return;
        }
        if(co<n){
            kas(n, co+1, p+"(", otv+"(", result);
        }
        if(p.length()>0){
            kas(n, co, p.substring(0, p.length()-1), otv+")", result);
        }
    }
}
