package dev.tanutapi.assignmentjavabootcamp.productPicture;

import dev.tanutapi.assignmentjavabootcamp.product.Product;

import javax.persistence.*;

@Entity
public class ProductPicture {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
