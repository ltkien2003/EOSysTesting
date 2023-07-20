package com.eos.ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.eos.dao.ChiTietDeThiDAO;
import com.eos.dao.DeThiDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.dao.KyThiDAO;
import com.eos.itext.ResultPDF;
import com.eos.model.DeThi;
import com.eos.model.KhoaHoc;
import com.eos.model.KyThi;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.XDate;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
/**
 *
 * @author Admin
 */
public class XemKetQuaJDialog extends javax.swing.JDialog {

    /**
     * Creates new form ThamGiaKHJDialog
     */
    JFileChooser fileChooser = new JFileChooser();
    DeThiDAO dtdao = new DeThiDAO();
    ChiTietDeThiDAO ctdtdao = new ChiTietDeThiDAO();
    KyThiDAO ktdao = new KyThiDAO();
    KhoaHocDAO khdao = new KhoaHocDAO();
    int row = -1;

    public XemKetQuaJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public XemKetQuaJDialog() {
		// TODO Auto-generated constructor stub
	}

	void init() {
        getRootPane().setOpaque(false);
        getContentPane().setBackground(Color.white);
        setBackground(Color.white);
        txtTenKyThi.requestFocus();
        btnTabDanhSach.doClick();
        tblDeThi.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblDeThi.getTableHeader().setForeground(Color.white);
        tblDeThi.getTableHeader().setOpaque(false);
        tblDeThi.getTableHeader().setBackground(new Color(0, 103, 192));
        tblDeThi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.fillTable();
        canGiua();
    }

    void setForm(DeThi dt) {
        try {
            KyThi kt = ktdao.selectByDeThi(dt.getMaDeThi());
            txtTenKyThi.setText(String.valueOf(kt.getTenKT()));
            txtTenKH.setText(timKiemTenKhoaHoc(kt.getMaKH()));
            txtMaDeThi.setText(String.valueOf(dt.getMaDeThi()));
            txtNgayThi.setText(XDate.toString(dt.getNgayTao(), "dd-MM-yyyy"));
            txtTongSoCau.setText(String.valueOf(ctdtdao.selectCountQuestion(dt.getMaDeThi())));
            txtSoCauDung.setText(String.valueOf(ctdtdao.selectCountCorrectQuestion(dt.getMaDeThi())));
            txtDiem.setText(String.valueOf(dt.getDiem()));
        } catch (Exception e) {
        }
    }

    void edit() {
        int maKT = Integer.parseInt(tblDeThi.getValueAt(this.row, 2).toString());
        DeThi dt = dtdao.selectById(maKT);
        this.setForm(dt);
        btnTabCapNhat.doClick();
    }
    public Object[] find(Integer maKT) {
    	try {
        	DeThi dt = dtdao.selectFindDeThi(maKT, Auth.user1.getMaND());
            KyThi kt = ktdao.selectByDeThi(dt.getMaDeThi());
            Object[] row = {kt.getTenKT(), timKiemTenKhoaHoc(kt.getMaKH()), dt.getMaDeThi(), ctdtdao.selectCountQuestion(dt.getMaDeThi()), ctdtdao.selectCountCorrectQuestion(dt.getMaDeThi()), dt.getDiem(), XDate.toString(dt.getNgayTao(), "dd-MM-yyyy")};
    		return row;
		} catch (Exception e) {
			return null;
		}
    }

    void canGiua() {
        TableCellRenderer rendererFromHeader = tblDeThi.getTableHeader().getDefaultRenderer();
        JLabel headerLabel = (JLabel) rendererFromHeader;
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblDeThi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblDeThi.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
    }

