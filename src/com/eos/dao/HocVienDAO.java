/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.HocVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HocVienDAO extends EOSys<HocVien, Integer> {

    String INSERT_SQL = "INSERT INTO HocVien(MaKH, MaND) VALUES(?, ?)";
    String UPDATE_SQL = "UPDATE HocVien SET MaKH=?, MaND=? WHERE MaHV=?";
    String DELETE_SQL = "DELETE FROM HocVien WHERE MaHV=?";
    String SELECT_ALL_SQL = "SELECT * FROM HocVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHV=?";

    @Override
    public void insert(HocVien entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaKH(),
                entity.getMaND()
        );
    }

    @Override
    public void update(HocVien entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getMaKH(),
                entity.getMaND(),
                entity.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public HocVien selectById(Integer id) {
        List<HocVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public HocVien selectByFindMaND(int maKH, String maND) {
        String SELECT_FIND_MaND = "SELECT * FROM HocVien WHERE MaKH = ? AND MaND = ?";
        List<HocVien> list = this.selectBySql(SELECT_FIND_MaND, maKH, maND);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HocVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<HocVien> selectByKhoaHoc(Integer makh) {
        String sql = "SELECT * FROM HocVien WHERE MaKH = ?";
        return this.selectBySql(sql, makh);
    }

    @Override
    protected List<HocVien> selectBySql(String sql, Object... args) {
        List<HocVien> list = new ArrayList<HocVien>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HocVien entity = new HocVien();
                entity.setMaHV(rs.getInt("MaHV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setMaND(rs.getString("MaND"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
