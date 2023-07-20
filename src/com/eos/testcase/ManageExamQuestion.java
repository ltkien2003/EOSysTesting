package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.DeThiDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.DeThiJDialog;

public class ManageExamQuestion {

	static DangNhapJDialog login;
	static DeThiJDialog examQuestion;
	DeThiDAO dao = new DeThiDAO();
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
    	examQuestion = new DeThiJDialog();
    }
    
    @Before
    public void data() {
    	nameOfCourse = "Tin học";
    }
    
    @Test
    public void DisplayComboboxCourse() {
    	setUp(true);
    	assertTrue(examQuestion.testFillComboboxKhoaHoc() != null);
    }
    
    @Test
    public void DisplayListExamQuestion() {
    	setUp(true);
    	assertTrue(examQuestion.testFillTable(nameOfCourse) != null);
    }
    
    @Test
    public void findUserByName() {
    	setUp(true);
    	String name = "Bảo";
    	assertTrue(examQuestion.testFindExamQuestion(nameOfCourse, name) != null);
    }
    
    
	
}
