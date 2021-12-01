package com.medlink.api.medlinkapi.repository;

import com.medlink.api.medlinkapi.controller.DeleteDrug;
import com.medlink.api.medlinkapi.controller.InsertRequest;
import com.medlink.api.medlinkapi.controller.UpdateDrugRequest;
import com.medlink.api.medlinkapi.entity.DrugEntity;
import com.medlink.api.medlinkapi.exception.ExceptionHandle;
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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DrugEntityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    ExceptionHandle exceptionHandle;


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


    //Insert
    public void insert(InsertRequest insertRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolderUnit = new GeneratedKeyHolder();
        KeyHolder keyHolderPrice = new GeneratedKeyHolder();
        try {
            //insert vào drug
            String INSERT_DRUG_MESSAGE_SQL
                    = "INSERT INTO drg_drug ( drg_drug_name, drg_store_id,drg_drug_cd,vat_percent) " + "VALUE(?, ?,?,?)" ;

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_DRUG_MESSAGE_SQL,new String[]{"drug_id"});
                ps.setString(1, insertRequest.getDrg_drug_name());
                ps.setInt(2, insertRequest.getDrg_store_id());
                ps.setString(3, insertRequest.getDrg_drug_cd());
                ps.setInt(4, insertRequest.getVat_percent());
                return ps;
            }, keyHolder);

            Number keyDrug = keyHolder.getKey();
            System.out.println("Newly persisted customer generated id: " + keyDrug.longValue());
            System.out.println("-- loading customer by id --");

            //insert vào unit
            String INSERT_UNIT_MESSAGE_SQL
                    = "INSERT INTO drg_drug_unit ( unit_name,unit_id, drg_store_id, drug_id)" + "VALUE(?, ?, ?, ?)" ;
            System.out.println(keyDrug);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_UNIT_MESSAGE_SQL,new String[]{"drug_unit_id"});
                ps.setString(1, insertRequest.getUnit_name());
                ps.setInt(2, insertRequest.getUnit_id());
                ps.setInt(3, insertRequest.getDrg_store_id());
                ps.setLong(4, keyDrug.longValue());
                return ps;
            }, keyHolderUnit);

            Number keyDrgUnitId = keyHolderUnit.getKey();

            System.out.println("Newly persisted customer generated unit_id: " + keyDrgUnitId.longValue());
            System.out.println("-- loading customer by id --");


            //insert vào price
            String INSERT_PRICE_MESSAGE_SQL
                    = "INSERT INTO drg_drug_price (price ,drug_unit_id, unit_id, drg_store_id, unit_name , drug_id )" + "VALUE(?, ?, ?, ?, ?, ?)" ;
            System.out.println(keyDrug);
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_PRICE_MESSAGE_SQL,new String[]{"drg_price_id"});
                ps.setBigDecimal(1, insertRequest.getPrice());
                ps.setLong(2, keyDrgUnitId.longValue());
                ps.setInt(3, insertRequest.getUnit_id());
                ps.setInt(4, insertRequest.getDrg_store_id());
                ps.setString(5, insertRequest.getUnit_name());
                ps.setLong(6, keyDrug.longValue());
                return ps;
            }, keyHolderPrice);

            Number keyDrgPriceId = keyHolderPrice.getKey();

            System.out.println("Newly persisted customer generated unit_id: " + keyDrgPriceId.longValue());
            System.out.println("-- loading customer by id --");


            System.out.println("Thêm mới " + insertRequest.getDrg_drug_name() + " thành công");
        } catch ( Exception e){
            System.out.println(e);
        }
    }



    public void updateByUnitId(UpdateDrugRequest updateDrugRequest) {
//        if(updateDrugRequest.getDrug_id() == 0){
//                exceptionHandle.updateError();
//        }else{
            System.out.println("hello");
            jdbcTemplate.update("update drg_drug_price " + " set price = ? " + " where drug_unit_id = ?",
                    updateDrugRequest.getPrice(), updateDrugRequest.getDrug_unit_id());
            jdbcTemplate.update("update drg_drug " + " set drg_drug_name = ? " + " where drug_id = ?",
                    updateDrugRequest.getDrg_drug_name(), updateDrugRequest.getDrug_id());
            System.out.println("Sửa thành công tên và giá của thuốc có id là :" + updateDrugRequest.getDrug_id());
        }
//    }


    public int deleteById(DeleteDrug deleteDrug) {
        System.out.println(deleteDrug.getDrug_id());
        return jdbcTemplate.update("DELETE FROM drg_drug WHERE drug_id=?", deleteDrug.getDrug_id());

    }
}

