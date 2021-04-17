package com.example.homeworkforcakeshop.entity;

import android.graphics.Bitmap;

public class OrderCake {
    private String name;
    private String ifOrder;
    private Bitmap bitmap;
    private int n=0;//代表复选框是否选中，默认为0
    private String user;

    public OrderCake(String name, String ifOrder, Bitmap bitmap,String user) {
        this.name = name;
        this.ifOrder = ifOrder;
        this.bitmap = bitmap;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getIfOrder() {
        return ifOrder;
    }

    public void setIfOrder(String ifOrder) {
        this.ifOrder = ifOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
