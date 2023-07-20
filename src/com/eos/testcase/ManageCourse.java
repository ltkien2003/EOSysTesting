package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.KhoaHocDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.KhoaHocJDialog;

public class ManageCourse {
	static DangNhapJDialog login;
	static KhoaHocJDialog course;
	KhoaHocDAO dao = new KhoaHocDAO();
	private String nameOfCourse;
	private String totalChapter;
	private String openingDay;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	course = new KhoaHocJDialog();
    }
    
    @Before
    public void data() {
    	nameOfCourse = "Khóa học 2";
    	totalChapter = "10";
    	openingDay = "10/4/2023";
    }
    
//  TC_MANAGE_COURSE_01
    @Test
    public void DisplayListCourse() {
    	setUp(true);
    	assertTrue(course.testFillTable() != null);
    }
    
	@Test
	public void findCourse() {
		setUp(true);
		String keyword = "T";
		assertTrue(course.findCourse(keyword) != null);
	}
    
//	TC_MANAGE_COURSE_04
    @Test
    public void SuccessfullyInsertedCourse() {
    	setUp(true);
    	String message = course.insert(nameOfCourse, totalChapter, openingDay);
    	assertEquals(message, "Thêm mới thành công!");
    }
    
    @Test
    public void SuccessfullyUpdatedCourse() {
    	setUp(true);
    	String idCourse = "10"; //Loai tru nhung khoa hoc da bi xoa
    	nameOfCourse = "Khóa học test 10";
    	totalChapter = "10";
    	openingDay = "10/4/2023";
    	String message = course.update(idCourse ,nameOfCourse, totalChapter, openingDay);
    	assertEquals(message, "Cập nhật thành công!");
    }
    
	@Test
	public void InsertCourseFailedWithNullName() {
		setUp(true);
		nameOfCourse = "";
		String message = course.insert(nameOfCourse, totalChapter, openingDay);
		assertEquals(message, "Vui lòng nhập tên khóa học");
	}
	
	@Test
	public void InsertCourseFailedWithNullTotalChapter() {
		setUp(true);
		totalChapter = "";
		String message = course.insert(nameOfCourse, totalChapter, openingDay);
		assertEquals(message, "Vui lòng nhập tổng số chương");
	}
	
	@Test
	public void UpdateCourseFailedWithNullName() {
		setUp(true);
		String idCourse = "10"; //Loai tru nhung khoa hoc da bi xoa
		nameOfCourse = "";
		String message = course.update(idCourse ,nameOfCourse, totalChapter, openingDay);
		assertEquals(message, "Vui lòng nhập tên khóa học");
	}
    
	@Test
	public void UpdateCourseFailedWithNullTotalChapter() {
		setUp(true);
		String idCourse = "10"; //Loai tru nhung khoa hoc da bi xoa
		totalChapter = "";
		String message = course.update(idCourse ,nameOfCourse, totalChapter, openingDay);
		assertEquals(message, "Vui lòng nhập tổng số chương");
	}
	@Test
	public void DeleteCourse() {
		setUp(true);
		String idCourse = "10";
		String message = course.delete(idCourse);
		assertEquals(message, "Xóa thành công");
	}
	


}
