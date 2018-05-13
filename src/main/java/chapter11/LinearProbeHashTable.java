package chapter11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearProbeHashTable {

    private String[] table;

    public LinearProbeHashTable(int size) {
        this.table = new String[size];
    }

    private int hashFunction(String forHashing) {
        int result = 0;
        int symbolCounter = 1;
        for (int i = forHashing.length()-1; i>=0; i--) {
            result += (forHashing.charAt(i) - 96) * (int)Math.pow(27., symbolCounter++);
        }
        result %= table.length;

        return result;
    }

    private int hashFunction2(String forHashing) {
        int result = 0;
        int symbolCounter = forHashing.length()-1;
        for (int i = symbolCounter; i>=0; i--) {
            result = (result * 27 + (forHashing.charAt(i) - 96)) % table.length;
        }
        return result;
    }

    private int hashViaFolding(String forHashing) {
        List<String> groups = new ArrayList<String>();
        for (int i = 0, j=0; i < forHashing.length();) {
            if (i+2 > forHashing.length()) {
                j = forHashing.length();

            } else {
                j = i+2;
            }

            groups.add(forHashing.substring(i, j));
            i+=2;
        }

        int result = 0;
        for (String group : groups) {
            result += hashFunction(group);
        }

        return result % table.length;
    }

    public void insert(String aString) {
        int hash = hashViaFolding(aString);
        while (table[hash] != null) {
            hash++;
            hash%=table.length;
        }
        table[hash] = aString;
    }

    public int find(String aString) {
        int visitedCount = 0;
        int hash = hashViaFolding(aString);
        while (table[hash] != aString && visitedCount < table.length) {
            visitedCount++;
            hash++;
            hash%=table.length;
        }

        return visitedCount < table.length ? hash : -1;
    }

    public static void main(String[] args) {
        LinearProbeHashTable table = new LinearProbeHashTable(100);
        String[] strings = new String[]{"lalala", "lululu", "lololo", "bobobo", "bebebe", "bububu", "bababa"};
        for (String string : strings) {
            table.insert(string);
        }

        for (int i = strings.length-1; i>=0; i--) {
            System.out.printf("%s is at %d\n", strings[i], table.find(strings[i]));
        }
        System.out.printf("%s is at %d\n", "absent", table.find("huy"));


        System.out.println(Arrays.toString(table.table));
    }
}
