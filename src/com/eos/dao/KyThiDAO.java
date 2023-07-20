/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.CauHoi;
import com.eos.model.KyThi;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class KyThiDAO extends EOSys<KyThi, Integer> {

    String INSERT_SQL = "INSERT INTO KyThi(MaKH, TenKT, MaNV, TGLamBai, TGMoDe, TGDongDe, TongSoCau, MatKhau, SoLanLam, MaChuong) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE KyThi SET MaKH = ?, TenKT = ?, MaNV = ?, TGLamBai = ?, TGMoDe = ?, TGDongDe = ?, TongSoCau = ?, MatKhau = ?, SoLanLam = ?, MaChuong = ? WHERE MaKyThi = ?";
    String DELETE_SQL = "DELETE FROM KyThi WHERE MaKyThi = ?";
    String SELECT_ALL_SQL = "SELECT * FROM KyThi";
    String SELECT_JOIN_EXAM_SQL = "SELECT * FROM KyThi WHERE MaKH IN (SELECT MaKH FROM HocVien WHERE MaND = ?) AND SoLanLam > ( SELECT COUNT(*) FROM DeThi WHERE MaHV IN (SELECT MaHV FROM HocVien WHERE MaND = ?)) AND (TGMoDe <= GETDATE() AND TGDongDe >= GETDATE())";
    String SELECT_BY_ID_SQL = "SELECT * FROM KyThi WHERE MaKyThi = ?";

    @Override
    public void insert(KyThi entity) {
        JdbcHelper.update(INSERT_SQL, entity.getMaKH(), entity.getTenKT(), entity.getMaNV(), entity.getTGLamBai(), entity.getTGMoDe(),
                entity.getTGDongDe(), entity.getTongSoCau(), entity.getMatKhau(), entity.getSoLanLam(), entity.getMaChuong());
    }

    @Override
    public void update(KyThi entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getMaKH(), entity.getTenKT(), entity.getMaNV(), entity.getTGLamBai(), entity.getTGMoDe(),
                entity.getTGDongDe(), entity.getTongSoCau(), entity.getMatKhau(), entity.getSoLanLam(), entity.getMaChuong(), entity.getMaKyThi());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    @Override
    public KyThi selectById(Integer id) {
        List<KyThi> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public KyThi selectByDeThi(int maDeThi) {
        String sql = "SELECT * FROM KyThi WHERE MaKyThi IN (SELECT MaKyThi FROM ChiTietKyThi WHERE MaDeThi = ?)";
        List<KyThi> list = this.selectBySql(sql, maDeThi);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KyThi> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<KyThi> selectAllByID(int id) {
        return this.selectBySql(SELECT_BY_ID_SQL, id);
    }

    public List<KyThi> selectByKhoaHoc(int makh) {
        String sql = "SELECT * FROM KyThi WHERE MaKH = ?";
        return this.selectBySql(sql, makh);
    }

    public List<KyThi> selectByKhoaHoc(int makh, int machuong) {
        String sql = "SELECT * FROM KyThi WHERE MaKH = ? AND MaChuong = ?";
        return this.selectBySql(sql, makh, machuong);
    }
    
    public List<KyThi> selectByChuong(int maChuong) {
        String SELECT_BY_CHUONG_SQL = "SELECT * FROM KyThi WHERE MaChuong = ?";
        return this.selectBySql(SELECT_BY_CHUONG_SQL, maChuong);
    }

    public List<KyThi> selectAllJoinExam(String maND) {
        return this.selectBySql(SELECT_JOIN_EXAM_SQL, maND, maND);
    }

    @Override
    protected List<KyThi> selectBySql(String sql, Object... args) {
        List<KyThi> list = new ArrayList<KyThi>();
        try {
            java.util.Date date1;
            java.util.Date date2;
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KyThi entity = new KyThi();
                entity.setMaKyThi(rs.getInt("MaKyThi"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setTenKT(rs.getString("TenKT"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTGLamBai(rs.getInt("TGLamBai"));
                Timestamp timestamp2 = rs.getTimestamp("TGMoDe");
                date2 = new java.util.Date(timestamp2.getTime());
                entity.setTGMoDe(date2);
                Timestamp timestamp1 = rs.getTimestamp("TGDongDe");
                date1 = new java.util.Date(timestamp1.getTime());
                entity.setTGDongDe(date1);
                entity.setTongSoCau(rs.getInt("TongSoCau"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setSoLanLam(rs.getInt("SoLanLam"));
                entity.setMaChuong(rs.getInt("MaChuong"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
