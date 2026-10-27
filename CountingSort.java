import java.util.Arrays;

// результаты бросков кубика: только 1..6
public class CountingSort {

    // count[v] = сколько раз выпало v
    public static int[] count(int[] a) {
        int[] count = new int[7];
        for (int v : a) {
            if (v < 1 || v > 6) throw new IllegalArgumentException("не результат броска: " + v);
            count[v]++;
        }
        return count;
    }

    public static void sort(int[] a) {
        int[] count = count(a);
        int k = 0;
        for (int v = 1; v <= 6; v++) {
            for (int r = 0; r < count[v]; r++) a[k++] = v;
        }
    }

    static void check(int[] in, int[] expected) {
        int[] a = in.clone();
        sort(a);
        System.out.println(Arrays.toString(in) + " -> " + Arrays.toString(a)
                + (Arrays.equals(a, expected) ? "  OK" : "  FAIL"));
        int[] count = count(in);
        StringBuilder s = new StringBuilder("  повторы:");
        for (int v = 1; v <= 6; v++) if (count[v] > 1) s.append(' ').append(v).append(" x").append(count[v]);
        System.out.println(s);
    }

    public static void main(String[] args) {
        check(new int[]{6, 1, 6, 3, 2}, new int[]{1, 2, 3, 6, 6});
        check(new int[]{4, 4, 2, 5}, new int[]{2, 4, 4, 5});

        java.util.Random rnd = new java.util.Random(42);
        int[] big = new int[1_000_000];
        for (int i = 0; i < big.length; i++) big[i] = rnd.nextInt(6) + 1;
        int[] expected = big.clone();
        Arrays.sort(expected);
        sort(big);
        System.out.println("random 1000000: " + (Arrays.equals(big, expected) ? "OK" : "FAIL"));
    }
}
