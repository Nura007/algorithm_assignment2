package org.example.cli;

import org.example.algorithm.KadaneAlgorithm;
import org.example.metrics.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    private static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(20) - 10;
        return arr;
    }

    private static int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i / 10;
        return arr;
    }

    private static int[] generateReverseSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = (n - i) / 10;
        return arr;
    }

    private static int[] generateNearlySortedArray(int n) {
        Random rand = new Random();
        int[] arr = generateSortedArray(n);
        for (int i = 0; i < n / 20; i++) {
            int a = rand.nextInt(n);
            int b = rand.nextInt(n);
            int tmp = arr[a];
            arr[a] = arr[b];
            arr[b] = tmp;
        }
        return arr;
    }

    private static long measureMemoryUsage(int[] array, Metrics metrics, boolean optimized) {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();

        long beforeUsedMem = runtime.totalMemory() - runtime.freeMemory();
        if (optimized)
            KadaneAlgorithm.findMaxSubarray(array, metrics);
        else
            KadaneAlgorithm.findMaxSubarray(array, metrics);
        long afterUsedMem = runtime.totalMemory() - runtime.freeMemory();

        return afterUsedMem - beforeUsedMem;
    }

    public static void main(String[] args) throws IOException {
        int[] sizes = {100, 1000, 10000, 100000};
        String[] distributions = {"Random", "Sorted", "Reverse", "NearlySorted"};

        try (FileWriter writer = new FileWriter("benchmark_results.csv")) {
            writer.write("n,distribution,version,time(ns),memory(bytes),comparisons,subarrayStartUpdates\n");

            for (int n : sizes) {
                for (String dist : distributions) {
                    int[] array;
                    switch (dist) {
                        case "Sorted" -> array = generateSortedArray(n);
                        case "Reverse" -> array = generateReverseSortedArray(n);
                        case "NearlySorted" -> array = generateNearlySortedArray(n);
                        default -> array = generateRandomArray(n);
                    }

                    Metrics m1 = new Metrics();
                    long start1 = System.nanoTime();
                    long mem1 = measureMemoryUsage(array, m1, false);
                    long end1 = System.nanoTime();
                    writer.write(n + "," + dist + ",Baseline," + (end1 - start1) + "," + mem1 + "," + m1.comparisons + "," + m1.subarrayStartUpdates + "\n");

                    Metrics m2 = new Metrics();
                    long start2 = System.nanoTime();
                    long mem2 = measureMemoryUsage(array, m2, true);
                    long end2 = System.nanoTime();
                    writer.write(n + "," + dist + ",Optimized," + (end2 - start2) + "," + mem2 + "," + m2.comparisons + "," + m2.subarrayStartUpdates + "\n");
                }
            }
        }

        System.out.println("Benchmark finished! Results saved to benchmark_results.csv");
    }
}