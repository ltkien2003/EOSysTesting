/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.eos.ui;

import com.eos.dao.NguoiDungDAO;
import com.eos.dao.NhanVienDAO;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Kienltpc04639
 */
public class DoiMatKhauJDialog extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;

	/**
     * Creates new form DoiMatKhauJDialog
     */
    public DoiMatKhauJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
    }
    public DoiMatKhauJDialog() {
		// TODO Auto-generated constructor stub
	}
	NhanVienDAO dao = new NhanVienDAO();
    NguoiDungDAO nddao = new NguoiDungDAO();

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private boolean validateChangePassword() {
        String matKhau = new String(txtMatKhau.getPassword());
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String matKhauMoi2 = new String(txtMatKhauMoi2.getPassword());
        if (matKhau.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mật khẩu hiện tại");
            txtMatKhau.requestFocus();
        } else if (matKhauMoi.equals("")) {
            MsgBox.alert(this, "Vui lòng nhập mật khẩu mới");
            txtMatKhauMoi.requestFocus();
        } else if (!isValidPassword(matKhauMoi)) {
            MsgBox.alert(this, "Mật khẩu phải chứa ít nhất 8 ký tự và nhiều nhất là 20 ký tự và phải chứa\nít nhất một chữ số, một chữ cái viết in hoa, một chữ cái viết thường, một\nký tự đặc biệt bao gồm  !@#$%&*()-+=^  và không chứa bất kỳ khoảng\ntrắng nào.");
            txtMatKhauMoi.requestFocus();
        } else if (matKhauMoi2.equals("")) {
            MsgBox.alert(this, "Vui lòng xác nhận lại mật khẩu mới");
            txtMatKhauMoi2.requestFocus();
        } else {
            return true;
        }
        return false;
    }
    public String changePassword(String password, String newPassword, String rePassword) {
    	String matKhau = password;
    	String matKhauMoi = newPassword;
    	String matKhauMoi2 = rePassword;
    	String notification = "";
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = argon2.hash(10, 65536, 1, matKhauMoi);
        if (Auth.isLoginNhanVien()) {
        	if (matKhau.equals("")) {
        		notification = "Mật khẩu hiện tại không được bỏ trống";
        	} else if (matKhauMoi.equals("")) {
        		notification = "Mật khẩu mới không được bỏ trống";
        	} else if (!isValidPassword(matKhauMoi)) {
        		notification = "Mật khẩu phải chứa ít nhất 8 ký tự và nhiều nhất là 20 ký tự và phải chứa ít nhất một chữ số, một chữ cái viết in hoa, một chữ cái viết thường, một ký tự đặc biệt bao gồm  !@#$%&*()-+=^  và không chứa bất kỳ khoảng trắng nào.";
        	} else if (matKhauMoi2.equals("")) {
        		notification = "Vui lòng xác nhận lại mật khẩu mới";
        	} else if (!argon2.verify(Auth.user.getMatKhau().trim(), matKhau)) {
        		notification =  "Sai mật khẩu";
            } else if (!matKhauMoi.equals(matKhauMoi2)) {
                notification = "Xác nhận mật khẩu không đúng";
            } else {
                Auth.user.setMatKhau(hashedPassword);
                dao.changePassword(Auth.user);
                notification = "Đổi mật khẩu thành công!";
            }
        } else if (Auth.isLoginNguoiDung()) {
        	if (matKhau.equals("")) {
        		notification = "Vui lòng nhập mật khẩu hiện tại";
        	} else if (matKhauMoi.equals("")) {
        		notification = "Vui lòng nhập mật khẩu mới";
        	} else if (!isValidPassword(matKhauMoi)) {
        		notification = "Mật khẩu phải chứa ít nhất 8 ký tự và nhiều nhất là 20 ký tự và phải chứa\nít nhất một chữ số, một chữ cái viết in hoa, một chữ cái viết thường, một\nký tự đặc biệt bao gồm  !@#$%&*()-+=^  và không chứa bất kỳ khoảng\ntrắng nào.";
        	} else if (matKhauMoi2.equals("")) {
        		notification = "Vui lòng xác nhận lại mật khẩu mới";
        	} else if (!argon2.verify(Auth.user1.getMatKhau().trim(), matKhau)) {
               notification = "Sai mật khẩu";
            } else if (!matKhauMoi.equals(matKhauMoi2)) {
                notification =  "Xác nhận mật khẩu không đúng";
            } else {
                Auth.user1.setMatKhau(hashedPassword);
                nddao.changePassword(Auth.user1);
                notification =  "Đổi mật khẩu thành công!";
            }
        } else {
            notification = "Vui lòng đăng nhập";
        }
    	return notification;
    }

    private void doiMatKhau() {
        String matKhau = new String(txtMatKhau.getPassword());
        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String matKhauMoi2 = new String(txtMatKhauMoi2.getPassword());
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hashedPassword = argon2.hash(10, 65536, 1, matKhauMoi);
        if (Auth.isLoginNhanVien()) {
            if (!validateChangePassword()) {

            } else if (!argon2.verify(Auth.user.getMatKhau(), matKhau)) {
                MsgBox.alert(this, "Sai mật khẩu");
                txtMatKhau.requestFocus();
            } else if (!matKhauMoi.equals(matKhauMoi2)) {
                MsgBox.alert(this, "Xác nhận mật khẩu không đúng");
                txtMatKhauMoi2.requestFocus();
            } else {
                Auth.user.setMatKhau(hashedPassword);
                dao.changePassword(Auth.user);
                MsgBox.alert(this, "Đổi mật khẩu thành công!");
            }
        } else if (Auth.isLoginNguoiDung()) {
            if (!validateChangePassword()) {

            } else if (!argon2.verify(Auth.user1.getMatKhau(), matKhau)) {
                MsgBox.alert(this, "Sai mật khẩu");
                txtMatKhau.requestFocus();
            } else if (!matKhauMoi.equals(matKhauMoi2)) {
                MsgBox.alert(this, "Xác nhận mật khẩu không đúng");
                txtMatKhauMoi2.requestFocus();
            } else {
                Auth.user1.setMatKhau(hashedPassword);
                nddao.changePassword(Auth.user1);
                MsgBox.alert(this, "Đổi mật khẩu thành công!");
            }
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMatKhau = new javax.swing.JLabel();
        lblMatKhauMoi = new javax.swing.JLabel();
        lblMatKhau2 = new javax.swing.JLabel();
        txtMatKhauMoi = new javax.swing.JPasswordField();
        txtMatKhau = new javax.swing.JPasswordField();
        txtMatKhauMoi2 = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        lblTieuDe = new javax.swing.JLabel();
        btnDoiMatKhau = new javax.swing.JButton();
        btnHuyBo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Đổi mật khẩu");

        lblMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMatKhau.setText("Mật khẩu hiện tại");

        lblMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMatKhauMoi.setText("Mật khẩu mới");

        lblMatKhau2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMatKhau2.setText("Xác nhận mật khẩu mới");

        txtMatKhauMoi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMatKhauMoi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMatKhauMoi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatKhauMoiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauMoiFocusLost(evt);
            }
        });
        txtMatKhauMoi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatKhauMoiKeyPressed(evt);
            }
        });

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMatKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusLost(evt);
            }
        });
        txtMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatKhauKeyPressed(evt);
            }
        });

        txtMatKhauMoi2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMatKhauMoi2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMatKhauMoi2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMatKhauMoi2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauMoi2FocusLost(evt);
            }
        });
        txtMatKhauMoi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMatKhauMoi2KeyPressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/eos/icon/doimatkhau.jpg"))); // NOI18N

        lblTieuDe.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lblTieuDe.setText("Đổi mật khẩu");

        btnDoiMatKhau.setBackground(new java.awt.Color(54, 123, 246));
        btnDoiMatKhau.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setText("Đổi mật khẩu");
        btnDoiMatKhau.setBorder(null);
        btnDoiMatKhau.setFocusable(false);
        btnDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDoiMatKhauMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDoiMatKhauMouseExited(evt);
            }
        });
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        btnHuyBo.setBackground(new java.awt.Color(255, 255, 255));
        btnHuyBo.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnHuyBo.setForeground(new java.awt.Color(177, 177, 177));
        btnHuyBo.setText("Hủy bỏ");
        btnHuyBo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnHuyBo.setFocusable(false);
        btnHuyBo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHuyBoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHuyBoMouseExited(evt);
            }
        });
        btnHuyBo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyBoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTieuDe)
                    .addComponent(lblMatKhauMoi)
                    .addComponent(lblMatKhau)
                    .addComponent(lblMatKhau2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtMatKhauMoi2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(lblTieuDe)
                .addGap(18, 18, 18)
                .addComponent(lblMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(lblMatKhauMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMatKhau2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMatKhauMoi2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyBo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiMatKhauMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMatKhauMouseEntered
        btnDoiMatKhau.setBackground(new Color(54, 123, 220));
    }//GEN-LAST:event_btnDoiMatKhauMouseEntered

    private void btnDoiMatKhauMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMatKhauMouseExited
        btnDoiMatKhau.setBackground(new Color(54, 123, 246));
    }//GEN-LAST:event_btnDoiMatKhauMouseExited

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        this.doiMatKhau();
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void btnHuyBoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyBoMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyBoMouseEntered

    private void btnHuyBoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyBoMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHuyBoMouseExited

    private void btnHuyBoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyBoActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyBoActionPerformed

    private void txtMatKhauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusGained
        txtMatKhau.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        txtMatKhau.setEchoChar((char) 0);

    }//GEN-LAST:event_txtMatKhauFocusGained

    private void txtMatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusLost
        txtMatKhau.setBorder(new MatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
        txtMatKhau.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));

    }//GEN-LAST:event_txtMatKhauFocusLost

    private void txtMatKhauMoiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauMoiFocusGained
        txtMatKhauMoi.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        txtMatKhauMoi.setEchoChar((char) 0);
    }//GEN-LAST:event_txtMatKhauMoiFocusGained

    private void txtMatKhauMoiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauMoiFocusLost
        txtMatKhauMoi.setBorder(new MatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
        txtMatKhauMoi.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
    }//GEN-LAST:event_txtMatKhauMoiFocusLost

    private void txtMatKhauMoi2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauMoi2FocusGained
        txtMatKhauMoi2.setBorder(new MatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
        txtMatKhauMoi2.setEchoChar((char) 0);

    }//GEN-LAST:event_txtMatKhauMoi2FocusGained

    private void txtMatKhauMoi2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauMoi2FocusLost
        txtMatKhauMoi2.setBorder(new MatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
        txtMatKhauMoi2.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));

    }//GEN-LAST:event_txtMatKhauMoi2FocusLost

    private void txtMatKhauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDoiMatKhau.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnHuyBo.doClick();
        }
    }//GEN-LAST:event_txtMatKhauKeyPressed

    private void txtMatKhauMoiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauMoiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDoiMatKhau.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnHuyBo.doClick();
        }
    }//GEN-LAST:event_txtMatKhauMoiKeyPressed

    private void txtMatKhauMoi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMatKhauMoi2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnDoiMatKhau.doClick();
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            btnHuyBo.doClick();
        }
    }//GEN-LAST:event_txtMatKhauMoi2KeyPressed

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
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoiMatKhauJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DoiMatKhauJDialog dialog = new DoiMatKhauJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnHuyBo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblMatKhau2;
    private javax.swing.JLabel lblMatKhauMoi;
    private javax.swing.JLabel lblTieuDe;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JPasswordField txtMatKhauMoi;
    private javax.swing.JPasswordField txtMatKhauMoi2;
    // End of variables declaration//GEN-END:variables
}
