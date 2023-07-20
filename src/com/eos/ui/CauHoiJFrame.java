/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.eos.ui;

import com.eos.dao.ChuongDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.dao.CauHoiDAO;
import com.eos.model.Chuong;
import com.eos.model.KhoaHoc;
import com.eos.model.BaiHoc;
import com.eos.model.CauHoi;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.NoScalingIcon;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Admin
 */
public class CauHoiJFrame extends javax.swing.JFrame {

    /**
     * Creates new form CauHoiJFrame
     */
    JFileChooser fileChooser = new JFileChooser();
    KhoaHocDAO khdao = new KhoaHocDAO();
    CauHoiDAO chdao = new CauHoiDAO();
    ChuongDAO cdao = new ChuongDAO();
    int row = -1;
    int isClickTabCapNhat = -1;
    int isClickTabDanhSach = -1;
    private final DefaultListModel<CauHoi> model;
    int maCH;

    public CauHoiJFrame() {
        initComponents();
        this.init();
        model = new DefaultListModel();
        listGoiY.setModel(model);
        popupMenu.add(jPanel6);
        this.fillComboboxKhoaHoc();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblCauHoi.getModel();
        model.setRowCount(0);
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            if (c != null) {
                List<CauHoi> list = chdao.selectByChuong(c.getMaChuong());
                for (CauHoi kh : list) {
                    Object[] row = {kh.getMaCH(), kh.getMaChuong(), kh.getTenCH(), kh.getPA1(), kh.getPA2(), kh.getPA3(), kh.getPA4(), kh.getDapAn()};
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILLTABLE CAUHOI
    public List<CauHoi> testFillTable(String nameOfChapter) {
    	String notification = "";
        try {
            Chuong c = (Chuong) cdao.selectByTenChuong(nameOfChapter);
            if (c != null) {
                List<CauHoi> list = chdao.selectByChuong(c.getMaChuong());
                return list;
            }
        } catch (Exception e) {
        	e.printStackTrace();
            notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
        return null;
    }

    void fillTableTimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblCauHoi.getModel();
        model.setRowCount(0);
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            List<CauHoi> list = chdao.selectAll(txtTimKiem.getText(), c.getMaChuong());
            for (CauHoi kh : list) {
                Object[] row = {kh.getMaCH(), kh.getMaChuong(), kh.getTenCH(), kh.getPA1(), kh.getPA2(), kh.getPA3(), kh.getPA4(), kh.getDapAn()};
                model.addRow(row);
                lblMaCH.setText(String.valueOf(kh.getMaCH() + 1));
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FIND QUESTION
    public List<CauHoi> fillTableTimKiem(String tenChuong, String keyword) {
        try {
            Chuong c = (Chuong) cdao.selectByTenChuong(tenChuong);
            List<CauHoi> list = chdao.selectAll(keyword, c.getMaChuong());
            return list;
        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
    }

    void setForm(CauHoi ch) {
        lblMaCH.setText(String.valueOf(ch.getMaCH()));
        txtTenCauHoi.setText(String.valueOf(ch.getTenCH()));
        txtPA1.setText(ch.getPA1());
        txtPA2.setText(ch.getPA2());
        txtPA3.setText(ch.getPA3());
        txtPA4.setText(ch.getPA4());
        if (ch.getPA1().equals(ch.getDapAn())) {
            chkPA1.setSelected(true);
        } else if (ch.getPA2().equals(ch.getDapAn())) {
            chkPA2.setSelected(true);
        } else if (ch.getPA3().equals(ch.getDapAn())) {
            chkPA3.setSelected(true);
        } else if (ch.getPA4().equals(ch.getDapAn())) {
            chkPA4.setSelected(true);
        }
    }

    private boolean checkValidate() {
        if (txtTenCauHoi.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập nội dung câu hỏi");
            txtTenCauHoi.requestFocus();

        } else if (txtPA1.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập nội dung đáp án 1");
            txtPA1.requestFocus();

        } else if (txtPA2.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập nội dung đáp án 2");
            txtPA2.requestFocus();

        } else if (txtPA3.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập nội dung đáp án 3");
            txtPA3.requestFocus();

        } else if (txtPA4.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập nội dung đáp án 4");
            txtPA4.requestFocus();
        } else if (!chkPA1.isSelected() && !chkPA2.isSelected() && !chkPA3.isSelected() && !chkPA4.isSelected()) {
            MsgBox.alert(this, "Vui lòng chọn đáp án đúng");
        } else {
            return true;
        }
        return false;
    }

    void insert() {
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            int checkTenCH = chdao.selectExistsInsertQuestion(txtTenCauHoi.getText(), c.getMaChuong());
            if (!checkValidate()) {

            } else if (checkTenCH > 0) {
                MsgBox.alert(this, "Tên câu hỏi đã tồn tại trong chương của khóa học");
                txtTenCauHoi.requestFocus();
            } else {
                try {
                    CauHoi ch = getForm();
                    chdao.insert(ch);
                    MsgBox.alert(this, "Thêm mới thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Thêm mới thất bại!");
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể kiểm tra tên câu hỏi hiện tại");
        }

    }
    
    //TEST INSERT QUESTION
    public String insert(String tenChuong, String tenCauHoi, String DA1, String DA2, String DA3, String DA4, String DADung) {
    	String notification = "";
        try {
            Chuong c = (Chuong) cdao.selectByTenChuong(tenChuong);
            int checkTenCH = chdao.selectExistsInsertQuestion(tenCauHoi, c.getMaChuong());
            if (tenCauHoi.equals("")) {
                notification = "Vui lòng nhập nội dung câu hỏi";
            } else if (DA1.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 1";
            } else if (DA2.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 2";
            } else if (DA3.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 3";
            } else if (DA4.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 4";
            } else if (DADung.equals("")) {
            	notification = "Vui lòng chọn đáp án đúng";
            } else if (checkTenCH > 0) {
            	notification = "Tên câu hỏi đã tồn tại trong chương của khóa học";
            } else {
                try {
                    CauHoi ch = new CauHoi();
                    ch.setMaChuong(c.getMaChuong());
                    ch.setTenCH(tenCauHoi);
                    ch.setPA1(DA1);
                    ch.setPA2(DA2);
                    ch.setPA3(DA3);
                    ch.setPA4(DA4);
                    if (DADung == "01") {
                    	ch.setDapAn(DA1);                    	
                    } else if (DADung == "02") {
                    	ch.setDapAn(DA2);      
                    } else if (DADung == "02") {
                    	ch.setDapAn(DA3);      
                    } else {
                    	ch.setDapAn(DA4);      
                    }
//                    chdao.insert(ch);
                    notification = "Thêm mới thành công!";
                } catch (Exception e) {
                	notification = "Thêm mới thất bại!";
                }
            }
        } catch (SQLException e) {
        	notification = "Không thể kiểm tra tên câu hỏi hiện tại";
        }
        return notification;
    }

    void update() {
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            int maBH = Integer.parseInt(lblMaCH.getText());
            int checkTenCH = chdao.selectExistsUpdateQuestion(txtTenCauHoi.getText(), c.getMaChuong(), maBH);
            if (!checkValidate()) {

            } else if (checkTenCH > 0) {
                MsgBox.alert(this, "Tên câu hỏi đã tồn tại trong chương của khóa học");
                txtTenCauHoi.requestFocus();
            } else {
                try {
                    CauHoi ch = getForm();
                    ch.setMaCH(Integer.parseInt(lblMaCH.getText()));
                    chdao.update(ch);
                    fillTable();
                    MsgBox.alert(this, "Cập nhật thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại!");
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể kiểm tra tên câu hỏi hiện tại");
        }
    }
    
    public String update(String maCauHoi, String tenChuong, String tenCauHoi, String DA1, String DA2, String DA3, String DA4, String DADung) {
    	String notification = "";
        try {
            Chuong c = (Chuong) cdao.selectByTenChuong(tenChuong);
            int maCH = Integer.parseInt(maCauHoi);
            int checkTenCH = chdao.selectExistsUpdateQuestion(tenCauHoi, c.getMaChuong(), maCH);
            if (tenCauHoi.equals("")) {
                notification = "Vui lòng nhập nội dung câu hỏi";
            } else if (DA1.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 1";
            } else if (DA2.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 2";
            } else if (DA3.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 3";
            } else if (DA4.equals("")) {
            	notification = "Vui lòng nhập nội dung đáp án 4";
            } else if (DADung.equals("")) {
            	notification = "Vui lòng chọn đáp án đúng";
            } else if (checkTenCH > 0) {
            	notification = "Tên câu hỏi đã tồn tại trong chương của khóa học";
            } else {
                try {
                    CauHoi ch = new CauHoi();
                    ch.setMaCH(Integer.parseInt(maCauHoi));
                    ch.setMaChuong(c.getMaChuong());
                    ch.setTenCH(tenCauHoi);
                    ch.setPA1(DA1);
                    ch.setPA2(DA2);
                    ch.setPA3(DA3);
                    ch.setPA4(DA4);
                    if (DADung == "01") {
                    	ch.setDapAn(DA1);                    	
                    } else if (DADung == "02") {
                    	ch.setDapAn(DA2);      
                    } else if (DADung == "02") {
                    	ch.setDapAn(DA3);      
                    } else {
                    	ch.setDapAn(DA4);      
                    }
//                    chdao.update(ch);
                    notification = "Cập nhật thành công!";
                } catch (Exception e) {
                	notification = "Cập nhật thất bại!";
                }
            }
        } catch (SQLException e) {
        	notification = "Không thể kiểm tra tên câu hỏi hiện tại";
        }
        return notification;
    }

    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa câu hỏi này");
        } else {
            int maCH = Integer.parseInt(lblMaCH.getText());
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa câu hỏi này?")) {
                try {
                    chdao.delete(maCH);
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
    
    //TEST DELETE QUESTION
    public String delete(String maCauHoi) {
    	String notification = "";
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa câu hỏi này");
        } else {
            int maCH = Integer.parseInt(maCauHoi);
            try {
//                chdao.delete(maCH);
                this.clearForm();
                notification = "Xóa thành công";
            } catch (Exception e) {
            	notification = "Xóa thất bại";
            }
        }
        return notification;
    }
    void clearForm() {
        txtTenCauHoi.setText("");
        txtPA1.setText("");
        txtPA2.setText("");
        txtPA3.setText("");
        txtPA4.setText("");
        buttonGroup1.clearSelection();
        this.row = -1;
        txtTenCauHoi.requestFocus();
        lblMaCH.setText(null);
        this.updateStatus();
        fillTable();

    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        //Trạng thái form
        btnThemCH.setEnabled(!edit);
        btnCapNhatCH.setEnabled(edit);
        btnXoaCH.setEnabled(edit);
    }

    CauHoi getForm() {
        CauHoi ch = new CauHoi();
        try {
            Chuong chuong = (Chuong) cboTenChuong.getSelectedItem();
            ch.setMaChuong(chuong.getMaChuong());
            ch.setTenCH(txtTenCauHoi.getText());
            ch.setPA1(txtPA1.getText().trim());
            ch.setPA2(txtPA2.getText().trim());
            ch.setPA3(txtPA3.getText().trim());
            ch.setPA4(txtPA4.getText().trim());
            String dapAn = null;
            if (chkPA1.isSelected()) {
                dapAn = txtPA1.getText().trim();
            } else if (chkPA2.isSelected()) {
                dapAn = txtPA2.getText().trim();
            } else if (chkPA3.isSelected()) {
                dapAn = txtPA3.getText().trim();
            } else if (chkPA4.isSelected()) {
                dapAn = txtPA4.getText().trim();
            }
            ch.setDapAn(dapAn);
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
        return ch;
    }

    void edit() {
        int maCH = (int) tblCauHoi.getValueAt(this.row, 0);
        CauHoi ch = chdao.selectById(maCH);
        this.setForm(ch);
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    void edit(int maCH) {
        CauHoi ch = chdao.selectById(maCH);
        this.setForm(ch);
        for (int i = 0; i < tblCauHoi.getRowCount(); i++) {
            if (tblCauHoi.getValueAt(i, 0).equals(ch.getMaCH())) {
                tblCauHoi.setRowSelectionInterval(i, i);
                this.row = tblCauHoi.getSelectedRow();
            }
        }
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    private void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        btnTabCapNhat.doClick();
        tblCauHoi.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblCauHoi.getTableHeader().setForeground(Color.white);
        tblCauHoi.getTableHeader().setOpaque(false);
        tblCauHoi.getTableHeader().setBackground(new Color(0, 103, 192));
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
        tblCauHoi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        XImage.save(file);
        canGiua();
    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblCauHoi.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblCauHoi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tblCauHoi.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
    }

    void fillComboboxKhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model.removeAllElements();
        try {
            List<KhoaHoc> list = khdao.selectCourseAll();
            for (KhoaHoc cd : list) {
                model.addElement(cd);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
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
    
public String xemCauHoi(String tenChuong, String tenCauHoi, String DA1, String DA2, String DA3, String DA4, String DADung) {
	String notification = "";
	
	Chuong c = (Chuong) cdao.selectByTenChuong(tenChuong);
    int checkTenCH;
	try {
		checkTenCH = chdao.selectExistsInsertQuestion(tenCauHoi, c.getMaChuong());
		if (tenCauHoi.equals("")) {
			notification = "Vui lòng nhập nội dung câu hỏi";
		} else if (DA1.equals("")) {
			notification = "Vui lòng nhập nội dung đáp án 1";
		} else if (DA2.equals("")) {
			notification = "Vui lòng nhập nội dung đáp án 2";
		} else if (DA3.equals("")) {
			notification = "Vui lòng nhập nội dung đáp án 3";
		} else if (DA4.equals("")) {
			notification = "Vui lòng nhập nội dung đáp án 4";
		} else if (DADung.equals("")) {
			notification = "Vui lòng chọn đáp án đúng";
		} else if (checkTenCH > 0) {
			notification = "Tên câu hỏi đã tồn tại trong chương của khóa học";
		} else {
			String dapAn = "";
            if (DADung == "01") {
            	dapAn = DA1;                    	
            } else if (DADung == "02") {
            	dapAn = DA2;    
            } else if (DADung == "03") {
            	dapAn = DA3;    
            } else if (DADung == "04"){
            	dapAn = DA4;    
            }
			new XemCauHoiJDialog(this, true, lblMaCH.getText(), txtTenCauHoi.getText(), txtPA1.getText(), txtPA2.getText(), txtPA3.getText(), txtPA4.getText(), dapAn).setVisible(true);
		}
	} catch (SQLException e) {
		notification = "Lỗi xem";
	}
	return notification;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listGoiY = new javax.swing.JList<>();
        popupMenu = new javax.swing.JPopupMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtTenCauHoi = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtPA1 = new javax.swing.JTextArea();
        chkPA1 = new javax.swing.JCheckBox();
        chkPA2 = new javax.swing.JCheckBox();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtPA2 = new javax.swing.JTextArea();
        chkPA3 = new javax.swing.JCheckBox();
        jScrollPane10 = new javax.swing.JScrollPane();
        txtPA3 = new javax.swing.JTextArea();
        jScrollPane11 = new javax.swing.JScrollPane();
        txtPA4 = new javax.swing.JTextArea();
        chkPA4 = new javax.swing.JCheckBox();
        btnXemCauHoi = new javax.swing.JButton();
        btnThemCH = new javax.swing.JButton();
        btnCapNhatCH = new javax.swing.JButton();
        btnXoaCH = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblMaCH = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCauHoi = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        btnTabCapNhat1 = new javax.swing.JButton();
        btnTabDanhSach1 = new javax.swing.JButton();
        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        cboTenChuong = new javax.swing.JComboBox<>();

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBorder(null);

        listGoiY.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        listGoiY.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        listGoiY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listGoiYMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listGoiY);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        popupMenu.setBackground(new java.awt.Color(255, 255, 255));
        popupMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));
        popupMenu.setFocusable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý câu hỏi");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 103, 192));
        jLabel1.setText("Câu hỏi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setText("Nội dung câu hỏi:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Câu trả lời 1:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel5.setText("Câu trả lời 2:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel6.setText("Câu trả lời 3:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel7.setText("Câu trả lời 4:");

        txtTenCauHoi.setColumns(20);
        txtTenCauHoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenCauHoi.setLineWrap(true);
        txtTenCauHoi.setRows(5);
        txtTenCauHoi.setWrapStyleWord(true);
        txtTenCauHoi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTenCauHoiKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txtTenCauHoi);

        txtPA1.setColumns(20);
        txtPA1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPA1.setLineWrap(true);
        txtPA1.setRows(5);
        txtPA1.setWrapStyleWord(true);
        txtPA1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPA1KeyTyped(evt);
            }
        });
        jScrollPane5.setViewportView(txtPA1);

        chkPA1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkPA1);
        chkPA1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkPA1.setText("(Đ/A Đúng)");
        chkPA1.setFocusable(false);
        chkPA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPA1ActionPerformed(evt);
            }
        });

        chkPA2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkPA2);
        chkPA2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkPA2.setText("(Đ/A Đúng)");
        chkPA2.setFocusable(false);
        chkPA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPA2ActionPerformed(evt);
            }
        });

