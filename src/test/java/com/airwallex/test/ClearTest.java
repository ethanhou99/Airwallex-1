package com.airwallex.test;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.airwallex.calculator.Calculator;
import com.airwallex.exceptions.ApplicationException;

/**
 * Clear operation test with and without undo
 * <p>
 */
public class ClearTest {

    /**
     * 'clear' operation test
     */
    @Test
    public void clearTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("5 3 clear");
            assertTrue("Stack shall have zero elements", calculator.stackValues().size() == 0);

            // Multiline test
            calculator.perform("5 3");
            calculator.perform("clear");
            assertTrue("Stack shall have zero elements", calculator.stackValues().size() == 0);

        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    /**
     * 'uclear' operation test, unclear is clear operation which can be undone via 'undo'
     */
    @Test
    public void clearWithUndoTest() {
        Calculator calculator = new Calculator();
        try {
            // 'uclear' operation: clear function which can be undone with 'undo'
            calculator.perform("5 3 uclear");
            assertTrue("Stack shall have zero elements", calculator.stackValues().size() == 0);

            calculator.perform("undo");
            List<BigDecimal> currentStackValues = calculator.stackValues();
            assertEquals("The first number should be 3", new BigDecimal(3.0), currentStackValues.get(0));
            assertEquals("The second number should be 5", new BigDecimal(5.0), currentStackValues.get(1));

        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }

    /**
     * Testing behavior when clear is called without any data.
     */
    @Test
    public void clearEmptyTest() {
        Calculator calculator = new Calculator();
        try {
            // exception is raised when there is nothing to clear.
            calculator.perform("clear");
            fail("Shouldn'be here.");
        } catch (ApplicationException e) {
        }
    }
}
