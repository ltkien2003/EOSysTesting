/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.eos.ui;

import com.eos.dao.BaiHocDAO;
import com.eos.dao.ChuongDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.model.BaiHoc;
import com.eos.model.Chuong;
import com.eos.model.KhoaHoc;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
public class BaiHocJFrame extends javax.swing.JFrame {

    /**
     * Creates new form CauHoiJFrame
     */
    JFileChooser fileChooser = new JFileChooser();
    KhoaHocDAO khdao = new KhoaHocDAO();
    BaiHocDAO bhdao = new BaiHocDAO();
    ChuongDAO cdao = new ChuongDAO();
    int row = -1;
    int isClickTabCapNhat = -1;
    int isClickTabDanhSach = -1;
    int maCH;

    public BaiHocJFrame() {
        initComponents();
        this.init();
        fillComboboxKhoaHoc();

    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblBaiHoc.getModel();
        model.setRowCount(0);
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            if (c != null) {
                List<BaiHoc> list = bhdao.selectByBaiHoc(c.getMaChuong());
                for (BaiHoc bh : list) {
                    Object[] row = {bh.getMaBH(), bh.getTenBH(), bh.getLink(), bh.getMaChuong()};
                    model.addRow(row);
                }
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILL TABLE BAI HOC
    public List<BaiHoc> testFillTable(String nameOfChapter) {
        try {
            Chuong c = cdao.selectByTenChuong(nameOfChapter);
            if (c != null) {
                List<BaiHoc> list = bhdao.selectByBaiHoc(c.getMaChuong());
                return list;
            }

        } catch (Exception e) {
            String notification = "Lỗi truy vấn dữ liệu";
        }
        return null;
    }

    void setForm(BaiHoc bh) {
        txtTenBH.setText(bh.getTenBH());
        txtLink.setText(bh.getLink());
        lblBH.setText(String.valueOf(bh.getMaBH()));
    }

    private boolean checkValidate() {
        if (txtTenBH.getText().trim().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên bài học");
            txtTenBH.requestFocus();
        } else if (txtLink.getText().trim().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập link bài học");
            txtLink.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    void insert() {
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            int checkTenBH = bhdao.selectExistsInsertLesson(txtTenBH.getText(), c.getMaChuong());
            if (!checkValidate()) {

            } else if (checkTenBH > 0) {
                MsgBox.alert(this, "Tên bài học đã tồn tại trong chương của khóa học");
                txtTenBH.requestFocus();
            } else {
                try {
                    BaiHoc bh = getForm();
                    bhdao.insert(bh);
                    this.clearForm();
                    fillTable();
                    MsgBox.alert(this, "Thêm mới thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Thêm mới thất bại!");
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể kiểm tra tên bài học hiện tại");
        }

    }
    //TEST INSERT BAI HOC
    public String insert(String tenChuong, String tenBaiHoc, String link) {
        String notification = "";
    	try {
            Chuong c = cdao.selectByTenChuong(tenChuong);
            int checkTenBH = bhdao.selectExistsInsertLesson(tenBaiHoc, c.getMaChuong());
            if (tenBaiHoc.equals("")) {
            	notification = "Vui lòng nhập tên bài học";
            } else if (link.equals("")) {
            	notification = "Vui lòng nhập link bài học";
            } else if (checkTenBH > 0) {
            	notification = "Tên bài học đã tồn tại trong chương của khóa học";
            } else {
                try {
                	BaiHoc bh = new BaiHoc();
                    bh.setMaChuong(c.getMaChuong());
                    bh.setTenBH(tenBaiHoc);
                    bh.setLink(link);
//                    bhdao.insert(bh);
                    notification = "Thêm mới thành công!";
                } catch (Exception e) {
                	notification = "Thêm mới thất bại!";
                }
            }
        } catch (SQLException e) {
        	notification = "Không thể kiểm tra tên bài học hiện tại";
        }
    	return notification;
    }

    void update() {
        try {
            Chuong c = (Chuong) cboTenChuong.getSelectedItem();
            int maBH = Integer.parseInt(lblBH.getText());
            int checkTenBH = bhdao.selectExistsUpdateLesson(txtTenBH.getText(), c.getMaChuong(), maBH);
            if (!checkValidate()) {

            } else if (checkTenBH > 0) {
                MsgBox.alert(this, "Tên bài học đã tồn tại trong chương của khóa học");
                txtTenBH.requestFocus();
            } else {
                try {
                    BaiHoc bh = getForm();
                    bh.setMaBH(Integer.parseInt(lblBH.getText().trim()));
                    bhdao.update(bh);
                    this.clearForm();
                    fillTable();
                    MsgBox.alert(this, "Cập nhật thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại!");
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể kiểm tra tên bài học hiện tại");
        }

    }
    
    public String update(String maBaiHoc, String tenChuong, String tenBaiHoc, String link) {
        String notification = "";
        try {
            Chuong c = cdao.selectByTenChuong(tenChuong);
            int checkTenBH = bhdao.selectExistsUpdateLesson(tenBaiHoc, c.getMaChuong(), Integer.parseInt(maBaiHoc));
            if (bhdao.selectById(Integer.parseInt(maBaiHoc)).equals(null)) {
            	notification = "Bài học không tồn tại";
            } else if (tenBaiHoc.equals("")) {
            	notification = "Vui lòng nhập tên bài học";
            } else if (link.equals("")) {
            	notification = "Vui lòng nhập link bài học";
            } else if (checkTenBH > 0) {
                notification = "Tên bài học đã tồn tại trong chương của khóa học";
            } else {
                try {
                    BaiHoc bh = new BaiHoc();
                    bh.setMaChuong(c.getMaChuong());
                    bh.setTenBH(tenBaiHoc);
                    bh.setLink(link);
                    bh.setMaBH(Integer.parseInt(maBaiHoc));
//                    bhdao.update(bh);
                    notification = "Cập nhật thành công!";
                } catch (Exception e) {
                	notification = "Cập nhật thất bại!";
                }
            }
        } catch (SQLException e) {
            MsgBox.alert(this, "Không thể kiểm tra tên bài học hiện tại");
        }
    	return notification;
    }

    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa bài học này");
        } else {
            int maBH = Integer.parseInt(lblBH.getText());
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa bài học này?")) {
                try {
                    bhdao.delete(maBH);
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
    //TEST DELETE LESSON
    public String delete(String maBaiHoc) {
    	String notification = "";
        if (!Auth.isAdmin()) {
            notification = "Bạn không có quyền xóa bài học này";
        } else {
        int maBH = Integer.parseInt(maBaiHoc);
            try {
//                bhdao.delete(maBH);
                notification = "Xóa thành công";
            } catch (Exception e) {
                notification = "Xóa thất bại";
            }
        }
        return notification;
    }

    void clearForm() {
        txtTenBH.setText("");
        txtLink.setText("");
        this.row = -1;
        lblBH.setText(null);
        txtTenBH.requestFocus();
        this.updateStatus();
        fillTable();
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        //Trạng thái form
        btnThemBH.setEnabled(!edit);
        btnCapNhatBH.setEnabled(edit);
        btnXoaBH.setEnabled(edit);
    }

    BaiHoc getForm() {
        BaiHoc bh = new BaiHoc();
        try {
            Chuong chuong = (Chuong) cboTenChuong.getSelectedItem();
            bh.setMaChuong(chuong.getMaChuong());
            bh.setTenBH(txtTenBH.getText().trim());
            bh.setLink(txtLink.getText().trim());
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
        return bh;
    }

    void edit() {
        int maBH = (int) tblBaiHoc.getValueAt(this.row, 0);
        BaiHoc ch = bhdao.selectById(maBH);
        this.setForm(ch);
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    void edit(int maBH) {
        BaiHoc ch = bhdao.selectById(maBH);
        this.setForm(ch);
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    private void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        btnTabCapNhat.doClick();
        tblBaiHoc.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblBaiHoc.getTableHeader().setForeground(Color.white);
        tblBaiHoc.getTableHeader().setOpaque(false);
        tblBaiHoc.getTableHeader().setBackground(new Color(0, 103, 192));
        tblBaiHoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        canGiua();
    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblBaiHoc.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblBaiHoc.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblBaiHoc.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
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
    
    //TEST LINK LESSON
    public String checkLink(String link, String tenBaiHoc) {
    	String notification = "";
    	if (tenBaiHoc.equals("")) {
            notification = "Vui lòng nhập tên bài học";
        } else if (link.equals("")) {
        	notification = "Vui lòng nhập link bài học";
        } else {
	    	try {
	            Desktop.getDesktop().browse(new URL(link).toURI());
	            notification = "Link hợp lệ";
	        } catch (IOException | URISyntaxException ex) {
	            notification = "Link không hợp lệ";
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtLink = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnKiemTraLink = new javax.swing.JButton();
        txtTenBH = new javax.swing.JTextField();
        lblTenBH = new javax.swing.JLabel();
        btnThemBH = new javax.swing.JButton();
        btnCapNhatBH = new javax.swing.JButton();
        btnXoaBH = new javax.swing.JButton();
        lblBH = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBaiHoc = new javax.swing.JTable();
        btnTabCapNhat1 = new javax.swing.JButton();
        btnTabDanhSach1 = new javax.swing.JButton();
        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cboKhoaHoc = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        cboTenChuong = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý bài học");
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtLink.setColumns(20);
        txtLink.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtLink.setLineWrap(true);
        txtLink.setRows(5);
        txtLink.setWrapStyleWord(true);
        txtLink.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLinkKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(txtLink);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setText("Link");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 103, 192));
        jLabel1.setText("Bài học");

        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setFocusable(false);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnKiemTraLink.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnKiemTraLink.setText("Kiểm tra link");
        btnKiemTraLink.setFocusable(false);
        btnKiemTraLink.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKiemTraLinkActionPerformed(evt);
            }
        });

        txtTenBH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenBH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenBH.setOpaque(false);
        txtTenBH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenBHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenBHFocusLost(evt);
            }
        });
        txtTenBH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTenBHKeyTyped(evt);
            }
        });

        lblTenBH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTenBH.setText("Tên bài học");

        btnThemBH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThemBH.setText("Thêm bài học");
        btnThemBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemBHActionPerformed(evt);
            }
        });

