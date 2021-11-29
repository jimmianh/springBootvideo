package com.medlink.api.medlinkapi.controller;


import com.medlink.api.medlinkapi.model.Drug;
import com.medlink.api.medlinkapi.repository.DrugEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;

@RestController
public class DrugController {
    @Autowired
    private DrugEntityRepository drugEntityRepository;

    //Show list drug
    @RequestMapping(value = "/drug", 
            method = RequestMethod.GET, 
            produces = { MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Drug> getDrugs() {
        List<Drug> list = drugEntityRepository.showAll();
        return list;
    }

    //FindByID
    @GetMapping(value = "/drug/{drug_id}",
            produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Drug getDrug(@RequestBody int drug_id) {
        return drugEntityRepository.findById(drug_id);
    }

//    //Create new drug
//    @RequestMapping(value = "/drug/create",
//            method = RequestMethod.POST,
//            produces = { MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_XML_VALUE })
//    @ResponseBody
//    public int createDrug(@RequestBody Drug drug) {
//
//        System.out.println("(Service Side) Creating Drug: " + drug.getDrug_id() + "," + drug.getDrg_drug_name() + "," + drug.getUnit_name() + "," + drug.getPrice() + "," + drug.getQuantity());
//
//        return drugEntityRepository.insert(drug);
//    }



    @RequestMapping(value = "/drug/update",
            method = RequestMethod.PUT,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public int updateDrug(@RequestBody @Valid UpdateDrugRequest updateDrugRequest) {
        return drugEntityRepository.updateByUnitId(updateDrugRequest);
    }



    //Delete by id
    @RequestMapping(value = "/drug/{drug_id}", 
            method = RequestMethod.DELETE, 
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void deleteDrug(@PathVariable("drug_id") String drug_id) {

        System.out.
                println("(Service Side) Deleting Drug: " + drug_id);

        drugEntityRepository.deleteById(drug_id);
    }
}
