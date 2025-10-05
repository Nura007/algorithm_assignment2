package org.example.algorithm;

import org.example.metrics.Metrics;
import java.util.Arrays;

public class KadaneAlgorithm {

    public static int[] findMaxSubarray(int[] arr, Metrics metrics) {
        try {
            System.out.println("KadaneAlgorithm.class location: " +
                    KadaneAlgorithm.class.getProtectionDomain().getCodeSource().getLocation());
        } catch (Exception e) {
            System.out.println("Can't get class location: " + e.getMessage());
        }

        if (arr == null || arr.length == 0) {
            System.out.println("Input array is empty or null");
            return new int[]{0, -1, -1};
        }

        System.out.println("Input array: " + Arrays.toString(arr));

        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;
        int start = 0, end = -1, tempStart = 0;

        boolean hasPositive = false;
        int maxElement = Integer.MIN_VALUE;

        for (int i = 0; i < arr.length; i++) {
            metrics.comparisons++;

            currentSum += arr[i];
            if (arr[i] > 0) hasPositive = true;
            if (arr[i] > maxElement) maxElement = arr[i];

            System.out.printf("i=%d, arr[i]=%d, currentSum(before check)=%d%n", i, arr[i], currentSum);

            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
                System.out.printf("  -> new maxSum=%d (start=%d, end=%d)%n", maxSum, start, end);
            }

            if (currentSum < 0) {
                currentSum = 0;
                tempStart = i + 1;
                metrics.subarrayStartUpdates++;
                System.out.printf("  -> currentSum reset to 0, tempStart=%d%n", tempStart);
            }
        }

        System.out.println("hasPositive=" + hasPositive + ", maxElement=" + maxElement + ", maxSum=" + maxSum);

        if (!hasPositive) {
            System.out.println("All non-positive. Returning maxElement = " + maxElement);
            return new int[]{maxElement, -1, -1};
        }

        if (maxSum < 0) {
            System.out.println("No positive subarray sum. Returning 0");
            return new int[]{0, -1, -1};
        }

        System.out.printf("Returning maxSum=%d, start=%d, end=%d%n", maxSum, start, end);
        return new int[]{maxSum, start, end};
    }

    public static void main(String[] args) {
        Metrics m = new Metrics();
        int[] a1 = {-2, -3, -1};
        System.out.println("Result for " + Arrays.toString(a1) + ": " + Arrays.toString(findMaxSubarray(a1, m)));

        Metrics m2 = new Metrics();
        int[] a2 = {1, -2, 3, 4, -1, 2};
        System.out.println("Result for " + Arrays.toString(a2) + ": " + Arrays.toString(findMaxSubarray(a2, m2)));

        Metrics m3 = new Metrics();
        int[] a3 = {0, 1, 2, 3, 0, -1, -2};
        System.out.println("Result for " + Arrays.toString(a3) + ": " + Arrays.toString(findMaxSubarray(a3, m3)));
    }
}
