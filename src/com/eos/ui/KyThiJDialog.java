/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.eos.ui;

import com.eos.dao.ChuongDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.dao.KyThiDAO;
import com.eos.model.CauHoi;
import com.eos.model.Chuong;
import com.eos.model.KhoaHoc;
import com.eos.model.KyThi;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.XDate;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author MSI PC
 */
public class KyThiJDialog extends javax.swing.JFrame {

    /**
     * Creates new form KyThiJDialog
     */
    KhoaHocDAO khdao = new KhoaHocDAO();
    KyThiDAO ktdao = new KyThiDAO();
    ChuongDAO cdao = new ChuongDAO();
    int row = -1;

    public KyThiJDialog() {
        initComponents();
        init();
    }

    public KyThiJDialog(java.awt.Frame parent, boolean modal) {
        initComponents();
        init();
    }

    public void selectTab(int index) {
        switch (index) {
            case 0 ->
                btnTabDanhSach.doClick();
            default -> {
            }
        }
    }

    void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        txtTenKT.requestFocus();
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        tblKyThi.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblKyThi.getTableHeader().setForeground(Color.white);
        tblKyThi.getTableHeader().setOpaque(false);
        tblKyThi.getTableHeader().setBackground(new Color(0, 103, 192));
        tblKyThi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        canGiua();
        txtMaKT.setEnabled(false);
        btnXoa.setEnabled(false);
        this.fillComboBoxKhoaHoc();

    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblKyThi.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKyThi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        tblKyThi.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
    }

    void fillComboBoxKhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model.removeAllElements();
        List<KhoaHoc> list = khdao.selectAll();
        for (KhoaHoc kh : list) {
            model.addElement(kh);
        }
        fillTable();
    }
    
  //TEST FILL COMBOBOX KHOA HOC
    public List<KhoaHoc> testFillComboboxKhoaHoc() {
    	String notification = "";
        try {
            List<KhoaHoc> list = khdao.selectAll();
            for (KhoaHoc cd : list) {
                return list;
            }
        } catch (Exception e) {
            notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
        return null	;
    }

    void fillComboboxChuong() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenChuong.getModel();
        model.removeAllElements();
        try {
            KhoaHoc khoaHoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (khoaHoc != null) {
                List<Chuong> list = cdao.selectByKhoaHoc(khoaHoc.getMaKH());
                for (Chuong c : list) {
                    model.addElement(c);
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILL COMBOBOX CHUONG
    public List<Chuong> testFillComboboxChuong(String nameOfChuongCourse) {
    	String notification = "";
        try {
        	KhoaHoc khoaHoc = khdao.selectByName(nameOfChuongCourse);
            if (khoaHoc != null) {
            	List<Chuong> list = cdao.selectByKhoaHoc(khoaHoc.getMaKH());
	            for (Chuong c : list) {
	                return list;
	            }
            }
        } catch (Exception e) {
            notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
        return null	;
    }

    void clearForm() {
        KyThi kt = new KyThi();
        KhoaHoc khoahoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
//        kt.setMaKyThi(kt.getMaKyThi());
        kt.setMaKH(khoahoc.getMaKH());
        kt.setMaNV(Auth.user.getMaNV());
        kt.setTGMoDe(XDate.now());
        kt.setTGDongDe(XDate.addDays(XDate.now(), 30));
        this.setForm(kt);
        this.updateStatus();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblKyThi.getRowCount() - 1);
        //Trạng thái form
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        //Trạng thái điều hướng
        btnFirst.setEnabled(edit && !first);
        btnPre.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
    }

    void setForm(KyThi kt) {
        cboKhoaHoc.setToolTipText(String.valueOf(kt.getMaKH()));
        txtTenKT.setText(kt.getTenKT());
        txtMaKT.setText(String.valueOf(kt.getMaKyThi()));
        txtTGMoDe.setText(String.valueOf(XDate.toString(kt.getTGMoDe(), "dd/MM/yyyy hh:mm:ss")));
        txtTGDongDe.setText(String.valueOf(XDate.toString(kt.getTGDongDe(), "dd/MM/yyyy hh:mm:ss")));
        txtTGLamBai.setText(String.valueOf(kt.getTGLamBai()));
        txtMatKhau.setText(kt.getMatKhau());
        cboTenChuong.setToolTipText(String.valueOf(kt.getMaChuong()));
        txtTongSoCau.setText(String.valueOf(kt.getTongSoCau()));
        txtSoLanLam.setText(String.valueOf(kt.getSoLanLam()));
    }

    KyThi getForm() {
        KyThi kt = new KyThi();
        KhoaHoc khoahoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        Chuong chuong = (Chuong) cboTenChuong.getSelectedItem();
        kt.setMaKH(khoahoc.getMaKH());
        kt.setMaChuong(chuong.getMaChuong());
        kt.setTenKT(txtTenKT.getText());
        kt.setMaNV(Auth.user.getMaNV());
        kt.setTGLamBai(Integer.parseInt(txtTGLamBai.getText()));
        kt.setTGMoDe(XDate.toDate(txtTGMoDe.getText(), "dd/MM/yyyy hh:mm:ss"));
        kt.setTGDongDe(XDate.toDate(txtTGDongDe.getText(), "dd/MM/yyyy hh:mm:ss"));
        kt.setTongSoCau(Integer.parseInt(txtTongSoCau.getText()));
        kt.setMatKhau(txtMatKhau.getText());
        kt.setSoLanLam(Integer.parseInt(txtSoLanLam.getText()));
        return kt;
    }
    
    public long tinhThoiGianLamBai(Date dateBatDau, Date dateKetThuc) {
        long differenceInMilliSeconds
                = dateKetThuc.getTime() - dateBatDau.getTime();
        long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;
        long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;

        differenceInMinutes += ((differenceInMilliSeconds / (60 * 60 * 1000))
                % 24) * 60;
        return differenceInMinutes;
    }

    void insert() {
        String kiemTraDinhDangTGDongMoDe = "^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]:[0-6][0-9]$";
        String tgMoDe = txtTGMoDe.getText();
        String tgDongDe = txtTGDongDe.getText();
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Vui lòng đăng nhập");
        } else if (txtTenKT.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên kỳ thi");
            txtTenKT.requestFocus();
        } else if (txtMatKhau.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mật khẩu kỳ thi");
            txtMaKT.requestFocus();
        } else if (tgMoDe.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập thời gian mở đề");
            txtTGMoDe.requestFocus();
        } else if (tgDongDe.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập thời gian đóng đề");
            txtTGDongDe.requestFocus();
        } else if (txtTGLamBai.getText().equals("")) {
        	MsgBox.alert(this, "Vui lòng nhập thời gian làm bài");
            txtTGLamBai.requestFocus();
        } else if (Integer.parseInt(txtTGLamBai.getText()) <= 0) {
        	MsgBox.alert(this, "Thời gian làm bài phải lớn hơn 0");
        	txtTGLamBai.requestFocus();
        } else if (txtTongSoCau.getText().equals("")) {
        	MsgBox.alert(this, "Vui lòng nhập tổng số câu");
            txtTongSoCau.requestFocus();
        } else if (Integer.parseInt(txtTongSoCau.getText()) <= 0) {
        	MsgBox.alert(this, "Tổng số câu phải lớn hơn 0");
            txtTongSoCau.requestFocus();
        } else if (txtSoLanLam.getText().equals("")) {
        	MsgBox.alert(this, "Vui lòng nhập số lần làm bài");
            txtSoLanLam.requestFocus();
        } else if (Integer.parseInt(txtSoLanLam.getText()) <= 0) {                               
        	MsgBox.alert(this, "Số lần làm phải lớn hơn 0");
            txtSoLanLam.requestFocus();
        } else if (!tgMoDe.matches(kiemTraDinhDangTGDongMoDe)) {
            MsgBox.alert(this, "TG mở đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
            txtTGMoDe.requestFocus();
        } else if (!tgDongDe.matches(kiemTraDinhDangTGDongMoDe)) {
            MsgBox.alert(this, "TG đóng đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
            txtTGDongDe.requestFocus();
        } 
        else if(XDate.toDate(txtTGMoDe.getText(), "dd/MM/yyyy hh:mm:ss").after(XDate.toDate(txtTGDongDe.getText(), "dd/MM/yyyy hh:mm:ss"))) {
        	MsgBox.alert(this, "Thời gian mở đề phải nhỏ hơn thời gian đóng đề");
        	txtTGMoDe.requestFocus();
        }
        // check thời gian làm bài trong khoảng tg mở và tg đóng
        else if (Long.parseLong(txtTGLamBai.getText()) > tinhThoiGianLamBai
    		(
        		XDate.toDate(txtTGMoDe.getText(), "dd/MM/yyyy hh:mm:ss"), 
				XDate.toDate(txtTGDongDe.getText(), "dd/MM/yyyy hh:mm:ss"))
    		) 
        {
        	MsgBox.alert(this, "Thời gian làm bài phải nằm trong khoản thời gian mở đề và thời gian đóng đề");
        	txtTGLamBai.requestFocus();
        }
        else {
            try {
                KyThi kt = getForm();
                ktdao.insert(kt);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                System.out.println(e);
                MsgBox.alert(this, "Thêm mới thất bại!");
            }
        }
    }
    
    //TEST INSERT EXAM
    public String insert(String tenChuong, String tenKyThi, String TGMoDe, String TGDongDe, String matKhau, String tongSoCau, String TGLamBai, String soLanLam) {
    	String notification = "";
    	Chuong c = (Chuong) cdao.selectByTenChuong(tenChuong);
    	String kiemTraDinhDangTGDongMoDe = "^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]:[0-6][0-9]$";
        if (!Auth.isAdmin()) {
            notification = "Vui lòng đăng nhập";
        } else if (tenKyThi.equals("")) {
            notification = "Vui lòng nhập tên kỳ thi";
        } else if (matKhau.equals("")) {
        	notification = "Vui lòng nhập mật khẩu kỳ thi";
        } else if (TGMoDe.equals("")) {
        	notification = "Vui lòng nhập thời gian mở đề";
        } else if (TGDongDe.equals("")) {
        	notification = "Vui lòng nhập thời gian đóng đề";
        } else if (tongSoCau.equals("")) {
        	notification = "Vui lòng nhập tổng số câu";
        } else if (Integer.parseInt(tongSoCau) <= 0) {
        	notification = "Tổng số câu phải lớn hơn 0";
        } else if (TGLamBai.equals("")) {
        	notification =  "Vui lòng nhập thời gian làm bài";
        } else if (Integer.parseInt(TGLamBai) <= 0) {
        	notification =  "Thời gian làm bài phải lớn hơn 0";
        } else if (soLanLam.equals("")) {
        	notification =  "Vui lòng nhập số lần làm bài";
        } else if (Integer.parseInt(soLanLam) <= 0) {
        	notification =  "Số lần làm phải lớn hơn 0";
        } else if (!TGMoDe.matches(kiemTraDinhDangTGDongMoDe)) {
        	notification = "TG mở đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss";
        } else if (!TGDongDe.matches(kiemTraDinhDangTGDongMoDe)) {
        	notification = "TG đóng đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss";
        } 
        else if(XDate.toDate(TGMoDe, "dd/MM/yyyy hh:mm:ss").after(XDate.toDate(TGDongDe, "dd/MM/yyyy hh:mm:ss"))) {
        	notification = "Thời gian mở đề phải nhỏ hơn thời gian đóng đề";
        }
        // check thời gian làm bài trong khoảng tg mở và tg đóng
        else if (Long.parseLong(TGLamBai) > tinhThoiGianLamBai
    		(
        		XDate.toDate(TGMoDe, "dd/MM/yyyy hh:mm:ss"), 
				XDate.toDate(TGDongDe, "dd/MM/yyyy hh:mm:ss"))
    		) 
        {
        	notification = "Thời gian làm bài phải nằm trong khoản thời gian mở đề và thời gian đóng đề";
        }
        else {
            try {
                KyThi kt = new KyThi();
                kt.setMaKH(c.getMaKH());
                kt.setMaChuong(c.getMaChuong());
                kt.setTenKT(tenKyThi);
                kt.setTGMoDe(XDate.toDate(TGMoDe, "dd/MM/yyyy hh:mm:ss"));
                kt.setTGDongDe(XDate.toDate(TGDongDe, "dd/MM/yyyy hh:mm:ss"));
                kt.setMatKhau(matKhau);
                kt.setTongSoCau(Integer.parseInt(tongSoCau));
                kt.setTGLamBai(Integer.parseInt(TGLamBai));
                kt.setSoLanLam(Integer.parseInt(soLanLam));
//                ktdao.insert(kt);
                notification = "Thêm mới thành công!";
            } catch (Exception e) {
                System.out.println(e);
                notification = "Thêm mới thất bại!";
            }
        }
        return notification;
    }

    void update() {
        String kiemTraDinhDangTGDongMoDe = "^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]:[0-6][0-9]$";
        String tgMoDe = txtTGMoDe.getText();
        String tgDongDe = txtTGDongDe.getText();
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Vui lòng đăng nhập");
        } else if (txtTenKT.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên kỳ thi");
            txtTenKT.requestFocus();
        } else if (txtMatKhau.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mật khẩu kỳ thi");
            txtMatKhau.requestFocus();
        } else if (tgMoDe.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập thời gian mở đề");
            txtTGMoDe.requestFocus();
        } else if (tgDongDe.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập thời gian đóng đề");
            txtTGDongDe.requestFocus();
        } else if (txtTongSoCau.getText().equals("")) {
        	MsgBox.alert(this, "Vui lòng nhập tổng số câu");
            txtTongSoCau.requestFocus();
        } else if (Integer.parseInt(txtTongSoCau.getText()) <= 0) {
        	MsgBox.alert(this, "Tổng số câu phải lớn hơn 0");
            txtTongSoCau.requestFocus();
        } else if (txtTGLamBai.getText().equals("")) {
        	MsgBox.alert(this, "Vui lòng nhập thời gian làm bài");
            txtTGLamBai.requestFocus();
        } else if (Integer.parseInt(txtTGLamBai.getText()) <= 0) {
        	MsgBox.alert(this, "Thời gian làm bài phải lớn hơn 0");
        	txtTGLamBai.requestFocus();
        } else if (txtSoLanLam.getText().equals("")) {
        	MsgBox.alert(this, "Vui lòng nhập số lần làm bài");
            txtSoLanLam.requestFocus();
        } else if (Integer.parseInt(txtSoLanLam.getText()) <= 0) {
        	MsgBox.alert(this, "Số lần làm phải lớn hơn 0");
            txtSoLanLam.requestFocus();
        } else if (!tgMoDe.matches(kiemTraDinhDangTGDongMoDe)) {
            MsgBox.alert(this, "TG mở đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
            txtTGMoDe.requestFocus();
        } else if (!tgDongDe.matches(kiemTraDinhDangTGDongMoDe)) {
            MsgBox.alert(this, "TG đóng đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
            txtTGMoDe.requestFocus();
        } 
        else if(XDate.toDate(txtTGMoDe.getText(), "dd/MM/yyyy hh:mm:ss").after(XDate.toDate(txtTGDongDe.getText(), "dd/MM/yyyy hh:mm:ss"))) {
        	MsgBox.alert(this, "Thời gian mở đề phải nhỏ hơn thời gian đóng đề");
        	txtTGMoDe.requestFocus();
        }
        // check thời gian làm bài trong khoảng tg mở và tg đóng
        else if (Long.parseLong(txtTGLamBai.getText()) > tinhThoiGianLamBai
    		(
        		XDate.toDate(txtTGMoDe.getText(), "dd/MM/yyyy hh:mm:ss"), 
				XDate.toDate(txtTGDongDe.getText(), "dd/MM/yyyy hh:mm:ss"))
    		) 
        {
        	MsgBox.alert(this, "Thời gian làm bài phải nằm trong khoản thời gian mở đề và thời gian đóng đề");
        	txtTGLamBai.requestFocus();
        }
        else {
            try {
                KyThi kt = getForm();
                kt.setMaKyThi(Integer.parseInt(txtMaKT.getText()));
                ktdao.update(kt);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                System.out.println(e);
                MsgBox.alert(this, "Cập nhật thất bại!");
            }
        }
    }
    
    //TEST UPDATE EXAM
    public String update(String maKyThi, String tenChuong, String tenKyThi, String TGMoDe, String TGDongDe, String matKhau, String tongSoCau, String TGLamBai, String soLanLam) {
    	String notification = "";
    	Chuong c = (Chuong) cdao.selectByTenChuong(tenChuong);
    	String kiemTraDinhDangTGDongMoDe = "^([1-9]|([012][0-9])|(3[01]))/([0]{0,1}[1-9]|1[012])/\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]:[0-6][0-9]$";
		int soLan = Integer.parseInt(soLanLam);
        if (!Auth.isAdmin()) {
            notification = "Vui lòng đăng nhập";
        } else if (tenKyThi.equals("")) {
            notification = "Vui lòng nhập tên kỳ thi";
        } else if (matKhau.equals("")) {
        	notification = "Vui lòng nhập mật khẩu kỳ thi";
        } else if (TGMoDe.equals("")) {
        	notification =  "Vui lòng nhập thời gian mở đề";
        } else if (TGDongDe.equals("")) {
        	notification =  "Vui lòng nhập thời gian đóng đề";
        } else if (TGLamBai.equals("")) {
        	notification =  "Vui lòng nhập thời gian làm bài";
        } else if (Integer.parseInt(TGLamBai) <= 0) {
        	notification =  "Thời gian làm bài phải lớn hơn 0";
        } else if (tongSoCau.equals("")) {
        	notification =  "Vui lòng nhập tổng số câu";
        } else if (Integer.parseInt(tongSoCau) <= 0) {
        	notification = "Tổng số câu phải lớn hơn 0";
        } else if (soLanLam.equals("")) {
        	notification =  "Vui lòng nhập số lần làm bài";
        } else if (Integer.parseInt(soLanLam) <= 0) {
        	notification =  "Số lần làm phải lớn hơn 0";
        } else if (!TGMoDe.matches(kiemTraDinhDangTGDongMoDe)) {
        	notification = "TG mở đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss";
        } else if (!TGDongDe.matches(kiemTraDinhDangTGDongMoDe)) {
        	notification = "TG đóng đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss";
        }
        else if(XDate.toDate(TGMoDe, "dd/MM/yyyy hh:mm:ss").after(XDate.toDate(TGDongDe, "dd/MM/yyyy hh:mm:ss"))) {
        	notification = "Thời gian mở đề phải nhỏ hơn thời gian đóng đề";
        }
     // check thời gian làm bài trong khoảng tg mở và tg đóng
        else if (Long.parseLong(TGLamBai) > tinhThoiGianLamBai
    		(
        		XDate.toDate(TGMoDe, "dd/MM/yyyy hh:mm:ss"), 
				XDate.toDate(TGDongDe, "dd/MM/yyyy hh:mm:ss"))
    		) 
        {
        	notification = "Thời gian làm bài phải nằm trong khoản thời gian mở đề và thời gian đóng đề";
        }
        else {
            try {
                KyThi kt = new KyThi();
                kt.setMaKyThi(Integer.parseInt(maKyThi));
                kt.setMaKH(c.getMaKH());
                kt.setMaChuong(c.getMaChuong());
                kt.setTenKT(tenKyThi);
                kt.setTGMoDe(XDate.toDate(TGMoDe, "dd/MM/yyyy hh:mm:ss"));
                kt.setTGDongDe(XDate.toDate(TGDongDe, "dd/MM/yyyy hh:mm:ss"));
                kt.setMatKhau(matKhau);
                kt.setTongSoCau(Integer.parseInt(tongSoCau));
                kt.setTGLamBai(Integer.parseInt(TGLamBai));
                kt.setSoLanLam(soLan);
//                ktdao.update(kt);
                notification = "Cập nhật thành công!";
            } catch (Exception e) {
                System.out.println(e);
                notification =  "Cập nhật thất bại!";
            }
        }
        return notification;
    }

    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề này");
        } else {
            Integer makt = Integer.valueOf(txtMaKT.getText());
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa chuyên đề này?")) {
                try {
                    ktdao.delete(makt);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
    
    public String delete(String idExam) {
    	String notification = "";
        if (!Auth.isAdmin()) {
            notification = "Bạn không có quyền xóa chuyên đề này";
        } else {
            Integer makt = Integer.valueOf(idExam);
            try {
//                ktdao.delete(makt);
                notification = "Xóa thành công";
            } catch (Exception e) {
            	notification = "Xóa thất bại";
            }
        }
        return notification;
    }

    void edit() {
        int makt = (int) tblKyThi.getValueAt(this.row, 0);
        KyThi kt = ktdao.selectById(makt);
        this.setForm(kt);
        this.updateStatus();
        btnTabCapNhat.doClick();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKyThi.getModel();
        model.setRowCount(0);
        try {
            Chuong chuong = (Chuong) cboTenChuong.getSelectedItem();
            if (chuong != null) {
            	List<KyThi> list = ktdao.selectByChuong(chuong.getMaChuong());
                for (KyThi kt : list) {
                    Object[] row = {
		                    kt.getMaKyThi(),
		                    kt.getTenKT(),
		                    kt.getMaNV(),
		                    kt.getTGLamBai(),
		                    XDate.toString(kt.getTGMoDe(), "dd/MM/yyyy hh:mm:ss"),
		                    XDate.toString(kt.getTGDongDe(), "dd/MM/yyyy hh:mm:ss"),
		                    kt.getTongSoCau(),
		                    kt.getMatKhau(),
		                    kt.getSoLanLam(),
		                    kt.getMaChuong()
                    	};
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
        	MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
  //TEST FILLTABLE EXAM
    public List<KyThi> testFillTable(String nameOfChapter) {
    	try {
            Chuong chuong = cdao.selectByTenChuong(nameOfChapter);
            if (chuong != null) {
            	List<KyThi> list = ktdao.selectByChuong(chuong.getMaChuong());
                return list;
            }
        } catch (Exception e) {
        	String notification = "Lỗi truy vấn dữ liệu";
        	return null;
        }
        return null;
    }

    void chonKhoaHoc() {
        KhoaHoc khoahoc = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        this.fillTable();
        this.row = -1;
        this.updateStatus();
    }

    void chonChuong() {
        Chuong chuong = (Chuong) cboTenChuong.getSelectedItem();
        this.fillTable();
        this.row = -1;
        this.updateStatus();
    }

    void first() {
        this.row = 0;
        this.edit();
    }

    void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }
    }

    void next() {
        if (this.row < tblKyThi.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblKyThi.getRowCount() - 1;
        this.edit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTabDanhSach = new javax.swing.JButton();
        btnTabCapNhat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPre = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblTenCD1 = new javax.swing.JLabel();
        lblTenCD = new javax.swing.JLabel();
        lblHocPhi1 = new javax.swing.JLabel();
        lblHocPhi = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTGLamBai = new javax.swing.JTextField();
        txtTGDongDe = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JTextField();
        txtTenKT = new javax.swing.JTextField();
        txtTGMoDe = new javax.swing.JTextField();
        txtTongSoCau = new javax.swing.JTextField();
        txtSoLanLam = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtMaKT = new javax.swing.JTextField();
        lblNgayTao1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKyThi = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        cboTenChuong = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý kỳ thi");

        btnTabDanhSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabDanhSach.setForeground(new java.awt.Color(177, 177, 177));
        btnTabDanhSach.setText("Danh sách");
        btnTabDanhSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabDanhSach.setContentAreaFilled(false);
        btnTabDanhSach.setFocusable(false);
        btnTabDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabDanhSachActionPerformed(evt);
            }
        });

        btnTabCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabCapNhat.setForeground(new java.awt.Color(177, 177, 177));
        btnTabCapNhat.setText("Cập nhật");
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setContentAreaFilled(false);
        btnTabCapNhat.setFocusable(false);
        btnTabCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabCapNhatActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setFocusable(false);

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setFocusable(false);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setFocusable(false);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setFocusable(false);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.setFocusable(false);
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnFirst.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnFirst.setText("|<");
        btnFirst.setFocusable(false);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPre.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnPre.setText("<<");
        btnPre.setFocusable(false);
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnNext.setText(">>");
        btnNext.setFocusable(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLast.setText(">|");
        btnLast.setFocusable(false);
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblTenCD1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTenCD1.setText("Thời gian làm bài (phút)");

        lblTenCD.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTenCD.setText("Tên kỳ thi");

        lblHocPhi1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHocPhi1.setText("Thời gian đóng đề (dd/MM/yyyy hh:mm:ss)");

        lblHocPhi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHocPhi.setText("Thời gian mở đề (dd/MM/yyyy hh:mm:ss)");

        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNgayTao.setText("Mật khẩu");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Tổng số câu");

        txtTGLamBai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTGLamBai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTGLamBai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGLamBaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGLamBaiFocusLost(evt);
            }
        });

        txtTGDongDe.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTGDongDe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTGDongDe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGDongDeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGDongDeFocusLost(evt);
            }
        });

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMatKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusLost(evt);
            }
        });

        txtTenKT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenKT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenKT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenKTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKTFocusLost(evt);
            }
        });
        txtTenKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKTActionPerformed(evt);
            }
        });

        txtTGMoDe.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTGMoDe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTGMoDe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGMoDeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGMoDeFocusLost(evt);
            }
        });

        txtTongSoCau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTongSoCau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTongSoCau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTongSoCauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTongSoCauFocusLost(evt);
            }
        });
        txtTongSoCau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTongSoCauActionPerformed(evt);
            }
        });

        txtSoLanLam.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSoLanLam.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtSoLanLam.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSoLanLamFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoLanLamFocusLost(evt);
            }
        });
        txtSoLanLam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLanLamActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setText("Số lần làm");

        txtMaKT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaKT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaKT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaKTFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaKTFocusLost(evt);
            }
        });

        lblNgayTao1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNgayTao1.setText("Mã kỳ thi");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenCD1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtTGLamBai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                        .addComponent(lblTenCD, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblHocPhi, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTenKT, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTGMoDe, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtTGDongDe, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblHocPhi1, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayTao)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTongSoCau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                .addComponent(txtSoLanLam, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(lblNgayTao1)
                            .addComponent(txtMaKT, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTenCD)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(txtMaKT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblNgayTao1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblHocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTGMoDe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHocPhi1)
                        .addGap(5, 5, 5)
                        .addComponent(txtTGDongDe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(txtTGLamBai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lblTenCD1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnThem)
                                .addComponent(btnSua)
                                .addComponent(btnXoa)
                                .addComponent(btnMoi))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFirst)
                                .addComponent(btnPre)
                                .addComponent(btnNext)
                                .addComponent(btnLast))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblNgayTao))
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTongSoCau, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoLanLam, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblKyThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblKyThi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KT", "TÊN KT", "MÃ NV", "TG LÀM BÀI", "TG MỞ", "TG ĐÓNG", "SỐ CÂU", "MẬT KHẨU", "LẦN LÀM", "MÃ CHƯƠNG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKyThi.setGridColor(new java.awt.Color(177, 177, 177));
        tblKyThi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKyThiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKyThi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, "card3");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tên khóa học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        cboKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cboKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tin học" }));
        cboKhoaHoc.setFocusable(false);
        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboKhoaHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tên chương", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        cboTenChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cboTenChuong.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", " " }));
        cboTenChuong.setFocusable(false);
        cboTenChuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenChuongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboTenChuong, 0, 307, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(cboTenChuong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTabCapNhat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTabDanhSach)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTabCapNhat)
                    .addComponent(btnTabDanhSach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        this.update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.row = -1;
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        this.prev();
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtTGLamBaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGLamBaiFocusGained
        txtTGLamBai.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTGLamBaiFocusGained

    private void txtTGLamBaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGLamBaiFocusLost
        txtTGLamBai.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTGLamBaiFocusLost

    private void txtTGDongDeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGDongDeFocusGained
        txtTGDongDe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTGDongDeFocusGained

    private void txtTGDongDeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGDongDeFocusLost
        txtTGDongDe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTGDongDeFocusLost

    private void txtMatKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusGained
        txtMatKhau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMatKhauFocusGained

    private void txtMatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusLost
        txtMatKhau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMatKhauFocusLost

    private void txtTenKTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKTFocusGained
        txtTenKT.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTenKTFocusGained

    private void txtTenKTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKTFocusLost
        txtTenKT.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTenKTFocusLost

    private void txtTenKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKTActionPerformed

    private void txtTGMoDeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGMoDeFocusGained
        txtTGMoDe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTGMoDeFocusGained

    private void txtTGMoDeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGMoDeFocusLost
        txtTGMoDe.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTGMoDeFocusLost

    private void txtTongSoCauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTongSoCauFocusGained
        txtTongSoCau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTongSoCauFocusGained

    private void txtTongSoCauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTongSoCauFocusLost
        txtTongSoCau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTongSoCauFocusLost

    private void txtTongSoCauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTongSoCauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTongSoCauActionPerformed

    private void tblKyThiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKyThiMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblKyThi.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
            btnSua.setEnabled(true);
//            btnXoa.setEnabled(true);
        }
    }//GEN-LAST:event_tblKyThiMouseClicked

    private void btnTabDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabDanhSachActionPerformed
        btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabDanhSach.setForeground(new Color(0, 103, 192));
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.show(jPanel1, "card3");
    }//GEN-LAST:event_btnTabDanhSachActionPerformed

    private void btnTabCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabCapNhatActionPerformed
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        btnTabDanhSach.setBorder(null);
        btnTabDanhSach.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.first(jPanel1);
    }//GEN-LAST:event_btnTabCapNhatActionPerformed

    private void txtSoLanLamFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoLanLamFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLanLamFocusGained

    private void txtSoLanLamFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoLanLamFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLanLamFocusLost

    private void txtSoLanLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLanLamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLanLamActionPerformed

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        fillComboboxChuong();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void cboTenChuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenChuongActionPerformed
        try {
            chonChuong();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cboTenChuongActionPerformed

    private void txtMaKTFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaKTFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKTFocusGained

    private void txtMaKTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaKTFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaKTFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KyThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KyThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KyThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KyThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KyThiJDialog dialog = new KyThiJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
//                new KyThiJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTabCapNhat;
    private javax.swing.JButton btnTabDanhSach;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cboTenChuong;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblHocPhi1;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblNgayTao1;
    private javax.swing.JLabel lblTenCD;
    private javax.swing.JLabel lblTenCD1;
    private javax.swing.JTable tblKyThi;
    private javax.swing.JTextField txtMaKT;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtSoLanLam;
    private javax.swing.JTextField txtTGDongDe;
    private javax.swing.JTextField txtTGLamBai;
    private javax.swing.JTextField txtTGMoDe;
    private javax.swing.JTextField txtTenKT;
    private javax.swing.JTextField txtTongSoCau;
    // End of variables declaration//GEN-END:variables
}
