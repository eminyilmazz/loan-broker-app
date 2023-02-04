package com.eminyilmazz.loanbrokerapp.exception;

public class DuplicateTcknException extends RuntimeException {

    private static final String GENERIC_MESSAGE = "Provided TCKN already exists.\nCannot accept duplicate TCKN.\n";

    public DuplicateTcknException() {
        super(GENERIC_MESSAGE);
    }
}
