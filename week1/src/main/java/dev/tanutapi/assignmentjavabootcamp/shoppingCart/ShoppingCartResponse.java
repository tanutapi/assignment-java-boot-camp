package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import java.util.List;

public class ShoppingCartResponse {
    private int userId;
    private List<ShoppingCartProductResponse> products;

    public ShoppingCartResponse() {
    }

    public ShoppingCartResponse(int userId, List<ShoppingCartProductResponse> products) {
        this.userId = userId;
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ShoppingCartProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ShoppingCartProductResponse> products) {
        this.products = products;
    }
}