        btnCapNhatBH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnCapNhatBH.setText("Cập nhật bài học");
        btnCapNhatBH.setEnabled(false);
        btnCapNhatBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatBHActionPerformed(evt);
            }
        });

        btnXoaBH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoaBH.setText("Xóa bài học");
        btnXoaBH.setEnabled(false);
        btnXoaBH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaBHActionPerformed(evt);
            }
        });

        lblBH.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBH.setForeground(new java.awt.Color(0, 103, 192));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenBH)
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenBH, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblBH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLamMoi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnKiemTraLink))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 350, Short.MAX_VALUE)
                                .addComponent(btnThemBH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCapNhatBH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXoaBH)))
                        .addGap(17, 17, 17))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnKiemTraLink, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lblBH)
                        .addComponent(btnLamMoi)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenBH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenBH, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemBH)
                    .addComponent(btnCapNhatBH)
                    .addComponent(btnXoaBH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setFocusable(false);
        jScrollPane2.setOpaque(false);

        tblBaiHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblBaiHoc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "MÃ BÀI HỌC", "TÊN BÀI HỌC", "LINK", "MÃ CHƯƠNG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBaiHoc.setGridColor(new java.awt.Color(177, 177, 177));
        tblBaiHoc.setShowGrid(true);
        tblBaiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBaiHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblBaiHoc);
        if (tblBaiHoc.getColumnModel().getColumnCount() > 0) {
            tblBaiHoc.getColumnModel().getColumn(0).setMinWidth(85);
            tblBaiHoc.getColumnModel().getColumn(0).setMaxWidth(85);
            tblBaiHoc.getColumnModel().getColumn(3).setMinWidth(90);
            tblBaiHoc.getColumnModel().getColumn(3).setMaxWidth(90);
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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

    private void tblBaiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBaiHocMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblBaiHoc.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
            txtTenBH.requestFocus();
        }
    }//GEN-LAST:event_tblBaiHocMouseClicked

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

    private void txtLinkKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLinkKeyTyped
        if (txtLink.getText().length() >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtLinkKeyTyped

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnKiemTraLinkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKiemTraLinkActionPerformed
        if (!checkValidate()) {

        } else {
            try {
                Desktop.getDesktop().browse(new URL(txtLink.getText()).toURI());
            } catch (IOException | URISyntaxException ex) {
                MsgBox.alert(null, "Link không hợp lệ");
            }
        }
    }//GEN-LAST:event_btnKiemTraLinkActionPerformed

    private void txtTenBHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenBHFocusGained
        txtTenBH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTenBHFocusGained

    private void txtTenBHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenBHFocusLost
        txtTenBH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTenBHFocusLost

    private void btnThemBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemBHActionPerformed
        insert();
    }//GEN-LAST:event_btnThemBHActionPerformed

    private void btnCapNhatBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatBHActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatBHActionPerformed

    private void btnXoaBHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaBHActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaBHActionPerformed

    private void txtTenBHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenBHKeyTyped
        if (txtTenBH.getText().length() >= 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTenBHKeyTyped

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
            java.util.logging.Logger.getLogger(BaiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BaiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BaiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BaiHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BaiHocJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatBH;
    private javax.swing.JButton btnKiemTraLink;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnTabCapNhat;
    private javax.swing.JButton btnTabCapNhat1;
    private javax.swing.JButton btnTabDanhSach;
    private javax.swing.JButton btnTabDanhSach1;
    private javax.swing.JButton btnThemBH;
    private javax.swing.JButton btnXoaBH;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboKhoaHoc;
    private javax.swing.JComboBox<String> cboTenChuong;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBH;
    private javax.swing.JLabel lblTenBH;
    private javax.swing.JTable tblBaiHoc;
    private javax.swing.JTextArea txtLink;
    private javax.swing.JTextField txtTenBH;
    // End of variables declaration//GEN-END:variables
}
