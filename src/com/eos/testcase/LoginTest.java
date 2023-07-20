package com.eos.testcase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.eos.ui.DangNhapJDialog;
import com.eos.untils.Auth;
public class LoginTest {
	static DangNhapJDialog dangNhap;
	private String usernameAdmin;
	private String passwordAdmin;
	private String usernameStaff;
	private String passwordStaff;
	private String usernameUser;
	private String passwordUser;
	@BeforeClass
    public static void setUp() {
    	 dangNhap = new DangNhapJDialog();
    }
    @Before
    public void valueDefault() {
    	usernameAdmin = "BaoDC";
    	passwordAdmin = "Bao1234@78910";
    	usernameStaff = "KhangNC";
    	passwordStaff = "Khang@123456";
    	usernameUser = "PC01020";
    	passwordUser = "Khang123@456";
    }
//    TR01_TC_LOGIN_01
    @Test
    public void testDangNhapSuccessWithAdminAccount() {
        String message = dangNhap.dangNhap(usernameAdmin, passwordAdmin);
        assertEquals(message, "Đăng nhập thành công");
        assertTrue(Auth.user != null && Auth.user.isVaiTro());
    }
//    TR01_TC_LOGIN_02
    @Test
    public void testDangNhapSuccessWithStaffAccount() {
    	String message = dangNhap.dangNhap(usernameStaff, passwordStaff);
    	assertEquals(message, "Đăng nhập thành công");
        assertTrue(Auth.user != null && Auth.user.isVaiTro() == false);
    }
//    TR01_TC_LOGIN_03
    @Test
    public void testDangNhapSuccessWithUserAccount() {
    	String message = dangNhap.dangNhap(usernameUser, passwordUser);
    	assertEquals(message, "Đăng nhập thành công");
    }
//    TR01_TC_LOGIN_04
    @Test
    public void testDangNhapFailWhenEmptyUserName() {
    	String message = dangNhap.dangNhap("", "Khang123@456");
    	assertEquals(message, "Tên đăng nhập không được bỏ trống");
    }
//    TR01_TC_LOGIN_05
    @Test
    public void testDangNhapFailWhenEmptyPassword() {
    	String message = dangNhap.dangNhap("PC01020", "");
    	assertEquals(message, "Mật khẩu không được bỏ trống");
    }
//    TR01_TC_LOGIN_06
    @Test
    public void testDangNhapFailWhenExistUserName() {
    	String message = dangNhap.dangNhap("PC05079", "ExistPassword1234");
    	assertEquals(message, "Tên đăng nhập hoặc mật khẩu không hợp lệ");
    }
//    TR01_TC_LOGIN_07
    @Test
    public void testDangNhapFailWhenExistPassword() {
    	String message = dangNhap.dangNhap("PC01020", "FailPassword1234");
    	assertEquals(message, "Tên đăng nhập hoặc mật khẩu không hợp lệ");
    }
}