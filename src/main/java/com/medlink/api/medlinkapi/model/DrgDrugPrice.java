package com.medlink.api.medlinkapi.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class DrgDrugPrice {
    private int drgPriceId;
    private int drugUnitId;
    private String unitName;
    private BigDecimal price;
}
