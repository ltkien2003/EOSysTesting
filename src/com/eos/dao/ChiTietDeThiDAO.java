/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.ChiTietDeThi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChiTietDeThiDAO extends EOSys<ChiTietDeThi, Integer> {

    String INSERT_SQL = "INSERT INTO ChiTietDeThi(MaDeThi, MaCH) VALUES(?, ?)";
    String UPDATE_SQL = "UPDATE ChiTietDeThi SET PALuaChon = ? WHERE IDCH = ?";
    String DELETE_SQL = "DELETE FROM ChiTietDeThi WHERE MaDeThi = ?";
    String SELECT_ALL_SQL = "SELECT * FROM ChiTietDeThi";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietDeThi WHERE IDCH = ?";

    @Override
    public void insert(ChiTietDeThi entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaDeThi(), entity.getMaCH());
    }

    @Override
    public void update(ChiTietDeThi entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getPALuaChon(), entity.getIDCH());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public ChiTietDeThi selectById(Integer id) {
        List<ChiTietDeThi> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public int selectCountQuestion(int maDeThi) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ChiTietDeThi WHERE MaDeThi = ?";
        ResultSet rs = JdbcHelper.query(sql, maDeThi);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectCountCorrectQuestion(int maDeThi) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ChiTietDeThi INNER JOIN CauHoi ON ChiTietDeThi.MaCH = CauHoi.MaCH WHERE MaDeThi = ? AND ChiTietDeThi.PALuaChon = CauHoi.DapAn";
        ResultSet rs = JdbcHelper.query(sql, maDeThi);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public ChiTietDeThi selectFindIDCH(int maDeThi, int maCH) {
        String SELECT_FIND_IDCH = "SELECT * FROM ChiTietDeThi WHERE MaDeThi = ? AND MaCH = ?";
        List<ChiTietDeThi> list = this.selectBySql(SELECT_FIND_IDCH, maDeThi, maCH);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChiTietDeThi> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<ChiTietDeThi> selectChiTietDeThi(int maDeThi) {
        String SELECT_BY_MA_DE_THI = "SELECT * FROM ChiTietDeThi WHERE MaDeThi = ?";
        return this.selectBySql(SELECT_BY_MA_DE_THI, maDeThi);
    }

    @Override
    protected List<ChiTietDeThi> selectBySql(String sql, Object... args) {
        List<ChiTietDeThi> list = new ArrayList<ChiTietDeThi>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChiTietDeThi entity = new ChiTietDeThi();
                entity.setIDCH(rs.getInt("IDCH"));
                entity.setMaDeThi(rs.getInt("MaDeThi"));
                entity.setMaCH(rs.getInt("MaCH"));
                entity.setPALuaChon(rs.getString("PALuaChon"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
