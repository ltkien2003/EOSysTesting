package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.NguoiDungDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.LichSuKhoaHocJDialog;

public class HistoryCourse {

	static DangNhapJDialog login;
	static LichSuKhoaHocJDialog historyCourse;
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
    	historyCourse = new LichSuKhoaHocJDialog();
    }
    
    @Before
    public void data() {
    	id = "11";
    }
    

    @Test
    public void DisplayListHistoryCourse() {
    	setUp(true);
    	assertTrue(historyCourse.TestFillTable() != null);
    }
    
    @Test
	public void RestoteCourse() {
		setUp(true);
		id = "11";
		String message = historyCourse.update(id);
		assertEquals(message, "Cập nhật thành công!");
	}
    
	@Test
	public void DeleteCoursePermanently() {
		setUp(true);
		id = "6";
		String message = historyCourse.delete(id);
		assertEquals(message, "Xóa thành công");
	}
    
    @Test
    public void DisplayListFindHistoryCourse() {
    	setUp(true);
    	String keyword = "N";
    	assertTrue(historyCourse.fillTableTimKiem(keyword) != null);
    }

}
