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

    //Create new drug
    @RequestMapping(value = "/drug/create",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public String createDrug(@RequestBody InsertRequest insertRequest) {

        System.out.println("(Service Side) Creating Drug: " + insertRequest.getDrg_drug_name()+ "\n" + insertRequest.getUnit_name() + "\n" + insertRequest.getPrice() );

        return drugEntityRepository.insert(insertRequest);
    }


    //update
    @RequestMapping(value = "/drug/update",
            method = RequestMethod.PUT,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public String updateDrug(@RequestBody @Valid UpdateDrugRequest updateDrugRequest) {
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
