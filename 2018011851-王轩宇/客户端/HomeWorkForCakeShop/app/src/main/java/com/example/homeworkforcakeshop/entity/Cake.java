package com.example.homeworkforcakeshop.entity;

import android.graphics.Bitmap;

public class Cake{
    private String size;
    private String name;
    private String price;
    private Bitmap photo;
    private int n=0;//代表复选框是否选中，默认为0

    public Cake(String name,String size, String price, Bitmap photo) {
        this.size = size;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
