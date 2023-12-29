package com.example.navigation;
public class ImageData {
    private int imageResId;
    private String name;
    private String features;
    private String price;

    public ImageData(int imageResId, String name, String features, String price) {
        this.imageResId = imageResId;
        this.name = name;
        this.features = features;
        this.price = price;
    }

    public int getImageResId() { return imageResId; }
    public String getName() { return name; }
    public String getFeatures() { return features; }
    public String getPrice() { return price; }
}
