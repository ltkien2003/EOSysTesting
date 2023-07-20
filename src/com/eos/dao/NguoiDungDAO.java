/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.NguoiDung;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kienltpc04639
 */
public class NguoiDungDAO extends EOSys<NguoiDung, String> {

    String INSERT_SQL = "INSERT INTO NguoiDung(MaND, MatKhau, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV, NgayDK) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NguoiDung SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DienThoai = ?, Email = ?, GhiChu = ?, MaNV = ? WHERE MaND = ?";
    String UPDATE_PERSONAL_SQL = "UPDATE NguoiDung SET HoTen = ?, NgaySinh = ?, GioiTinh = ?, DienThoai = ?, Email = ? WHERE MaND = ?";
    String RESTORE_DATA_SQL = "UPDATE NguoiDung SET Xoa = 0 WHERE MaND = ?";
    String DELETE_SQL = "DELETE FROM NguoiDung WHERE MaND = ?";
    String DELETE_TEMPORARY_SQL = "UPDATE NguoiDung SET Xoa = 1 WHERE MaND = ?";
    String SELECT_ALL_SQL = "SELECT * FROM NguoiDung WHERE Xoa = 0";
    String SELECT_ALL_HISTORY_USER_SQL = "SELECT * FROM NguoiDung WHERE Xoa = 1";
    String SELECT_BY_ID_SQL = "SELECT * FROM NguoiDung WHERE MaND = ?";
    String SELECT_BY_HISTORY_ID_SQL = "SELECT * FROM NguoiDung WHERE MaND = ? AND Xoa = 1";
    String CHANGE_PASSWORD = "UPDATE NguoiDung SET MatKhau = ? WHERE MaND = ?";
    String SELECT_NOT_IN_COURSE = "SELECT * FROM NguoiDung WHERE Xoa = 0 AND HoTen LIKE ? AND MaND NOT IN(SELECT MaND FROM HocVien WHERE MaKH=?)";
    @Override
    public void insert(NguoiDung entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaND(), entity.getMatKhau(), entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(),
                entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getNgayDK());
    }

    @Override
    public void update(NguoiDung entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(),
                entity.getDienThoai(), entity.getEmail(), entity.getGhiChu(), entity.getMaNV(), entity.getMaND());
    }
    public void updatePersonal(NguoiDung entity) {
        JdbcHelper.update(UPDATE_PERSONAL_SQL, entity.getHoTen(), entity.getNgaySinh(), entity.isGioiTinh(),
                entity.getDienThoai(), entity.getEmail(), entity.getMaND());
    }

    public void restoreData(NguoiDung entity) {
        JdbcHelper.update(RESTORE_DATA_SQL, entity.getMaND());
    }

    public void changePassword(NguoiDung entity) {
        JdbcHelper.update(CHANGE_PASSWORD,
                entity.getMatKhau(), entity.getMaND());
    }

    @Override
    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    public void deleteTemporary(String id) {
        JdbcHelper.update(DELETE_TEMPORARY_SQL, id);
    }

    @Override
    public NguoiDung selectById(String id) {
        List<NguoiDung> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public NguoiDung selectHistoryById(String id) {
        List<NguoiDung> list = this.selectBySql(SELECT_BY_HISTORY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public NguoiDung findNameByIDHV(int maHV) {
        String FIND_NAME_BY_IDHV = "SELECT * FROM NguoiDung WHERE MaND IN (SELECT MaND FROM HocVien WHERE MaHV = ?)";
        List<NguoiDung> list = this.selectBySql(FIND_NAME_BY_IDHV, maHV);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NguoiDung> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<NguoiDung> selectAllHistoryUser() {
        return this.selectBySql(SELECT_ALL_HISTORY_USER_SQL);
    }

    public List<NguoiDung> findUser(String keyword) {
        String FIND_USER = "SELECT * FROM NguoiDung WHERE HoTen LIKE ? AND Xoa = 0";
        return this.selectBySql(FIND_USER, "%" + keyword + "%");
    }

    public List<NguoiDung> findHistoryUser(String keyword) {
        String FIND_HISTORY_USER = "SELECT * FROM NguoiDung WHERE HoTen LIKE ? AND Xoa = 1";
        return this.selectBySql(FIND_HISTORY_USER, "%" + keyword + "%");
    }

    public List<NguoiDung> selectNotInCourse(int makh, String keyword) {
        return this.selectBySql(SELECT_NOT_IN_COURSE, "%" + keyword + "%", makh);
    }

    public int selectExistsInsertEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE Email = ?";
        ResultSet rs = JdbcHelper.query(sql, email);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectExistsInsertPhone(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE DienThoai = ?";
        ResultSet rs = JdbcHelper.query(sql, phone);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectExistsUpdateEmail(String email, String maND) throws SQLException {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE Email = ? AND MaND NOT IN (SELECT MaND FROM NguoiDung WHERE MaND = ?)";
        ResultSet rs = JdbcHelper.query(sql, email, maND);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int selectExistsUpdatePhone(String phone, String maND) throws SQLException {
        String sql = "SELECT COUNT(*) FROM NguoiDung WHERE DienThoai = ? AND MaND NOT IN (SELECT MaND FROM NguoiDung WHERE MaND = ?)";
        ResultSet rs = JdbcHelper.query(sql, phone, maND);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    @Override
    protected List<NguoiDung> selectBySql(String sql, Object... args) {
        List<NguoiDung> list = new ArrayList<NguoiDung>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NguoiDung entity = new NguoiDung();
                entity.setMaND(rs.getString("MaND"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setEmail(rs.getString("Email"));
                entity.setDienThoai(rs.getString("DienThoai"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayDK(rs.getDate("NgayDK"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
