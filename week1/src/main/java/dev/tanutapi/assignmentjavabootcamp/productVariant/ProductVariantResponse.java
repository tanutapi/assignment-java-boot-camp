package dev.tanutapi.assignmentjavabootcamp.productVariant;

import java.math.BigDecimal;

public class ProductVariantResponse {
    private String name;
    private BigDecimal price;

    public ProductVariantResponse() {
    }

    public ProductVariantResponse(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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
