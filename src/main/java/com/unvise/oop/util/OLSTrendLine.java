package com.unvise.oop.util;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class OLSTrendLine implements TrendLine {
    protected abstract double[] xVector(Double x);
    protected abstract boolean logY();
    private RealMatrix coefficients;

    @Override
    public void setValues(List<Double> y, List<Double> x) {
        if (x.size() != y.size()) {
            throw new IllegalArgumentException(String.format("Количество значений y и x должно быть равным (%d != %d)", y.size(), x.size()));
        }
        double[][] xData = new double[x.size()][];
        for (int i = 0; i < x.size(); i++) {
            xData[i] = xVector(x.get(i));
        }
        if (logY()) {
            y = new ArrayList<>(y);
            for (int i = 0; i < x.size(); i++) {
                y.set(i, Math.log(y.get(i)));
            }
        }
        OLSMultipleLinearRegression ols = new OLSMultipleLinearRegression();
        ols.setNoIntercept(true);
        final double[] arr = new double[y.size()];
        Arrays.setAll(arr, y::get);
        ols.newSampleData(arr, xData);
        coefficients = MatrixUtils.createColumnRealMatrix(ols.estimateRegressionParameters());
    }

    @Override
    public Double predictY(Double x) {
        double yhat = coefficients.preMultiply(xVector(x))[0];
        if (logY()) yhat = (Math.exp(yhat));
        return yhat;
    }

    @Override
    public List<Double> predictY(List<Double> x) {
        return x
                .stream()
                .map(this::predictY)
                .toList();
    }
}