package com.eos.testcase;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.eos.ui.DangNhapJDialog;
import com.eos.ui.DoiMatKhauJDialog;
import com.eos.ui.NhanVienJDialog;

@FixMethodOrder(MethodSorters.JVM)
public class ChangePasswordTest {
	static DangNhapJDialog login;
	static DoiMatKhauJDialog changepassword;
	static NhanVienJDialog nv;
    public static void setUp(String role) {
    	login = new DangNhapJDialog();
    	if(role.equalsIgnoreCase("admin")) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else if(role.equalsIgnoreCase("staff")) {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	else if(role.equalsIgnoreCase("user")) {
            login.dangNhap("PC01020", "Khang123@456");    		
    	}
    	changepassword = new DoiMatKhauJDialog();
    	nv = new NhanVienJDialog();
    }
    @SuppressWarnings("unused")
	private String currentPasswordAdmin;
	@SuppressWarnings("unused")
	private String newPasswordAdmin;
	@SuppressWarnings("unused")
	private String confirmPasswordAdmin;
	@SuppressWarnings("unused")
	private String currentPasswordStaff;
	@SuppressWarnings("unused")
	private String newPasswordStaff;
	@SuppressWarnings("unused")
	private String confirmPasswordStaff;
	@SuppressWarnings("unused")
	private String newPasswordUser;
	@SuppressWarnings("unused")
	private String confirmPasswordUser;
	private String currentPasswordUser;
//    TR01_TC_LOGIN_01
    @Before
    public void data() {
    	currentPasswordAdmin = "Bao1234@78910";
    	newPasswordAdmin = "Bao1234@78910";
    	confirmPasswordAdmin = "Bao1234@78910";
    	currentPasswordStaff = "Khang@123456";
    	newPasswordStaff = "Khang@123456";
    	confirmPasswordStaff = "Khang@123456";
    	currentPasswordUser = "Khang123@456";
    	newPasswordUser = "Khang123@456";
    	confirmPasswordUser = "Khang123@456";
    	
    }
    @Test
    public void testChangePasswordEmptyCurrentPasswordWithAdminAccount() {
        setUp("admin");
        currentPasswordAdmin = "";
    	String message = changepassword.changePassword(currentPasswordAdmin, newPasswordAdmin, confirmPasswordAdmin);
    	assertEquals(message, "Mật khẩu hiện tại không được bỏ trống");
    	
    }
    @Test
    public void testChangePasswordEmptyNewPasswordWithAdminAccount() {
    	setUp("admin");
    	newPasswordAdmin = "";
    	String message = changepassword.changePassword(currentPasswordAdmin, newPasswordAdmin, confirmPasswordAdmin);
    	assertEquals(message, "Mật khẩu mới không được bỏ trống");
    }
    @Test
    public void testChangePasswordValidNewPasswordWithAdminAccount() {
    	setUp("admin");
    	newPasswordAdmin = "1234";
    	String message = changepassword.changePassword(currentPasswordAdmin, newPasswordAdmin, confirmPasswordAdmin);
    	assertEquals(message, "Mật khẩu phải chứa ít nhất 8 ký tự và nhiều nhất là 20 ký tự và phải chứa ít nhất một chữ số, một chữ cái viết in hoa, một chữ cái viết thường, một ký tự đặc biệt bao gồm  !@#$%&*()-+=^  và không chứa bất kỳ khoảng trắng nào.");
    }
    @Test
    public void testChangePasswordInvalidCurrentPasswordWithAdminAccount() {
    	setUp("admin");
    	currentPasswordAdmin = "Khang1234";
    	String message = changepassword.changePassword(currentPasswordAdmin, newPasswordAdmin, confirmPasswordAdmin);
    	assertEquals(message, "Sai mật khẩu");
    }
    @Test
    public void testChangePasswordValidWithAdminAccount() {
    	setUp("admin");
    	newPasswordAdmin = "Bao1234@78910";
    	confirmPasswordAdmin = "Bao1234@78910";
    	String message = changepassword.changePassword(currentPasswordAdmin, newPasswordAdmin, confirmPasswordAdmin);
    	assertEquals(message, "Đổi mật khẩu thành công!");
    }
    @Test
    public void testChangePasswordValidWithStaffAccount() {
    	setUp("staff");
    	newPasswordStaff = "Khang@123456";
    	confirmPasswordStaff = "Khang@123456";
    	String message = changepassword.changePassword(currentPasswordStaff, newPasswordStaff, confirmPasswordStaff);
    	assertEquals(message, "Đổi mật khẩu thành công!");
    }
    @Test
    public void testChangePasswordValidWithUserAccount() {
    	setUp("user");
    	newPasswordUser = "Khang123@456";
    	confirmPasswordUser = "Khang123@456";
    	String message = changepassword.changePassword(currentPasswordUser, newPasswordUser, confirmPasswordUser);
    	assertEquals(message, "Đổi mật khẩu thành công!");
    }
    @Test
    public void testChangePasswordInValidCurrentPasswordWithUserAccount() {
    	setUp("user");
    	currentPasswordUser = "1234";
    	String message = changepassword.changePassword(currentPasswordUser, newPasswordUser, confirmPasswordUser);
    	assertEquals(message, "Sai mật khẩu");
    }
    
    
}
