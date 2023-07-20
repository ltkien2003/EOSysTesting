package com.eos.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eos.dao.NguoiDungDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.ThongTinCaNhanJDialog;

public class InfoUserTest {
	static DangNhapJDialog dangnhap;
	static ThongTinCaNhanJDialog info;
	NguoiDungDAO dao = new NguoiDungDAO();
	@SuppressWarnings("unused")
	private String fullname;
	@SuppressWarnings("unused")
	private String gender;
	@SuppressWarnings("unused")
	private String birthday;
	@SuppressWarnings("unused")
	private String phone;
	private String email;
    @BeforeClass
    public static void setUp() {
        dangnhap = new DangNhapJDialog();
        dangnhap.dangNhap("PC01020", "Khang123@456");
        info = new ThongTinCaNhanJDialog();
    }
//    TR01_TC_LOGIN_01
    @Before
    public void data() {
    	fullname = "Nguyễn Chí Khang";
    	gender = "Nam";
    	birthday = "09/08/2000";
    	phone = "0985754622";
    	email = "PC01020@fpt.edu.vn";

    }
    @Test
    public void testDisplayInfoUserSuccessWithAdminAccount() {
    	assertTrue(info.checkData() != null);
    }
    @Test
    public void testInfoUserFailWithValid() {
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Cập nhật thành công!");
    	testDisplayInfoUserSuccessWithAdminAccount();
    }
    @Test
    public void testInfoUserFailWithEmptyFullname() {
    	fullname = "";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Họ tên người dùng không được bỏ trống");
    }
    @Test
    public void testInfoUserFailWithEmptyGender() {
    	gender = "";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Vui lòng chọn giới tính");
    }
    @Test
    public void testInfoUserFailWithEmptyBirthday() {
    	birthday = "";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Vui lòng chọn ngày sinh");
    }
	@Test
    public void testInfoUserFailWithEmptyPhone() {
    	phone = "";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Vui lòng nhập số điện thoại");
    }
	@Test
    public void testInfoUserFailWithEmptyEmail() {
    	email = "";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Vui lòng nhập email");
    }
	@Test
    public void testInfoUserFailWithInvalidPhone() {
    	phone = "abc";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Số điện thoại không đúng định dạng");
    }
	@Test
    public void testInfoUserFailWithInvalidEmail() {
    	email = "khang@";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Email không đúng định dạng (VD: example@gmail.com)");
    }
	@Test
    public void testInfoUserFailWithExistSPhone() {
    	phone = "0928768265";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Số điện thoại đã tồn tại");
    }
	@Test
    public void testInfoUserFailWithExistEmail() {
    	email = "PC01638@fpt.edu.vn";
    	String message = info.validateFormTest(fullname, gender, birthday, phone, email);
    	assertEquals(message, "Email đã tồn tại");
    }
    
}