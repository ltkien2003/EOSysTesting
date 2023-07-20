/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.ChiTietKyThi;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class ChiTietKyThiDAO extends EOSys<ChiTietKyThi, Integer> {

    String INSERT_SQL = "INSERT INTO ChiTietKyThi(MaKyThi, MaDeThi) VALUES(?, ?)";
    String UPDATE_SQL = "UPDATE ChiTietKyThi SET MaDeThi = ? WHERE MaKyThi = ?";
    String DELETE_SQL = "DELETE FROM ChiTietKyThi WHERE MaKyThi = ?";
    String SELECT_ALL_SQL = "SELECT * FROM ChiTietKyThi";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietKyThi WHERE MaKyThi=?";    
    @Override
    public void insert(ChiTietKyThi entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaKyThi(), entity.getMaDeThi());
    }

    @Override
    public void update(ChiTietKyThi entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMaDeThi(), entity.getMaKyThi());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public ChiTietKyThi selectById(Integer id) {
        List<ChiTietKyThi> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChiTietKyThi> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<ChiTietKyThi> selectBySql(String sql, Object... args) {
        List<ChiTietKyThi> list = new ArrayList<ChiTietKyThi>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChiTietKyThi entity = new ChiTietKyThi();
                entity.setMaKyThi(rs.getInt("MaKyThi"));
                entity.setMaDeThi(rs.getInt("MaDeThi"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
