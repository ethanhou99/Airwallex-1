package com.airwallex.test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import com.airwallex.calculator.Calculator;
import com.airwallex.exceptions.ApplicationException;

/**
 * Example test
 * <p>
 * This tests are based on the example given in the assignment
 */
public class ExampleTest {

    @Test
    public void example1Test() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("5 2");
            List<BigDecimal> values = calculator.stackValues();
            assertEquals("Stack should have two numbers", 2, values.size());
            assertEquals("first number should be 2", new BigDecimal(2.0), values.get(0));
            assertEquals("second number should be 5", new BigDecimal(5.0), values.get(1));

        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example2Test() {
        try {
            Calculator calculator = new Calculator();

            calculator.perform("2 sqrt");
            List<BigDecimal> values = calculator.stackValues();
            assertEquals("Stack should have one number", 1, values.size());
            assertTrue("Sqrt of 2 is incorret", values.get(0).toString().contains("1.4142135623"));

            calculator.perform("clear 3 sqrt");
            values = calculator.stackValues();
            assertEquals("Stack should have one number", 1, values.size());
            assertTrue("Sqrt of 3 is incorret", values.get(0).toString().contains("9"));

        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example3Test() {
        try {
            Calculator calculator = new Calculator();
            calculator.perform("5 2 -");
            List<BigDecimal> values = calculator.stackValues();
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("Number should be 3", new BigDecimal(3.0), values.get(0));

            calculator.perform("3 -");
            values = calculator.stackValues();
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("Number should be 0", new BigDecimal(0), values.get(0));
        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example4Test() {
        try {
            Calculator calculator = new Calculator();
            calculator.perform("5 4 3 2");
            List<BigDecimal> values = calculator.stackValues();
            String stringValues = convertToString(values);

            assertEquals("Stack should have four numbers", 4, values.size());
            assertEquals("numbers should be 5, 4, 3 and 2", "5 4 3 2", stringValues);

            calculator.perform("undo undo *");
            values = calculator.stackValues();
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("Number should be 20", new BigDecimal(20.0), values.get(0));

            calculator.perform("5 *");
            values = calculator.stackValues();
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("Number should be 100", new BigDecimal(100.0), values.get(0));

            calculator.perform("undo");
            values = calculator.stackValues();
            stringValues = convertToString(values);

            assertEquals("Stack should have two numbers", 2, values.size());
            assertEquals("Numbers should be 20, 5", "20 5", stringValues);
        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example5Test() {
        try {
            Calculator calculator = new Calculator();

            calculator.perform("7 12 2 /");
            List<BigDecimal> values = calculator.stackValues();
            String stringValues = convertToString(values);
            assertEquals("Stack should have two numbers", 2, values.size());
            assertEquals("numbers should be 7 and 6", "7 6", stringValues);

            calculator.perform("*");
            values = calculator.stackValues();
            stringValues = convertToString(values);
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("number should be 42", "42", stringValues);

            calculator.perform("4 /");
            values = calculator.stackValues();
            stringValues = convertToString(values);
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("number should be 10.5", "10.5", stringValues);
        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example6Test() {
        try {
            Calculator calculator = new Calculator();

            calculator.perform("1 2 3 4 5");
            List<BigDecimal> values = calculator.stackValues();
            String stringValues = convertToString(values);
            assertEquals("Stack should have five numbers", 5, values.size());
            assertEquals("numbers should be 1, 2, 3, 4, and 5", "1 2 3 4 5", stringValues);

            calculator.perform("*");
            values = calculator.stackValues();
            stringValues = convertToString(values);
            assertEquals("Stack should have four numbers", 4, values.size());
            assertEquals("numbers should be 1, 2, 3, and 20", "1 2 3 20", stringValues);

            calculator.perform("clear 3 4 -");
            values = calculator.stackValues();
            stringValues = convertToString(values);
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("number should be -1", "-1", stringValues);
        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example7Test() {
        try {
            Calculator calculator = new Calculator();

            calculator.perform("1 2 3 4 5");
            List<BigDecimal> values = calculator.stackValues();
            String stringValues = convertToString(values);
            assertEquals("Stack should have five numbers", 5, values.size());
            assertEquals("numbers should be 1, 2, 3, 4, and 5", "1 2 3 4 5", stringValues);

            calculator.perform("* * * *");
            values = calculator.stackValues();
            stringValues = convertToString(values);
            assertEquals("Stack should have one number", 1, values.size());
            assertEquals("number should be 120", "120", stringValues);
        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    @Test
    public void example8Test() {
        try {
            Calculator calculator = new Calculator();
            calculator.perform("1 2 3 * 5 + * * 6 5 ");
            fail("Shouldn't be here.");
        } catch (ApplicationException e) {
            // Error message format is slightly different as position
            // is added at the end of message instead of middle.
            assertTrue(e.getMessage().contains("insufficient parameters"));
        }
    }

    /**
     * Covert current stack values into the string.
     * @param currentStackValues
     * @return converted string
     */
    private String convertToString(List<BigDecimal> currentStackValues) {
        return IntStream.rangeClosed(1, currentStackValues.size())
                .mapToObj(i -> currentStackValues.get(currentStackValues.size() - i).toString()).collect(Collectors.joining(" "));
    }
}