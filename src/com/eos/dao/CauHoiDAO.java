/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.CauHoi;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class CauHoiDAO extends EOSys<CauHoi, Integer> {

    String INSERT_SQL = "INSERT INTO CauHoi(MaChuong, TenCH, PA1, PA2, PA3, PA4, DapAn) VALUES(?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE CauHoi SET MaChuong = ?, TenCH = ?, PA1 = ?, PA2 = ?, PA3 = ?, PA4 = ?, DapAn = ? WHERE MaCH = ?";
    String DELETE_SQL = "DELETE FROM CauHoi WHERE MaCH = ?";

    @Override
    public void insert(CauHoi entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaChuong(), entity.getTenCH(), entity.getPA1(), entity.getPA2(), entity.getPA3(), entity.getPA4(),
                entity.getDapAn());
    }

    @Override
    public void update(CauHoi entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaChuong(), entity.getTenCH(), entity.getPA1(), entity.getPA2(), entity.getPA3(), entity.getPA4(),
                entity.getDapAn(), entity.getMaCH());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public CauHoi selectById(Integer id) {
        String SELECT_BY_ID = "SELECT * FROM CauHoi WHERE MaCH = ?";
        List<CauHoi> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<CauHoi> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM CauHoi";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<CauHoi> selectByChuong(int maChuong) {
        String SELECT_BY_CHUONG_SQL = "SELECT * FROM CauHoi WHERE MaChuong = ?";
        return this.selectBySql(SELECT_BY_CHUONG_SQL, maChuong);
    }

    public List<CauHoi> selectAll(String tenCH, int maChuong) {
        String SELECT_ALL_QUESTION = "SELECT * FROM CauHoi WHERE TenCH LIKE ? AND MaChuong = ?";
        return this.selectBySql(SELECT_ALL_QUESTION, "%" + tenCH + "%", maChuong);
    }

    public List<CauHoi> selectQuestion(int soCH, int maChuong) {
        String sql = "SELECT TOP " + soCH + " * FROM CauHoi WHERE MaChuong = " + maChuong + " ORDER BY NEWID()";
        return this.selectBySql(sql);
    }

    public List<CauHoi> selectQuestionExam(int maDeThi) {
        String sql = "SELECT CauHoi.* FROM CauHoi INNER JOIN ChiTietDeThi ON ChiTietDeThi.MaCH = CauHoi.MaCH WHERE MaDeThi = ?";
        return this.selectBySql(sql, maDeThi);
    }

    public int selectExistsInsertQuestion(String question, int maChuong) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CauHoi WHERE MaChuong = ? AND TenCH = N'" + question + "'";
        ResultSet rs = JdbcHelper.query(sql, maChuong);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectExistsUpdateQuestion(String question, int maChuong, int maCH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM CauHoi WHERE MaChuong = ? AND MaCH != ? AND TenCH = N'" + question + "'";
        ResultSet rs = JdbcHelper.query(sql, maChuong, maCH);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    protected List<CauHoi> selectBySql(String sql, Object... args) {
        List<CauHoi> list = new ArrayList<CauHoi>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                CauHoi entity = new CauHoi();
                entity.setMaCH(rs.getInt("MaCH"));
                entity.setMaChuong(rs.getInt("MaChuong"));
                entity.setTenCH(rs.getString("TenCH"));
                entity.setPA1(rs.getString("PA1"));
                entity.setPA2(rs.getString("PA2"));
                entity.setPA3(rs.getString("PA3"));
                entity.setPA4(rs.getString("PA4"));
                entity.setDapAn(rs.getString("DapAn"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
