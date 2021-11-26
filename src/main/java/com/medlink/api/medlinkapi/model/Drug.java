package com.medlink.api.medlinkapi.model;

public class Drug {
    private String drug_Id;
    private String name;
    private String unit;
    private double price;
    private int quantity;

    public Drug() {
    }

    public Drug(String drug_Id, String name, String unit, double price, int quantity) {
        this.drug_Id = drug_Id;
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

    public String getdrug_Id() {
        return drug_Id;
    }

    public void setdrug_Id(String drug_Id) {
        this.drug_Id = drug_Id;
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

