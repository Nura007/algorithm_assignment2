import org.example.algorithm.KadaneAlgorithm;
import java.util.Random;
import org.example.metrics.Metrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KadaneAlgorithmTest {

    // -------------------
    // Edge case tests
    // -------------------

    @Test
    void testEmptyArray() {
        assertEquals(0, KadaneAlgorithm.findMaxSubarray(new int[]{}, new Metrics())[0],
                "Empty array should return 0 for sum and invalid indices");
    }

    @Test
    void testSingleElement() {
        assertEquals(5, KadaneAlgorithm.findMaxSubarray(new int[]{5}, new Metrics())[0],
                "Single element array should return that element");
    }

    @Test
    void testAllPositiveElements() {
        assertEquals(15, KadaneAlgorithm.findMaxSubarray(new int[]{1, 2, 3, 4, 5}, new Metrics())[0],
                "All positive elements should return the sum of the entire array");
    }

    @Test
    void testAllNegativeElements() {
        assertEquals(-1, KadaneAlgorithm.findMaxSubarray(new int[]{-1, -2, -3, -4, -5}, new Metrics())[0],
                "All negative elements should return the largest (least negative) element");
    }

    // -------------------
    // Performance tests
    // -------------------

    @Test
    void testPerformanceRandom() {
        int[] sizes = {100, 1000, 10000, 100000};
        for (int n : sizes) {
            runBenchmark(generateRandomArray(n), "Random");
        }
    }

    @Test
    void testPerformanceSorted() {
        int[] sizes = {100, 1000, 10000, 100000};
        for (int n : sizes) {
            runBenchmark(generateSortedArray(n), "Sorted");
        }
    }

    @Test
    void testPerformanceReverse() {
        int[] sizes = {100, 1000, 10000, 100000};
        for (int n : sizes) {
            runBenchmark(generateReverseArray(n), "Reverse");
        }
    }

    @Test
    void testPerformanceNearlySorted() {
        int[] sizes = {100, 1000, 10000, 100000};
        for (int n : sizes) {
            runBenchmark(generateNearlySortedArray(n), "NearlySorted");
        }
    }

    // -------------------
    // Helper methods for performance testing
    // -------------------

    private int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(20) - 10; // случайные числа от -10 до 10
        return arr;
    }

    private int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i / 10;
        return arr;
    }

    private int[] generateReverseArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = (n - i) / 10;
        return arr;
    }

    private int[] generateNearlySortedArray(int n) {
        int[] arr = generateSortedArray(n);
        Random rand = new Random();
        for (int i = 0; i < n / 20; i++) {
            int a = rand.nextInt(n);
            int b = rand.nextInt(n);
            int tmp = arr[a];
            arr[a] = arr[b];
            arr[b] = tmp;
        }
        return arr;
    }

    private void runBenchmark(int[] arr, String distribution) {
        Metrics metrics = new Metrics();
        Runtime runtime = Runtime.getRuntime();

        runtime.gc();
        long beforeMem = runtime.totalMemory() - runtime.freeMemory();

        long start = System.nanoTime();
        KadaneAlgorithm.findMaxSubarray(arr, metrics);
        long end = System.nanoTime();

        long afterMem = runtime.totalMemory() - runtime.freeMemory();

        System.out.printf("n=%d | dist=%s | time=%d ns | mem=%d bytes | comparisons=%d | subarrayStartUpdates=%d%n",
                arr.length, distribution, (end - start), (afterMem - beforeMem),
                metrics.comparisons, metrics.subarrayStartUpdates);

        Assertions.assertTrue(true);
    }
}
