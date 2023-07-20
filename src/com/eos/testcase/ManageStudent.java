package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.HocVienDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.HocVienJDialog;

public class ManageStudent {

	static DangNhapJDialog login;
	static HocVienJDialog student;
	HocVienDAO dao = new HocVienDAO();
	private String nameOfCourse;
//	private String nameOfChapter;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	student = new HocVienJDialog();
    }
    
    @Before
    public void data() {
    	nameOfCourse = "Tin học";
    }
    
    @Test
    public void DisplayComboboxCourse() {
    	setUp(true);
    	assertTrue(student.testFillComboboxKhoaHoc() != null);
    }
    
    @Test
    public void DisplayListStudent() {
    	setUp(true);
    	assertTrue(student.testFillTableHocVien(nameOfCourse) != null);
    }
    
    @Test
    public void DisplayListUser() {
    	setUp(true);
    	String keyword = "";
    	assertTrue(student.fillTableNguoiDung(nameOfCourse, keyword) != null);
    }
    
    @Test
    public void findUserByName() {
    	setUp(true);
    	String name = "Bảo";
    	assertTrue(student.fillTableNguoiDung(nameOfCourse, name) != null);
    }
    
    @Test
    public void deleteStudentOutOfCourse() {
    	setUp(true);
    	String idStudent = "41";
    	String message = student.removeHocVien(idStudent);
    	assertEquals(message, "Xóa học viên thành công");
    }
    
    @Test
    public void addStudentOutOfCourse() {
    	setUp(true);
    	String idUser = "PC002";
    	String message = student.addHocVien(nameOfCourse, idUser);
    	assertEquals(message, "Thêm học viên thành công");
    }
	
}
