package com.airwallex.operations;

import java.math.BigDecimal;
import java.util.Deque;

import com.airwallex.exceptions.ApplicationException;

@FunctionalInterface
/**
 * Services pertaining to the operations related to the calculator with UNDO support .
 */
public interface OperationWithUndo {

    /**
     * Perform the operation on the given stack
     * @param stack data stack
     * @param undoStack undo stack
     * @throws ApplicationException if operation can be perform successfully.
     */
	void perform(Deque<BigDecimal> stack, Deque<UndoModel> undoStack) throws ApplicationException;
}
