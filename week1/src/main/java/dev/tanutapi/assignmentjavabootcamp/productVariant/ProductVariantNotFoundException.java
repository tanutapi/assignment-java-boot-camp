package dev.tanutapi.assignmentjavabootcamp.productVariant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product variant was not found")
public class ProductVariantNotFoundException extends RuntimeException {
    public ProductVariantNotFoundException(String s) {
        super(s);
    }
}
