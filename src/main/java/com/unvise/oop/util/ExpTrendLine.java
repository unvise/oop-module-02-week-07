package com.unvise.oop.util;

public class ExpTrendLine extends OLSTrendLine {
    @Override
    protected double[] xVector(Double x) {
        return new double[]{1,x};
    }

    @Override
    protected boolean logY() {return true;}
}