package dev.tanutapi.assignmentjavabootcamp.user;

import dev.tanutapi.assignmentjavabootcamp.userShippingAddress.UserShippingAddress;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;

    private String username;

    private String salt;

    private String hashedPassword;

    private String firstName;

    private String lastName;

    @OneToOne
    private UserShippingAddress userShippingAddress;

    public UserShippingAddress getUserShippingAddress() {
        return userShippingAddress;
    }

    public void setUserShippingAddress(UserShippingAddress userShippingAddress) {
        this.userShippingAddress = userShippingAddress;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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
}
