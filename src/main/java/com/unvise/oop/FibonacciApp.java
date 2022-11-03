package com.unvise.oop;

import com.unvise.oop.ui.JPEGLineChart;
import com.unvise.oop.util.ExpTrendLine;
import com.unvise.oop.util.PolyTrendLine;
import com.unvise.oop.util.TrendLine;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.unvise.oop.Benchmarks.iterAvgNanoTime;

public class FibonacciApp {
    public static void main(String[] args) {
        List<Integer> xValues = IntStream.range(5, 26).boxed().toList();
        List<Long> fibonacciTimes = fibonacciTimes(xValues);
        System.out.println("Время (нс) выполнения классической (рекурсивной) реализации Фибоначчи: " + fibonacciTimes);
        List<Long> fibonacciCachedTimes = cachedFibonacciTimes(xValues);
        System.out.println("Время (нс) выполнения кеширующей (рекурсивной) реализации Фибоначчи: " + fibonacciCachedTimes);
        exponentChartsSave(xValues, fibonacciTimes.stream().map(Long::doubleValue).toList());
        linearChartsSave(xValues, fibonacciCachedTimes.stream().map(Long::doubleValue).toList());
    }

    private static void exponentChartsSave(List<Integer> xValues, List<Double> yValues) {
        List<Double> xValuesAsDoubles = xValues.stream().mapToDouble(Integer::doubleValue).boxed().toList();
        // создание графика экспоненциальной временной сложности для Фибоначчи
        TrendLine expTrendLine = new ExpTrendLine();
        expTrendLine.setValues(yValues, xValuesAsDoubles);
        createExpFibTimeComplexityChart(xValues, expTrendLine.predictY(xValuesAsDoubles), "ExponentFibonacciTimeComplexityChart1.jpeg");
        // создание еще одного графика с умноженными на 100 значениями
        expTrendLine.setValues(yValues.stream().map(el -> el * 100).toList(), xValuesAsDoubles);
        createExpFibTimeComplexityChart(xValues, expTrendLine.predictY(xValuesAsDoubles), "ExponentFibonacciTimeComplexityChart2.jpeg");
    }

    private static void linearChartsSave(List<Integer> xValues, List<Double> yValues) {
        List<Double> xValuesAsDoubles = xValues.stream().mapToDouble(Integer::doubleValue).boxed().toList();
        // создание графика линейной временной сложности для Фибоначчи
        TrendLine linearTrendLine = new PolyTrendLine(1);
        linearTrendLine.setValues(yValues, xValuesAsDoubles);
        createLinearFibTimeComplexityChart(xValues, linearTrendLine.predictY(xValuesAsDoubles), "LinearFibonacciTimeComplexityChart1.jpeg");
        // создание еще одного графика с умноженными на 100 значениями
        linearTrendLine.setValues(yValues.stream().map(el -> el / 50).toList(), xValuesAsDoubles);
        createLinearFibTimeComplexityChart(xValues, linearTrendLine.predictY(xValuesAsDoubles), "LinearFibonacciTimeComplexityChart2.jpeg");
    }

    private static List<Long> fibonacciTimes(List<Integer> xValues) {
        return iterAvgNanoTime((x) -> {
            Fibonacci fibonacci = new Fibonacci();
            fibonacci.fibonacci(x);
        }, List.of(xValues.get(0), xValues.get(xValues.size() - 1), 1), 1000);
    }

    private static List<Long> cachedFibonacciTimes(List<Integer> xValues) {
        return iterAvgNanoTime((x) -> {
            // каждый раз создается новый экземпляр класса, чтобы
            // очистить мапу с кешированными значениями
            Fibonacci fibonacci = new Fibonacci();
            fibonacci.fibonacciCached(x);
        }, List.of(xValues.get(0), xValues.get(xValues.size() - 1), 1), 1000);
    }

    private static void createLinearFibTimeComplexityChart(List<Integer> xValues, List<Double> yValues, String fileName) {
        XYSeries xyCalculated = new XYSeries("Вычисленная (линейный тренд)");
        XYSeries xyTheoretical = new XYSeries("Теоретическая (N)");
        IntStream
                .range(0, xValues.size())
                .forEach(idx -> {
                    xyCalculated.add(xValues.get(idx).doubleValue(), yValues.get(idx) - 1);
                    xyTheoretical.add(xValues.get(idx).doubleValue(), xValues.get(idx));
                });

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xyCalculated);
        xySeriesCollection.addSeries(xyTheoretical);
        JPEGLineChart jpegLineChart = JPEGLineChart.builder()
                .title("Временная сложность кеширующей реализации Фибоначчи (Рекурсивная)")
                .xAxis("Число Фибоначчи")
                .yAxis("Миллисекунды")
                .categoryDataset(xySeriesCollection)
                .build();
        try {
            jpegLineChart.save(saveResource().apply(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createExpFibTimeComplexityChart(List<Integer> xValues, List<Double> yValues, String fileName) {
        XYSeries xyCalculated = new XYSeries("Вычисленная (экспоненциальный тренд)");
        XYSeries xyTheoretical = new XYSeries("Теоретическая (2^N)");
        IntStream
                .range(0, xValues.size() - 1)
                .boxed()
                .forEach(idx -> {
                    xyCalculated.add(xValues.get(idx).doubleValue(), yValues.get(idx) - 1);
                    xyTheoretical.add(xValues.get(idx).doubleValue(), Math.pow(2, xValues.get(idx)));
                });
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(xyCalculated);
        xySeriesCollection.addSeries(xyTheoretical);
        JPEGLineChart jpegLineChart = JPEGLineChart.builder()
                .title("Временная сложность классической реализации Фибоначчи (Рекурсивная)")
                .xAxis("Число Фибоначчи")
                .yAxis("Миллисекунды")
                .categoryDataset(xySeriesCollection)
                .build();
        try {
            jpegLineChart.save(saveResource().apply(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Function<String, File> saveResource() {
        return (s) -> new File("src/main/resources/" + s);
    }
}
