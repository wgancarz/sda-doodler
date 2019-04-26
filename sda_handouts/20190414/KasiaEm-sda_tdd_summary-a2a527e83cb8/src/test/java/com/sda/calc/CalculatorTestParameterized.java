package com.sda.calc;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by katar on 06.12.2018.
 */
@RunWith(Parameterized.class)
public class CalculatorTestParameterized {

    private Calculator calculator;
    private Double valueA;
    private Double valueB;

    public CalculatorTestParameterized(Double valueA, Double valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    @Before
    public void setup(){
        calculator = new Calculator();
    }

    @Parameterized.Parameters
    public static Collection getParameters() {
        return Arrays.asList(new Double[][]{{1.0, 2.5}, {3.3, 5.0}, {2d, 4d}});
    }

    @Test
    public void testAdd(){
        double result = calculator.add(valueA, valueB);
        assertThat(result).isEqualTo(valueA+valueB);
    }
}