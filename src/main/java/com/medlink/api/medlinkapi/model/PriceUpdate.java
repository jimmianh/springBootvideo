package com.medlink.api.medlinkapi.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PriceUpdate {
    int drgUnitId;
    BigDecimal price;
}
