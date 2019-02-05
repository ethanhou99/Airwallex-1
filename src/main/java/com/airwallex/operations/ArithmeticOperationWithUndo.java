package com.airwallex.operations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.airwallex.exceptions.ApplicationException;

public class ArithmeticOperationWithUndo {

	public static final OperationWithUndo ADD = ( (stack, undo) -> {
		minimumOperandsCheck(stack, "+", 2);
		Iterator<BigDecimal> iterator = stack.iterator();
		BigDecimal secondValue = iterator.next();
		BigDecimal firstValue = iterator.next();
		ArithmeticOperations.ADD.perform(stack);
		undo.push(new UndoModel(firstValue, secondValue));
	});

	public static final OperationWithUndo SUBTRACT = ( (stack, undo) -> {
		Iterator<BigDecimal> iterator = stack.iterator();
		minimumOperandsCheck(stack, "-", 2);
		BigDecimal secondValue = iterator.next();
		BigDecimal firstValue = iterator.next();
		ArithmeticOperations.SUBTRACT.perform(stack);
		undo.push(new UndoModel(firstValue, secondValue));
	});

	public static final OperationWithUndo MULTIPLY = ( (stack, undo) -> {
		Iterator<BigDecimal> iterator = stack.iterator();
		minimumOperandsCheck(stack, "*", 2);
		BigDecimal secondValue = iterator.next();
		BigDecimal firstValue = iterator.next();
		ArithmeticOperations.MULTIPLY.perform(stack);
		undo.push(new UndoModel(firstValue, secondValue));
	});

	public static final OperationWithUndo DIVIDE = ( (stack, undo) -> {
		Iterator<BigDecimal> iterator = stack.iterator();
		minimumOperandsCheck(stack, "/", 2);
		BigDecimal secondValue = iterator.next();
		BigDecimal firstValue = iterator.next();
		ArithmeticOperations.DIVIDE.perform(stack);
		undo.push(new UndoModel(firstValue, secondValue));
	});

	public static final OperationWithUndo CLEAR_WITH_UNDO = ( (stack, undo) -> {
	    if (stack.size() == 0) {
	        throw new ApplicationException("uclear");
	    }
	    List<BigDecimal> undoList = new ArrayList<>();
	    stack.descendingIterator().forEachRemaining(undoList::add);
		undo.push(new UndoModel(undoList));
		ArithmeticOperations.CLEAR.perform(stack);
	});

	   public static final OperationWithUndo CLEAR = ( (stack, undo) -> {
	        if (stack.size() == 0) {
	            throw new ApplicationException("clear");
	        }
	        undo.clear();
	        ArithmeticOperations.CLEAR.perform(stack);
	    });

	public static final OperationWithUndo SQRT = ( (stack, undo) -> {
		BigDecimal value = stack.peek();
		ArithmeticOperations.SQRT.perform(stack);
		undo.push(new UndoModel(value));
	});

	public static final OperationWithUndo UNDO = ( (stack, undo) -> {
		if (stack.size() > 0) {
		  stack.pop();
		  if (undo.size() > 0) {
			undo.pop().data().forEach(item -> stack.push(item));
		  }
		} else {
			  if (undo.size() > 0) {
					undo.pop().data().forEach(item -> stack.push(item));
			  }
			  else {
				  throw new ApplicationException("undo");
			  }
		}
	});

	/**
	 * private constructor as instance is not needed for this class.
	 */
	private ArithmeticOperationWithUndo() {
	}

	public static void pushItem(Deque<BigDecimal> stack, Deque<UndoModel> undo, BigDecimal value) {
		stack.push(value);
	}

	public static final Map<String, OperationWithUndo > OPERATIONS  =
	Collections.unmodifiableMap(Stream.of(
            new SimpleEntry<>("+",     ADD),
            new SimpleEntry<>("-",     SUBTRACT),
            new SimpleEntry<>("*",     MULTIPLY),
            new SimpleEntry<>("/",     DIVIDE),
            new SimpleEntry<>("sqrt",  SQRT),
            new SimpleEntry<>("clear", CLEAR),
            new SimpleEntry<>("uclear", CLEAR_WITH_UNDO),
            new SimpleEntry<>("undo",  UNDO))
			.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));

	/**
	 * Validates if minimum operands are present in the stack to perform the given operation.
	 * @param stack
	 * @param operation
	 * @param minOperands
	 * @throws ApplicationException
	 */
	private static void minimumOperandsCheck(Deque<BigDecimal> stack, String operation, int minOperands) throws ApplicationException {
		if (stack.size() < minOperands) {
			throw new ApplicationException(operation, "insufficient parameters");
		}
	}


	/**
	 * Perform the arithmetic operation such as + - / and *
	 * No validation on operands is performed here.
	 */
	private static class ArithmeticOperations {

	    public static final Operation ADD = stack -> stack.push(stack.pop().add(stack.pop()));

	    public static final Operation SUBTRACT = (stack -> {
	        BigDecimal secondOperand = stack.pop();
	        stack.push(stack.pop().subtract(secondOperand));
	    });

	    public static final Operation MULTIPLY = stack -> stack.push(stack.pop().multiply(stack.pop()));

	    public static final Operation DIVIDE = (stack -> {
	        BigDecimal secondOperand = stack.pop();
	        stack.push(stack.pop().divide(secondOperand, MathContext.DECIMAL128));
	    });

	    public static final Operation CLEAR = stack -> stack.clear();

	    public static final Operation SQRT = (stack -> {
	        int scale = 50;
	        BigDecimal a = stack.pop();

	            BigDecimal x = BigDecimal.valueOf(Math.sqrt(a.doubleValue()));
	            if(scale < 17){
	                 x = x.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
	                 stack.push(x);
	            } else {

	                BigDecimal b2 = new BigDecimal(2);
	                for(int tempScale = 16; tempScale < scale; tempScale *= 2){
	                    x = x.subtract(
	                            x.multiply(x).subtract(a).divide(
	                            x.multiply(b2), scale, BigDecimal.ROUND_HALF_EVEN));
	                }
	                 stack.push(x);
	            }
	    });
	}
}