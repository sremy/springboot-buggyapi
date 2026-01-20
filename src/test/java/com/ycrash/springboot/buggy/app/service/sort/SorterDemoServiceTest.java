package com.ycrash.springboot.buggy.app.service.sort;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class SorterDemoServiceTest {
    private SubStringSorterService.MyComp comparator;

    @BeforeEach
    void setUp() {
        comparator = new SubStringSorterService.MyComp();
    }

    @Test
    void compareStringsWithDifferentNumbers_ReturnsExpectedOrder() {
        String a = "Day10";
        String b = "Day20";

        int result = comparator.compare(a, b);

        assertTrue(result < 0);
        assertTrue(comparator.compare(b, a) > 0);
    }

    @Test
    void compareStringsWithSameNumber_ReturnsZero() {
        String a = "Day42";
        String b = "Day42";

        int result = comparator.compare(a, b);

        assertEquals(0, result);
    }

    @ParameterizedTest
    @CsvSource({
            "Day5,Day1000",
            "Day0,Day99999",
            "Day1,Day10000000"
    })
    void compareStringsWithVeryDifferentNumbers_ReturnsExpectedOrder(String smaller, String larger) {
        assertTrue(comparator.compare(smaller, larger) < 0);
        assertTrue(comparator.compare(larger, smaller) > 0);
    }

    @Test
    void compareWithMaxAndMinIntegers_ReturnsExpectedOrder() {
        String min = "Day" + 0;
        String max = "Day" + Integer.MAX_VALUE;

         assertTrue(comparator.compare(min, max) < 0);
        assertTrue(comparator.compare(max, min) > 0);
    }
}