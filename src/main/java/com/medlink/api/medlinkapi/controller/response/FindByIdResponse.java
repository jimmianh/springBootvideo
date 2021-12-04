package com.medlink.api.medlinkapi.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FindByIdResponse {
    private String drgDrugName;
    private int  drugId;
    private String unitName;
    private BigDecimal price;
    private int drgUnitId;
    private int drgPriceId ;
}
