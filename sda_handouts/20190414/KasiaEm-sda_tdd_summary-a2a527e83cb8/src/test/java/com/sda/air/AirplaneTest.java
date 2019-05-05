package com.sda.air;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirplaneTest {
    private Airplane a;

    @Before
    public void setup() {
        a = new Airplane("x", 1000);
    }

    @Test
    public void testDescent() {
        //when
        a.descent(1500);

        // then
        // simple JUnit assertions
        assertEquals(a.getHeight(), 0);
    }

    @Test
    public void testAscent() {
        //when
        a.ascent(1000000);

        // then
        // simple JUnit assertions
        assertEquals(a.getHeight(), 1000000);
    }
}