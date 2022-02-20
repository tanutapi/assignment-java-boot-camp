package dev.tanutapi.assignmentjavabootcamp.userShippingAddress;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No shipping address was found")
public class ShippingAddressNotFoundException extends RuntimeException {
    public ShippingAddressNotFoundException(String s) {
        super(s);
    }
}
