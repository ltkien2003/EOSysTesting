package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.KhoaHocDAO;
import com.eos.ui.ChuongJFrame;
import com.eos.ui.DangNhapJDialog;

public class ManageChapter {

	static DangNhapJDialog login;
	static ChuongJFrame chapter;
	KhoaHocDAO dao = new KhoaHocDAO();
	private String nameOfCourse;
	private String nameOfChapter;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	chapter = new ChuongJFrame();
    }
    
    @Before
    public void data() {
    	nameOfCourse = "Khóa học test";
    	nameOfChapter = "Chương test 3";
    }
    
//    TC_MANAGE_USER_01
    @Test
    public void DisplayComboboxCourse() {
    	setUp(true);
    	assertTrue(chapter.testFillComboboxKhoaHoc() != null);
    }
    
    @Test
    public void DisplayListChapter() {
    	setUp(true);
    	nameOfCourse = "Tin học";
    	assertTrue(chapter.testFillTable(nameOfCourse) != null);
    }
    
//	TC_MANAGE_COURSE_04
	@Test
	public void SuccessfullyInsertedChapter() {
		setUp(true);
		String message = chapter.insert(nameOfCourse, nameOfChapter);
		assertEquals(message, "Thêm mới thành công!");
	}
    
	  @Test
	  public void SuccessfullyUpdatedChapter() {
	  	setUp(true);
	  	String idChapter = "9";
	  	nameOfChapter = "Chương test 04";
	  	String message = chapter.update(nameOfCourse , idChapter, nameOfChapter);
	  	assertEquals(message, "Cập nhật thành công!");
	  }
	    
	  @Test
	  public void InsertChapterFailedWithNullNameOfChapter() {
	  	setUp(true);
	  	String idChapter = "9";
	  	nameOfChapter = "";
	  	String message = chapter.update(nameOfCourse , idChapter, nameOfChapter);
	  	assertEquals(message, "Vui lòng nhập tên chương");
	  }
	    
	  @Test
	  public void InsertChapterFailedWithExistName() {
	  	setUp(true);
	  	nameOfChapter = "Chương test 01";
	  	String message = chapter.insert(nameOfCourse, nameOfChapter);
	  	assertEquals(message, "Tên chương đã tồn tại trong khóa học");
	  }
	  
	  @Test
	  public void InsertChapterFailedWithOverflowChapter() {
	  	setUp(true);
	  	nameOfCourse = "Dự án mẫu";
	  	nameOfChapter = "Chương test 01";
	  	String message = chapter.insert(nameOfCourse, nameOfChapter);
	  	assertEquals(message, "Đã đủ số chương không thể thêm chương mới");
	  }
	  
	  @Test
	  public void UpdateChapterFailedWithNullNameOfChapter() {
	  	setUp(true);
	  	String idChapter = "9";
	  	nameOfChapter = "";
	  	String message = chapter.update(nameOfCourse , idChapter, nameOfChapter);
	  	assertEquals(message, "Vui lòng nhập tên chương");
	  }
	  
	  @Test
	  public void UpdateChapterFailedWithSameNameOfChapter() {
	  	setUp(true);
	  	String idChapter = "9";
	  	nameOfChapter = "Chương test 01";
	  	String message = chapter.update(nameOfCourse , idChapter, nameOfChapter);
	  	assertEquals(message, "Tên chương đã tồn tại trong khóa học");
	  }
	
	  @Test
	public void DeleteCourse() {
		String idChapter = "9";
		nameOfCourse = "Khóa học test";
		String message = chapter.delete(idChapter);
		assertEquals(message, "Xóa thành công");
	}
	
}
