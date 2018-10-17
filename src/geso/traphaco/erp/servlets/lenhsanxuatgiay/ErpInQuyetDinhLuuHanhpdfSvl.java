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

public class ErpInQuyetDinhLuuHanhpdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String tenSp="";
	private String dangBaoChe="";
	private String QuyCach="";
	private String id="";
	private String soLo="";
	private String dmvt="";
	private String ngaySX="";
	private String HD="";
	private String quyetdinhluuhanh="";
	private String QTSX="";
	private String TCKT="";
	private String soHoSo="";
	private String noiSanXuat="";
	private String ngayBatDauSX="";
	private String ngayHoanThanh="";
	private String lenhSanXuatSo="";
	private String [] soTT = new String [5];
	private String [] noiDung= new String [5];
	private String [] soPKN= new String [5];
	private String [] ngayKiemNghiem= new String [5];
	private String [] ketQua= new String [5];
	private String [] ghiChuKiemNghiem= new String [5];
	private String [] check = new String[5] ;
	
    public ErpInQuyetDinhLuuHanhpdfSvl() {
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
			Font font17bold = new Font(bf, 17, Font.BOLD);
			float CONVERT = 28.346457f; // = 1cm
			float[] TABLE_WIDTHS = new float[] {18f * CONVERT };
			float[] TABLE_HEADER_WIDTHS = new float[] {9.25f * CONVERT ,11.25f * CONVERT };
			float[] TABLE_CONTENT_WIDTHS = new float[] {1.5f * CONVERT, 3.0f * CONVERT,8.0f * CONVERT,2f * CONVERT ,3f * CONVERT};
			float[] TABLE_CONTENT2_WIDTHS = new float[] {1.5f * CONVERT, 3.0f * CONVERT,5.0f * CONVERT,4f * CONVERT ,4f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS = new float[] {1.5f * CONVERT, 4.5f * CONVERT,2.6f * CONVERT,1.5f * CONVERT ,2.5f * CONVERT,2f * CONVERT,3f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS2 = new float[] {1.5f * CONVERT, 2.62f * CONVERT, 2.62f * CONVERT,2.66f * CONVERT,3.3f * CONVERT,5.0f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS3 = new float[] {1.5f * CONVERT, 2.62f * CONVERT, 2.62f * CONVERT,2.66f * CONVERT,2.2f * CONVERT,2.2f * CONVERT,2.0f * CONVERT,2f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS5 = new float[] {1.5f * CONVERT, 2.62f * CONVERT, 2.62f * CONVERT,3.5f * CONVERT,1.5f * CONVERT,3.5f * CONVERT,3.0f*CONVERT};

			float[] TABLE_FOOTER_WIDTHS = new float[] {1.5f * CONVERT ,8.25f * CONVERT,8.25f * CONVERT };
			PdfPCell cell, cell2, cell3;

			
			int BORDER_WIDTH = 0;

			
			LayDuLieuSanPham();
			LayDuLieuHoSoLo();
			LayDuLieuTienDo();
			LayDuLieuNhapKho();
			LayDuLieuPhieuKiemNghiem();
			
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
			p.add(new Chunk("\n", font13)); 
			p.setAlignment(Element.ALIGN_RIGHT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			cell.setColspan(2);
			table2.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("QUYẾT ĐỊNH LƯU HÀNH SẢN PHẨM \n", font17bold));
			cell.setBorder(0);
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table2.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("\n",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(2);
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
			p.add(new Chunk("I. THÔNG TIN LÔ SẢN XUẤT", font15bold)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.addElement(p);
			cell.setColspan(5);
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
			p.add(new Chunk("Tên sản phẩm : ", font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.addElement(p);
			table3.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.tenSp, font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.addElement(p);
			table3.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số lô : " , font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.addElement(p);
			table3.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
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
			p.add(new Chunk("Dạng bào chế : ", font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.dangBaoChe , font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			

			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("NSX : ", font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+ this.ngaySX , font13));
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
			p.add(new Chunk("Quy cách : ", font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.QuyCach , font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("HD : " , font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table3.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" +this.HD, font13));
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
			p.add(new Chunk("\n", font13)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorderWidth(1);
			cell.addElement(p);
			cell.setColspan(5);
			table3.addCell(cell);
			
			
			PdfPTable table4 = new PdfPTable(TABLE_CONTENT2_WIDTHS.length);	
			table4.setHorizontalAlignment(Element.ALIGN_CENTER);
			table4.setWidths(TABLE_CONTENT2_WIDTHS);
			table4.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("QTSX số:",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.QTSX,font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("ĐMVT số:",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.dmvt,font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("TCKT số:",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.TCKT,font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Hồ sơ lô gốc số:",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.soHoSo,font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Nơi sản xuất:",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.noiSanXuat,font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Lệnh sản xuất số:",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("" + this.lenhSanXuatSo,font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table4.addCell(cell);
			
			// PHẦN III..............
			
			
			PdfPTable table5 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS.length);	
			table5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table5.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
			table5.setWidthPercentage(100.0f);
			
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
			p.add(new Chunk("II. CĂN CỨ",font15bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(6);
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
			p.add(new Chunk("Phiếu kiểm nghiệm BTP số: ",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.soPKN[0],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày: ",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ngayKiemNghiem[0],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Kết quả: ",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ketQua[0],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table5.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Phiếu kiểm nghiệm BTP số: ",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.soPKN[1],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày: ",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ngayKiemNghiem[1],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Kết quả: ",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ketQua[1],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table5.addCell(cell);

			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Phiếu kiểm nghiệm TP số: ",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.soPKN[4],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày: ",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ngayKiemNghiem[4],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Kết quả: ",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ketQua[4],font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table5.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Hồ sơ lô sản xuất bắt đầu ngày:",font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ngayBatDauSX,font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setColspan(2);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Hoàn thành ngày:",font12));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table5.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk(""+this.ngayHoanThanh,font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setColspan(2);
			cell.setBorderWidth(1);
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
			p.add(new Chunk("II. QUYẾT ĐỊNH",font15bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(6);
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
			p.add(new Chunk(""+this.quyetdinhluuhanh,font15bold));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			cell.setColspan(6);
			table5.addCell(cell);
			
			
			
			
			
			PdfPTable table6 = new PdfPTable(TABLE_FOOTER_WIDTHS.length);	
			table6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table6.setWidths(TABLE_FOOTER_WIDTHS);
			table6.setWidthPercentage(100.0f);
			
			
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
			p.add(new Chunk("\n\n\n\n",font13));
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			cell.setColspan(2);
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
			p.add(new Chunk("Ngày.....tháng......năm",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày.....tháng......năm",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorder(0);
			table6.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("PHÓ TỔNG GIÁM ĐỐC",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("TP.ĐẢM BẢO CHẤT LƯỢNG",font13));
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
			p.add(new Chunk("\n\n\n" + "Nguyễn Huy Văn",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("\n\n\n" + "Nguyễn Thị Vân Anh",font13));
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.addElement(p);
			cell.setBorderWidth(1);
			table6.addCell(cell);
			
			
			document.add(hinhanh);
			document.add(table2);
			document.add(table3);
			document.add(table4);
			document.add(table5);
			document.add(table6);
			

			
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
		String query="Select SOHOSO,DMVT,NSX,HD , QTSX ,TCKT,quyetdinhluuhanh FROM ERP_LENHSANXUAT_HOSOLO where lenhsanxuat_fk="+ this.id ;
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.ngaySX = rs.getString("NSX");
			this.HD = rs.getString("HD");
			this.soHoSo = rs.getString("SOHOSO");
			this.dmvt = rs.getString("DMVT");
			this.QTSX = rs.getString("QTSX");
			this.TCKT = rs.getString("TCKT");
			this.quyetdinhluuhanh = rs.getString("quyetdinhluuhanh");

		}
		query=" select nm.tennhamay,lsx.diengiai from ERP_LENHSANXUAT_GIAY lsx inner join ERP_NHAMAY nm on nm.pk_seq=lsx.NHAMAY_FK "+
		" where lsx.PK_SEQ= "+this.id;
		rs = db.get(query);
		if(rs.next())
		{
			this.noiSanXuat=rs.getString("tennhamay");
			this.lenhSanXuatSo=rs.getString("diengiai");
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
		String query="SELECT CASE isnull (SP.DANGBAOCHE,'') when 0 then N'Tây y' when 1 then N'Thuốc nước' when 2 then N'Thuốc mỡ' when 3 then N'Thuốc viên' else N'Nang mềm' END  AS LOAITHUOC ,dm.TENBOM,LSX.NGAYBATDAU,LSX.NGAYDUKIENHT,lsxsp.SOLUONG,SP.MA,SP.TEN ,DV.DONVI," +
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
		" LEFT JOIN DANGTHUOC D ON D.PK_SEQ=SP.DANGTHUOC_FK \n"+
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
		dbutils db = new dbutils();
		String query="SELECT SUM(NKSP.SOLUONGNHAP) as SoLuongNhap,NKSP.SOLUONGLAYMAU,NK.NGAYNHAPKHO,NKSP.SOLO,dv.DONVI FROM ERP_NHAPKHO_SANPHAM \n" +
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
			
			}else
			{
				this.soLo = this.soLo + "," + rs.getString("SOLO");
		
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
			ketQua[i]= rs.getString("KETQUA").equals("1")?"Đạt chất lượng":"Không đạt";
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
	
	
	
	
	
	
	
}
