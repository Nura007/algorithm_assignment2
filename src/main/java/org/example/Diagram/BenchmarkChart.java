package org.example.Diagram;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class BenchmarkChart {

    private static final String CSV_FILE_NAME = "benchmark_results.csv";

    public static void main(String[] args) {
        Map<String, Map<Double, Double>> data = loadAndFilterData(CSV_FILE_NAME);

        if (data.isEmpty()) {
            System.out.println("" + CSV_FILE_NAME);
            return;
        }

        XYChart chart = createChart(data);
        System.out.println("");
        new SwingWrapper<>(chart).displayChart();
    }

    private static Map<String, Map<Double, Double>> loadAndFilterData(String fileName) {
        Map<String, Map<Double, Double>> groupedData = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] header = reader.readNext();
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (!nextLine[1].equals("Random")) {
                    continue;
                }

                double n = Double.parseDouble(nextLine[0]);
                String version = nextLine[2];
                double timeMs = Double.parseDouble(nextLine[3]) / 1_000_000.0;

                groupedData.computeIfAbsent(version, k -> new HashMap<>()).put(n, timeMs);
            }

        } catch (IOException | CsvValidationException e) {
            System.err.println("Ошибка при чтении или разборе CSV: " + e.getMessage());
            e.printStackTrace();
        }
        return groupedData;
    }

    private static XYChart createChart(Map<String, Map<Double, Double>> data) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Time Complexity O(N)")
                .xAxisTitle("N (Log Scale)")
                .yAxisTitle("Time (ms)")
                .build();

        chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
        chart.getStyler().setXAxisLogarithmic(true);
        chart.getStyler().setYAxisLogarithmic(false);

        for (Map.Entry<String, Map<Double, Double>> entry : data.entrySet()) {
            String version = entry.getKey();
            Map<Double, Double> seriesData = entry.getValue();

            List<Double> xData = new ArrayList<>(seriesData.keySet());
            xData.sort(Double::compare);

            List<Double> yData = new ArrayList<>();
            for (Double n : xData) {
                yData.add(seriesData.get(n));
            }


            chart.addSeries("Версия: " + version, xData, yData);
        }

        return chart;
    }
}