    private String timKiemTenKhoaHoc(int maKH) {
        KhoaHoc kh = khdao.selectByAllId(maKH);
        return kh.getTenKH();
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblDeThi.getModel();
        model.setRowCount(0);
        try {
            List<DeThi> list = dtdao.selectViewAnswer(Auth.user1.getMaND());
            for (DeThi dt : list) {
                KyThi kt = ktdao.selectByDeThi(dt.getMaDeThi());
                int soCauHoi = ctdtdao.selectCountQuestion(dt.getMaDeThi());
                int soCauDung = ctdtdao.selectCountCorrectQuestion(dt.getMaDeThi());
                Object[] row = {kt.getTenKT(), timKiemTenKhoaHoc(kt.getMaKH()), dt.getMaDeThi(), soCauHoi, soCauDung, dt.getDiem(), XDate.toString(dt.getNgayTao(), "dd-MM-yyyy")};
                model.addRow(row);
            }

        } catch (SQLException e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
        }
    }
    public List<Object[]> loadData() {
    	try {
    		List<Object[]> data  = new ArrayList<Object[]>();
    		List<DeThi> list = dtdao.selectViewAnswer(Auth.user1.getMaND());
    		for (DeThi dt : list) {
    			KyThi kt = ktdao.selectByDeThi(dt.getMaDeThi());
    			int soCauHoi = ctdtdao.selectCountQuestion(dt.getMaDeThi());
    			int soCauDung = ctdtdao.selectCountCorrectQuestion(dt.getMaDeThi());
    			Object[] row = {kt.getTenKT(), timKiemTenKhoaHoc(kt.getMaKH()), dt.getMaDeThi(), soCauHoi, soCauDung, dt.getDiem(), XDate.toString(dt.getNgayTao(), "dd-MM-yyyy")};
    	        data.add(row);
    		}
    		return data;
    		
    	} catch (SQLException e) {
    		return null;
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

        btnTabCapNhat = new javax.swing.JButton();
        btnTabDanhSach = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblMaDeThi = new javax.swing.JLabel();
        btnXemLaiDapAn = new javax.swing.JButton();
        lblThoiLuong = new javax.swing.JLabel();
        lblTongSoCau = new javax.swing.JLabel();
        txtTenKyThi = new javax.swing.JTextField();
        txtMaDeThi = new javax.swing.JTextField();
        txtTongSoCau = new javax.swing.JTextField();
        txtDiem = new javax.swing.JTextField();
        lblTenKyThi = new javax.swing.JLabel();
        lblNgayThi = new javax.swing.JLabel();
        txtNgayThi = new javax.swing.JTextField();
        lblTenKyThi1 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        lblSoCauDung = new javax.swing.JLabel();
        txtSoCauDung = new javax.swing.JTextField();
        btnXuatKetQua = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDeThi = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EOSys - Xem kết quả");

        btnTabCapNhat.setBackground(new java.awt.Color(255, 255, 255));
        btnTabCapNhat.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabCapNhat.setForeground(new java.awt.Color(177, 177, 177));
        btnTabCapNhat.setText("Tham gia");
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setContentAreaFilled(false);
        btnTabCapNhat.setFocusable(false);
        btnTabCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabCapNhatActionPerformed(evt);
            }
        });

        btnTabDanhSach.setBackground(new java.awt.Color(255, 255, 255));
        btnTabDanhSach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnTabDanhSach.setForeground(new java.awt.Color(177, 177, 177));
        btnTabDanhSach.setText("Danh sách");
        btnTabDanhSach.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTabDanhSach.setContentAreaFilled(false);
        btnTabDanhSach.setFocusable(false);
        btnTabDanhSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTabDanhSachActionPerformed(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lblMaDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblMaDeThi.setText("Mã đề thi");

        btnXemLaiDapAn.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXemLaiDapAn.setText("Xem lại đáp án");
        btnXemLaiDapAn.setFocusable(false);
        btnXemLaiDapAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemLaiDapAnActionPerformed(evt);
            }
        });

        lblThoiLuong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblThoiLuong.setText("Điểm");

        lblTongSoCau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTongSoCau.setText("Tổng số câu");

        txtTenKyThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenKyThi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenKyThi.setEnabled(false);
        txtTenKyThi.setOpaque(false);
        txtTenKyThi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenKyThiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKyThiFocusLost(evt);
            }
        });

        txtMaDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtMaDeThi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtMaDeThi.setEnabled(false);
        txtMaDeThi.setOpaque(false);
        txtMaDeThi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaDeThiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaDeThiFocusLost(evt);
            }
        });

        txtTongSoCau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTongSoCau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTongSoCau.setEnabled(false);
        txtTongSoCau.setOpaque(false);
        txtTongSoCau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTongSoCauFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTongSoCauFocusLost(evt);
            }
        });

        txtDiem.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtDiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtDiem.setEnabled(false);
        txtDiem.setOpaque(false);
        txtDiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiemFocusLost(evt);
            }
        });

        lblTenKyThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTenKyThi.setText("Tên kỳ thi");

        lblNgayThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNgayThi.setText("Ngày thi");

        txtNgayThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtNgayThi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtNgayThi.setEnabled(false);
        txtNgayThi.setOpaque(false);
        txtNgayThi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNgayThiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayThiFocusLost(evt);
            }
        });

        lblTenKyThi1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblTenKyThi1.setText("Tên khóa học");

        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtTenKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtTenKH.setEnabled(false);
        txtTenKH.setOpaque(false);
        txtTenKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTenKHFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKHFocusLost(evt);
            }
        });

        lblSoCauDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoCauDung.setText("Số câu đúng");

        txtSoCauDung.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        txtSoCauDung.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(177, 177, 177)));
        txtSoCauDung.setEnabled(false);
        txtSoCauDung.setOpaque(false);
        txtSoCauDung.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSoCauDungFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoCauDungFocusLost(evt);
            }
        });

        btnXuatKetQua.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        btnXuatKetQua.setText("Xuất kết quả");
        btnXuatKetQua.setFocusable(false);
        btnXuatKetQua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatKetQuaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenKyThi)
                    .addComponent(txtMaDeThi)
                    .addComponent(txtTongSoCau)
                    .addComponent(txtDiem)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblThoiLuong)
                            .addComponent(lblTenKyThi)
                            .addComponent(lblTongSoCau)
                            .addComponent(lblMaDeThi)
                            .addComponent(lblNgayThi)
                            .addComponent(lblTenKyThi1)
                            .addComponent(lblSoCauDung))
                        .addGap(0, 525, Short.MAX_VALUE))
                    .addComponent(txtNgayThi)
                    .addComponent(txtTenKH)
                    .addComponent(txtSoCauDung)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXemLaiDapAn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXuatKetQua)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenKyThi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKyThi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenKyThi1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(lblMaDeThi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaDeThi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTongSoCau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTongSoCau, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoCauDung)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoCauDung, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThoiLuong)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNgayThi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayThi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXemLaiDapAn)
                    .addComponent(btnXuatKetQua))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, "card2");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblDeThi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblDeThi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "TÊN KỲ THI", "TÊN KHÓA HỌC", "MÃ ĐỀ THI", "TỔNG SỐ CÂU", "SỐ CÂU ĐÚNG", "ĐIỂM", "NGÀY THI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDeThi.setGridColor(new java.awt.Color(177, 177, 177));
        tblDeThi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDeThiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDeThi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTabDanhSach)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTabCapNhat)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTabCapNhat)
                    .addComponent(btnTabDanhSach))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTabCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabCapNhatActionPerformed
        btnTabCapNhat.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabCapNhat.setForeground(new Color(0, 103, 192));
        btnTabDanhSach.setBorder(null);
        btnTabDanhSach.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.first(jPanel1);
    }//GEN-LAST:event_btnTabCapNhatActionPerformed

    private void btnTabDanhSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTabDanhSachActionPerformed
        btnTabDanhSach.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)),
                BorderFactory.createEmptyBorder(5, 20, 10, 20)));
        btnTabDanhSach.setForeground(new Color(0, 103, 192));
        btnTabCapNhat.setBorder(null);
        btnTabCapNhat.setForeground(new Color(177, 177, 177));
        CardLayout layout = (CardLayout) jPanel1.getLayout();
        layout.show(jPanel1, "card3");
    }//GEN-LAST:event_btnTabDanhSachActionPerformed

    private void btnXemLaiDapAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemLaiDapAnActionPerformed
        if (txtMaDeThi.getText().trim().equals("")) {
        } else {
            dispose();
            new XemLaiDapAnJDialog(Integer.parseInt(txtMaDeThi.getText())).setVisible(true);
        }
    }//GEN-LAST:event_btnXemLaiDapAnActionPerformed

    private void txtTenKyThiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKyThiFocusGained
        txtTenKyThi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTenKyThiFocusGained

    private void txtTenKyThiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKyThiFocusLost
        txtTenKyThi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTenKyThiFocusLost

    private void txtMaDeThiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaDeThiFocusGained
        txtMaDeThi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtMaDeThiFocusGained

    private void txtMaDeThiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaDeThiFocusLost
        txtMaDeThi.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtMaDeThiFocusLost

    private void txtTongSoCauFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTongSoCauFocusGained
        txtTongSoCau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtTongSoCauFocusGained

    private void txtTongSoCauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTongSoCauFocusLost
        txtTongSoCau.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtTongSoCauFocusLost

    private void txtDiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemFocusGained
        txtDiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0, 103, 192)));
    }//GEN-LAST:event_txtDiemFocusGained

    private void txtDiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemFocusLost
        txtDiem.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(177, 177, 177)));
    }//GEN-LAST:event_txtDiemFocusLost

    private void tblDeThiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDeThiMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblDeThi.getSelectedRow();
            this.edit();
            btnTabCapNhat.doClick();
        }
    }//GEN-LAST:event_tblDeThiMouseClicked

    private void txtNgayThiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayThiFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayThiFocusGained

    private void txtNgayThiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayThiFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayThiFocusLost

    private void txtTenKHFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHFocusGained

    private void txtTenKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKHFocusLost

    private void txtSoCauDungFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoCauDungFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoCauDungFocusGained

    private void txtSoCauDungFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoCauDungFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoCauDungFocusLost

    private void btnXuatKetQuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatKetQuaActionPerformed
        if (txtMaDeThi.getText().trim().equals("")) {
        } else {
            ResultPDF t = new ResultPDF();
            t.xuatKetQua(Integer.parseInt(txtMaDeThi.getText()));
        }
    }//GEN-LAST:event_btnXuatKetQuaActionPerformed

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
            java.util.logging.Logger.getLogger(XemKetQuaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(XemKetQuaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(XemKetQuaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(XemKetQuaJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                XemKetQuaJDialog dialog = new XemKetQuaJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnTabCapNhat;
    private javax.swing.JButton btnTabDanhSach;
    private javax.swing.JButton btnXemLaiDapAn;
    private javax.swing.JButton btnXuatKetQua;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMaDeThi;
    private javax.swing.JLabel lblNgayThi;
    private javax.swing.JLabel lblSoCauDung;
    private javax.swing.JLabel lblTenKyThi;
    private javax.swing.JLabel lblTenKyThi1;
    private javax.swing.JLabel lblThoiLuong;
    private javax.swing.JLabel lblTongSoCau;
    private javax.swing.JTable tblDeThi;
    private javax.swing.JTextField txtDiem;
    private javax.swing.JTextField txtMaDeThi;
    private javax.swing.JTextField txtNgayThi;
    private javax.swing.JTextField txtSoCauDung;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenKyThi;
    private javax.swing.JTextField txtTongSoCau;
    // End of variables declaration//GEN-END:variables
}
