package com.unvise.oop.util;

import java.util.List;

public interface TrendLine {
    void setValues(List<Double> y, List<Double> x);

    Double predictY(Double x);

    List<Double> predictY(List<Double> x);
}