/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.eos.ui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.eos.dao.CauHoiDAO;
import com.eos.dao.ChiTietDeThiDAO;
import com.eos.model.CauHoi;
import com.eos.model.ChiTietDeThi;
import com.eos.untils.MsgBox;

/**
 *
 * @author Admin
 */
public class XemLaiDapAnJDialog extends javax.swing.JFrame {

	/**
	 * Creates new form NewJFrame
	 */
	CauHoiDAO dao = new CauHoiDAO();
	ChiTietDeThiDAO ctdtdao = new ChiTietDeThiDAO();
	List<ChiTietDeThi> ctdtlist;
	List<CauHoi> list;
	int i = 0;
	String correct = "<span style=\"color:#0067C0;font-size: 14px;\">&ensp;&#10004;</span>";
	String uncorrect = "<span style=\"color:red;font-size: 14px;\">&ensp;&#10006;</span>";

	public XemLaiDapAnJDialog() {
		initComponents();
		getContentPane().setBackground(Color.white);
		getData(1065);
	}

	public XemLaiDapAnJDialog(String testing) {
	}

	public XemLaiDapAnJDialog(int maDeThi) {
		initComponents();
		getContentPane().setBackground(Color.white);
		getData(maDeThi);
	}

	private void getData(int maDeThi) {
		try {
			list = dao.selectQuestionExam(maDeThi);
			ctdtlist = ctdtdao.selectChiTietDeThi(maDeThi);
			loadData();
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
	}

	public List<Object[]> getViewAnswers(int maDeThi) {
		try {
			List<Object[]> data = new ArrayList<Object[]>();
			list = dao.selectQuestionExam(maDeThi);
			ctdtlist = ctdtdao.selectChiTietDeThi(maDeThi);
			for (int i = 0; i < ctdtlist.size(); i++) {
				Object[] row = { ctdtlist.get(i).getMaDeThi(), list.get(i).getTenCH(), list.get(i).getPA1(),
						list.get(i).getPA2(), list.get(i).getPA3(), list.get(i).getPA4(),
						ctdtlist.get(i).getPALuaChon(), list.get(i).getDapAn() };
				data.add(row);
			}
			return data;
		} catch (Exception e) {
			MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
		}
		return null;
	}

	public String getColor(int maDeThi, int ch) {
		try {
			list = dao.selectQuestionExam(maDeThi);
			ctdtlist = ctdtdao.selectChiTietDeThi(maDeThi);
			list = dao.selectQuestionExam(maDeThi);
			ctdtlist = ctdtdao.selectChiTietDeThi(maDeThi);
			if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getDapAn())) {
				if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA1())) {
					return "green";
				} else if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA2())) {
					return "green";
				} else if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA3())) {
					return "green";
				} else if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA4())) {
					return "green";
				}
			} else {
				if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA1())) {
					return "red";
				} else if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA2())) {
					return "red";
				} else if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA3())) {
					return "red";
				} else if (ctdtlist.get(ch - 1).getPALuaChon().equals(list.get(ch - 1).getPA4())) {
					return "red";
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	private void loadData() {
		lblTenQuiz.setText("Quiz " + ctdtlist.get(0).getMaDeThi());
		lblThuTuCauHoi.setText("Câu hỏi " + (i + 1) + " of " + list.size());
		if (ctdtlist.get(i).getPALuaChon() == null) {
			lblNoiDungCauHoi.setText("<html><div style=\"padding: 10px;color:red;\"><prev>" + list.get(i).getTenCH()
					+ "</prev><div></html>");
			lblPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1() + "</prev><div></html>");
			lblPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2() + "</prev><div></html>");
			lblPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3() + "</prev><div></html>");
			lblPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4() + "</prev><div></html>");
		} else {
			lblNoiDungCauHoi.setText(
					"<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getTenCH() + "</prev><div></html>");
			lblPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1() + "</prev><div></html>");
			lblPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2() + "</prev><div></html>");
			lblPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3() + "</prev><div></html>");
			lblPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4() + "</prev><div></html>");
		}
		resetColor();
		kiemTraPADaChon();
	}

	private void resetColor() {
		lblPA1.setForeground(Color.BLACK);
		lblPA2.setForeground(Color.BLACK);
		lblPA3.setForeground(Color.BLACK);
		lblPA4.setForeground(Color.BLACK);
	}

	private void kiemTraPADaChon() {
		String PADaChon = "<html><div style=\"padding: 10px;\"><prev>" + ctdtlist.get(i).getPALuaChon()
				+ "</prev><div></html>";
		String dapAn = "<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getDapAn() + "</prev><div></html>";
		if (PADaChon.equals(dapAn)) {
			if (lblPA1.getText().equals(PADaChon)) {
				rdoPA1.setSelected(true);
				lblPA1.setForeground(new Color(0, 103, 192));
				lblPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1() + correct
						+ "</prev><div></html>");
			} else if (lblPA2.getText().equals(PADaChon)) {
				rdoPA2.setSelected(true);
				lblPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2() + correct
						+ "</prev><div></html>");
				lblPA2.setForeground(new Color(0, 103, 192));
			} else if (lblPA3.getText().equals(PADaChon)) {
				rdoPA3.setSelected(true);
				lblPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3() + correct
						+ "</prev><div></html>");
				lblPA3.setForeground(new Color(0, 103, 192));
			} else if (lblPA4.getText().equals(PADaChon)) {
				rdoPA4.setSelected(true);
				lblPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4() + correct
						+ "</prev><div></html>");
				lblPA4.setForeground(new Color(0, 103, 192));
			}
		} else {
			kiemTraDapAn();
			if (lblPA1.getText().equals(PADaChon)) {
				rdoPA1.setSelected(true);
				lblPA1.setForeground(Color.RED);
				lblPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1() + uncorrect
						+ "</prev><div></html>");
			} else if (lblPA2.getText().equals(PADaChon)) {
				rdoPA2.setSelected(true);
				lblPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2() + uncorrect
						+ "</prev><div></html>");
				lblPA2.setForeground(Color.RED);
			} else if (lblPA3.getText().equals(PADaChon)) {
				rdoPA3.setSelected(true);
				lblPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3() + uncorrect
						+ "</prev><div></html>");
				lblPA3.setForeground(Color.RED);
			} else if (lblPA4.getText().equals(PADaChon)) {
				rdoPA4.setSelected(true);
				lblPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4() + uncorrect
						+ "</prev><div></html>");
				lblPA4.setForeground(Color.RED);
			}
		}

	}

	private void kiemTraDapAn() {
		String dapAn = "<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getDapAn() + "</prev><div></html>";
		if (lblPA1.getText().equals(dapAn)) {
			rdoPA1.setSelected(true);
			lblPA1.setForeground(new Color(0, 103, 192));
			lblPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1() + correct
					+ "</prev><div></html>");
		} else if (lblPA2.getText().equals(dapAn)) {
			rdoPA2.setSelected(true);
			lblPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2() + correct
					+ "</prev><div></html>");
			lblPA2.setForeground(new Color(0, 103, 192));
		} else if (lblPA3.getText().equals(dapAn)) {
			rdoPA3.setSelected(true);
			lblPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3() + correct
					+ "</prev><div></html>");
			lblPA3.setForeground(new Color(0, 103, 192));
		} else if (lblPA4.getText().equals(dapAn)) {
			rdoPA4.setSelected(true);
			lblPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4() + correct
					+ "</prev><div></html>");
			lblPA4.setForeground(new Color(0, 103, 192));
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		lblNoiDungCauHoi = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		rdoPA1 = new javax.swing.JRadioButton();
		rdoPA2 = new javax.swing.JRadioButton();
		rdoPA3 = new javax.swing.JRadioButton();
		rdoPA4 = new javax.swing.JRadioButton();
		lblPA1 = new javax.swing.JLabel();
		lblPA2 = new javax.swing.JLabel();
		lblPA3 = new javax.swing.JLabel();
		lblPA4 = new javax.swing.JLabel();
		lblTenQuiz = new javax.swing.JLabel();
		lblThuTuCauHoi = new javax.swing.JLabel();
		btnNext = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("EOSys - Xem đáp án");
		setBackground(new java.awt.Color(255, 255, 255));
		addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				formKeyPressed(evt);
			}
		});

		lblNoiDungCauHoi.setBackground(new java.awt.Color(255, 255, 255));
		lblNoiDungCauHoi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
		lblNoiDungCauHoi.setAlignmentX(20.0F);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));

		rdoPA1.setBackground(new java.awt.Color(255, 255, 255));
		buttonGroup1.add(rdoPA1);
		rdoPA1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		rdoPA1.setEnabled(false);
		rdoPA1.setFocusable(false);
		rdoPA1.setIconTextGap(10);
		rdoPA1.setMaximumSize(new java.awt.Dimension(121, 35));
		rdoPA1.setMinimumSize(new java.awt.Dimension(121, 35));
		rdoPA1.setPreferredSize(new java.awt.Dimension(121, 35));
		rdoPA1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rdoPA1ActionPerformed(evt);
			}
		});

		rdoPA2.setBackground(new java.awt.Color(255, 255, 255));
		buttonGroup1.add(rdoPA2);
		rdoPA2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		rdoPA2.setText("jRadioButton2");
		rdoPA2.setEnabled(false);
		rdoPA2.setFocusable(false);
		rdoPA2.setIconTextGap(10);
		rdoPA2.setMaximumSize(new java.awt.Dimension(121, 35));
		rdoPA2.setMinimumSize(new java.awt.Dimension(121, 35));
		rdoPA2.setPreferredSize(new java.awt.Dimension(121, 35));

		rdoPA3.setBackground(new java.awt.Color(255, 255, 255));
		buttonGroup1.add(rdoPA3);
		rdoPA3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		rdoPA3.setText("jRadioButton3");
		rdoPA3.setEnabled(false);
		rdoPA3.setFocusable(false);
		rdoPA3.setIconTextGap(10);
		rdoPA3.setMaximumSize(new java.awt.Dimension(121, 35));
		rdoPA3.setMinimumSize(new java.awt.Dimension(121, 35));
		rdoPA3.setPreferredSize(new java.awt.Dimension(121, 35));

		rdoPA4.setBackground(new java.awt.Color(255, 255, 255));
		buttonGroup1.add(rdoPA4);
		rdoPA4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		rdoPA4.setText("jRadioButton4");
		rdoPA4.setEnabled(false);
		rdoPA4.setFocusable(false);
		rdoPA4.setIconTextGap(10);
		rdoPA4.setMaximumSize(new java.awt.Dimension(121, 35));
		rdoPA4.setMinimumSize(new java.awt.Dimension(121, 35));
		rdoPA4.setPreferredSize(new java.awt.Dimension(121, 35));

		lblPA1.setBackground(new java.awt.Color(255, 255, 255));
		lblPA1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		lblPA1.setText("jLabel1");
		lblPA1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		lblPA1.setOpaque(true);

		lblPA2.setBackground(new java.awt.Color(255, 255, 255));
		lblPA2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		lblPA2.setText("jLabel1");
		lblPA2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		lblPA2.setOpaque(true);

		lblPA3.setBackground(new java.awt.Color(255, 255, 255));
		lblPA3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		lblPA3.setText("jLabel1");
		lblPA3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		lblPA3.setOpaque(true);

		lblPA4.setBackground(new java.awt.Color(255, 255, 255));
		lblPA4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
		lblPA4.setText("jLabel1");
		lblPA4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
		lblPA4.setOpaque(true);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
								.addComponent(rdoPA4, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
								.addComponent(rdoPA3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
								.addComponent(rdoPA2, javax.swing.GroupLayout.Alignment.LEADING,
										javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addComponent(rdoPA1, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(lblPA1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
								.addComponent(lblPA2, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblPA3, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblPA4, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(rdoPA1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblPA1, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
								.addComponent(rdoPA2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPA2, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(15, 15, 15)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(rdoPA3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPA3, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(15, 15, 15)
						.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(rdoPA4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPA4, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
										javax.swing.GroupLayout.PREFERRED_SIZE))));

		lblTenQuiz.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
		lblTenQuiz.setText("Quiz 1");

		lblThuTuCauHoi.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
		lblThuTuCauHoi.setText("Câu hỏi 1 of 40");

		btnNext.setBackground(new java.awt.Color(0, 103, 192));
		btnNext.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
		btnNext.setForeground(new java.awt.Color(255, 255, 255));
		btnNext.setText("Next >");
		btnNext.setBorder(null);
		btnNext.setFocusable(false);
		btnNext.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNextActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
								.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(35, 35, 35))
				.addGroup(layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup().addGap(39, 39, 39).addComponent(lblNoiDungCauHoi,
								javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup().addGap(49, 49, 49)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(lblTenQuiz).addComponent(lblThuTuCauHoi))))
						.addContainerGap(45, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
						.addComponent(lblTenQuiz).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(lblThuTuCauHoi)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(lblNoiDungCauHoi).addGap(18, 18, 18)
						.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
						.addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 62,
								javax.swing.GroupLayout.PREFERRED_SIZE)));

		setSize(new java.awt.Dimension(784, 537));
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNextActionPerformed
		if (i >= list.size() - 1) {
			i = 0;
			loadData();
		} else {
			i++;
			loadData();
		}
	}// GEN-LAST:event_btnNextActionPerformed

	private void rdoPA1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rdoPA1ActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_rdoPA1ActionPerformed

	private void formKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_formKeyPressed
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			btnNext.doClick();
		}
	}// GEN-LAST:event_formKeyPressed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(XemLaiDapAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(XemLaiDapAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(XemLaiDapAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(XemLaiDapAnJDialog.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new XemLaiDapAnJDialog().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnNext;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JLabel lblNoiDungCauHoi;
	private javax.swing.JLabel lblPA1;
	private javax.swing.JLabel lblPA2;
	private javax.swing.JLabel lblPA3;
	private javax.swing.JLabel lblPA4;
	private javax.swing.JLabel lblTenQuiz;
	private javax.swing.JLabel lblThuTuCauHoi;
	private javax.swing.JRadioButton rdoPA1;
	private javax.swing.JRadioButton rdoPA2;
	private javax.swing.JRadioButton rdoPA3;
	private javax.swing.JRadioButton rdoPA4;
	// End of variables declaration//GEN-END:variables
}
