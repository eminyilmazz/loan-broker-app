package com.eminyilmazz.loanbrokerapp.utility;

import com.eminyilmazz.loanbrokerapp.exception.IllegalTcknException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TcknValidatorTest {

    @Test
    void validate_withValidTckn_shouldPass() {
        long tckn = 12345678910L;

        TcknValidator.validate(tckn);
    }

    @Test
    void validate_withInvalidTckn_shouldThrowException() {
        long tckn = 1234567890L;

        Exception exception = assertThrows(IllegalTcknException.class, () -> TcknValidator.validate(tckn));
        assertEquals(IllegalTcknException.class, exception.getClass());
        assertEquals("TCKN needs to be 11 digits and can only contain only numbers.", exception.getMessage());
    }
}