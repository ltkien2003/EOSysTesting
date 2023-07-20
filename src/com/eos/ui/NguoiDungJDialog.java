package com.eos.ui;

import com.eos.dao.NguoiDungDAO;
import com.eos.model.NguoiDung;
import com.eos.model.NhanVien;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.NoScalingIcon;
import com.eos.untils.StrongPasswordGenerator;
import com.eos.untils.XDate;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.w3c.dom.traversal.NodeIterator;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class NguoiDungJDialog extends javax.swing.JDialog {

    /**
     * Creates new form NguoiDungJDialog
     */
    NguoiDungDAO dao = new NguoiDungDAO();
    int row = -1;
    private DefaultListModel<NguoiDung> model;

    public NguoiDungJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
        model = new DefaultListModel();
        listGoiY.setModel(model);
        popupMenu.add(jPanel6);
        popup.add(calendar);
    }

    public NguoiDungJDialog() {
		// TODO Auto-generated constructor stub
	}

	void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        txtMaND.requestFocus();
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        tblNguoiDung.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblNguoiDung.getTableHeader().setForeground(Color.white);
        tblNguoiDung.getTableHeader().setOpaque(false);
        tblNguoiDung.getTableHeader().setBackground(new Color(0, 103, 192));
        this.fillTable();
        this.clearForm();
        canGiua();
        calendar.setLocale(Locale.forLanguageTag("vi-VN"));
        calendar.getSettings().setDateRangeLimits(LocalDate.MIN, LocalDate.now().minusYears(10));
        calendar.getSettings().setVisibleClearButton(false);
        NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\search.png"));
        btnTimKiem.setIcon(icon19);
        tblNguoiDung.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        calendar.addCalendarListener(new CalendarListener() {
            @Override
            public void selectedDateChanged(CalendarSelectionEvent cse) {
                txtNgaySinh.setText(calendar.getSelectedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            @Override
            public void yearMonthChanged(YearMonthChangeEvent ymce) {
            }
        });
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
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        try {
            List<NguoiDung> list = dao.selectAll();
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
    
    //TEST HIEN THI LEN BANG
    public List<NguoiDung> TestFillTable() {
        try {
            List<NguoiDung> list = dao.selectAll();
            if (!list.equals(null)) {
            	return list;
            } else {
            	return null;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            return null;
        }
    }

    void fillTableTimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblNguoiDung.getModel();
        model.setRowCount(0);
        try {
            List<NguoiDung> list = dao.findUser(txtTimKiem.getText());
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

    void setForm(NguoiDung nd) {
        txtMaND.setText(nd.getMaND());
        txtHoTen.setText(nd.getHoTen());
        if (nd.getNgaySinh() != null) {
            txtNgaySinh.setText(XDate.toString(nd.getNgaySinh(), "dd/MM/yyyy"));
        }
        txtDienThoai.setText(nd.getDienThoai());
        txtEmail.setText(nd.getEmail());
        txtMaNV.setText(nd.getMaNV());
        txtGhiChu.setText(nd.getGhiChu());
        txtNgayDK.setText(XDate.toString(nd.getNgayDK(), "dd/MM/yyyy"));
        rdoMale.setSelected(nd.isGioiTinh());
        rdoFemale.setSelected(!nd.isGioiTinh());
    }

    NguoiDung getForm() {
        NguoiDung nd = new NguoiDung();
        nd.setMaND(txtMaND.getText());
        nd.setHoTen(txtHoTen.getText());
        nd.setNgaySinh(XDate.toDate(txtNgaySinh.getText(), "dd/MM/yyyy"));
        nd.setDienThoai(txtDienThoai.getText());
        nd.setEmail(txtEmail.getText());
        nd.setMaNV(Auth.user.getMaNV());
        nd.setGhiChu(txtGhiChu.getText());
        nd.setGioiTinh(rdoMale.isSelected());
        return nd;
    }

    void clearForm() {
        NguoiDung nv = new NguoiDung();
        nv.setNgayDK(XDate.now());
        nv.setMaNV(Auth.user.getMaNV());
        this.setForm(nv);
        buttonGroup1.clearSelection();
        this.row = -1;
        txtMaND.requestFocus();
        this.updateStatus();

    }

    void edit() {
        String mand = (String) tblNguoiDung.getValueAt(this.row, 0);
        NguoiDung nd = dao.selectById(mand);
        this.setForm(nd);
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    void edit(String maND) {
        NguoiDung ch = dao.selectById(maND);
        this.setForm(ch);
        for (int i = 0; i < tblNguoiDung.getRowCount(); i++) {
            if (tblNguoiDung.getValueAt(i, 0).equals(ch.getMaND())) {
                tblNguoiDung.setRowSelectionInterval(i, i);
                this.row = tblNguoiDung.getSelectedRow();
            }
        }
        btnTabCapNhat.doClick();
        this.updateStatus();
    }

    void insert() {
        try {
            int checkDienThoai = dao.selectExistsInsertPhone(txtDienThoai.getText());
            int checkEmail = dao.selectExistsInsertEmail(txtEmail.getText());
            NguoiDung check = dao.selectById(txtMaND.getText());
            NguoiDung checkHistory = dao.selectHistoryById(txtMaND.getText());
            if (check != null) {
                MsgBox.alert(this, "Mã người dùng đã tồn tại");
                txtMaND.requestFocus();
            } else if (checkHistory != null) {
                MsgBox.alert(this, "Mã người dùng đã tồn tại trong lịch sử người dùng.");
                txtMaND.requestFocus();
            } else if (!validateForm()) {

            } else if (checkDienThoai > 0) {
                MsgBox.alert(this, "Số điện thoại đã tồn tại");
                txtDienThoai.requestFocus();
            } else if (checkEmail > 0) {
                MsgBox.alert(this, "Email đã tồn tại");
                txtEmail.requestFocus();
            } else {
                try {
                    NguoiDung nd = getForm();
                    String password = StrongPasswordGenerator.generateStrongPassword(12);
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String hashedPassword = argon2.hash(10, 65536, 1, password);
                    nd.setMatKhau(hashedPassword);
                    dao.insert(nd);
                    this.fillTable();
                    MsgBox.copy(this, "Thêm mới thành công!." + "\nTên đăng nhập: " + txtMaND.getText() + "\n"
                            + "Mật khẩu: " + password);
                    this.clearForm();
                } catch (Exception e) {
                    MsgBox.alert(this, "Thêm mới thất bại!");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn");
        }
    }
    
    //TEST INSERT NGUOI DUNG
    public String insert(String id, String fullname, String gender, String birthday, String phone,
	String email, String note) {
    	String notification = "";
        try {
        	String checkMaND = "^PC+\\d+(\\d+)+";
            String checkPhone = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
            String checkEmailFormat = "\\w+@\\w+(\\.\\w+){1,2}";
            int checkDienThoai = dao.selectExistsInsertPhone(phone);
            int checkEmail = dao.selectExistsInsertEmail(email);
            NguoiDung check = dao.selectById(id);
            NguoiDung checkHistory = dao.selectHistoryById(id);
            if (check != null) {
            	notification = "Mã người dùng đã tồn tại";
            } else if (checkHistory != null) {
            	notification = "Mã người dùng đã tồn tại trong lịch sử người dùng.";
            } else if (id.equals("")) {
                notification = "Mã người dùng không được bỏ trống";
            } else if (fullname.equals("")) {
            	notification ="Họ tên người dùng không được bỏ trống";
            } else if (gender.equals("")) {
            	notification = "Vui lòng chọn giới tính";
            } else if (birthday.equals("")) {
            	notification ="Vui lòng chọn ngày sinh";
            } else if (phone.equals("")) {
            	notification ="Vui lòng nhập số điện thoại";
            } else if (email.equals("")) {
            	notification ="Vui lòng nhập email";
            } else if (!id.matches(checkMaND)) {
            	notification = "Mã người dùng không đúng định dạng (VD: PC01)";
            } else if (!phone.trim().matches(checkPhone)) {
            	notification ="Số điện thoại không đúng định dạng";
            } else if (!email.trim().matches(checkEmailFormat)) {
            	notification = "Email không đúng định dạng (VD: example@gmail.com)";
            } else if (checkDienThoai > 0) {
                notification = "Số điện thoại đã tồn tại";
            } else if (checkEmail > 0) {
            	notification = "Email đã tồn tại";
            } else {
                try {
                    NguoiDung nd = new NguoiDung();
                    String password = StrongPasswordGenerator.generateStrongPassword(12);
                    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
                    String hashedPassword = argon2.hash(10, 65536, 1, password);
                    nd.setMaND(id);
                    nd.setHoTen(fullname);
                    if (gender == "Nam") {
                    	nd.setGioiTinh(true);                    	
                    } else if (gender == "Nữ") {
                    	nd.setGioiTinh(false);                    	                    	
                    }
                    nd.setNgaySinh(XDate.toDate(birthday, "dd/MM/yyyy"));
                    nd.setDienThoai(phone);
                    nd.setEmail(email);
                    nd.setGhiChu(note);
//                    dao.insert(nd);
                    notification =  "Thêm mới thành công!." + "\nTên đăng nhập: " + id + "\n" + "Mật khẩu: " + password;
                } catch (Exception e) {
                	notification = "Thêm mới thất bại!";
                }
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn");
        }
        return notification;
    }

    private boolean validateForm() {
        String hoTen = txtHoTen.getText();
        String checkMaND = "^PC+\\d+(\\d+)+";
        String checkPhone = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        String checkEmail = "\\w+@\\w+(\\.\\w+){1,2}";
        if (txtMaND.getText().equals("")) {
            MsgBox.alert(this, "Mã người dùng không được bỏ trống");
            txtMaND.requestFocus();
        } else if (hoTen.equals("")) {
            MsgBox.alert(this, "Họ tên người dùng không được bỏ trống");
            txtHoTen.requestFocus();
        } else if (!rdoMale.isSelected() && !rdoFemale.isSelected()) {
            MsgBox.alert(this, "Vui lòng chọn giới tính");
            rdoMale.requestFocus();
        } else if (txtNgaySinh.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng chọn ngày sinh");
            txtNgaySinh.requestFocus();
        } else if (txtDienThoai.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập số điện thoại");
            txtDienThoai.requestFocus();
        } else if (txtEmail.getText().equals("")) {
            MsgBox.alert(this, "Vui lòng nhập email");
            txtEmail.requestFocus();
        } else if (!txtMaND.getText().matches(checkMaND)) {
            JOptionPane.showMessageDialog(this, "Mã người dùng không đúng định dạng (VD: PC01)", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaND.requestFocus();
        } else if (!txtDienThoai.getText().trim().matches(checkPhone)) {
            MsgBox.alert(this, "Số điện thoại không đúng định dạng");
            txtDienThoai.requestFocus();
        } else if (!txtEmail.getText().trim().matches(checkEmail)) {
            MsgBox.alert(this, "Email không đúng định dạng (VD: example@gmail.com)");
            txtEmail.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    void update() {
        try {
            int checkDienThoai = dao.selectExistsUpdatePhone(txtDienThoai.getText(), txtMaND.getText());
            int checkEmail = dao.selectExistsUpdateEmail(txtEmail.getText(), txtMaND.getText());

            if (!validateForm()) {

            } else if (checkDienThoai > 0) {
                MsgBox.alert(this, "Số điện thoại đã tồn tại");
                txtDienThoai.requestFocus();
            } else if (checkEmail > 0) {
                MsgBox.alert(this, "Email đã tồn tại");
                txtEmail.requestFocus();
            } else {
                try {
                    NguoiDung nv = getForm();
                    dao.update(nv);
                    this.clearForm();
                    this.fillTable();
                    MsgBox.alert(this, "Cập nhật thành công!");
                } catch (Exception e) {
                    MsgBox.alert(this, "Cập nhật thất bại!");
                }
            }
        } catch (SQLException ex) {
            MsgBox.alert(this, "Lỗi truy vấn");
        }
    }

    //TEST UPDATE
    public String update(String id, String fullname,String gender,String birthday,String phone,String email,String note) {
        String notification = "";
    	try {
    		String checkPhone = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
            String checkEmailFormat = "\\w+@\\w+(\\.\\w+){1,2}";
            int checkDienThoai = dao.selectExistsUpdatePhone(phone, id);
            int checkEmail = dao.selectExistsUpdateEmail(email, id);
            NguoiDung check = dao.selectById(id);
            if (check == null) {
            	notification = "Người dùng chưa có tài khoản!";
            } else if (fullname.equals("")) {
            	notification ="Họ tên người dùng không được bỏ trống";
            } else if (gender.equals("")) {
            	notification = "Vui lòng chọn giới tính";
            } else if (birthday.equals("")) {
            	notification ="Vui lòng chọn ngày sinh";
            } else if (phone.equals("")) {
            	notification ="Vui lòng nhập số điện thoại";
            } else if (email.equals("")) {
            	notification ="Vui lòng nhập email";
            } else if (!phone.trim().matches(checkPhone)) {
            	notification ="Số điện thoại không đúng định dạng";
            } else if (!email.trim().matches(checkEmailFormat)) {
            	notification = "Email không đúng định dạng (VD: example@gmail.com)";
            } else if (checkDienThoai > 0) {
                notification = "Số điện thoại đã tồn tại";
            } else if (checkEmail > 0) {
            	notification = "Email đã tồn tại";
            } else if (checkDienThoai > 0) {
                MsgBox.alert(this, "Số điện thoại đã tồn tại");
                txtDienThoai.requestFocus();
            } else if (checkEmail > 0) {
                MsgBox.alert(this, "Email đã tồn tại");
                txtEmail.requestFocus();
            } else {
                try {
                    NguoiDung nd = new NguoiDung();
                    nd.setHoTen(fullname);
                    if (gender == "Nam") {
                    	nd.setGioiTinh(true);                    	
                    } else if (gender == "Nữ") {
                    	nd.setGioiTinh(false);                    	                    	
                    }
                    nd.setNgaySinh(XDate.toDate(birthday, "dd/MM/yyyy"));
                    nd.setDienThoai(phone);
                    nd.setEmail(email);
                    nd.setGhiChu(note);
//                    dao.update(nd);
                    notification = "Cập nhật thành công!";
                } catch (Exception e) {
                    notification = "Cập nhật thất bại!";
                }
            }
        } catch (SQLException ex) {
            MsgBox.alert(this, "Lỗi truy vấn");
        }
    	return notification;
    }

    
    void delete() {
        if (!Auth.isAdmin()) {
            MsgBox.alert(this, "Bạn không có quyền xóa người dùng");
        } else {
            String maND = txtMaND.getText();
            if (MsgBox.confirm(this, "Bạn thực sự muốn xóa người dùng này?")) {
                try {
                    dao.deleteTemporary(maND);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Xóa thành công");
                } catch (Exception e) {
                    MsgBox.alert(this, "Xóa thất bại");

                }
            }
        }
    }
  //TEST DELEtE
    public String delete(String id) {
    	String notification = "";
        if (!Auth.isAdmin()) {
        	notification = "Bạn không có quyền xóa người dùng";
        } else {
        	String maND = id;
        	NguoiDung check = dao.selectById(maND);
        	if (check != null) {
        		try {
//                dao.deleteTemporary(maND);
        			notification = "Xóa thành công";
        		} catch (Exception e) {
        			notification = "Xóa thất bại";
        		}
        	} else {
        		notification = "Người dùng không tồn tại!";
        	}
        }
        return notification;
    }
    
    void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNguoiDung.getRowCount() - 1);
        //Trạng thái form
        btnThem.setEnabled(!edit);
        txtMaND.setEditable(!edit);
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
        if (this.row < tblNguoiDung.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    void last() {
        this.row = tblNguoiDung.getRowCount() - 1;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listGoiY = new javax.swing.JList<>();
        popupMenu = new javax.swing.JPopupMenu();
        popup = new javax.swing.JPopupMenu();
        calendar = new com.github.lgooddatepicker.components.CalendarPanel();
        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
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
        lblNgaySinh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMoTa = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        rdoMale = new javax.swing.JRadioButton();
        rdoFemale = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        txtMaND = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtNgaySinh = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtDienThoai = new javax.swing.JTextField();
        lblNgayTao = new javax.swing.JLabel();
        txtNgayDK = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiDung = new javax.swing.JTable();

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

        calendar.setBackground(new java.awt.Color(255, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Quản lý người dùng");

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

        btnThem.setText("Thêm");
        btnThem.setFocusable(false);
        btnThem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.setFocusable(false);
        btnSua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
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

        btnMoi.setText("Mới");
        btnMoi.setFocusable(false);
        btnMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
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

        btnPre.setText("<<");
        btnPre.setFocusable(false);
        btnPre.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreActionPerformed(evt);
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

        btnLast.setText(">|");
        btnLast.setFocusable(false);
        btnLast.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblNgaySinh.setText("Ngày sinh");
        lblNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel2.setText("Mã người dùng");
        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel7.setText("Địa chỉ email");
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        jLabel3.setText("Họ và tên");
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        lblMoTa.setText("Ghi chú");
        lblMoTa.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtGhiChu.setColumns(20);
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtGhiChu.setRows(5);
        jScrollPane3.setViewportView(txtGhiChu);

        jLabel4.setText("Giới tính");
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        rdoMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoMale);
        rdoMale.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdoMale.setText("Nam");
        rdoMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoMaleActionPerformed(evt);
            }
        });

        rdoFemale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoFemale);
        rdoFemale.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdoFemale.setText("Nữ");

        jLabel5.setText("Điện thoại");
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

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

        txtNgaySinh.setEditable(false);
        txtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgaySinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtNgaySinh.setOpaque(false);
        txtNgaySinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgaySinhFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgaySinhFocusLost(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtEmail.setOpaque(false);
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });

        txtDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtDienThoai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtDienThoai.setOpaque(false);
        txtDienThoai.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDienThoaiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDienThoaiFocusLost(evt);
            }
        });
        txtDienThoai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDienThoaiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDienThoaiKeyTyped(evt);
            }
        });

        lblNgayTao.setText("Ngày đăng ký");
        lblNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        txtNgayDK.setEditable(false);
        txtNgayDK.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgayDK.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtNgayDK.setOpaque(false);
        txtNgayDK.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgayDKFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayDKFocusLost(evt);
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

        jLabel8.setText("Mã nhân viên");
        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                        .addComponent(btnFirst)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNext)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLast))
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(lblNgayTao)
                            .addComponent(txtNgayDK, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(txtDienThoai))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV)
                            .addComponent(txtEmail)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdoMale)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rdoFemale)))
                        .addGap(212, 212, 212)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgaySinh)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblNgaySinh)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblMoTa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(6, 6, 6)
                        .addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoMale)
                            .addComponent(rdoFemale)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNgaySinh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNgayTao)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgayDK, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(lblMoTa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi)
                    .addComponent(btnFirst)
                    .addComponent(btnPre)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

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
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setFocusable(false);
        jScrollPane2.setOpaque(false);

        tblNguoiDung.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "MÃ NGƯỜI DÙNG", "HỌ TÊN", "NGÀY SINH", "GIỚI TÍNH", "ĐIỆN THOẠI", "EMAIL", "MÃ NV", "NGÀY ĐK", "GHI CHÚ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        txtTimKiem.requestFocus();
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

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        this.first();
        tblNguoiDung.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreActionPerformed
        this.prev();
        tblNguoiDung.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnPreActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        this.next();
        tblNguoiDung.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        this.last();
        tblNguoiDung.setRowSelectionInterval(this.row, this.row);
    }//GEN-LAST:event_btnLastActionPerformed

    private void txtMaNDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNDFocusGained
        txtMaND.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaNDFocusGained

    private void txtMaNDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNDFocusLost
        txtMaND.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaNDFocusLost

    private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusGained
        txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtHoTenFocusGained

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtHoTenFocusLost

    private void txtNgaySinhFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgaySinhFocusGained
        txtNgaySinh.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        popup.show(txtNgaySinh, 0, txtNgaySinh.getHeight());
    }//GEN-LAST:event_txtNgaySinhFocusGained

    private void txtNgaySinhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgaySinhFocusLost
        txtNgaySinh.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtNgaySinhFocusLost

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtDienThoaiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDienThoaiFocusGained
        txtDienThoai.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtDienThoaiFocusGained

    private void txtDienThoaiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDienThoaiFocusLost
        txtDienThoai.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtDienThoaiFocusLost

    private void rdoMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoMaleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoMaleActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        fillTableTimKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        txtTimKiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        try {
            String timKiem = txtTimKiem.getText().trim();
            if (!timKiem.equals("")) {
                model.removeAllElements();
                List<NguoiDung> list = dao.findUser(timKiem);
                for (NguoiDung q : list) {
                    model.addElement(q);
                }
                popupMenu.show(txtTimKiem, 0, txtTimKiem.getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    //TEST FIND USER
    public List<NguoiDung> findUser(String keyword) {//GEN-FIRST:event_txtTimKiemFocusGained
        try {
            String timKiem = keyword;
            if (!timKiem.equals("")) {
                List<NguoiDung> list = dao.findUser(timKiem);
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
                List<NguoiDung> list = dao.findUser(timKiem);
                for (NguoiDung q : list) {
                    model.addElement(q);
                }
                popupMenu.show(txtTimKiem, 0, txtTimKiem.getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void tblNguoiDungMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiDungMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblNguoiDung.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
            txtMaND.requestFocus();
        }
    }//GEN-LAST:event_tblNguoiDungMouseClicked

    private void txtMaNVFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVFocusGained

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNVFocusLost

    private void txtNgayDKFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayDKFocusLost
        txtNgayDK.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtNgayDKFocusLost

    private void txtNgayDKFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayDKFocusGained
        txtNgayDK.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtNgayDKFocusGained

    private void listGoiYMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listGoiYMouseClicked
        if (listGoiY.getModel().getSize() == 0) {

        } else {
            if (evt.getClickCount() == 2) {
                NguoiDung q = listGoiY.getSelectedValue();
                this.edit(q.getMaND());
                popupMenu.setVisible(false);
            }
        }
    }//GEN-LAST:event_listGoiYMouseClicked

    private void txtDienThoaiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDienThoaiKeyTyped
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9')
                || (c == KeyEvent.VK_BACK_SPACE)
                || (c == KeyEvent.VK_DELETE))) {
            getToolkit().beep();
            evt.consume();
        } else if (txtDienThoai.getText().length() >= 10) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtDienThoaiKeyTyped

    private void txtDienThoaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDienThoaiKeyPressed
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txtDienThoaiKeyPressed

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
            java.util.logging.Logger.getLogger(NguoiDungJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NguoiDungJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                NguoiDungJDialog dialog = new NguoiDungJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private com.github.lgooddatepicker.components.CalendarPanel calendar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblMoTa;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblNgayTao;
    private javax.swing.JList<NguoiDung> listGoiY;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JPopupMenu popupMenu;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JTable tblNguoiDung;
    private javax.swing.JTextField txtDienThoai;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaND;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtNgayDK;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
