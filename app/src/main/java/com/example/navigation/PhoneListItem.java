package com.example.navigation;

public class PhoneListItem {
    private String name;
    private String num;
    public PhoneListItem(String name, String num){
        this.name = name;
        this.num = num;
    }
    public String getName(){
        return this.name;
    }
    public String getNum(){
        return num;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setNum(String num) {
        this.num = num;
    }
}
