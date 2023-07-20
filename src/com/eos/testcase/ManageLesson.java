package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.BaiHocDAO;
import com.eos.ui.BaiHocJFrame;
import com.eos.ui.DangNhapJDialog;

public class ManageLesson {

	static DangNhapJDialog login;
	static BaiHocJFrame lesson;
	BaiHocDAO dao = new BaiHocDAO();
//	private String nameOfCourse;
	private String nameOfChapter;
	private String nameOfLesson;
	private String linkLesson;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	lesson = new BaiHocJFrame();
    }
    
    @Before
    public void data() {
//    	nameOfCourse = "Khóa học test";
    	nameOfChapter = "Chương test 01";
    	nameOfLesson = "Bài học test 01";
    	linkLesson = "youtube.com";
    }
    
    @Test
    public void DisplayComboboxCourse() {
    	setUp(true);
    	assertTrue(lesson.testFillComboboxKhoaHoc() != null);
    }
    
    @Test
    public void DisplayComboboxChapter() {
    	setUp(true);
    	String nameOfCourse = "Khóa học test";
    	assertTrue(lesson.testFillComboboxChuong(nameOfCourse) != null);
    }
    
    @Test
    public void DisplayListLesson() {
    	setUp(true);
    	assertTrue(lesson.testFillTable(nameOfChapter) != null);
    }
    
    @Test
	public void SuccessfullyInsertedLesson() {
		setUp(true);
		String message = lesson.insert(nameOfChapter, nameOfLesson, linkLesson);
		assertEquals(message, "Thêm mới thành công!");
	}
    
    @Test
    public void SuccessfullyUpdatedLesson() {
	  	setUp(true);
	  	String idLesson = "43";
	  	String message = lesson.update(idLesson , nameOfChapter, nameOfLesson, linkLesson);
	  	assertEquals(message, "Cập nhật thành công!");
    }	
    
    @Test
	public void InsertedLessonNullNameOfLesson() {
		setUp(true);
		nameOfLesson = "";
		String message = lesson.insert(nameOfChapter, nameOfLesson, linkLesson);
		assertEquals(message, "Vui lòng nhập tên bài học");
    }
    
    @Test
    public void InsertedLessonNullLink() {
    	setUp(true);
    	linkLesson = "";
    	String message = lesson.insert(nameOfChapter, nameOfLesson, linkLesson);
    	assertEquals(message, "Vui lòng nhập link bài học");
    }
    
    @Test
    public void UpdateLessonNullNameOfLesson() {
    	setUp(true);
    	String idLesson = "43";
    	nameOfLesson = "";
    	String message = lesson.update(idLesson, nameOfChapter, nameOfLesson, linkLesson);
    	assertEquals(message, "Vui lòng nhập tên bài học");
    }
	
	@Test
    public void UpdatedLessonNullLink() {
    	setUp(true);
    	String idLesson = "43";
    	linkLesson = "";
    	String message = lesson.update(idLesson, nameOfChapter, nameOfLesson, linkLesson);
    	assertEquals(message, "Vui lòng nhập link bài học");
    }
    
	@Test
    public void SuccessCheckedLink() {
    	setUp(true);
//    	String idLesson = "43";
    	linkLesson = "https://www.youtube.com/watch?v=cz89pCXUxX4";
    	String message = lesson.checkLink(linkLesson, nameOfLesson);
    	assertEquals(message, "Link hợp lệ");
    }
	
	@Test
    public void CheckLinkFailWithNullNameOfLesson() {
    	setUp(true);
//    	String idLesson = "43";
    	nameOfLesson = "";
    	String message = lesson.checkLink(linkLesson, nameOfLesson);
    	assertEquals(message, "Vui lòng nhập tên bài học");
    }
	
	@Test
    public void CheckLinkFailWithNullLink() {
    	setUp(true);
//    	String idLesson = "43";
    	linkLesson = "";
    	String message = lesson.checkLink(linkLesson, nameOfLesson);
    	assertEquals(message, "Vui lòng nhập link bài học");
    }
	
	@Test
    public void CheckLinkFailWithUnvalidLink() {
    	setUp(true);
    	linkLesson = "youtube.com";
    	String message = lesson.checkLink(linkLesson, nameOfLesson);
    	assertEquals(message, "Link không hợp lệ");
    }
	
	@Test
	public void DeleteLesson() {
		String idLesson = "43";
		String message = lesson.delete(idLesson);
		assertEquals(message, "Xóa thành công");
	}
    
}
