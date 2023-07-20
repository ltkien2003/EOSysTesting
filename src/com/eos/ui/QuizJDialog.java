/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.eos.ui;

import com.eos.dao.CauHoiDAO;
import com.eos.dao.ChiTietDeThiDAO;
import com.eos.dao.ChiTietKyThiDAO;
import com.eos.dao.DeThiDAO;
import com.eos.dao.HocVienDAO;
import com.eos.dao.KyThiDAO;
import com.eos.model.CauHoi;
import com.eos.model.ChiTietDeThi;
import com.eos.model.ChiTietKyThi;
import com.eos.model.DeThi;
import com.eos.model.HocVien;
import com.eos.model.KyThi;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.XDate;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class QuizJDialog extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    CauHoiDAO dao = new CauHoiDAO();
    KyThiDAO ktdao = new KyThiDAO();
    HocVienDAO hvdao = new HocVienDAO();
    ChiTietDeThiDAO ctdtdao = new ChiTietDeThiDAO();
    ChiTietKyThiDAO ctktdao = new ChiTietKyThiDAO();
    DeThiDAO dtdao = new DeThiDAO();
    Date dateBatDau = XDate.now();
    Date dateKetThuc = null;
    int i = 0;
    double score = 0;
    List<CauHoi> list;
    List<ChiTietDeThi> ctdtlist;
    List<KyThi> ktlist;
    long sec;
    long min;

    public QuizJDialog() {
        initComponents();
        getContentPane().setBackground(Color.white);
//        layDuLieuKyThi();
        loadData();
        tinhThoiGian();
        chayThoiGian();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                calScore();
                JOptionPane.showMessageDialog(null,
                        "Điểm của bạn: " + score);
                timKiemMaHocVien();
//                tinhThoiGianLamBai();

            }
        });
    }

    public QuizJDialog(int maKyThi) {
        initComponents();
        getContentPane().setBackground(Color.white);
        layDuLieuKyThi(maKyThi);
        themDeThi();
        themChiTietKyThi();
        loadData();
        themChiTietDeThi();
        tinhThoiGian();
        chayThoiGian();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                calScore();
                JOptionPane.showMessageDialog(null,
                        "Điểm của bạn: " + score);
                capNhatDeThi();
            }
        });
    }

    private void themDeThi() {
        dateKetThuc = null;
        try {
            DeThi dt = getInsertDeThi();
            dtdao.insert(dt);
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi thêm dữ liệu");
        }
    }

    private void capNhatDeThi() {
        dateKetThuc = XDate.now();
        try {
            DeThi dt = getUpdateDeThi();
            dtdao.update(dt);
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi cập nhật dữ liệu");
        }
    }

    private void themChiTietKyThi() {
        try {
            ChiTietKyThi ctkt = getInsertChiTietKyThi();
            ctktdao.insert(ctkt);
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi thêm dữ liệu");
        }
    }

    private void tinhThoiGian() {
        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = simpleDateFormat.parse(XDate.changePeriod(XDate.now()));
            date2 = simpleDateFormat.parse(XDate.changePeriod(ktlist.get(0).getTGDongDe()));
        } catch (ParseException ex) {
            Logger.getLogger(QuizJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        long differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());
        long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;
        differenceInMinutes += ((differenceInMilliSeconds / (60 * 60 * 1000))
                % 24) * 60;
        long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;
        if (differenceInMinutes >= ktlist.get(0).getTGLamBai()) {
            differenceInMinutes = ktlist.get(0).getTGLamBai();
            differenceInSeconds = 0;
        }

        min = differenceInMinutes;
        sec = differenceInSeconds;

    }

    DeThi getInsertDeThi() {
        DeThi dt = new DeThi();
        dt.setTGBatDau(dateBatDau);
        dt.setNgayTao(XDate.now());
        dt.setMaHV(timKiemMaHocVien());
        return dt;
    }

    DeThi getUpdateDeThi() {
        DeThi dt = new DeThi();
        dt.setMaDeThi(timKiemMaDeThi());
        dt.setTGKetThuc(dateKetThuc);
        dt.setDiem(score);
        dt.setMaHV(timKiemMaHocVien());
        return dt;
    }

    ChiTietDeThi getInsertChiTietDeThi(int thuTu) {
        ChiTietDeThi ctdt = new ChiTietDeThi();
        ctdt.setMaCH(list.get(thuTu).getMaCH());
        ctdt.setMaDeThi(timKiemMaDeThi());
        return ctdt;
    }

    ChiTietKyThi getInsertChiTietKyThi() {
        ChiTietKyThi ctkt = new ChiTietKyThi();
        ctkt.setMaKyThi(ktlist.get(0).getMaKyThi());
        ctkt.setMaDeThi(timKiemMaDeThi());
        return ctkt;
    }

    ChiTietDeThi getUpdateChiTietDeThi(String PALuaChon) {
        ChiTietDeThi ctdt = new ChiTietDeThi();
        ctdt.setPALuaChon(PALuaChon);
        ctdt.setIDCH(timKiemIDCH());
        return ctdt;
    }

    private int timKiemMaHocVien() {
        int maKH = ktlist.get(0).getMaKH();
        String maND = Auth.user1.getMaND();
        HocVien hv = hvdao.selectByFindMaND(maKH, maND);
        return hv.getMaHV();
    }

    private int timKiemIDCH() {
        int maCH = list.get(i).getMaCH();
        ChiTietDeThi ctdt = ctdtdao.selectFindIDCH(timKiemMaDeThi(), maCH);
        return ctdt.getIDCH();
    }

    private int timKiemMaDeThi() {
        Timestamp ts1 = new Timestamp(dateBatDau.getTime());
        DeThi dt = dtdao.selectFindMaDeThi(ts1.toString(), timKiemMaHocVien());
        return dt.getMaDeThi();
    }

    private void tinhThoiGianLamBai() {
        long differenceInMilliSeconds
                = dateKetThuc.getTime() - dateBatDau.getTime();
        long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;
        long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;

        differenceInMinutes += ((differenceInMilliSeconds / (60 * 60 * 1000))
                % 24) * 60;
        JOptionPane.showMessageDialog(null, differenceInMinutes + ":" + differenceInSeconds);
    }

    private void chayThoiGian() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (sec >= 1) {
                    sec = sec - 1;
                    if (sec < 10 && min < 10) {
                        lblDongHo.setText(String.valueOf("0" + min + " : 0" + sec));

                    } else if (min < 10) {
                        lblDongHo.setText(String.valueOf("0" + min + " : " + sec));
                    } else if (sec < 10) {
                        lblDongHo.setText(String.valueOf(min + " : 0" + sec));
                    } else {
                        lblDongHo.setText(String.valueOf(min + " : " + sec));
                    }
                } else if (sec == 0 && min > 0) {
                    sec = 59;
                    min = min - 1;
                    if (min < 10) {
                        lblDongHo.setText(String.valueOf("0" + min + " : " + sec));

                    } else {
                        lblDongHo.setText(String.valueOf(min + " : " + sec));
                    }
                } else if (min <= 0 || ktlist.get(0).getTGDongDe().compareTo(XDate.now()) == 0) {
                    JOptionPane.showMessageDialog(null, "Hết giờ");
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "Điểm của bạn: " + score);
                    dispose();
                }
            }
        }, 0, 1000);
    }

    private void layDuLieuKyThi(int maKyThi) {
        try {
            ktlist = ktdao.selectAllByID(maKyThi);
            lblTenKyThi.setText(ktlist.get(0).getTenKT());
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void loadData() {
        try {
            list = dao.selectQuestion(ktlist.get(0).getTongSoCau(), ktlist.get(0).getMaChuong());
            lblThuTuCauHoi.setText("Câu hỏi " + (i + 1) + " of " + list.size());
            lblNDCauHoi.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getTenCH().trim() + "</prev><div></html>");
            rdoPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1().trim() + "</prev><div></html>");
            rdoPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2().trim() + "</prev><div></html>");
            rdoPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3().trim() + "</prev><div></html>");
            rdoPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4().trim() + "</prev><div></html>");
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }

    private void themChiTietDeThi() {
        for (int j = 0; j < list.size(); j++) {
            try {
                ChiTietDeThi ctdt = getInsertChiTietDeThi(j);
                ctdtdao.insert(ctdt);
            } catch (Exception e) {
                MsgBox.alert(null, "Lỗi thêm dữ liệu");
            }
        }
    }

    private void capNhatChiTietDeThi(String PALuaChon) {
        try {
            ChiTietDeThi ctdt = getUpdateChiTietDeThi(PALuaChon);
            ctdtdao.update(ctdt);
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi thêm dữ liệu");
        }

    }

    private void calScore() {
        double diemTungCau = 10.0 / ktlist.get(0).getTongSoCau();
        String dapAn = "<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getDapAn().trim() + "</prev><div></html>";
        if (rdoPA1.isSelected() && rdoPA1.getText().trim().equals(dapAn)) {
            score += diemTungCau;
            capNhatChiTietDeThi(list.get(i).getPA1());
        } else if (rdoPA2.isSelected() && rdoPA2.getText().trim().equals(dapAn)) {
            score += diemTungCau;
            capNhatChiTietDeThi(list.get(i).getPA2());
        } else if (rdoPA3.isSelected() && rdoPA3.getText().trim().equals(dapAn)) {
            score += diemTungCau;
            capNhatChiTietDeThi(list.get(i).getPA3());
        } else if (rdoPA4.isSelected() && rdoPA4.getText().trim().equals(dapAn)) {
            score += diemTungCau;
            capNhatChiTietDeThi(list.get(i).getPA4());
        } else {
            if (rdoPA1.isSelected()) {
                capNhatChiTietDeThi(list.get(i).getPA1());
            } else if (rdoPA2.isSelected()) {
                capNhatChiTietDeThi(list.get(i).getPA2());
            } else if (rdoPA3.isSelected()) {
                capNhatChiTietDeThi(list.get(i).getPA3());
            } else if (rdoPA4.isSelected()) {
                capNhatChiTietDeThi(list.get(i).getPA4());
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblNDCauHoi = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rdoPA1 = new javax.swing.JRadioButton();
        rdoPA2 = new javax.swing.JRadioButton();
        rdoPA3 = new javax.swing.JRadioButton();
        rdoPA4 = new javax.swing.JRadioButton();
        lblTenKyThi = new javax.swing.JLabel();
        lblThuTuCauHoi = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        lblDongHo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Thi");
        setBackground(new java.awt.Color(255, 255, 255));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        lblNDCauHoi.setBackground(new java.awt.Color(255, 255, 255));
        lblNDCauHoi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblNDCauHoi.setAlignmentX(20.0F);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(4, 0, 0, 15));

        rdoPA1.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoPA1);
        rdoPA1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoPA1.setText("jRadioButton1");
        rdoPA1.setFocusable(false);
        rdoPA1.setIconTextGap(10);
        rdoPA1.setMaximumSize(new java.awt.Dimension(121, 35));
        rdoPA1.setMinimumSize(new java.awt.Dimension(121, 35));
        rdoPA1.setPreferredSize(new java.awt.Dimension(121, 35));
        jPanel1.add(rdoPA1);

        rdoPA2.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoPA2);
        rdoPA2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoPA2.setText("jRadioButton2");
        rdoPA2.setFocusable(false);
        rdoPA2.setIconTextGap(10);
        rdoPA2.setMaximumSize(new java.awt.Dimension(121, 35));
        rdoPA2.setMinimumSize(new java.awt.Dimension(121, 35));
        rdoPA2.setPreferredSize(new java.awt.Dimension(121, 35));
        jPanel1.add(rdoPA2);

        rdoPA3.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoPA3);
        rdoPA3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoPA3.setText("jRadioButton3");
        rdoPA3.setFocusable(false);
        rdoPA3.setIconTextGap(10);
        rdoPA3.setMaximumSize(new java.awt.Dimension(121, 35));
        rdoPA3.setMinimumSize(new java.awt.Dimension(121, 35));
        rdoPA3.setPreferredSize(new java.awt.Dimension(121, 35));
        jPanel1.add(rdoPA3);

        rdoPA4.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoPA4);
        rdoPA4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoPA4.setText("jRadioButton4");
        rdoPA4.setFocusable(false);
        rdoPA4.setIconTextGap(10);
        rdoPA4.setMaximumSize(new java.awt.Dimension(121, 35));
        rdoPA4.setMinimumSize(new java.awt.Dimension(121, 35));
        rdoPA4.setPreferredSize(new java.awt.Dimension(121, 35));
        jPanel1.add(rdoPA4);

        lblTenKyThi.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lblTenKyThi.setText("Quiz 1");

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

        lblDongHo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblDongHo.setText("10:10");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(lblNDCauHoi, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenKyThi)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblThuTuCauHoi)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDongHo))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenKyThi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblThuTuCauHoi)
                    .addComponent(lblDongHo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNDCauHoi)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(784, 537));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (i >= list.size() - 1) {
            calScore();
            JOptionPane.showMessageDialog(this, "Điểm của bạn: " + score);
            dispose();
            dateKetThuc = XDate.now();
            capNhatDeThi();
        } else {
            switch (btnNext.getText()) {
                case "Next >" -> {
                    if (!rdoPA1.isSelected() && !rdoPA2.isSelected() && !rdoPA3.isSelected() && !rdoPA4.isSelected()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất 1 đáp án");
                    } else {
                        calScore();
                        buttonGroup1.clearSelection();
                        i++;
                        lblNDCauHoi.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getTenCH() + "</prev><div></html>");
                        rdoPA1.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA1() + "</prev><div></html>");
                        rdoPA2.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA2() + "</prev><div></html>");
                        rdoPA3.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA3() + "</prev><div></html>");
                        rdoPA4.setText("<html><div style=\"padding: 10px;\"><prev>" + list.get(i).getPA4() + "</prev><div></html>");
                        if (i == ktlist.get(0).getTongSoCau() - 1) {
                            btnNext.setText("Submit");
                        }
                        lblThuTuCauHoi.setText("Câu hỏi " + (i + 1) + " of " + ktlist.get(0).getTongSoCau());
                    }
                }

            }
        }

    }//GEN-LAST:event_btnNextActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            btnNext.doClick();
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(QuizJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuizJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuizJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuizJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuizJDialog().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblNDCauHoi;
    private javax.swing.JLabel lblTenKyThi;
    private javax.swing.JLabel lblThuTuCauHoi;
    private javax.swing.JRadioButton rdoPA1;
    private javax.swing.JRadioButton rdoPA2;
    private javax.swing.JRadioButton rdoPA3;
    private javax.swing.JRadioButton rdoPA4;
    // End of variables declaration//GEN-END:variables
}
