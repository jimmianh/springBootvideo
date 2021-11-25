package com.medlink.api.medlinkapi.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medlink.api.medlinkapi.model.Drug;
import org.springframework.stereotype.Repository;

@Repository
public class DrugDAO {

    private static final Map<String, Drug> drugMap = new HashMap<String, Drug>();

    static {
        initDrugs();
    }

    private static void initDrugs() {
        Drug drug1 = new Drug("E01","rockket","hop", 10.22,20);
        Drug drug2 = new Drug("E02", "thuoc la","goi", 1111.3,20);
        Drug drug3 = new Drug("E03", "thuoc lao","chai", 1451.5443,20);

        drugMap.put(drug1.getDrugID(), drug1);
        drugMap.put(drug2.getDrugID(), drug2);
        drugMap.put(drug3.getDrugID(), drug3);
    }

    public Drug getDrug(String drugNo) {
        return drugMap.get(drugNo);
    }

    public Drug addDrug(Drug drug) {
        drugMap.put(drug.getDrugID(), drug);
        return drug;
    }

    public Drug updateDrug(Drug drug) {
        drugMap.put(drug.getDrugID(), drug);
        return drug;
    }

    public void deleteDrug(String drugNo) {
        drugMap.remove(drugNo);
    }

    public List<Drug> getAllDrugs() {
        Collection<Drug> c = drugMap.values();
        List<Drug> list = new ArrayList<Drug>();
        list.addAll(c);
        return list;
    }
}
