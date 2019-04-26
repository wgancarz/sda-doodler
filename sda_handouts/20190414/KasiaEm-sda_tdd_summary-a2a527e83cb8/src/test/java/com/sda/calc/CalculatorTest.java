package com.sda.calc;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void testAdd() {
        // given
        double expected = 6.1d;
        // when
        Double result = calculator.add(2.5d, 3.6d);

        // then
        // simple JUnit assertions
        assertNotNull(result);
        assertEquals(expected, result.doubleValue());

        // AssertJ assertions
        assertThat(result).as("2+3 should return 5").isNotNull().isEqualTo(expected);
    }

    @Test
    public void testSubstract() {
        // given
        double expected = 2.5;
        // when
        Double result = calculator.substract(7d, 4.5);

        // then
        // simple JUnit assertions
        assertNotNull(result);
        assertEquals(expected, result.doubleValue());

        // AssertJ assertions
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @Test
    public void testMultiply() {
        // given
        double expected = 6d;
        // when
        Double result = calculator.multiply(1.5, 4);

        // then
        // simple JUnit assertions
        assertNotNull(result);
        assertEquals(expected, result.doubleValue());

        // AssertJ assertions
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @Test
    public void testDivide() {
        // given
        double expected = 5d;
        // when
        //Double result = calculator.divide(5d, 0d);
        Double result = calculator.divide(10d, 2d);

        // then
        // simple JUnit assertions
        assertNotNull(result);
        assertEquals(expected, result.doubleValue());

        // AssertJ assertions
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @Test
    public void testRoot() {
        // given
        double expected = 2d;
        // when
        Double result = calculator.root(4d);

        // then
        // simple JUnit assertions
        assertNotNull(result);
        assertEquals(expected, result.doubleValue());

        // AssertJ assertions
        assertThat(result).isNotNull().isEqualTo(expected);
    }

    @Test
    public void testRootException() {
        thrown.expect(RootException.class);
        thrown.expectMessage("Can't root a negative number.");
        Double result = calculator.root(-2d);
        assertNull(result);
    }
}
