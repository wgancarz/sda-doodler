package com.sda.calc;

public class DoubleProvider {

    /* must have at least one static method that starts
    with provide and return Object[] */

    public static Double[][] provideNegative() {
        return new Double[][]{
                new Double[]{-4.5, -10d},
                new Double[]{-2.76, -9d}
        };
    }

    public static Double[][] providePositive() {
        return new Double[][]{
                new Double[]{5.5, 15d},
                new Double[]{3.56, 96.5}
        };
    }
}
