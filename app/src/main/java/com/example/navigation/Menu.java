package com.example.navigation;

public class Menu {
    private String menuName;
    private int menuImgResId;
    private String menuPrice;

    public Menu(String menuName, int menuImgResId, String menuPrice) {
        this.menuName = menuName;
        this.menuImgResId = menuImgResId;
        this.menuPrice = menuPrice;
    }

    // Getter 메소드들
    public String getMenuName() {
        return menuName;
    }

    public int getMenuImgResId() {
        return menuImgResId;
    }

    public String getMenuPrice() {
        return menuPrice;
    }
}
