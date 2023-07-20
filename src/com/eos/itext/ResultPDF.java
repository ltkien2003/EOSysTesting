package com.eos.itext;

import com.eos.dao.CauHoiDAO;
import com.eos.dao.ChiTietDeThiDAO;
import com.eos.dao.DeThiDAO;
import com.eos.dao.KhoaHocDAO;
import com.eos.dao.KyThiDAO;
import com.eos.dao.NguoiDungDAO;
import com.eos.model.CauHoi;
import com.eos.model.ChiTietDeThi;
import com.eos.model.DeThi;
import com.eos.model.KhoaHoc;
import com.eos.model.KyThi;
import com.eos.model.NguoiDung;
import com.eos.untils.Auth;
import com.eos.untils.MsgBox;
import com.eos.untils.XDate;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFileChooser;

public class ResultPDF {

    CauHoiDAO dao = new CauHoiDAO();
    ChiTietDeThiDAO ctdtdao = new ChiTietDeThiDAO();
    KhoaHocDAO khdao = new KhoaHocDAO();
    NguoiDungDAO nddao = new NguoiDungDAO();
    DeThiDAO dtdao = new DeThiDAO();
    KyThiDAO ktdao = new KyThiDAO();
//    public static void main(String[] args) {
//        ResultPDF t = new ResultPDF();
//        t.xuatKetQua();
//    }

