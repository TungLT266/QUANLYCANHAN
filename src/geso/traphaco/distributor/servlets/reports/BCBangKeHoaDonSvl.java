package geso.traphaco.distributor.servlets.reports;


import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.reports.IBCBangKeHoaDon;
import geso.traphaco.distributor.beans.reports.IBCHoaDonChuaThanhToan;
import geso.traphaco.distributor.beans.reports.imp.BCBangKeHoaDon;
import geso.traphaco.distributor.beans.reports.imp.BCHoaDonChuaThanhToan;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import jxl.write.Number;
import jxl.write.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspose.cells.Cell;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class BCBangKeHoaDonSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    public BCBangKeHoaDonSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		 
		IBCBangKeHoaDon bccn = new BCBangKeHoaDon();
		
		Utility util=new Utility();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		
    	bccn.setUserId(userId);
    	bccn.createRs();
    	
    	session.setAttribute("bccn",bccn);
    	
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BCBangKeHoaDon.jsp";
		response.sendRedirect(nextJSP);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		Utility util=new Utility();
		
		System.out.println("vào đây");
		IBCBangKeHoaDon bccn = new BCBangKeHoaDon();
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));  
        bccn.setUserId(userId);
        
        String userTen = (String) session.getAttribute("userTen");
        bccn.setUserName(userTen);
		
        String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));  
        bccn.setTuNgay(tuNgay);
        
        String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
        bccn.setDenNgay(denNgay);
        
    	String _scheme_nvbh = "";
		String _scheme_nvgn = "";
		String _scheme_kh = "";
		String _scheme_dt = "";
        
        String[] nvbhIds = request.getParameterValues("nvbhId");
		if (nvbhIds != null)
		{
		
			for(int i = 0; i < nvbhIds.length; i++)
			{
				_scheme_nvbh += nvbhIds[i] + ",";
			}
			
			if(_scheme_nvbh.trim().length() > 0)
			{
				_scheme_nvbh = _scheme_nvbh.substring(0, _scheme_nvbh.length() - 1);
			}
		}
		
        String[] nvgnIds = request.getParameterValues("nvgnId");
		if (nvgnIds != null)
		{

			for(int i = 0; i < nvgnIds.length; i++)
			{
				_scheme_nvgn += nvgnIds[i] + ",";
			}
			
			if(_scheme_nvgn.trim().length() > 0)
			{
				_scheme_nvgn = _scheme_nvgn.substring(0, _scheme_nvgn.length() - 1);
			}
		}
		
		  String[] khIds = request.getParameterValues("khId");
			if (khIds != null)
			{

				for(int i = 0; i < khIds.length; i++)
				{
					_scheme_kh += khIds[i] + ",";
				}
				
				if(_scheme_kh.trim().length() > 0)
				{
					_scheme_kh = _scheme_kh.substring(0, _scheme_kh.length() - 1);
				}
			}
			
			  String[] dtIds = request.getParameterValues("dtId");
				if (dtIds != null)
				{
			
					for(int i = 0; i < dtIds.length; i++)
					{
						_scheme_dt += dtIds[i] + ",";
					}
					
					if(_scheme_dt.trim().length() > 0)
					{
						_scheme_dt = _scheme_dt.substring(0, _scheme_dt.length() - 1);
					}
				}
        
		bccn.setNvbhIds(_scheme_nvbh);
		bccn.setNvgnIds(_scheme_nvgn);
		bccn.setDtIds(_scheme_dt);
		bccn.setKhIds(_scheme_kh);
		
        String action = request.getParameter("action");
		
		
	    
		  if(action.equals("pdf"))
		  {
			 bccn.initPdf();   	
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", " inline; filename=Bangkethutien.pdf");
	
			Document document = new Document();
			ServletOutputStream outstream = response.getOutputStream();
			
			this.CreateBangKeTT(document, outstream, bccn,_scheme_nvbh,_scheme_nvgn,_scheme_dt,_scheme_kh);
		}	
		  
		 
		  if(action.equals("excel"))
		  {
			  bccn.initPdf();   	
			ToExcel (response,bccn,userId,userTen, tuNgay, denNgay, _scheme_nvbh,_scheme_nvgn,_scheme_kh ,_scheme_dt);
		}	

 
	}

	private void ToExcel(HttpServletResponse response,IBCBangKeHoaDon tthdBean ,String userId, String userTen, String tuNgay, String DenNgay, String _scheme_nvbh,String _scheme_nvgn, String _scheme_kh, String _scheme_dt) throws IOException
	{  
        //bccn.initExcel();
        
		OutputStream out = null;
		ResultSet rs = tthdBean.getRS();
		
		if (rs!=null)
		{
		try
		{
			dbutils db = new dbutils();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BCBangKeHoaDon.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 10;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			sheet = w.createSheet("Sheet1", k);//ten sheet
			
						
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);
			
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
			
			WritableCellFormat cellF = new WritableCellFormat(cellFont);
			cellF.setAlignment(Alignment.RIGHT);	
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			
			sheet.addCell(new Label(0, 1, userTen, cellFormatTD));								
			sheet.mergeCells(0, 1, 2, 1);
			
			ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+tthdBean.getnppId()+"'");
			String diachinpp="";
			if(dc!=null)
			{
				while (dc.next())
					diachinpp=dc.getString("DiaChi");
			}
			
			sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
			
			sheet.addCell(new Label(0, 4, "BẢNG KÊ THU TIỀN ", celltieude));			
			//sheet.addMergedRegion(new CellRangeAddress(1,1,4,1))		
			
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 4, 5, 4);
			
			sheet.addCell(new Label(0, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(1, 6, tuNgay, cellFormatTD)); // lấy ngày đã chọn
			
			sheet.addCell(new Label(0, 7, "Đến ngày: "));
			sheet.addCell(new Label(1, 7, DenNgay, cellFormatTD)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			sheet.addCell(new Label(0, 9, "STT", cellFormat));
			sheet.addCell(new Label(1, 9, "KHÁCH HÀNG/ĐỐI TÁC", cellFormat));
			sheet.addCell(new Label(2, 9, "SỐ HÓA ĐƠN", cellFormat));
			sheet.addCell(new Label(3, 9, "NGÀY HÓA ĐƠN", cellFormat));
			sheet.addCell(new Label(4, 9, "TỔNG TIỀN HÓA ĐƠN", cellFormat));			
			sheet.addCell(new Label(5, 9, "SỐ TIỀN NỘP", cellFormat));
						
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 50);
			sheet.setColumnView(2, 15);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);
			
			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = new WritableCellFormat(cellFont);
			
			WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
			cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
			cformat1.setAlignment(Alignment.RIGHT);
			cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			Label label;
			Number number;

			int stt = 0;
			double tienhd=0;
			double total=0;
			
			while (rs.next())
			{
				
				stt++;				
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("tendt"), cformat3);sheet.addCell(label);
				label = new Label(2, j, rs.getString("SOHOADON"), cformat3);sheet.addCell(label);
				label = new Label(3, j, rs.getString("NGAYXUATHD"), cformat3);sheet.addCell(label);
				tienhd=rs.getDouble("SOTIENHOADON");				
				total+=tienhd;		
				
				/*label = new Label(4, j, formatter.format(tienhd), cformat1);*/
				number = new Number(4, j, tienhd, inFormat);sheet.addCell(number);
				label = new Label(5, j, "", cformat3); sheet.addCell(label);
				j++;
			}
			
			label = new Label(1, j, "TỔNG CỘNG", cformat3);
			sheet.addCell(label);
			
			number = new Number(4, j, total, inFormat);sheet.addCell(number);
			/*label = new Label(4, j, formatter.format(total), cformat3);*/
			w.write();
			w.close();
			rs.close();
			db.shutDown();
			
		} catch (Exception e)
		{
			System.out.println("Lỗi : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
		}else
 		{
 			try {
				throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
	}
	
	private void CreateBangKeTT(Document document,ServletOutputStream outstream, IBCBangKeHoaDon tthdBean, String _scheme_nvbh, String _scheme_nvgn, String _scheme_dt, String _scheme_kh)
	{
		dbutils db = new dbutils();
		try
		{	
			
           
			// LAY THONG TIN CHI NHANH/DOITAC
			
			String ten="";
			String diachi ="";
			
			String tenNVBH="";
			String tenNVGN="";
			String tenKH="";
			String tenDT="";
			
			
			String query ="SELECT TEN, DIACHI FROM NHAPHANPHOI WHERE PK_SEQ= '"+ tthdBean.getnppId() +"' ";
			 ResultSet  rss = db.get(query) ;
			 if(rss!= null)
			 {
				 while(rss.next())
				 {
					 ten = rss.getString("TEN");
					 diachi = rss.getString("DIACHI");
				 }rss.close();
			 }
			
			 if(_scheme_nvbh.trim().length() > 0)
			 {
				 query =" select TEN from DAIDIENKINHDOANH where PK_SEQ in ("+_scheme_nvbh+") ";
				 ResultSet LayNVBH = db.get(query);
				 if(LayNVBH!= null)
				 {
					 while(LayNVBH.next())
					 {
						tenNVBH += LayNVBH.getString("TEN");
						tenNVBH += ",";
					 }
					 LayNVBH.close();
				 }
				 tenNVBH = tenNVBH.substring(0, tenNVBH.length() - 1);
				 
			 }			 
			 
			 if(_scheme_nvgn.trim().length() > 0)
			 {
				 query =" select TEN from NHANVIENGIAONHAN where PK_SEQ in ("+_scheme_nvgn+") ";
				 ResultSet LayNVGN = db.get(query);
				 if(LayNVGN!= null)
				 {
					 while(LayNVGN.next())
					 {
						tenNVGN += LayNVGN.getString("TEN");
						tenNVGN += ",";
					 }
					 LayNVGN.close();
				 }
				 tenNVGN = tenNVGN.substring(0, tenNVGN.length() - 1);
			 }
			 if(_scheme_kh.trim().length() > 0)
			 {
				 query =" select TEN from KHACHHANG where PK_SEQ in ("+_scheme_kh+") ";
				 ResultSet LayKH = db.get(query);
				 if(LayKH!= null)
				 {
					 while(LayKH.next())
					 {
						tenKH += LayKH.getString("TEN");
						tenKH += ",";
					 }
					 LayKH.close();
				 }
				 tenKH = tenKH.substring(0, tenKH.length() - 1);
			 }
			 if(_scheme_dt.trim().length() > 0)
			 {
				 query =" select TEN from NHAPHANPHOI where PK_SEQ in ("+_scheme_dt+") ";
				 ResultSet LayDT = db.get(query);
				 if(LayDT!= null)
				 {
					 while(LayDT.next())
					 {
						tenDT += LayDT.getString("TEN");
						tenDT += ",";
					 }
					 LayDT.close();
				 }
				 tenDT = tenDT.substring(0, tenDT.length() - 1);
			 }
			 
			 
			
			DecimalFormat formatter = new DecimalFormat("#,###,###.##");
			PdfWriter writer = PdfWriter.getInstance(document, outstream);
			//document.setMargins(10.0f, -5.0f, 5.0f,0.0f);
			document.open();
			// document.setPageSize(new Rectangle(210.0f, 297.0f));
			
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 13, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			
			PdfPTable tableheader=new PdfPTable(3);
			tableheader.setWidthPercentage(95);
			float[] withsheader = { 350.0f, 60.0f, 40.0f};
			tableheader.setHorizontalAlignment(Element.ALIGN_CENTER);
			tableheader.setWidths(withsheader); 			
			
		// DÒNG 1	
			PdfPCell celltenCT= new PdfPCell();
			celltenCT.setHorizontalAlignment(Element.ALIGN_LEFT);
			celltenCT.setVerticalAlignment(Element.ALIGN_TOP);
						
			Paragraph pxk = new Paragraph(ten.toUpperCase(),  new Font(bf, 9, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			celltenCT.addElement(pxk);
			celltenCT.setBorder(0);
						
			tableheader.addCell(celltenCT);
			
			PdfPCell cellSophieu = new PdfPCell();
			cellSophieu.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSophieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
			cellSophieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellSophieu.setBorder(0);
						
			tableheader.addCell(cellSophieu);
			
			PdfPCell cellGTphieu = new PdfPCell();
			cellGTphieu.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTphieu.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			
			pxk = new Paragraph("", new Font(bf, 10, Font.NORMAL));
			cellGTphieu.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTphieu.setBorder(0);
						
			tableheader.addCell(cellGTphieu);
			

			
	   // DONG 2		
			
			PdfPCell cellDiachi= new PdfPCell();
			cellDiachi.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellDiachi.setVerticalAlignment(Element.ALIGN_TOP);						
		    pxk = new Paragraph(diachi.toUpperCase(),  new Font(bf, 10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellDiachi.addElement(pxk);
			cellDiachi.setBorder(0);
						
			tableheader.addCell(cellDiachi);
			
			PdfPCell cellTK = new PdfPCell();
			cellTK.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellTK.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			cellTK.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellTK.setBorder(0);
						
			tableheader.addCell(cellTK);
			
			
			PdfPCell cellGTtaikh = new PdfPCell();
			cellGTtaikh.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellGTtaikh.setVerticalAlignment(Element.ALIGN_MIDDLE);						
			pxk = new Paragraph(" ", new Font(bf, 10, Font.NORMAL));
			cellGTtaikh.addElement(pxk);
			pxk.setAlignment(Element.ALIGN_LEFT);
			cellGTtaikh.setBorder(0);
						
			tableheader.addCell(cellGTtaikh);
			
			document.add(tableheader);	
			
	// Tiêu đề		
	
			pxk = new Paragraph("BẢNG KÊ THU TIỀN" , new Font(bf, 13, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			document.add(pxk);

		

			
   // Thông tin Phiếu thu
			PdfPTable table1 =new PdfPTable(2);
			table1.setWidthPercentage(95);
			float[] withs1 = {100.0f,300.0f};
			table1.setWidths(withs1);
			
			if(tthdBean.getTuNgay().trim().length() > 0)
			{
				PdfPCell cell3 = new PdfPCell();			
				pxk = new Paragraph("Từ ngày: " , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell3.addElement(pxk);
				cell3.setBorder(0);						
				table1.addCell(cell3);	
				
				PdfPCell cell4= new PdfPCell();			
				pxk = new Paragraph(tthdBean.getTuNgay() , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell4.addElement(pxk);
				cell4.setBorder(0);						
				table1.addCell(cell4);
			}
			
			if(tthdBean.getDenNgay().trim().length() > 0)
			{
				PdfPCell cell3a = new PdfPCell();			
				pxk = new Paragraph("Đến ngày: " , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell3a.addElement(pxk);
				cell3a.setBorder(0);						
				table1.addCell(cell3a);	
				
				PdfPCell cell4a= new PdfPCell();			
				pxk = new Paragraph(tthdBean.getDenNgay(), new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell4a.addElement(pxk);
				cell4a.setBorder(0);						
				table1.addCell(cell4a);
			}
			
			if(_scheme_nvbh.trim().length() > 0)
			{
				PdfPCell cell5a = new PdfPCell();			
				pxk = new Paragraph("Trình dược viên: " , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell5a.addElement(pxk);
				cell5a.setBorder(0);						
				table1.addCell(cell5a);	
				
				PdfPCell cell5b= new PdfPCell();			
				pxk = new Paragraph(tenNVBH, new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell5b.addElement(pxk);
				cell5b.setBorder(0);						
				table1.addCell(cell5b);
			}
			
			if(_scheme_nvgn.trim().length() > 0)
			{
				PdfPCell cell6a = new PdfPCell();			
				pxk = new Paragraph("Nhân viên giao nhận: " , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell6a.addElement(pxk);
				cell6a.setBorder(0);						
				table1.addCell(cell6a);	
				
				PdfPCell cell6b= new PdfPCell();			
				pxk = new Paragraph(tenNVGN, new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell6b.addElement(pxk);
				cell6b.setBorder(0);						
				table1.addCell(cell6b);
			}
			
			if(_scheme_dt.trim().length() > 0)
			{
				PdfPCell cell7a = new PdfPCell();			
				pxk = new Paragraph("Đối tác: " , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell7a.addElement(pxk);
				cell7a.setBorder(0);						
				table1.addCell(cell7a);	
				
				PdfPCell cell7b= new PdfPCell();			
				pxk = new Paragraph(tenDT, new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell7b.addElement(pxk);
				cell7b.setBorder(0);						
				table1.addCell(cell7b);
			}
			
			if(_scheme_kh.trim().length() > 0)
			{
				PdfPCell cell8a = new PdfPCell();			
				pxk = new Paragraph("Khách hàng: " , new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell8a.addElement(pxk);
				cell8a.setBorder(0);						
				table1.addCell(cell8a);	
				
				PdfPCell cell8b= new PdfPCell();			
				pxk = new Paragraph(tenKH, new Font(bf, 12, Font.NORMAL));
				pxk.setAlignment(Element.ALIGN_LEFT);
				cell8b.addElement(pxk);
				cell8b.setBorder(0);						
				table1.addCell(cell8b);
			}
			
			document.add(table1);
			
			String[] th = new String[]{ "STT", "Khách hàng/ Đối tác", "Ký hiệu hóa đơn", "Số hóa đơn", "Ngày hóa đơn", "Tổng tiền hóa đơn", "Số tiền nộp" };
			
			PdfPTable table = new PdfPTable(th.length);
			table.setWidthPercentage(100);
			table.setSpacingBefore(10.0f);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			float[] withs =  { 8.0f, 40.0f, 15.0f, 15.0f, 15.0f, 20.0f, 15.0f  };
			table.setWidths(withs);
			
			

			PdfPCell[] cell1s = new PdfPCell[th.length];
			for (int i = 0; i < th.length ; i++)
			{
					cell1s[i] = new PdfPCell(new Paragraph(th[i], new Font(bf, 12, Font.NORMAL)));
					cell1s[i].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell1s[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell1s[i].setPadding(3.0f);
					table.addCell(cell1s[i]);		
			}
			
			  
				
				System.out.println("[ERP_DONDATHANG_SANPHAM]"+query);
				
				ResultSet rsSP =  tthdBean.getRS();
				double total = 0;
				int stt = 1;
				while(rsSP.next())
				{
					double sotienhoadon = rsSP.getDouble("SOTIENHOADON");
					
					total += sotienhoadon;
					
					String madoituong= rsSP.getString("madt");
					String tendoituong= rsSP.getString("tendt");
					String kyhieuhoadon= rsSP.getString("KYHIEU") == null ? "" : rsSP.getString("KYHIEU");
					String sohoadon= rsSP.getString("sohoadon") == null ? "" : rsSP.getString("sohoadon") ;
					String ngayhoadon= rsSP.getString("NGAYXUATHD") == null ? "" : rsSP.getString("NGAYXUATHD") ;
					
									
					String[] arr = new String[] { Integer.toString(stt), madoituong + "-" + tendoituong , kyhieuhoadon, sohoadon, ngayhoadon, formatter.format(sotienhoadon), " " };


					for (int j = 0; j < th.length; j++)
					{
						PdfPCell cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
						if (j == 5 ){
							cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						else{
							if(j==1)
								cells.setHorizontalAlignment(Element.ALIGN_LEFT);
							else
							cells.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						
						
						cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cells.setPadding(2.0f);	
											
						table.addCell(cells);
					}
					
					stt++;
					
				}
			
				String[] arr = new String[] { "Tổng ", formatter.format(total)," " };


				for (int j = 0; j < arr.length; j++)
				{
					PdfPCell cells = new PdfPCell(new Paragraph(arr[j], new Font(bf, 12, Font.NORMAL)));
					if (j == 0 ){
						cells.setHorizontalAlignment(Element.ALIGN_LEFT);
						cells.setColspan(5);
					}
					else{
						cells.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					
					
					cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cells.setPadding(2.0f);	
					cells.setFixedHeight(20.0f);					
					table.addCell(cells);
				}
				
			// DOC TIEN BANG CHU
			doctienrachu doctien = new doctienrachu();		    
		    String tien = doctien.docTien(Math.round(total));	
			//  Viết hoa ký tự đầu tiên
		    String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
		    System.out.println("So tien bang chu "+TienIN);
		    String[] arr1 = new String[] { "Bằng chữ:  " + TienIN};
 

			for (int j = 0; j < arr1.length; j++)
			{
				PdfPCell cells = new PdfPCell(new Paragraph(arr1[j], new Font(bf, 12, Font.NORMAL)));
					cells.setHorizontalAlignment(Element.ALIGN_LEFT);
					cells.setColspan(7);	
				cells.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cells.setPadding(2.0f);	
				cells.setFixedHeight(20.0f);
									
				table.addCell(cells);
			}
						
			
			document.add(table);
			

			
			
	// FOOTER
			PdfPTable table4 =new PdfPTable(2);
			table4.setWidthPercentage(100);
			float[] withs4 = {200.0f,250.0f};
			table4.setWidths(withs4);
			
			PdfPCell cell17 = new PdfPCell();						
			pxk = new Paragraph("", new Font(bf,10, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell17.addElement(pxk);
			cell17.setBorder(0);						
			table4.addCell(cell17);	
						
			
			PdfPCell cell18 = new PdfPCell();						
			pxk = new Paragraph("Ngày......tháng.......năm.......", new Font(bf, 12, Font.BOLDITALIC));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell18.addElement(pxk);
			cell18.setBorder(0);						
			table4.addCell(cell18);	
			
			PdfPCell cell19 = new PdfPCell();						
			pxk = new Paragraph("NGUỜI NỘP", new Font(bf, 12, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell19.addElement(pxk);
			cell19.setBorder(0);						
			table4.addCell(cell19);	
						
			PdfPCell cell20 = new PdfPCell();						
			pxk = new Paragraph("THỦ QUỸ", new Font(bf, 12, Font.BOLD));
			pxk.setAlignment(Element.ALIGN_CENTER);
			cell20.addElement(pxk);
			cell20.setBorder(0);						
			table4.addCell(cell20);	
			
		
						
			
						
			document.add(table4);
				
		
			document.close();
		
			
		
			
		}
		catch(Exception e)
		{
			System.out.println("Exception In Phieu Thu: " + e.getMessage());
		}
		finally
		{
			db.shutDown();
		}
		
	
		
	
		
	}



}
