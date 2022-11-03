package com.unvise.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FibonacciTest {
    private final Fibonacci fibonacci = new Fibonacci();

    @Test
    void fibonacciTest() {
        // given
        Long exp1 = 1L;
        Long exp2 = 13L;
        Long exp3 = 5L;
        Long exp4 = 377L;
        // then
        assertAll(
                () -> assertEquals(exp1, fibonacci.fibonacci(1)),
                () -> assertEquals(exp2, fibonacci.fibonacci(7)),
                () -> assertEquals(exp3, fibonacci.fibonacci(5)),
                () -> assertEquals(exp4, fibonacci.fibonacci(14))
        );
    }

    @Test
    void fibonacciCachedTest() {
        // given
        Long exp1 = 1L;
        Long exp2 = 13L;
        Long exp3 = 5L;
        Long exp4 = 377L;
        // then
        assertAll(
                () -> assertEquals(exp1, fibonacci.fibonacciCached(1)),
                () -> assertEquals(exp2, fibonacci.fibonacciCached(7)),
                () -> assertEquals(exp3, fibonacci.fibonacciCached(5)),
                () -> assertEquals(exp4, fibonacci.fibonacciCached(14))
        );
    }
}