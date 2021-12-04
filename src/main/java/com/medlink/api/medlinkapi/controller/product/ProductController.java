package com.medlink.api.medlinkapi.controller.product;


import com.medlink.api.medlinkapi.controller.request.InsertRequest;
import com.medlink.api.medlinkapi.controller.request.UpdateDrugRequest;
import com.medlink.api.medlinkapi.model.Drug;
import com.medlink.api.medlinkapi.repository.DrugEntityRepository;
import com.medlink.api.medlinkapi.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private DrugEntityRepository drugEntityRepository;

    @Autowired
    ProductService productService;

    //Show list drug
    @GetMapping
    public List<Drug> getDrugs() {
        return productService.getListProduct();
    }

    //FindByID
    @GetMapping(value = "/{drugId}")
    public ResponseEntity<Drug> getDrug(@PathVariable("drugId") Integer drugId) throws SQLException {
        Drug drug = productService.findProductById(drugId);
        return new ResponseEntity<>(drug, HttpStatus.OK);
    }


    //Create new drug
    @RequestMapping(value ="",
            method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public void createDrug(@RequestBody InsertRequest insertRequest) {

        System.out.println("(Service Side) Creating Drug: " + insertRequest.getDrgDrugName() + "\n" );

        productService.createProduct(insertRequest);
    }


    //update
    @RequestMapping(value= "",
            method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public String updateDrug(@RequestBody @Valid UpdateDrugRequest updateDrugRequest) throws SQLException {
        return productService.updateProduct(updateDrugRequest);
    }

    //Delete by id
    @RequestMapping(value = "/{drugId}",
            method = RequestMethod.DELETE, 
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    public String deleteDrug(@PathVariable int drugId) throws SQLException{
        System.out.
                println("(Service Side) Deleting Drug: " + drugId);
       return productService.deleteProduct(drugId);
    }
}
