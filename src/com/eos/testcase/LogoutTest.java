package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.eos.ui.DangNhapJDialog;
import com.eos.ui.MainJFrame;

public class LogoutTest {
	static DangNhapJDialog login;
	static MainJFrame main;

	public static void setUp(String role) {
		login = new DangNhapJDialog();
		if (role.equals("admin")) {
			login.dangNhap("BaoDC", "Bao1234@78910");
		} else if (role.equals("staff")) {
			login.dangNhap("KhangNC", "Khang@123456");

		} else if (role.equals("user")) {
			login.dangNhap("PC01020", "Khang123@456");
		}
	}
	@BeforeClass
	public static void start() {
		main = new MainJFrame("");
	}
	@Test
	public void testLogoutLoginWithAdminAccount() {
		setUp("admin");
		String message = main.logout();
		assertEquals(message, "Đăng xuất tài khoản thành công");
	}
	@Test
	public void testLogoutLoginWithStaffAccount() {
		setUp("staff");
		String message = main.logout();
		assertEquals(message, "Đăng xuất tài khoản thành công");
	}
	@Test
	public void testLogoutLoginWithUserAccount() {
		setUp("user");
		String message = main.logout();
		assertEquals(message, "Đăng xuất tài khoản thành công");
	}
}
