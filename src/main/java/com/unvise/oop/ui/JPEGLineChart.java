package com.unvise.oop.ui;

import lombok.Builder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;

import java.io.File;
import java.io.IOException;

@Builder
public class JPEGLineChart {
    private String title;
    private String xAxis;
    private String yAxis;
    private XYDataset categoryDataset;
    @Builder.Default
    private Integer height = 600;
    @Builder.Default
    private Integer width = 1000;

    public void save(File file) throws IOException {
        JFreeChart lineChart = createChart();
        ChartUtils.saveChartAsJPEG(file, lineChart, width, height);
    }

    private JFreeChart createChart() {
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                title,
                xAxis,
                yAxis,
                categoryDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = lineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false);
        plot.setRenderer(renderer);

        return lineChart;
    }


}