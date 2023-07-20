/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eos.untils;

import com.eos.model.NguoiDung;
import com.eos.model.NhanVien;

/**
 *
 * @author Kienltpc04639
 */
public class Auth {

    public static NhanVien user = null;
    public static NguoiDung user1;

    public static void clear() {
        if (isLoginNhanVien()) {
            Auth.user = null;
        } else {
            Auth.user1 = null;

        }
    }

    public static boolean isLoginNhanVien() {
        return Auth.user != null;
    }

    public static boolean isLoginNguoiDung() {
        return Auth.user1 != null;
    }

    public static boolean isAdmin() {
        return Auth.isLoginNhanVien() && user.getVaiTro();
    }
}
