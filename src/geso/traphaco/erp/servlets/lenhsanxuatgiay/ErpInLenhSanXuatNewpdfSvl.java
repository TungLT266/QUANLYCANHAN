package geso.traphaco.erp.servlets.lenhsanxuatgiay;


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


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpInLenhSanXuatNewpdfSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String id;
	private String soHoSo="";
	private String DMVT="";
	private String dangBaoChe="";
	private String tenSp="";
	private String quatrinh1="";
	private String quatrinh2="";
	private String dang1="";
	private String dang2="";
	private String dang3="";
	private String SKN1="";
	private String SKN2="";
	private String SKN3="";
	private String QuyCach="";
	private String soLoSx="";
	private String ngaySX="";
	private String HD="";
	private String soLuongtheoLenh="";
	private String donViTheoLenh="";
	private float soLuongNhapkho=0;
	private String donViNhapKho="";
	private String ngayBatDauSx="";
	private String ngayHoanThanh="";
	private String chon="";
	private float hieuSuat=0;
	private String[] mangcheck=new String[]{"1 Lệnh sản xuất","2 Phiếu giao nhận vật tư SX","3 Hồ sơ sản xuất","4 Phiếu giao nhận vật tư đóng gói","5 Hồ sơ lô đóng gói",
			   "6 Nhãn trong quá trình sản xuất","7 Phiếu kiểm tra trước khi SX_ĐG"," 7.1 Trước khi lĩnh nguyên liệu"," 7.2 Trước khi pha chế + ĐG sơ cấp", 
			   " 7.3 Trước khi đóng gói thức cấp", "11 Biên bản xửa lý dư phẩm,phế phẩm","12 Phiếu thanh toán vật tư"};
	private String soKiemNghiem="";
	private String ngayKiemNghiem="";
    public ErpInLenhSanXuatNewpdfSvl() {
//        new ErpInLenhSanXuatNewpdfSvl();
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
			float[] TABLE_HEADER_WIDTHS = new float[] {12f * CONVERT,7f * CONVERT };
			float[] TABLE_CONTENT_WIDTHS = new float[] {2f * CONVERT, 4f * CONVERT, 11f * CONVERT,3.4f * CONVERT  };
			float[] TABLE_CONTENT2_WIDTHS = new float[] {2f * CONVERT, 6.9f * CONVERT, 3.1f * CONVERT,4.5f * CONVERT ,4f * CONVERT};
			float[] TABLE_CONTENT_FIELDS_WIDTHS = new float[] {1.9f * CONVERT, 1.5f * CONVERT, 10.6f * CONVERT,0.6f * CONVERT,6.9f * CONVERT};
			float[] TABLE_FOOTER_WIDTHS = new float[] {11.25f * CONVERT, 11.25f * CONVERT};
			PdfPCell cell, cell2, cell3;

			
			int BORDER_WIDTH = 0;
			
			LayDuLieuHoSoLo();
			LayDuLieuKiemNghiem();
			LayDuLieuNhapKho();
			LayDuLieuSanPham();
			LayDuLieuKiemNghiemCT();

			PdfPTable table2 = new PdfPTable(TABLE_HEADER_WIDTHS.length);	
			table2.setHorizontalAlignment(Element.ALIGN_CENTER);
			table2.setWidths(TABLE_HEADER_WIDTHS);
			table2.setWidthPercentage(100.0f);
			cell = new PdfPCell();
			Paragraph p = new Paragraph();
			p.add("");
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table2.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số hồ sơ:    ", font12boldblue)); 
			p.add(new Chunk("		" + this.soHoSo + "\n", font12bold)); 
			p.add(new Chunk("ĐMVT:        ",font12boldblue));
			p.add(new Chunk("		" + this.DMVT +"\n", font12bold)); 
	
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table2.addCell(cell);
			
			PdfPTable table3 = new PdfPTable(TABLE_CONTENT_WIDTHS.length);	
			table3.setHorizontalAlignment(Element.ALIGN_CENTER);
			table3.setWidths(TABLE_CONTENT_WIDTHS);
			table3.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			table3.addCell(cell);
			
			
			Image hinhanh=Image.getInstance( getServletContext().getInitParameter("pathPrint")+"/logo.gif");
			hinhanh.scalePercent(10);
			hinhanh.setAbsolutePosition(3.0f * CONVERT, document.getPageSize().getHeight() - 2.7f * CONVERT);
		
			cell = new PdfPCell();
			p = new Paragraph();
			p.add("");
//			cell.addElement(hinhanh);
			cell.setBorder(0);
			table3.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("\n", font11bold)); 
			p.add(new Chunk("HỒ SƠ LÔ SẢN PHẨM \n", font22boldblue)); 
			p.add(new Chunk("(Kiêm lệnh xuất xưởng)\n ", font13Italic)); 
			p.add(new Chunk("Dạng bào chế ",font13boldblue));
			p.add(new Chunk("" + this.dangBaoChe +" \n", font13bold)); 
			
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_TOP);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(2);
			cell.setBorder(0);
			cell.addElement(p);
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
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Tên sản phẩm: ", font12boldblue)); 
			p.add(new Chunk("" + this.tenSp + "\n", font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("HL: ", font12boldblue)); 
			p.add(new Chunk("\n", font12bold)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(""); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Quy cách:      ", font12boldblue)); 
			p.add(new Chunk("" + this.QuyCach + "\n", font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(4);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(""); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số lô SX:   ", font12boldblue)); 
			p.add(new Chunk("" + this.soLoSx + "     	  " , font12bold)); 
			p.add(new Chunk("NSX:  ", font12boldblue)); 
			p.add(new Chunk("" + this.ngaySX  + "      	   ", font12bold)); 
			p.add(new Chunk(" HD:  ", font12boldblue)); 
			p.add(new Chunk("" + this.HD , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(4);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(""); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số lượng theo lệnh:   ", font12boldblue)); 
			p.add(new Chunk("" + this.soLuongtheoLenh +"\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Đơn vị:   ", font12boldblue)); 
			p.add(new Chunk("" + this.donViTheoLenh +"\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày bắt đầu SX:   ", font12boldblue)); 
			p.add(new Chunk("" + this.ngayBatDauSx +"\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(""); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Số lượng nhập kho:   ", font12boldblue)); 
			p.add(new Chunk("" + this.soLuongNhapkho +"\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Đơn vị:   ", font12boldblue)); 
			p.add(new Chunk("" + this.donViNhapKho +"\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ngày hoàn thành :   ", font12boldblue)); 
			p.add(new Chunk("" + this.ngayHoanThanh +"\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table4.addCell(cell);
			
			
			this.hieuSuat= this.soLuongNhapkho / Float.parseFloat(this.soLuongtheoLenh) *100 ;
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(""); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.addElement(p);
			table4.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Hiệu suất :   ", font12boldblue)); 
			p.add(new Chunk("" + this.hieuSuat + "%"+ "\n" , font12bold)); 
			p.setAlignment(Element.ALIGN_LEFT);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(0);
			cell.setColspan(4);
			cell.addElement(p);
			table4.addCell(cell);
			
			PdfPTable table5 = new PdfPTable(TABLE_CONTENT_FIELDS_WIDTHS.length);	
			table5.setHorizontalAlignment(Element.ALIGN_CENTER);
			table5.setWidths(TABLE_CONTENT_FIELDS_WIDTHS);
			table5.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("HỒ SƠ NÀY GỒM   ", font14boldblue)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(5);
			cell.addElement(p);
			table5.addCell(cell);
			
			for (int i=0;i< this.mangcheck.length ;i++)
			{
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(""); 
				p.setAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.addElement(p);
				table5.addCell(cell);
				
				
				cell = new PdfPCell();
				p = new Paragraph();
				p.add(new Chunk(this.mangcheck[i], font12blue)); 
				p.setAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.setColspan(2);
				cell.addElement(p);
				table5.addCell(cell);
				
				String check=String.valueOf(unchecked);
				List<String> items = Arrays.asList(this.chon.split("\\s*,\\s*"));
				for(int j =0 ; j<items.size();j++)
				{
					System.out.println(items.get(j)+ " ");
					System.out.println(mangcheck[i]);
					System.out.println(items.get(j));
					if(items.get(j)!="")
					if(mangcheck[i].indexOf(items.get(j))==0)
					{
						check=String.valueOf(checked);
						break;
					}
				}
				// CÁI THỨ 7
				if(i!=6)
				{
				cell = new PdfPCell();
				p = new Paragraph(check,charfont);
				p.setAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(0);
				cell.setColspan(2);
				cell.addElement(p);
				table5.addCell(cell);
				}else
				{
					cell = new PdfPCell();
					p = new Paragraph("");
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(2);
					cell.addElement(p);
					table5.addCell(cell);
				}
				if(i==9)
				{
					// 8.
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("8 Phiếu kiểm soát quá trình sản xuất đóng gói", font12blue)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(4);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Quá trình :", font12blue)); 
					p.add(new Chunk("" + this.quatrinh1, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(4);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Quá trình :", font12blue)); 
					p.add(new Chunk("" + this.quatrinh2, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(4);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					// 9.
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("9 Phiếu kiểm nghiệm bán thành phẩm ", font12blue)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(4);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Dạng : ", font12blue)); 
					p.add(new Chunk(""+ this.dang1, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(3);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("SKN : ", font12blue));
					p.add(new Chunk("" + this.SKN1, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Dạng : ", font12blue)); 
					p.add(new Chunk("" + this.dang2, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(3);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("SKN : ", font12blue)); 
					p.add(new Chunk("" + this.SKN2, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Dạng : ", font12blue)); 
					p.add(new Chunk("" + this.dang3, font12));
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(3);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("SKN : ", font12blue)); 
					p.add(new Chunk("" + this.SKN3, font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					//// 10.
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("10 Phiếu kiểm nghiệm thành phẩm ", font12blue)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(4);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(""); 
					p.setAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.addElement(p);
					table5.addCell(cell);
					
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Số kiểm nghiệm  : ", font12blue)); 
					p.add(new Chunk("" + this.soKiemNghiem  + "\n", font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(2);
					cell.addElement(p);
					table5.addCell(cell);
					
					cell = new PdfPCell();
					p = new Paragraph();
					p.add(new Chunk("  Ngày  : ", font12blue)); 
					p.add(new Chunk("" + this.ngayKiemNghiem  + "\n", font12)); 
					p.setAlignment(Element.ALIGN_LEFT);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setBorder(0);
					cell.setColspan(2);
					cell.addElement(p);
					table5.addCell(cell);
			
				}
			}

			PdfPTable table6 = new PdfPTable(TABLE_FOOTER_WIDTHS.length);	
			table6.setHorizontalAlignment(Element.ALIGN_CENTER);
			table6.setWidths(TABLE_FOOTER_WIDTHS);
			table6.setWidthPercentage(100.0f);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("QUYẾT ĐỊNH \n   ", font14boldblue)); 
			p.add(new Chunk("SẢN PHẨM ĐƯỢC PHÉP XUẤT XƯỞNG  ", font13boldblue)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.setColspan(2);
			cell.addElement(p);
			table6.addCell(cell);
			
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("PHÓ TỔNG GIÁM ĐỐC CÔNG TY \n    ", font13boldblue)); 
			p.add(new Chunk("NGÀY : "+ getDateTime() +"    ", font13boldblue)); 
			p.add("\n"); 
			p.add("\n"); 
			p.add("\n"); 
			p.add("\n"); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.addElement(p);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("TRƯỞNG PHÒNG DBCL \n    ", font13boldblue)); 
			p.add(new Chunk("NGÀY : "+ getDateTime() +"    ", font13boldblue)); 
			p.add("\n"); 
			p.add("\n"); 
			p.add("\n"); 
			p.add("\n"); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.addElement(p);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("Ths. Nguyễn Huy Văn    ", font13boldblue)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.addElement(p);
			table6.addCell(cell);
			
			cell = new PdfPCell();
			p = new Paragraph();
			p.add(new Chunk("DS. Nguyễn Thị Vân Anh    ", font13boldblue)); 
			p.setAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(0);
			cell.addElement(p);
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
	private void LayDuLieuHoSoLo()
	{
		dbutils db = new dbutils();
		String query="Select SOHOSO,DMVT,NSX,HD,CHON FROM ERP_LENHSANXUAT_HOSOLO where lenhsanxuat_fk="+ this.id ;
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.soHoSo = rs.getString("SOHOSO");
			System.out.println(rs.getString("SOHOSO"));
			this.DMVT = rs.getString("DMVT");
			this.ngaySX = rs.getString("NSX");
			this.HD = rs.getString("HD");
			this.chon = rs.getString("CHON");
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
		this.soLuongtheoLenh="0";
		dbutils db = new dbutils();
		String query="SELECT isnull(D.TEN,'') AS LOAITHUOC ,dm.TENBOM,LSX.NGAYBATDAU_NEW,LSX.NGAYDUKIENHT,lsxsp.SOLUONG,SP.MA,SP.TEN ,DV.DONVI," +
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
			this.ngayBatDauSx = rs.getString("NGAYBATDAU_NEW");
			this.ngayHoanThanh = rs.getString("NGAYDUKIENHT");
			this.soLuongtheoLenh = rs.getString("SOLUONG");
			this.tenSp = rs.getString("TEN");
			this.donViTheoLenh = rs.getString("DONVI");
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
		this.soLoSx="";
		this.soLuongNhapkho=0;
		dbutils db = new dbutils();
		String query="SELECT SUM(NKSP.SOLUONGNHAP) as SoLuongNhap,NKSP.SOLO,dv.DONVI FROM ERP_NHAPKHO_SANPHAM \n" +
			" NKSP INNER JOIN ERP_NHAPKHO NK ON NK.PK_SEQ=NKSP.SONHAPKHO_FK \n" +
			" inner join DONVIDOLUONG dv on dv.PK_SEQ=NKSP.DVDL_FK \n" +
			" WHERE NK.SOLENHSANXUAT="+ this.id +" \n" +
			" GROUP BY NKSP.SOLO,dv.DONVI ";
		
		ResultSet rs = db.get(query);
		try
		{
		while(rs.next())
		{
			if(soLoSx.length()==0)
			{
			this.soLoSx = rs.getString("SOLO");
			}else
			{
				this.soLoSx = this.soLoSx + "," + rs.getString("SOLO");
			}
			this.soLuongNhapkho =(float) (this.soLuongNhapkho+ rs.getDouble("SoLuongNhap")) ;
			this.donViNhapKho = rs.getString("DONVI");
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
	private void LayDuLieuKiemNghiem()
	{
		dbutils db = new dbutils();
		String query=" select isnull(KYHIEUMAU,'') as MAKIEMDINH,ngaykiem  from ERP_YeuCauKiemDinh  WHERE LenhSanXuat_FK="+this.id ;

		this.soKiemNghiem="";
		this.ngayKiemNghiem="";
		ResultSet rs = db.get(query);
		try
		{
		while(rs.next())
		{
			if(this.soKiemNghiem.length()==0)
			{
			this.soKiemNghiem = rs.getString("MAKIEMDINH");
			this.ngayKiemNghiem = rs.getString("ngaykiem");
			}
			else
			{
				this.soKiemNghiem =this.soKiemNghiem + ','+ rs.getString("MAKIEMDINH");
				this.ngayKiemNghiem =this.ngayKiemNghiem + ','+ rs.getString("ngaykiem");
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
	private void LayDuLieuKiemNghiemCT()
	{
		dbutils db = new dbutils();
		String query=" select LENHSANXUAT_FK ,QUATRINH1 , QUATRINH2   ,DANG1 , DANG2  , DANG3,SKN1,SKN2,SKN3 from ERP_LENHSANXUAT_KIEMNGHIEM where lenhsanxuat_fk="+this.id;
		ResultSet rs = db.get(query);
		try
		{
		if(rs.next())
		{
			this.quatrinh1 = rs.getString("QUATRINH1");
			this.quatrinh2 = rs.getString("QUATRINH2");
			this.dang1 = rs.getString("DANG1");
			this.dang2 = rs.getString("DANG2");
			this.dang3 = rs.getString("DANG3");
			this.SKN1 = rs.getString("SKN1");
			this.SKN2 = rs.getString("SKN2");
			this.SKN3 = rs.getString("SKN3");
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
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	
}
