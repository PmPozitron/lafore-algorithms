package chapter11;

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

    public void insert(String aString) {
        int hash = hashFunction(aString);
        table[hash] = aString;
    }

    public int find(String aString) {
        int hash = hashFunction(aString);
        if (table[hash].equals(aString)) {
            return hash;

        } else {
            return -1;
        }
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
    }
}
