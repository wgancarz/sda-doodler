package com.sda.calc;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class CalculatorTestParams {

    private Calculator calculator;

    @Before
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    @Parameters({"1d,2d", "8.5,3.5"})
    public void testAdd(double valueA, double valueB) {
        assertThat(calculator.add(valueA, valueB)).isEqualTo(valueA + valueB);
    }

    @Test
    @Parameters({"1d,2d", "8.5,3.5"})
    public void testSubstract(double valueA, double valueB) {
        assertThat(calculator.substract(valueA, valueB)).isEqualTo(valueA - valueB);
    }

    //Params from provider class
    @Test
    @Parameters(source = DoubleProvider.class)
    public void testMultiply(double valueA, double valueB) {
        assertThat(calculator.multiply(valueA, valueB)).isEqualTo(valueA * valueB);
    }

    //Params from method below, identified by name.
    @Test
    @Parameters //(method = "")
    public void testDivide(double valueA, double valueB) {
        assertThat(calculator.divide(valueA, valueB)).isEqualTo(valueA / valueB);
    }

    private Double[][] parametersForTestDivide() {
        return new Double[][]{
                new Double[]{1d, 2d},
                new Double[]{8.5, 3.5}
        };
    }

    @Test
    @Parameters({"1d", "8.5"})
    public void testRoot(double valueA) {
        assertThat(calculator.root(valueA)).isNotNull().isEqualTo(Math.sqrt(valueA));
    }

    @Test
    @Parameters(source = DoubleProvider.class)
    public void testPower(double number, double power) {
        assertThat(calculator.power(number, power)).isNotNull().isEqualTo(Math.pow(number, power));
    }

    @Test
    public void testIsDivisible() {
        assertThat(calculator.isDivisible(6, 2)).isTrue();
        assertThat(calculator.isDivisible(7, 4)).isFalse();
    }

    @Test
    public void testSum() {
        //given
        double[] tab = {2.45, 6.78, 4.5, 10d, 123d};
        //when
        Double result = calculator.sum(tab);
        //then
        assertThat(result).isNotNull().isEqualTo(Arrays.stream(tab).sum());
    }

}
