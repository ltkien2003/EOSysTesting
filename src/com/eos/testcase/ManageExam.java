package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eos.dao.KyThiDAO;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.KyThiJDialog;

public class ManageExam {

	static DangNhapJDialog login;
	static KyThiJDialog exam;
	KyThiDAO dao = new KyThiDAO();
//	private String tenKhoaHoc;
	private String tenChuong;
	private String tenKyThi;
	private String TGMoDe;
	private String TGDongDe;
	private String matKhau;
	private String tongSoCau;
	private String TGLamBai;
	private String soLanLam;
    public static void setUp(boolean admin) {
    	login = new DangNhapJDialog();
    	if(admin) {
            login.dangNhap("BaoDC", "Bao1234@78910");
    	}
    	else {
            login.dangNhap("KhangNC", "Khang@123456");    		
    	}
    	exam = new KyThiJDialog();
    }
    
    @Before
    public void data() {
//    	tenKhoaHoc = "Khóa học test 01";
    	tenChuong = "Chương test 01";
    	tenKyThi = "Thi test 01"; 
    	TGMoDe = "2/3/2023 01:00:00";
    	TGDongDe = "2/3/2023 03:00:00";
    	matKhau = "123";
    	tongSoCau = "60";
    	TGLamBai = "120";
    	soLanLam = "2";
    }
    
    @Test
    public void DisplayComboboxCourse() {
    	setUp(true);
    	assertTrue(exam.testFillComboboxKhoaHoc() != null);
    }
    
    @Test
    public void DisplayComboboxChapter() {
    	setUp(true);
    	String nameOfCourse = "Khóa học test";
    	assertTrue(exam.testFillComboboxChuong(nameOfCourse) != null);
    }
    
    @Test
    public void DisplayListQuestion() {
    	setUp(true);
    	assertTrue(exam.testFillTable(tenChuong) != null);
    }
    
    @Test
	public void SuccessfullyInsertedExam() {
		setUp(true);
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thêm mới thành công!");
	}
    
    @Test
    public void SuccessfullyUpdatedExam() {
	  	setUp(true);
	  	String idExam = "1";
	  	tenKyThi = "Thi test 02";
	  	String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
	  	assertEquals(message, "Cập nhật thành công!");
    }	
    
    @Test
	public void InsertedQuestionNullNameOfExam() {
		setUp(true);
		tenKyThi = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập tên kỳ thi");
    }
    
    @Test
	public void InsertedQuestionNullOpenTime() {
		setUp(true);
		TGMoDe = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian mở đề");
    }
    
    @Test
	public void InsertedQuestionNullCloseTime() {
		setUp(true);
		TGDongDe = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian đóng đề");
    }
    
    @Test
	public void InsertedQuestionNullPassword() {
		setUp(true);
		matKhau = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập mật khẩu kỳ thi");
    }
    
    @Test
	public void InsertedQuestionNullExamTime() {
		setUp(true);
		TGLamBai = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian làm bài");
    }
    
    @Test
	public void InsertedQuestionNullTotalQuestion() {
		setUp(true);
		tongSoCau = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập tổng số câu");
    }
    
    @Test
	public void InsertedQuestionNullTimes() {
		setUp(true);
		TGLamBai = "";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian làm bài");
    }
    
    @Test
	public void UpdatedQuestionNullNameOfExam() {
		setUp(true);
		String idExam = "1";
		tenKyThi = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập tên kỳ thi");
    }
    
    @Test
	public void UpdatedQuestionNullOpenTime() {
		setUp(true);
		String idExam = "1";
		TGMoDe = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian mở đề");
    }
    
    @Test
	public void UpdatedQuestionNullCloseTime() {
		setUp(true);
		String idExam = "1";
		TGDongDe = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian đóng đề");
    }
    
    @Test
	public void UpdatedQuestionNullPassword() {
		setUp(true);
		String idExam = "1";
		matKhau = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập mật khẩu kỳ thi");
    }
    
