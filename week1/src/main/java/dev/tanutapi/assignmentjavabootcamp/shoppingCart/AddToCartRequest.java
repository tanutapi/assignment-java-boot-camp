package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

public class AddToCartRequest {
    private Integer productId;
    private String variant;
    private Integer amount;

    public AddToCartRequest() {
    }

    public AddToCartRequest(Integer productId, String variant, Integer amount) {
        this.productId = productId;
        this.variant = variant;
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
