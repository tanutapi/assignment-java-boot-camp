package dev.tanutapi.assignmentjavabootcamp.shoppingCart;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartProductResponse {
    private int productId;
    private String title;
    private String desc;
    private String brand;
    private float rating;
    private int ratingCnt;
    private String variant;
    private BigDecimal unitPrice;
    private List<String> pictures;
    private int amount;
    private BigDecimal totalPrice;

    public ShoppingCartProductResponse() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getRatingCnt() {
        return ratingCnt;
    }

    public void setRatingCnt(int ratingCnt) {
        this.ratingCnt = ratingCnt;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<String> getPictures() {
        return pictures;
    }

    public void setPictures(List<String> pictures) {
        this.pictures = pictures;
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
