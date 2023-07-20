package com.eos.ui;

import com.eos.dao.HocVienDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.dao.NguoiDungDAO;
import com.eos.model.Chuong;
import com.eos.model.HocVien;
import com.eos.model.KhoaHoc;
import com.eos.model.NguoiDung;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.NoScalingIcon;
import com.eos.untils.XDate;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
public class HocVienJDialog extends javax.swing.JDialog {

    /**
     * Creates new form HocVienJDialog
     */
    KhoaHocDAO khdao = new KhoaHocDAO();
    NguoiDungDAO nddao = new NguoiDungDAO();
    HocVienDAO hvdao = new HocVienDAO();
    int row = -1;

    public HocVienJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        fillComboboxKhoaHoc();
        this.init();
        this.fillTableNguoiDung();
        tblNguoiDung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblHocVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public HocVienJDialog() {
		// TODO Auto-generated constructor stub
	}

	void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        btnTabHocVien.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabHocVien.setForeground(new Color(0, 103, 192));
        tblNguoiDung.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNguoiDung.getTableHeader().setForeground(Color.white);
        tblNguoiDung.getTableHeader().setOpaque(false);
        tblNguoiDung.getTableHeader().setBackground(new Color(0, 103, 192));
        tblHocVien.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblHocVien.getTableHeader().setForeground(Color.white);
        tblHocVien.getTableHeader().setOpaque(false);
        tblHocVien.getTableHeader().setBackground(new Color(0, 103, 192));
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
        canGiua();

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
        TableCellRenderer rendererFromHeader = tblNguoiDung.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblNguoiDung.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblNguoiDung.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblNguoiDung.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblNguoiDung.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblNguoiDung.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblNguoiDung.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        TableCellRenderer header = tblHocVien.getTableHeader().getDefaultRenderer();
        JLabel label = (JLabel) header;
        label.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblHocVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblHocVien.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblHocVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblHocVien.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblHocVien.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    }

    void fillTableHocVien() {
        DefaultTableModel model = (DefaultTableModel) tblHocVien.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            List<HocVien> list = hvdao.selectByKhoaHoc(kh.getMaKH());
            int i = 0;
            for (HocVien hv : list) {
                NguoiDung nd = nddao.selectById(hv.getMaND());
                Object[] row = {i + 1, hv.getMaHV(), hv.getMaND(), nd.getHoTen(), nd.isGioiTinh() ? "Nam" : "Nữ"};
                model.addRow(row);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    //TEST FILL TABLE HOCVIEN
    public List<HocVien> testFillTableHocVien(String khoaHoc) {
        try {
            KhoaHoc kh = khdao.selectByName(khoaHoc);
            List<HocVien> list = hvdao.selectByKhoaHoc(kh.getMaKH());
            int i = 0;
            return list;
        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
    }

    void fillTableNguoiDung() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            List<NguoiDung> list = nddao.selectNotInCourse(kh.getMaKH(), txtTimKiem.getText());
            for (NguoiDung nd : list) {
                Object[] row = {nd.getMaND(), nd.getHoTen(),
                    XDate.toString(nd.getNgaySinh(), "dd/MM/yyyy"), nd.isGioiTinh() ? "Nam" : "Nữ",
                    nd.getDienThoai(), nd.getEmail(), nd.getMaNV(), nd.getNgayDK(), nd.getGhiChu()};
                model.addRow(row);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILLTABLE NGUOIDUNG
    public List<NguoiDung> fillTableNguoiDung(String khoaHoc, String keyword) {
        try {
            KhoaHoc kh = khdao.selectByName(khoaHoc);
            List<NguoiDung> list = nddao.selectNotInCourse(kh.getMaKH(), keyword);
            if (list.isEmpty()) {
            	return null;
        	} else {
        		return list;        		
        	}
        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
    }

    void addHocVien() {
        KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
        for (int row : tblNguoiDung.getSelectedRows()) {
            HocVien hv = new HocVien();
            hv.setMaKH(kh.getMaKH());
            hv.setMaND((String) tblNguoiDung.getValueAt(row, 0));
            hvdao.insert(hv);
            MsgBox.alert(this, "Thêm học viên thành công");
        }
        this.fillTableHocVien();
        btnTabHocVien.doClick();
    }
    
    //THEM HOC VIEN VAO KHOA HOC
    public String addHocVien(String tenKH, String MaND) {
    	String notification = "";
        KhoaHoc kh = khdao.selectByName(tenKH);
        HocVien hv = new HocVien();
        hv.setMaKH(kh.getMaKH());
        hv.setMaND(MaND);
        hvdao.insert(hv);
        notification = "Thêm học viên thành công";
        return notification;
    }

    void removeHocVien() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề này");
        } else {
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa học viên này?")) {
                for (int row : tblHocVien.getSelectedRows()) {
                    int mahv = (Integer) tblHocVien.getValueAt(row, 1);
                    hvdao.delete(mahv);
                    MsgBox.alert(this, "Xóa học viên thành công");
                }
                fillTableHocVien();
            }
        }
    }
    
    //XOA HOC VIEN KHOI KHOA HOC
    public String removeHocVien(String maHocVien) {
    	String notification = "";
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa chuyên đề này");
        } else {
            int mahv = Integer.parseInt(maHocVien);
//	            hvdao.delete(mahv);
            notification = "Xóa học viên thành công";
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

        btnTabHocVien = new javax.swing.JButton();
        btnTabNguoiHoc = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnXoaKhoiKhoaHoc = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        btnThemVaoKhoaHoc = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý học viên");

        btnTabHocVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabHocVien.setForeground(new java.awt.Color(177, 177, 177));
        btnTabHocVien.setText("Học viên");
        btnTabHocVien.setBorder(null);
        btnTabHocVien.setContentAreaFilled(false);
        btnTabHocVien.setFocusable(false);
        btnTabHocVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabHocVienActionPerformed(evt);
            }
        });

        btnTabNguoiHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabNguoiHoc.setForeground(new java.awt.Color(177, 177, 177));
        btnTabNguoiHoc.setText("Người dùng");
        btnTabNguoiHoc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabNguoiHoc.setContentAreaFilled(false);
        btnTabNguoiHoc.setFocusable(false);
        btnTabNguoiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabNguoiHocActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnXoaKhoiKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoaKhoiKhoaHoc.setText("Xóa khỏi khóa học");
        btnXoaKhoiKhoaHoc.setFocusable(false);
        btnXoaKhoiKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhoiKhoaHocActionPerformed(evt);
            }
        });

        tblHocVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "TT", "MÃ HV", "MÃ ND", "HỌ VÀ TÊN", "GIỚI TÍNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHocVien.setGridColor(new java.awt.Color(177, 177, 177));
        tblHocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHocVienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblHocVien);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXoaKhoiKhoaHoc)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoaKhoiKhoaHoc)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(603, 343));

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        btnTimKiem.setBorder(null);
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        btnTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTimKiemKeyPressed(evt);
            }
        });

        btnThemVaoKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThemVaoKhoaHoc.setText("Thêm vào khóa học");
        btnThemVaoKhoaHoc.setFocusable(false);
        btnThemVaoKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemVaoKhoaHocActionPerformed(evt);
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
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setFocusable(false);
        jScrollPane2.setOpaque(false);

        tblNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NGƯỜI DÙNG", "HỌ TÊN", "NGÀY SINH", "GIỚI TÍNH", "ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY ĐK", "GHI CHÚ"
            }) 	{
		            boolean[] canEdit = new boolean [] {
		                false, false, false, false, false, false, false, false, false
		            };
		
		            public boolean isCellEditable(int rowIndex, int columnIndex) {
		                return canEdit [columnIndex];
	            }
        });
        tblNguoiDung.setGridColor(new java.awt.Color(177, 177, 177));
        tblNguoiDung.setShowGrid(true);
        tblNguoiDung.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiDungMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNguoiDung);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiem)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemVaoKhoaHoc)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addGap(14, 14, 14)
                .addComponent(btnThemVaoKhoaHoc)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, "card3");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Khóa học", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 0, 13))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        cboKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        cboKhoaHoc.setFocusable(false);
        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboKhoaHoc, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTabHocVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTabNguoiHoc)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTabHocVien)
                    .addComponent(btnTabNguoiHoc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTabHocVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabHocVienActionPerformed
        btnTabHocVien.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabHocVien.setForeground(new Color(0, 103, 192));
        btnTabNguoiHoc.setBorder(null);
        btnTabNguoiHoc.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.first(jPanel1);
    }//GEN-LAST:event_btnTabHocVienActionPerformed

    private void btnTabNguoiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabNguoiHocActionPerformed
        btnTabNguoiHoc.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabNguoiHoc.setForeground(new Color(0, 103, 192));
        btnTabHocVien.setBorder(null);
        btnTabHocVien.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.show(jPanel1, "card3");
        txtTimKiem.requestFocus();
    }//GEN-LAST:event_btnTabNguoiHocActionPerformed

    private void btnXoaKhoiKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhoiKhoaHocActionPerformed
        if (tblHocVien.getSelectedRow() < 0) {

        } else {
            removeHocVien();
            fillTableNguoiDung();
        }
    }//GEN-LAST:event_btnXoaKhoiKhoaHocActionPerformed

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        fillTableHocVien();
        fillTableNguoiDung();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void btnThemVaoKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemVaoKhoaHocActionPerformed
        if (tblNguoiDung.getSelectedRow() < 0) {

        } else {
            addHocVien();
            fillTableNguoiDung();
        }
    }//GEN-LAST:event_btnThemVaoKhoaHocActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableNguoiDung();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTimKiemKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemKeyPressed

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        this.row = tblNguoiDung.getSelectedRow();
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void tblHocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHocVienMouseClicked
        this.row = tblHocVien.getSelectedRow();
    }//GEN-LAST:event_tblHocVienMouseClicked

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnTimKiem.doClick();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

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
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HocVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                HocVienJDialog dialog = new HocVienJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTabHocVien;
    private javax.swing.JButton btnTabNguoiHoc;
    private javax.swing.JButton btnThemVaoKhoaHoc;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoaKhoiKhoaHoc;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
