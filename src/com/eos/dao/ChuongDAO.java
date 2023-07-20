/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.Chuong;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
//    int maChuong;
//    String tenChuong;
//    int maKH;
public class ChuongDAO extends EOSys<Chuong, Integer> {

    String INSERT_SQL = "INSERT INTO Chuong(TenChuong, MaKH) VALUES(?, ?)";
    String UPDATE_SQL = "UPDATE Chuong SET TenChuong = ?, MaKH = ? WHERE MaChuong = ?";
    String DELETE_SQL = "DELETE FROM Chuong WHERE MaChuong = ?";
    String SELECT_ALL_SQL = "SELECT * FROM Chuong";
    String SELECT_BY_ID_SQL = "SELECT * FROM Chuong WHERE MaChuong = ?";
    String SELECT_BY_NAME_SQL = "SELECT * FROM Chuong WHERE TenChuong = ?";

    @Override
    public void insert(Chuong entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTenChuong(), entity.getMaKH());
    }

    @Override
    public void update(Chuong entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenChuong(), entity.getMaKH(), entity.getMaChuong());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public Chuong selectById(Integer id) {
        List<Chuong> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public Chuong selectByTenChuong(String tenChuong) {
        List<Chuong> list = this.selectBySql(SELECT_BY_NAME_SQL, tenChuong);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public int selectExistsInsertChapter(String chapter, int maKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Chuong WHERE MaKH = ? AND TenChuong = N'" + chapter + "'";
        ResultSet rs = JdbcHelper.query(sql, maKH);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectExistsUpdateChapter(String chapter, int maKH, int maChuong) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Chuong WHERE MaKH = ? AND MaChuong != ? AND TenChuong = N'" + chapter + "'";
        ResultSet rs = JdbcHelper.query(sql, maKH, maChuong);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectCountChapter(int maKH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Chuong WHERE MaKH = ?";
        ResultSet rs = JdbcHelper.query(sql, maKH);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public Chuong selectById(int maChuong) {
        String SELECT = "SELECT * FROM Chuong WHERE MaChuong = ?";
        List<Chuong> list = this.selectBySql(SELECT, maChuong);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<Chuong> selectByKhoaHoc(Integer maKH) {
        String sql = "SELECT * FROM Chuong WHERE MaKH = ?";
        return this.selectBySql(sql, maKH);
    }

    @Override
    public List<Chuong> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<Chuong> selectAll(String tenChuong) {
        String SELECT_ALL_SQL = "SELECT * FROM Chuong WHERE TenChuong LIKE N'%" + tenChuong + "%'";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    protected List<Chuong> selectBySql(String sql, Object... args) {
        List<Chuong> list = new ArrayList<Chuong>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                Chuong entity = new Chuong();
                entity.setMaChuong(rs.getInt("MaChuong"));
                entity.setTenChuong(rs.getString("TenChuong"));
                entity.setMaKH(rs.getInt("MaKH"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
