package dev.tanutapi.assignmentjavabootcamp.user;

import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddressResponse;

public class UserResponse {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private UserShippingAddressResponse shippingAddress;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserShippingAddressResponse getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(UserShippingAddressResponse shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
