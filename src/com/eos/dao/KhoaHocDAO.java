/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.dao;

import com.eos.helper.JdbcHelper;
import com.eos.model.KhoaHoc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhoaHocDAO extends EOSys<KhoaHoc, Integer> {

    String INSERT_SQL = "INSERT INTO KhoaHoc(TenKH, HocPhi, NgayKG, GhiChu, MaNV, NgayTao, TongSoChuong, Hinh) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE KhoaHoc SET TenKH = ?, HocPhi = ?, NgayKG = ?, GhiChu = ?, MaNV = ?, NgayTao = ?, TongSoChuong = ?, Hinh = ? WHERE MaKH = ?";
    String RESTORE_COURSE_SQL = "UPDATE KhoaHoc SET Xoa = 0 WHERE MaKH = ?";
    String DELETE_TEMPORARY_SQL = "UPDATE KhoaHoc SET Xoa = 1 WHERE MaKH = ?";
    String DELETE_SQL = "DELETE FROM KhoaHoc WHERE MaKH = ?";
    String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc WHERE Xoa = 0";
    String SELECT_HISTORY_COURSE = "SELECT * FROM KhoaHoc WHERE Xoa = 1";
    String SELECT_BY_NOT_JOINED = "SELECT * FROM KhoaHoc WHERE MaKH NOT IN (SELECT MaKH FROM HocVien WHERE MaND = ?) AND NgayKG <= GETDATE() AND Xoa = 0";
    String SELECT_BY_JOINED = "SELECT * FROM KhoaHoc WHERE MaKH IN (SELECT MaKH FROM HocVien WHERE MaND = ?) AND Xoa = 0";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH = ? AND Xoa = 0";
    String SELECT_BY_NAME_SQL = "SELECT * FROM KhoaHoc WHERE TenKH = ? AND Xoa = 0"; //BẢO thêm vào
    String SELECT_BY_ALL_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH = ?"; 
    String SELECT_HISTORY_BY_ID_SQL = "SELECT * FROM KhoaHoc WHERE MaKH = ? AND Xoa = 1";

    @Override
    public void insert(KhoaHoc entity) {
        JdbcHelper.update(INSERT_SQL, entity.getTenKH(), entity.getHocPhi(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(),
                entity.getNgayTao(), entity.getTongSoChuong(), entity.getHinh());
    }

    @Override
    public void update(KhoaHoc entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getTenKH(), entity.getHocPhi(), entity.getNgayKG(), entity.getGhiChu(), entity.getMaNV(),
                entity.getNgayTao(), entity.getTongSoChuong(), entity.getHinh(), entity.getMaKH());
    }

    public void restoreCourse(KhoaHoc entity) {
        JdbcHelper.update(RESTORE_COURSE_SQL, entity.getMaKH());
    }

    @Override
    public void delete(Integer id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    public void deleteTemporary(Integer id) {
        JdbcHelper.update(DELETE_TEMPORARY_SQL, id);
    }

    @Override
    public KhoaHoc selectById(Integer id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BY_ID_SQL, id);
//        System.out.println(list);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public KhoaHoc selectByName(String name) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BY_NAME_SQL, name);
//        System.out.println(list);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public KhoaHoc selectByAllId(Integer id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_BY_ALL_ID_SQL, id); //BẢO thêm vào
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public KhoaHoc selectByHistoryId(Integer id) {
        List<KhoaHoc> list = this.selectBySql(SELECT_HISTORY_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhoaHoc> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }
    public List<KhoaHoc> selectCourseAll() {
        String selectCourseAll = "SELECT * FROM KhoaHoc WHERE MaKH IN (SELECT MaKH FROM Chuong)";
        return this.selectBySql(selectCourseAll);
    }

    public List<KhoaHoc> selectAllLichSuKhoaHoc() {
        return this.selectBySql(SELECT_HISTORY_COURSE);
    }

    public List<KhoaHoc> selectByNotJoined(String maND) {
        return this.selectBySql(SELECT_BY_NOT_JOINED, maND);

    }

    public List<KhoaHoc> selectByJoined(String maND) {
        return this.selectBySql(SELECT_BY_JOINED, maND);

    }

    public List<KhoaHoc> findCourse(String keyword) {
        String SELECT_NOT_IN_COURSE = "SELECT * FROM KhoaHoc WHERE TenKH LIKE ? AND Xoa = 0";
        return this.selectBySql(SELECT_NOT_IN_COURSE, "%" + keyword + "%");
    }
    public List<KhoaHoc> findHistoryCourse(String keyword) {
        String SELECT_NOT_IN_COURSE = "SELECT * FROM KhoaHoc WHERE TenKH LIKE ? AND Xoa = 1";
        return this.selectBySql(SELECT_NOT_IN_COURSE, "%" + keyword + "%");
    }

    @Override
    protected List<KhoaHoc> selectBySql(String sql, Object... args) {
        List<KhoaHoc> list = new ArrayList<KhoaHoc>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhoaHoc entity = new KhoaHoc();
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setHocPhi(rs.getDouble("HocPhi"));
                entity.setNgayKG(rs.getDate("NgayKG"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setTongSoChuong(rs.getInt("TongSoChuong"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
