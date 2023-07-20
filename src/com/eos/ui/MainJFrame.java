package com.eos.ui;

import com.eos.untils.NoScalingIcon;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.XImage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Kienltpc04639
 */
public class MainJFrame extends javax.swing.JFrame {

	/**
	 * Creates new form MainJFrame
	 */
	public MainJFrame() {
		initComponents();
		init();
	}
	public MainJFrame(String test) {
	}

	private void init() {
		this.setLocationRelativeTo(null);
		this.setIconImage(XImage.getAppIcon());
		new ChaoJDialog(this, true).setVisible(true);
		new DangNhapJDialog(this, true).setVisible(true);
		startDongHo();
		getRootPane().setOpaque(false);
		getContentPane().setBackground(Color.white);
		setBackground(Color.white);
		icon();
		if (Auth.isLoginNguoiDung()) {
			nguoiDung();
		} else if (Auth.isLoginNhanVien()) {
			nhanVien();
		} else {
			macDinh();
		}
	}

	private void macDinh() {
		menuBar.setVisible(false);
		setSize(getWidth(), 560);
		btnKiThi.setVisible(false);
		btnNguoiDung.setVisible(false);
		btnKhoaHoc.setVisible(false);
		btnHocVien.setVisible(false);
		btnDangXuat.setVisible(false);
		btnDangNhap.setVisible(true);
	}

	private void nguoiDung() {
		menuBar.setVisible(false);
		setSize(getWidth(), 560);
		btnKiThi.setVisible(false);
		btnNguoiDung.setVisible(false);
		btnKhoaHoc.setVisible(false);
		btnHocVien.setVisible(false);
		btnDangXuat.setVisible(true);
		btnThamGiaKH.setVisible(true);
		btnHocOnline.setVisible(true);
		btnThi.setVisible(true);
		btnDangNhap.setVisible(false);
		btnKetQuaThi.setVisible(true);
		btnThongTinCaNhanNguoiDung.setVisible(true);

	}

	private void nhanVien() {
		menuBar.setVisible(true);
		setSize(getWidth(), 584);
		btnDangXuat.setVisible(true);
		btnKiThi.setVisible(true);
		btnNguoiDung.setVisible(true);
		btnKhoaHoc.setVisible(true);
		btnHocVien.setVisible(true);
		btnThamGiaKH.setVisible(false);
		btnHocOnline.setVisible(false);
		btnThi.setVisible(false);
		btnDangNhap.setVisible(false);
		mniDangNhap.setVisible(false);
		btnKetQuaThi.setVisible(false);
		btnThongTinCaNhanNguoiDung.setVisible(false);

	}

	private void icon() {
		NoScalingIcon icon = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\close.png"));
		btnKetThuc.setIcon(icon);
		btnKetThuc.setText("Kết thúc");
		mniKetThuc.setIcon(icon);
		NoScalingIcon icon1 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\exit.png"));
		btnDangXuat.setIcon(icon1);
		btnDangXuat.setText("Đăng xuất");
		mniDangXuat.setIcon(icon1);
		NoScalingIcon icon2 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\exam.png"));
		btnKiThi.setIcon(icon2);
		mniKiThi.setIcon(icon2);
		NoScalingIcon icon3 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\user.png"));
		btnNguoiDung.setIcon(icon3);
		mniLichSuNguoiDung.setIcon(icon3);
		mniNguoiDung.setIcon(icon3);
		btnThongTinCaNhanNguoiDung.setIcon(icon3);
		NoScalingIcon icon4 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\course.png"));
		btnKhoaHoc.setIcon(icon4);
		mniKhoaHoc.setIcon(icon4);
		mniLichSuKH.setIcon(icon4);
		NoScalingIcon icon5 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\student.png"));
		btnHocVien.setIcon(icon5);
		mniHocVien.setIcon(icon5);
		NoScalingIcon icon6 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\score.png"));
		NoScalingIcon icon7 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\test.png"));
		NoScalingIcon icon8 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\question.png"));
		NoScalingIcon icon9 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\q&a.png"));
		mniCauHoi.setIcon(icon9);
		NoScalingIcon icon10 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\lesson.png"));
		mniBaiHoc.setIcon(icon10);
		NoScalingIcon icon11 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\chapter.png"));
		mniChuong.setIcon(icon11);
		NoScalingIcon icon12 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\staff.png"));
		mniNhanVien.setIcon(icon12);
		mniLichSuNV.setIcon(icon12);
		NoScalingIcon icon13 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\introduce.png"));
		mniGioiThieu.setIcon(icon13);
		NoScalingIcon icon14 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\reset-password.png"));
		mniDoiMatKhau.setIcon(icon14);
		NoScalingIcon icon15 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\Alarm.png"));
		lblDongHo.setIcon(icon15);
		NoScalingIcon icon16 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\info.png"));
		lblTrangThai.setIcon(icon16);
		NoScalingIcon icon17 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\enroll.png"));
		btnThamGiaKH.setIcon(icon17);
		NoScalingIcon icon18 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\study-online.png"));
		btnHocOnline.setIcon(icon18);
		NoScalingIcon icon19 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\exam-online.png"));
		btnThi.setIcon(icon19);
		NoScalingIcon icon20 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\login.png"));
		btnDangNhap.setIcon(icon20);
		mniDangNhap.setIcon(icon20);
		btnDangNhap.setText("Đăng nhập");
		NoScalingIcon icon21 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\answer.png"));
		btnKetQuaThi.setIcon(icon21);
		NoScalingIcon icon22 = new NoScalingIcon(new ImageIcon("src\\com\\eos\\icon\\exam-result.png"));
		mniDeThi.setIcon(icon22);
		btnDoiMatKhau.setText("Đổi mật khẩu");
		btnDoiMatKhau.setIcon(icon14);
	}

