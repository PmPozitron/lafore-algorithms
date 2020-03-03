package interview;

public class Triangle {

    public static void main(String[] args) {
//        int n = 10;
//        assert triangleIterative(n) == triangle(n) : "Did not match";

        System.out.println(new Integer('0'));
    }

    public static int triangleIterative(int n) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += i;
        }

        return result;
    }

    public static int triangleRecursive(int n) {
        if (n == 1) return n;
        return n + triangleRecursive(n - 1);
    }

    public static int triangle(int n)
    {
        int total = 0;
        while(n > 0)           // until n is 1
        {
            total = total + n;  // add n (column height) to total
            --n;                // decrement column height
        }
        return total;
    }
}
