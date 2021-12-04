package com.medlink.api.medlinkapi.controller.request;

import com.medlink.api.medlinkapi.model.UnitInsert;
import lombok.Data;

import java.util.List;


@Data
public class InsertRequest {
    private int vatPercent;
    private String drgDrugCd;
    private int drgStoreId;
    private String drgDrugName;
    private List<UnitInsert> units;
}
