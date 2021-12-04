package com.medlink.api.medlinkapi.service.product;

import com.medlink.api.medlinkapi.controller.request.InsertRequest;
import com.medlink.api.medlinkapi.controller.request.UpdateDrugRequest;
import com.medlink.api.medlinkapi.model.Drug;
import com.medlink.api.medlinkapi.repository.DrugEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    DrugEntityRepository drugEntityRepository;

    public List<Drug> getListProduct(){
        return drugEntityRepository.showAll();
    }

    public Drug findProductById(int drugId) throws SQLException {
        return drugEntityRepository.findById(drugId);
    }
    public void createProduct(InsertRequest insertRequest){
        drugEntityRepository.insert(insertRequest);
    }

    public String updateProduct(UpdateDrugRequest updateDrugRequest) throws SQLException {
        return drugEntityRepository.updateById(updateDrugRequest);
    }

    public String deleteProduct(int drugId) throws SQLException {
        return drugEntityRepository.deleteById(drugId);
    }
}
