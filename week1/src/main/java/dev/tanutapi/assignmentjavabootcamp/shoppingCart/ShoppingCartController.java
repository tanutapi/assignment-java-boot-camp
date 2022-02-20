package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/shoppingcarts/{userId}")
    ShoppingCartResponse getShoppingCart(@PathVariable Integer userId) {
        // TODO verify JWT. Only return the shopping cart of the JWT's subject!
        if (userId != null) {
            return shoppingCartService.getShoppingCart(userId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/shoppingcarts/{userId}")
    ShoppingCartResponse addProductIntoShoppingCart(@PathVariable Integer userId, @RequestBody AddToCartRequest addToCartRequest) {
        // TODO verify JWT. Only modify the shopping cart of the JWT's subject!
        if (addToCartRequest.getProductId() != null && addToCartRequest.getVariant() != null && addToCartRequest.getAmount() != null) {
            if (userId != null) {
                shoppingCartService.addProductToShoppingCart(userId, addToCartRequest.getProductId(), addToCartRequest.getVariant(), addToCartRequest.getAmount());
                return shoppingCartService.getShoppingCart(userId);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/shoppingcarts/{userId}/{productId}/{variantName}")
    ShoppingCartResponse removeProductFromShoppingCart(@PathVariable Integer userId, @PathVariable Integer productId, @PathVariable String variantName) {
        // TODO verify JWT. Only modify the shopping cart of the JWT's subject!
        if (userId != null && productId != null && variantName != null) {
            shoppingCartService.remove(userId, productId, variantName);
            return shoppingCartService.getShoppingCart(userId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
