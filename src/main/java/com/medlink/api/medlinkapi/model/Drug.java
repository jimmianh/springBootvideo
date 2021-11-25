package com.medlink.api.medlinkapi.model;

public class Drug {
    private String drugID;
    private String name;
    private String unit;
    private double price;
    private int quantity;

    public Drug() {
    }

    public Drug(String drugID, String name, String unit, double price, int quantity) {
        this.drugID = drugID;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}

