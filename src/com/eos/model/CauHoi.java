/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.model;

/**
 *
 * @author Admin
 */
public class CauHoi {

    private int maCH;
    private int maChuong;
    private String tenCH;
    private String PA1;
    private String PA2;
    private String PA3;
    private String PA4;
    private String dapAn;
    @Override
    public String toString() {
        return this.tenCH;
    }
    public int getMaCH() {
        return maCH;
    }

    public void setMaCH(int maCH) {
        this.maCH = maCH;
    }

    public int getMaChuong() {
        return maChuong;
    }

    public void setMaChuong(int maChuong) {
        this.maChuong = maChuong;
    }

    public String getTenCH() {
        return tenCH;
    }

    public void setTenCH(String tenCH) {
        this.tenCH = tenCH;
    }

    public String getPA1() {
        return PA1;
    }

    public void setPA1(String PA1) {
        this.PA1 = PA1;
    }

    public String getPA2() {
        return PA2;
    }

    public void setPA2(String PA2) {
        this.PA2 = PA2;
    }

    public String getPA3() {
        return PA3;
    }

    public void setPA3(String PA3) {
        this.PA3 = PA3;
    }

    public String getPA4() {
        return PA4;
    }

    public void setPA4(String PA4) {
        this.PA4 = PA4;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }
    
}
