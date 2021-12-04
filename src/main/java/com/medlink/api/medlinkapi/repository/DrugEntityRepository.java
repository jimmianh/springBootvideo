package com.medlink.api.medlinkapi.repository;

import com.medlink.api.medlinkapi.controller.request.InsertRequest;
import com.medlink.api.medlinkapi.controller.request.UpdateDrugRequest;
import com.medlink.api.medlinkapi.exception.ExceptionHandle;
import com.medlink.api.medlinkapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class DrugEntityRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ExceptionHandle exceptionHandle;




    public Connection establishConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/medlinkdemo", "root", "Jimmi_1807");
            System.out.println("Connection made!");
            System.out.println("Schema Name:"+connection.getSchema()+"\n");
        } catch (SQLException sqle) {
            System.err.println("SQL Exception thrown while making connection");
        }
        return connection;
    }

    static class DrugRowMapper implements RowMapper<Drug> {

        @Override
        public Drug mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Drug drug = new Drug();
            drug.setDrugId(resultSet.getInt("drug_id"));
            drug.setDrgStoreId(resultSet.getInt("drg_store_id"));
            drug.setDrgDrugName(resultSet.getString("drg_drug_name"));
            return drug;
        }
    }

    public List<Drug> showAll() {
        return jdbcTemplate.query("SELECT * FROM drg_drug", new DrugRowMapper());
    }

    //find
    public Drug findById(int drugId) throws SQLException {
            Connection connection = establishConnection();
            PreparedStatement ps = connection.prepareStatement("select * from drg_drug where drug_id = ?");
            ps.setInt(1, drugId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                DrgDrug drgDrug = (DrgDrug) jdbcTemplate.queryForObject(
                        "select * from drg_drug where drug_id = ?",
                        new Object[]{drugId},
                        new BeanPropertyRowMapper(DrgDrug.class));

                List<DrgDrugPrice> drgDrugPrice = showAllPrice(drugId);
                return new Drug(drgDrug.getDrugId(), drgDrug.getDrgStoreId(), drgDrug.getDrgDrugName(), drgDrugPrice);
            }
            else {
                return null;
            }
    }



    static class DrgUnitRowMapper implements RowMapper<PriceUpdate> {

        @Override
        public PriceUpdate mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            PriceUpdate drgDrugUnit = new PriceUpdate();
            drgDrugUnit.setDrgUnitId(resultSet.getInt("drug_unit_id"));
            drgDrugUnit.setPrice(resultSet.getBigDecimal("price"));
            return drgDrugUnit;
        }
    }

    public List<PriceUpdate> showAllUnit(int drugId) {
        return jdbcTemplate.query("SELECT * FROM drg_drug_unit where drug_id = ?", new DrgUnitRowMapper(), drugId);
    }

    static class DrgPriceRowMapper implements RowMapper<DrgDrugPrice> {

        @Override
        public DrgDrugPrice mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            DrgDrugPrice drgDrugPrice = new DrgDrugPrice();
            drgDrugPrice.setDrgPriceId(resultSet.getInt("drg_price_id"));
            drgDrugPrice.setPrice(resultSet.getBigDecimal("price"));
            drgDrugPrice.setDrugUnitId(resultSet.getInt("drug_unit_id"));
            drgDrugPrice.setUnitName(resultSet.getString("unit_name"));
            return drgDrugPrice;
        }
    }

    public List<DrgDrugPrice> showAllPrice(int drug_id) {
        return jdbcTemplate.query("SELECT * FROM drg_drug_price where drug_id = ?", new DrgPriceRowMapper(), drug_id);
    }




    static boolean idExistsSQL(Connection connection, String tableName ) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }


    //Insert
    public void insert(InsertRequest insertRequest) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        KeyHolder keyHolderUnit = new GeneratedKeyHolder();
        KeyHolder keyHolderPrice = new GeneratedKeyHolder();

        try {
            //insert vào drug
            String INSERT_DRUG_MESSAGE_SQL
                    = "INSERT INTO drg_drug ( drg_drug_name, drg_store_id,drg_drug_cd,vat_percent) " + "VALUE(?, ?,?,?)";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_DRUG_MESSAGE_SQL, new String[]{"drug_id"});
                ps.setString(1, insertRequest.getDrgDrugName());
                ps.setInt(2, insertRequest.getDrgStoreId());
                ps.setString(3, insertRequest.getDrgDrugCd());
                ps.setInt(4, insertRequest.getVatPercent());
                return ps;
            }, keyHolder);

            Number keyDrug = keyHolder.getKey();
            System.out.println("Newly persisted customer generated id: " + keyDrug.longValue());
            System.out.println("-- loading customer by id --");

            List<UnitInsert> units = new ArrayList<UnitInsert>();
            units = insertRequest.getUnits();

            for(UnitInsert unit : units){
                String INSERT_UNIT_MESSAGE_SQL
                        = "INSERT INTO drg_drug_unit ( unit_name, drg_store_id, drug_id)" + "VALUE( ?, ?, ?)";
                System.out.println(keyDrug);
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_UNIT_MESSAGE_SQL, new String[]{"drug_unit_id"});
                    ps.setString(1, unit.getUnitName());
                    ps.setInt(2, insertRequest.getDrgStoreId());
                    ps.setLong(3, keyDrug.longValue());
                    return ps;
                }, keyHolderUnit);
                Number keyDrgUnitId = keyHolderUnit.getKey();
                System.out.println("Newly persisted customer generated unit_id: " + keyDrgUnitId.longValue());
                System.out.println("-- loading customer by id --");
                //insert vào price
                String INSERT_PRICE_MESSAGE_SQL
                        = "INSERT INTO drg_drug_price (price ,drug_unit_id, drg_store_id, unit_name , drug_id )" + "VALUE(?, ?, ?, ?, ?)";
                System.out.println(keyDrug);
                jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(INSERT_PRICE_MESSAGE_SQL, new String[]{"drg_price_id"});
                    ps.setBigDecimal(1, unit.getPrice());
                    ps.setLong(2,  keyDrgUnitId.longValue());
                    ps.setLong(3, insertRequest.getDrgStoreId());
                    ps.setString(4, unit.getUnitName());
                    ps.setLong(5, keyDrug.longValue());
                    return ps;
                }, keyHolderPrice);

                Number keyDrgPriceId = keyHolderPrice.getKey();

                System.out.println("Newly persisted customer generated unit_id: " + keyDrgPriceId.longValue());
                System.out.println("-- loading customer by id --");
                System.out.println("Thêm mới " + insertRequest.getDrgDrugName() + " thành công");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public String updateById(UpdateDrugRequest updateDrugRequest) throws SQLException {

        if (updateDrugRequest.getDrugId() == 0) {
            System.out.println("bạn đang để trống id");

            return exceptionHandle.updateError(exceptionHandle);
        } else {
            Connection connection = establishConnection();
            PreparedStatement ps = connection.prepareStatement("select * from drg_drug where drug_id = ?");
            ps.setInt(1, updateDrugRequest.getDrugId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                System.out.println("hello");
                jdbcTemplate.update("update drg_drug " + " set drg_drug_name = ? " + " where drug_id = ?",
                        updateDrugRequest.getDrgDrugName(), updateDrugRequest.getDrugId());

                List<PriceUpdate> priceUpdates = new ArrayList<PriceUpdate>();
                priceUpdates = updateDrugRequest.getPriceUpdates();
                for (PriceUpdate price : priceUpdates) {
                    Connection connectionUnit = establishConnection();
                    PreparedStatement psUnit = connectionUnit.prepareStatement("select * from drg_drug_price where drug_id = ?");
                    psUnit.setInt(1, updateDrugRequest.getDrugId());
                    ResultSet resultSet = psUnit.executeQuery();
                    if (resultSet.next()) {
                        Connection connectionDrugUnitId = establishConnection();
                        PreparedStatement psDrugUnitId = connectionDrugUnitId.prepareStatement("SELECT drug_unit_id  FROM drg_drug_price WHERE drug_id = ?");
                        psDrugUnitId.setInt(1, updateDrugRequest.getDrugId());
                        List<PriceUpdate> drugPrices = showAllUnit(updateDrugRequest.getDrugId());
                        drugPrices = updateDrugRequest.getPriceUpdates();
                        for(PriceUpdate unit  : drugPrices){
//                            List[] a = new List[]{drugPrices};
//                            boolean contains = ListStream.of(a).anyMatch(x -> x == unit.getDrgUnitId());
//                                if(contains == true){
                                    for (PriceUpdate drugPrice : drugPrices) {
                                        jdbcTemplate.update("update drg_drug_price set price = ?  where drug_unit_id = ?", price.getPrice(), price.getDrgUnitId());
                                        System.out.println("Sửa thành công tên và giá của thuốc có id là :" + updateDrugRequest.getDrugId());
                                        return "Sửa thành công tên và giá của thuốc có id là :" + updateDrugRequest.getDrugId();
                                    }
//                                }


                        }

                    } else {
                        System.out.println("Id không tồn tại");
                    }
                }
            }
        }return null;
    }






    public String deleteById(int drugId) throws SQLException {
        if (drugId != 0) {
            Connection connection = establishConnection();
            PreparedStatement ps = connection.prepareStatement("select * from drg_drug where drug_id = ?");
            ps.setInt(1, drugId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                jdbcTemplate.update("DELETE FROM drg_drug_price WHERE drug_id=?", drugId);
                jdbcTemplate.update("DELETE FROM drg_drug_unit WHERE drug_id=?", drugId);
                jdbcTemplate.update("DELETE FROM drg_drug WHERE drug_id=?", drugId);
                return "Xóa thành công sản phẩm có Id là:" + drugId;
            } else {
                return exceptionHandle.deleteIdNotExist(exceptionHandle).toString();
            }
        } else{
                return exceptionHandle.deleteIdIsNull(exceptionHandle).toString();
            }
        }
    }