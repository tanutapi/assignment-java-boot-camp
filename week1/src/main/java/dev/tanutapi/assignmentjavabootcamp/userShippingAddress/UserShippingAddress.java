package dev.tanutapi.assignmentjavabootcamp.userShippingAddress;

import dev.tanutapi.assignmentjavabootcamp.user.User;

import javax.persistence.*;

@Entity
public class UserShippingAddress {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String address;

    private String district;

    private String province;

    private String postCode;

    private String telephone;

    public UserShippingAddress() {
    }

    public UserShippingAddress(User user, String name, String address, String district, String province, String postCode, String telephone) {
        this.user = user;
        this.name = name;
        this.address = address;
        this.district = district;
        this.province = province;
        this.postCode = postCode;
        this.telephone = telephone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
