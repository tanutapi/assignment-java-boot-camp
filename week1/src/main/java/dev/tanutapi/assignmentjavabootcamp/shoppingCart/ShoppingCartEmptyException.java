package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Shopping cart is empty")
public class ShoppingCartEmptyException extends RuntimeException {
    public ShoppingCartEmptyException(String s) {
        super(s);
    }
}
