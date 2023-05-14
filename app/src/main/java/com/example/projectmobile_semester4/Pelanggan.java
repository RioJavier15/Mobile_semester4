package com.example.projectmobile_semester4;

import java.io.Serializable;

public class Pelanggan implements Serializable {
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String status;
    private String address;
    private String subscribeDate;
    private int productId;
    private String productName;
    private String speed;
    private String price;
    private String bandwidth;

    public Pelanggan(int id, String name, String username, String email, String password, String phoneNumber, String status, String address, String subscribeDate, int productId, String productName, String speed, String price, String bandwidth) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.address = address;
        this.subscribeDate = subscribeDate;
        this.productId = productId;
        this.productName = productName;
        this.speed = speed;
        this.price = price;
        this.bandwidth = bandwidth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getSubscribeDate() {
        return subscribeDate;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getSpeed() {
        return speed;
    }

    public String getPrice() {
        return price;
    }

    public String getBandwidth() {
        return bandwidth;
    }
}
