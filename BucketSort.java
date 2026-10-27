import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// доли голосов: числа от 0 до 1, распределены по всему интервалу
public class BucketSort {

    public static void sort(double[] a) {
        int n = a.length;
        if (n == 0) return;

        List<List<Double>> buckets = new ArrayList<>(n);
        for (int i = 0; i < n; i++) buckets.add(new ArrayList<>());

        for (double v : a) {
            if (v < 0 || v > 1) throw new IllegalArgumentException("доля вне [0, 1]: " + v);
            int i = Math.min(n - 1, (int) (v * n));   // 1.0 кладём в последнюю корзину
            buckets.get(i).add(v);
        }

        int k = 0;
        for (List<Double> b : buckets) {
            insertionSort(b);
            for (double v : b) a[k++] = v;
        }
    }

    private static void insertionSort(List<Double> b) {
        for (int i = 1; i < b.size(); i++) {
            double value = b.get(i);
            int j = i - 1;
            while (j >= 0 && b.get(j) > value) {
                b.set(j + 1, b.get(j));
                j--;
            }
            b.set(j + 1, value);
        }
    }

    static void check(double[] in, double[] expected) {
        double[] a = in.clone();
        sort(a);
        System.out.println(Arrays.toString(in) + " -> " + Arrays.toString(a)
                + (Arrays.equals(a, expected) ? "  OK" : "  FAIL"));
    }

    public static void main(String[] args) {
        check(new double[]{0.25, 0.70, 0.10, 0.90}, new double[]{0.10, 0.25, 0.70, 0.90});
        check(new double[]{0.05, 0.05, 0.80}, new double[]{0.05, 0.05, 0.80});
        check(new double[]{1.0, 0.0, 0.5}, new double[]{0.0, 0.5, 1.0});
        check(new double[]{}, new double[]{});

        // случайный массив, сравниваем с Arrays.sort
        java.util.Random rnd = new java.util.Random(42);
        double[] big = new double[100_000];
        for (int i = 0; i < big.length; i++) big[i] = rnd.nextDouble();
        double[] expected = big.clone();
        Arrays.sort(expected);
        sort(big);
        System.out.println("random 100000: " + (Arrays.equals(big, expected) ? "OK" : "FAIL"));
    }
}
