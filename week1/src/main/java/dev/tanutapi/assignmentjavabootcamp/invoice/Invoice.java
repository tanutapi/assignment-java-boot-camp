package dev.tanutapi.assignmentjavabootcamp.invoice;

import dev.tanutapi.assignmentjavabootcamp.order.OrderItem;
import dev.tanutapi.assignmentjavabootcamp.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Invoice {
    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String method;

    private Boolean paid;

    @OneToMany(mappedBy = "invoice")
    private List<OrderItem> orderItems;


}
