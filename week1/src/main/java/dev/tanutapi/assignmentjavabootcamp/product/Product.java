package dev.tanutapi.assignmentjavabootcamp.product;

import dev.tanutapi.assignmentjavabootcamp.productPicture.ProductPicture;
import dev.tanutapi.assignmentjavabootcamp.productVariant.ProductVariant;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue
    private int id;

    private String title;

    private String desc;

    private String brand;

    private float rating;

    private int ratingCnt;

    @OneToMany(mappedBy = "product")
    private List<ProductPicture> productPictures;

    @OneToMany(mappedBy = "product")
    private List<ProductVariant> productVariants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<ProductPicture> getProductPictures() {
        return productPictures;
    }

    public void setProductPictures(List<ProductPicture> productPictures) {
        this.productPictures = productPictures;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }
}
