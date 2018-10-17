package geso.traphaco.erp.servlets.lenhsanxuatgiay;

import geso.traphaco.center.beans.cauhinhinhoadon.IErpCauHinhInHoaDon;
import geso.traphaco.center.beans.cauhinhinhoadon.imp.ErpCauHinhInHoaDon;
import geso.traphaco.center.beans.doctien.DocTien;
import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.util.Utility;
import geso.dms.db.sql.dbutils;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Color;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInTongKetLoSanXuatpdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String tenSp="";
	private String dangBaoChe="";
	private String QuyCach="";
	private String id="";
	private String soLo="";
	private String ngaySX="";
	private String HD="";
	private String soLuongTheoLenh="0";
	private float soLuongNhapKho=0;
	private String songaychamsovoilenh="";
	private String phaChe="";
	private String chon="";
	private String ngayBatDauSX="";
	private String ngayHoanThanh="";
	private String ngayGiaoSPTheoLenh="";
	private float soLuongLayMau=0;
	private String congDoanSx="";
	private String congDoanDG="";
	private String toanChang="";
	private String ghiChu="";
	private String yKienKhac="";
	private String ndHL="";
	private String [] soTT = new String [5];
	private String [] noiDung= new String [5];
	private String [] soPKN= new String [5];
	private String [] ngayKiemNghiem= new String [5];
	private String [] ketQua= new String [5];
	private String [] ghiChuKiemNghiem= new String [5];
	private String [] check = new String[3] ;
	
    public ErpInTongKetLoSanXuatpdfSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		//String userTen = (String) session.getAttribute("userTen");  	

		 if (userId.length() == 0)
		    	userId = request.getParameter("userId");
		Utility util = new Utility(); 
		String querystring = request.getQueryString();
		String id = util.getId(querystring);
		this.id=id;

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=HOSOLO_"+id+".pdf");
		
