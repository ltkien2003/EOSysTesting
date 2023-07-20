package com.eos.ui;

import com.eos.dao.DeThiDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.dao.NguoiDungDAO;
import com.eos.itext.ResultPDF;
import com.eos.model.CauHoi;
import com.eos.model.Chuong;
import com.eos.model.DeThi;
import com.eos.model.KhoaHoc;
import com.eos.model.NguoiDung;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.NoScalingIcon;
import com.eos.untils.XDate;
import com.eos.untils.XImage;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class DeThiJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ChuyenDeJDialog
     */
    KhoaHocDAO khdao = new KhoaHocDAO();
    NguoiDungDAO nddao = new NguoiDungDAO();
    DeThiDAO dtdao = new DeThiDAO();
    int row = -1;

    public DeThiJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        fillComboboxKhoaHoc();
    }

    public DeThiJDialog() {
		// TODO Auto-generated constructor stub
	}

	void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabDanhSach.setForeground(new Color(0, 103, 192));
        tblDeThi.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblDeThi.getTableHeader().setForeground(Color.white);
        tblDeThi.getTableHeader().setOpaque(false);
        tblDeThi.getTableHeader().setBackground(new Color(0, 103, 192));
        tblDeThi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        canGiua();
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
    }

    void fillComboboxKhoaHoc() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model.removeAllElements();
        try {
            List<KhoaHoc> list = khdao.selectAll();
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
    

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblDeThi.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblDeThi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDeThi.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                List<DeThi> list = dtdao.selectViewResult(kh.getMaKH());
                for (DeThi dt : list) {
                    NguoiDung nd = nddao.findNameByIDHV(dt.getMaHV());
                    Object[] row = {dt.getMaDeThi(), nd.getHoTen(), XDate.toString(dt.getTGBatDau(), "dd/MM/yyyy hh:mm:ss"), XDate.toString(dt.getTGKetThuc(), "dd/MM/yyyy hh:mm:ss"), XDate.toString(dt.getNgayTao(), "dd/MM/yyyy"), dt.getDiem(),
                        dt.getMaHV(), nd.getMaND()};
                    model.addRow(row);
                }
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
  //TEST FILLTABLE CAUHOI
    public List<DeThi> testFillTable(String nameOfCourse) {
        try {
            KhoaHoc kh = khdao.selectByName(nameOfCourse);
            if (kh != null) {
                List<DeThi> list = dtdao.selectViewResult(kh.getMaKH());
                return list;
            }

        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
        }
        return null;
    }

    void fillTableTimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblDeThi.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                List<DeThi> list = dtdao.findNameID(kh.getMaKH(), txtTimKiem.getText());
                for (DeThi dt : list) {
                    NguoiDung nd = nddao.findNameByIDHV(dt.getMaHV());
                    Object[] row = {dt.getMaDeThi(), nd.getHoTen(), XDate.toString(dt.getTGBatDau(), "dd/MM/yyyy hh:mm:ss"), XDate.toString(dt.getTGKetThuc(), "dd/MM/yyyy hh:mm:ss"), XDate.toString(dt.getNgayTao(), "dd/MM/yyyy"), dt.getDiem(),
                        dt.getMaHV(), nd.getMaND()};
                    model.addRow(row);
                }
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FIND EXAM QUESTION
    public List<DeThi> testFindExamQuestion(String nameOfCourse, String hoTen) {
    	try {
            KhoaHoc kh = khdao.selectByName(nameOfCourse);
            if (kh != null) {
                List<DeThi> list = dtdao.findNameID(kh.getMaKH(), hoTen);
                return list;
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
        return null;
    }

    void setForm(DeThi dt) {
        NguoiDung nd = nddao.findNameByIDHV(dt.getMaHV());
        txtMaDeThi.setText(String.valueOf(dt.getMaDeThi()));
        txtHoTen.setText(nd.getHoTen());
        txtTGBatDau.setText(XDate.toString(dt.getTGBatDau(), "dd/MM/yyyy hh:mm:ss"));
        txtTGKetThuc.setText(XDate.toString(dt.getTGKetThuc(), "dd/MM/yyyy hh:mm:ss"));
        txtNgayTao.setText(XDate.toString(dt.getNgayTao(), "dd/MM/yyyy"));
        txtDiem.setText(String.valueOf(dt.getDiem()));
        txtMaHV.setText(String.valueOf(dt.getMaHV()));
        txtMaND.setText(nd.getMaND());
    }

    void clearForm() {
        txtMaDeThi.setText("");
        txtHoTen.setText("");
        txtTGBatDau.setText("");
        txtTGKetThuc.setText("");
        txtNgayTao.setText("");
        txtDiem.setText("");
        txtMaHV.setText("");
        txtMaND.setText("");
        this.row = -1;
    }

    void edit() {
        try {
            int madt = (int) tblDeThi.getValueAt(this.row, 0);
            DeThi kh = dtdao.selectById(madt);
            if (kh != null) {
                this.setForm(kh);
                btnTabCapNhat.doClick();

            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    boolean validateForm() {
        if (txtMaDeThi.getText().trim().equals("")) {
        } else {
            return true;
        }
        return false;
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDeThi = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblHoTen = new javax.swing.JLabel();
        txtMaDeThi = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        lblMaDeThi = new javax.swing.JLabel();
        btnXuatKetQua = new javax.swing.JButton();
        txtTGBatDau = new javax.swing.JTextField();
        lblTGKetThuc = new javax.swing.JLabel();
        txtTGKetThuc = new javax.swing.JTextField();
        lblTGBatDau = new javax.swing.JLabel();
        txtDiem = new javax.swing.JTextField();
        lblNgayTao = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        lblDiem = new javax.swing.JLabel();
        lblMaND = new javax.swing.JLabel();
        txtMaND = new javax.swing.JTextField();
        lblMaHV = new javax.swing.JLabel();
        txtMaHV = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý đề thi");

        btnTabDanhSach.setText("Danh sách");
        btnTabDanhSach.setBackground(new java.awt.Color(255, 255, 255));
        btnTabDanhSach.setBorder(null);
        btnTabDanhSach.setContentAreaFilled(false);
        btnTabDanhSach.setFocusable(false);
        btnTabDanhSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabDanhSach.setForeground(new java.awt.Color(177, 177, 177));
        btnTabDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabDanhSachActionPerformed(evt);
            }
        });

        btnTabCapNhat.setText("Cập nhật");
        btnTabCapNhat.setBackground(new java.awt.Color(255, 255, 255));
        btnTabCapNhat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabCapNhat.setContentAreaFilled(false);
        btnTabCapNhat.setFocusable(false);
        btnTabCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabCapNhat.setForeground(new java.awt.Color(177, 177, 177));
        btnTabCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabCapNhatActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblDeThi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ ĐỀ THI", "HỌ TÊN", "TG BẮT ĐẦU", "TG KẾT THÚC", "ĐIỂM", "NGÀY TẠO", "MÃ HV", "MÃ ND"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblDeThi.setGridColor(new java.awt.Color(177, 177, 177));
        tblDeThi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDeThiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDeThi);

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setBorder(null);
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

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
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTimKiem)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, "card3");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lblHoTen.setText("Họ tên");
        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtMaDeThi.setEditable(false);
        txtMaDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaDeThi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaDeThi.setOpaque(false);
        txtMaDeThi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaDeThiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaDeThiFocusLost(evt);
            }
        });

        txtHoTen.setEditable(false);
        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtHoTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtHoTen.setOpaque(false);
        txtHoTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHoTenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoTenFocusLost(evt);
            }
        });

        lblMaDeThi.setText("Mã đề thi");
        lblMaDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btnXuatKetQua.setText("Xuất kết quả");
        btnXuatKetQua.setFocusable(false);
        btnXuatKetQua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXuatKetQua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatKetQuaActionPerformed(evt);
            }
        });

        txtTGBatDau.setEditable(false);
        txtTGBatDau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTGBatDau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTGBatDau.setOpaque(false);
        txtTGBatDau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGBatDauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGBatDauFocusLost(evt);
            }
        });

        lblTGKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTGKetThuc.setText("Thời gian kết thúc");

        txtTGKetThuc.setEditable(false);
        txtTGKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTGKetThuc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTGKetThuc.setOpaque(false);
        txtTGKetThuc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTGKetThucFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTGKetThucFocusLost(evt);
            }
        });

        lblTGBatDau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTGBatDau.setText("Thời gian bắt đầu");

        txtDiem.setEditable(false);
        txtDiem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtDiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtDiem.setOpaque(false);
        txtDiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiemFocusLost(evt);
            }
        });

        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNgayTao.setText("Ngày tạo");

        txtNgayTao.setEditable(false);
        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgayTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtNgayTao.setOpaque(false);
        txtNgayTao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgayTaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayTaoFocusLost(evt);
            }
        });

        lblDiem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblDiem.setText("Điểm");

        lblMaND.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMaND.setText("Mã người dùng");

        txtMaND.setEditable(false);
        txtMaND.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaND.setOpaque(false);
        txtMaND.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNDFocusLost(evt);
            }
        });

        lblMaHV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMaHV.setText("Mã học viên");

        txtMaHV.setEditable(false);
        txtMaHV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaHV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaHV.setOpaque(false);
        txtMaHV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaHVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaHVFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblMaDeThi)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMaDeThi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoTen)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTGBatDau)
                                .addGap(0, 203, Short.MAX_VALUE))
                            .addComponent(txtTGBatDau))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTGKetThuc)
                            .addComponent(txtTGKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblDiem)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtDiem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayTao)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblMaHV)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMaHV))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMaND)
                            .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXuatKetQua)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaDeThi)
                    .addComponent(lblHoTen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTGBatDau)
                    .addComponent(lblTGKetThuc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTGBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTGKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiem)
                    .addComponent(lblNgayTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMaHV)
                    .addComponent(lblMaND))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaHV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(btnXuatKetQua)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTabDanhSach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTabCapNhat)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTabDanhSach)
                    .addComponent(btnTabCapNhat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTabDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabDanhSachActionPerformed
        btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabDanhSach.setForeground(new Color(0, 103, 192));
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.first(jPanel1);
    }//GEN-LAST:event_btnTabDanhSachActionPerformed

    private void btnTabCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabCapNhatActionPerformed
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        btnTabDanhSach.setBorder(null);
        btnTabDanhSach.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.show(jPanel1, "card2");
    }//GEN-LAST:event_btnTabCapNhatActionPerformed

    private void txtMaDeThiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaDeThiFocusGained
        txtMaDeThi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaDeThiFocusGained

    private void txtMaDeThiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaDeThiFocusLost
        txtMaDeThi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaDeThiFocusLost

    private void tblDeThiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDeThiMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblDeThi.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
        }
    }//GEN-LAST:event_tblDeThiMouseClicked

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtHoTenFocusLost

    private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusGained
        txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtHoTenFocusGained

    private void btnXuatKetQuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatKetQuaActionPerformed
        if (txtMaDeThi.getText().trim().equals("")) {
        } else {
            ResultPDF t = new ResultPDF();
            t.xuatKetQuaChiTiet(Integer.parseInt(txtMaDeThi.getText()));
        }
    }//GEN-LAST:event_btnXuatKetQuaActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnTimKiem.doClick();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased

    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        fillTable();
        clearForm();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void txtTGBatDauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGBatDauFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGBatDauFocusGained

    private void txtTGBatDauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGBatDauFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGBatDauFocusLost

    private void txtTGKetThucFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGKetThucFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGKetThucFocusGained

    private void txtTGKetThucFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTGKetThucFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTGKetThucFocusLost

    private void txtDiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemFocusGained

    private void txtDiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiemFocusLost

    private void txtNgayTaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayTaoFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTaoFocusGained

    private void txtNgayTaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayTaoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayTaoFocusLost

    private void txtMaNDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNDFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNDFocusGained

    private void txtMaNDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNDFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNDFocusLost

    private void txtMaHVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaHVFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHVFocusGained

    private void txtMaHVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaHVFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHVFocusLost

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed

    }//GEN-LAST:event_txtTimKiemActionPerformed

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
            java.util.logging.Logger.getLogger(DeThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeThiJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DeThiJDialog dialog = new DeThiJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTabCapNhat;
    private javax.swing.JButton btnTabDanhSach;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXuatKetQua;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDiem;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblMaDeThi;
    private javax.swing.JLabel lblMaHV;
    private javax.swing.JLabel lblMaND;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblTGBatDau;
    private javax.swing.JLabel lblTGKetThuc;
    private javax.swing.JTable tblDeThi;
    private javax.swing.JTextField txtDiem;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaDeThi;
    private javax.swing.JTextField txtMaHV;
    private javax.swing.JTextField txtMaND;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtTGBatDau;
    private javax.swing.JTextField txtTGKetThuc;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
