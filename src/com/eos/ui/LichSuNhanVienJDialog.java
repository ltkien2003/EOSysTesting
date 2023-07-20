package com.eos.ui;

import com.eos.dao.NguoiDungDAO;
import com.eos.dao.NhanVienDAO;
import com.eos.model.NguoiDung;
import com.eos.model.NhanVien;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.NoScalingIcon;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
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
public class LichSuNhanVienJDialog extends javax.swing.JDialog {

    /**
     * Creates new form NhanVienJDialog
     */
    NhanVienDAO dao = new NhanVienDAO();
    int row = -1;
    private DefaultListModel<NhanVien> model;

    public LichSuNhanVienJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        model = new DefaultListModel();
        listGoiY.setModel(model);
        popupMenu.add(jPanel6);
    }

    public LichSuNhanVienJDialog() {
		// TODO Auto-generated constructor stub
	}

	void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        txtMaNV.requestFocus();
        btnTabDanhSach.doClick();
        tblNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNhanVien.getTableHeader().setForeground(Color.white);
        tblNhanVien.getTableHeader().setOpaque(false);
        tblNhanVien.getTableHeader().setBackground(new Color(0, 103, 192));
        tblNhanVien.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.fillTable();
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
        canGiua();

    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblNhanVien.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblNhanVien.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblNhanVien.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.selectLichSuNhanVien();
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNV(), nv.getHoTen(),
                    nv.getVaiTro() ? "Người quản trị" : "Nhân viên"};
                model.addRow(row);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILLTABLE HISTORY STAFF
    public List<NhanVien> TestFillTable() {
    	try {
    		List<NhanVien> list = dao.selectLichSuNhanVien();
            return list;
            
        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
        }
    	return null;
    }

    void fillTableTimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.findHistoryStaff(txtTimKiem.getText());
            for (NhanVien nv : list) {
                Object[] row = {nv.getMaNV(), nv.getHoTen(),
                    nv.getVaiTro() ? "Người quản trị" : "Nhân viên"};
                model.addRow(row);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FIND STAFF
    public List<NhanVien> fillTableTimKiem(String keyword) {
        try {
            List<NhanVien> list = dao.findHistoryStaff(keyword);
            return list;
        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
        }
        return null;
    }
    
    void setForm(NhanVien nv) {
        txtMaNV.setText(nv.getMaNV());
        txtHoTen.setText(nv.getHoTen());
        rdoTruongPhong.setSelected(nv.getVaiTro());
        rdoNhanVien.setSelected(!nv.getVaiTro());
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setVaiTro(rdoTruongPhong.isSelected());
        return nv;
    }

    void clearForm() {
        NhanVien nv = new NhanVien();
        this.setForm(nv);
    }

    void edit() {
        String manv = (String) tblNhanVien.getValueAt(this.row, 0);
        NhanVien nv = dao.selectHistoryById(manv);
        this.setForm(nv);
        btnTabCapNhat.doClick();
    }

    void edit(String maNV) {
        NhanVien nv = dao.selectHistoryById(maNV);
        this.setForm(nv);
        btnTabCapNhat.doClick();
    }

    void update() {
        try {
            NhanVien nv = getForm();
            dao.restoreStaff(nv);
            this.fillTable();
            this.clearForm();
            MsgBox.alert(this, "Khôi phục nhân viên thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Khôi phục nhân viên thất bại!");
        }
    }
    
  //TEST RESTORE STAFF
    public String update(String id) {
    	String notification = "";
    	if (id.trim().equals("")) {
        } else {
            try {
            	NhanVienDAO nvdao = new NhanVienDAO();
            	NhanVien nv = nvdao.selectHistoryById(id);
                dao.restoreStaff(nv);
                notification = "Khôi phục nhân viên thành công!";
            } catch (Exception e) {
            	e.printStackTrace();
                notification = "Khôi phục nhân viên thất bại!";
            }
        }
        return notification;
    }

    void delete() {
        String manv = txtMaNV.getText();
        if (manv.equals(Auth.user.getMaNV())) {
            MsgBox.alert(this, "Bạn không được xóa chính bạn");
        } else if (MsgBox.confirm(this, "Bạn thực sự muốn xóa nhân viên này vĩnh viễn không?")) {
            try {
                dao.delete(manv);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Xóa thành công");
            } catch (Exception e) {
                MsgBox.alert(this, "Xóa thất bại");

            }
        }
    }
    
    //TEST DELETE STAFFS PERMANENTLY
    public String delete(String id) {
    	String notification = "";
    	String manv = id;
        if (manv.equals(Auth.user.getMaNV())) {
            notification = "Bạn không được xóa chính bạn";
        } else {
            try {
                dao.delete(manv);
                notification = "Xóa thành công";
            } catch (Exception e) {
            	notification = "Xóa thất bại";
            }
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        popupMenu = new javax.swing.JPopupMenu();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listGoiY = new javax.swing.JList<>();
        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        lblMaNV = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        lblHoTen = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        lblVaiTro = new javax.swing.JLabel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        txtMaNV = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

        popupMenu.setBackground(new java.awt.Color(255, 255, 255));
        popupMenu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(255, 255, 255)));
        popupMenu.setFocusable(false);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBorder(null);

        listGoiY.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        listGoiY.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        listGoiY.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listGoiYMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listGoiY);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý nhân viên");

        btnTabCapNhat.setBackground(new java.awt.Color(255, 255, 255));
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

        btnTabDanhSach.setBackground(new java.awt.Color(255, 255, 255));
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

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThem.setText("Khôi phục dữ liệu");
        btnThem.setFocusable(false);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMaNV.setText("Mã nhân viên");

        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setFocusable(false);
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        lblHoTen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHoTen.setText("Họ và tên");

        txtHoTen.setEditable(false);
        txtHoTen.setBackground(new java.awt.Color(255, 255, 255));
        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtHoTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtHoTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHoTenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoTenFocusLost(evt);
            }
        });

        lblVaiTro.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblVaiTro.setText("Vai trò");

        rdoTruongPhong.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdoTruongPhong.setText("Người quản trị");
        rdoTruongPhong.setEnabled(false);

        rdoNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdoNhanVien.setText("Nhân viên");
        rdoNhanVien.setEnabled(false);

        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaNV.setOpaque(false);
        txtMaNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNVFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHoTen)
                    .addComponent(txtMaNV)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoTruongPhong)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdoNhanVien))
                            .addComponent(lblMaNV)
                            .addComponent(lblHoTen))
                        .addGap(0, 344, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblHoTen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblVaiTro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoTruongPhong)
                    .addComponent(rdoNhanVien))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addGap(10, 10, 10))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "MÃ NV", "HỌ TÊN", "VAI TRÒ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.setGridColor(new java.awt.Color(177, 177, 177));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTabDanhSach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTabCapNhat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblNhanVien.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (txtMaNV.getText().equals("")) {

        } else {
            this.update();
        }

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (txtMaNV.getText().equals("")) {

        } else {
            this.delete();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusGained
        txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtHoTenFocusGained

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtHoTenFocusLost

    private void txtMaNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusGained
        txtMaNV.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaNVFocusGained

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        txtMaNV.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaNVFocusLost

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        try {
            String timKiem = txtTimKiem.getText().trim();
            if (!timKiem.equals("")) {
                model.removeAllElements();
                List<NhanVien> list = dao.findStaff(timKiem);
                for (NhanVien q : list) {
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

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnTimKiem.doClick();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        try {
            String timKiem = txtTimKiem.getText().trim();
            if (!timKiem.equals("")) {
                model.removeAllElements();
                List<NhanVien> list = dao.findHistoryStaff(timKiem);
                for (NhanVien q : list) {
                    model.addElement(q);
                }
                popupMenu.show(txtTimKiem, 0, txtTimKiem.getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void listGoiYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listGoiYMouseClicked
        if (listGoiY.getModel().getSize() == 0) {

        } else {
            if (evt.getClickCount() == 2) {
                NhanVien q = listGoiY.getSelectedValue();
                this.edit(q.getMaNV());
                popupMenu.setVisible(false);
            }
        }
    }//GEN-LAST:event_listGoiYMouseClicked

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
            java.util.logging.Logger.getLogger(LichSuNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LichSuNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LichSuNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LichSuNhanVienJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                LichSuNhanVienJDialog dialog = new LichSuNhanVienJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JList<NhanVien> listGoiY;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
