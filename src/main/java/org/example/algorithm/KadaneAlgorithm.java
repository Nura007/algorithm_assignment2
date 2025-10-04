package org.example.algorithm;

import org.example.metrics.Metrics;

public class KadaneAlgorithm {

    public static int[] findMaxSubarray(int[] arr, Metrics metrics) {
        if (arr == null || arr.length == 0) {
            return new int[]{0, -1, -1}; // Пустой массив
        }

        int maxSum = arr[0];
        int currentSum = arr[0];
        int start = 0, end = 0, tempStart = 0;

        for (int i = 1; i < arr.length; i++) {
            metrics.comparisons++;

            // Логика обновления текущей суммы
            if (currentSum + arr[i] > arr[i]) {
                currentSum += arr[i];
            } else {
                currentSum = arr[i];
                tempStart = i;
            }

            // Обновляем максимальную сумму и индексы
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }

        // Если максимальная сумма меньше 0, то возвращаем наименьший элемент (для тестов с отрицательными числами)
        if (maxSum < 0) {
            maxSum = arr[0];
            start = 0;
            end = 0;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > maxSum) {
                    maxSum = arr[i];
                    start = i;
                    end = i;
                }
            }
        }

        // Если массив состоит из нулей, возвращаем сумму наибольшего подмассива
        if (maxSum == 0) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] == 0) {
                    maxSum = 0;
                    start = i;
                    end = i;
                }
            }
        }

        metrics.subarrayStartUpdates++;
        return new int[]{maxSum, start, end};
    }
}
