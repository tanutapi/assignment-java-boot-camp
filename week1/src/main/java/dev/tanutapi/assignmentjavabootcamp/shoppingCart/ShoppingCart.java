package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariant;
import dev.tanutapi.assignmentjavabootcamp.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ShoppingCart {
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    private int amount;

    private BigDecimal totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