    public void xuatKetQua(int maDeThi) {
        try {
            // Creating a PdfWriter object 
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("/home/me/Documents"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try ( FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".pdf")) {
                    String dest = chooser.getSelectedFile() + ".pdf";
                    PdfWriter writer = new PdfWriter(dest);
                    // Creating a PdfDocument object   
                    PdfDocument pdfDoc = new PdfDocument(writer);
                    pdfDoc.setDefaultPageSize(PageSize.A4);
                    // Creating a Document object
                    Document doc = new Document(pdfDoc);
                    // Creating a table
                    Table table = new Table(2);
                    String imageFile = "src\\com\\eos\\icon\\eos.png";
                    ImageData data = ImageDataFactory.create(imageFile);
                    // Creating the image       
                    Image img = new Image(data);
                    img.setHeight(100);
                    img.setWidth(100);
                    Cell cell1 = new Cell();
                    cell1.add(img);
                    cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    table.addCell(cell1);
                    PdfFont font = PdfFontFactory.createFont("src\\com\\eos\\font\\vuArial.ttf");
                    Cell cell = new Cell();
                    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    cell.add(new Paragraph("E- Learning Company \nĐịa chỉ: 288 đường Nguyễn Văn Linh, Hưng Lợi, \nNinh Kiều, Cần Thơ\nĐiện thoại: 02927300468").setFont(font)).setFontSize(16).setPaddingLeft(10);
                    table.addCell(cell);
                    RemoveBorder(table);
                    table.setHorizontalAlignment(HorizontalAlignment.LEFT);
                    doc.add(table);
                    Div d = new Div();
                    d.setTextAlignment(TextAlignment.CENTER);
                    d.add(new Paragraph("KẾT QUẢ KIỂM TRA").setFont(font).setFontSize(18).setBold());
                    doc.add(d);
                    KyThi kt = ktdao.selectByDeThi(maDeThi);
                    DeThi dt = dtdao.selectById(maDeThi);
                    Table table1 = new Table(3);
                    table1.addCell(new Paragraph("Họ và tên: " + Auth.user1.getHoTen()).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Tên khóa học: ").setFont(font).setFontSize(14).setPaddingLeft(150));
                    table1.addCell(new Paragraph(timKiemTenKhoaHoc(kt.getMaKH())).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.addCell(new Paragraph("Email: " + Auth.user1.getEmail()).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Số câu đúng: ").setFont(font).setFontSize(14).setPaddingLeft(150));
                    table1.addCell(new Paragraph(String.valueOf(ctdtdao.selectCountCorrectQuestion(maDeThi))).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.addCell(new Paragraph("Số điện thoại: " + Auth.user1.getDienThoai()).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Điểm: ").setFont(font).setFontSize(14).setPaddingLeft(150));
                    table1.addCell(new Paragraph(String.valueOf(dt.getDiem())).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.setMarginBottom(15);
                    RemoveBorder(table1);
                    doc.add(table1);
                    List<CauHoi> list = dao.selectQuestionExam(maDeThi);
                    List<ChiTietDeThi> ctdtlist = ctdtdao.selectChiTietDeThi(maDeThi);
                    for (int i = 0; i < list.size(); i++) {
                        String[] lines = list.get(i).getTenCH().split("\\R");
                        if (list.get(i).getDapAn().equals(list.get(i).getPA1())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA2())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA3())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));

                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA4())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                }
                            }
                        } else if (list.get(i).getDapAn().equals(list.get(i).getPA2())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA1())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA3())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));

                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA4())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                }
                            }

                        } else if (list.get(i).getDapAn().equals(list.get(i).getPA3())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA1())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA2())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));

                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA4())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                }
                            }

                        } else if (list.get(i).getDapAn().equals(list.get(i).getPA4())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA1())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA2())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA3())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));

                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));

                                }
                            }
                        }
                    }
                    doc.add(new Paragraph("Lưu ý:").setFont(font));
                    doc.add(new Paragraph("Câu hỏi được tô đậm màu đỏ là câu hỏi thí sinh không làm").setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                    doc.add(new Paragraph("Phương án được tô đậm màu đỏ là phương án sai mà thí sinh đã chọn").setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                    doc.add(new Paragraph("Phương án được tô đậm màu xanh là phương án đúng").setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                    doc.close();
                    if (MsgBox.confirm(null, "Bạn có muốn mở kết quả vừa xuất không?")) {
                        File f = new File(dest);
                        Desktop.getDesktop().open(f);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void xuatKetQuaChiTiet(int maDeThi) {
        try {
            // Creating a PdfWriter object 
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("/home/me/Documents"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try ( FileWriter fw = new FileWriter(chooser.getSelectedFile() + ".pdf")) {
                    String dest = chooser.getSelectedFile() + ".pdf";
                    PdfWriter writer = new PdfWriter(dest);
                    // Creating a PdfDocument object   
                    PdfDocument pdfDoc = new PdfDocument(writer);
                    pdfDoc.setDefaultPageSize(PageSize.A4);
                    // Creating a Document object
                    Document doc = new Document(pdfDoc);
                    // Creating a table
                    Table table = new Table(2);
                    String imageFile = "src\\com\\eos\\icon\\eos.png";
                    ImageData data = ImageDataFactory.create(imageFile);
                    // Creating the image       
                    Image img = new Image(data);
                    img.setHeight(100);
                    img.setWidth(100);
                    Cell cell1 = new Cell();
                    cell1.add(img);
                    cell1.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    table.addCell(cell1);
                    PdfFont font = PdfFontFactory.createFont("src\\com\\eos\\font\\vuArial.ttf");
                    Cell cell = new Cell();
                    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    cell.add(new Paragraph("E- Learning Company \nĐịa chỉ: 288 đường Nguyễn Văn Linh, Hưng Lợi, \nNinh Kiều, Cần Thơ\nĐiện thoại: 02927300468").setFont(font)).setFontSize(16).setPaddingLeft(10);
                    table.addCell(cell);
                    RemoveBorder(table);
                    table.setHorizontalAlignment(HorizontalAlignment.LEFT);
                    doc.add(table);
                    Div d = new Div();
                    d.setTextAlignment(TextAlignment.CENTER);
                    d.add(new Paragraph("KẾT QUẢ KIỂM TRA").setFont(font).setFontSize(18).setBold());
                    doc.add(d);
                    KyThi kt = ktdao.selectByDeThi(maDeThi);
                    DeThi dt = dtdao.selectById(maDeThi);
                    NguoiDung nd = nddao.findNameByIDHV(dt.getMaHV());
                    Table table1 = new Table(3);
                    table1.addCell(new Paragraph("Họ và tên: " + nd.getHoTen()).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Tên khóa học: ").setFont(font).setFontSize(14).setPaddingLeft(50));
                    table1.addCell(new Paragraph(timKiemTenKhoaHoc(kt.getMaKH())).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.addCell(new Paragraph("Email: " + nd.getEmail()).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Số câu đúng: ").setFont(font).setFontSize(14).setPaddingLeft(50));
                    table1.addCell(new Paragraph(String.valueOf(ctdtdao.selectCountCorrectQuestion(maDeThi))).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.addCell(new Paragraph("Số điện thoại: " + nd.getDienThoai()).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Điểm: ").setFont(font).setFontSize(14).setPaddingLeft(50));
                    table1.addCell(new Paragraph(String.valueOf(dt.getDiem())).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.addCell(new Paragraph("Thời gian bắt đầu: " + XDate.toString(dt.getTGBatDau(), "dd/MM/yyyy hh:mm:ss")).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Mã học viên: ").setFont(font).setFontSize(14).setPaddingLeft(50));
                    table1.addCell(new Paragraph(String.valueOf(dt.getMaHV())).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.addCell(new Paragraph("Thời gian kết thúc: " + XDate.toString(dt.getTGKetThuc(), "dd/MM/yyyy hh:mm:ss")).setFont(font).setFontSize(14));
                    table1.addCell(new Paragraph("Mã người dùng: ").setFont(font).setFontSize(14).setPaddingLeft(50));
                    table1.addCell(new Paragraph(String.valueOf(nd.getMaND())).setFont(font).setFontSize(14).setTextAlignment(TextAlignment.CENTER));
                    table1.setMarginBottom(15);
                    RemoveBorder(table1);
                    doc.add(table1);
                    List<CauHoi> list = dao.selectQuestionExam(maDeThi);
                    List<ChiTietDeThi> ctdtlist = ctdtdao.selectChiTietDeThi(maDeThi);
                    for (int i = 0; i < list.size(); i++) {
                        String[] lines = list.get(i).getTenCH().split("\\R");
                        if (list.get(i).getDapAn().equals(list.get(i).getPA1())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA2())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA3())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));

                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA4())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                }
                            }
                        } else if (list.get(i).getDapAn().equals(list.get(i).getPA2())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA1())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA3())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));

                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA4())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                }
                            }

                        } else if (list.get(i).getDapAn().equals(list.get(i).getPA3())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA1())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA2())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));

                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA4())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font));
                                }
                            }

                        } else if (list.get(i).getDapAn().equals(list.get(i).getPA4())) {
                            if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA1())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA2())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                            } else if (ctdtlist.get(i).getPALuaChon() != null && ctdtlist.get(i).getPALuaChon().equals(list.get(i).getPA3())) {
                                doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));

                            } else {
                                if (ctdtlist.get(i).getPALuaChon() == null) {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold().setFontColor(new DeviceRgb(java.awt.Color.RED)));
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                                } else {
                                    doc.add(new Paragraph(i + 1 + ". " + lines[0]).setFont(font).setBold());
                                    doc.add(new Paragraph("A. " + list.get(i).getPA1()).setFont(font));
                                    doc.add(new Paragraph("B. " + list.get(i).getPA2()).setFont(font));
                                    doc.add(new Paragraph("C. " + list.get(i).getPA3()).setFont(font));
                                    doc.add(new Paragraph("D. " + list.get(i).getPA4()).setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));

                                }
                            }
                        }
                    }
                    doc.add(new Paragraph("Lưu ý:").setFont(font));
                    doc.add(new Paragraph("Câu hỏi được tô đậm màu đỏ là câu hỏi thí sinh không làm").setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                    doc.add(new Paragraph("Phương án được tô đậm màu đỏ là phương án sai mà thí sinh đã chọn").setFont(font).setFontColor(new DeviceRgb(java.awt.Color.RED)));
                    doc.add(new Paragraph("Phương án được tô đậm màu xanh là phương án đúng").setFont(font).setFontColor(new DeviceRgb(0, 103, 192)));
                    doc.close();
                    if (MsgBox.confirm(null, "Bạn có muốn mở kết quả vừa xuất không?")) {
                        File f = new File(dest);
                        Desktop.getDesktop().open(f);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private String timKiemTenKhoaHoc(int maKH) {
        KhoaHoc kh = khdao.selectByAllId(maKH);
        return kh.getTenKH();
    }

    private static void RemoveBorder(Table table) {
        for (IElement iElement : table.getChildren()) {
            ((Cell) iElement).setBorder(Border.NO_BORDER);
        }
    }

}
