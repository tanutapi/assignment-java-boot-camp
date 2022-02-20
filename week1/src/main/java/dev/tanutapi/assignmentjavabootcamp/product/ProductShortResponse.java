package dev.tanutapi.assignmentjavabootcamp.product;

public class ProductShortResponse {
    private int productId;
    private String title;
    private float rating;
    private int ratingCnt;
    private String picture;

    public ProductShortResponse() {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