        txtPA2.setColumns(20);
        txtPA2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPA2.setLineWrap(true);
        txtPA2.setRows(5);
        txtPA2.setWrapStyleWord(true);
        txtPA2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPA2KeyTyped(evt);
            }
        });
        jScrollPane9.setViewportView(txtPA2);

        chkPA3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkPA3);
        chkPA3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkPA3.setText("(Đ/A Đúng)");
        chkPA3.setFocusable(false);
        chkPA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPA3ActionPerformed(evt);
            }
        });

        txtPA3.setColumns(20);
        txtPA3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPA3.setLineWrap(true);
        txtPA3.setRows(5);
        txtPA3.setWrapStyleWord(true);
        txtPA3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPA3KeyTyped(evt);
            }
        });
        jScrollPane10.setViewportView(txtPA3);

        txtPA4.setColumns(20);
        txtPA4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtPA4.setLineWrap(true);
        txtPA4.setRows(5);
        txtPA4.setWrapStyleWord(true);
        txtPA4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPA4KeyTyped(evt);
            }
        });
        jScrollPane11.setViewportView(txtPA4);

        chkPA4.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(chkPA4);
        chkPA4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkPA4.setText("(Đ/A Đúng)");
        chkPA4.setFocusable(false);
        chkPA4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPA4ActionPerformed(evt);
            }
        });

        btnXemCauHoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXemCauHoi.setText("Xem câu hỏi");
        btnXemCauHoi.setFocusable(false);
        btnXemCauHoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemCauHoiActionPerformed(evt);
            }
        });

        btnThemCH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThemCH.setText("Thêm câu hỏi");
        btnThemCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCHActionPerformed(evt);
            }
        });

        btnCapNhatCH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCapNhatCH.setText("Cập nhật câu hỏi");
        btnCapNhatCH.setEnabled(false);
        btnCapNhatCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatCHActionPerformed(evt);
            }
        });

        btnXoaCH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoaCH.setText("Xóa câu hỏi");
        btnXoaCH.setEnabled(false);
        btnXoaCH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCHActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 103, 192));
        jLabel8.setText("Đáp án");

        lblMaCH.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblMaCH.setForeground(new java.awt.Color(0, 103, 192));

        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFocusable(false);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaCH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLamMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXemCauHoi))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkPA2))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkPA1))
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                                    .addComponent(jScrollPane10))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(chkPA3))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(chkPA4))))
                            .addComponent(jLabel8))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemCH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCapNhatCH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaCH)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnXemCauHoi)
                    .addComponent(lblMaCH)
                    .addComponent(btnLamMoi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(chkPA1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(chkPA2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(chkPA3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(chkPA4)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemCH)
                    .addComponent(btnCapNhatCH)
                    .addComponent(btnXoaCH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setFocusable(false);
        jScrollPane2.setOpaque(false);

        tblCauHoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblCauHoi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ CH", "MÃ CHƯƠNG", "TÊN CH", "PA1", "PA2", "PA3", "PA4", "ĐÁP ÁN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCauHoi.setGridColor(new java.awt.Color(177, 177, 177));
        tblCauHoi.setShowGrid(true);
        tblCauHoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCauHoiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCauHoi);

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTimKiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTimKiem.setOpaque(false);
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        btnTimKiem.setBorder(null);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, "card3");

        btnTabCapNhat1.setBackground(new java.awt.Color(255, 255, 255));
        btnTabCapNhat1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabCapNhat1.setForeground(new java.awt.Color(177, 177, 177));
        btnTabCapNhat1.setText("Cập nhật");
        btnTabCapNhat1.setBorder(null);
        btnTabCapNhat1.setContentAreaFilled(false);
        btnTabCapNhat1.setFocusable(false);
        btnTabCapNhat1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTabCapNhat1MouseEntered(evt);
            }
        });
        btnTabCapNhat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabCapNhat1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnTabCapNhat1, "card4");

        btnTabDanhSach1.setBackground(new java.awt.Color(255, 255, 255));
        btnTabDanhSach1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabDanhSach1.setForeground(new java.awt.Color(177, 177, 177));
        btnTabDanhSach1.setText("Danh sách");
        btnTabDanhSach1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabDanhSach1.setContentAreaFilled(false);
        btnTabDanhSach1.setFocusable(false);
        btnTabDanhSach1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTabDanhSach1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTabDanhSach1MouseExited(evt);
            }
        });
        btnTabDanhSach1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabDanhSach1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnTabDanhSach1, "card5");

        btnTabCapNhat.setBackground(new java.awt.Color(255, 255, 255));
        btnTabCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabCapNhat.setForeground(new java.awt.Color(177, 177, 177));
        btnTabCapNhat.setText("Cập nhật");
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setContentAreaFilled(false);
        btnTabCapNhat.setFocusable(false);
        btnTabCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTabCapNhatMouseEntered(evt);
            }
        });
        btnTabCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabCapNhatActionPerformed(evt);
            }
        });

        btnTabDanhSach.setBackground(new java.awt.Color(255, 255, 255));
        btnTabDanhSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabDanhSach.setForeground(new java.awt.Color(177, 177, 177));
        btnTabDanhSach.setText("Danh sách");
        btnTabDanhSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabDanhSach.setContentAreaFilled(false);
        btnTabDanhSach.setFocusable(false);
        btnTabDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTabDanhSachMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTabDanhSachMouseExited(evt);
            }
        });
        btnTabDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabDanhSachActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tên khóa học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        cboKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
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

    private void chkPA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPA1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPA1ActionPerformed

    private void chkPA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPA2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPA2ActionPerformed

    private void chkPA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPA3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPA3ActionPerformed

    private void chkPA4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPA4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkPA4ActionPerformed

    private void btnXemCauHoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemCauHoiActionPerformed
        if (!checkValidate()) {

        } else {
            String dapAn = "";
            if (chkPA1.isSelected()) {
                dapAn = txtPA1.getText();
            } else if (chkPA2.isSelected()) {
                dapAn = txtPA2.getText();
            } else if (chkPA3.isSelected()) {
                dapAn = txtPA3.getText();
            } else if (chkPA4.isSelected()) {
                dapAn = txtPA4.getText();
            }
            new XemCauHoiJDialog(this, true, lblMaCH.getText(), txtTenCauHoi.getText(), txtPA1.getText(), txtPA2.getText(), txtPA3.getText(), txtPA4.getText(), dapAn).setVisible(true);
        }
    }//GEN-LAST:event_btnXemCauHoiActionPerformed

    private void tblCauHoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCauHoiMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblCauHoi.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
            txtTenCauHoi.requestFocus();
        }
    }//GEN-LAST:event_tblCauHoiMouseClicked

    private void btnTabCapNhat1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTabCapNhat1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTabCapNhat1MouseEntered

    private void btnTabCapNhat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabCapNhat1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTabCapNhat1ActionPerformed

    private void btnTabDanhSach1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTabDanhSach1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTabDanhSach1MouseEntered

    private void btnTabDanhSach1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTabDanhSach1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTabDanhSach1MouseExited

    private void btnTabDanhSach1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabDanhSach1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTabDanhSach1ActionPerformed

    private void btnTabCapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTabCapNhatMouseEntered
        if (isClickTabCapNhat == 0) {
        } else {
            btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)),
                    BorderFactory.createEmptyBorder(5, 0, 10, 0)));
        }
    }//GEN-LAST:event_btnTabCapNhatMouseEntered

    private void btnTabCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabCapNhatActionPerformed
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        btnTabDanhSach.setBorder(null);
        btnTabDanhSach.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.first(jPanel1);
        isClickTabCapNhat = 0;
        isClickTabDanhSach = -1;
    }//GEN-LAST:event_btnTabCapNhatActionPerformed

    private void btnTabDanhSachMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTabDanhSachMouseEntered
        if (isClickTabDanhSach == 0) {
        } else {
            btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)),
                    BorderFactory.createEmptyBorder(5, 0, 10, 0)));
        }
    }//GEN-LAST:event_btnTabDanhSachMouseEntered

    private void btnTabDanhSachMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTabDanhSachMouseExited
        if (isClickTabDanhSach == 0) {
            btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                    BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        } else {
            btnTabDanhSach.setBorder(null);
        }
    }//GEN-LAST:event_btnTabDanhSachMouseExited

    private void btnTabDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabDanhSachActionPerformed
        btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabDanhSach.setForeground(new Color(0, 103, 192));
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.show(jPanel1, "card3");
        isClickTabDanhSach = 0;
        isClickTabCapNhat = -1;
    }//GEN-LAST:event_btnTabDanhSachActionPerformed

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        fillComboboxChuong();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void cboTenChuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenChuongActionPerformed
        fillTable();
        this.clearForm();
    }//GEN-LAST:event_cboTenChuongActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        try {
            String timKiem = txtTimKiem.getText().trim();
            if (!timKiem.equals("")) {
                model.removeAllElements();
                Chuong c = (Chuong) cboTenChuong.getSelectedItem();
                List<CauHoi> list = chdao.selectAll(txtTimKiem.getText(), c.getMaChuong());
                for (CauHoi q : list) {
                    model.addElement(q);
                }
                popupMenu.show(txtTimKiem, 0, txtTimKiem.getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        try {
            String timKiem = txtTimKiem.getText().trim();
            if (!timKiem.equals("")) {
                model.removeAllElements();
                Chuong c = (Chuong) cboTenChuong.getSelectedItem();
                List<CauHoi> list = chdao.selectAll(txtTimKiem.getText(), c.getMaChuong());
                for (CauHoi q : list) {
                    model.addElement(q);
                }
                popupMenu.show(txtTimKiem, 0, txtTimKiem.getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void listGoiYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listGoiYMouseClicked
        if (listGoiY.getModel().getSize() == 0) {

        } else {
            if (evt.getClickCount() == 2) {
                CauHoi q = listGoiY.getSelectedValue();
                this.edit(q.getMaCH());
                popupMenu.setVisible(false);
            }
        }
    }//GEN-LAST:event_listGoiYMouseClicked

    private void btnThemCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCHActionPerformed
        insert();
        btnLamMoi.doClick();
    }//GEN-LAST:event_btnThemCHActionPerformed

    private void btnCapNhatCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatCHActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatCHActionPerformed

    private void btnXoaCHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCHActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaCHActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnTimKiem.doClick();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTenCauHoiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenCauHoiKeyTyped
        if (txtTenCauHoi.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTenCauHoiKeyTyped

    private void txtPA1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPA1KeyTyped
        if (txtPA1.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPA1KeyTyped

    private void txtPA2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPA2KeyTyped
        if (txtPA2.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPA2KeyTyped

    private void txtPA3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPA3KeyTyped
        if (txtPA3.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPA3KeyTyped

    private void txtPA4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPA4KeyTyped
        if (txtPA4.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPA4KeyTyped

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
            java.util.logging.Logger.getLogger(CauHoiJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CauHoiJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CauHoiJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CauHoiJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CauHoiJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatCH;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTabCapNhat;
    private javax.swing.JButton btnTabCapNhat1;
    private javax.swing.JButton btnTabDanhSach;
    private javax.swing.JButton btnTabDanhSach1;
    private javax.swing.JButton btnThemCH;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXemCauHoi;
    private javax.swing.JButton btnXoaCH;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cboTenChuong;
    private javax.swing.JCheckBox chkPA1;
    private javax.swing.JCheckBox chkPA2;
    private javax.swing.JCheckBox chkPA3;
    private javax.swing.JCheckBox chkPA4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblMaCH;
    private javax.swing.JList<CauHoi> listGoiY;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JTable tblCauHoi;
    private javax.swing.JTextArea txtPA1;
    private javax.swing.JTextArea txtPA2;
    private javax.swing.JTextArea txtPA3;
    private javax.swing.JTextArea txtPA4;
    private javax.swing.JTextArea txtTenCauHoi;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
