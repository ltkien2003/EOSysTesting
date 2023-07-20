package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.CauHoiDAO;
import com.eos.ui.CauHoiJFrame;
import com.eos.ui.DangNhapJDialog;

public class ManageQuestion {

	static DangNhapJDialog login;
	static CauHoiJFrame question;
	CauHoiDAO dao = new CauHoiDAO();
	private String nameOfChapter;
	private String nameOfQuestion;
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String trueAnswer;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	question = new CauHoiJFrame();
    }
    
    @Before
    public void data() {
//    	nameOfCourse = "Khóa học test";
    	nameOfChapter = "Chương test 01";
    	nameOfQuestion = "Câu hỏi test 01";
    	answer1 = "01";
    	answer2 = "02";
    	answer3 = "03";
    	answer4 = "04";
    	trueAnswer = "answer4";
    }
    
    @Test
    public void DisplayComboboxCourse() {
    	setUp(true);
    	assertTrue(question.testFillComboboxKhoaHoc() != null);
    }
    
    @Test
    public void DisplayComboboxChapter() {
    	setUp(true);
    	String nameOfCourse = "Khóa học test";
    	assertTrue(question.testFillComboboxChuong(nameOfCourse) != null);
    }
    
    @Test
    public void DisplayListQuestion() {
    	setUp(true);
    	assertTrue(question.testFillTable(nameOfChapter) != null);
    }
    
    @Test
	public void findQuestion() {
		setUp(true);
		String keyword = "T";
		assertTrue(question.fillTableTimKiem(nameOfChapter, keyword) != null);
	}
    
    @Test
	public void SuccessfullyInsertedQuestion() {
		setUp(true);
		String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
		assertEquals(message, "Thêm mới thành công!");
	}
    
    @Test
    public void SuccessfullyUpdatedQuestion() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Cập nhật thành công!");
    }	
    
    @Test
	public void InsertedQuestionNullNameOfQuestion() {
		setUp(true);
		nameOfQuestion = "";
		String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
		assertEquals(message, "Vui lòng nhập nội dung câu hỏi");
    }
    
    @Test
    public void InsertedQuestionNullDA1() {
    	setUp(true);
    	answer1 = "";
    	String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
    	assertEquals(message, "Vui lòng nhập nội dung đáp án 1");
    }

    @Test
    public void InsertedQuestionNullDA2() {
    	setUp(true);
    	answer2 = "";
    	String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
    	assertEquals(message, "Vui lòng nhập nội dung đáp án 2");
    }

    @Test
    public void InsertedQuestionNullDA3() {
    	setUp(true);
    	answer3 = "";
    	String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
    	assertEquals(message, "Vui lòng nhập nội dung đáp án 3");
    }

    @Test
    public void InsertedQuestionNullDA4() {
    	setUp(true);
    	answer4 = "";
    	String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
    	assertEquals(message, "Vui lòng nhập nội dung đáp án 4");
    }

    @Test
    public void InsertedQuestionNullTrueAnswer() {
    	setUp(true);
    	trueAnswer = "";
    	String message = question.insert(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
    	assertEquals(message, "Vui lòng chọn đáp án đúng");
    }
    
    @Test
    public void UpdateQuestionNullNameOfQuestion() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	nameOfQuestion = "";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung câu hỏi");
    }
    
    @Test
    public void UpdateQuestionNullDA1() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	answer1 = "";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 1");
    }

    @Test
    public void UpdateQuestionNullDA2() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	answer2 = "";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 2");
    }

    @Test
    public void UpdateQuestionNullDA3() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	answer3 = "";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 3");
    }

    @Test
    public void UpdateQuestionNullDA4() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	answer4 = "";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 4");
    }
    
    @Test
    public void UpdateQuestionNullTrueAnswer() {
	  	setUp(true);
	  	String idQuestion = "57";
	  	trueAnswer = "";
	  	String message = question.update(idQuestion , nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng chọn đáp án đúng");
    }
    
    @Test
    public void SeeQuestionNullNameOfQuestion() {
	  	setUp(true);
//	  	String idQuestion = "57";
	  	nameOfQuestion = "";
	  	String message = question.xemCauHoi(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung câu hỏi");
    }
    
    @Test
    public void SeeQuestionNullDA1() {
	  	setUp(true);
//	  	String idQuestion = "57";
	  	answer1 = "";
	  	String message = question.xemCauHoi(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 1");
    }

    @Test
    public void SeeQuestionNullDA2() {
	  	setUp(true);
//	  	String idQuestion = "57";
	  	answer2 = "";
	  	String message = question.xemCauHoi(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 2");
    }

    @Test
    public void SeeQuestionNullDA3() {
	  	setUp(true);
//	  	String idQuestion = "57";
	  	answer3 = "";
	  	String message = question.xemCauHoi(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 3");
    }

    @Test
    public void SeeQuestionNullDA4() {
	  	setUp(true);
//	  	String idQuestion = "57";
	  	answer4 = "";
	  	String message = question.xemCauHoi(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng nhập nội dung đáp án 4");
    }
    
    @Test
    public void SeeQuestionNullTrueAnswer() {
	  	setUp(true);
//	  	String idQuestion = "57";
	  	trueAnswer = "";
	  	String message = question.xemCauHoi(nameOfChapter, nameOfQuestion, answer1, answer2, answer3, answer4, trueAnswer);
	  	assertEquals(message, "Vui lòng chọn đáp án đúng");
    }

	@Test
	public void DeleteQuestion() {
		String idQuestion = "57";
		String message = question.delete(idQuestion);
		assertEquals(message, "Xóa thành công");
	}
    
}
