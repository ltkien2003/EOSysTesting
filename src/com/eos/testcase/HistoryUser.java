package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.NguoiDungDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.LichSuNguoiDungJDialog;

public class HistoryUser {

	static DangNhapJDialog login;
	static LichSuNguoiDungJDialog historyUser;
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
    	historyUser = new LichSuNguoiDungJDialog();
    }
    
    @Before
    public void data() {
    	id = "PC001";
    }
    

    @Test
    public void DisplayListHistoryUser() {
    	setUp(true);
    	assertTrue(historyUser.TestFillTable() != null);
    }
    
    @Test
	public void RestoteUser() {
		setUp(true);
		id = "PC01";
		String message = historyUser.update(id);
		assertEquals(message, "Cập nhật thành công!");
	}
    
	@Test
	public void DeleteUserPermanently() {
		setUp(true);
		id = "PC01";
		String message = historyUser.delete(id);
		assertEquals(message, "Xóa thành công");
	}
    
    @Test
    public void DisplayListFindHistoryUser() {
    	setUp(true);
    	String keyword = "N";
    	assertTrue(historyUser.fillTableTimKiem(keyword) != null);
    }

}
