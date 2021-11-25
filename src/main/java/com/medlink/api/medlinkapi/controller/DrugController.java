package com.medlink.api.medlinkapi.controller;

import com.medlink.api.medlinkapi.dao.DrugDAO;
import com.medlink.api.medlinkapi.model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DrugController {
    @Autowired
    private DrugDAO drugDAO;

    //Show list drug
    @RequestMapping(value = "/drug", 
            method = RequestMethod.GET, 
            produces = { MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public List<Drug> getDrugs() {
        List<Drug> list = drugDAO.getAllDrugs();
        return list;
    }

    //FindByID
    @RequestMapping(value = "/drug/{drugID}", 
            method = RequestMethod.GET, 
            produces = { MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Drug getDrug(@PathVariable("drugID") String drugID) {
        return drugDAO.getDrug(drugID);
    }

    //Create new drug
    @RequestMapping(value = "/drug/create",
            method = RequestMethod.POST, 
            produces = { MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Drug createDrug(@RequestBody Drug drug) {

        System.out.println("(Service Side) Creating Drug: " + drug.getDrugID());

        return drugDAO.addDrug(drug);
    }

    //update by id
    @RequestMapping(value = "/drug/update/{drugID}",
            method = RequestMethod.PUT, 
            produces = { MediaType.APPLICATION_JSON_VALUE, 
                    MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public Drug updateDrug(@RequestBody Drug drug) {

        System.out.println("(Service Side) Editing Drug: " + drug.getDrugID());

        return drugDAO.updateDrug(drug);
    }

    //Delete by id
    @RequestMapping(value = "/drug/{drugID}", 
            method = RequestMethod.DELETE, 
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public void deleteDrug(@PathVariable("drugID") String drugID) {

        System.out.println("(Service Side) Deleting Drug: " + drugID);

        drugDAO.deleteDrug(drugID);
    }
}
