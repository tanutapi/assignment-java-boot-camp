package dev.tanutapi.assignmentjavabootcamp.productVariant;

import dev.tanutapi.assignmentjavabootcamp.product.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class ProductVariant {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;

    private BigDecimal price;

    public ProductVariant() {
    }

    public ProductVariant(Product product, String name, BigDecimal price) {
        this.product = product;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
