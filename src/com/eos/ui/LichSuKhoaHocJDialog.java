package com.eos.ui;

import com.eos.dao.KhoaHocDAO;
import com.eos.dao.NguoiDungDAO;
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
public class LichSuKhoaHocJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ChuyenDeJDialog
     */
    JFileChooser fileChooser = new JFileChooser();
    KhoaHocDAO dao = new KhoaHocDAO();
    int row = -1;
    private DefaultListModel<KhoaHoc> model;

    public LichSuKhoaHocJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        model = new DefaultListModel();
        listGoiY.setModel(model);
        popupMenu.add(jPanel6);
        popup.add(calendar);
    }

    public LichSuKhoaHocJDialog() {
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
        tblKhoaHoc.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblKhoaHoc.getTableHeader().setForeground(Color.white);
        tblKhoaHoc.getTableHeader().setOpaque(false);
        tblKhoaHoc.getTableHeader().setBackground(new Color(0, 103, 192));
        tblKhoaHoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.fillTable();
        canGiua();
        lblHinh.setToolTipText("no-image.png");
        lblHinh.setIcon(new ImageIcon(new ImageIcon(XImage.read("no-image.png").toString()).getImage().getScaledInstance(185, 188, Image.SCALE_DEFAULT)));
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblKhoaHoc.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblKhoaHoc.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        tblKhoaHoc.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        try {
            List<KhoaHoc> list = dao.selectAllLichSuKhoaHoc();
            for (KhoaHoc kh : list) {
                Object[] row = {kh.getMaKH(), kh.getTenKH(), kh.getHocPhi(),
                    kh.getNgayKG(), kh.getMaNV(), kh.getNgayTao(), kh.getTongSoChuong(), kh.getHinh()};
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
  //TEST FILLTABLE HISTORY COURSE
    public List<KhoaHoc> TestFillTable() {
        try {
            List<KhoaHoc> list = dao.selectAllLichSuKhoaHoc();
            return list;

        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
        }
        return null;
    }

    void fillTableTimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        try {
            List<KhoaHoc> list = dao.findHistoryCourse(txtTimKiem.getText());
            for (KhoaHoc kh : list) {
                Object[] row = {kh.getMaKH(), kh.getTenKH(), kh.getHocPhi(),
                    kh.getNgayKG(), kh.getMaNV(), kh.getNgayTao(), kh.getTongSoChuong(), kh.getHinh()};
                model.addRow(row);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILLTABLE COURSE
    public List<KhoaHoc> fillTableTimKiem(String keyword) {
        try {
            List<KhoaHoc> list = dao.findHistoryCourse(keyword);
            return list;
        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
        }
        return null;
    }

    void setForm(KhoaHoc kh) {
        txtMaKH.setText(String.valueOf(kh.getMaKH()));
        txtTenKH.setText(kh.getTenKH());
        txtHocPhi.setText(String.valueOf(kh.getHocPhi()));
        txtMaNV.setText(String.valueOf(kh.getMaNV()));
        txtTongSoChuong.setText(String.valueOf(kh.getTongSoChuong()));
        txtNgayKG.setText(String.valueOf(XDate.toString(kh.getNgayKG(), "dd/MM/yyyy")));
        txtNgayTao.setText(XDate.toString(kh.getNgayTao(), "dd/MM/yyyy"));
        txtGhiChu.setText(kh.getGhiChu());
        if (kh.getHinh() != null) {
            lblHinh.setToolTipText(kh.getHinh());
            ImageIcon icon = new ImageIcon(XImage.read(kh.getHinh()).getImage().getScaledInstance(185, 188, Image.SCALE_DEFAULT));
            lblHinh.setIcon(icon);
        }
    }

    KhoaHoc getForm() {
        KhoaHoc kh = new KhoaHoc();
        kh.setMaKH(Integer.parseInt(txtMaKH.getText()));
        return kh;
    }

    void clearForm() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtHocPhi.setText("");
        txtMaNV.setText("");
        txtTongSoChuong.setText("");
        txtNgayKG.setText("");
        txtNgayTao.setText("");
        txtGhiChu.setText("");
        lblHinh.setToolTipText("no-image.png");
        lblHinh.setIcon(new ImageIcon(new ImageIcon(XImage.read("no-image.png").toString()).getImage().getScaledInstance(185, 188, Image.SCALE_DEFAULT)));
        this.row = -1;
        fillTable();
    }

    void edit() {
        try {
            int makh = (int) tblKhoaHoc.getValueAt(this.row, 0);
            KhoaHoc kh = dao.selectByHistoryId(makh);
            if (kh != null) {
                this.setForm(kh);
                btnTabCapNhat.doClick();

            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void edit(int maKH) {
        KhoaHoc kh = dao.selectByHistoryId(maKH);
        this.setForm(kh);
        btnTabCapNhat.doClick();
    }

    void update() {
        KhoaHoc kh = getForm();
        if (!validateForm()) {

        } else {
            try {
                dao.restoreCourse(kh);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Khôi phục dữ liệu thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Khôi phục dữ liệu thất bại!");
            }
        }
    }
    
    //TEST RESTORE COURSE
    public String update(String id) {
    	String notification = "";
    	if (id.equals("")) {
        } else {
            try {
            	KhoaHoc kh = dao.selectByHistoryId(Integer.parseInt(id));
                dao.restoreCourse(kh);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Khôi phục dữ liệu thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Khôi phục dữ liệu thất bại!");
            }
        }
        return notification;
    }	

    boolean validateForm() {
        if (txtMaKH.getText().trim().equals("")) {
        } else {
            return true;
        }
        return false;
    }

    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khóa học này");
        } else if (!validateForm()) {
        } else {
            int maKH = Integer.parseInt(txtMaKH.getText());
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa khóa học này vĩnh viễn?")) {
                try {
                    dao.delete(maKH);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
    
    //TEST DELETE USER PERMANENTLY
    public String delete(String id) {
    	String notification = "";
    	if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khóa học này");
        } else if (id.equals("")) {
        } else {
            int maKH = Integer.parseInt(id);
            try {
                dao.delete(maKH);
                this.fillTable();
                this.clearForm();
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

        calendar = new com.github.lgooddatepicker.components.CalendarPanel();
        popup = new javax.swing.JPopupMenu();
        popupMenu = new javax.swing.JPopupMenu();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listGoiY = new javax.swing.JList<>();
        btnTabDanhSach = new javax.swing.JButton();
        btnTabCapNhat = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblTenKH = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblHocPhi = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtHocPhi = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        lblHinh = new javax.swing.JLabel();
        lblGhiChu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        lblMaKH = new javax.swing.JLabel();
        txtNgayKG = new javax.swing.JTextField();
        lblNgayKG = new javax.swing.JLabel();
        txtTongSoChuong = new javax.swing.JTextField();
        lblTongSoChuong = new javax.swing.JLabel();
        lblNgayTao = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        calendar.setBackground(new java.awt.Color(255, 255, 255));

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
        setTitle("EOSys - Lịch sử khóa học");

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

        tblKhoaHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ KH", "TÊN KH", "HỌC PHÍ", "NGÀY KG", "MÃ NV", "NGÀY TẠO", "TỔNG SỐ CHƯƠNG", "HÌNH"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblKhoaHoc.setGridColor(new java.awt.Color(177, 177, 177));
        tblKhoaHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhoaHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhoaHoc);

        btnTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        btnTimKiem.setBorder(null);
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, "card3");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lblTenKH.setText("Tên khóa học");
        lblTenKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblMaNV.setText("Mã nhân viên");
        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblHocPhi.setText("Học phí");
        lblHocPhi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtMaKH.setEditable(false);
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaKH.setEnabled(false);
        txtMaKH.setOpaque(false);
        txtMaKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaKHFocusLost(evt);
            }
        });

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenKH.setEnabled(false);
        txtTenKH.setOpaque(false);
        txtTenKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKHFocusLost(evt);
            }
        });

        txtHocPhi.setEditable(false);
        txtHocPhi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtHocPhi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtHocPhi.setEnabled(false);
        txtHocPhi.setOpaque(false);
        txtHocPhi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHocPhiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHocPhiFocusLost(evt);
            }
        });
        txtHocPhi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHocPhiActionPerformed(evt);
            }
        });

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaNV.setEnabled(false);
        txtMaNV.setOpaque(false);
        txtMaNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaNVFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNVFocusLost(evt);
            }
        });

        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 103, 192)));
        lblHinh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        lblGhiChu.setText("Ghi chú");
        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel2.setText("Hình logo");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.setEnabled(false);
        jScrollPane1.setViewportView(txtGhiChu);

        lblMaKH.setText("Mã khóa học");
        lblMaKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtNgayKG.setEditable(false);
        txtNgayKG.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgayKG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtNgayKG.setEnabled(false);
        txtNgayKG.setOpaque(false);
        txtNgayKG.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgayKGFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayKGFocusLost(evt);
            }
        });
        txtNgayKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNgayKGKeyTyped(evt);
            }
        });

        lblNgayKG.setText("Ngày khai giảng");
        lblNgayKG.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtTongSoChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTongSoChuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTongSoChuong.setEnabled(false);
        txtTongSoChuong.setOpaque(false);
        txtTongSoChuong.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTongSoChuongFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTongSoChuongFocusLost(evt);
            }
        });
        txtTongSoChuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTongSoChuongKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTongSoChuongKeyTyped(evt);
            }
        });

        lblTongSoChuong.setText("Tổng số chương");
        lblTongSoChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblNgayTao.setText("Ngày tạo");
        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtNgayTao.setEditable(false);
        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgayTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtNgayTao.setEnabled(false);
        txtNgayTao.setOpaque(false);
        txtNgayTao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgayTaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayTaoFocusLost(evt);
            }
        });

        btnThem.setText("Khôi phục dữ liệu");
        btnThem.setFocusable(false);
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.setFocusable(false);
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTongSoChuong)
                            .addComponent(lblGhiChu)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtTongSoChuong, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblHinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHocPhi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblMaNV, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblHocPhi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMaKH, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenKH, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 337, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgayKG)
                                    .addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgayTao)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblMaKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(lblTenKH)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblHocPhi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongSoChuong)
                    .addComponent(lblNgayKG)
                    .addComponent(lblNgayTao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTongSoChuong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGhiChu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

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

    private void txtMaKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaKHFocusGained
        txtMaKH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaKHFocusGained

    private void txtMaKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaKHFocusLost
        txtMaKH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaKHFocusLost

    private void txtHocPhiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHocPhiFocusGained
        txtHocPhi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtHocPhiFocusGained

    private void txtHocPhiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHocPhiFocusLost
        txtHocPhi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtHocPhiFocusLost

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
    }//GEN-LAST:event_lblHinhMouseClicked

    private void tblKhoaHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhoaHocMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblKhoaHoc.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
        }
    }//GEN-LAST:event_tblKhoaHocMouseClicked

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        txtMaNV.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaNVFocusLost

    private void txtMaNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusGained
        txtMaNV.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaNVFocusGained

    private void txtHocPhiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHocPhiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHocPhiActionPerformed

    private void txtNgayKGFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayKGFocusLost
        txtNgayKG.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtNgayKGFocusLost

    private void txtNgayKGFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayKGFocusGained
        txtNgayKG.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        popup.show(txtNgayKG, 0, txtNgayKG.getHeight());
    }//GEN-LAST:event_txtNgayKGFocusGained

    private void txtTongSoChuongFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTongSoChuongFocusLost
        txtTongSoChuong.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTongSoChuongFocusLost

    private void txtTongSoChuongFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTongSoChuongFocusGained
        txtTongSoChuong.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTongSoChuongFocusGained

    private void txtNgayTaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayTaoFocusGained
        txtNgayTao.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtNgayTaoFocusGained

    private void txtNgayTaoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayTaoFocusLost
        txtNgayTao.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtNgayTaoFocusLost

    private void txtTenKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusLost
        txtTenKH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTenKHFocusLost

    private void txtTenKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusGained
        txtTenKH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTenKHFocusGained

    private void txtTongSoChuongKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTongSoChuongKeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTongSoChuongKeyTyped

    private void txtNgayKGKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNgayKGKeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_SLASH))) {
            JOptionPane.showMessageDialog(null, "Please Enter Valid");
            evt.consume();
        }
    }//GEN-LAST:event_txtNgayKGKeyTyped

    private void txtTongSoChuongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTongSoChuongKeyPressed
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtTongSoChuongKeyPressed

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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        try {
            String timKiem = txtTimKiem.getText().trim();
            if (!timKiem.equals("")) {
                model.removeAllElements();
                List<KhoaHoc> list = dao.findHistoryCourse(timKiem);
                for (KhoaHoc q : list) {
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
                List<KhoaHoc> list = dao.findCourse(timKiem);
                for (KhoaHoc q : list) {
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
                KhoaHoc q = listGoiY.getSelectedValue();
                this.edit(q.getMaKH());
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
            java.util.logging.Logger.getLogger(LichSuKhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LichSuKhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LichSuKhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LichSuKhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LichSuKhoaHocJDialog dialog = new LichSuKhoaHocJDialog(new javax.swing.JFrame(), true);
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
    private com.github.lgooddatepicker.components.CalendarPanel calendar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblGhiChu;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblHocPhi;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgayKG;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTongSoChuong;
    private javax.swing.JList<KhoaHoc> listGoiY;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JTable tblKhoaHoc;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHocPhi;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayKG;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongSoChuong;
    // End of variables declaration//GEN-END:variables
}
