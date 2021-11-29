package com.medlink.api.medlinkapi.repository;

import com.medlink.api.medlinkapi.controller.FindByIdResponse;
import com.medlink.api.medlinkapi.controller.UpdateDrugRequest;
import com.medlink.api.medlinkapi.model.DrgDrug;
import com.medlink.api.medlinkapi.model.DrgDrugPrice;
import com.medlink.api.medlinkapi.model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DrugEntityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;


    static class DrugRowMapper implements RowMapper<Drug> {

        @Override
        public Drug mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Drug drug = new Drug();
            drug.setDrug_id(resultSet.getInt("drug_id"));
            drug.setDrg_store_id(resultSet.getInt("drg_store_id"));
            drug.setDrg_drug_name(resultSet.getString("drg_drug_name"));

            return drug;
        }
    }

    public List<Drug> showAll() {
        return jdbcTemplate.query("SELECT * FROM drg_drug", new DrugRowMapper());
    }

//    public Optional < Drug > findById(long drug_id) {
//
//       return Optional.ofNullable(jdbcTemplate.queryForObject("select * from drg_drug where id=?", new Object[] {
//                       drug_id
//             },
//               new BeanPropertyRowMapper< Drug >(Drug.class)));

//            cach 1: select * from drg_drug_unit where drug_id = ?
////                        select * from drg_drug_price where drug_id = ?
////                        join ...
////            cach 2:
//
//    }
//
//
//    public void unitAndPrice(int drug_id){
//
//
//
//        List [
//                {
//                    unit_name:
//                    price:
//                }]
//
//        return
//        {
//            drug_id: drgDrug.drug_id
//            drg_drug_name:drgDrug.drg_drug_name
//            [
//                    {
//                        unit_name:
//                        price:
//                    },
//                    {
//                        unit_name:
//                        price:
//                    },
//                    {
//                        unit_name:
//                        price:
//                    },
//            ]
//        }
//    }
//    }
    public Drug  findById(  int drug_id) {

            DrgDrug drgDrug = (DrgDrug) jdbcTemplate.queryForObject(
                    "select * from drg_drug where drug_id = ?",
                    new Object[]{drug_id},
                    new BeanPropertyRowMapper(DrgDrug.class));

        List<DrgDrugPrice> drgDrugPrice = jdbcTemplate.query(
                "select *  from drg_drug_price where drug_id=?",
                new BeanPropertyRowMapper(DrgDrugPrice.class), drgDrug.getDrug_id());

        Drug drug = new Drug(drgDrug.getDrug_id(),drgDrug.getDrg_store_id(), drgDrug.getDrg_drug_name(),drgDrugPrice );
        return drug;
    }

//
//    public int insert(Drug drug) {
//        return jdbcTemplate.update("INSERT INTO drg_inv (drug_id,drg_store_id, drg_drug_name, unit_name, price, quantity) " + "VALUE(?,?, ?, ?, ?,?)",
//                drug.getDrug_id(), drug.getDrg_store_id(), drug.getDrg_drug_name(), drug.getUnit_name(), drug.getPrice(), drug.getQuantity());
//    }

    //xong
    public int updateByUnitId(UpdateDrugRequest updateDrugRequest) {
        System.out.println("hello");
        jdbcTemplate.update("update drg_drug_price " + " set price = ? " + " where drug_unit_id = ?",
                updateDrugRequest.getPrice(), updateDrugRequest.getDrug_unit_id());
        jdbcTemplate.update("update drg_drug " + " set drg_drug_name = ? " + " where drug_id = ?",
                updateDrugRequest.getDrg_drug_name(), updateDrugRequest.getDrug_id());
        return 1;
    }

    //xong
    public int deleteById(String drugId) {
        return jdbcTemplate.update("DELETE FROM drg_inv WHERE drug_id=?", drugId);
    }
}

