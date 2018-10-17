package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.reports.IBCHoaDonChuaThanhToan;
import geso.traphaco.distributor.beans.reports.ITomTatCongNoTrongKy;
import geso.traphaco.distributor.beans.reports.imp.BCHoaDonChuaThanhToan;
import geso.traphaco.distributor.beans.reports.imp.TomTatCongNoTrongKy;

import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.net.ns.RefusePacket;

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


public class BCHoaDonChuaThanhToanSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    public BCHoaDonChuaThanhToanSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		 
		IBCHoaDonChuaThanhToan bccn = new BCHoaDonChuaThanhToan();
		
		Utility util=new Utility();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
    	session.setAttribute("loi", "");
		
    	String view=request.getParameter("view");
  		if(view == null)
  			view = "";
  		
  		System.out.println("view: "+view );
  		  		
    	bccn.setUserId(userId);
    	bccn.createRs();
    	if(bccn.getnppId()!=null)
    		view="NPP";
    	else
    		view="TT";
    	
    	session.setAttribute("bccn",bccn);
    	
    	String nextJSP="";
    	if(view.equals("TT"))
    	{
      		nextJSP = "/TraphacoHYERP/pages/Center/BCHoaDonChuaThanhToan_TT.jsp";
    		
    	}
        
        else
    	{    		
    		nextJSP = "/TraphacoHYERP/pages/Distributor/BCHoaDonChuaThanhToan.jsp";
    	}
    			
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
		
        String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));        
        String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
        
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
        

        String action = request.getParameter("action");
		String userTen = (String)session.getAttribute("userTen");
		String userId =  util.antiSQLInspection(request.getParameter("userId"));
		
		
	    String view=request.getParameter("view");
	  		if(view == null)
	  			view = "";
	  	
	  		System.out.println("view post: "+view );
	  	String nppId="";
	  		if(view.equals("TT"))
			{
				 nppId = util.antiSQLInspection(request.getParameter("nppId"));
				if (nppId == null)
						nppId = "";
				System.out.println("nppId"+ nppId);
				
			}else
			{
				nppId=util.getIdNhapp(userId);
				
			}
	  	System.out.println("TT???"+view);
	  	
	  	String nextJSP = "";
		
		
		
		System.out.println("Từ ngày "+tuNgay);
	    
		  if(action.equals("excel"))
		  {
	        	//OutputStream out = response.getOutputStream(); 
	        	//response.setContentType("application/vnd.ms-excel");
	        	//response.setHeader("Content-Disposition", "attachment; filename=BCCongNoChiTiet.xls");
	        	//CreatePivotTable(out,response,request, userId, userTen, tuNgay, denNgay, _scheme_nvbh,_scheme_nvgn,_scheme_kh ,_scheme_dt);
			  	ToExcel (response,nppId,userId, userTen, tuNgay, denNgay, _scheme_nvbh,_scheme_nvgn,_scheme_kh ,_scheme_dt);
			  	
			  	
	    		
			  	if(view.equals("TT"))
		    	{
		      		nextJSP = "/TraphacoHYERP/pages/Center/BCHoaDonChuaThanhToan_TT.jsp";
		    		
		    	}
		        
		        else
		    	{    		
		    		nextJSP = "/TraphacoHYERP/pages/Distributor/BCHoaDonChuaThanhToan.jsp";
		    	}
				response.sendRedirect(nextJSP);
	       }	

 
	}

	private void ToExcel(HttpServletResponse response, String nppId, String userId, String UserTen, String tuNgay, String DenNgay, String _scheme_nvbh,String _scheme_nvgn, String _scheme_kh, String _scheme_dt) throws IOException
	{
		IBCHoaDonChuaThanhToan obj = new BCHoaDonChuaThanhToan();
        obj.setUserId(userId);  
        obj.setUserName(UserTen);
        obj.setTuNgay(tuNgay);
        obj.setDenNgay(DenNgay);
        obj.setNvbhIds(_scheme_nvbh);
        obj.setNvgnIds(_scheme_nvgn);
        obj.setKhIds(_scheme_kh);
        obj.setDtIds(_scheme_dt);
        obj.setnppId(nppId);
        obj.initExcel();
        
		OutputStream out = null;
		ResultSet rs = obj.getRS();
		if (rs!=null)
		{
		try
		{
			dbutils db = new dbutils();
			NumberFormat formatter = new DecimalFormat("#,###,###");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BCHoaDonChuaThanhToan.xls");
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

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
			
			
			
			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			
			sheet.addCell(new Label(0, 1, UserTen, cellFormatTD));								
			sheet.mergeCells(0, 1, 2, 1);
			
			if(obj.getnppId()!=null)
			{
			ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+obj.getnppId()+"'");
			String diachinpp="";
			if(dc!=null)
			{
				while (dc.next())
					diachinpp=dc.getString("DiaChi");
			}
			
			
			sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
			}
			
			sheet.addCell(new Label(0, 4, "HÓA ĐƠN CHƯA THANH TOÁN ", celltieude));			
			//sheet.addMergedRegion(new CellRangeAddress(1,1,4,1))		
			
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 4, 7, 4);
			
			sheet.addCell(new Label(0, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(1, 6, tuNgay, cellFormatTD)); // lấy ngày đã chọn
			
			sheet.addCell(new Label(0, 7, "Đến ngày: "));
			sheet.addCell(new Label(1, 7, DenNgay, cellFormatTD)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			sheet.addCell(new Label(0, 9, "STT", cellFormat));
			sheet.addCell(new Label(1, 9, "Mã Fast", cellFormat));
			sheet.addCell(new Label(2, 9, "Khách hàng/Đối tác", cellFormat));
			sheet.addCell(new Label(3, 9, "Số hóa đơn", cellFormat));
			sheet.addCell(new Label(4, 9, "Ký hiệu HD", cellFormat));			
			sheet.addCell(new Label(5, 9, "Ngày xuất HD", cellFormat));
			sheet.addCell(new Label(6, 9, "Tổng tiền HD", cellFormat));
						
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 15);
			sheet.setColumnView(2, 50);
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
			
			Label label;
			Number number;

			int stt = 0;			
			
			while (rs.next())
			{
				stt++;				
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("maFAST"), cformat3);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("tenkh"), cformat3);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("sohoadon"), cformat3);
				sheet.addCell(label);	
				label = new Label(4, j, rs.getString("KYHIEU"), cformat3);
				sheet.addCell(label);	
				label = new Label(5, j, rs.getString("ngayhoadon"), cformat3);
				sheet.addCell(label);
				label = new Label(6, j, formatter.format(rs.getDouble("TongTien")), cformat1);
				sheet.addCell(label);								
				j++;
			}
						
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
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String userId, String userName, String tuNgay, String denNgay, String nvbhId, String nvgnId, String khId, String dtId) throws IOException 
	{
	
		FileInputStream fstream = null;		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoTongHopCongNo.xlsx");
		
		Workbook workbook = new Workbook();		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		IBCHoaDonChuaThanhToan obj = new BCHoaDonChuaThanhToan();
        obj.setUserId(userId);       
        obj.setTuNgay(tuNgay);
        obj.setDenNgay(denNgay);
        obj.setNvbhIds(nvbhId);
        obj.setNvgnIds(nvgnId);
        obj.initExcel();
      //Buoc 1
	    
	  //Buoc2 tao khung
	    //ham tao khu du lieu
	     obj.createStaticHeader(workbook,obj);
	  //Buoc3 
	     // day du lieu vao
	     obj.createStaticData(workbook);

	     workbook.save(out);
	     
		
	}
	
	private void CreateReportPDF(Document document, HttpServletRequest request, ServletOutputStream outstream, String userId, String userTen, String tuNgay, String denNgay) throws IOException
	{
		try{
			IBCHoaDonChuaThanhToan obj = new BCHoaDonChuaThanhToan();
	        obj.setUserId(userId);
	        obj.setTuNgay(tuNgay);
	        obj.setDenNgay(denNgay);
	        obj.init();
	        NumberFormat formatter = new DecimalFormat("#,###,###");
	        
		    PdfWriter.getInstance(document, outstream);
			document.open();
			
			//chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 12, Font.BOLD);
			Font font2 = new Font(bf, 8, Font.BOLD);
			Font font3 = new Font(bf, 8, Font.UNDERLINE);
			 
			Paragraph mdcu = new Paragraph("Công Nợ Theo Từng Hóa Đơn", font);
			mdcu.setSpacingAfter(15);
			mdcu.setAlignment(Element.ALIGN_CENTER);
			document.add(mdcu);
			
			PdfPTable tableHead = new PdfPTable(2);
			tableHead.setWidthPercentage(50);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with = {15.0f, 20.0f}; //set chieu rong cac columns
			tableHead.setWidths(with);
			
			
			PdfPCell cell1 = new PdfPCell(new Paragraph("Nhà phân phối ", font2));
			PdfPCell cell2 = new PdfPCell(new Paragraph(userTen, font2));
			cell1.setBorder(0);
			cell2.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			
			PdfPCell cell3 = new PdfPCell(new Paragraph("Tính đến ngày khóa sổ ", font2));
			PdfPCell cell4 = new PdfPCell(new Paragraph(obj.getNgayKS(), font2));
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);
							
			document.add(tableHead);
			//Table Content
			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(110);
			float[] widths = {20.0f, 8.0f, 8.0f, 10.0f, 10.0f, 10.0f}; //set chieu rong cac columns
	        table.setWidths(widths);
	        
			String[] th = new String[]{"Số hóa đơn", "", "Chứng từ thanh toán",  "Ngày hóa đơn",  "Tiền hóa đơn", "Thanh toán"};
			PdfPCell[] cell = new PdfPCell[9];
						
			for(int i=0; i <= 5 ; i++)
			{
				cell[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[i].setPadding(2);
				cell[i].setBackgroundColor(BaseColor.WHITE);							
				table.addCell(cell[i]);
				
			}
			ResultSet rs = obj.getRS();
			int i = 9;
			String khId = "";

			double tongTienDH = 0;
			double tongTienTT = 0;
			double tmpttkh=0;
			double tmphdkh=0;
			String hoadonbk="";
			String soHD="";
			String tienHD="";
			String ctTT = "";
			String ngayHD = "";
			String tientt ="";

			Font font4 = new Font(bf, 7);			

			try{
				while(rs.next()){
					if(khId.length()==0 )
					{
						khId=rs.getString("smartid") +"-"+ rs.getString("khid") + " - " + rs.getString("tenkh");
					}
					
					if(!((rs.getString("smartid") +"-"+ rs.getString("khid") + " - " + rs.getString("tenkh")).equals(khId))){
			
						cell[0] = new PdfPCell(new Paragraph(khId, font4));		
						cell[0].setHorizontalAlignment(Element.ALIGN_LEFT);
						cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[0].setPadding(2);
						table.addCell(cell[0]);				

						cell[1] = new PdfPCell(new Paragraph("", font4));		
						cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[1].setPadding(2);
						table.addCell(cell[1]);				
				
						cell[2] = new PdfPCell(new Paragraph("", font4));		
						cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[2].setPadding(2);
						table.addCell(cell[2]);										

						cell[3] = new PdfPCell(new Paragraph("", font4));		
						cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
						cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[3].setPadding(2);
						table.addCell(cell[3]);				

						cell[4] = new PdfPCell(new Paragraph(formatter.format(tmphdkh), font4));		
						cell[4].setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[4].setPadding(2);
						table.addCell(cell[4]);				

						cell[5] = new PdfPCell(new Paragraph(formatter.format(tmpttkh), font4));		
						cell[5].setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell[5].setPadding(2);
						table.addCell(cell[5]);				
						
						tmphdkh=0;
						tmpttkh=0;
						
						khId =rs.getString("smartid") +"-"+ rs.getString("khid") + " - " + rs.getString("tenkh");
						// tong tien thanh theo khach hang
	
									
						i++;
					}
					soHD = rs.getString("dhid");
					if(soHD.equals(hoadonbk)){
						tienHD ="0";							
					}else{
						tienHD = rs. getString("tiendh");
		    			tongTienDH += Double.parseDouble(tienHD);
		    			hoadonbk=soHD;
					}
						
					System.out.println(i +" -- "+tongTienDH);
						
	    			ctTT = rs.getString("SOCHUNGTU");
//	    			String ngayTT = rs.getString("ngaythanhtoan");;
	    			ngayHD = rs.getString("ngaydh");
	    				    				
	    			tientt = rs.getString("TIENTHANHTOAN");
	    			tongTienTT += Double.parseDouble(tientt);
					
					
					cell[0] = new PdfPCell(new Paragraph("", font4));		
					cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[0].setPadding(2);
					table.addCell(cell[0]);				

					cell[1] = new PdfPCell(new Paragraph(soHD, font4));		
					cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[1].setPadding(2);
					table.addCell(cell[1]);				
			
					if(ctTT.equals("0"))
						cell[2] = new PdfPCell(new Paragraph("", font4));
					else
						cell[2] = new PdfPCell(new Paragraph(ctTT, font4));						
					cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[2].setPadding(2);
					table.addCell(cell[2]);				
					
					cell[3] = new PdfPCell(new Paragraph(ngayHD, font4));		
					cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[3].setPadding(2);
					table.addCell(cell[3]);				

					cell[4] = new PdfPCell(new Paragraph(formatter.format(Math.round(Float.parseFloat(tienHD))), font4));		
					cell[4].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[4].setPadding(2);
					table.addCell(cell[4]);				

					cell[5] = new PdfPCell(new Paragraph(formatter.format(Math.round(Float.parseFloat(tientt))), font4));		
					cell[5].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell[5].setPadding(2);
					table.addCell(cell[5]);				

					tmpttkh=tmpttkh+ Double.parseDouble(tientt);
					tmphdkh=tmphdkh+ Double.parseDouble(tienHD);

					i++;
				}

				cell[0] = new PdfPCell(new Paragraph(khId, font4));		
				cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[0].setPadding(2);
				table.addCell(cell[0]);				

				cell[1] = new PdfPCell(new Paragraph("", font4));		
				cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[1].setPadding(2);
				table.addCell(cell[1]);				
		
				cell[2] = new PdfPCell(new Paragraph("", font4));		
				cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[2].setPadding(2);
				table.addCell(cell[2]);										

				cell[3] = new PdfPCell(new Paragraph("", font4));		
				cell[3].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[3].setPadding(2);
				table.addCell(cell[3]);				

				cell[4] = new PdfPCell(new Paragraph(formatter.format(tmphdkh), font4));		
				cell[4].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[4].setPadding(2);
				table.addCell(cell[4]);				

				cell[5] = new PdfPCell(new Paragraph(formatter.format(tmpttkh), font4));		
				cell[5].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[5].setPadding(2);
				table.addCell(cell[5]);				
				
				
				font4 = new Font(bf, 7, Font.BOLD);
				cell[0] = new PdfPCell(new Paragraph("Tổng cộng", font4));		
				cell[0].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[0].setPadding(2);
				table.addCell(cell[0]);				

				cell[1] = new PdfPCell(new Paragraph("", font4));		
				cell[1].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[1].setPadding(2);
				table.addCell(cell[1]);				

				cell[2] = new PdfPCell(new Paragraph("", font4));		
				cell[2].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[2].setPadding(2);
				table.addCell(cell[2]);				
				
				cell[3] = new PdfPCell(new Paragraph("", font4));		
				cell[3].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[3].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[3].setPadding(3);
				table.addCell(cell[3]);				

				cell[4] = new PdfPCell(new Paragraph(formatter.format(tongTienDH), font4));		
				cell[4].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[4].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[4].setPadding(3);
				table.addCell(cell[4]);				

				cell[5] = new PdfPCell(new Paragraph(formatter.format(tongTienTT), font4));		
				cell[5].setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell[5].setPadding(3);
				table.addCell(cell[5]);				
				

			}catch(Exception e){}
					
			document.add(table);
			
			document.close(); 
		}
		catch(DocumentException e)
		{
			e.printStackTrace();
		}

	}

}
