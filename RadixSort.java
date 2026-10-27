import java.util.Arrays;

// коды товаров: 12-значные неотрицательные числа, массив счётчиков на весь диапазон не создаём
public class RadixSort {

    static final int DIGITS = 12;

    public static void sort(long[] a) {
        for (long v : a) {
            if (v < 0 || v > 999_999_999_999L) throw new IllegalArgumentException("не 12-значный код: " + v);
        }
        long place = 1;
        for (int d = 0; d < DIGITS; d++, place *= 10) {
            countingByDigit(a, place);
        }
    }

    // устойчивая сортировка подсчётом по одной цифре, счётчиков всего 10
    private static void countingByDigit(long[] a, long place) {
        int[] count = new int[10];
        for (long v : a) count[(int) (v / place % 10)]++;
        for (int d = 1; d < 10; d++) count[d] += count[d - 1];

        long[] out = new long[a.length];
        for (int i = a.length - 1; i >= 0; i--) {   // с конца, чтобы сохранить порядок равных
            int d = (int) (a[i] / place % 10);
            out[--count[d]] = a[i];
        }
        System.arraycopy(out, 0, a, 0, a.length);
    }

    static void check(long[] in, long[] expected) {
        long[] a = in.clone();
        sort(a);
        System.out.println(Arrays.toString(in) + " -> " + Arrays.toString(a)
                + (Arrays.equals(a, expected) ? "  OK" : "  FAIL"));
    }

    public static void main(String[] args) {
        check(new long[]{123456789012L, 12, 900000000000L}, new long[]{12, 123456789012L, 900000000000L});
        check(new long[]{111111111111L, 101010101010L}, new long[]{101010101010L, 111111111111L});
        check(new long[]{0, 999999999999L, 5, 5}, new long[]{0, 5, 5, 999999999999L});

        java.util.Random rnd = new java.util.Random(42);
        long[] big = new long[100_000];
        for (int i = 0; i < big.length; i++) big[i] = (long) (rnd.nextDouble() * 1_000_000_000_000L);
        long[] expected = big.clone();
        Arrays.sort(expected);
        sort(big);
        System.out.println("random 100000: " + (Arrays.equals(big, expected) ? "OK" : "FAIL"));
    }
}
