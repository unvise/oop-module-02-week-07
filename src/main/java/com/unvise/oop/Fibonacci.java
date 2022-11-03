package com.unvise.oop;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
    private final Map<Integer, Long> cache = new HashMap<>();

    public Fibonacci() {
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    // Time Complexity - O(2^N)
    public Long fibonacci(Integer number) {
        if (number < 0) {
            return 0L;
        }
        if (number <= 1) {
            return Long.valueOf(number);
        }
        return fibonacci(number - 1) + fibonacci(number - 2);
    }

    // Time Complexity - O(N)
    public Long fibonacciCached(Integer number) {
        if (number < 0) {
            return 0L;
        }
        if (cache.containsKey(number)) {
            return cache.get(number);
        }
        Long result = fibonacciCached(number - 1) + fibonacciCached(number - 2);
        cache.put(number, result);
        return result;
    }
}