    @Test
	public void UpdatedQuestionNullExamTime() {
		setUp(true);
		String idExam = "1";
		TGLamBai = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian làm bài");
    }
    
    @Test
	public void UpdatedQuestionNullTotalQuestion() {
		setUp(true);
		String idExam = "1";
		tongSoCau = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập tổng số câu");
    }
    
    @Test
	public void UpdatedQuestionNullTimes() {
		setUp(true);
		String idExam = "1";
		TGLamBai = "";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Vui lòng nhập thời gian làm bài");
    }
    
    @Test
	public void InsertQuestionNullTimeExamLessThan0() {
		setUp(true);
		TGLamBai = "-1";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thời gian làm bài phải lớn hơn 0");
    }
    
    @Test
	public void UpdateQuestionNullTimeExamLessThan0() {
		setUp(true);
		String idExam = "1";
		TGLamBai = "-1";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thời gian làm bài phải lớn hơn 0");
    }
    
    @Test
	public void InsertQuestionNullTotalQuestionLessThan0() {
		setUp(true);
		tongSoCau = "-1";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Tổng số câu phải lớn hơn 0");
    }
    
    @Test
	public void UpdateQuestionNullTotalQuestionLessThan0() {
		setUp(true);
		String idExam = "1";
		tongSoCau = "-1";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Tổng số câu phải lớn hơn 0");
    }
    
    @Test
	public void InsertQuestionNullTimesLessThan0() {
		setUp(true);
		soLanLam = "-1";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Số lần làm phải lớn hơn 0");
    }
    
    @Test
	public void UpdateQuestionNullTimesLessThan0() {
		setUp(true);
		String idExam = "1";
		soLanLam = "-1";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Số lần làm phải lớn hơn 0");
    }
    
    @Test
	public void InsertQuestionFailTimeOpen() {
		setUp(true);
		TGMoDe = "02/28/2023 12:25:00";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "TG mở đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
    }
    
    @Test
	public void UpdateQuestionFailTimeOpen() {
		setUp(true);
		String idExam = "1";
		TGMoDe = "02/28/2023 12:25:00";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "TG mở đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
    }
    
    @Test
	public void InsertQuestionFailTimeClose() {
		setUp(true);
		TGDongDe = "02/28/2023 12:25:00";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "TG đóng đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
    }
    
    @Test
	public void UpdateQuestionFailTimeClose() {
		setUp(true);
		String idExam = "1";
		TGDongDe = "02/28/2023 12:25:00";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "TG đóng đề chưa đúng định dạng dd/MM/yyyy hh:mm:ss");
    }
    
    @Test
	public void InsertQuestionFailDateTime() {
		setUp(true);
		TGMoDe = "2/3/2023 17:00:00";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thời gian mở đề phải nhỏ hơn thời gian đóng đề");
    }
    
    @Test
	public void UpdateQuestionFailDateTime() {
		setUp(true);
		String idExam = "1";
		TGDongDe = "30/1/2023 06:00:00";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thời gian mở đề phải nhỏ hơn thời gian đóng đề");
    }
    
    @Test
	public void InsertQuestionFailExamTime() {
		setUp(true);
		TGLamBai = "1000";
		String message = exam.insert(tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thời gian làm bài phải nằm trong khoản thời gian mở đề và thời gian đóng đề");
    }
    
    @Test
	public void UpdateQuestionFailExamTime() {
		setUp(true);
		String idExam = "1";
		TGLamBai = "1000";
		String message = exam.update(idExam ,tenChuong, tenKyThi, TGMoDe, TGDongDe, matKhau, tongSoCau, TGLamBai, soLanLam);
		assertEquals(message, "Thời gian làm bài phải nằm trong khoản thời gian mở đề và thời gian đóng đề");
    }
    
	@Test
	public void DeleteQuestion() {
		String idExam = "1";
		String message = exam.delete(idExam);
		assertEquals(message, "Xóa thành công");
	}
    
}
