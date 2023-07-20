package com.eos.ui;

import com.eos.dao.ChuongDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.model.KhoaHoc;
import com.eos.model.NguoiDung;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.NoScalingIcon;
import com.eos.untils.XDate;
import com.eos.untils.XImage;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
public class KhoaHocJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ChuyenDeJDialog
     */
    JFileChooser fileChooser = new JFileChooser();
    KhoaHocDAO dao = new KhoaHocDAO();
    ChuongDAO cdao = new ChuongDAO();
    int row = -1;
    private DefaultListModel<KhoaHoc> model;

    public KhoaHocJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        popup.add(calendar);
        model = new DefaultListModel();
        listGoiY.setModel(model);
        popupMenu.add(jPanel6);
    }

    public KhoaHocJDialog() {

    }
    
    void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        txtMaKH.requestFocus();
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        tblKhoaHoc.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblKhoaHoc.getTableHeader().setForeground(Color.white);
        tblKhoaHoc.getTableHeader().setOpaque(false);
        tblKhoaHoc.getTableHeader().setBackground(new Color(0, 103, 192));
        tblKhoaHoc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.fillTable();
        canGiua();
        lblHinh.setToolTipText("no-image.png");
        lblHinh.setIcon(new ImageIcon(new ImageIcon(XImage.read("no-image.png").toString()).getImage().getScaledInstance(185, 188, Image.SCALE_DEFAULT)));
        calendar.setLocale(Locale.forLanguageTag("vi-VN"));
        calendar.getSettings().setDateRangeLimits(LocalDate.now(), LocalDate.MAX);
        calendar.getSettings().setVisibleClearButton(false);
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
        this.clearForm();
        calendar.addCalendarListener(new CalendarListener() {
            @Override
            public void selectedDateChanged(CalendarSelectionEvent cse) {
                txtNgayKG.setText(calendar.getSelectedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            @Override
            public void yearMonthChanged(YearMonthChangeEvent ymce) {
            }
        });
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
            List<KhoaHoc> list = dao.selectAll();
            for (KhoaHoc kh : list) {
                Object[] row = {kh.getMaKH(), kh.getTenKH(), kh.getHocPhi(),
                    kh.getNgayKG(), kh.getMaNV(), kh.getNgayTao(), kh.getTongSoChuong(), kh.getHinh()};
                model.addRow(row);
            }
            txtMaKH.setText(String.valueOf(list.get(list.size() - 1).getMaKH() + 1));
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FILL TABLE
    public List<KhoaHoc> testFillTable() {
        try {
            List<KhoaHoc> list = dao.selectAll();
            for (KhoaHoc kh : list) {
            	return list;
            }
        } catch (Exception e) {
            return null;
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
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
    }

    KhoaHoc getForm() {
        KhoaHoc kh = new KhoaHoc();
        kh.setMaKH(Integer.parseInt(txtMaKH.getText()));
        kh.setTenKH(txtTenKH.getText());
        kh.setHocPhi(Double.parseDouble(txtHocPhi.getText()));
        kh.setMaNV(Auth.user.getMaNV());
        kh.setTongSoChuong(Integer.parseInt(txtTongSoChuong.getText()));
        kh.setNgayKG(XDate.toDate(txtNgayKG.getText(), "dd/MM/yyyy"));
        kh.setNgayTao(XDate.now());
        kh.setGhiChu(txtGhiChu.getText());
        kh.setHinh(lblHinh.getToolTipText());
        return kh;
    }

    void clearForm() {
        KhoaHoc kh = new KhoaHoc();
        kh.setMaNV(Auth.user.getMaNV());
        kh.setHocPhi(0);
        kh.setNgayKG(XDate.addDays(XDate.now(), 20));
        kh.setNgayTao(XDate.now());
        lblHinh.setToolTipText("no-image.png");
        lblHinh.setIcon(new ImageIcon(new ImageIcon(XImage.read("no-image.png").toString()).getImage().getScaledInstance(185, 188, Image.SCALE_DEFAULT)));
        this.setForm(kh);
        this.row = -1;
        fillTable();
        this.updateStatus();
        txtTenKH.requestFocus();
    }

    void edit() {
        try {
            int makh = (int) tblKhoaHoc.getValueAt(this.row, 0);
            KhoaHoc kh = dao.selectById(makh);
            if (kh != null) {
                this.setForm(kh);
                btnTabCapNhat.doClick();
                this.updateStatus();
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    void edit(int maKH) {
        KhoaHoc kh = dao.selectById(maKH);
        this.setForm(kh);
        for (int i = 0; i < tblKhoaHoc.getRowCount(); i++) {
            if (tblKhoaHoc.getValueAt(i, 0).equals(kh.getMaKH())) {
                tblKhoaHoc.setRowSelectionInterval(i, i);
                this.row = tblKhoaHoc.getSelectedRow();
            }
        }
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    void insert() {
        KhoaHoc kh = getForm();
        if (!validateForm()) {

        } else {
            try {
                dao.insert(kh);
                this.fillTable();
                this.clearForm();
                MsgBox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm mới thất bại!");
            }
        }
    }
    
    //TEST INSERT COURSE
    public String insert(String nameOfCourse, String totalChapter, String openingDay) {
        String notification = "";
    	KhoaHoc kh = new KhoaHoc();
        if (nameOfCourse.equals("")) {
            notification = "Vui lòng nhập tên khóa học";
        } else if (totalChapter.equals("")) {
            notification = "Vui lòng nhập tổng số chương";
        } else {
            try {
            	kh.setTenKH(nameOfCourse);
            	kh.setTenKH(totalChapter);
            	kh.setTenKH(openingDay);
//                dao.insert(kh);
                notification = "Thêm mới thành công!";
            } catch (Exception e) {
                notification = "Thêm mới thất bại!";
            }
        }
        return notification;
    }

    void update() {
        try {
            int chapterExists = cdao.selectCountChapter(Integer.parseInt(txtMaKH.getText()));
            if (!validateForm()) {

            } else if (Integer.parseInt(txtTongSoChuong.getText()) < chapterExists) {
                MsgBox.alert(this, "Không thể cập nhật tổng số chương do đã có dữ liệu");
                txtTongSoChuong.requestFocus();
            } else {
                try {
                    KhoaHoc kh = getForm();
                    dao.update(kh);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Cập nhật thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại!");
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại!");
        }
    }
    
    //TEST UPDATE COURSE
    public String update(String idCourse, String nameOfCourse, String totalChapter, String openingDay) {
        String notification = "";
    	try {
            int chapterExists = cdao.selectCountChapter(Integer.parseInt(idCourse)); //mã khóa học
            KhoaHoc check = dao.selectById(Integer.parseInt(idCourse));
            if (check == null) {
            	notification = "Khóa học không tồn tại!";
            } else if (nameOfCourse.equals("")) {
                notification = "Vui lòng nhập tên khóa học";
            } else if (totalChapter.equals("")) {
                notification = "Vui lòng nhập tổng số chương";
            } else if (Integer.parseInt(totalChapter) < chapterExists) {
                notification = "Không thể cập nhật tổng số chương do đã có dữ liệu";
            } else {
                try {
                    KhoaHoc kh = dao.selectById(Integer.parseInt(idCourse));
                    kh.setTenKH(nameOfCourse);
                    kh.setTongSoChuong(chapterExists);
                    kh.setNgayKG(XDate.toDate(openingDay, "dd/MM/yyyy"));
//                    dao.update(kh);
                    notification = "Cập nhật thành công!";
                } catch (Exception e) {
                	notification = "Cập nhật thất bại!";
                }
            }
        } catch (Exception e) {
        	notification = "Cập nhật thất bại!";
        }
        return notification;
    }

    boolean validateForm() {
        if (txtTenKH.getText().trim().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tên khóa học");
            txtTenKH.requestFocus();
        } else if (txtTongSoChuong.getText().trim().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập tổng số chương");
            txtTongSoChuong.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    void fillTableTimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblKhoaHoc.getModel();
        model.setRowCount(0);
        try {
            List<KhoaHoc> list = dao.findCourse(txtTimKiem.getText());
            for (KhoaHoc kh : list) {
                Object[] row = {kh.getMaKH(), kh.getTenKH(), kh.getHocPhi(),
                    kh.getNgayKG(), kh.getMaNV(), kh.getNgayTao(), kh.getTongSoChuong(), kh.getHinh()};
                model.addRow(row);
            }

        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    
    //TEST FIND COURSE
    public List<KhoaHoc> findCourse(String keyword) {
    	try {
            String timKiem = keyword;
            if (!timKiem.equals("")) {
                List<KhoaHoc> list = dao.findCourse(timKiem);
                if (!list.isEmpty()) {
                	return list;
                } else {
                	return null;
                }
            }
        } catch (Exception e) {
        	return null;
        }
        return null;
    }

    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khóa học này");
        } else {
            int maKH = Integer.parseInt(txtMaKH.getText());
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
                try {
                    dao.deleteTemporary(maKH);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
    
    //TEST DELETE COURSE
    public String delete(String idCourse) {
    	String notification = "";
    	KhoaHoc check = dao.selectById(Integer.parseInt(idCourse));
        if (check == null) {
        	notification = "Khóa học không tồn tại!";
        } else if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa khóa học này");
        } else {
            int maKH = Integer.parseInt(idCourse);
            try {
//                dao.deleteTemporary(maKH);
                notification = "Xóa thành công";
            } catch (Exception e) {
                notification = "Xóa thất bại";
            }
        }
        return notification;
    }

    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblKhoaHoc.getRowCount() - 1);
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
        if (this.row < tblKhoaHoc.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblKhoaHoc.getRowCount() - 1;
        this.edit();
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = new ImageIcon(XImage.read(file.getName()).getImage().getScaledInstance(185, 188, Image.SCALE_DEFAULT));
            lblHinh.setIcon(icon);
            lblHinh.setToolTipText(file.getName());
        }
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
        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTenKH = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        lblMaNV = new javax.swing.JLabel();
        btnSua = new javax.swing.JButton();
        lblHocPhi = new javax.swing.JLabel();
        btnXoa = new javax.swing.JButton();
        txtMaKH = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        txtTenKH = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        txtHocPhi = new javax.swing.JTextField();
        btnPre = new javax.swing.JButton();
        txtMaNV = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        lblHinh = new javax.swing.JLabel();
        btnLast = new javax.swing.JButton();
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
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhoaHoc = new javax.swing.JTable();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();

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
        setTitle("EOSys - Quản lý khóa học");

        btnTabCapNhat.setText("Cập nhật");
        btnTabCapNhat.setBackground(new java.awt.Color(255, 255, 255));
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setContentAreaFilled(false);
        btnTabCapNhat.setFocusable(false);
        btnTabCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabCapNhat.setForeground(new java.awt.Color(177, 177, 177));
        btnTabCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabCapNhatActionPerformed(evt);
            }
        });

        btnTabDanhSach.setText("Danh sách");
        btnTabDanhSach.setBackground(new java.awt.Color(255, 255, 255));
        btnTabDanhSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabDanhSach.setContentAreaFilled(false);
        btnTabDanhSach.setFocusable(false);
        btnTabDanhSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabDanhSach.setForeground(new java.awt.Color(177, 177, 177));
        btnTabDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabDanhSachActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lblTenKH.setText("Tên khóa học");
        lblTenKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btnThem.setText("Thêm");
        btnThem.setFocusable(false);
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        lblMaNV.setText("Mã nhân viên");
        lblMaNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btnSua.setText("Sửa");
        btnSua.setFocusable(false);
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        lblHocPhi.setText("Học phí");
        lblHocPhi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        btnXoa.setText("Xóa");
        btnXoa.setFocusable(false);
        btnXoa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        txtMaKH.setEditable(false);
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaKH.setOpaque(false);
        txtMaKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaKHFocusLost(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.setFocusable(false);
        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenKH.setOpaque(false);
        txtTenKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKHFocusLost(evt);
            }
        });
        txtTenKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTenKHKeyTyped(evt);
            }
        });

        btnFirst.setText("|<");
        btnFirst.setFocusable(false);
        btnFirst.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        txtHocPhi.setEditable(false);
        txtHocPhi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtHocPhi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
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

        btnPre.setText("<<");
        btnPre.setFocusable(false);
        btnPre.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
            }
        });

        txtMaNV.setEditable(false);
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

        btnNext.setText(">>");
        btnNext.setFocusable(false);
        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        lblHinh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 103, 192)));
        lblHinh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        btnLast.setText(">|");
        btnLast.setFocusable(false);
        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblGhiChu.setText("Ghi chú");
        lblGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel2.setText("Hình logo");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtGhiChu.setRows(5);
        txtGhiChu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtGhiChuKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtGhiChu);

        lblMaKH.setText("Mã khóa học");
        lblMaKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtNgayKG.setEditable(false);
        txtNgayKG.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgayKG.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
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
        txtNgayTao.setOpaque(false);
        txtNgayTao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgayTaoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayTaoFocusLost(evt);
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
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast))
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
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgayKG)
                                    .addComponent(txtNgayKG, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNgayTao)
                                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
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
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

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
        btnTimKiem.setBorder(null);
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
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
                    .addComponent(txtTimKiem)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(btnTabCapNhat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTabDanhSach)
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

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        this.update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtMaKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaKHFocusGained
        txtMaKH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaKHFocusGained

    private void txtMaKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaKHFocusLost
        txtMaKH.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaKHFocusLost

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
        tblKhoaHoc.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void txtHocPhiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHocPhiFocusGained
        txtHocPhi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtHocPhiFocusGained

    private void txtHocPhiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHocPhiFocusLost
        txtHocPhi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtHocPhiFocusLost

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        this.prev();
        tblKhoaHoc.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.next();
        tblKhoaHoc.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnNextActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        this.chonAnh();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.last();
        tblKhoaHoc.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnLastActionPerformed

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

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
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

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

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

    private void txtTenKHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKHKeyTyped
        if (txtTenKH.getText().length() >= 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTenKHKeyTyped

    private void txtGhiChuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGhiChuKeyTyped
        if (txtGhiChu.getText().length() >= 50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtGhiChuKeyTyped

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
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                KhoaHocJDialog dialog = new KhoaHocJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPre;
    private javax.swing.JButton btnSua;
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
