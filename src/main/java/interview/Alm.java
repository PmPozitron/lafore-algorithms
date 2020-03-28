package interview;

public class Alm {
    static long counter;

    public static void main(String[] args) {
        long n = 1_000_000;
        long a = 499_999_500_000L;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                f(i, j);
            }
        }

        System.out.println(counter);
    }

    static void f (int i, int j) {
        counter++;
    }

}
