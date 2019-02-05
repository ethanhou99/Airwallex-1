package com.airwallex.operations;

import java.math.BigDecimal;
import java.util.Deque;

import com.airwallex.exceptions.ApplicationException;


/**
 * Services pertaining to the operations related to the calculator.
 */
@FunctionalInterface
public interface Operation {
    /**
     * Perform the operation on the given stack
     * @param stack
     * @throws ApplicationException if operation can be perform successfully.
     */
	void perform(Deque<BigDecimal> stack)  throws ApplicationException;
}