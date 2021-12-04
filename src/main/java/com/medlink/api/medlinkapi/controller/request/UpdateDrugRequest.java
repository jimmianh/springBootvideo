package com.medlink.api.medlinkapi.controller.request;

import com.medlink.api.medlinkapi.model.PriceUpdate;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDrugRequest {
    private int drugId;
    private String drgDrugName;
    private List<PriceUpdate> priceUpdates;
}
