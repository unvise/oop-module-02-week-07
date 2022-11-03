package com.unvise.oop.util;

public class PolyTrendLine extends OLSTrendLine {
    private final Integer degree;

    public PolyTrendLine(Integer degree) {
        if (degree < 0) throw new IllegalArgumentException("Степень полинома не должна быть отрицательной");
        this.degree = degree;
    }

    protected double[] xVector(Double x) {
        double[] poly = new double[degree + 1];
        double xi = 1;
        for (int i = 0; i <= degree; i++) {
            poly[i] = xi;
            xi *= x;
        }
        return poly;
    }

    @Override
    protected boolean logY() {
        return false;
    }
}