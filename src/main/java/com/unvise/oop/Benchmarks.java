package com.unvise.oop;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Benchmarks {
    public static List<Long> iterAvgNanoTime(Consumer<Integer> integerConsumer,
                                             List<Integer> iterParams,
                                             Integer iterRepeat) throws IllegalArgumentException {
        if (iterRepeat < 1)
            throw new IllegalArgumentException();
        if (iterParams.size() != 3)
            throw new IllegalArgumentException();

        List<Long> res = new ArrayList<>();
        for (int i = iterParams.get(0); i <= iterParams.get(1); i += iterParams.get(2)) {
            long sumTimeRes = 0L;
            for (int j = 0; j < iterRepeat; j++) {
                sumTimeRes += measureExecutionTime(integerConsumer, i).getNano();
            }
            double averageTime = (double) (sumTimeRes / iterRepeat);
            res.add((long) Math.floor(averageTime));
        }
        return res;
    }

    public static <T> Duration measureExecutionTime(Consumer<T> instanceConsumer, T instance) {
        Instant fibonacciStartTime = Instant.now();
        instanceConsumer.accept(instance);
        Instant fibonacciEndTime = Instant.now();
        return Duration.between(fibonacciStartTime, fibonacciEndTime);
    }
}
