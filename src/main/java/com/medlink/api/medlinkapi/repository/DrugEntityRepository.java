package com.medlink.api.medlinkapi.repository;

import com.medlink.api.medlinkapi.controller.InsertRequest;
import com.medlink.api.medlinkapi.controller.UpdateDrugRequest;
import com.medlink.api.medlinkapi.model.DrgDrug;
import com.medlink.api.medlinkapi.model.DrgDrugPrice;
import com.medlink.api.medlinkapi.model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

// thêm
    public String insert(InsertRequest insertRequest) {
        try {
            String INSERT_MESSAGE_SQL
                    = "INSERT INTO drg_drug ( drg_drug_name, drg_store_id,drg_drug_cd,vat_percent) " + "VALUE(?, ?,?,?)" ;
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_MESSAGE_SQL,new String[]{"drug_id"});
                ps.setString(1, insertRequest.getDrg_drug_name());
                ps.setInt(2, insertRequest.getDrg_store_id());
                ps.setString(3, insertRequest.getDrg_drug_cd());
                ps.setInt(4, insertRequest.getVat_percent());
                return ps;
            }, keyHolder);

            Number key = keyHolder.getKey();
            System.out.println("Newly persisted customer generated id: " + key.longValue());
            System.out.println("-- loading customer by id --");
            jdbcTemplate.update("INSERT INTO drg_drug_unit ( unit_name = ? , drug_id = key.longValue())",
                    insertRequest.getUnit_name());
            jdbcTemplate.update("INSERT INTO drg_drug_price (price = ?)",
                    insertRequest.getPrice());

        } catch ( Exception e){
            System.out.println(e);
        }
        return "Thêm mới" + insertRequest.getDrg_drug_name() + " thành công";


    }



    //xong
    public String updateByUnitId(UpdateDrugRequest updateDrugRequest) {
        System.out.println("hello");
        jdbcTemplate.update("update drg_drug_price " + " set price = ? " + " where drug_unit_id = ?",
                updateDrugRequest.getPrice(), updateDrugRequest.getDrug_unit_id());
        jdbcTemplate.update("update drg_drug " + " set drg_drug_name = ? " + " where drug_id = ?",
                updateDrugRequest.getDrg_drug_name(), updateDrugRequest.getDrug_id());
        return "Sửa thành công tên và giá của thuốc có id là :" + updateDrugRequest.getDrug_id();
    }

    //xong
    public int deleteById(String drugId) {
        return jdbcTemplate.update("DELETE FROM drg_drug WHERE drug_id=?", drugId);
    }
}

