package com.medlink.api.medlinkapi.controller;

import com.medlink.api.medlinkapi.model.DrgDrugPrice;
import com.medlink.api.medlinkapi.model.UnitInsert;
import lombok.Data;

import java.util.List;


@Data
public class InsertRequest {
    private int vat_percent;
    private String drg_drug_cd;
    private int drg_store_id;
    private String drg_drug_name;
    private List<UnitInsert> units;
}
