package com.eos.ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import com.eos.dao.NguoiDungDAO;
import com.eos.model.NguoiDung;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.XDate;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class ThongTinCaNhanJDialog extends javax.swing.JDialog {

	/**
	 * Creates new form NguoiDungJDialog
	 */
	NguoiDungDAO dao = new NguoiDungDAO();
	int row = -1;

	public ThongTinCaNhanJDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		init();
		popup.add(calendar);
	}

	public ThongTinCaNhanJDialog() {
		// TODO Auto-generated constructor stub
	}

	void init() {
		getRootPane().setOpaque(false);
		getContentPane().setBackground(Color.white);
		setBackground(Color.white);
		txtMaND.requestFocus();
		this.loadData();
		calendar.setLocale(Locale.forLanguageTag("vi-VN"));
		calendar.getSettings().setDateRangeLimits(LocalDate.MIN, LocalDate.now().minusYears(10));
		calendar.getSettings().setVisibleClearButton(false);
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

	void loadData() {
		try {
			NguoiDung nd = dao.selectById(Auth.user1.getMaND());
			this.setForm(nd);
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	public NguoiDung checkData() {
		NguoiDung nd = null;
		try {
			nd = dao.selectById(Auth.user1.getMaND());
			return nd;
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
		return nd;
	}

	void setForm(NguoiDung nd) {
		txtMaND.setText(nd.getMaND());
		txtHoTen.setText(nd.getHoTen());
		if (nd.getNgaySinh() != null) {
			txtNgaySinh.setText(XDate.toString(nd.getNgaySinh(), "dd/MM/yyyy"));
		}
		txtDienThoai.setText(nd.getDienThoai());
		txtEmail.setText(nd.getEmail());
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
		nd.setGioiTinh(rdoMale.isSelected());
		return nd;
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
			JOptionPane.showMessageDialog(this, "Mã người học không đúng định dạng (VD: PC01)", "Thông báo lỗi",
					JOptionPane.ERROR_MESSAGE);
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

	public String validateFormTest(String fullname, String gender, String birthday, String phone, String email) {
		String checkPhone = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
		String checkEmail = "\\w+@\\w+(\\.\\w+){1,2}";
		String notification = "";
		try {
			int checkDienThoai = dao.selectExistsUpdatePhone(phone, Auth.user1.getMaND());
			int checkEmailAddress = dao.selectExistsUpdateEmail(email, Auth.user1.getMaND());
			if (fullname.equals("")) {
				notification = "Họ tên người dùng không được bỏ trống";
			} else if (gender.equals("")) {
				notification = "Vui lòng chọn giới tính";
			} else if (birthday.equals("")) {
				notification = "Vui lòng chọn ngày sinh";
			} else if (phone.equals("")) {
				notification = "Vui lòng nhập số điện thoại";
			} else if (email.equals("")) {
				notification = "Vui lòng nhập email";
			} else if (!phone.matches(checkPhone)) {
				notification = "Số điện thoại không đúng định dạng";
			} else if (!email.matches(checkEmail)) {
				notification = "Email không đúng định dạng (VD: example@gmail.com)";
			} else if (checkDienThoai > 0) {
				notification = "Số điện thoại đã tồn tại";
			} else if (checkEmailAddress > 0) {
				notification = "Email đã tồn tại";
			} else {
				try {
					NguoiDung nd = new NguoiDung();
					nd.setMaND(Auth.user1.getMaND());
					nd.setHoTen(fullname);
					nd.setNgaySinh(XDate.toDate(birthday, "dd/MM/yyyy"));
					nd.setDienThoai(phone);
					nd.setEmail(email);
					if(gender.equals("Nam")) {
						nd.setGioiTinh(true);
					}
					else if(gender.equals("Nữ")) {
						nd.setGioiTinh(false);
						
					}
					dao.updatePersonal(nd);
					notification = "Cập nhật thành công!";
				} catch (Exception e) {
					notification = "Cập nhật thất bại!";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notification;

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
					dao.updatePersonal(nv);
					this.loadData();
					MsgBox.alert(this, "Cập nhật thành công!");
				} catch (Exception e) {
					MsgBox.alert(this, "Cập nhật thất bại!");
				}
			}
		} catch (SQLException ex) {
			MsgBox.alert(this, "Lỗi truy vấn");
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		popup = new javax.swing.JPopupMenu();
		calendar = new com.github.lgooddatepicker.components.CalendarPanel();
		jPanel1 = new javax.swing.JPanel();
		jPanel2 = new javax.swing.JPanel();
		btnSua = new javax.swing.JButton();
		lblNgaySinh = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		rdoMale = new javax.swing.JRadioButton();
		rdoFemale = new javax.swing.JRadioButton();
		jLabel5 = new javax.swing.JLabel();
		txtMaND = new javax.swing.JTextField();
		txtHoTen = new javax.swing.JTextField();
		txtNgaySinh = new javax.swing.JTextField();
		txtEmail = new javax.swing.JTextField();
		txtDienThoai = new javax.swing.JTextField();

		calendar.setBackground(new java.awt.Color(255, 255, 255));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("EOSys - Thông tin cá nhân");

		jPanel1.setLayout(new java.awt.CardLayout());

		jPanel2.setBackground(new java.awt.Color(255, 255, 255));

		btnSua.setText("Cập nhật thông tin");
		btnSua.setFocusable(false);
		btnSua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnSua.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSuaActionPerformed(evt);
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
		txtNgaySinh
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
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
		txtDienThoai
				.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
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

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel5).addComponent(txtDienThoai,
												javax.swing.GroupLayout.PREFERRED_SIZE, 290,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(txtEmail)
										.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel7).addGap(0,
												256, Short.MAX_VALUE))))
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel2).addComponent(txtMaND,
												javax.swing.GroupLayout.PREFERRED_SIZE, 290,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGap(18, 18, 18)
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel3).addGap(0,
												0, Short.MAX_VALUE))))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel2Layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
										.addComponent(btnSua))
						.addGroup(jPanel2Layout.createSequentialGroup()
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jLabel4)
										.addGroup(jPanel2Layout.createSequentialGroup().addComponent(rdoMale)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(rdoFemale)))
								.addGap(212, 212, 212)
								.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(txtNgaySinh).addGroup(jPanel2Layout.createSequentialGroup()
												.addComponent(lblNgaySinh).addGap(0, 0, Short.MAX_VALUE)))))
						.addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel2).addGap(6, 6, 6)
										.addComponent(txtMaND, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel3)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(7, 7, 7)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel4)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jPanel2Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(rdoMale).addComponent(rdoFemale)))
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(lblNgaySinh)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 25,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(jPanel2Layout.createSequentialGroup()
										.addGroup(jPanel2Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel5).addComponent(jLabel7,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
						.addComponent(btnSua).addContainerGap()));

		jPanel1.add(jPanel2, "card2");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(0, 0, 0)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void txtDienThoaiKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtDienThoaiKeyTyped
		char c = evt.getKeyChar();
		if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			getToolkit().beep();
			evt.consume();
		} else if (txtDienThoai.getText().length() >= 10) {
			getToolkit().beep();
			evt.consume();
		}
	}// GEN-LAST:event_txtDienThoaiKeyTyped

	private void txtDienThoaiKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtDienThoaiKeyPressed
		if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
			getToolkit().beep();
			evt.consume();
		}
	}// GEN-LAST:event_txtDienThoaiKeyPressed

	private void txtDienThoaiFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtDienThoaiFocusLost
		txtDienThoai.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
	}// GEN-LAST:event_txtDienThoaiFocusLost

	private void txtDienThoaiFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtDienThoaiFocusGained
		txtDienThoai.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
	}// GEN-LAST:event_txtDienThoaiFocusGained

	private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtEmailFocusLost
		txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
	}// GEN-LAST:event_txtEmailFocusLost

	private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtEmailFocusGained
		txtEmail.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
	}// GEN-LAST:event_txtEmailFocusGained

	private void txtNgaySinhFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtNgaySinhFocusLost
		txtNgaySinh.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
	}// GEN-LAST:event_txtNgaySinhFocusLost

	private void txtNgaySinhFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtNgaySinhFocusGained
		txtNgaySinh.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
		popup.show(txtNgaySinh, 0, txtNgaySinh.getHeight());
	}// GEN-LAST:event_txtNgaySinhFocusGained

	private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtHoTenFocusLost
		txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
	}// GEN-LAST:event_txtHoTenFocusLost

	private void txtHoTenFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtHoTenFocusGained
		txtHoTen.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
	}// GEN-LAST:event_txtHoTenFocusGained

	private void txtMaNDFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtMaNDFocusLost
		txtMaND.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
	}// GEN-LAST:event_txtMaNDFocusLost

	private void txtMaNDFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_txtMaNDFocusGained
		txtMaND.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
	}// GEN-LAST:event_txtMaNDFocusGained

	private void rdoMaleActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdoMaleActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_rdoMaleActionPerformed

	private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSuaActionPerformed
		this.update();
	}// GEN-LAST:event_btnSuaActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(ThongTinCaNhanJDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(ThongTinCaNhanJDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(ThongTinCaNhanJDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ThongTinCaNhanJDialog.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ThongTinCaNhanJDialog dialog = new ThongTinCaNhanJDialog(new javax.swing.JFrame(), true);
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
	private javax.swing.JButton btnSua;
	private javax.swing.ButtonGroup buttonGroup1;
	private com.github.lgooddatepicker.components.CalendarPanel calendar;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JLabel lblNgaySinh;
	private javax.swing.JPopupMenu popup;
	private javax.swing.JRadioButton rdoFemale;
	private javax.swing.JRadioButton rdoMale;
	private javax.swing.JTextField txtDienThoai;
	private javax.swing.JTextField txtEmail;
	private javax.swing.JTextField txtHoTen;
	private javax.swing.JTextField txtMaND;
	private javax.swing.JTextField txtNgaySinh;
	// End of variables declaration//GEN-END:variables
}
