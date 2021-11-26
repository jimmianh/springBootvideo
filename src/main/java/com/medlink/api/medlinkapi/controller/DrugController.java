package com.medlink.api.medlinkapi.controller;

import com.medlink.api.medlinkapi.model.Drug;
import com.medlink.api.medlinkapi.repository.DrugEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    @GetMapping(value = "/drug/{drug_id}")
    @ResponseBody
    public Optional<Drug>  getDrug(@PathVariable int drug_id) {
        Optional<Drug> drug = drugEntityRepository.findById(drug_id);
        return drug;
    }

    //Create new drug
    @RequestMapping(value = "/drug/create",
            method = RequestMethod.POST, 
            produces = { MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public int createDrug(@RequestBody Drug drug) {

        System.out.println("(Service Side) Creating Drug: " + drug.getDrug_id() + "," + drug.getDrg_drug_name() + "," + drug.getUnit_name() + "," + drug.getPrice() + "," + drug.getQuantity());

        return drugEntityRepository.insert(drug);
    }

//    //update by id
//    @RequestMapping(value = "/drug/update/{drug_id}",
//            method = RequestMethod.PUT,
//            produces = { MediaType.APPLICATION_JSON_VALUE,
//                    MediaType.APPLICATION_XML_VALUE })
//    @ResponseBody
//    public int updateDrug(@PathVariable("drug_id")  String drg_drug_name, String unit_name, BigDecimal price, BigDecimal quantity, int drug_id) {
//
//        System.out.println("(Service Side) Editing Drug: " + drug_id);
//
//        return drugEntityRepository.updateById(drg_drug_name, unit_name,price,quantity,drug_id);
//    }

    //Delete by id
    @RequestMapping(value = "/drug/{drug_id}", 
            method = RequestMethod.DELETE, 
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void deleteDrug(@PathVariable("drug_id") String drug_id) {

        System.out.println("(Service Side) Deleting Drug: " + drug_id);

        drugEntityRepository.deleteById(drug_id);
    }
}