	void startDongHo() {
		SimpleDateFormat formater = new SimpleDateFormat("hh:mm:ss a");
		new Timer(1000, ((ActionEvent e) -> {
			lblDongHo.setText(formater.format(new Date()));
		})).start();

	}

	void openDoiMatKhau() {
		if (Auth.isLoginNhanVien() || Auth.isLoginNguoiDung()) {
			new DoiMatKhauJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openGioiThieu() {
		new GioiThieuJDialog(this, true).setVisible(true);
	}

	void openKhoaHoc() {
		if (Auth.isLoginNhanVien()) {
			new KhoaHocJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openKyThi() {
		if (Auth.isLoginNhanVien()) {
			new KyThiJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openBaiHoc() {
		if (Auth.isLoginNhanVien()) {
			new BaiHocJFrame().setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openChuong() {
		if (Auth.isLoginNhanVien()) {
			new ChuongJFrame().setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openDeThi() {
		if (Auth.isLoginNhanVien()) {
			new DeThiJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openThongTinCaNhanNguoiDung() {
		if (Auth.isLoginNguoiDung()) {
			new ThongTinCaNhanJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openNguoiDung() {
		if (Auth.isLoginNhanVien()) {
			new NguoiDungJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openCauHoi() {
		if (Auth.isLoginNhanVien()) {
			new CauHoiJFrame().setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openHocVien() {
		if (Auth.isLoginNhanVien()) {
			new HocVienJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openNhanVien() {
		if (Auth.isAdmin()) {
			new NhanVienJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openLichSuNhanVien() {
		if (Auth.isAdmin()) {
			new LichSuNhanVienJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openLichSuKhoaHoc() {
		if (Auth.isAdmin()) {
			new LichSuKhoaHocJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openLichSuNguoiDung() {
		if (Auth.isAdmin()) {
			new LichSuNguoiDungJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openThamGiaKhoaHoc() {
		if (Auth.isLoginNguoiDung()) {
			new ThamGiaKHJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openKetQuaThi() {
		if (Auth.isLoginNguoiDung()) {
			new XemKetQuaJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openThamGiaThi() {
		if (Auth.isLoginNguoiDung()) {
			new ThamGiaKyThiJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openHocOnline() {
		if (Auth.isLoginNguoiDung()) {
			new VaoHocJDialog(this, true).setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void openThi() {
		if (Auth.isLoginNguoiDung()) {
//            new Q().setVisible(true);
		} else {
			MsgBox.alert(this, "Vui lòng đăng nhập");
		}
	}

	void dangXuat() {
		Auth.clear();
		new DangNhapJDialog(this, true).setVisible(true);
		if (Auth.isLoginNguoiDung()) {
			nguoiDung();
		} else if (Auth.isLoginNhanVien()) {
			nhanVien();
		} else {
			mniDangXuat.setVisible(false);
			btnDangXuat.setVisible(false);
			mniDangNhap.setVisible(true);
			btnDangNhap.setVisible(true);
		}
	}

	public String logout() {
		if (Auth.isLoginNguoiDung()) {
			Auth.clear();
			return "Đăng xuất tài khoản thành công";
		} else if (Auth.isLoginNhanVien()) {
			Auth.clear();
			return "Đăng xuất tài khoản thành công";
		} else {
			return "Đăng xuất tài khoản thất bại";
		}
	}

	void ketThuc() {
		if (MsgBox.confirm(this, "Bạn muốn kết thúc làm việc?")) {
			System.exit(0);
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

		toolBar = new javax.swing.JToolBar();
		btnDangXuat = new javax.swing.JButton();
		btnDangNhap = new javax.swing.JButton();
		btnKetThuc = new javax.swing.JButton();
		btnThongTinCaNhanNguoiDung = new javax.swing.JButton();
		btnDoiMatKhau = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JToolBar.Separator();
		btnKiThi = new javax.swing.JButton();
		btnNguoiDung = new javax.swing.JButton();
		btnKhoaHoc = new javax.swing.JButton();
		btnHocVien = new javax.swing.JButton();
		btnThamGiaKH = new javax.swing.JButton();
		btnHocOnline = new javax.swing.JButton();
		btnThi = new javax.swing.JButton();
		btnKetQuaThi = new javax.swing.JButton();
		pnlTrump = new javax.swing.JPanel();
		lblLogo = new javax.swing.JLabel();
		pnStatusBar = new javax.swing.JPanel();
		lblTrangThai = new javax.swing.JLabel();
		lblDongHo = new javax.swing.JLabel();
		menuBar = new javax.swing.JMenuBar();
		mnuHeThong = new javax.swing.JMenu();
		mniDangXuat = new javax.swing.JMenuItem();
		jSeparator3 = new javax.swing.JPopupMenu.Separator();
		mniDangNhap = new javax.swing.JMenuItem();
		mniDoiMatKhau = new javax.swing.JMenuItem();
		jSeparator4 = new javax.swing.JPopupMenu.Separator();
		mniKetThuc = new javax.swing.JMenuItem();
		mnuQuanLy = new javax.swing.JMenu();
		mniKhoaHoc = new javax.swing.JMenuItem();
		mniNguoiDung = new javax.swing.JMenuItem();
		mniHocVien = new javax.swing.JMenuItem();
		mniCauHoi = new javax.swing.JMenuItem();
		mniKiThi = new javax.swing.JMenuItem();
		mniBaiHoc = new javax.swing.JMenuItem();
		mniChuong = new javax.swing.JMenuItem();
		jSeparator5 = new javax.swing.JPopupMenu.Separator();
		mniDeThi = new javax.swing.JMenuItem();
		mniNhanVien = new javax.swing.JMenuItem();
		jMenu1 = new javax.swing.JMenu();
		mniLichSuNV = new javax.swing.JMenuItem();
		mniLichSuKH = new javax.swing.JMenuItem();
		mniLichSuNguoiDung = new javax.swing.JMenuItem();
		mnuTroGiup = new javax.swing.JMenu();
		mniGioiThieu = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("EOSys - HỆ THỐNG HỌC TRỰC TUYẾN E-LEARNING");
		setBackground(new java.awt.Color(255, 255, 255));
		setIconImages(null);

		toolBar.setBackground(new java.awt.Color(255, 255, 255));
		toolBar.setRollover(true);

		btnDangXuat.setBackground(new java.awt.Color(255, 255, 255));
		btnDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnDangXuat.setFocusable(false);
		btnDangXuat.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnDangXuat.setMargin(new java.awt.Insets(2, 5, 2, 14));
		btnDangXuat.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDangXuatActionPerformed(evt);
			}
		});
		toolBar.add(btnDangXuat);

		btnDangNhap.setBackground(new java.awt.Color(255, 255, 255));
		btnDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnDangNhap.setFocusable(false);
		btnDangNhap.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnDangNhap.setMargin(new java.awt.Insets(2, 5, 2, 14));
		btnDangNhap.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDangNhapActionPerformed(evt);
			}
		});
		toolBar.add(btnDangNhap);

		btnKetThuc.setBackground(new java.awt.Color(255, 255, 255));
		btnKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnKetThuc.setText("Kết thúc");
		btnKetThuc.setFocusable(false);
		btnKetThuc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnKetThuc.setMargin(new java.awt.Insets(2, 9, 2, 9));
		btnKetThuc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnKetThuc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnKetThucActionPerformed(evt);
			}
		});
		toolBar.add(btnKetThuc);

		btnThongTinCaNhanNguoiDung.setBackground(new java.awt.Color(255, 255, 255));
		btnThongTinCaNhanNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnThongTinCaNhanNguoiDung.setText("Thông tin cá nhân");
		btnThongTinCaNhanNguoiDung.setFocusable(false);
		btnThongTinCaNhanNguoiDung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnThongTinCaNhanNguoiDung.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnThongTinCaNhanNguoiDung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThongTinCaNhanNguoiDungActionPerformed(evt);
			}
		});
		toolBar.add(btnThongTinCaNhanNguoiDung);

		btnDoiMatKhau.setBackground(new java.awt.Color(255, 255, 255));
		btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnDoiMatKhau.setFocusable(false);
		btnDoiMatKhau.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnDoiMatKhau.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDoiMatKhauActionPerformed(evt);
			}
		});
		toolBar.add(btnDoiMatKhau);

		jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		jSeparator1.setOpaque(true);
		toolBar.add(jSeparator1);

		btnKiThi.setBackground(new java.awt.Color(255, 255, 255));
		btnKiThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnKiThi.setText("Kỳ thi");
		btnKiThi.setFocusable(false);
		btnKiThi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnKiThi.setMargin(new java.awt.Insets(2, 9, 2, 9));
		btnKiThi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnKiThi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnKiThiActionPerformed(evt);
			}
		});
		toolBar.add(btnKiThi);

		btnNguoiDung.setBackground(new java.awt.Color(255, 255, 255));
		btnNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnNguoiDung.setText("Người dùng");
		btnNguoiDung.setFocusable(false);
		btnNguoiDung.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnNguoiDung.setMargin(new java.awt.Insets(2, 9, 2, 9));
		btnNguoiDung.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnNguoiDung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNguoiDungActionPerformed(evt);
			}
		});
		toolBar.add(btnNguoiDung);

		btnKhoaHoc.setBackground(new java.awt.Color(255, 255, 255));
		btnKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnKhoaHoc.setText("Khóa học");
		btnKhoaHoc.setFocusable(false);
		btnKhoaHoc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnKhoaHoc.setMargin(new java.awt.Insets(2, 9, 2, 9));
		btnKhoaHoc.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnKhoaHocActionPerformed(evt);
			}
		});
		toolBar.add(btnKhoaHoc);

		btnHocVien.setBackground(new java.awt.Color(255, 255, 255));
		btnHocVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnHocVien.setText("Học viên");
		btnHocVien.setFocusable(false);
		btnHocVien.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnHocVien.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnHocVien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHocVienActionPerformed(evt);
			}
		});
		toolBar.add(btnHocVien);

		btnThamGiaKH.setBackground(new java.awt.Color(255, 255, 255));
		btnThamGiaKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnThamGiaKH.setText("Tham gia khóa học");
		btnThamGiaKH.setFocusable(false);
		btnThamGiaKH.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnThamGiaKH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnThamGiaKH.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThamGiaKHActionPerformed(evt);
			}
		});
		toolBar.add(btnThamGiaKH);

		btnHocOnline.setBackground(new java.awt.Color(255, 255, 255));
		btnHocOnline.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnHocOnline.setText("Học online");
		btnHocOnline.setFocusable(false);
		btnHocOnline.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnHocOnline.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnHocOnline.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHocOnlineActionPerformed(evt);
			}
		});
		toolBar.add(btnHocOnline);

		btnThi.setBackground(new java.awt.Color(255, 255, 255));
		btnThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnThi.setText("Thi");
		btnThi.setFocusable(false);
		btnThi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnThi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnThi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThiActionPerformed(evt);
			}
		});
		toolBar.add(btnThi);

		btnKetQuaThi.setBackground(new java.awt.Color(255, 255, 255));
		btnKetQuaThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		btnKetQuaThi.setText("Kết quả thi");
		btnKetQuaThi.setFocusable(false);
		btnKetQuaThi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		btnKetQuaThi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		btnKetQuaThi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnKetQuaThiActionPerformed(evt);
			}
		});
		toolBar.add(btnKetQuaThi);

		pnlTrump.setLayout(new java.awt.GridLayout(1, 0));

		lblLogo.setBackground(new java.awt.Color(255, 255, 255));
		lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eos/icon/home1.jpg"))); // NOI18N
		lblLogo.setOpaque(true);
		pnlTrump.add(lblLogo);

		pnStatusBar.setBackground(new java.awt.Color(255, 255, 255));

		lblTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		lblTrangThai.setText("Hệ quản lý đào tạo");

		lblDongHo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		lblDongHo.setText("09:07:47 AM");

		javax.swing.GroupLayout pnStatusBarLayout = new javax.swing.GroupLayout(pnStatusBar);
		pnStatusBar.setLayout(pnStatusBarLayout);
		pnStatusBarLayout
				.setHorizontalGroup(pnStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(pnStatusBarLayout.createSequentialGroup().addContainerGap().addComponent(lblTrangThai)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblDongHo).addContainerGap()));
		pnStatusBarLayout
				.setVerticalGroup(pnStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								pnStatusBarLayout.createSequentialGroup().addGap(2, 2, 2)
										.addGroup(pnStatusBarLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(lblTrangThai).addComponent(lblDongHo))
										.addGap(2, 2, 2)));

		mnuHeThong.setText("Hệ thống");

		mniDangXuat.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniDangXuat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniDangXuat.setText("Đăng xuất");
		mniDangXuat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniDangXuatActionPerformed(evt);
			}
		});
		mnuHeThong.add(mniDangXuat);
		mnuHeThong.add(jSeparator3);

		mniDangNhap.setText("Đăng nhập");
		mniDangNhap.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniDangNhapActionPerformed(evt);
			}
		});
		mnuHeThong.add(mniDangNhap);

		mniDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniDoiMatKhau.setText("Đổi mật khẩu");
		mniDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniDoiMatKhauActionPerformed(evt);
			}
		});
		mnuHeThong.add(mniDoiMatKhau);
		mnuHeThong.add(jSeparator4);

		mniKetThuc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
		mniKetThuc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniKetThuc.setText("Kết thúc");
		mniKetThuc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniKetThucActionPerformed(evt);
			}
		});
		mnuHeThong.add(mniKetThuc);

		menuBar.add(mnuHeThong);

		mnuQuanLy.setText("Quản lý");

		mniKhoaHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniKhoaHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniKhoaHoc.setText("Khóa học");
		mniKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniKhoaHocActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniKhoaHoc);

		mniNguoiDung.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniNguoiDung.setText("Người dùng");
		mniNguoiDung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniNguoiDungActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniNguoiDung);

		mniHocVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniHocVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniHocVien.setText("Học viên");
		mniHocVien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniHocVienActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniHocVien);

		mniCauHoi.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniCauHoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniCauHoi.setText("Câu hỏi");
		mniCauHoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniCauHoiActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniCauHoi);

		mniKiThi.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniKiThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniKiThi.setText("Kỳ thi");
		mniKiThi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniKiThiActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniKiThi);

		mniBaiHoc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniBaiHoc.setText("Bài học");
		mniBaiHoc.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniBaiHoc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniBaiHocActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniBaiHoc);

		mniChuong.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniChuong.setText("Chương");
		mniChuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniChuong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniChuongActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniChuong);
		mnuQuanLy.add(jSeparator5);

		mniDeThi.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F8,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniDeThi.setText("Đề Thi");
		mniDeThi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniDeThiActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniDeThi);

		mniNhanVien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F9,
				java.awt.event.InputEvent.CTRL_DOWN_MASK));
		mniNhanVien.setText("Nhân viên");
		mniNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniNhanVien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniNhanVienActionPerformed(evt);
			}
		});
		mnuQuanLy.add(mniNhanVien);

		menuBar.add(mnuQuanLy);

		jMenu1.setText("Lịch sử");

		mniLichSuNV.setText("Nhân viên");
		mniLichSuNV.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniLichSuNVActionPerformed(evt);
			}
		});
		jMenu1.add(mniLichSuNV);

		mniLichSuKH.setText("Khóa học");
		mniLichSuKH.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniLichSuKHActionPerformed(evt);
			}
		});
		jMenu1.add(mniLichSuKH);

		mniLichSuNguoiDung.setText("Người dùng");
		mniLichSuNguoiDung.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniLichSuNguoiDungActionPerformed(evt);
			}
		});
		jMenu1.add(mniLichSuNguoiDung);

		menuBar.add(jMenu1);

		mnuTroGiup.setText("Trợ giúp");
		mnuTroGiup.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N

		mniGioiThieu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
		mniGioiThieu.setText("Giới thiệu phần mềm");
		mniGioiThieu.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mniGioiThieuActionPerformed(evt);
			}
		});
		mnuTroGiup.add(mniGioiThieu);

		menuBar.add(mnuTroGiup);

		setJMenuBar(menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(pnStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(pnlTrump, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, 0)
						.addComponent(pnlTrump, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(pnStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDangXuatActionPerformed
		this.dangXuat();
	}// GEN-LAST:event_btnDangXuatActionPerformed

	private void mniHocVienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniHocVienActionPerformed
		this.openHocVien();
	}// GEN-LAST:event_mniHocVienActionPerformed

	private void mniNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniNguoiDungActionPerformed
		this.openNguoiDung();
	}// GEN-LAST:event_mniNguoiDungActionPerformed

	private void mniNhanVienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniNhanVienActionPerformed
		this.openNhanVien();
	}// GEN-LAST:event_mniNhanVienActionPerformed

	private void btnNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNguoiDungActionPerformed
		this.openNguoiDung();
	}// GEN-LAST:event_btnNguoiDungActionPerformed

	private void btnKiThiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKiThiActionPerformed
		this.openKyThi();
	}// GEN-LAST:event_btnKiThiActionPerformed

	private void btnKetThucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKetThucActionPerformed
		this.ketThuc();
	}// GEN-LAST:event_btnKetThucActionPerformed

	private void btnKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKhoaHocActionPerformed
		this.openKhoaHoc();
	}// GEN-LAST:event_btnKhoaHocActionPerformed

	private void btnHocVienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHocVienActionPerformed
		this.openHocVien();
	}// GEN-LAST:event_btnHocVienActionPerformed

	private void mniDangXuatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniDangXuatActionPerformed
		this.dangXuat();
	}// GEN-LAST:event_mniDangXuatActionPerformed

	private void mniDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniDoiMatKhauActionPerformed
		this.openDoiMatKhau();
	}// GEN-LAST:event_mniDoiMatKhauActionPerformed

	private void mniKetThucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniKetThucActionPerformed
		this.ketThuc();
	}// GEN-LAST:event_mniKetThucActionPerformed

	private void mniCauHoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniCauHoiActionPerformed
		this.openCauHoi();
	}// GEN-LAST:event_mniCauHoiActionPerformed

	private void mniKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniKhoaHocActionPerformed
		this.openKhoaHoc();
	}// GEN-LAST:event_mniKhoaHocActionPerformed

	private void mniGioiThieuActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniGioiThieuActionPerformed
		this.openGioiThieu();
	}// GEN-LAST:event_mniGioiThieuActionPerformed

	private void btnThamGiaKHActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThamGiaKHActionPerformed
		this.openThamGiaKhoaHoc();
	}// GEN-LAST:event_btnThamGiaKHActionPerformed

	private void btnHocOnlineActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHocOnlineActionPerformed
		openHocOnline();
	}// GEN-LAST:event_btnHocOnlineActionPerformed

	private void btnThiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThiActionPerformed
		this.openThamGiaThi();
	}// GEN-LAST:event_btnThiActionPerformed

	private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDangNhapActionPerformed
		dangXuat();
	}// GEN-LAST:event_btnDangNhapActionPerformed

	private void mniDangNhapActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniDangNhapActionPerformed
		dangXuat();
	}// GEN-LAST:event_mniDangNhapActionPerformed

	private void btnKetQuaThiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKetQuaThiActionPerformed
		this.openKetQuaThi();
	}// GEN-LAST:event_btnKetQuaThiActionPerformed

	private void mniKiThiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniKiThiActionPerformed
		this.openKyThi();
	}// GEN-LAST:event_mniKiThiActionPerformed

	private void mniBaiHocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniBaiHocActionPerformed
		this.openBaiHoc();
	}// GEN-LAST:event_mniBaiHocActionPerformed

	private void mniChuongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniChuongActionPerformed
		this.openChuong();
	}// GEN-LAST:event_mniChuongActionPerformed

	private void mniLichSuNVActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniLichSuNVActionPerformed
		this.openLichSuNhanVien();
	}// GEN-LAST:event_mniLichSuNVActionPerformed

	private void mniLichSuKHActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniLichSuKHActionPerformed
		this.openLichSuKhoaHoc();
	}// GEN-LAST:event_mniLichSuKHActionPerformed

	private void mniLichSuNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniLichSuNguoiDungActionPerformed
		this.openLichSuNguoiDung();
	}// GEN-LAST:event_mniLichSuNguoiDungActionPerformed

	private void btnThongTinCaNhanNguoiDungActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThongTinCaNhanNguoiDungActionPerformed
		this.openThongTinCaNhanNguoiDung();
	}// GEN-LAST:event_btnThongTinCaNhanNguoiDungActionPerformed

	private void mniDeThiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_mniDeThiActionPerformed
		this.openDeThi();
	}// GEN-LAST:event_mniDeThiActionPerformed

	private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDoiMatKhauActionPerformed
		this.openDoiMatKhau();
	}// GEN-LAST:event_btnDoiMatKhauActionPerformed

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
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainJFrame().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnDangNhap;
	private javax.swing.JButton btnDangXuat;
	private javax.swing.JButton btnDoiMatKhau;
	private javax.swing.JButton btnHocOnline;
	private javax.swing.JButton btnHocVien;
	private javax.swing.JButton btnKetQuaThi;
	private javax.swing.JButton btnKetThuc;
	private javax.swing.JButton btnKhoaHoc;
	private javax.swing.JButton btnKiThi;
	private javax.swing.JButton btnNguoiDung;
	private javax.swing.JButton btnThamGiaKH;
	private javax.swing.JButton btnThi;
	private javax.swing.JButton btnThongTinCaNhanNguoiDung;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JPopupMenu.Separator jSeparator3;
	private javax.swing.JPopupMenu.Separator jSeparator4;
	private javax.swing.JPopupMenu.Separator jSeparator5;
	private javax.swing.JLabel lblDongHo;
	private javax.swing.JLabel lblLogo;
	private javax.swing.JLabel lblTrangThai;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenuItem mniBaiHoc;
	private javax.swing.JMenuItem mniCauHoi;
	private javax.swing.JMenuItem mniChuong;
	private javax.swing.JMenuItem mniDangNhap;
	private javax.swing.JMenuItem mniDangXuat;
	private javax.swing.JMenuItem mniDeThi;
	private javax.swing.JMenuItem mniDoiMatKhau;
	private javax.swing.JMenuItem mniGioiThieu;
	private javax.swing.JMenuItem mniHocVien;
	private javax.swing.JMenuItem mniKetThuc;
	private javax.swing.JMenuItem mniKhoaHoc;
	private javax.swing.JMenuItem mniKiThi;
	private javax.swing.JMenuItem mniLichSuKH;
	private javax.swing.JMenuItem mniLichSuNV;
	private javax.swing.JMenuItem mniLichSuNguoiDung;
	private javax.swing.JMenuItem mniNguoiDung;
	private javax.swing.JMenuItem mniNhanVien;
	private javax.swing.JMenu mnuHeThong;
	private javax.swing.JMenu mnuQuanLy;
	private javax.swing.JMenu mnuTroGiup;
	private javax.swing.JPanel pnStatusBar;
	private javax.swing.JPanel pnlTrump;
	private javax.swing.JToolBar toolBar;
	// End of variables declaration//GEN-END:variables
}
