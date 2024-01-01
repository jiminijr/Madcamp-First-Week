package com.example.navigation;


import java.io.Serializable;
import java.util.List;


public class RestaurantItem implements Serializable {
    private int outImg;
    private String name;
    private String number;
    private String address;

    // New fields added based on the JSON structure
    private int foodImg;
    private String info;
    private String tag;

    private List<Menu> menuList;

    private boolean isExpanded;

    // Updated constructor to include new fields
    public RestaurantItem(int outImgResId, int foodImgResId, String name, String address, String number, String info, String tag, List<Menu> menuList){
        this.outImg = outImgResId;
        this.foodImg = foodImgResId;
        this.name = name;
        this.number = number;
        this.address = address;
        this.info = info;
        this.tag = tag;
        this.isExpanded = false;
        this.menuList = menuList;
    }

    // isExpanded의 getter와 setter
    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
    // Existing getters
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

    public int getFoodImg() {
        return foodImg;
    }

    public String getInfo() {
        return info;
    }

    public String getTag() {
        return tag;
    }

    // 메뉴 목록에 대한 getter
    public List<Menu> getMenuList() {
        return menuList;
    }
}