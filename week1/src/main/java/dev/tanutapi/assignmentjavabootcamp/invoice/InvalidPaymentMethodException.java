package dev.tanutapi.assignmentjavabootcamp.invoice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Provided payment method is not support")
public class InvalidPaymentMethodException extends RuntimeException {
    public InvalidPaymentMethodException(String s) {
        super(s);
    }
}
