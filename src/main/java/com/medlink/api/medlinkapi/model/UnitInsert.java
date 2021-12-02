package com.medlink.api.medlinkapi.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UnitInsert {
    private String unit_name;
    private BigDecimal price;
}
