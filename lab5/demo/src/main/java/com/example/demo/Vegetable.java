package com.example.demo;

import lombok.Data;

@Data // Sử dụng Lombok để tự động tạo getter, setter
public class Vegetable {
    private int vegetableID;
    private String vegetableName;
    private String unit;
    private int amount;
    private double price;

    public Vegetable() {
    }

    public Vegetable(int vegetableID, String vegetableName, String unit, int amount, double price) {
        this.vegetableID = vegetableID;
        this.vegetableName = vegetableName;
        this.unit = unit;
        this.amount = amount;
        this.price = price;
    }
}