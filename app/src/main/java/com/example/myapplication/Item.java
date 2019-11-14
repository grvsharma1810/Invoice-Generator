package com.example.myapplication;

public class Item {
    private String name;
    private String quantity;
    private String amount;
    private String rate;

    public Item(String name, String quantity, String amount, String rate) {
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRate() {
        return rate;
    }

    public String getAmount() {
        return amount;
    }



}
