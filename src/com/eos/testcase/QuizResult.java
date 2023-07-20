package com.eos.testcase;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.eos.ui.DangNhapJDialog;
import com.eos.ui.XemKetQuaJDialog;
import com.eos.ui.XemLaiDapAnJDialog;

public class QuizResult {
	static XemKetQuaJDialog quizresult;
	static XemLaiDapAnJDialog viewresult;
	@BeforeClass
    public static void setUp() {
        DangNhapJDialog dangnhap = new DangNhapJDialog();
        dangnhap.dangNhap("PC01020", "Khang123@456");
    	 quizresult = new XemKetQuaJDialog();
    }
	@Test
	public void testQuizResultJoinedExam() {
		assertTrue(quizresult.loadData() != null);
	}
	@Test
	public void testQuizResultDetailExam() {
		assertTrue(quizresult.find(4) != null);		
	}
	@Test
	public void testQuizResultViewResult() {
		viewresult = new XemLaiDapAnJDialog("");
		assertTrue(viewresult.getViewAnswers(4) != null);		
	}
	@Test
	public void testQuizResultColorWrongQuestion() {
		viewresult = new XemLaiDapAnJDialog("");
		assertTrue(viewresult.getColor(18, 5).equals("red"));		
	}
}
