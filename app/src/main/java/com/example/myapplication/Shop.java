package com.example.myapplication;

public class Shop {
    private String orgName;
    private String address;

    public Shop(String orgName, String address) {
        this.orgName = orgName;
        this.address = address;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getAddress() {
        return address;
    }
}