//		IErpCauHinhInHoaDon config = new ErpCauHinhInHoaDon();
//		config.initWithName("PHIEUTHU");
//		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 1*CONVERT, PAGE_RIGHT = 1*CONVERT, PAGE_TOP = 0.5f*CONVERT, PAGE_BOTTOM = 0.5f*CONVERT; //cm //cm
//		float PAGE_LEFT = config.getMarginLeft()*CONVERT, PAGE_RIGHT = config.getMarginRight()*CONVERT, PAGE_TOP = config.getMarginTop()*CONVERT, PAGE_BOTTOM = config.getMarginBottom()*CONVERT; //cm
		Rectangle a4 = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight());
		Document document = new Document(a4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		
		ServletOutputStream outstream = response.getOutputStream();
		
		this.CreateLSX(document, outstream);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.doGet(request, response);
	}
	
	private void CreateLSX(Document document, ServletOutputStream outstream) 
	{
		try
		{			
			
			
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter2 = new DecimalFormat("#,###,##0.00000"); 
			NumberFormat formatter3 = new DecimalFormat("#,###,##0.000");
//			
//			IErpCauHinhInHoaDon config = new ErpCauHinhInHoaDon();
//			config.initWithName("PHIEUCHI");
//			
//			IErpCauHinhInHoaDon configp = new ErpCauHinhInHoaDon();
//			configp.initWithName("PHIEUCHI");
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));
			BaseFont charbase = BaseFont.createFont("C:\\windows\\fonts\\Wingding.ttf", BaseFont.IDENTITY_H, false);
			Font charfont = new Font(charbase, 11f, Font.NORMAL);
			char checked='\u00FE';
			char unchecked='\u00A8';

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font12boldblue = new Font(bf, 12, Font.BOLD);
			font12boldblue.setColor(10,10,150);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font13boldblue = new Font(bf, 13, Font.BOLD);
			font13boldblue.setColor(10,10,150);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font14boldblue = new Font(bf, 14, Font.BOLD);
			font14boldblue.setColor(10,10,150);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font15boldblue = new Font(bf, 15, Font.BOLD);
			font15boldblue.setColor(10,10,150);
			Font font22boldblue = new Font(bf, 22, Font.BOLD);
			font22boldblue.setColor(10,10,150);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font12blue = new Font(bf, 12, Font.NORMAL);
			font12blue.setColor(10,10,150);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font13Italic = new Font(bf, 13,Font.ITALIC);
			font13Italic.setColor(10,10,150);
			Font font13blue = new Font(bf, 13, Font.NORMAL);
			font13blue.setColor(10,10,150);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font14blue = new Font(bf, 14, Font.NORMAL);
			font14blue.setColor(10,10,150);
			Font font15 = new Font(bf, 15, Font.NORMAL);
			Font font15blue = new Font(bf, 15, Font.NORMAL);
			font15blue.setColor(10,10,150);
			
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_WIDTHS = new float[] {18f * CONVERT };
			float[] TABLE_HEADER_WIDTHS = new float[] {9.25f * CONVERT ,11.25f * CONVERT };
			float[] TABLE_CONTENT_WIDTHS = new float[] {1.5f * CONVERT, 9f * CONVERT,4f * CONVERT ,3f * CONVERT};
			float[] TABLE_CONTENT2_WIDTHS = new float[] {1.5f * CONVERT, 16f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS = new float[] {1.5f * CONVERT, 0.8f * CONVERT, 3.1f * CONVERT,3.8f * CONVERT,2.4f * CONVERT,3.8f * CONVERT,2.5f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS2 = new float[] {1.5f * CONVERT, 2.62f * CONVERT, 2.62f * CONVERT,2.66f * CONVERT,3.3f * CONVERT,5.0f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS3 = new float[] {1.5f * CONVERT, 2.62f * CONVERT, 2.62f * CONVERT,2.66f * CONVERT,2.2f * CONVERT,2.2f * CONVERT,2.0f * CONVERT,2f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS5 = new float[] {1.5f * CONVERT, 2.62f * CONVERT, 2.62f * CONVERT,3.5f * CONVERT,1.5f * CONVERT,3.5f * CONVERT,3.0f*CONVERT};
			float[] TABLE_FOOTER_WIDTHS = new float[] {1.5f * CONVERT, 5.6f * CONVERT ,5.6f* CONVERT,5.6f* CONVERT };
			float[] TABLE_CONTENT_FIELDS_WIDTHS4 = new float[] {1.5f * CONVERT ,6.5f * CONVERT,10f * CONVERT };
			PdfPCell cell, cell2, cell3;

			
			int BORDER_WIDTH = 0;

			
			LayDuLieuSanPham();
			LayDuLieuHoSoLo();
			
			LayDuLieuNhapKho();
			LayDuLieuPhieuKiemNghiem();
			LayDuLieuTongKetLo();
			LayDuLieuTienDo();
			LayDuLieuCheck(checked, unchecked);
			PdfPTable table2 = new PdfPTable(TABLE_HEADER_WIDTHS.length);	
			table2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100.0f);
			
			

			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("pathPrint")+"/logo.gif");
			hinhanh.scalePercent(10);
			hinhanh.setAbsolutePosition(4f * CONVERT, document.getPageSize().getHeight() - 1.2f * CONVERT);
			

			cell = new PdfPCell();
			
			Paragraph p = new Paragraph();
			p = new Paragraph();
			p.add("\n");
//			cell.addElement(hinhanh);
			cell.setBorder(0);
			
			p.add(new Chunk("CÔNG TY TNHH TRAPHACO HƯNG YÊN", font13)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.addElement(p);
			table2.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("BM411C \n", font13)); 
			p.add(new Chunk("BH/SĐ: 0110711 \n", font13)); 
			p.setAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table2.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("TỔNG KẾT LÔ SẢN XUẤT \n", font14bold));
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table2.addCell(cell);
			
			PdfPTable table3 = new PdfPTable(TABLE_CONTENT_WIDTHS.length);	
			table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.setWidths(TABLE_CONTENT_WIDTHS);
			table3.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table3.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("TÊN SẢN PHẨM : ", font13));
			p.add(new Chunk("" + this.tenSp, font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.setColspan(2);
			cell.addElement(p);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số lô : " , font12));
			p.add(new Chunk(""+ this.soLo , font10));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.addElement(p);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Dạng bào chế : "+this.dangBaoChe , font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("NĐ/HL : " + this.ndHL +"%", font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("NSX : "+ this.ngaySX , font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Quy cách : "+this.QuyCach , font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(2);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("HD : " +this.HD, font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			
		
			
//			PdfPTable table4 = new PdfPTable(TABLE_CONTENT2_WIDTHS.length);	
//			table4.setHorizontalAlignment(Element.ALIGN_CENTER);
//			table4.setWidths(TABLE_CONTENT2_WIDTHS);
//			table4.setWidthPercentage(100.0f);
//			
//			cell = new PdfPCell();
//			p = new Paragraph();
//			p.add(new Chunk("",font11));
//			p.setAlignment(Element.ALIGN_LEFT);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell.addElement(p);
//			cell.setColspan(2);
//			cell.setBorder(0);
//			table4.addCell(cell);
//			
//			cell = new PdfPCell();
//			p = new Paragraph();
//			p.add("");
//			p.setAlignment(Element.ALIGN_LEFT);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell.addElement(p);
//			cell.setBorder(0);
//			table4.addCell(cell);
//			
//			
//			// PHẦN I.
//			cell = new PdfPCell();
//			p = new Paragraph();
//			p.add(new Chunk("I. HỒ SƠ LÔ NÀY GỒM " , font14bold));
//			p.setAlignment(Element.ALIGN_LEFT);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell.addElement(p);
//			cell.setBorderWidth(1);
//			table4.addCell(cell);
//
//			
//			cell = new PdfPCell();
//			p = new Paragraph();
//			p.add("");
//			p.setAlignment(Element.ALIGN_LEFT);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell.addElement(p);
//			cell.setBorder(0);
//			table4.addCell(cell);
//			
//			cell = new PdfPCell();
//			p = new Paragraph();
//			p.add(new Chunk("1. Lệnh sản xuất .................................................................................................................... " , font13));
//			p.add(new Chunk(check[0],charfont));
//			p.add(new Chunk("\n2. Phiếu giao nhận vật tư SX, đóng gói \n" , font13));
//			p.add(new Chunk(" " +check[1],charfont));
//			p.add(new Chunk("NL ban đầu" , font11));
//			p.add(new Chunk(" " +check[2],charfont));
//			p.add(new Chunk("NL bao gói cấp 1" , font11));
//			p.add(new Chunk(" " +check[3],charfont));
//			p.add(new Chunk("Nhãn SP" , font11));
//			p.add(new Chunk(" " +check[4],charfont));
//			p.add(new Chunk("NL bao gói cấp 2" , font11));
//			p.add(new Chunk(" " +check[5],charfont));
//			p.add(new Chunk("Bổ sung SX" , font11));
//			p.add(new Chunk(" " +check[6],charfont));
//			p.add(new Chunk("Bổ sung ĐG" , font11));
//			p.add(new Chunk("\n3. Bộ hồ sơ lô \n" , font13));
//			p.add(new Chunk(" " +check[7],charfont));
//			p.add(new Chunk("HSL sản xuất kèm nhãn trong quá trình SX" , font11));
//			p.add(new Chunk("    " +check[8],charfont));
//			p.add(new Chunk("HSL sản xuất kèm nhãn trong quá trình ĐG" , font11));
//			p.add(new Chunk("\n4. Phiếu kiểm tra trước khi SX-ĐG:\n" , font13));
//			p.add(new Chunk(" " +check[9],charfont));
//			p.add(new Chunk("Trước khi lĩnh nguyên liệu" , font11));
//			p.add(new Chunk("   " +check[10],charfont));
//			p.add(new Chunk("Trước khi sản xuất" , font11));
//			p.add(new Chunk("      " +check[11],charfont));
//			p.add(new Chunk("Trước khi đóng gói thứ cấp" , font11));
//			p.add(new Chunk("\n5. Phiếu kiểm xoát quá trình SX-ĐG\n" , font13));
//			p.add(new Chunk(" " +check[12],charfont));
//			p.add(new Chunk("Đóng chai/Lọ T.nước" , font11));
//			p.add(new Chunk("                     " +check[13],charfont));
//			p.add(new Chunk("" + this.phaChe , font11));
//			p.add(new Chunk("\n6. Hồ sơ lấy mẫu BTP........................................................................................................... " , font13));
//			p.add(new Chunk(check[14],charfont));
//			p.add(new Chunk("\n7. Biên bản xử lý dư phẩm/ phế phẩm  " , font13));
//			p.add(new Chunk("   " +check[15],charfont));
//			p.add(new Chunk("Quá trình SX" , font11));
//			p.add(new Chunk("   " +check[16],charfont));
//			p.add(new Chunk("Quá trình đóng gói" , font11));
//			p.add(new Chunk("\n8. Phiếu thanh toán vật tư " , font13));
//			p.add(new Chunk("         " +check[17],charfont));
//			p.add(new Chunk("Quá trình SX" , font11));
//			p.add(new Chunk("   " +check[18],charfont));
//			p.add(new Chunk("Quá trình đóng gói" , font11));
//			p.setAlignment(Element.ALIGN_LEFT);
//			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cell.setVerticalAlignment(Element.ALIGN_CENTER);
//			cell.addElement(p);
//			cell.setBorderWidth(1);
//			table4.addCell(cell);
//			cell = new PdfPCell();
			
			
			
			
		//	------------------------- HẾT BẢNG II
			
			
			
			
			
			// PHẦN III..............
			
			PdfPTable table6 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS2.length);	
			table6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table6.setWidths(TABLE_CONTENT_FIELDS_WIDTHS2);
			table6.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(6);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("I. TIẾN ĐỘ " , font14bold));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(6);
			table6.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày bắt đầu \n SX" , font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày hoàn thành" , font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày giao sản\nphẩm theo lệnh" , font11));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số ngày chậm so \nvới lệnh" , font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ghi chú \n(Ngoài 5 ngày phải giải thích)" , font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table6.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(this.ngayBatDauSX,font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(this.ngayHoanThanh,font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(this.ngayGiaoSPTheoLenh,font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.songaychamsovoilenh,font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(this.ghiChu,font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			// END III
			
			// IV
			PdfPTable table7 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS3.length);	
			table7.setHorizontalAlignment(Element.ALIGN_CENTER);
			table7.setWidths(TABLE_CONTENT_FIELDS_WIDTHS3);
			table7.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(8);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("II. HIỆU SUẤT",font14bold));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(7);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số lượng sản phẩm (lọ)",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(3);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Hiệu suất (%)",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(3);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ghi chú",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(3);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Theo lệnh",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Nhập kho",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Lấy mẫu",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Công đoạn \n SX",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Công đoạn ĐG",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Toàn chặng\n",font10));
			p.add(new Chunk("(YC:100 ± 2%)",font10));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setBorderWidthRight(1);
			cell.setBorderWidthBottom(0);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+ this.soLuongTheoLenh,font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.soLuongNhapKho,font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+ this.soLuongLayMau,font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" +this.congDoanSx,font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" +this.congDoanDG,font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" +this.toanChang,font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table7.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("",font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setBorderWidthBottom(1);
			cell.setBorderWidthRight(1);
			table7.addCell(cell);
			
			
			
			
			PdfPTable table5 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS.length);	
			table5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table5.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
			table5.setWidthPercentage(100.0f);
			
			// PHẦN III..............
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("",font10));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(7);
			table5.addCell(cell);
			
		
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table5.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("III. PHIẾU KIỂM NGHIỆM " , font14bold));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(7);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table5.addCell(cell);
			
			

			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("TT" , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Nội dung" , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số PKN" , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày" , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			cell = new PdfPCell();
			p = new Paragraph();
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Kết quả" , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ghi chú" , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			for(int i=0; i<soTT.length ; i++)
			{
				cell = new PdfPCell();
				p = new Paragraph();
				p.add("");
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorder(0);
				table5.addCell(cell);
				
				

				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(""+ soTT[i] , font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorderWidth(1);
				table5.addCell(cell);
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(""+ noiDung[i] , font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorderWidth(1);
				table5.addCell(cell);
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(""+ soPKN[i] , font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorderWidth(1);
				table5.addCell(cell);
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(""+ ngayKiemNghiem[i] , font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorderWidth(1);
				table5.addCell(cell);
				cell = new PdfPCell();
				p = new Paragraph();
				
				String ketquaKN="";
				String ketquaKNKDat="";
				if(ketQua[1]!=null &&  ketQua[i].equals("1"))
				{
					ketquaKN=String.valueOf(checked);
					ketquaKNKDat=String.valueOf(unchecked);
				}
				else
				{
					ketquaKN=String.valueOf(unchecked);
					ketquaKNKDat=String.valueOf(checked);
				}
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(""+ ketquaKN , charfont));
				p.add(new Chunk("Đạt" , font12));
				p.add(new Chunk("  "+ ketquaKNKDat , charfont));
				p.add(new Chunk("Không đạt" , font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorderWidth(1);
				table5.addCell(cell);
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(""+ ghiChuKiemNghiem[i] , font12));
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.addElement(p);
				cell.setBorderWidth(1);
				table5.addCell(cell);
			}
			
	// 		V
			PdfPTable table8 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS4.length);	
			table8.setHorizontalAlignment(Element.ALIGN_CENTER);
			table8.setWidths(TABLE_CONTENT_FIELDS_WIDTHS4);
			table8.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(3);
			table8.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table8.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("ĐÁNH GIÁ CHUNG",font14bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(2);
			table8.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table8.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(check[0],charfont));
			p.add(new Chunk("  Lô SX đạt yêu cầu xuất xưởng",font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table8.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+check[1],charfont));
			p.add(new Chunk("Ý kiến khác",font12));
			if(this.yKienKhac.length()!=0)
			{
				p.add(new Chunk("...............................................................\n....................................................................................",font12));
			}
			else
			{
				p.add(new Chunk(""+this.yKienKhac +"\n......................................................................................",font12));
			}
			
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table8.addCell(cell);
			
			
			//////////////// KIỂM SOÁT.
			
			PdfPTable table10 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS5.length);	
			table10.setHorizontalAlignment(Element.ALIGN_CENTER);
			table10.setWidths(TABLE_CONTENT_FIELDS_WIDTHS5);
			table10.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(7);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("IV. KIỂM SOÁT " , font14bold));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(6);
			table10.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Sự thay đổi",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(2);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Sự cố kỹ thuật",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(2);
			table10.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Sản phẩm không phù hợp loại 3",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(2);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table10.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Có", font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+check[0],charfont));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Có", font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			
			String [] khongcheck = new String[3];
			for(int i=0;i<check.length;i++)
			{
				if(check[i].equals(String.valueOf(checked)))
				{
					khongcheck[i]=String.valueOf(unchecked);
				}else
				{
					khongcheck[i]=String.valueOf(checked);
				}
			}
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+check[1],charfont));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Có", font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+check[2],charfont));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table10.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Không", font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(khongcheck[0],charfont));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Không", font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(khongcheck[1],charfont));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Không", font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(khongcheck[2],charfont));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table10.addCell(cell);
			
			
			
			
			
			
						
			
			PdfPTable table9 = new PdfPTable(TABLE_FOOTER_WIDTHS.length);	
			table9.setHorizontalAlignment(Element.ALIGN_CENTER);
			table9.setWidths(TABLE_FOOTER_WIDTHS);
			table9.setWidthPercentage(100.0f);
			
			
	
			
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(4);
			table9.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table9.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày....Tháng....Năm....",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table9.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày....Tháng....Năm....",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table9.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày....Tháng....Năm....",font12));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table9.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table9.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("PP. ĐẢM BẢO CHẤT LƯỢNG",font12bold));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("DS.Lại Thúy Nga",font11bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table9.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("GIÁM ĐỐC SẢN XUẤT",font12bold));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("Ths.Nguyễn Thị Hậu",font11bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table9.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("CÁN BỘ ĐBCL",font12bold));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("\n",font12));
			p.add(new Chunk("DS.Vũ Văn Thuấn",font11bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table9.addCell(cell);
			
			document.add(hinhanh);
			document.add(table2);
			document.add(table3);
//			document.add(table4);
		
			document.add(table6);
			document.add(table7);
			document.add(table5);
			document.add(table8);
			document.add(table10);
			document.add(table9);
			
			document.close(); 
	        
		}
		catch(Exception e)
		{
			System.out.println("Exception In Phieu Chi: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private String getDate(String date)
	{
		String arr[] = date.split("-");
		String nam = arr[0];
		String thang = arr[1];
		String ngay = arr[2];
		
		return ngay + "/" + thang + "/" + nam;
	}
	
	
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void LayDuLieuHoSoLo()
	{
		dbutils db = new dbutils();
		String query="Select SOHOSO,DMVT,NSX,HD FROM ERP_LENHSANXUAT_HOSOLO where lenhsanxuat_fk="+ this.id ;
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.ngaySX = rs.getString("NSX");
			this.HD = rs.getString("HD");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.shutDown();
		}
	}
	private void LayDuLieuTienDo()
	{
		dbutils db = new dbutils();
		String query="Select LENHSANXUAT_FK,NGAYBATDAUSX,NGAYHOANTHANH,SONGAYCHAMSOVOILENH,GHICHUTIENDO FROM ERP_LENHSANXUAT_TONGKETLO_TIENDO where lenhsanxuat_fk="+ this.id ;
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.ngayBatDauSX= rs.getString("NGAYBATDAUSX");
			this.ngayHoanThanh= rs.getString("NGAYHOANTHANH");
			this.ghiChu= rs.getString("GHICHUTIENDO");
			this.songaychamsovoilenh= rs.getString("SONGAYCHAMSOVOILENH");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.shutDown();
		}
	}
	private void LayDuLieuTongKetLo()
	{
		dbutils db = new dbutils();
		String query="select SOLUONGNHAPKHO,SOLUONGLAYMAU,HIEUSUAT_SX ,HIEUSUAT_DG , TOANCHANG ,YKIENKHAC ,  NDHL , PHACHE , CHECKCHON  FROM LENHSANXUAT_TONGKETLO \n"+
			" where LENHSANXUAT_FK ="+ this.id ;
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.congDoanSx = rs.getString("HIEUSUAT_SX");
			this.congDoanDG = rs.getString("HIEUSUAT_DG");
			this.yKienKhac  = rs.getString("YKIENKHAC");
			this.toanChang = rs.getString("TOANCHANG");
			this.ndHL = rs.getString("NDHL");
			this.phaChe = rs.getString("PHACHE");
			this.chon = rs.getString("CHECKCHON");
			this.soLuongNhapKho=rs.getInt("SOLUONGNHAPKHO");
			this.soLuongLayMau=rs.getInt("SOLUONGLAYMAU");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.shutDown();
		}
	}
	private void LayDuLieuSanPham()
	{
		dbutils db = new dbutils();
		String query="SELECT CASE isnull(SP.DANGBAOCHE,'') when 0 then N'Tây y' when 1 then N'Thuốc nước' when 2 then N'Thuốc mỡ' when 3 then N'Thuốc viên' else N'Nang mềm' END  AS LOAITHUOC ,dm.TENBOM,LSX.NGAYBATDAU,LSX.NGAYDUKIENHT,lsxsp.SOLUONG,SP.MA,SP.TEN ,DV.DONVI," +
		" isnull((select dvdl.DONVI+' x ' +cast(qc.SOLUONG1 as nvarchar(10)) +' ' +dvdl.DONVI +'/'+dvdl2.DONVI  +' x ' + cast(qc.SOLUONG2 as nvarchar(10)) +' ' +dvdl2.DONVI   \n"+
		" from erp_sanpham sp inner join quycach qc  on qc.SANPHAM_FK=sp.PK_SEQ  \n"+
		"  inner join DONVIDOLUONG dvdl on qc.DVDL1_FK=dvdl.PK_SEQ  \n"+
		"  inner join DONVIDOLUONG dvdl2 on qc.DVDL2_FK=dvdl2.PK_SEQ \n"+
		"  where sp.pk_Seq=lsxsp.SANPHAM_FK \n"+
		"  For XML PATH ('')),'') as quycach \n"+ 
		" FROM ERP_LENHSANXUAT_SANPHAM lsxsp inner join ERP_DANHMUCVATTU dm on dm.PK_SEQ=lsxsp.DanhMucVT_FK \n"+
		" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ=DM.DVDL_FK \n"+
		" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=lsxsp.LENHSANXUAT_FK \n"+
		" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=lsxsp.SANPHAM_FK \n"+
		" where lenhsanxuat_fk="+ this.id ;
		System.out.println("SAN PHAM :" +query);
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.dangBaoChe = rs.getString("LOAITHUOC");
			this.QuyCach = rs.getString("quycach");
			this.tenSp = rs.getString("TEN");
			this.soLuongTheoLenh= rs.getString("SOLUONG");
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.shutDown();
		}
	}
	private void LayDuLieuNhapKho()
	{
		this.soLo="";
		this.ngayGiaoSPTheoLenh="";
		dbutils db = new dbutils();
		String query="SELECT NK.NGAYNHAPKHO,NKSP.SOLO FROM ERP_NHAPKHO_SANPHAM \n" +
			" NKSP INNER JOIN ERP_NHAPKHO NK ON NK.PK_SEQ=NKSP.SONHAPKHO_FK \n" +
			" inner join DONVIDOLUONG dv on dv.PK_SEQ=NKSP.DVDL_FK \n" +
			" WHERE NK.SOLENHSANXUAT="+ this.id +" \n" +
			" GROUP BY NKSP.SOLO,dv.DONVI,NK.NGAYNHAPKHO,NKSP.SOLUONGLAYMAU ";
		
		ResultSet rs = db.get(query);
		try
		{
		while(rs.next())
		{
			if(soLo.length()==0)
			{
			this.soLo = rs.getString("SOLO");
			this.ngayGiaoSPTheoLenh = rs.getString("NGAYNHAPKHO");
			}else
			{
				this.soLo = this.soLo + "," + rs.getString("SOLO");
				this.ngayGiaoSPTheoLenh = this.ngayGiaoSPTheoLenh  + "," + rs.getString("NGAYNHAPKHO");
			}
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.shutDown();
		}
	}
	private void LayDuLieuPhieuKiemNghiem()
	{
		dbutils db = new dbutils();
		String query="select SOTT ,NOIDUNG , SOPKN ,NGAY ,KETQUA  ,GHICHU  from LENHSANXUAT_PHIEUKIEMNGHIEM WHERE  LENHSANXUAT_FK= "+this.id +" ORDER BY SOTT asc";
		int i=0;
		ResultSet rs = db.get(query);
		try
		{
		while(rs.next())
		{
			
			soTT[i] =rs.getString("SOTT");
			noiDung[i]= rs.getString("NOIDUNG");
			soPKN[i]= rs.getString("SOPKN");
			ngayKiemNghiem[i]= rs.getString("NGAY");
			ketQua[i]= rs.getString("KETQUA");
			ghiChuKiemNghiem[i] = rs.getString("GHICHU");
			i++;
		}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.shutDown();
		}
	}
	private void LayDuLieuCheck(char checked,char unchecked)
	{
		String mangcheck [] = { "1. ","2. ","3. "};
		for(int i=0;i<mangcheck.length ;i++)
		{
			
			check[i]=String.valueOf(unchecked);
			System.out.println("CHON "+ this.chon );
			List<String> items = Arrays.asList(this.chon.split("\\s*,\\s*"));
			for(int j =0 ; j<items.size();j++)
			{
				if(items.get(j).length()>0)
				if(mangcheck[i].indexOf(items.get(j))==0)
				{
					check[i]=String.valueOf(checked);
					break;
				}
			}
		}
		
	}
	
	
	
	
	
}
