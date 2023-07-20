/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.eos.ui;

import com.eos.dao.ChuongDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.model.Chuong;
import com.eos.model.KhoaHoc;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
public class ChuongJFrame extends javax.swing.JFrame {

    /**
     * Creates new form CauHoiJFrame
     */
    JFileChooser fileChooser = new JFileChooser();
    KhoaHocDAO khdao = new KhoaHocDAO();
    ChuongDAO cdao = new ChuongDAO();
    int row = -1;
    int isClickTabCapNhat = -1;
    int isClickTabDanhSach = -1;
    int maCH;

    public ChuongJFrame() {
        initComponents();
        this.init();
        fillComboboxKhoaHoc();

    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblChuong.getModel();
        model.setRowCount(0);
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            if (kh != null) {
                List<Chuong> list = cdao.selectByKhoaHoc(kh.getMaKH());
                for (Chuong c : list) {
                    Object[] row = {c.getMaChuong(), c.getTenChuong(), c.getMaKH()};
                    model.addRow(row);
                }
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILLTABLE CHUONG
    public List<Chuong> testFillTable(String nameOfCourse) {
    	String notification = "";
        try {
        	KhoaHoc kh = khdao.selectByName(nameOfCourse);
            List<Chuong> list = cdao.selectByKhoaHoc(kh.getMaKH());
            for (Chuong c : list) {
                return list;
            }
        } catch (Exception e) {
            notification = "Lỗi truy vấn dữ liệu";
            return null;
        }
        return null;
    }

    void setForm(Chuong bh) {
        Chuong chuong = cdao.selectById(bh.getMaChuong());
        DefaultComboBoxModel model2 = (DefaultComboBoxModel) cboKhoaHoc.getModel();
        model2.setSelectedItem(khdao.selectById(chuong.getMaKH()));
        txtTenChuong.setText(bh.getTenChuong());
        lblChuong.setText(String.valueOf(bh.getMaChuong()));
    }

    private boolean checkValidate() {
        if (txtTenChuong.getText().trim().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên chương");
            txtTenChuong.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    void insert() {
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            int soChuongHienTai = cdao.selectCountChapter(kh.getMaKH());
            int checkTenChuong = cdao.selectExistsInsertChapter(txtTenChuong.getText(), kh.getMaKH());
            if (!checkValidate()) {

            } else if (soChuongHienTai >= kh.getTongSoChuong()) {
                MsgBox.alert(this, "Đã đủ số chương không thể thêm chương mới");
                txtTenChuong.requestFocus();
            } else if (checkTenChuong > 0) {
                MsgBox.alert(this, "Tên chương đã tồn tại trong khóa học");
                txtTenChuong.requestFocus();
            } else {
                try {
                    Chuong c = getForm();
                    cdao.insert(c);
                    this.clearForm();
                    fillTable();
                    MsgBox.alert(this, "Thêm mới thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Thêm mới thất bại!");
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể tìm số chương hiện tại");
        }

    }
    
    //TEST INSERT CHAPTER
    public String insert(String nameOfCourse, String nameOfChapter) {
    	String notification = "";
        try {
        	KhoaHoc kh = khdao.selectByName(nameOfCourse);
            int soChuongHienTai = cdao.selectCountChapter(kh.getMaKH());
            int checkTenChuong = cdao.selectExistsInsertChapter(nameOfChapter, kh.getMaKH());
            if (nameOfChapter.equals("")) {
                notification = "Vui lòng nhập tên chương";
            } else if (soChuongHienTai >= kh.getTongSoChuong()) {
                notification = "Đã đủ số chương không thể thêm chương mới";
            } else if (checkTenChuong > 0) {
                notification = "Tên chương đã tồn tại trong khóa học";
            } else {
                try {
                    Chuong c = new Chuong();
                    c.setMaKH(kh.getMaKH());
                    c.setTenChuong(nameOfChapter);
//                    cdao.insert(c);
                    notification = "Thêm mới thành công!";
                } catch (Exception e) {
                    notification = "Thêm mới thất bại!";
                }
            }
        } catch (SQLException e) {
            notification = "Không thể tìm số chương hiện tại";
        }
        return notification;
    }


    void update() {
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            int maChuong = Integer.parseInt(lblChuong.getText());
            int checkTenChuong = cdao.selectExistsUpdateChapter(txtTenChuong.getText(), kh.getMaKH(), maChuong);
            if (!checkValidate()) {

            } else if (checkTenChuong > 0) {
                MsgBox.alert(this, "Tên chương đã tồn tại trong khóa học");
                txtTenChuong.requestFocus();
            } else {
                try {
                    Chuong c = getForm();
                    c.setMaChuong(Integer.parseInt(lblChuong.getText().trim()));
                    cdao.update(c);
                    this.clearForm();
                    fillTable();
                    MsgBox.alert(this, "Cập nhật thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại!");
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể kiểm tra tên chương hiện tại");
        }
    }
    
    //TEST UPDATE CHAPTER
    public String update(String nameOfCourse, String idChapter, String nameOfChapter) {
    	String notification = "";
        try {
        	KhoaHoc kh = khdao.selectByName(nameOfCourse);
        	int maChuong = Integer.parseInt(idChapter);
            int checkTenChuong = cdao.selectExistsUpdateChapter(nameOfChapter, kh.getMaKH(), maChuong);
            if (nameOfChapter.equals("")) {
                notification = "Vui lòng nhập tên chương";
            } else if (checkTenChuong > 0) {
                notification = "Tên chương đã tồn tại trong khóa học";
            } else {
                try {
                    Chuong c = new Chuong();
                    c.setTenChuong(nameOfChapter);
                    c.setMaKH(kh.getMaKH());
                    c.setMaChuong(maChuong);
//                    cdao.update(c);
                    notification = "Cập nhật thành công!";
                } catch (Exception e) {
                	notification = "Cập nhật thất bại!";
                }
            }
        } catch (SQLException e) {
        	notification = "Không thể kiểm tra tên chương hiện tại";
        }
        return notification;
    }

    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa câu hỏi này");
        } else {
            int maChuong = Integer.parseInt(lblChuong.getText());
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa câu hỏi này?")) {
                try {
                    cdao.delete(maChuong);
                    this.clearForm();
                    fillTable();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
    
    public String delete(String idChapter) {
    	String notification = "";
        if (!Auth.isAdmin()) {
        	notification = "Bạn không có quyền xóa câu hỏi này";
        } else {
            int maChuong = Integer.parseInt(idChapter);
            try {
//                cdao.delete(maChuong);
            	notification = "Xóa thành công";
            } catch (Exception e) {
            	notification = "Xóa thất bại";

            }
        }
        return notification;
    }

    void clearForm() {
        txtTenChuong.setText("");
        this.row = -1;
        txtTenChuong.requestFocus();
        this.updateStatus();
        lblChuong.setText(null);
        fillTable();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        //Trạng thái form
        btnThemChuong.setEnabled(!edit);
        btnCapNhatChuong.setEnabled(edit);
        btnXoaChuong.setEnabled(edit);
    }

    Chuong getForm() {
        Chuong bh = new Chuong();
        try {
            KhoaHoc kh = (KhoaHoc) cboKhoaHoc.getSelectedItem();
            bh.setMaKH(kh.getMaKH());
            bh.setTenChuong(txtTenChuong.getText().trim());
        } catch (NumberFormatException e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            System.out.println("Hello");
        }
        return bh;
    }

    void edit() {
        int maBH = (int) tblChuong.getValueAt(this.row, 0);
        Chuong ch = cdao.selectById(maBH);
        this.setForm(ch);
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    void edit(int maBH) {
        Chuong ch = cdao.selectById(maBH);
        this.setForm(ch);
        for (int i = 0; i < tblChuong.getRowCount(); i++) {
            if (tblChuong.getValueAt(i, 0).equals(ch.getMaChuong())) {
                tblChuong.setRowSelectionInterval(i, i);
                this.row = tblChuong.getSelectedRow();
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
        tblChuong.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblChuong.getTableHeader().setForeground(Color.white);
        tblChuong.getTableHeader().setOpaque(false);
        tblChuong.getTableHeader().setBackground(new Color(0, 103, 192));
        tblChuong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        canGiua();
    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblChuong.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblChuong.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblChuong.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        txtTenChuong = new javax.swing.JTextField();
        lblTenChuong = new javax.swing.JLabel();
        btnThemChuong = new javax.swing.JButton();
        btnCapNhatChuong = new javax.swing.JButton();
        btnXoaChuong = new javax.swing.JButton();
        lblChuong = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChuong = new javax.swing.JTable();
        btnTabCapNhat1 = new javax.swing.JButton();
        btnTabDanhSach1 = new javax.swing.JButton();
        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý chương");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 103, 192));
        jLabel1.setText("Chương");

        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFocusable(false);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtTenChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenChuong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenChuong.setOpaque(false);
        txtTenChuong.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenChuongFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenChuongFocusLost(evt);
            }
        });
        txtTenChuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenChuongActionPerformed(evt);
            }
        });
        txtTenChuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTenChuongKeyTyped(evt);
            }
        });

        lblTenChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTenChuong.setText("Tên chương");

        btnThemChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThemChuong.setText("Thêm chương");
        btnThemChuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChuongActionPerformed(evt);
            }
        });

        btnCapNhatChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCapNhatChuong.setText("Cập nhật chương");
        btnCapNhatChuong.setEnabled(false);
        btnCapNhatChuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatChuongActionPerformed(evt);
            }
        });

        btnXoaChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoaChuong.setText("Xóa chương");
        btnXoaChuong.setEnabled(false);
        btnXoaChuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChuongActionPerformed(evt);
            }
        });

        lblChuong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblChuong.setForeground(new java.awt.Color(0, 103, 192));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTenChuong)
                        .addContainerGap(627, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenChuong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblChuong)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 535, Short.MAX_VALUE)
                                .addComponent(btnLamMoi)))
                        .addGap(17, 17, 17))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThemChuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCapNhatChuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaChuong)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblChuong)
                    .addComponent(btnLamMoi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenChuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenChuong, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemChuong)
                    .addComponent(btnCapNhatChuong)
                    .addComponent(btnXoaChuong))
                .addGap(21, 21, 21))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setFocusable(false);
        jScrollPane2.setOpaque(false);

        tblChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblChuong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "MÃ CHƯƠNG", "TÊN CHƯƠNG", "MÃ KHÓA HỌC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChuong.setGridColor(new java.awt.Color(177, 177, 177));
        tblChuong.setShowGrid(true);
        tblChuong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChuongMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChuong);
        if (tblChuong.getColumnModel().getColumnCount() > 0) {
            tblChuong.getColumnModel().getColumn(0).setMinWidth(90);
            tblChuong.getColumnModel().getColumn(0).setMaxWidth(90);
            tblChuong.getColumnModel().getColumn(2).setMinWidth(100);
            tblChuong.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 698, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void tblChuongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChuongMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblChuong.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
            txtTenChuong.requestFocus();
        }
    }//GEN-LAST:event_tblChuongMouseClicked

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
        fillTable();
        clearForm();
    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTenChuongFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenChuongFocusGained
        txtTenChuong.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTenChuongFocusGained

    private void txtTenChuongFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenChuongFocusLost
        txtTenChuong.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTenChuongFocusLost

    private void btnThemChuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChuongActionPerformed
        insert();
    }//GEN-LAST:event_btnThemChuongActionPerformed

    private void btnCapNhatChuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatChuongActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatChuongActionPerformed

    private void btnXoaChuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChuongActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaChuongActionPerformed

    private void txtTenChuongKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenChuongKeyTyped
        if (txtTenChuong.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTenChuongKeyTyped

    private void txtTenChuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenChuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenChuongActionPerformed

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
            java.util.logging.Logger.getLogger(ChuongJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChuongJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChuongJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChuongJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChuongJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatChuong;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTabCapNhat;
    private javax.swing.JButton btnTabCapNhat1;
    private javax.swing.JButton btnTabDanhSach;
    private javax.swing.JButton btnTabDanhSach1;
    private javax.swing.JButton btnThemChuong;
    private javax.swing.JButton btnXoaChuong;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChuong;
    private javax.swing.JLabel lblTenChuong;
    private javax.swing.JTable tblChuong;
    private javax.swing.JTextField txtTenChuong;
    // End of variables declaration//GEN-END:variables
}
