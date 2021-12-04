package com.medlink.api.medlinkapi.model;

import java.math.BigDecimal;
import java.util.List;

public class Drug {
    private int drugId;
    private int drgStoreId;
    private String drgDrugName;
    private List<DrgDrugPrice> units;

    public Drug() {
    }

    public Drug(int drugId, int drgStoreId, String drgDrugName, List<DrgDrugPrice> units) {
        this.drugId = drugId;
        this.drgStoreId = drgStoreId;
        this.drgDrugName = drgDrugName;
        this.units = units;
    }

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

    public List<DrgDrugPrice> getUnits() {
        return units;
    }

    public void setUnits(List<DrgDrugPrice> units) {
        this.units = units;
    }
}

