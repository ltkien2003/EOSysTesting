package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.NguoiDungDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.NguoiDungJDialog;

public class ManageUser {

	static DangNhapJDialog login;
	static NguoiDungJDialog user;
	NguoiDungDAO dao = new NguoiDungDAO();
	private String id;
	private String fullname;
	private String gender;
	private String birthday;
	private String phone;
	private String email;
	private String note;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	user = new NguoiDungJDialog();
    }
    
    @Before
    public void data() {
    	id = "PC001";
    	fullname = "Dương Chí Bảo";
    	gender = "Nam";
    	birthday = "23/12/2003";
    	phone = "0333222333";
    	email = "chibao123@gmail.com";
    	note = "";
    }
    
//    TC_MANAGE_USER_01
    @Test
    public void DisplayListUser() {
    	setUp(true);
    	assertTrue(user.TestFillTable() != null);
    }
    
//  TC_MANAGE_USER_02
    @Test
    public void SuccessfullyInsertedUser() {
    	setUp(true);
    	String message = user.insert(id, fullname, gender, birthday, phone, email, note);
    	assertTrue(message.contains("Thêm mới thành công!." + "\nTên đăng nhập: " + id + "\n"
                + "Mật khẩu: "));
    }
    
    @Test
	public void SuccessfullyUpdatedUser() {
		setUp(true);
		id = "PC01";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Cập nhật thành công!");
	}
    
    @Test
	public void InsertUserFailedWithExistedId() {
		setUp(true);
		id = "PC01";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Mã người dùng đã tồn tại");
	}
	
    @Test
	public void InsertUserFailedWithExistedPhonenumber() {
		setUp(true);
		phone = "0355323355";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Số điện thoại đã tồn tại");
	}
    
    @Test
	public void InsertUserFailedWithExistedEmail() {
		setUp(true);
		email = "bao123@gmail.com";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Email đã tồn tại");
	}
	
	@Test
	public void InsertStaffFailedWithNullFullname() {
		setUp(true);
		fullname = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Họ tên người dùng không được bỏ trống");
	}
	
	@Test
	public void InsertUserFailedWithNullId() {
		setUp(true);
		id = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Mã người dùng không được bỏ trống");
	}
	
	@Test
	public void InsertUserFailedWithNullFullname() {
		setUp(true);
		fullname = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Họ tên người dùng không được bỏ trống");
	}
	
	@Test
	public void InsertUserFailedWithNullGender() {
		setUp(true);
		gender = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Vui lòng chọn giới tính");
	}
	
	@Test
	public void InsertUserFailedWithNullBirthday() {
		setUp(true);
		birthday = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Vui lòng chọn ngày sinh");
	}
	
	@Test
	public void InsertUserFailedWithNullPhone() {
		setUp(true);
		phone = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Vui lòng nhập số điện thoại");
	}
	
	@Test
	public void InsertUserFailedWithNullEmail() {
		setUp(true);
		email = "";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Vui lòng nhập email");
	}
	
	@Test
	public void UpdateUserFailedWithNullFullname() {
		setUp(true);
		id = "PC01";
		fullname = "";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Họ tên người dùng không được bỏ trống");
	}
	
	@Test
	public void UpdateUserFailedWithNullPhonenumber() {
		setUp(true);
		id = "PC01";
		phone = "";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Vui lòng nhập số điện thoại");
	}
	
	@Test
	public void UpdateUserFailedWithNullEmail() {
		setUp(true);
		id = "PC01";
		email = "";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Vui lòng nhập email");
	}
	
    @Test
	public void UpdateUserFailedWithExistedPhonenumber() {
		setUp(true);
		id = "PC01";
		phone = "0355323355";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Số điện thoại đã tồn tại");
	}
    
    @Test
	public void UpdateUserFailedWithExistedEmail() {
		setUp(true);
		id = "PC01";
		email = "bao123@gmail.com";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Email đã tồn tại");
	}
    
    @Test
	public void InsertUserFailWithIdFormat() {
		setUp(true);
		id = "NV001";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Mã người dùng không đúng định dạng (VD: PC01)");
	}
    
    @Test
	public void InsertUserFailWithPhoneFormat() {
		setUp(true);
		phone = "123123";
		String message = user.insert(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Số điện thoại không đúng định dạng");
	}
    
    @Test
    public void InsertUserFailWithEmailFormat() {
    	setUp(true);
    	email = "123@123";
    	String message = user.insert(id, fullname, gender, birthday, phone, email, note);
    	assertEquals(message, "Email không đúng định dạng (VD: example@gmail.com)");
    }
    
    @Test
	public void UpdateUserFailWithPhoneFormat() {
		setUp(true);
		id = "PC01";
		phone = "123123";
		String message = user.update(id, fullname, gender, birthday, phone, email, note);
		assertEquals(message, "Số điện thoại không đúng định dạng");
	}
    
    @Test
    public void UpdateUserFailWithEmailFormat() {
    	setUp(true);
    	id = "PC01";
    	email = "123@123";
    	String message = user.update(id, fullname, gender, birthday, phone, email, note);
    	assertEquals(message, "Email không đúng định dạng (VD: example@gmail.com)");
    }

	@Test
	public void DeleteUser() {
		setUp(true);
		id = "PC01";
		String message = user.delete(id);
		assertEquals(message, "Xóa thành công");
	}
	
	@Test
	public void findUser() {
		setUp(true);
		String keyword = "Zyeh";
		assertTrue(user.findUser(keyword) != null);
	}

}
