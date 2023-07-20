/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kienltpc04639
 */
public class NhanVienDAO extends EOSys<NhanVien, String> {

    String INSERT_SQL = "INSERT INTO NhanVien(MaNV, MatKhau, HoTen, VaiTro) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET HoTen = ?, VaiTro = ? WHERE MaNV = ?";
    String DELETE_TEMPORARY_SQL = "UPDATE NhanVien SET Xoa = 1 WHERE MaNV = ?";
    String RESTORE_SQL = "UPDATE NhanVien SET Xoa = 0 WHERE MaNV = ?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    String DISABLED_SQL = "UPDATE NhanVien SET Xoa = 1 WHERE MaNV = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien WHERE Xoa = 0";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ? AND Xoa = 0";
    String SELECT_HISTORY_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ? AND Xoa = 1";
    String CHANGE_PASSWORD = "UPDATE NhanVien SET MatKhau = ? WHERE MaNV = ?";

    @Override
    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getMaNV(), entity.getMatKhau(), entity.getHoTen(), entity.isVaiTro());
    }

    @Override
    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getHoTen(), entity.isVaiTro(), entity.getMaNV());
    }

    public void restoreStaff(NhanVien entity) {
        JdbcHelper.update(RESTORE_SQL, entity.getMaNV());
    }

    public void changePassword(NhanVien entity) {
        JdbcHelper.update(CHANGE_PASSWORD,
                entity.getMatKhau(), entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    public void deletetTemporary(String id) {
        JdbcHelper.update(DELETE_TEMPORARY_SQL, id);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public NhanVien selectHistoryById(String id) {
        List<NhanVien> list = this.selectBySql(SELECT_HISTORY_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<NhanVien> selectLichSuNhanVien() {
        String SELECT_LICH_SU_NHAN_VIEN = "SELECT * FROM NhanVien WHERE Xoa = 1";
        return this.selectBySql(SELECT_LICH_SU_NHAN_VIEN);
    }

    public List<NhanVien> findStaff(String keyword) {
        String FIND_STAFF = "SELECT * FROM NhanVien WHERE HoTen LIKE ? AND Xoa = 0";
        return this.selectBySql(FIND_STAFF, "%" + keyword + "%");
    }

    public List<NhanVien> findHistoryStaff(String keyword) {
        String FIND_HISTORY_STAFF = "SELECT * FROM NhanVien WHERE HoTen LIKE ? AND Xoa = 1";
        return this.selectBySql(FIND_HISTORY_STAFF, "%" + keyword + "%");
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<NhanVien>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
