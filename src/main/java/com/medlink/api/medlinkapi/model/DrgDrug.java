package com.medlink.api.medlinkapi.model;

import java.math.BigDecimal;

public class DrgDrug {
    private int drugId;
    private int drgStoreId;
    private String drgDrugName;
    private String unitName;
    private BigDecimal price;

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public int getDrgStoreId() {
        return drgStoreId;
    }

    public void setDrgStoreId(int drgStoreId) {
        this.drgStoreId = drgStoreId;
    }

    public String getDrgDrugName() {
        return drgDrugName;
    }

    public void setDrgDrugName(String drgDrugName) {
        this.drgDrugName = drgDrugName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DrgDrug() {
    }

    public DrgDrug(int drugId, int drgStoreId, String drgDrugName, String unitName, BigDecimal price) {
        this.drugId = drugId;
        this.drgStoreId = drgStoreId;
        this.drgDrugName = drgDrugName;
        this.unitName = unitName;
        this.price = price;
    }
}
