package com.airwallex.calculator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import com.airwallex.exceptions.ApplicationException;
import com.airwallex.operations.ArithmeticOperationWithUndo;
import com.airwallex.operations.OperationWithUndo;
import com.airwallex.operations.UndoModel;

public class Calculator {
    /**
     * Available operations
     * <p>
     */
    private static Map<String, OperationWithUndo> SUPPORTED_OPERATIONS = ArithmeticOperationWithUndo.OPERATIONS;

    /**
     * Represent the stack where data and operation results are held.
     */
    private Deque<BigDecimal> stack = new ArrayDeque<>();

    /**
     * Represent the UNDO stack where undo data is held.
     */
    private Deque<UndoModel> undo = new ArrayDeque<>();

    /**
     * regular expression used to display stack values.
     */
    private static final String REGEX_PADDING_ZERO = "\\.*0*$";

    /**
     * Parse the input text and perform the relevant operation as add, subtract
     * undo, clear etc.
     *
     * @param input
     *            the input text
     * @throws ApplicationException
     *             anything wrong when parse and handle the text.
     */
    public BigDecimal perform(String input) throws ApplicationException {
        process(input.split(" "));
        return this.stack.peek();
    }

    /**
     * print all values in the stack
     */
    public void print() {
        System.out.print("Stack:");
        stack.descendingIterator().forEachRemaining(value -> {
            System.out.print(" ");
            System.out.print(String.format("%.10g", value).replaceAll(REGEX_PADDING_ZERO, ""));
        });
        System.out.print("\n");
    }

    /**
     * Return the unmodifiable current values of stack
     *
     * @return list of current values.
     */
    public List<BigDecimal> stackValues() {
        return Collections.unmodifiableList(new ArrayList<>(stack));
    }

    /**
     * Parse the input string tokens and perform the relevant operation as add,
     * subtract undo, clear etc.
     *
     * @param tokens
     *            Input string tokens
     * @throws ApplicationException
     *             anything wrong when parse and handle the text.
     */
    private void process(String... tokens) throws ApplicationException {
        int position = 0;
        for (String token : tokens) {
            try {
                position++;
            operationFor(token).perform(stack, undo);
            }
            catch(ApplicationException exception) {
                throw new ApplicationException(exception.getMessage(), position);
            }
        }
    }

    /**
     * Find the operation related to the word.
     *
     * @param word
     *            Input string, whose operation needs to be located.
     * @throws ApplicationException
     *             if not available operation can be located.
     */
    private OperationWithUndo operationFor(String word) throws ApplicationException {
        if (SUPPORTED_OPERATIONS.get(word) != null) {
            return SUPPORTED_OPERATIONS.get(word);
        }
        else {
            try {
                BigDecimal value = new BigDecimal(word);
                return ((dataStack, undoStack) -> {
                    ArithmeticOperationWithUndo.pushItem(dataStack, undoStack, value);
                });
            } catch (NumberFormatException e) {
                throw new ApplicationException(word);
            }
        }
    }
}