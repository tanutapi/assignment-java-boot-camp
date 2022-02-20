package dev.tanutapi.assignmentjavabootcamp.invoice;

import dev.tanutapi.assignmentjavabootcamp.orderItem.OrderItemResponse;

import java.math.BigDecimal;
import java.util.List;

public class InvoiceResponse {
    private int invoiceId;
    private int userId;
    private String paymentMethod;
    private boolean paid;
    private List<OrderItemResponse> items;
    private BigDecimal totalPrice;

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
