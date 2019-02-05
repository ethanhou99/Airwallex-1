package com.airwallex.test;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.airwallex.calculator.Calculator;
import com.airwallex.exceptions.ApplicationException;

public class UndoTest {

    /**
     * Undo addition test
     */
    @Test
    public void undoAdditionTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("5 3 2 1 undo undo");
            List<BigDecimal> currentStackValues = calculator.stackValues();
            assertEquals("It should two numbers in the stack", 2, currentStackValues.size());
            assertEquals("The first number should be 3", new BigDecimal(3.0), currentStackValues.get(0));
            assertEquals("The second number should be 5", new BigDecimal(5.0), currentStackValues.get(1));

            calculator.perform("+");
            calculator.perform("undo");

            currentStackValues = calculator.stackValues();
            assertEquals("The first number should be 3", new BigDecimal(3.0), currentStackValues.get(0));
            assertEquals("The second number should be 5", new BigDecimal(5.0), currentStackValues.get(1));

        } catch (ApplicationException e) {
            fail("Shouldn't catch any exception here.");
        }
    }

    /**
     * Undo subtraction test
     */
    @Test
    public void undoSubtractionTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("-5 3 2 undo");
            calculator.perform("-");

            List<BigDecimal> currentStackValues = calculator.stackValues();
            assertEquals("Stack shall have one number", 1, currentStackValues.size());
            assertEquals("Number should be -8", new BigDecimal(-8.0), currentStackValues.get(0));

            calculator.perform("undo");

            currentStackValues = calculator.stackValues();
            assertEquals("The first number should be 3", new BigDecimal(3.0), currentStackValues.get(0));
            assertEquals("The second number should be -5", new BigDecimal(-5.0), currentStackValues.get(1));

        } catch (ApplicationException e) {
            fail("Shouldn't catch any exception here.");
        }
    }

    /**
     * Undo multiplication test
     */
    @Test
    public void undoMultiplicationTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("15 6 -8 undo");
            calculator.perform("*");

            List<BigDecimal> currentStackValues = calculator.stackValues();
            assertEquals("Stack shall have one number", 1, currentStackValues.size());
            assertEquals("Number should be 2", new BigDecimal(90.0), currentStackValues.get(0));

            calculator.perform("undo");

            currentStackValues = calculator.stackValues();
            assertEquals("The first number should be 6", new BigDecimal(6.0), currentStackValues.get(0));
            assertEquals("The second number should be 15", new BigDecimal(15.0), currentStackValues.get(1));

        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    /**
     * Undo division test
     */
    @Test
    public void undoDivisionTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("20 11");
            calculator.perform("/");

            List<BigDecimal> currentStackValues = calculator.stackValues();
            assertEquals("Stack shall have one number", 1, currentStackValues.size());
            assertTrue("Number should be 1.81818", currentStackValues.get(0).toString().contains("1.81818"));

            calculator.perform("undo");

            currentStackValues = calculator.stackValues();
            assertEquals("The first number should be 11", new BigDecimal(11.0), currentStackValues.get(0));
            assertEquals("The second number should be 20", new BigDecimal(20.0), currentStackValues.get(1));

        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    /**
     * Testing behavior when undo is called without any data.
     */
    @Test
    public void undoEmptyTest() {
        Calculator calculator = new Calculator();
        try {
            // Testing that exception is raised when there is nothing to undone.
            calculator.perform("undo");
            fail("Shouldn'be here.");
        } catch (ApplicationException e) {
        }
    }
}
