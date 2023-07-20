package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.NguoiDungDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.LichSuNhanVienJDialog;

public class HistoryStaff {

	static DangNhapJDialog login;
	static LichSuNhanVienJDialog historyStaff;
	NguoiDungDAO dao = new NguoiDungDAO();
	private String id;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	historyStaff = new LichSuNhanVienJDialog();
    }
    
    @Before
    public void data() {
    	id = "11";
    }
    

    @Test
    public void DisplayListHistoryStaff() {
    	setUp(true);
    	assertTrue(historyStaff.TestFillTable() != null);
    }
    
    @Test
	public void RestoteStaff() {
		setUp(true);
		id = "MA01";
		String message = historyStaff.update(id);
		assertEquals(message, "Khôi phục nhân viên thành công!");
	}
    
	@Test
	public void DeleteStaffPermanently() {
		setUp(true);
		id = "KhangNC";
		String message = historyStaff.delete(id);
		assertEquals(message, "Xóa thành công");
	}
    
    @Test
    public void DisplayListFindHistoryStaff() {
    	setUp(true);
    	String keyword = "N";
    	assertTrue(historyStaff.fillTableTimKiem(keyword) != null);
    }

}
