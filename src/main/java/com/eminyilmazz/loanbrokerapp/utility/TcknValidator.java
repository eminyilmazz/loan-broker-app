package com.eminyilmazz.loanbrokerapp.utility;

import com.eminyilmazz.loanbrokerapp.exception.IllegalTcknException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TcknValidator {
    private TcknValidator() {
    }

    private static final String TCKN_REGEX = "^[1-9]\\d{10}$";

    public static void validate(Long tckn) {
        String tcknString = String.valueOf(tckn);
        Pattern pattern = Pattern.compile(TCKN_REGEX);
        Matcher matcher = pattern.matcher(tcknString);
        if (!matcher.matches()) {
            throw new IllegalTcknException("TCKN needs to be 11 digits and can only contain only numbers.");
        }
    }
}
