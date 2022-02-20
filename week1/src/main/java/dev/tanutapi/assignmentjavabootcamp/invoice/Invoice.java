package dev.tanutapi.assignmentjavabootcamp.invoice;

import dev.tanutapi.assignmentjavabootcamp.orderItem.OrderItem;
import dev.tanutapi.assignmentjavabootcamp.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String method;

    private Boolean paid;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER)
    private List<OrderItem> items;

    private BigDecimal totalPrice;

    public Invoice() {
    }

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

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
