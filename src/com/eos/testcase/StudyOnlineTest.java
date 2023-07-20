package com.eos.testcase;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import com.eos.ui.DangNhapJDialog;
import com.eos.ui.HocOnlineJDialog;
import com.eos.ui.VaoHocJDialog;

public class StudyOnlineTest {
	static VaoHocJDialog online;
	static HocOnlineJDialog honline;
	static DangNhapJDialog dangnhap;
	@BeforeClass
    public static void setUp() {
        dangnhap = new DangNhapJDialog();
    	 online = new VaoHocJDialog();
    	 honline = new HocOnlineJDialog("");
    }
	@Test
	public void testJoinedCourse() {
		dangnhap.dangNhap("PC01020", "Khang123@456");
		assertTrue(online.loadData().isEmpty() == false);
	}

	@Test
	public void testNotJoinedCourse() {
		dangnhap.dangNhap("PC05895", "Hao045@752");
		assertTrue(online.loadData().isEmpty());
	}
	@Test
	public void testDetailCourse() {
		dangnhap.dangNhap("PC0125", "123Khang@789");
		assertTrue(online.edit(1) != null);
	}
	@Test
	public void testJoinCourse() {
		dangnhap.dangNhap("PC05895", "Hao045@752");
		assertTrue(honline.fillComboboxChuong(1).isEmpty() == false);
	}
	@Test
	public void changeChapterWhenStudiedOnline() {
		dangnhap.dangNhap("PC01020", "Khang123@456");
		assertTrue(honline.fillComboboxBaiHoc(2).isEmpty() == false);
	}
	@Test
	public void changeLessonWhenStudiedOnline() {
		dangnhap.dangNhap("PC01020", "Khang123@456");
		assertTrue(honline.edit(2).isEmpty() == false);
	}
}
