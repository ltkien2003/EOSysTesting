/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class KyThi {
    int maKyThi;
    int maKH;
    String tenKT;
    String maNV;
    int TGLamBai;
    Date TGMoDe;
    Date TGDongDe;
    int tongSoCau;
    String matKhau;
    int soLanLam;
    int maChuong;
    @Override
    public String toString() {
        return this.tenKT;
    }
    public int getMaKyThi() {
        return maKyThi;
    }

    public void setMaKyThi(int maKyThi) {
        this.maKyThi = maKyThi;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public String getTenKT() {
        return tenKT;
    }

    public void setTenKT(String tenKT) {
        this.tenKT = tenKT;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getTGLamBai() {
        return TGLamBai;
    }

    public void setTGLamBai(int TGLamBai) {
        this.TGLamBai = TGLamBai;
    }

    public Date getTGMoDe() {
        return TGMoDe;
    }

    public void setTGMoDe(Date TGMoDe) {
        this.TGMoDe = TGMoDe;
    }

    public Date getTGDongDe() {
        return TGDongDe;
    }

    public void setTGDongDe(Date TGDongDe) {
        this.TGDongDe = TGDongDe;
    }

    public int getTongSoCau() {
        return tongSoCau;
    }

    public void setTongSoCau(int tongSoCau) {
        this.tongSoCau = tongSoCau;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getSoLanLam() {
        return soLanLam;
    }

    public void setSoLanLam(int soLanLam) {
        this.soLanLam = soLanLam;
    }

    public int getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(int maChuong) {
        this.maChuong = maChuong;
    }
    
}
