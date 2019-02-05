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
 * Calculator test
 * <p>
 * Test basic calculator operations such as '+', '-', '/', '*' and 'sqrt'
 */
public class CalculatorTest {

    /**
     * Calculation operation test.
     */
    @Test
    public void calculatorTest() {
        Calculator calculator = new Calculator();
        try {
			assertEquals("Value should be same",
					new BigDecimal(15.0), calculator.perform("8 7 +"));

			assertEquals("Value should be same",
					new BigDecimal(125.0), calculator.perform("99 11 + 8 7 + +"));

			assertEquals("Value should be same",
					new BigDecimal(28.0), calculator.perform("4 7 *"));

			assertEquals("Value should be same",
					new BigDecimal(280.0), calculator.perform("4 7 * 5 2 * *"));

			assertEquals("Value should be same",
					new BigDecimal(5.0), calculator.perform("8 3 -"));

	         assertEquals("Value should be same",
	                    new BigDecimal(-5.0), calculator.perform("-8 3 +"));

			assertEquals("Value should be same",
					new BigDecimal(26.0), calculator.perform("33 3 - 10 6 - -"));

			assertEquals("Value should be same",
					new BigDecimal(4.0), calculator.perform("36 9 / "));

			assertEquals("Value should be same",
					new BigDecimal(5.0), calculator.perform("90 3 / 30 5 / /"));

			assertEquals("Value should be same",
					new BigDecimal(5.0), calculator.perform("15 7 1 1 + - / 3 * 2 1 1 + + -"));

			assertTrue("Sqrt of 3 is incorret", calculator.perform("3 sqrt").toString().contains("1.732050807568877"));

		} catch (ApplicationException e) {
            fail("Shouldn't be here.");
		}
    }


    /**
     * Simple push operation test.
     */
    @Test
    public void pushTest() {
        Calculator calculator = new Calculator();
        try {
            calculator.perform("5 3");
            List<BigDecimal> values = calculator.stackValues();
            assertEquals("stack should have two numbers", 2, values.size());
            assertEquals("The first number should be 3", new BigDecimal(3.0), values.get(0));
            assertEquals("The second number should be 5", new BigDecimal(5.0), values.get(1));
        } catch (ApplicationException e) {
            fail("Shouldn't be here.");
        }
    }
}
