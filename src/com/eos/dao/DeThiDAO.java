/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.DeThi;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class DeThiDAO extends EOSys<DeThi, Integer> {

    String INSERT_SQL = "INSERT INTO DeThi(TGBatDau,NgayTao, MaHV) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE DeThi SET TGKetThuc = ?, Diem = ? WHERE MaDeThi = ? AND MaHV = ?";
    String DELETE_SQL = "DELETE FROM DeThi WHERE MaDeThi = ?";
    String SELECT_ALL_SQL = "SELECT * FROM DeThi";
    String SELECT_BY_ID_SQL = "SELECT * FROM DeThi WHERE MaDeThi = ?";

    @Override
    public void insert(DeThi entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getTGBatDau(), entity.getNgayTao(), entity.getMaHV());
    }

    @Override
    public void update(DeThi entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTGKetThuc(), entity.getDiem(), entity.getMaDeThi(), entity.getMaHV());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public DeThi selectById(Integer id) {
        List<DeThi> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public DeThi selectFindMaDeThi(String TGBatDau, int maHV) {
        String SELECT_FIND_MADETHI = "SELECT * FROM DeThi WHERE TGBatDau = ? AND MaHV = ?";
        List<DeThi> list = this.selectBySql(SELECT_FIND_MADETHI, TGBatDau, maHV);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DeThi> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<DeThi> selectViewAnswer(String maND) {
        String sql = "SELECT * FROM DeThi WHERE MaDeThi IN (SELECT MaDeThi FROM DeThi WHERE MaHV IN (SELECT MaHV FROM NguoiDung WHERE MAND = ?)) AND MaHV IN (SELECT MaHV FROM HocVien WHERE MaND = ?)";
        return this.selectBySql(sql, maND, maND);
    }
    public DeThi selectFindDeThi(int maDeThi, String maND) {
    	String sql = "SELECT * FROM DeThi WHERE MaDeThi = ? AND MaDeThi IN (SELECT MaDeThi FROM DeThi WHERE MaHV IN (SELECT MaHV FROM NguoiDung WHERE MAND = ?)) AND MaHV IN (SELECT MaHV FROM HocVien WHERE MaND = ?)";
    	List<DeThi> list =  this.selectBySql(sql, maDeThi, maND, maND);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<DeThi> selectViewResult(int maKH) {
        String sql = "SELECT * FROM DeThi WHERE MaHV IN (SELECT MaHV FROM HocVien WHERE MaKH = ?)";
        return this.selectBySql(sql, maKH);
    }

    public List<DeThi> findNameID(int maKH, String hoTen) {
        String sql = "SELECT * FROM DeThi WHERE MaHV IN (SELECT MaHV FROM HocVien WHERE MaKH = ? AND MaND IN (SELECT MaND FROM NguoiDung WHERE HoTen LIKE ?))";
        return this.selectBySql(sql, maKH, "%" + hoTen + "%");
    }

    @Override
    protected List<DeThi> selectBySql(String sql, Object... args) {
        List<DeThi> list = new ArrayList<DeThi>();
        try {
            java.util.Date date1;
            java.util.Date date2;
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                DeThi entity = new DeThi();
                entity.setMaDeThi(rs.getInt("MaDeThi"));
                if (rs.getDate("TGKetThuc") == null) {
                    entity.setTGKetThuc(null);
                } else {
                    Timestamp timestamp1 = rs.getTimestamp("TGBatDau");
                    date1 = new java.util.Date(timestamp1.getTime());
                    entity.setTGBatDau(date1);
                    Timestamp timestamp2 = rs.getTimestamp("TGKetThuc");
                    date2 = new java.util.Date(timestamp2.getTime());
                    entity.setTGKetThuc(date2);
                }
                entity.setDiem(rs.getDouble("Diem"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setMaHV(rs.getInt("MaHV"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
