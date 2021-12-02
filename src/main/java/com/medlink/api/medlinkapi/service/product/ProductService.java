package com.medlink.api.medlinkapi.service.product;

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
}
