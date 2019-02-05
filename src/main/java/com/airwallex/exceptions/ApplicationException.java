package com.airwallex.exceptions;

/**
 * This is an custom exception to present the invalid operator and position in the input text.
 */
public class ApplicationException extends RuntimeException {
	public ApplicationException(String op) {
        super(String.format("Invalid operation %1$s", op));
    }

    public ApplicationException(String op, String message) {
        super(String.format("Invalid operator %1$s : %2$s)", op, message));
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(String op, int pos) {
        super(String.format("%1$s (position: %2$d)", op, pos));
    }
}