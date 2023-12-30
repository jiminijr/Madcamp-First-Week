package com.example.navigation;

public class RestaurantItem {
    private int outImg;
    private String name;
    private String number;
    private String address;

    public RestaurantItem(int outImg, String name, String number, String address) {
        this.outImg = outImg;
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public int getOutImg() {
        return outImg;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }
}
