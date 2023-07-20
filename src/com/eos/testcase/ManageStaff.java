package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.NhanVienDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.NhanVienJDialog;

public class ManageStaff {
	static DangNhapJDialog login;
	static NhanVienJDialog staff;
	NhanVienDAO dao = new NhanVienDAO();
	private String id;
	private String fullname;
	private String role;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	staff = new NhanVienJDialog();
    }
    
    @Before
    public void data() {
    	id = "Chibao12";
    	fullname = "Dương Chí Bảo";
    	role = "Admin";
    }
    
    @Test
    public void DisplayListStaff() {
    	setUp(true);
    	assertTrue(staff.TestFillTable() != null);
    }
    
//    "TC_MANAGE_STAFF_03"
    @Test
    public void SuccessfullyInsertedStaff() {
    	setUp(true);
    	String message = staff.insert(id, fullname, role);
    	assertTrue(message.contains("Thêm mới thành công!." + "\nTên đăng nhập: " + id + "\n"
                        + "Mật khẩu: "));
    }
    
    @Test
	public void SuccessfullyUpdatedStaff() {
		setUp(true);
		fullname = "Dương Chí Bảo";
		String message = staff.update(id, fullname, role);
		assertEquals(message, "Cập nhật thành công!");
	}
    
    @Test
	public void InsertStaffFailedWithExistedId() {
		setUp(true);
		id = "BaoDC";
		String message = staff.insert(id, fullname, role);
		assertEquals(message, "Mã nhân viên đã tồn tại");
	}
	
	@Test
	public void InsertStaffFailedWithNullId() {
		setUp(true);
		id = "";
		String message = staff.insert(id, fullname, role);
		assertEquals(message, "Mã nhân viên không được bỏ trống");
	}
	
	@Test
	public void InsertStaffFailedWithNullFullname() {
		setUp(true);
		fullname = "";
		String message = staff.insert(id, fullname, role);
		assertEquals(message, "Họ tên không được bỏ trống");
	}
	
	@Test
	public void UpdateStaffFailedWithNullFullname() {
		setUp(true);
		fullname = "";
		String message = staff.update(id, fullname, role);
		assertEquals(message, "Họ tên không được bỏ trống");
	}
	
	@Test
	public void InsertStaffFailedWithNullRole() {
		setUp(true);
		role = "";
		String message = staff.insert(id, fullname, role);
		assertEquals(message, "Vui lòng chọn vai trò");
	}
	
	@Test
	public void DeleteStaff() {
		setUp(true);
//		id = "...";
		String message = staff.delete(id);
		assertEquals(message, "Xóa thành công");
	}
	
	@Test
	public void DeleteYourself() {
		setUp(true);
		id = "BaoDC";
		String message = staff.delete(id);
		assertEquals(message, "Bạn không được xóa chính bạn");
	}
	
	@Test
	public void findStaff() {
		setUp(true);
		String keyword = "KhangDC";
		assertTrue(staff.findStaff(keyword) != null);
	}

}
