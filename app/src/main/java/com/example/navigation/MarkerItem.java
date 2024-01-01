package com.example.navigation;

public class MarkerItem {
    private double lat;
    private double lon;
    private String name;
    private String number;
    private String address;
    private String info;
    private int foodimg;
    private String link;

    public MarkerItem(double lat, double lon, String name, String number, String address, String info, int foodimg, String link){
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.number = number;
        this.address = address;
        this.info = info;
        this.foodimg = foodimg;
        this.link = link;
    }

    public double getLat(){
        return lat;
    }
    public double getLon(){
        return lon;
    }

    public String getName(){
        return name;
    }
    public String getNumber(){
        return number;
    }
    public String getAddress(){
        return address;
    }
    public String getInfo(){return info;}
    public int getFoodimg(){ return foodimg; }
    public String getLink(){ return link; }
}
