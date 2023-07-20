/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.BaiHoc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BaiHocDAO extends EOSys<BaiHoc, Integer> {

    String INSERT_SQL = "INSERT INTO BaiHoc(TenBH, MaChuong, Link) VALUES(?, ?, ?)";
    String UPDATE_SQL = "UPDATE BaiHoc SET TenBH = ?, MaChuong = ?, Link = ? WHERE MaBH = ?";
    String DELETE_SQL = "DELETE FROM BaiHoc WHERE MaBH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM BaiHoc";
    String SELECT_BY_ID = "SELECT * FROM BaiHoc WHERE MaBH = ?";
    String SELECT_BY_BaiHoc = "SELECT * FROM BaiHoc WHERE MaBH = ?";
    String SELECT_BY_TenBaiHoc = "SELECT * FROM BaiHoc WHERE TenBH = ?";

    @Override
    public void insert(BaiHoc entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTenBH(), entity.getMaChuong(), entity.getLink());
    }

    @Override
    public void update(BaiHoc entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenBH(), entity.getMaChuong(), entity.getLink(), entity.getMaBH());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public BaiHoc selectById(Integer id) {
        List<BaiHoc> list = this.selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<BaiHoc> selectByBaiHoc(Integer machuong) {
        String sql = "SELECT * FROM BaiHoc WHERE MaChuong = ?";
        return this.selectBySql(sql, machuong);
    }

    public List<BaiHoc> selectByMaBaiHoc(Integer maBH) {
        String sql = "SELECT * FROM BaiHoc WHERE MaBH = ?";
        return this.selectBySql(sql, maBH);
    }

    @Override
    public List<BaiHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<BaiHoc> selectAll(String tenBH) {
        String SELECT_ALL_SQL = "SELECT * FROM BaiHoc WHERE TenBH LIKE N'%" + tenBH + "%'";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public int selectExistsInsertLesson(String lesson, int maChuong) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BaiHoc WHERE MaChuong = ? AND TenBH = N'" + lesson + "'";
        ResultSet rs = JdbcHelper.query(sql, maChuong);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectExistsUpdateLesson(String lesson, int maChuong, int maBH) throws SQLException {
        String sql = "SELECT COUNT(*) FROM BaiHoc WHERE MaChuong = ? AND MaBH != ? AND TenBH = N'" + lesson + "'";
        ResultSet rs = JdbcHelper.query(sql, maChuong, maBH);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public List<BaiHoc> selectByKhoaHoc(Integer maChuong) {
        String sql = "SELECT * FROM BaiHoc WHERE MaChuong = ?";
        return this.selectBySql(sql, maChuong);
    }

    public List<BaiHoc> selectByMaBH(Integer maBH) {
        String sql = "SELECT * FROM BaiHoc WHERE MaBH = ?";
        return this.selectBySql(sql, maBH);
    }

    @Override
    protected List<BaiHoc> selectBySql(String sql, Object... args) {
        List<BaiHoc> list = new ArrayList<BaiHoc>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                BaiHoc entity = new BaiHoc();
                entity.setMaBH(rs.getInt("MaBH"));
                entity.setTenBH(rs.getString("TenBH"));
                entity.setMaChuong(rs.getInt("MaChuong"));
                entity.setLink(rs.getString("Link"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
